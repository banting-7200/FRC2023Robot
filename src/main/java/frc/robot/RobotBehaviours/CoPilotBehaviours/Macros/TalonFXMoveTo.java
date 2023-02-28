package frc.robot.RobotBehaviours.CoPilotBehaviours.Macros;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import frc.robot.Core.Utility.CoPilotControls;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.*;

public class TalonFXMoveTo implements RobotAutoMaster{
    private RobotAutoBehaviour[] autoChain;
    private Shoulder shoulder;
    private Lift lift;

    private int autoPtr;

    public TalonFXMoveTo(Lift lift, Shoulder shoulder, int positionBind) {
        this.shoulder = shoulder;
        this.lift = lift;

        autoChain = new RobotAutoBehaviour[] {
            //Moves motors to positions
            new RobotAutoBehaviour() {
                private boolean isComplete;

                @Override
                public void behaviourInit() {
                    isComplete = false;
                }

                @Override
                public void behaviourPeriodic() {
                    boolean liftFinished = lift.moveLiftToPosition(positionBind, 0.8);
                    boolean shoulderFinished = shoulder.moveShoulderToPosition(positionBind, 0.7);
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
            if(!autoChain[autoPtr].isFinished()) {
                autoChain[autoPtr].behaviourPeriodic();
            } else {
                autoPtr++;
            }
        }
    }

    @Override
    public void resetAuto() {
        shoulder.killSpeed();
        lift.killSpeed();
        autoPtr = 0;

        for(RobotAutoBehaviour behaviour : autoChain) {
            behaviour.behaviourInit();
        }
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
