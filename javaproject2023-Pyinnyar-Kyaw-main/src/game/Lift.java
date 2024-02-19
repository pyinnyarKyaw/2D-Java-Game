package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Lift extends StaticBody implements StepListener {
    private static final Shape liftShape = new BoxShape(1.8f, 0.5f);
    private Vec2 startUpPosition;
    private Vec2 newUpPosition = new Vec2(-11, -8);
    private float top, bottom;
    private float deltaUp;


    public Lift(World w) {
        super(w, liftShape);
            startUpPosition = newUpPosition;
            bottom = startUpPosition.y;
            top = startUpPosition.y + 15;
            deltaUp = 0.08f;
        w.addStepListener(this);
    }

    @Override
    public void preStep(StepEvent stepEvent) {
            if (getPosition().y < bottom) {
                this.setPosition(startUpPosition);
                deltaUp *= -1;
            }
            if (getPosition().y > top) {
                deltaUp *= -1;
            }
            this.setPosition(new Vec2(this.getPosition().x, this.getPosition().y + deltaUp));
        }

    @Override
    public void postStep(StepEvent stepEvent) {

    }
}