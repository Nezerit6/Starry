package sta.planets;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.noise.*;
import mindustry.ai.*;
import mindustry.ai.Astar.*;
import mindustry.content.*;
import mindustry.game.Schematics;
import mindustry.game.Team;
import mindustry.maps.generators.*;
import mindustry.type.*;
import mindustry.world.*;
import sta.content.StarryBlocks;

import static arc.graphics.g2d.Draw.scl;
import static mindustry.Vars.*;
public class StarryPlanetGenerator extends PlanetGenerator {
    public float heightPow = 3f, heightMult = 1.6f;
    float waterOffset = 0.07f;
    String launchSchem = "bXNjaAF4nGNgYWBhZmDJS8xNZWBMZOBOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWTgT84vStVNyy/NS0mEyjOCEJAAAEfaEBU=";

    public static final int seed = 7;
    public static int widthSeed = 1, heightSeed = 2, roomSeed = 3, strokeSeed = 4;

    public Block[] arr = {
            StarryBlocks.pinkgrass, Blocks.water,
            StarryBlocks.dune, Blocks.water, StarryBlocks.dune,
            StarryBlocks.pinkgrass, StarryBlocks.dune,
            StarryBlocks.dune, StarryBlocks.pinkgrass, Blocks.water, StarryBlocks.dune, StarryBlocks.dune,
            StarryBlocks.pinkgrass,
            StarryBlocks.dune, StarryBlocks.dune,
            Blocks.water, StarryBlocks.dune,
            StarryBlocks.dune, StarryBlocks.pinkgrass, StarryBlocks.dune, StarryBlocks.dune,
            Blocks.water,
            //Blocks.snow,
    };
    ObjectMap<Block, Block> dec = ObjectMap.of(

    );

    float rawHeight(Vec3 pos) {
        return Simplex.noise3d(seed, 28, 0.7f, 0.9f, pos.x, pos.y, pos.z);
    }
    float humidity(Vec3 pos)  {
        return Simplex.noise3d(13, 20, 0.3f, 0.8f, pos.x, pos.y, pos.z);
    }

    Block getBlock(Vec3 pos) {
        float height = 1 - rawHeight(pos);
        float humidity = humidity(pos);
        return arr[Mathf.clamp((int) (height + humidity * arr.length), 0, arr.length -1)];
    }
    Block getBlock(float x, float y, float z) {
        Vec3 pos = new Vec3(x, y, z);
        float height = 1 - rawHeight(pos);
        float humidity = humidity(pos);
        return arr[Mathf.clamp((int) (height + humidity * arr.length), 0, arr.length -1)];
    }

    @Override
    public void generateSector(Sector sector){
    }

    @Override
    public boolean allowLanding(Sector sector){
        return false;
    }

    @Override
    public float getHeight(Vec3 position){
        return Mathf.pow(rawHeight(position), heightPow) * heightMult;
    }

    @Override
    public Color getColor(Vec3 pos) {
        return getBlock(pos).mapColor;
    }

    @Override
    protected float noise(float x, float y, double octaves, double falloff, double scl, double mag) {
        return Simplex.noise2d(seed, octaves, falloff, 1f / scl, x, y) * (float)mag;
    }
    protected float noise3d(int seed, Vec3 p, double octaves, double falloff, double scl, double mag) {
        return Simplex.noise3d(seed, octaves, falloff, 1f / scl, p.x, p.y, p.z) * (float)mag;
    }

    @Override
    public Seq<Tile> pathfind(int startX, int startY, int endX, int endY, TileHueristic th, DistanceHeuristic dh){
        return Astar.pathfind(startX, startY, endX, endY, th, dh, tile -> true);
    }

