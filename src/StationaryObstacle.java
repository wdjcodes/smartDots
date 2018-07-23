/**
 * Created by johnswd on 7/20/2018.
 */
public class StationaryObstacle extends Obstacle {


    public StationaryObstacle( int x, int y, int w, int h, int xMax, int yMax, DisplayPanel display ) {
        super( x, y, w, h, xMax, yMax, display );
    }

    @Override
    public void update() {
        return;
    }

    @Override
    public void restart() {
        return;
    }
}
