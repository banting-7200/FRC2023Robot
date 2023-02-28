package frc.robot.RobotBehaviours.CoPilotBehaviours.Macros;

import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;

/*
 * Author: Lucas Soliman
 * Date Created: February 28, 2023
 * 
 * This is a testing macro
 */
public class TempMacro implements RobotAutoMaster{

    private RobotAutoBehaviour[] autoChain;
    private int autoPtr;

    public TempMacro() {
        autoChain = new RobotAutoBehaviour[] {
            new RobotAutoBehaviour() {
                private int timesRun = 0;

                @Override
                public void behaviourInit() {
                    timesRun = 0;
                }

                @Override
                public void behaviourPeriodic() {
                    System.out.println("Ran: " + timesRun);
                    timesRun++;
                }

                @Override
                public boolean isFinished() {
                    return timesRun >= 10;
                }
            }
        };
    }

    @Override
    public void runAuto() {
        if(autoPtr < autoChain.length) {
            RobotAutoBehaviour currBehaviour = autoChain[autoPtr];
            if(!currBehaviour.isFinished()) {
                currBehaviour.behaviourPeriodic();
            } else {
                autoPtr++;
            }
        }
    }

    @Override
    public void resetAuto() {
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
