package frc.robot.RobotBehaviours.CoPilotBehaviours.Macros;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import frc.robot.Core.Utility.CoPilotControls;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.*;

public class TalonFXMoveTo implements RobotAutoMaster{
    private RobotAutoBehaviour[] autoChain;
    private int autoPtr;

    public TalonFXMoveTo(Lift lift, Shoulder shoulder, int liftTargetBind, int shoulderTargetBind) {
        autoChain = new RobotAutoBehaviour[] {
            //Moves motors to positions
            new RobotAutoBehaviour() {
                private boolean isComplete;

                @Override
                public void behaviourInit() {}

                @Override
                public void behaviourPeriodic() {
                    boolean liftFinished = lift.moveLiftToPosition(liftTargetBind, 0.8);
                    boolean shoulderFinished = shoulder.moveShoulderToPosition(shoulderTargetBind, 0.7);
                    isComplete = liftFinished && shoulderFinished;
                }

                @Override
                public boolean isFinished() {
                    return isComplete;
                }
            }
        };

        for(RobotAutoBehaviour behaviour : autoChain) {
            behaviour.behaviourInit();
        }

        autoPtr = 0;
    }


    @Override
    public void runAuto() {
        if(autoPtr < autoChain.length) {
            autoChain[autoPtr].behaviourPeriodic();
            if(autoChain[autoPtr].isFinished()) {
                autoPtr++;
            }
        }
    }

    @Override
    public void resetAuto() {
        autoPtr = 0;
    }

    @Override
    public RobotAutoBehaviour[] getBehaviourChain() {
        return autoChain;
    }

    @Override
    public boolean isCompleted() {
        return autoPtr >= autoChain.length;
    }
}
