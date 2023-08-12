package sta.content;

import arc.graphics.Color;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.Team;
import mindustry.graphics.g3d.*;
import mindustry.type.Planet;
import mindustry.world.Block;
import sta.planets.StarryPlanetGenerator;

public class StarryPlanets {
    public static Planet
            plumia, starry;

    public static void load() {
        Planets.serpulo.hiddenItems.addAll(Vars.content.items()).removeAll(Items.serpuloItems);
        Planets.erekir.hiddenItems.addAll(Vars.content.items()).removeAll(Items.erekirItems);

        plumia = new Planet("plumia", Planets.sun, 5, 1) {{
            accessible = false;
            meshLoader = () -> new SunMesh(
                    this, 3,
                    5, 0.3, 1.7, 1.2, 1,
                    1.1f,
                    Color.valueOf("ffffff")
            );
            bloom = true;
            drawOrbit = false;
            atmosphereColor = Color.valueOf("ffffff");
            orbitRadius = 666;
        }};

        starry = new Planet("starry", StarryPlanets.plumia, 1f, 3) {{
            generator = new StarryPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 2, 0.15f, 0.14f, 5, Color.valueOf("ffffff").a(0.75f), 2, 0.42f, 1f, 0.43f),
                    new HexSkyMesh(this, 3, 0.6f, 0.15f, 5, Color.valueOf("ffffff").a(0.75f), 2, 0.42f, 1.2f, 0.45f)
            );
            alwaysUnlocked = true;
            //hasAtmosphere = false;
            landCloudColor = Color.valueOf("ed6542");
            atmosphereColor = Color.valueOf("B79E54");//,b58724
            startSector = 28;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            clearSectorOnLose = true;
            sectorSeed = 8;
            defaultCore = Blocks.coreShard;
            accessible = true;
            orbitRadius = 73;
            ruleSetter = r -> {
                r.waveTeam = Team.malis;
                r.attributes.clear();
                r.showSpawns = true;
                r.fog = false;
                r.onlyDepositCore = false;
            };
            unlockedOnLand.add(Blocks.coreShard);
            hiddenItems.addAll(Vars.content.items()).removeAll(StarryItems.starryItems);
        }};
    }
}