package frc.robot.RobotBehaviours.AutoBehaviours.Behaviours;

import static frc.robot.Core.Utility.*;
import frc.robot.Core.RobotDrive;
import frc.robot.RobotBehaviours.AutoBehaviours.SubBehaviour.*;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Kicker;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Wrist;

public class Pos1Auto implements RobotAutoMaster{
    private RobotDrive driverInstance;
    private Shoulder shoulderInstance;
    private Wrist wristInstance;
    private Lift liftInstance;
    private int autoPtr;

    private final RobotAutoBehaviour[] autoChain;

    public Pos1Auto(RobotDrive drive, Lift lift, Shoulder shoulder, Wrist wristInstance) {
        this.wristInstance = wristInstance;
        shoulderInstance = shoulder;
        driverInstance = drive;
        liftInstance = lift;

        autoChain = new RobotAutoBehaviour[] {
            //Ensure claw is closed
            new AutoClaw(false, 0.1),

            //Max lift for pre-calibration
            new AutoLift(liftInstance, 100, 0.6),
            new AutoWrist(wristInstance, 0.425, 0.8),

            //Move shoulder to level 3 position
            new AutoShoulderPos(shoulderInstance, CoPilotControls.MACRO_LEVEL3.get()),
    
            //Move lift to level 3 position
            new AutoLiftPos(liftInstance, CoPilotControls.MACRO_LEVEL3.get(), 0.6),
    
            //Drive fwd to get game piece above level 3
            new AutoDrive(driverInstance, 1.25, 0, 0.25),

            //Rotate wrist to dropping position
            new AutoWrist(wristInstance, 0.35, 0.8),

            //Open and close claw
            new AutoClaw(true, 0.5),
            new AutoClaw(false, 0.5),
            new AutoDrive(driverInstance, 3.5, 0.0, -0.4)

            /*
            //Move to pickup position and backup at the same time
            new AutoParallel(new RobotAutoBehaviour[] {
                new AutoDrive(drive, 3.5, 0, -0.4)
                new AutoLiftPos(lift, CoPilotControls.MACRO_PICKUP.get(), 0.6),
                new AutoShoulderPos(shoulder, CoPilotControls.MACRO_PICKUP.get())
            })
            */
        };

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
