package frc.robot.RobotBehaviours.AutoBehaviours.SubBehaviour;

import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;

public class AutoShoulderPos implements RobotAutoBehaviour{
    private final int POSBIND;

    private Shoulder shoulderInstance;
    private boolean isFinished;

    public AutoShoulderPos(Shoulder shoulder, int positionBind) {
        shoulderInstance = shoulder;
        POSBIND = positionBind;
    }

    @Override
    public void behaviourInit() {
        isFinished = false;
    }

    @Override
    public void behaviourPeriodic() {
        if(!isFinished) {
            isFinished = shoulderInstance.moveShoulderToPosition(POSBIND, 1.0);
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
    
}
