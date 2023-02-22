package frc.robot.RobotBehaviours;

public interface RobotAutoBehaviour {
    public void behaviourInit();
    public void behaviourPeriodic();
    
    public boolean isFinished();
    public boolean isInterruptable();
}
