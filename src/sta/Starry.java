package sta;

import arc.util.*;
import mindustry.mod.*;
import sta.content.StarryBlocks;
import sta.content.StarryItems;
import sta.content.StarryPlanets;
import sta.content.StarrySounds;

public class Starry extends Mod{

    public Starry(){
        Log.info("Loaded ExampleJavaMod constructor.");

        //listen for game load event
        /*Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("behold").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                dialog.cont.image(Core.atlas.find("example-java-mod-frog")).pad(20f).row();
                dialog.cont.button("I see", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });*/
    }

    @Override
    public void loadContent(){
        Log.info("Loading some example content.");

        StarrySounds.load();
        StarryItems.load();
        StarryBlocks.load();
        StarryPlanets.load();
    }
}