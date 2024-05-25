package sta.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;

public class StarryFx {

    public static final Effect none = new Effect(0, 0f, e -> {
    }),

    laserSparks = new Effect(11f, e -> {
        color(Color.white, e.color, e.fin());
        stroke(e.fout() * 1.1f + 0.5f);
        randLenVectors(e.id, 5, 15f * e.fin(), e.rotation, 4f, (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 2f + 0.5f);
        });
    }),

    steamAbsorption = new Effect(72f, e -> {
        color(Pal.coalBlack, 0.1f + 0.9f * e.fin());

        randLenVectors(e.id, 12, 0.4f + 37f * e.fout(), e.rotation, 360f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fslope() * 0.05f + 0.87f);
        });
    });
}