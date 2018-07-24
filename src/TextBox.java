import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnswd on 7/24/2018.
 */
public class TextBox {

    public static enum textAlignment{
        LEFT, CENTER, RIGHT
    }

    private int x;
    private int y;
    private int width;
    private int height;
    private List<DisplayString> displayStrings;
    private int xCurrentCursor;
    private int yCurrentCursor;

    //These values should only be changed by the user
    //Outside of setters they should be treated as final
    private int LINE_SPACING = 1;
    private int MARGIN_TOP = 3;
    private int MARGIN_LEFT = 3;
    private int MARGIN_RIGHT = 3;
    private int MARGIN_BOTTOM = 3;
    private Color BORDER_COLOR = Color.BLACK;
    private boolean BORDER_VISIBLE = false;

    public TextBox( int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        xCurrentCursor = x + MARGIN_LEFT;
        yCurrentCursor = y + MARGIN_TOP;
        this.width = width;
        this.height = height;
        displayStrings = new ArrayList<>(  );
    }

    public void draw( Graphics g){
        for(int i = 0; i < displayStrings.size(); i++){
            DisplayString tmpString = displayStrings.get(i);
            switch (tmpString.getAlignment()){
                case LEFT:
                    drawLeftAlligned(tmpString, g);
                    break;
                case RIGHT:
                    drawRightAlligned(tmpString, g);
                    break;
                case CENTER:
                    drawCenterAlligned(tmpString, g);
                    break;
            }
            g.setColor( tmpString.getColor() );
        }
        if(BORDER_VISIBLE) {
            g.setColor( BORDER_COLOR );
            g.drawRect( x, y, width, height );
        }
        //Reset the cursor for next draw
        xCurrentCursor = x + MARGIN_LEFT;
        yCurrentCursor = y + MARGIN_TOP;
    }

    private void drawCenterAlligned( DisplayString displayString, Graphics g ) {
        g.setColor( displayString.getColor() );
        g.setFont( displayString.getFont() );
        String string = displayString.toString();
        g.drawString( string, (x + width - MARGIN_LEFT - MARGIN_RIGHT - g.getFontMetrics().stringWidth( string ))/2,
                yCurrentCursor + g.getFontMetrics().getAscent() );
        yCurrentCursor += g.getFontMetrics().getHeight() + LINE_SPACING;
    }

    private void drawRightAlligned( DisplayString displayString, Graphics g ) {
        g.setColor( displayString.getColor() );
        g.setFont( displayString.getFont() );
        String string = displayString.toString();
        g.drawString( string , x + width - MARGIN_RIGHT - g.getFontMetrics().stringWidth( string ),
                yCurrentCursor + g.getFontMetrics().getAscent() );
        yCurrentCursor += g.getFontMetrics().getHeight() + LINE_SPACING;

    }

    private void drawLeftAlligned( DisplayString displayString, Graphics g ) {
        g.setColor( displayString.getColor() );
        g.setFont( displayString.getFont() );
        g.drawString( displayString.toString(), xCurrentCursor, yCurrentCursor + g.getFontMetrics().getAscent());
        yCurrentCursor += g.getFontMetrics().getHeight() + LINE_SPACING;
    }

    public void addDisplayString(DisplayString displayString){
        displayStrings.add(displayString);
    }

    public void clearDisplayStrings(){
        displayStrings.clear();
    }

    public void setBorderColor(){
        BORDER_COLOR = Color.BLACK;
    }

    public void setBorderVisible(){
        BORDER_VISIBLE = true;
    }

    public void setBorderVisible(boolean visible){
        BORDER_VISIBLE = visible;
    }

    /**
     * Created by johnswd on 7/24/2018.
     */
    public static class DisplayString{

        private String string;
        private Font font;
        private Color color;
        private textAlignment alignment;

        public DisplayString(String string){
            this.string = string;
        }

        public DisplayString(String string, Font font, Color color, textAlignment alignment){
            this.string = string;
            this.font = font;
            this.color = color;
            this.alignment = alignment;
        }

        public void setFont(Font font){
            this.font = font;
        }

        public Font getFont(){
            return font;
        }

        public void setColor(Color color){
            this.color = color;
        }

        public Color getColor(){
            return color;
        }

        public void setAlignment(textAlignment alignment){
            this.alignment = alignment;
        }

        public textAlignment getAlignment(){
            return alignment;
        }

        @Override
        public String toString(){
            return string;
        }


    }
}
