import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by johnswd on 7/16/2018.
 */
public class Dot extends MapObject{

    private static Color COLOR = Color.RED;
    private static int DIAM = 6;
    private static Color BEST_COLOR = Color.GREEN;
    private static int BEST_DIAM = 8;
    private static int MAX_VELOCITY = 5;


    private boolean best;
    private Brain brain;
    private boolean running;
    private boolean success;
    private double fitness;
    private int moves;
    private List<Obstacle> obstacles;
    private List<Target> targets;
    private int xStart, yStart;

    public Dot(int xMax, int yMax, int instructions, List<Obstacle> obs, List<Target> targ, DisplayPanel d){

        super(new Circle2D( new Point2D.Double( xMax/2, yMax - 15 ), DIAM/2 ), mObjectID.AIplayer,
                new Rectangle( 0, 0, xMax, yMax ), d );
        best = false;
        brain = new Brain( instructions );
        running = true;
        success = false;
        fitness = 0;
        moves = 0;
        obstacles = obs;
        targets = targ;
        xStart = xMax/2;
        yStart = yMax - 15;
    }

    public Dot(int x, int y, int xMax, int yMax, int instructions, List<Obstacle>obs, List<Target>targ, DisplayPanel d){
        super(new Circle2D( new Point2D.Double( x, y ), DIAM/2), mObjectID.AIplayer,
                new Rectangle( 0, 0, xMax, yMax ),  d);
//        xDraw = (int)location.getX() - DIAM /2;
//        yDraw = (int)location.getY() - DIAM /2;
        best = false;
        brain = new Brain( instructions );
        running = true;
        success = false;
        fitness = 0;
        moves = 0;
        obstacles = obs;
        targets = targ;
        xStart = x;
        yStart = y;
    }

    public Dot(int x, int y, Rectangle mapBounds, int instructions, List<Obstacle>obs, List<Target>targ, DisplayPanel d){
        super(new Circle2D( new Point2D.Double( x, y ), DIAM/2), mObjectID.AIplayer, mapBounds, d);
        best = false;
        brain = new Brain( instructions );
        running = true;
        success = false;
        fitness = 0;
        moves = 0;
        obstacles = obs;
        targets = targ;
        xStart = x;
        yStart = y;
    }

    public void draw(Graphics g){
        if (best){
            g.setColor( BEST_COLOR );
            g.fillOval( (int) getObjectShape().getCenterX() - BEST_DIAM/2,
                    (int) getObjectShape().getCenterY() - BEST_DIAM/2, BEST_DIAM, BEST_DIAM );
            g.setColor( BORDER_COLOR );
            g.drawOval( (int) getObjectShape().getCenterX() - BEST_DIAM/2,
                    (int) getObjectShape().getCenterY() - BEST_DIAM/2, BEST_DIAM, BEST_DIAM );
        } else {
            g.setColor( COLOR );
            g.fillOval( (int) getObjectShape().getX(), (int) getObjectShape().getY(), DIAM, DIAM );
            g.setColor( BORDER_COLOR );
            g.drawOval( (int) getObjectShape().getX(), (int) getObjectShape().getY(), DIAM, DIAM );
        }
    }

    private void move(){
        if(!brain.hasMove()){
            running = false;
            return;
        }
        moves++;
        if(best) {
//            display.repaint((int)getObjectShape().getCenterX() - BEST_DIAM/2, (int)getObjectShape().getCenterY() - BEST_DIAM/2,
//                    BEST_DIAM, BEST_DIAM);
        } else{
//            display.repaint( getObjectBounds() );
        }

        accel = brain.getMove();
        velocity.add( accel );
        velocity.capMagnitude( MAX_VELOCITY );

        getObjectShape().move(velocity.actOnPoint( getObjectShape().getCenter() ));

        if(best) {
//            display.repaint((int)getObjectShape().getCenterX() - BEST_DIAM/2, (int)getObjectShape().getCenterY() - BEST_DIAM/2,
//                    BEST_DIAM, BEST_DIAM);
        } else{
//            display.repaint( getObjectBounds() );
        }
    }

