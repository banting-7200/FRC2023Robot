package frc.robot.RobotBehaviours.CoPilotBehaviours.Macros;

import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;

public class Unpack implements RobotAutoMaster{

    private int autoPtr;
    private RobotAutoBehaviour[] autoChain = new RobotAutoBehaviour[] {

        // Max out lift
        new RobotAutoBehaviour() {
            private boolean liftComplete;

            @Override
            public void behaviourInit() {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void behaviourPeriodic() {
                if(!liftComplete) {
                    liftInstance.moveLiftToPosition(-1, 1.0);
                }
                
            }

            @Override
            public boolean isFinished() {
                return liftComplete;
            }
        },

        // Move shoulder to 0
        new RobotAutoBehaviour() {
            private boolean shoulderFinished;

            @Override
            public void behaviourInit() {}

            @Override
            public void behaviourPeriodic() {
                if(!shoulderFinished) {
                    shoulderFinished = shoulderInstance.moveShoulderToPosition(-1, 0.9);
                }
            }

            @Override
            public boolean isFinished() {
                return shoulderFinished;
            }
        },
    };

    private Lift liftInstance;
    private Shoulder shoulderInstance;

    public Unpack(Lift lift, Shoulder shoulder) {
        liftInstance = lift;
        shoulderInstance = shoulder;
        autoPtr = 0;
    }
    
    @Override
    public void runAuto() {
        if(autoPtr < autoChain.length) {
            if(!autoChain[autoPtr].isFinished()) {
                autoChain[autoPtr].behaviourPeriodic();
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
