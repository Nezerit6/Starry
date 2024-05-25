package sta.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.production.Drill;
import mindustry.world.draw.DrawTurret;

import static mindustry.type.ItemStack.with;

public class StarryBlocks {
    public static Block

    //environments
    cobaltOre, darkgrass, darkmossWall, dehydrate, dune, dunecliffWall, hematiteOre, iridiumOre, lightmossWall, pinkgrass, pinkstone,

    //turrets
    chire, basedTurret,

    //production
    cobaltDrill;

    public static void load() {

        cobaltOre = new OreBlock("cobaltOre"){{
            itemDrop = StarryItems.cobalt;
        }};

        darkgrass = new Floor("darkgrass"){{
        }};

        darkmossWall = new StaticWall("darkmossWall"){{
           variants = 2;
        }};

        dehydrate = new Floor("dehydrate"){{
        }};

        dune = new Floor("dune"){{
           variants = 3;
        }};

        dunecliffWall = new StaticWall("dunecliffWall"){{
           variants = 2;
        }};

        hematiteOre = new OreBlock("hematiteOre"){{
           itemDrop = StarryItems.hematite;
        }};

        iridiumOre = new OreBlock("iridiumOre"){{
           itemDrop = StarryItems.iridium;
        }};

        lightmossWall = new StaticWall("lightmossWall"){{
           variants = 2;
        }};

        pinkgrass = new Floor("pinkgrass"){{
        }};

        pinkstone = new Floor("pinkstone"){{
        }};

        chire = new ItemTurret("chire"){{
            requirements(Category.turret, with(StarryItems.cobalt, 24));
            ammo(
                    StarryItems.cobalt, new BasicBulletType(3.4f,11){{
                        width = 6;
                        height = 8;
                        lifetime = 60;
                        ammoPerShot = 2;
                    }}
            );

            drawer = new DrawTurret("based-"){
                {
                    parts.addAll(

                            new RegionPart("-side") {{
                                heatProgress = PartProgress.warmup;
                                progress = PartProgress.warmup;
                                mirror = true;
                                moveX = 2f * 4f / 3f;
                                moveY = -0.5f;
                                moveRot = -40f;
                                under = true;
                                heatColor = Color.red.cpy();

                            }});
                }};
            size = 1;
            recoil = 1;
            reload = 25;
            range = 110;
            health = 150;
            inaccuracy = 9;
            rotateSpeed = 12;
            coolant = consumeCoolant(0.1f);
            researchCostMultiplier = 0.10f;

        }};

        basedTurret = new ItemTurret("BasedTurret") {{
            requirements(Category.turret, with(Items.copper, 110, Items.lead, 65, Items.titanium, 35, Items.silicon, 20));
            health = 430;
            rotateSpeed = 4.2f;
            recoil = 1f;
            size = 2;
            range = 205;
            reload = 90f;
            shootY = 6.5f;
            heatColor = Color.valueOf("AFEEEE");
            inaccuracy = 3;
            liquidCapacity = 70;
            squareSprite = false;
            shootSound = Sounds.laser;
            shootEffect = Fx.none;
            smokeEffect = StarryFx.laserSparks;
            moveWhileCharging = false;
            accurateDelay = false;
            shoot.firstShotDelay = 60f;
            coolant = consumeCoolant(0.2f);
            consumePower(3.6f);

            ammo(
                    Items.copper, new BasicBulletType(12.6f, 35){{
                        despawnEffect = Fx.colorSpark;
                        hitEffect = Fx.none;
                        lifetime = 17.5f;
                        width = 4;
                        height = 28;
                        ammoMultiplier = 1;
                        splashDamageRadius = 32f * 0.50f;
                        splashDamage = 20f;
                        chargeEffect = new MultiEffect(Fx.lancerLaserCharge, Fx.lancerLaserChargeBegin);
                        status = StatusEffects.shocked;
                        statusDuration = 240f;
                        hitColor = backColor = trailColor = Color.valueOf("afeeee");
                        trailLength = 3;
                        trailWidth = 1.9f;
                        homingPower = 0.03f;
                        homingDelay = 2f;
                        homingRange = 60f;
                    }},
                    Items.silicon, new BasicBulletType(15.6f, 47){{
                        despawnEffect = Fx.colorSpark;
                        hitEffect = Fx.none;
                        lifetime = 14.5f;
                        width = 2.5f;
                        height = 18;
                        ammoMultiplier = 1;
                        splashDamageRadius = 32f * 0.75f;
                        splashDamage = 25f;
                        chargeEffect = new MultiEffect(Fx.lancerLaserCharge, Fx.lancerLaserChargeBegin);
                        status = StatusEffects.shocked;
                        statusDuration = 240f;
                        hitColor = backColor = trailColor = Color.valueOf("698e8e");
                        trailLength = 4;
                        trailWidth = 1.3f;
                        homingPower = 0.05f;
                        homingDelay = 2f;
                        homingRange = 60f;
                        reloadMultiplier = 0.6f;
                    }}
            );

            drawer = new DrawTurret("based-") {{
                parts.addAll(
                        new RegionPart("-nozzle") {{
                            progress = DrawPart.PartProgress.warmup;
                            heatProgress = DrawPart.PartProgress.charge;
                            mirror = true;
                            under = false;
                            moveRot = 7f;
                            moves.add(new DrawPart.PartMove(DrawPart.PartProgress.recoil, 0f, 0f, -30f));
                            heatColor = Color.valueOf("afeeee");
                        }}
                );
            }};
        }};

        cobaltDrill = new Drill("cobalt-drill"){{
            requirements(Category.production, with(StarryItems.cobalt, 18));
            tier = 1;
            drillTime = 770;
            size = 2;
            squareSprite = false;

            researchCost = with(StarryItems.cobalt, 10);

            consumeLiquid(Liquids.water, 0.05f).boost();
        }};

    }
}