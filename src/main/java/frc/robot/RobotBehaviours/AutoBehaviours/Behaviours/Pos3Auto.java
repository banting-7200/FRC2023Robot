package frc.robot.RobotBehaviours.AutoBehaviours.Behaviours;

import static frc.robot.Core.Utility.*;
import frc.robot.Core.RobotDrive;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Kicker;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Wrist;
import frc.robot.RobotBehaviours.AutoBehaviours.SubBehaviour.*;

public class Pos3Auto implements RobotAutoMaster{

    private final RobotAutoBehaviour[] autoChain;
    private RobotDrive driverInstance;
    private Shoulder shoulderInstance;
    private Wrist wristInstance;
    private Lift liftInstance;
    private int autoPtr;

    public Pos3Auto(RobotDrive drive, Lift lift, Shoulder shoulder, Wrist wrist) {
        driverInstance = drive;
        shoulderInstance = shoulder;
        wristInstance = wrist;
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
            new AutoDrive(driverInstance, 1.3, 0, 0.25),

            //Rotate wrist to dropping position
            new AutoWrist(wristInstance, 0.35, 0.8),

            //Open and close claw
            new AutoClaw(true, 0.5),
            new AutoClaw(false, 0.5),
            new AutoDrive(driverInstance, 2.05, 0.0, -0.6),
        
            //Move to pickup position and backup at the same time
            new AutoParallel(new RobotAutoBehaviour[] {
                new AutoDrive(driverInstance, 1.275, 0.5, 0),
                new AutoLiftPos(lift, CoPilotControls.MACRO_PICKUP.get(), 0.6),
                new AutoShoulderPos(shoulder, CoPilotControls.MACRO_PICKUP.get())
            })
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
