package frc.robot.RobotBehaviours.AutoBehaviours.Behaviours;

import static frc.robot.Core.Utility.*;
import frc.robot.Core.RobotDrive;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Kicker;

public class Pos1Auto implements RobotAutoMaster{
    private final RobotAutoBehaviour[] autoChain = new RobotAutoBehaviour[] {
        //Kick ball
        new RobotAutoBehaviour() {
            private boolean isCompleted = false;
            private int delayCount = 25;

            @Override
            public void behaviourInit() {
                isCompleted = false;
                delayCount = 25;
            }

            @Override
            public void behaviourPeriodic() {
                kickerInstance.setKickerState(true);

                if(delayCount > 0) {
                    delayCount--;
                    return;
                }

                kickerInstance.setKickerState(false);
                isCompleted = true;
            }

            @Override
            public boolean isFinished() {
                return isCompleted;
            }
        },

        //Back up into community zone
        new RobotAutoBehaviour() {
            private final int BACKUP_TICKS = 150;
            private int currentTicks = 0;

            @Override
            public void behaviourInit() {
                currentTicks = BACKUP_TICKS;
            }

            @Override
            public void behaviourPeriodic() {
                if(currentTicks <= 0) {
                    driverInstance.DriveRobot(0, 0);
                    return;
                }

                driverInstance.DriveRobot(0.0, 0.75);
                currentTicks--;
            }

            @Override
            public boolean isFinished() {
                return currentTicks <= 0;
            }
        }
    };

    private RobotDrive driverInstance;
    private Kicker kickerInstance;
    private int autoPtr;

    public Pos1Auto(RobotDrive drive) {
        driverInstance = drive;
        kickerInstance = INSTANCE_KICKER;
        autoPtr = 0;
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
