package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class GameWorld extends World{

    Player player;

    Enemy enemy;
    float startX = -28f;
    float startY = -15f;
    float xOffset = 1.92f;
    float yOffset = 2f;

    public GameWorld(){
        super(60);

        super.setGravity(25);

        BodyImage groundImage = new BodyImage("data/ground.png", 2f);
        BodyImage dirtImage = new BodyImage("data/dirt.png", 2f);


        //left ground
        for (int i=0;i<5;i++){
            new Tile(this, new Vec2(-28f + i*1.92f, -3f), groundImage);
        }
        //left lowerGround
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 5; i++) {
                new Tile(this, new Vec2(startX + i * xOffset, startY + j * yOffset), dirtImage);
            }
        }
        //middle ground
        for (int i=0;i<8;i++){
            new Tile(this, new Vec2(-20.3f + i*1.92f, -11f), groundImage);
        }
        //middle lowerGround
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 8; i++) {
                new Tile(this, new Vec2(-20.3f + i * xOffset, -15f + j * yOffset), dirtImage);
            }
        }
        //right ground
        for (int i=0;i<13;i++){
            new Tile(this, new Vec2(6f + i*1.92f, -11f), groundImage);
        }
        //right lowerGround
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 13; i++) {
                new Tile(this, new Vec2(6f + i * xOffset, -15f + j * yOffset), dirtImage);
            }
        }

        //spikes
        Shape spikeShape = new BoxShape(1, 1);
        StaticBody spike = new StaticBody(this, spikeShape);
        spike.setPosition(new Vec2(-3, -16));
        spike.addImage(new BodyImage("data/spikes.png", 20));

        StaticBody spike1 = new StaticBody(this, spikeShape);
        spike1.setPosition(new Vec2(0, -16));
        spike1.addImage(new BodyImage("data/spikes.png", 20));

        StaticBody spike2 = new StaticBody(this, spikeShape);
        spike2.setPosition(new Vec2(3, -16));
        spike2.addImage(new BodyImage("data/spikes.png", 20));

        //collision listener for spikes
        spike.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent e) {
                if (e.getOtherBody() == player) {
                    player.setLives(0);
                }
            }
        });
        spike1.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent e) {
                if (e.getOtherBody() == player) {
                    player.setLives(0);
                }
            }
        });
        spike2.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent e) {
                if (e.getOtherBody() == player) {
                    player.setLives(0);
                }
            }
        });

        //suspended platform
        Shape platformShape = new PolygonShape(
                -6.37f,3.54f, 7.07f,3.61f, 7.14f,-1.5f, -6.37f,-1.64f);
        StaticBody platform1 = new StaticBody(this, platformShape);
        platform1.setPosition(new Vec2(3f, 3f));
        platform1.addImage(new BodyImage("data/platform.png", 25));

        //lift
        Lift lift = new Lift(this);
        lift.setPosition(new Vec2(-11, -8));
        lift.addImage(new BodyImage("data/lift.png", 1.3f));


        //out of bounds walls
        Shape wallShape = new BoxShape(0.1f, 20);
        StaticBody wallRight = new StaticBody (this, wallShape);
        wallRight.setPosition(new Vec2(29, -4f));

        StaticBody wallLeft = new StaticBody (this, wallShape);
        wallLeft.setPosition(new Vec2(-29, -4f));

        //enemy
        Enemy enemy = new Enemy(this, 10, 20);
        enemy.setPosition(new Vec2(25,-5));
        AttackCollisionHandler ch = new AttackCollisionHandler(enemy);

        //character (with an overlaid image)
        player = new Player(this, ch);
        player.setPosition(new Vec2(-25,5));
        player.setLives(5);


    }

    public Player getPlayer(){
        return player;
    }

}
