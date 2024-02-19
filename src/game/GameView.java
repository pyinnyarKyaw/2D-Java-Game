package game;

import city.cs.engine.UserView;
import city.cs.engine.World;

import javax.swing.*;
import java.awt.*;

public class GameView extends UserView {

    private final Image background = new ImageIcon("data/hills.gif").getImage();
    private Player player;


    public GameView(World world, Player player){
        super(world, 1152, 648);
        this.player = player;
    }

    @Override
    protected void paintBackground(Graphics2D g){
        g.drawImage(background, 0, 0, 1152, 648, this);
    }

    @Override
    protected void paintForeground(Graphics2D g) {
        super.paintForeground(g);
        ImageIcon heartIcon = new ImageIcon("data/heart.png");
        int lives = player.getLives();
        for (int i = 0; i < lives; i++) {
            g.drawImage(heartIcon.getImage(), i * heartIcon.getIconWidth(), 0, null);
        }
    }

    public void setPlayer(Player player){
        this.player = player;
    }
}