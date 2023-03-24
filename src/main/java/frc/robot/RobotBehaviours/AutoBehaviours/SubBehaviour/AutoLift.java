package frc.robot.RobotBehaviours.AutoBehaviours.SubBehaviour;

import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;

public class AutoLift implements RobotAutoBehaviour{
    private Lift LIFT;
    private final int MOVETIME;
    private final double SPEED;

    private boolean isFinished;
    private int moveTicks;

    public AutoLift(Lift liftInstance, double seconds, double output) {
        MOVETIME = (int)((seconds * 1000) / 20);
        LIFT = liftInstance;
        SPEED = output;

        moveTicks = MOVETIME;
    }

    @Override
    public void behaviourInit() {
        moveTicks = MOVETIME;
        isFinished = false;
    }

    @Override
    public void behaviourPeriodic() {
        boolean canMove = LIFT.moveLift(SPEED, Math.signum(SPEED));
        moveTicks--;
        
        if(moveTicks <= 0 || canMove == false) {
            isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }    
}
