package frc.robot.RobotBehaviours.CoPilotBehaviours.Macros;

import frc.robot.Core.Utility.CoPilotControls;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;

public class Pickup implements RobotAutoMaster {
    private RobotAutoBehaviour[] autoChain;
    private boolean isCompleted;
    private int autoPtr;

    public Pickup(Lift lift, Shoulder shoulder) {
        isCompleted = false;
        autoPtr = 0;

        autoChain = new RobotAutoBehaviour[] {
            new RobotAutoBehaviour() {
                private boolean isCompleted = false;

                @Override
                public void behaviourInit() {}

                @Override
                public void behaviourPeriodic() {
                    boolean liftReachedTarget = lift.moveLiftToPosition(CoPilotControls.MACRO_PICKUP);
                    boolean shoulderReachedTarget = shoulder.moveShoulderToPosition(CoPilotControls.MACRO_PICKUP);
                    isCompleted = liftReachedTarget && shoulderReachedTarget;
                }

                @Override
                public boolean isFinished() {
                    return isCompleted;
                }
            }
        };
    }

    @Override
    public void runAuto() {
        if(autoPtr < autoChain.length) {
            autoChain[autoPtr].behaviourPeriodic();
            if(autoChain[autoPtr].isFinished()) {
                autoPtr++;
            }
        } else {
            isCompleted = true;
        }
    }

    @Override
    public void resetAuto() {
        autoPtr = 0;
        isCompleted = false;
    }

    @Override
    public RobotAutoBehaviour[] getBehaviourChain() {
        return autoChain;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
    
}
