import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by johnswd on 7/19/2018.
 */
public class Target extends MapObject {

    private static int DIAM = 10;
    private static Color COLOR = Color.BLUE;


    public Target(int xMax, int yMax, DisplayPanel d){
        super(new Circle2D( new Point2D.Double( xMax/2, DIAM/2 ), DIAM/2 ), mObjectID.target,
                new Rectangle( 0, 0, xMax, yMax ), d);
    }

    public Target(int x, int y, int xMax, int yMax, DisplayPanel d){
        super(new Circle2D( new Point2D.Double( x, y ), DIAM/2 ), mObjectID.target,
                new Rectangle( 0, 0, xMax, yMax ), d);
    }

    @Override
    public void draw( Graphics g ) {
        g.setColor( COLOR );
        g.fillOval( (int)getObjectShape().getX(), (int)getObjectShape().getY(), DIAM, DIAM );
        g.setColor( BORDER_COLOR );
        g.drawOval( (int)getObjectShape().getX(), (int)getObjectShape().getY(), DIAM, DIAM );
    }

    @Override
    public void update() {
        return;
    }

    @Override
    public void restart() {
        return;
    }

    @Override
    public Circle2D getObjectShape() {
        return (Circle2D)objectShape;
    }

    public int getRadius(){
        return DIAM/2;
    }

}
