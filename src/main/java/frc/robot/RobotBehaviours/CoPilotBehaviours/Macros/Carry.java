package frc.robot.RobotBehaviours.CoPilotBehaviours.Macros;

import frc.robot.Core.Utility.CoPilotControls;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;

public class Carry implements RobotAutoMaster{
    private RobotAutoBehaviour[] autoChain;
    private boolean isCompleted;
    private int autoPtr;

    public Carry(Lift lift, Shoulder shoulder) {
        autoChain = new RobotAutoBehaviour[] {
            new RobotAutoBehaviour() {
                private boolean isCompleted;

                @Override
                public void behaviourInit() {}

                @Override
                public void behaviourPeriodic() {
                    boolean liftReachedTarget = lift.moveLiftToPosition(CoPilotControls.MACRO_CARRY);
                    boolean shoulderReachedTarget = shoulder.moveShoulderToPosition(CoPilotControls.MACRO_CARRY);
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
        isCompleted = false;
        autoPtr = 0;
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
