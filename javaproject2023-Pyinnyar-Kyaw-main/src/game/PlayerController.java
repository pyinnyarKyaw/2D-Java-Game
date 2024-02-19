package game;

import city.cs.engine.BodyImage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener {
    Player player;
    public static boolean facingRight;
    Timer attackTimer;
    boolean attacking = false;

    public PlayerController(Player player) {
        this.player = player;
        attackTimer = new Timer (500, new AttackAnimationHandler());
        attackTimer.setRepeats(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        // other key commands omitted
        if (code == KeyEvent.VK_A) {
            facingRight = false;
            player.startWalking(-8);
            player.removeAllImages();
            player.addImage(new BodyImage("data/adventurer-run-left.gif", 4));
        } else if (code == KeyEvent.VK_D) {
            facingRight = true;
            player.startWalking(8);
            player.removeAllImages();
            player.addImage(new BodyImage("data/adventurer-run.gif", 4));
        } else if (code == KeyEvent.VK_SPACE){
            player.jump(15);
        } else if (code == KeyEvent.VK_K){
            if (!attacking){
                attacking = true;
                if (facingRight){
                    player.removeAllImages();
                    player.addImage(new BodyImage("data/adventurer-attack.gif", 4));
                    attackTimer.start();
                    player.attack();
                } else {
                    player.removeAllImages();
                    player.addImage(new BodyImage("data/adventurer-attack-left.gif", 4));
                    attackTimer.start();
                    player.attack();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        // other key commands omitted
        if (code == KeyEvent.VK_A) {
            facingRight = false;
            player.stopWalking();
            player.removeAllImages();
            player.addImage(new BodyImage("data/adventurer-idle-left.gif", 4));
        } else if (code == KeyEvent.VK_D) {
            facingRight = true;
            player.stopWalking();
            player.removeAllImages();
            player.addImage(new BodyImage("data/adventurer-idle.gif", 4));
        } else if (code == KeyEvent.VK_K){
            if (!attacking){
                if (facingRight){
                    player.removeAllImages();
                    player.addImage(new BodyImage("data/adventurer-idle.gif", 4));
                } else {
                    player.removeAllImages();
                    player.addImage(new BodyImage("data/adventurer-idle-left.gif", 4));
                }
            }
        }
    }

    private class AttackAnimationHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            attacking = false;
            player.removeAllImages();
            if (facingRight){
                player.addImage(new BodyImage("data/adventurer-idle.gif", 4));
            } else {
                player.addImage(new BodyImage("data/adventurer-idle-left.gif", 4));
            }
        }
    }

    public void setPlayer(Player player){
        this.player = player;
    }

}
