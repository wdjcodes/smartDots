import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by johnswd on 7/18/2018.
 */
public abstract class MapObject {

    protected static Color BORDER_COLOR = Color.BLACK;

//    protected int xDraw, yDraw;
    protected MotionVector velocity, accel;
    protected Shape objectShape;
    protected mObjectID ID;
    protected DisplayPanel display;
    protected Rectangle mapBounds;

    protected MapObject(Shape objectShape, mObjectID ID, Rectangle mapBounds, DisplayPanel display){
        this.objectShape = objectShape;
        this.ID = ID;
        this.display = display;
        this.mapBounds = mapBounds;
        velocity = new MotionVector(  );
        accel = new MotionVector(  );
    }

    public abstract void draw( Graphics g);
    public abstract void update();
    public abstract void restart();

    public abstract Shape getObjectShape();
//    public abstract Rectangle getDrawingBounds();

    public Rectangle getObjectBounds(){
        return objectShape.getBounds();
    }

    public mObjectID getID(){
        return ID;
    }
}
