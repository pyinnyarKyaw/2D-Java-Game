package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Enemy extends Walker implements StepListener {

    private final static Shape enemyShape = new PolygonShape(
            -1.34f,1.01f, -0.15f,-1.55f, 1.28f,0.94f);
    private final static BodyImage image = new BodyImage("data/monster-left.gif", 5);
    private final static BodyImage attackedImage = new BodyImage("data/hit-2.png", 5);
    Timer imageRevertTimer;
    Timer destructionTimer;
    public int health = 2;
    private boolean facingRight;
    private float leftBound;
    private float rightBound;


    public Enemy(World world, float leftBound, float rightBound) {
        super(world, enemyShape);
        this.addImage(image);
        facingRight = false;
        startWalking(-3);
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        world.addStepListener(this);

        destructionTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                destroy();
            }
        });
        destructionTimer.setRepeats(false);


        imageRevertTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (facingRight) {
                    removeAllImages();
                    addImage(new BodyImage("data/monster.gif", 5));
                } else {
                    removeAllImages();
                    addImage(new BodyImage("data/monster-left.gif", 5));
                }
            }
        });
        imageRevertTimer.setRepeats(false);
    }

    public void ChangeDirection(){
        stopWalking();
        if (!facingRight){
            facingRight = true;
            startWalking(3);
            this.removeAllImages();
            this.addImage(new BodyImage("data/monster.gif", 5));
        } else{
            facingRight = false;
            startWalking(-3);
            this.removeAllImages();
            this.addImage(new BodyImage("data/monster-left.gif", 5));
        }
    }

    public void delayedDestruct(){
        showHitImage();
        destructionTimer.start();
    }

    public void showHitImage(){
        this.removeAllImages();
        this.addImage(attackedImage);
        imageRevertTimer.start();
    }

    @Override
    public void preStep(StepEvent stepEvent) {
        if (facingRight && getPosition().x >= rightBound) {
            ChangeDirection();
        } else if (!facingRight && getPosition().x <= leftBound) {
            ChangeDirection();
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }

}
