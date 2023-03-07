package frc.robot.RobotBehaviours.AutoBehaviours.Behaviours;

import static frc.robot.Core.Utility.*;

import frc.robot.Robot;
import frc.robot.Core.RobotDrive;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Kicker;
import frc.robot.RobotBehaviours.PilotBehaviours.BalanceDrive;


//TODO: TEST AND TWEAK VALUES
// Make kicker side face charge station
public class Pos2Auto implements RobotAutoMaster{
    private final RobotAutoBehaviour[] autoChain = new RobotAutoBehaviour[] {
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
<<<<<<< HEAD
        //Kick ball
        new RobotAutoBehaviour() {
            private int tickCounts = 113;
            private int tickTimes = 0;
            private double rotSpeed = 0.6;
            
            @Override
            public void behaviourInit() {
                tickCounts = 113;
                tickTimes = 0;
=======

        //Back up onto ramp
        new RobotAutoBehaviour() {
            private final int backupTicks = 113;
            private int backupLeft = backupTicks;
            @Override
            public void behaviourInit() {
                backupLeft = backupTicks;
>>>>>>> f45c22609ca8d948d7a96b88068d4a4551526e8f
            }

            @Override
            public void behaviourPeriodic() {
<<<<<<< HEAD
                if(tickTimes % 5 == 0) {
                    rotSpeed *= -1;
                }

                if(tickCounts > 0) {
                    driverInstance.DriveRobot(rotSpeed, 1);
                    tickCounts--;
=======
                if(backupLeft > 0) {
                    driverInstance.DriveRobot(0, 1);
                    backupLeft--;
>>>>>>> f45c22609ca8d948d7a96b88068d4a4551526e8f
                    return;
                }

                driverInstance.DriveRobot(0, 0);
            }

            @Override
            public boolean isFinished() {
<<<<<<< HEAD
                return tickCounts <= 0;
            }
        },

        //Once angle on gyro is not balanced, run balanceDrive until balanced
        new RobotAutoBehaviour() {
            private int balanceTime = 350;
            @Override
            public void behaviourInit() {
                balanceTime = 350;
=======
                return backupLeft <= 0;
            }

        },

        //Go onto charge station
        new RobotAutoBehaviour() {
            private final int autoTicks = 100;
            private int ticksLeft = autoTicks;

            @Override
            public void behaviourInit() {
                ticksLeft = autoTicks;
>>>>>>> f45c22609ca8d948d7a96b88068d4a4551526e8f
            }

            @Override
            public void behaviourPeriodic() {
<<<<<<< HEAD
                if(balanceTime > 0) {
                    balanceDriver.BehaviourPeriodic();
                    balanceTime--;
                    return;
                }

                driverInstance.DriveRobot(0, 0);
                Robot.BREAK.set(false);
=======
                if(ticksLeft > 0) {
                    balanceDriver.BehaviourPeriodic();
                    ticksLeft--;
                    return;
                }

                Robot.BREAK.set(false);
                driverInstance.DriveRobot(0, 0);
>>>>>>> f45c22609ca8d948d7a96b88068d4a4551526e8f
            }

            @Override
            public boolean isFinished() {
<<<<<<< HEAD
                return balanceTime <= 0;
=======
                return ticksLeft <= 0;
>>>>>>> f45c22609ca8d948d7a96b88068d4a4551526e8f
            }
        }
    };

    private BalanceDrive balanceDriver;
    private RobotDrive driverInstance;
    private Kicker kickerInstance;
    private int autoPtr;

    public Pos2Auto(RobotDrive drive) {
        driverInstance = drive;
        balanceDriver = new BalanceDrive(drive);
        balanceDriver.BehaviourInit(null);

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