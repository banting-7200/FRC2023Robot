package frc.robot.RobotBehaviours.CoPilotBehaviours.AutoBehaviours;

import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.PilotBehaviours.DefaultModes.Shoulder;

/*
 * Author: Lucas Soliman
 * Date Created: 
 */
public class ArmToStartPos implements RobotAutoMaster{
    private RobotAutoBehaviour[] autoChain;

    private boolean isCompleted;
    private int autoPtr;

    public ArmToStartPos(Lift lift, Shoulder shoulder) {
        autoPtr = 0;
        autoChain = new RobotAutoBehaviour[] {
            //Move Lift to highest Position Behaviour
            new RobotAutoBehaviour() {
                @Override
                public void behaviourInit() {}

                @Override
                public void behaviourPeriodic() {}

                @Override
                public boolean isFinished() {
                    return lift.setInput(-0.8) == false;
                }
            },

            // Move Shoulder to Pickup Position Behaviour
            // TODO: Add Implementation
            new RobotAutoBehaviour() {
                @Override
                public void behaviourInit() {
                }

                @Override
                public void behaviourPeriodic() {
                }

                @Override
                public boolean isFinished() {
                    return true;
                }
            }
        };
    }

    @Override
    public void runAuto() {
        if(isCompleted) {
            System.out.println("ArmToStart is Completed!");
            return;
        }

        RobotAutoBehaviour currBehaviour = autoChain[autoPtr];
        currBehaviour.behaviourPeriodic();

        if(currBehaviour.isFinished()) {
            autoPtr++;

            if(autoPtr == autoChain.length) {
                isCompleted = true;
            }
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
