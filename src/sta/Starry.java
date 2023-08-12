package sta;

import mindustry.mod.Mod;
import sta.content.*;

public class Starry extends Mod {

    @Override
    public void loadContent() {

        StarryItems.load();
        StarryBlocks.load();
        StarryPlanets.load();

    }
}