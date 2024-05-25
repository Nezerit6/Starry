package sta.content;

import arc.graphics.Color;
import mindustry.content.Liquids;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.part.RegionPart;
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
    chire,

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

            drawer = new DrawTurret("reinforced-"){
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