package frc.robot.RobotBehaviours.CoPilotBehaviours.Macros;

import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;

public class Unpack implements RobotAutoMaster{

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
                // TODO Auto-generated method stub
                return false;
            }
        },

        // Move shoulder to 0
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
                // TODO Auto-generated method stub
                return false;
            }
            
        }
    };

    private Lift liftInstance;
    private Shoulder shoulderInstance;

    public Unpack(Lift lift, Shoulder shoulder) {
        liftInstance = lift;
        shoulderInstance = shoulder;
    }
    
    @Override
    public void runAuto() {

    }

    @Override
    public void resetAuto() {

    }

    @Override
    public RobotAutoBehaviour[] getBehaviourChain() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCompleted() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
