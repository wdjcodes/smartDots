import java.awt.*;

/**
 * Created by johnswd on 7/17/2018.
 */
public abstract class Obstacle extends MapObject {


    public Obstacle(int x, int y, int w, int h, int xMax, int yMax, DisplayPanel display){
        super(new Rectangle( x, y, w, h ), mObjectID.enemy, new Rectangle(0, 0, xMax, yMax  ), display);
    }

    @Override
    public void draw(Graphics g){
        g.setColor( Color.YELLOW);
//        g.fillRect(getLeft(), getTop(), getWidth() -1 , getHeight() -1);
        g.fillRect(getLeft(), getTop(), getWidth(), getHeight() );
        g.setColor( Color.BLACK );
//        g.drawRect(getLeft(), getTop(), getWidth() -1, getHeight() -1);
        g.drawRect(getLeft(), getTop(), getWidth(), getHeight() );
    }

    @Override
    public Rectangle getObjectShape() {
        return (Rectangle)objectShape;
    }

    public int getLeft(){
        return (int)getObjectShape().getX();
    }

    public int getWidth(){
        return (int)getObjectShape().getWidth();
    }

    public int getTop(){
        return (int)getObjectShape().getY();
    }

    public int getHeight(){
        return (int) getObjectShape().getHeight();
    }
}
