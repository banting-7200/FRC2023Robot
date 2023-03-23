package frc.robot.RobotBehaviours.AutoBehaviours.SubBehaviour;

import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;

public class AutoLift implements RobotAutoBehaviour{
    private final Lift LIFT;
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
        double out = LIFT.canMoveLift(-SPEED);
        LIFT.moveLift(out, Math.signum(out));
        moveTicks--;
        
        if(out == 0 || moveTicks <= 0) {
            isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }    
}
