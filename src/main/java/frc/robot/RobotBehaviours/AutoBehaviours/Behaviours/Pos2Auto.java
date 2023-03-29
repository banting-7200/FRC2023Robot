package frc.robot.RobotBehaviours.AutoBehaviours.Behaviours;

import static frc.robot.Core.Utility.*;

import frc.robot.Robot;
import frc.robot.Core.RobotDrive;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Kicker;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.PilotBehaviours.BalanceDrive;
import frc.robot.RobotBehaviours.AutoBehaviours.SubBehaviour.*;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Wrist;

//TODO: TEST AND TWEAK VALUES
// Make kicker side face charge station
public class Pos2Auto implements RobotAutoMaster{
    private final RobotAutoBehaviour[] autoChain;
    private RobotDrive driverInstance;
    private int autoPtr;

    public Pos2Auto(RobotDrive drive, Lift liftInstance, Shoulder shoulderInstance, Wrist wristInstance) {
        driverInstance = drive;

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
            new AutoDrive(driverInstance, 1.05, 0, 0.25),

            //Rotate wrist to dropping position
            new AutoWrist(wristInstance, 0.35, 0.8),

            //Ensure claw is closed
            new AutoClaw(true, 0.1)
        };
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