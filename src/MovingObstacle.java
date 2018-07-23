import java.awt.*;

/**
 * Created by johnswd on 7/20/2018.
 */
public class MovingObstacle extends Obstacle {

    private MotionVector velocity;
    private Rectangle startRect;
    private MotionVector startVel;

    public MovingObstacle( int x, int y, int w, int h, int vel, double rad, int xMax, int yMax, DisplayPanel display ) {
        super( x, y, w, h, xMax, yMax, display );
        velocity = new MotionVector( rad ).scalarMultiply( vel );
        startRect = new Rectangle( getObjectShape());
        startVel = new MotionVector(velocity);
    }

    @Override
    public void update() {
        move();
        if(mapCollide()){
            velocity.negate();
        }
    }

    @Override
    public void restart() {
        velocity = new MotionVector( startVel );
        objectShape = new Rectangle( startRect );
    }

    private void move(){
//        display.repaint( (int)getObjectBounds().getX(), (int)getObjectBounds().getY(),
//                (int)getObjectBounds().getWidth(), (int)getObjectBounds().getHeight());
        getObjectShape().setLocation(velocity.actOnPoint( getObjectShape().getLocation() ));
//        display.repaint( (int)getObjectBounds().getX(), (int)getObjectBounds().getY(),
//                (int)getObjectBounds().getWidth(), (int)getObjectBounds().getHeight());
    }

    private boolean mapCollide(){
        if(getObjectShape().getX() <= mapBounds.getX() || getObjectShape().getX() <= mapBounds.getY() ||
                getObjectShape().getX() + getObjectShape().getWidth() >= mapBounds.getX() + mapBounds.getWidth() -1 ||
                getObjectShape().getY() + getObjectShape().getHeight() >= mapBounds.getY() + mapBounds.getHeight() - 1){
            return true;
        }
        return false;
    }
}
