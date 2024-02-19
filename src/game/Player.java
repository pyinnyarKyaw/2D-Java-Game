package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static game.PlayerController.facingRight;

public class Player extends Walker {

    private int lives;
    private DynamicBody hitbox;
    private Timer hitboxTimer;
    private AttackCollisionHandler ch;

    private final static Shape playerShape = new PolygonShape(
                    -0.72f,1.34f,
                            0.4f,1.34f,
                            0.85f,-0.86f,
                            0.63f,-1.85f,
                            -0.55f,-1.87f,
                            -0.93f,-0.86f);
    private final static BodyImage image = new BodyImage("data/adventurer-idle.gif", 4);
    public int getLives() {return lives;}
    public void setLives(int lives) { this.lives = lives; }

    public Player(World world, AttackCollisionHandler ch){
        super(world, playerShape);
        SolidFixture fixture = new SolidFixture(this, playerShape, 110);
        fixture.setFriction(100);
        this.addImage(image);
        this.ch = ch;

        //player collision handler
        PlayerCollisionHandler pch = new PlayerCollisionHandler(this);
        this.addCollisionListener(pch);

        //timer to remove attack hitbox
        hitboxTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hitbox != null) {
                    hitbox.destroy();
                    hitbox = null;
                }
            }
        });
        hitboxTimer.setRepeats(false);
    }

    public void attack(){
        if (facingRight){
            Shape hitboxShape = new PolygonShape(
                    0.78f,-1.47f,
                    1.50f,-1.2f,
                    2.23f,-0.03f,
                    2.17f,0.88f,
                    1.37f,1.90f,
                    -0.31f,1.51f);
            hitbox = new DynamicBody(getWorld());
            Vec2 position = getPosition().add(new Vec2(0.6f,0));
            hitbox.setPosition(position);
            hitbox.setGravityScale(0);
            Sensor sensor = new Sensor(hitbox, hitboxShape);
            BodyImage transparentImage = new BodyImage("data/transparent.png", 1);
            hitbox.addImage(transparentImage);
            sensor.addSensorListener(ch);
            try {
                SoundClip attackSound = new SoundClip("data/adventurer-attack.wav");
                attackSound.play();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
        } else {
            Shape hitboxShape = new PolygonShape(
                    -0.78f,-1.47f,
                    -1.50f,-1.2f,
                    -2.23f,-0.03f,
                    -2.17f,0.88f,
                    -1.37f,1.90f,
                    0.31f,1.51f);
            hitbox = new DynamicBody(getWorld());
            Vec2 position = getPosition().add(new Vec2(-0.6f,0));
            hitbox.setPosition(position);
            hitbox.setGravityScale(0);
            Sensor sensor = new Sensor(hitbox, hitboxShape);
            BodyImage transparentImage = new BodyImage("data/transparent.png", 1);
            hitbox.addImage(transparentImage);
            sensor.addSensorListener(ch);
            try {
                SoundClip attackSound = new SoundClip("data/adventurer-attack.wav");
                attackSound.play();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
        }
        hitboxTimer.start();
    }

}
