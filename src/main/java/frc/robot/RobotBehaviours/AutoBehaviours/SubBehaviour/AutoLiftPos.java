package frc.robot.RobotBehaviours.AutoBehaviours.SubBehaviour;

import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;

public class AutoLiftPos implements RobotAutoBehaviour {
    private final Lift LIFT;
    private final int LIFT_POSBIND;
    private final double SPEED;

    private boolean liftComplete;

    public AutoLiftPos(Lift liftInstance, int positionBind, double movementSpeed) {
        LIFT = liftInstance;
        LIFT_POSBIND = positionBind;
        SPEED = movementSpeed;
    }

    @Override
    public void behaviourInit() {
        liftComplete = false;
    }

    @Override
    public void behaviourPeriodic() {
        if(!liftComplete) {
            liftComplete = LIFT.moveLiftToPosition(LIFT_POSBIND, SPEED);
        }
    }

    @Override
    public boolean isFinished() {
        return liftComplete;
    }
}
