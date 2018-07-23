import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by johnswd on 7/16/2018.
 */
public class Brain{

    private static double MUTATION_RATE = 0.01;

    private List<MotionVector> moves = new ArrayList<>(  );
    private Random rand = new Random();
    private int move;

    public Brain(int instructions){
        for(int i = 0; i < instructions; i++){
            double rad = rand.nextDouble()*2*Math.PI;
            moves.add(new MotionVector( rad ));
        }
        move = 0;
    }

    public MotionVector getMove(){
        return moves.get(move++);
    }

    public boolean hasMove(){
        if(move < moves.size()){
            return true;
        }
        return false;
    }

    public Brain clone(){
        Brain b = new Brain(0);
        for(int i = 0; i < moves.size(); i++){
            b.moves.add(moves.get(i));
        }
        b.move = 0;
        return b;
    }

    public Brain mutate(){
        for (int i = 0; i < moves.size(); i++){
            double r = rand.nextDouble();
            if(r < MUTATION_RATE){
                double rad = rand.nextDouble()*2*Math.PI;
//                double newRad = .9*moves.get(i).getAngle() + .1*rad;
                double newRad = rad;
                if(newRad > 2*Math.PI){
                    newRad -= 2*Math.PI;
                }
                moves.set(i, new MotionVector( newRad ));
            }
        }
        return this;
    }

    public void addMoves(int m){
        for(int i = 0; i < m; i++){
            double rad = rand.nextDouble()*2*Math.PI;
            moves.add(new MotionVector( rad ));
        }
    }
}
