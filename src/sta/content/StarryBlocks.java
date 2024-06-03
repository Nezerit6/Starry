package sta.content;

import arc.graphics.Color;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.production.Drill;
import mindustry.world.draw.DrawTurret;
import sta.world.blocks.power.MagnesiumNode;

import static mindustry.type.ItemStack.with;

public class StarryBlocks {
    public static Block

    //environments
    cobaltOre, darkgrass, darkmossWall, dehydrate, dune, dunecliffWall, hematiteOre, iridiumOre, lightmossWall, pinkgrass, pinkstone,

    //turrets
    chire, basedTurret, stormBringer, test,

    //production
    cobaltDrill, magnesiumNode;

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

        /*chire = new ItemTurret("chire"){{
            requirements(Category.turret, with(StarryItems.cobalt, 24));
            reload = 3;
            ammo(
                    StarryItems.cobalt, new BasicBulletType(10.0f,4){{
                        width = 6;
                        height = 8;
                        lifetime = 60;
                        ammoPerShot = 2;
                        collidesTeam = true;
                        collidesGround = true;
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

        }};*/

        basedTurret = new ItemTurret("BasedTurret") {{
            requirements(Category.turret, with(Items.copper, 110, Items.lead, 65, Items.titanium, 35, Items.silicon, 20));
            health = 430;
            rotateSpeed = 4.2f;
            recoil = 3f;
            size = 2;
            range = 205;
            reload = 90f;
            shootY = 6.5f;
            heatColor = Color.valueOf("AFEEEE");
            inaccuracy = 3;
            liquidCapacity = 70;
            squareSprite = false;
            shootSound = StarrySounds.bigLaserShoot;
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

        stormBringer = new PowerTurret("StormBringer"){{
            requirements(Category.turret, with(StarryItems.cobalt, 1));
            size = 2;
            range = 130;
            recoil = 2f;
            reload = 420f;
            health = 600;
            inaccuracy = 14;
            rotateSpeed = 7;

            //accurateDelay = false;
            shoot.shots = 32;
            shoot.shotDelay = 4;
            shoot.firstShotDelay = 15f;

            shootEffect = Fx.sparkShoot;
            shootSound = Sounds.lasershoot;

            consumePower(1.4f);
            coolant = consumeCoolant(0.2f);

            shootType = new LaserBoltBulletType(6, 13){{
                knockback = 0.3f;
                lifetime = 20f;
                backColor = Pal.heal;
                frontColor = Color.white;
                status = StatusEffects.corroded;
                statusDuration = 240f;
                smokeEffect = Fx.none;
                hitEffect = despawnEffect = Fx.hitLaser;
                hitColor = trailColor = Pal.heal;
                trailLength = 2;
                trailWidth = 1.8f;
            }};

            drawer = new DrawTurret("based-") {{
                parts.addAll(
                        new RegionPart("-side"){{
                            progress = PartProgress.warmup;
                            mirror = true;
                            under = false;
                            moveY = 4.5f;
                            moves.add(new PartMove(PartProgress.heat, 0f, -4.5f, 0f));
                        }}
                );
            }};
        }};

        /*regeneratingBlaster = new ItemTurret("regeneratingBlaster"){{
            requirements(Category.turret, with(StarryItems.cobalt, 24));
            size = 1;
            recoil = 1;
            reload = 39;
            range = 110;
            health = 150;
            inaccuracy = 0;
            rotateSpeed = 13.4f;
            shootSound = Sounds.lasershoot;
            consumePower(1.4f);
            researchCostMultiplier = 0.10f;



            ammo(
                    StarryItems.cobalt, new HealBulletType(5.3f,60f){{
                        lifetime = 24;
                        ammoPerShot = 2;
                    }}
            );

            drawer = new DrawTurret("based-");

        }};*/

        cobaltDrill = new Drill("cobalt-drill"){{
            requirements(Category.production, with(StarryItems.cobalt, 18));
            tier = 1;
            drillTime = 770;
            size = 2;
            squareSprite = false;

            researchCost = with(StarryItems.cobalt, 10);

            consumeLiquid(Liquids.water, 0.05f).boost();
        }};

        magnesiumNode = new MagnesiumNode("magnesiumNode"){{
            requirements(Category.power, with(StarryItems.hematite, 10));
            health = 65;
            baseExplosiveness = 1;
            laserRange = 5;
            outputsPower = true;
            //laserColor1 = Color.black;
            //laserColor2 = Color.valueOf("5c5e9e");

            maxNodes = 5;
            //consumePowerBuffered(750f);
        }};
    }
}