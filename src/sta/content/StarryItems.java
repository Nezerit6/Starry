package sta.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;

public class StarryItems {
    public static Item

    cobalt, hematite, iridium, lithium;

    public static final Seq<Item> starryItems = new Seq<>();
    public static void load() {

        cobalt = new Item("cobalt", Color.valueOf("8a7c94")){{
            hardness = 1;
        }};

        hematite = new Item("hematite", Color.valueOf("d56563")) {{
            hardness = 1;
        }};

        iridium = new Item("iridium", Color.valueOf("5a5e82")) {{
            hardness = 1;
        }};

        lithium = new Item("lithium", Color.valueOf("747d91")){{
        }};

    }
}
