import javax.swing.*;
import java.util.List;

/**
 * Created by johnswd on 7/20/2018.
 */
public class GameLoop implements Runnable{

    private static final int MS = 1000;

    private final SmartDots smartDots;
    private final List<MapObject> mapObjects;

    private volatile boolean running;

    private int frameDelayMS;

    public GameLoop( SmartDots smartDots, List<MapObject>mapObjects, int frameRate ){
        this.smartDots = smartDots;
        this.mapObjects = mapObjects;
        this.frameDelayMS = MS/frameRate;
        setRunning( false );
    }

    @Override
    public void run() {
        setRunning( true );
        while(running) {
            updateObjects();
            if(smartDots.isAllDead()){
//                smartDots.breedGeneration();
                try {
                    smartDots.getGenerationBreeder().getLock();
                    smartDots.getGenerationBreeder().signalBreeding();
                    smartDots.getGenerationBreeder().waitForBreeding();
//                    smartDots.getGenerationBreeder().releaseLock();
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                } finally{
                    smartDots.getGenerationBreeder().releaseLock();
                }
            }
            drawMap();
            sleep();
        }
    }

    private void updateObjects(){
        for(int i = 0; i < mapObjects.size(); i++){
            mapObjects.get( i ).update();
        }
    }

    private void drawMap(){
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {
                smartDots.repaintDisplay();
            }
        } );
    }

    private void sleep(){
        try{
            Thread.sleep( frameDelayMS );
        } catch ( InterruptedException e ){

        }
    }

    public synchronized void setRunning(boolean running){
        this.running = running;
    }

//    private void updateObjects(){
//        int generation = 1;
//        int bestStep = -1;
//        while(running) {
//            while ( !smartDots.allDead() ) {
//                try {
//                    TimeUnit.MILLISECONDS.sleep( SLEEP_DELAY );
//                } catch ( InterruptedException e ) {
//                    e.printStackTrace();
//                }
//                //Update Targets
//                for ( int j = 0; j < targets.size(); j++ ) {
//                    targets.get( j ).update();
//                }
//                for ( int j = 0; j < obstacles.size(); j++ ) {
//                    obstacles.get( j ).update();
//                }
//                for ( int j = 0; j < population; j++ ) {
//                    Dot d = dots.get( j );
//                    if ( d.isRunning() && (bestStep < 0 || d.getMoves() < bestStep) ) {
//                        d.update();
//                    } else {
//                        d.kill();
//                    }
//                }
//            }
//
//        }
//    }
}
