import javax.swing.JPanel;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by johnswd on 7/16/2018.
 */
public class DisplayPanel extends JPanel {

    public static int TARGET_DIAM = 10;
    private static int FONT_SIZE = 14;

    private List <Dot> dots;
    private List <MapObject> mapObjects = new ArrayList<>(  );
//    private int xTarget = 300;
//    private int yTarget = TARGET_DIAM/2;
//    private int xDraw = xTarget - TARGET_DIAM/2;
//    private int yDraw = yTarget - TARGET_DIAM/2;
    private String gen = "Generation: 0";
    private String best = "Best: N/A";

    public DisplayPanel(List<MapObject> mapObjects){
        setPreferredSize( new Dimension( 600,600 ) );
        this.mapObjects = mapObjects;
    }

    public DisplayPanel( int x, int y, List<MapObject> mapObjects){
        setPreferredSize( new Dimension( x, y ) );
        this.mapObjects = mapObjects;
    }

//    public DisplayPanel( int x, int y, int xTarget, int yTarget){
//        setPreferredSize( new Dimension( x, y ) );
//        this.xTarget = xTarget;
//        this.yTarget = yTarget;
//        xDraw = this.xTarget - TARGET_DIAM/2;
//        yDraw = this.yTarget - TARGET_DIAM/2;
//    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent( g );


        if( mapObjects != null) {
            for ( int i = 0; i < mapObjects.size(); i++ ) {
                mapObjects.get( i ).draw( g );
            }
        }
//        if(dots != null) {
//            for ( int i = 1; i < dots.size(); i++ ) {
//                dots.get( i ).draw( g );
//            }
//            dots.get(0).draw(g);
//        }
        g.setFont( new Font("Arial", Font.BOLD, FONT_SIZE ) );
        g.setColor( Color.BLACK );
        g.drawString( gen, 10, 10 );
        g.drawString( best, 10, 20);
    }

    public void addDot(Dot d){
        dots.add(d);
    }

    public void setDots(List<Dot> d){
        dots = d;
        this.repaint();
    }

    public void clearDots(){
        dots.clear();
    }

    public void setGen(int g){
        gen = "Generation: " + g;
    }

    public void setBest(double b){
        best = "Best: " + b;
    }

    public void setMapObjects( List<? extends MapObject> o){
        mapObjects = (List<MapObject>)o;
    }

    public void appendObjects(List<? extends MapObject> o){
        mapObjects.addAll(o);
    }

//    public void clearObjectType(Class c){
//        ArrayList<MapObject> newObjects = new ArrayList<>(  );
//        for( int i = 0; i < mapObjects.size(); i++){
//            if( mapObjects.get(i).getClass() != c){
//                newObjects.add( mapObjects.get(i));
//            }
//        }
//        mapObjects = newObjects;
//    }
}
