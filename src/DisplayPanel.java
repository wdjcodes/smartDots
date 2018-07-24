import javax.swing.JPanel;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by johnswd on 7/16/2018.
 */
public class DisplayPanel extends JPanel {

    private static int FONT_SIZE = 14;
    private static final Font HUD_FONT = new Font("Arial", Font.BOLD, FONT_SIZE );
    private static final Color HUD_FONT_COLOR = Color.BLACK;
    private static final TextBox.textAlignment HUD_ALIGNMENT = TextBox.textAlignment.LEFT;
    private List <Dot> dots;
    private List <MapObject> mapObjects = new ArrayList<>(  );
    private String gen = "Generation: 0";
    private String best = "Best: N/A";
    private TextBox textBoxHUD;

    public DisplayPanel(List<MapObject> mapObjects){
        setPreferredSize( new Dimension( 600,600 ) );
        this.mapObjects = mapObjects;
    }

    public DisplayPanel( int x, int y, List<MapObject> mapObjects){
        setPreferredSize( new Dimension( x, y ) );
        this.mapObjects = mapObjects;
        textBoxHUD = new TextBox( 0, 0, 300, 300 );
        textBoxHUD.addDisplayString( new TextBox.DisplayString( gen, HUD_FONT, HUD_FONT_COLOR, HUD_ALIGNMENT ) );
        textBoxHUD.addDisplayString( new TextBox.DisplayString( best, HUD_FONT, HUD_FONT_COLOR, HUD_ALIGNMENT ) );
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent( g );


        if( mapObjects != null) {
            for ( int i = 0; i < mapObjects.size(); i++ ) {
                mapObjects.get( i ).draw( g );
            }
        }
        textBoxHUD.draw( g );
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

    public void refreshHUD(){
        textBoxHUD.clearDisplayStrings();
        textBoxHUD.addDisplayString( new TextBox.DisplayString( gen, HUD_FONT, HUD_FONT_COLOR, HUD_ALIGNMENT ) );
        textBoxHUD.addDisplayString( new TextBox.DisplayString( best, HUD_FONT, HUD_FONT_COLOR, HUD_ALIGNMENT ) );
    }

    public void setMapObjects( List<? extends MapObject> o){
        mapObjects = (List<MapObject>)o;
    }

    public void appendObjects(List<? extends MapObject> o){
        mapObjects.addAll(o);
    }
}
