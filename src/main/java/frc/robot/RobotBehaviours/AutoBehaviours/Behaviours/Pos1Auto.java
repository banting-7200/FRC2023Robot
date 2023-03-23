package frc.robot.RobotBehaviours.AutoBehaviours.Behaviours;

import static frc.robot.Core.Utility.*;
import frc.robot.Core.RobotDrive;
import frc.robot.RobotBehaviours.AutoBehaviours.SubBehaviour.*;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Kicker;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;

public class Pos1Auto implements RobotAutoMaster{
    private RobotDrive driverInstance;
    private Shoulder shoulderInstance;
    private Lift liftInstance;
    private int autoPtr;

    //TODO: Test each autonomous section individually when given the chance
    private final RobotAutoBehaviour[] autoChain = new RobotAutoBehaviour[] {
        new AutoClaw(false, 0.1),
        new AutoDrive(driverInstance, 0.5, 0, -0.6),
        new AutoLift(liftInstance, 100, 0.6), //Move up until maxed. This allows shoulder movement AND zero's lift
        new AutoShoulderPos(shoulderInstance, CoPilotControls.MACRO_LEVEL3.get()),
        new AutoLiftPos(liftInstance, CoPilotControls.MACRO_LEVEL3.get(), 0.6),
        new AutoClaw(true, 1),
        new AutoClaw(false, 0.5)
    };

    public Pos1Auto(RobotDrive drive, Lift lift, Shoulder shoulder) {
        shoulderInstance = shoulder;
        driverInstance = drive;
        liftInstance = lift;

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
