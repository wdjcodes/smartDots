import java.awt.*;

/**
 * Created by johnswd on 7/23/2018.
 */
public class MapBounds extends MapObject {

    protected MapBounds( Shape objectShape, mObjectID ID, Rectangle mapBounds, DisplayPanel display ) {
        super( objectShape, ID, mapBounds, display );
    }

    @Override
    public void draw( Graphics g ) {

    }

    @Override
    public void update() {

    }

    @Override
    public void restart() {

    }

    @Override
    public Shape getObjectShape() {
        return null;
    }
}