    @Override
    protected void generate() {

        Vec2 pos = new Vec2();
        Seq<Room> r = new Seq<>();
        Seq<Room> roomseq = new Seq<>();
        float maxd = Mathf.dst(width/2f, height/2f);

        // enemy and player rooms
        Vec2 trns = Tmp.v1.trns(rand.random(360f), width/2.6f);
        int
                spawnX = (int)(trns.x + width/2f), spawnY = (int)(trns.y + height/2f),
                launchX = (int)(-trns.y + width/2f), launchY = (int)(-trns.y + height/2f);
        r.add(
                new Room(
                        spawnX,
                        spawnY,
                        (int) (10f + noise3d(strokeSeed * 90, sector.tile.v, 1, 1, 1f, 5))
                ),
                new Room(
                        launchX,
                        launchY,
                        (int)( 10f + noise3d(strokeSeed * 96, sector.tile.v, 1, 1, 1f, 5))
                )
        );

        // floor
        pass((x, y) -> {
            floor = getBlock(x / (width * 0.5f), y / (height * 0.5f), sector.tile.v.z);
        });

        pass((x, y) -> {
            if (block == Blocks.air) {
                block = floor.asFloor().wall;
            }

            //decoration
            if (rand.chance(0.01) && block == Blocks.air) {
                block = dec.get(floor, floor.asFloor().decoration);
            }

            //gallium
            if(floor == StarryBlocks.dune){
                if(Math.abs(0.5f - noise(x - 40, y, 2, 0.7, 80)) > 0.25f &&
                        Math.abs(0.5f - noise(x, y + sector.id*10, 1, 1.5f, 80)) > 0.41f && !(roomseq.contains(t -> Mathf.within(x, y, t.x, t.y, 15)))){
                    floor = StarryBlocks.dune;
                }
            }
        });

        // create rooms
        for (int i = 0; i < 7; i++) {
            pos.set(
                    Mathf.clamp((int) noise3d(widthSeed * i, sector.tile.v, 1, 1, 1f, width), 20, width - 20),
                    Mathf.clamp((int) noise3d(heightSeed * i, sector.tile.v, 1, 1, 1f, height), 20, height - 20)
            );
            r.add(
                    new Room(
                            (int) pos.x,
                            (int) pos.y,
                            (int) (10f + noise3d(strokeSeed * i, sector.tile.v, 1, 1, 1f, 5))
                    )
            );
        }

        // connect rooms
        r.each(room -> {
            int roomId = 0;

            // get room to connect
            room.connect(
                    r.get(
                            (int) noise3d((int) roomSeed * roomId, sector.tile.v, 1, 1, 1f, r.size - 1)
                    )
            );

            // if it tries to connect to itself, it'll connect to spawn instead
            if (room.connected == room) room.connect(r.get(0));

            // actually connect the rooms
            room.open();
            if (room.isConnected()) {
                brush(pathfind(room.x, room.y, room.connected.x, room.connected.y, tile -> 5000f, Astar.manhattan), 20);
            }
            roomId++;
        });

        // make connections look more natural
        distort(130f, 76f);

        // make core and enemy area
        erase(spawnX, spawnY, 15);
        erase(launchX, launchY, 10);
        brush(pathfind(r.get(0).x, r.get(0).y, r.get(1).x, r.get(1).y, tile -> 5000f, Astar.manhattan), 20);

        // more roughness
        distort(112f, 24f);
        distort(7f, 13f);
        distort(2f, 7f);
        median(3);


        // ores
        Seq<Block> ores = Seq.with(StarryBlocks.cobaltOre, StarryBlocks.hematiteOre);
        float poles = 1f - Math.abs(sector.tile.v.y);
        float nmag = 0.5f;
        float scl = 1f;
        float addscl = 1.3f;
        if(Simplex.noise3d(seed, 2, 0.5, scl, sector.tile.v.x, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.3f*addscl){
            ores.add(StarryBlocks.hematiteOre);
        }
        if(Simplex.noise3d(seed, 2, 0.5, scl, sector.tile.v.x, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.25f*addscl){
            ores.add(StarryBlocks.cobaltOre);
        }


        pass((x, y) -> {
            if (!floor.asFloor().hasSurface()) return;

            float offsetX = x - 4, offsetY = y + 23;
            for (int i = ores.size - 1; i >= 0; i--) {
                Block entry = ores.get(i);
                if (Math.abs(0.5 - noise(offsetX, offsetY + i * 999, 2, 0.7f, (40 + i * 2))) > 0.22f + i * 0.01 &&
                        Math.abs(0.5 - noise(offsetX, offsetY - i * 999, 1, 1, (30 + i * 4))) > 0.35f ) {
                    ore = entry;
                    break;
                }
            }
        });

        Room spawn = null;
        Seq<Room> enemies = new Seq<>();
        int enemySpawns = rand.random(1, Math.max(Mathf.floor(sector.threat * 4), 1));
        int offset = rand.nextInt(360);
        float length = (float)(width / 2.55 - rand.random(13, 23));
        int angleStep = 5;

        for (int i = 0; i < 360; i += angleStep){
            int angle = offset + i;
            int cx = (int)Math.floor(width / 2f + Angles.trnsx(angle, length));
            int cy = (int)Math.floor(height / 2f + Angles.trnsy(angle, length));

            if (i + angleStep >= 360){
                spawn = new Room(cx, cy, rand.random(10, 18));
                r.add(spawn);

                for(int j = 0; j < enemySpawns; j++){
                    float enemyOffset = rand.range(60);

                    Tmp.v1.set(cx - width / 2f, cy - height / 2f).rotate(180 + enemyOffset).add(width / 2f, height / 2f);
                    Room espawn = new Room((int)Math.floor(Tmp.v1.x), (int)Math.floor(Tmp.v1.y), rand.random(10, 16));
                    r.add(espawn);
                    enemies.add(espawn);
                }
                break;
            }
        }
        Schematics.placeLoadout(Schematics.readBase64(launchSchem), spawnX, spawnY, Team.sharded);

        tiles.getn(r.get(1).x, r.get(1).y).setOverlay(Blocks.spawn);

        /**if (sector.hasEnemyBase()){
         basegen.generate(tiles, enemies.map(room -> tiles.getn(room.x, room.y)), tiles.get(spawnX, spawnY), state.rules.waveTeam, sector, sector.threat);
         state.rules.attackMode = sector.info.attack = true;
         }else{
         state.rules.winWave = sector.info.winWave = 10 + 5 * (int)Math.max(sector.threat * 10, 1);
         }*/

        state.rules.waveSpacing = Mathf.lerp(60 * 65 * 2, 60f * 60f * 1f, Math.max(sector.threat - 0.4f, 0f));
        state.rules.spawns = StarryWaves.generate(sector.threat, new Rand(), state.rules.attackMode);
        state.rules.winWave = sector.info.winWave = 10 + 5 * (int)Math.max(sector.threat * 12, 1);
        state.rules.waves = sector.info.waves = true;
        state.rules.env = sector.planet.defaultEnv;
    }

    @Override
    public void postGenerate(Tiles tiles) {
    }

    public class Room {
        int x, y, size;
        Room connected;

        public Room(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.connected = this;
        }

        public int getDistance(Room to) {
            int
                    distX = Math.abs(x - to.x),
                    distY = Math.abs(y - to.y);
            return (int) (distX+distY/2f);
        }

        public boolean isConnected() {
            return connected != this;
        }

        public void open() {erase(x, y, size);}

        public void connect(Room to) {
            if (
                    to.connected == this ||
                            connected != this ||
                            getDistance(to) < size
            ) return;

            connected = to;
        }
    }
}