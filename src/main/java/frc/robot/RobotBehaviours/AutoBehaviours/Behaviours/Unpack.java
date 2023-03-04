package frc.robot.RobotBehaviours.AutoBehaviours.Behaviours;

import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;

public class Unpack implements RobotAutoMaster{
    private final RobotAutoBehaviour[] autoChain = new RobotAutoBehaviour[] 
    {
        //Max out the lift (Doubles as calibration)
        new RobotAutoBehaviour() {
            private boolean liftFinished;

            @Override
            public void behaviourInit() {}

            @Override
            public void behaviourPeriodic() {
                if(!liftFinished) {
                    liftFinished = lift.moveLift(0.4, 1) == false;
                }
            }

            @Override
            public boolean isFinished() {
                return liftFinished;
            }
        },

        //Move shoulder to unpacked position
        new RobotAutoBehaviour() {
            private boolean shoulderFinished;

            @Override
            public void behaviourInit() {}

            @Override
            public void behaviourPeriodic() {
                if(!shoulderFinished) {
                    shoulder.moveShoulderToPosition(-1, 0.5);
                }
            }

            @Override
            public boolean isFinished() {
                return shoulderFinished;
            }
        }
    };

    private Lift lift;
    private Shoulder shoulder;
    private int autoPtr;

    public Unpack(Lift lift, Shoulder shoulder) {
        this.lift = lift;
        this.shoulder = shoulder;
        autoPtr = 0;
    }

    @Override
    public void runAuto() {
        if(autoPtr < autoChain.length) {
            RobotAutoBehaviour b = autoChain[autoPtr];
            if(!b.isFinished()) {
                b.behaviourPeriodic();
                return;
            }

            autoPtr++;
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
