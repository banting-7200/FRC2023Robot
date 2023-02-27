package frc.robot.RobotBehaviours.CoPilotBehaviours.Macros;

import frc.robot.Core.Utility.*;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;

public class Level1 implements RobotAutoMaster {
    private RobotAutoBehaviour[] autoChain;
    private boolean isCompleted;
    private int autoPtr;

    public Level1(Lift lift, Shoulder shoulder) {
        autoChain = new RobotAutoBehaviour[] {
            new RobotAutoBehaviour() {
                private boolean isCompleted;

                @Override
                public void behaviourInit() {}

                @Override
                public void behaviourPeriodic() {
                    boolean liftReachedTarget = lift.moveLiftToPosition(CoPilotControls.MACRO_LEVEL1);
                    boolean shoulderReachedTarget = shoulder.moveShoulderToPosition(CoPilotControls.MACRO_LEVEL1);
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
}
