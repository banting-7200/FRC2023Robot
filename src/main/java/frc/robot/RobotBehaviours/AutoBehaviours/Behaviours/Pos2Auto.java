package frc.robot.RobotBehaviours.AutoBehaviours.Behaviours;

import static frc.robot.Core.Utility.*;
import frc.robot.Core.RobotDrive;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Kicker;
import frc.robot.RobotBehaviours.PilotBehaviours.BalanceDrive;


//TODO: TEST AND TWEAK VALUES
public class Pos2Auto implements RobotAutoMaster{
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
            private final int BACKUP_TICKS = 100;
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

                driverInstance.DriveRobot(0.0, 0.8);
                currentTicks--;
            }

            @Override
            public boolean isFinished() {
                return currentTicks <= 0;
            }
        },

        //Go onto charge station
        new RobotAutoBehaviour() {
            @Override
            public void behaviourInit() {}

            @Override
            public void behaviourPeriodic() {
                if(balanceDriver.isBalanced) {
                    driverInstance.DriveRobot(0, 1);
                }
            }

            @Override
            public boolean isFinished() {
                return balanceDriver.isBalanced == false;
            }
        },

        //Once angle on gyro is not balanced, run balanceDrive until balanced
        new RobotAutoBehaviour() {
            @Override
            public void behaviourInit() {}

            @Override
            public void behaviourPeriodic() {
                if(!balanceDriver.isBalanced) {
                    balanceDriver.BehaviourPeriodic();
                }

                if(balanceDriver.isBalanced) {
                    driverInstance.DriveRobot(0, 0);
                }
            }

            @Override
            public boolean isFinished() {
                return balanceDriver.isBalanced;
            }
        }
    };

    private BalanceDrive balanceDriver;
    private Kicker kickerInstance;
    private RobotDrive driverInstance;
    private int autoPtr;

    public Pos2Auto(RobotDrive drive) {
        driverInstance = drive;
        balanceDriver = new BalanceDrive(drive);
        kickerInstance = INSTANCE_KICKER;
    }

    @Override
    public void runAuto() {
        System.out.println("AUTO: POSITION-2 MACRO RUNNING");

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