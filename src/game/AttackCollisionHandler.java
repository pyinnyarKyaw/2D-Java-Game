package game;

import city.cs.engine.*;

public class AttackCollisionHandler implements SensorListener {

    private int counter = 0;
    private Enemy enemy;

    public AttackCollisionHandler(Enemy enemy){
        this.enemy = enemy;
    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        if (sensorEvent.getContactBody() instanceof Enemy){
            enemy.showHitImage();
            enemy.health--;
            if (enemy.health <=0){
                enemy.delayedDestruct();
            }
        }

    }

    @Override
    public void endContact(SensorEvent sensorEvent) {

    }
}
