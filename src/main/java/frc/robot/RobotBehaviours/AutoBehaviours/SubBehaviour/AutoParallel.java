package frc.robot.RobotBehaviours.AutoBehaviours.SubBehaviour;

import frc.robot.Interfaces.RobotAutoBehaviour;

public class AutoParallel implements RobotAutoBehaviour{
    private RobotAutoBehaviour[] behaviours;
    public AutoParallel(RobotAutoBehaviour[] behaviours) {
        this.behaviours = behaviours;
    }

    @Override
    public void behaviourInit() {
        for(RobotAutoBehaviour behaviour : behaviours) {
            behaviour.behaviourInit();
        }
    }

    @Override
    public void behaviourPeriodic() {
        for(RobotAutoBehaviour behaviour : behaviours) {
            if(!behaviour.isFinished()) {
                behaviour.behaviourPeriodic();
            }
        }
    }

    @Override
    public boolean isFinished() {
        for(RobotAutoBehaviour behaviour : behaviours) {
            if(!behaviour.isFinished()) {
                return false;
            }
        }

        return true;
    }
}
