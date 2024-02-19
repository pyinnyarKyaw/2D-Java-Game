package game;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;


/**
 * Main game entry point
 */
public class Game implements StepListener{

    Player player;
    private GameWorld world;
    private GameView view;
    private PlayerController playerController;

    private SoundClip gameMusic;

    /** Initialise a new Game. */
    public Game() {

        //1. instantiate game world
        world = new GameWorld();

        //game music
        try{
            gameMusic = new SoundClip("data/desert-theme.wav");
            gameMusic.loop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){

        }

        //retrieve player from world
        player = world.getPlayer();

        //2. instantiate GameView to look into the game world
        view = new GameView(world, player);

        //3. instantiates player controls
        playerController = new PlayerController(world.getPlayer());
        view.addKeyListener(playerController);

        view.addMouseListener(new GiveFocus(view));

        //optional: draw a 1-metre grid over the view
        //view.setGridResolution(1);

        //4. create a Java window (frame) and add the game
        //   view to it
        final JFrame frame = new JFrame("2D Platformer");
        frame.add(view);

        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // size the frame to fit the world view
        frame.pack();
        // finally, make the frame visible
        frame.setVisible(true);

        //optional: uncomment this to make a debugging view
        //JFrame debugView = new DebugViewer(world, 1152, 648);

        world.addStepListener(this);

        // start our game world simulation!
        world.start();
    }

    /** Restarts the game when player dies */
    public void restartGame(){
        world.stop();

        world = new GameWorld();

        player = world.getPlayer();

        view.setWorld(world);
        view.setPlayer(player);

        playerController.setPlayer(player);

        world.addStepListener(this);
        world.start();
    }

    @Override
    public void preStep(StepEvent e) {
    }

    @Override
    public void postStep(StepEvent e) {
        if (player.getLives() <= 0) {
            restartGame();
        }
    }

    /** Run the game. */
    public static void main(String[] args) {

        new Game();
    }
}
