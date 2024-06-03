package sta.world.blocks.power;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.core.Renderer;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.power.PowerNode;
import sta.content.StarryFx;
import sta.content.StarrySounds;

import static mindustry.Vars.*;

public class MagnesiumNode extends PowerNode {
    public Effect energyEffect = StarryFx.powerSparks;
    public Color lightningColor = Pal.surge;
    public Sound lightningSound = Sounds.spark;
    public float lightningDamage = 5f;
    public int lightningLength = 9;
    public int lightningAmount = 3;
    public int explosionPuddles = 10;
    public float explosionPuddleRange = tilesize * 2f;
    public float powerLimit = 16.7f;
    public final Vec2 v1 = new Vec2();
    public Color backupColour = Pal.powerLight;

    public MagnesiumNode(String name) {
        super(name);
        update = true;
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("voltage", (MagnesiumNodeBuild entity) ->
                new Bar(
                        () -> "Voltage",
                        () -> Pal.accent,
                        () -> entity.voltage / 10f
                )
        );
    }

    public class MagnesiumNodeBuild extends PowerNodeBuild {
        private float voltage = 0f;
        private float timeAccumulator = 0f;
        private boolean smokeEffectActive = false;

        @Override
        public void draw() {
            super.draw();

            if (Mathf.zero(Renderer.laserOpacity) || isPayload()) return;

            for (int i = 0; i < power.links.size; i++) {
                Building link = world.build(power.links.get(i));

                if (!linkValid(this, link)) continue;

                if (link.block instanceof PowerNode && link.id >= id) continue;

                drawLaser(x, y, link.x, link.y, size, link.block.size);
            }

            Draw.reset();
            drawParticles();
            drawHeatOverlay();
        }

        public void drawParticles() {
            if (!state.isPlaying()) return;

            float currentPower = getCurrentPower();

            if (currentPower != 0 && Mathf.random() > 0.99) {
                int amount = determineAmountOfBubbles(currentPower);
                for (int i = 0; i < (amount > 10 ? 10 : amount); i++) {
                    energyEffect.at(x + Mathf.range(-1f, 1f), y + Mathf.range(-1f, 1f), Pal.bulletYellow);
                }
            }
        }

        public void drawHeatOverlay() {
            float progressRatio = voltage / 10f;
            Color color = Color.white.cpy().lerp(Color.red, progressRatio);
            Draw.color(color);
            Draw.rect(Core.atlas.find(name + "-heat"), x, y);
            Draw.reset();
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if (!state.isPlaying()) return;

            float currentPower = getCurrentPower();

            if (currentPower > powerLimit) {
                timeAccumulator += Time.delta;
                if (timeAccumulator >= 60f) {
                    voltage = Math.min(voltage + 1, 10);
                    timeAccumulator = 0f;

                    if (voltage >= 4) {
                        Fx.fireSmoke.at(x, y);
                    }
                        if (voltage >= 9) {
                        StarrySounds.shortCircuit.at(x,y);
                        }

                }

                if (voltage == 10) {
                    damage(Mathf.random(0.1f, 0.7f));
                }
            } else {
                timeAccumulator = 0f;
                voltage = 0f;
            }

            if (voltage >= 4) {
                Fx.fireSmoke.at(x, y);
            }
        }

        public float getCurrentPower() {
            return power.graph.getPowerBalance();
        }

        public int determineAmountOfBubbles(float power) {
            return Mathf.round(power / 10);
        }

        @Override
        public void onDestroyed() {
            if (voltage >= 10) {
                for (int i = 0; i < explosionPuddles; i++) {
                    v1.trns(Mathf.random(360f), Mathf.random(explosionPuddleRange));
                }
                Team team1 = null;
                for (int i = 0; i < lightningAmount; i++) {
                    Lightning.create(team1, lightningColor, lightningDamage, x, y, Mathf.random(360), lightningLength);
                }
                lightningSound.at(tile, Mathf.random(0.9f, 1.1f));
                Damage.damage(x, y, explosionPuddleRange, lightningDamage * 4);
            }
            super.onDestroyed();
        }
    }
}
