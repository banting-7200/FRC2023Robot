package frc.robot.RobotBehaviours.CoPilotBehaviours.AutoBehaviours;

import frc.robot.Interfaces.RobotAutoBehaviour;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.Lift;
import frc.robot.RobotBehaviours.PilotBehaviours.DefaultModes.Shoulder;

public class ArmToStartPos implements RobotAutoMaster{

    private RobotAutoBehaviour[] autoChain;

    public ArmToStartPos(Lift lift, Shoulder shoulder) {
        
        autoChain = new RobotAutoBehaviour[] {
            //Move Lift to highest Position Behaviour
            new RobotAutoBehaviour() {
                @Override
                public void behaviourInit() {}

                @Override
                public void behaviourPeriodic() {}

                @Override
                public boolean isFinished() {
                    return !lift.setInput(-0.8);
                }
            },

            //Move Shoulder to Pickup Position Behaviour
            new RobotAutoBehaviour() {
                @Override
                public void behaviourInit() {
                }

                @Override
                public void behaviourPeriodic() {
                }

                @Override
                public boolean isFinished() {
                    return false;
                }
                
            }
        };
    }

    @Override
    public void runAuto() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public RobotBehaviour[] getBehaviourChain() {
        // TODO Auto-generated method stub
        return null;
    }
}
