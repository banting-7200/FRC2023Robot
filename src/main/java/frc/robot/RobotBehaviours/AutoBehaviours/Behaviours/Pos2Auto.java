package frc.robot.RobotBehaviours.AutoBehaviours.Behaviours;

import static frc.robot.Core.Utility.*;

import frc.robot.Robot;
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

        //Back up onto ramp
        new RobotAutoBehaviour() {
            private final int backupTicks = 113;
            private int backupLeft = backupTicks;
            @Override
            public void behaviourInit() {
                backupLeft = backupTicks;
            }

            @Override
            public void behaviourPeriodic() {
                if(backupLeft > 0) {
                    driverInstance.DriveRobot(0, 1);
                    backupLeft--;
                    return;
                }

                driverInstance.DriveRobot(0, 0);
            }

            @Override
            public boolean isFinished() {
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
            }

            @Override
            public void behaviourPeriodic() {
                if(ticksLeft > 0) {
                    balanceDriver.BehaviourPeriodic();
                    ticksLeft--;
                    return;
                }

                Robot.BREAK.set(false);
                driverInstance.DriveRobot(0, 0);
            }

            @Override
            public boolean isFinished() {
                return ticksLeft <= 0;
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