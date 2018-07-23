import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by johnswd on 7/19/2018.
 */
public class MotionVector extends Point2D.Double {

    public MotionVector(double rad){
        super(Math.cos(rad), Math.sin(rad));
    }

    public MotionVector(double x, double y){
        super(x, y);
    }

    public MotionVector(){
        super();
    }

    public MotionVector(MotionVector mv){
        super(mv.getX(), mv.getY());
    }

    public MotionVector add(MotionVector mv){
        x += mv.getX();
        y += mv.getY();
        return this;
    }

    public MotionVector subtract(MotionVector mv){
        x -= mv.getX();
        y -= mv.getY();
        return this;
    }

    public MotionVector scalarDivide(double d){
        x = x/d;
        y = y/d;
        return this;
    }

    public MotionVector scalarMultiply(double m){
        x = x*m;
        y = y*m;
        return this;
    }

    public MotionVector capMagnitude(double cap){
        if(this.getMagnitude() > cap) {
            return this.scalarDivide( this.getMagnitude() ).scalarMultiply( cap );
        }
        return this;
    }

    public MotionVector normalize(){
        return this.scalarDivide( this.getMagnitude() );
    }

    public MotionVector normalize(double magnitude){
        return this.scalarDivide( this.getMagnitude() ).scalarMultiply( magnitude );
    }

    public MotionVector negate(){
        return this.scalarMultiply( -1 );
    }

    public double getMagnitude(){
        return Math.sqrt( Math.pow( x, 2 ) + Math.pow( y, 2 ) );
    }

    public double getAngle(){
        return Math.atan2( y, x );
    }

    public Point2D.Double actOnPoint( Point2D.Double d){
        d.setLocation( d.getX() + x, d.getY() + y );
        return d;
    }

    public Point2D.Double actOnPoint(double x, double y){
        return new Point2D.Double(x + this.x, y + this.y  );
    }

    public Point actOnPoint(Point p){
        p.setLocation( (int)(p.getX() + x), (int)(p.getY() + y) );
        return p;
    }

}
