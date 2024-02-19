package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

public class PlayerCollisionHandler implements CollisionListener {

    private Player player;

    public PlayerCollisionHandler(Player player){
        this.player = player;
    }

    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Enemy) {
            player.setLives(player.getLives() - 1);
            if (player.getLives() <= 0) {
                // Handle player death here
                System.out.println("DEAD");
            }
        }
    }
}
