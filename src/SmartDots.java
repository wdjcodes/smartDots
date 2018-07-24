/**
 * Created by johnswd on 7/16/2018.
 */

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class SmartDots implements Runnable {

    private static final int FRAME_RATE = 45;
    private static int xMax = 600;
    private static int yMax = 600;
    private static int population = 400;

//    private static int xTarget = xMax/2;
//    private static int yTarget = 1;
    private List<Dot> dots;
    private double fitSum = 0;
    private Random randD = new Random();
    private boolean incremental = false;
    private int increment = 5;
    private int incrementInterval = 10;
    private List<Obstacle> obstacles = new ArrayList<>(  );
    private List<Target> targets = new ArrayList<>(  );
    private List<MapObject> mapObjects = new ArrayList<>(  );
    private JFrame frame;
    private DisplayPanel display;
    private int bestMoves = -1;
    private int generation = 1;
    private GameLoop gameLoop;
    private GenerationBreeder generationBreeder;

    private SmartDots(){}

    @Override
    public void run() {
        frame = new JFrame( "SmartDots" );
        frame.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
        display = new DisplayPanel( xMax, yMax, mapObjects);
        frame.add( display );
        frame.pack();
        frame.setVisible( true );
        frame.addWindowListener( new WindowClosingHandler() );

        buildTargets();
        buildObstacles(4);
        createDots();

//        display.appendObjects( targets );
//        display.appendObjects(obstacles);
//        display.appendObjects( dots );
        display.repaint(  );

        gameLoop = new GameLoop( this, mapObjects, FRAME_RATE );
        new Thread( gameLoop ).start();
        generationBreeder = new GenerationBreeder( this );
        new Thread( generationBreeder ).start();


    }

    private void exitProcedure(){
        gameLoop.setRunning(false);
        generationBreeder.setRunning(false);
        frame.dispose();
        System.exit( 0 );
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater( new SmartDots() );
    }

    public boolean isAllDead(){
        for(int i = 0; i < mapObjects.size(); i++){
            MapObject object = mapObjects.get(i);
            if(object.getID() != mObjectID.AIplayer){
                continue;
            }

            Dot tempDot = (Dot)object;
            if(bestMoves > 0 && tempDot.getMoves() >= bestMoves){
                tempDot.kill();
            }
            if(tempDot.isRunning()){
                return false;
            }
        }
        return true;
    }

    public void breedGeneration(){
        sumFitness();
        Dot best = getBestDot();
        if ( best.reachedTarget() ) {
            bestMoves = best.getMoves();
        }
        generation++;
        display.setGen( generation );
        if ( best.reachedTarget() ) {
            display.setBest( best.getMoves() );
            System.out.println( "Gen: " + generation + " Moves: " + best.getMoves() );
        } else {
            display.setBest( best.getFitness() );
            System.out.println( "Gen: " + generation + " Best; " + best.getFitness() );
        }
        List<Dot> newDots = new ArrayList<>();
        Dot bestChild = best.baby();
        bestChild.setBest();
        for ( int i = 1; i < population; i++ ) {
            Dot baby = getParent().baby().mutate();
            newDots.add( baby );
        }
        newDots.add( bestChild );
        dots = newDots;
        clearObjectTypeFromMapObjects( Dot.class );
        mapObjects.addAll( newDots );
//        display.appendObjects( dots );
        for ( int j = 0; j < targets.size(); j++ ) {
            targets.get( j ).restart();
        }
        for ( int j = 0; j < obstacles.size(); j++ ) {
            obstacles.get( j ).restart();
        }

        display.refreshHUD();
        display.repaint();
    }

    private boolean allDead(){
        for(int i = 0; i < population; i++){
            if(dots.get(i).isRunning()){
                return false;
            }
        }
        return true;
    }

    private void sumFitness(){
        fitSum = 0;
        for(int i = 0; i < population; i++){
            fitSum += dots.get(i).getFitness();
        }
    }

    private Dot getParent(){
        double rand = fitSum*randD.nextDouble();
        double runningFit = 0;
        for(int i = 0; i < population; i++){
            runningFit += dots.get(i).getFitness();
            if(runningFit >= rand){
                Dot d = dots.get( i );
                return d;
            }
        }
        //Should never get here
        return null;
    }

    private int findBest(){
        double bestWeight = 0;
        int best = 0;
        for(int i = 0; i < population; i++){
            if(dots.get(i).getFitness() > bestWeight){
                bestWeight = dots.get( i ).getFitness();
                best = i;
            }
        }
        return best;
    }

    private Dot getBestDot(){
        double bestScore = 0;
        int best =  0;
        Dot bestDot = null;
        for(int i = 0; i < mapObjects.size(); i++){
            if(mapObjects.get( i ).getID() == mObjectID.AIplayer) {
                Dot tempDot = (Dot) mapObjects.get( i );
                if ( tempDot.getFitness() > bestScore ) {
                    bestDot = tempDot;
                    bestScore = bestDot.getFitness();
                }
            }
        }

        return bestDot;
    }

    private void buildObstacles(){
//        obstacles.add(new Obstacle( 150, 250, 300, 100, xMax, yMax, display ));
        obstacles.add(new MovingObstacle( 150, 250, 300, 100, 5, 0, xMax, yMax, display ));
        mapObjects.addAll( obstacles );
    }

    private void buildObstacles(int level){
        switch (level){
            case 1:
                obstacles.add( new StationaryObstacle( 150, 250, 300, 100, xMax, yMax, display ) );
                break;
            case 2:
                obstacles.add(new MovingObstacle( 150, 250, 300, 100, 5, 0, xMax, yMax, display ));
                break;
            case 3:
                obstacles.add(new StationaryObstacle( 0, 200, 450, 50, xMax, yMax, display ));
                obstacles.add( new StationaryObstacle( 150,  300, 450, 50, xMax, yMax, display) );
                break;
            case 4:
                obstacles.add(new MovingObstacle( 0, 200, 450, 50, 5, 0, xMax, yMax, display ));
                obstacles.add( new MovingObstacle( 150,  300, 450, 50, 5, Math.PI, xMax, yMax, display) );
                break;
            default:
                buildObstacles();
                break;
        }
        mapObjects.addAll( obstacles );
    }

    private void createDots(){
        dots = new ArrayList<>();
        for(int i = 0; i < population; i++){
            Dot dot;
            if(incremental){
                dot = new Dot( xMax, yMax, increment, obstacles, targets, display );
            } else {
                dot = new Dot( xMax, yMax, 400, obstacles, targets, display );
            }
            dots.add(dot);
            mapObjects.add( dot );
        }
    }

    private void buildTargets() {
        Target t = new Target( xMax, yMax, display );
        targets.add( t );
        mapObjects.add( t );
    }

    private void clearObjectTypeFromMapObjects(Class removeClass){
        ListIterator<MapObject> iterator = mapObjects.listIterator();
        while(iterator.hasNext()){
            if(iterator.next().getClass() == removeClass){
                iterator.remove();
            }
        }
    }

    private class WindowClosingHandler extends WindowAdapter{
        @Override
        public void windowClosing( WindowEvent eevent ) {
            exitProcedure();
        }
    }

    public void repaintDisplay(){
        display.repaint(  );
    }

    public GenerationBreeder getGenerationBreeder(){
        return generationBreeder;
    }

}
