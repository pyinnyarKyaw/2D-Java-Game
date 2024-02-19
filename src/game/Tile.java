package game;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class Tile extends StaticBody {
    private static final float tileSize = 2f;

    public Tile(World world, Vec2 position, BodyImage image) {
        super(world, new BoxShape(tileSize/2, tileSize / 2));
        setPosition(position);
        addImage(image);
    }
}