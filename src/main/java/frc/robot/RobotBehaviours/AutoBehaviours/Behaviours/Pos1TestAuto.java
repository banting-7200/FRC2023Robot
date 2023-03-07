package frc.robot.RobotBehaviours.AutoBehaviours.Behaviours;

import frc.robot.Core.Utility.CoPilotControls;
import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;

public class Pos1TestAuto implements RobotAutoMaster{

    private RobotAutoBehaviour[] autoChain = new RobotAutoBehaviour[] {
        // Max lift to high:
        new RobotAutoBehaviour() {
            private boolean isFinished;

            @Override
            public void behaviourInit() {
                isFinished = false;
            }

            @Override
            public void behaviourPeriodic() {
                if(!isFinished) {
                    isFinished = liftInstance.moveLift(0.4, 1);
                }
            }

            @Override
            public boolean isFinished() {
                return isFinished;
            }
        },

        // Move shoulder to pickup position
        new RobotAutoBehaviour() {
            private boolean isFinished = false;
            @Override
            public void behaviourInit() {
                isFinished = false;
            }

            @Override
            public void behaviourPeriodic() {
                if(!isFinished) {
                    isFinished = shoulderInstance.moveShoulderToPosition(CoPilotControls.MACRO_PICKUP, 0.5);
                }
            }

            @Override
            public boolean isFinished() {
                return isFinished;
            }
        },

        // Move lift to pickup position
        // Open claw
        new RobotAutoBehaviour() {

            @Override
            public void behaviourInit() {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void behaviourPeriodic() {
                // TODO Auto-generated method stub
                
            }

            @Override
            public boolean isFinished() {
                return true;
            }
        },

        // Close claw
        // Move lift to level 2
        new RobotAutoBehaviour() {

            @Override
            public void behaviourInit() {
            }

            @Override
            public void behaviourPeriodic() {                
            }

            @Override
            public boolean isFinished() {
                return true;
            }

        },

        // Move shoulder to level 2
        // Open claw when done
        new RobotAutoBehaviour() {

            @Override
            public void behaviourInit() {
            }

            @Override
            public void behaviourPeriodic() {
            }

            @Override
            public boolean isFinished() {
                return true;
            }

        }
    };

    private Lift liftInstance;
    private Shoulder shoulderInstance;
    private int autoPtr = 0;

    public Pos1TestAuto(Lift lift, Shoulder shoulder) {
        liftInstance = lift;
        shoulderInstance = shoulder;
        resetAuto();
    }

    @Override
    public void runAuto() {

    }

    @Override
    public void resetAuto() {
        autoPtr = 0;
        for(RobotAutoBehaviour b : autoChain) {
            b.behaviourInit();
        }
    }

    @Override
    public RobotAutoBehaviour[] getBehaviourChain() {
        return autoChain;
    }

    @Override
    public boolean isCompleted() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