    public void update(){
        if(!running){
            return;
        }

        move();

        for(int i = 0; i < targets.size(); i++){
            if(objectCollide( targets.get(i) )){
                running = false;
                success = true;
                calcFitness();
                return;
            }
        }

        for(int i = 0; i < obstacles.size(); i++){
            if(objectCollide( obstacles.get(i) )){
                running = false;
                calcFitness();
                return;
            }
        }

        if(mapCollide()){
            running = false;
            calcFitness();
            return;
        } else if(!brain.hasMove()){
            running = false;
            calcFitness();
        }

//        if(Math.sqrt((Math.pow( x - xTarget,2 ) + Math.pow( y - yTarget, 2 ))) < (DisplayPanel.TARGET_DIAM/2 + DIAM /2)){
//            running = false;
//            success = true;
//            calcFitness();
//        } else if(x >= xMax - DIAM /2 || x <= DIAM /2 || y >= yMax - DIAM /2 || y <= DIAM /2){
//            running = false;
//            calcFitness();
//        } else if(!brain.hasMove()){
//            running = false;
//            calcFitness();
//        } else{
//            for( int i = 0; i < obstacles.size(); i++){
//                Obstacle o = obstacles.get(i);
//                if(x >= (o.getLeft() - DIAM /2) && x <= (o.getRight() + DIAM /2)
//                        && y >= (o.getTop() - DIAM /2) && (y <= o.getBottom() + DIAM /2)){
//                    running = false;
//                    calcFitness();
//                }
//            }
//        }
    }

    @Override
    public void restart() {
        return;
    }

    private boolean objectCollide(MapObject m){
        return getObjectShape().collides(m.getObjectShape());
    }

    private boolean mapCollide(){
        if( getObjectShape().getCenterX() < mapBounds.getX() + DIAM/2 ){
            getObjectShape().move( mapBounds.getX() + DIAM / 2, getObjectShape().getCenterY() );
        } else if( getObjectShape().getCenterX() > mapBounds.getX() + mapBounds.getWidth() - 1 - DIAM/2){
            getObjectShape().move( mapBounds.getX() + mapBounds.getWidth() - 1 - DIAM/2, getObjectShape().getCenterY() );
        } else if( getObjectShape().getCenterY() < mapBounds.getY() + DIAM/2){
            getObjectShape().move( getObjectShape().getCenterX(), mapBounds.getY() + DIAM/2 );
        } else if( getObjectShape().getCenterY() > mapBounds.getY() + mapBounds.getHeight() - 1 - DIAM/2){
            getObjectShape().move( getObjectShape().getCenterX(), mapBounds.getY() + mapBounds.getHeight() - 1 - DIAM/2 );
        } else{
            return false;
        }
        return true;
    }

    public boolean isRunning(){
        return running;
    }

    public double getFitness(){
        return fitness;
    }

    private void calcFitness(){
        if(success){
//            fitness = 1000 + 1000.0/moves;
            fitness = 1 + 1.0/Math.pow( moves, 2 );
        } else {
//            fitness = 1000.0/(Math.pow( x - xTarget,2 ) + Math.pow( y - yTarget, 2 ));
//            fitness = 1.0/(Math.pow( x - xTarget,2 ) + Math.pow( y - yTarget, 2 ));
            for(int i = 0; i < targets.size(); i++){
                Rectangle tBound = targets.get(i).getObjectBounds();
                fitness = 1.0/ getObjectShape().getCenter().distance( tBound.getCenterX(), tBound.getCenterY() );
            }
        }
    }

    public Dot baby(){
        Dot baby = new Dot(xStart, yStart, mapBounds, 0, obstacles, targets,  display);
        baby.setBrain( brain.clone());
        return baby;
    }

    public Dot mutate(){
        this.brain.mutate();
        return this;
    }

    protected void setBrain(Brain brain){
        this.brain = brain;
    }

    public void setBest(){
        best = true;
    }

    public int getMoves(){
        return moves;
    }

    public boolean reachedTarget(){
        return success;
    }

    public void kill(){
        running = false;
        calcFitness();
    }

    public void addMoves(int m){
        brain.addMoves(m);
    }

    public int getRadius(){
        return DIAM/2;
    }

    public Point2D.Double getLocation(){
        return getObjectShape().getCenter();
    }

    @Override
    public Circle2D getObjectShape(){
        return (Circle2D)objectShape;
    }

//    @Override
//    public Rectangle getDrawingBounds() {
//        if(best){
//            return new Rectangle( (int)getObjectShape().getX(), (int)getObjectShape().getY(), BEST_DIAM/2, BEST_DIAM/2 );
//        }
//        return getObjectBounds();
//    }


}
