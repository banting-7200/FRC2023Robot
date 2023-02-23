package frc.robot.Interfaces;

public interface RobotAutoMaster {
    public void runAuto();
    public void resetAuto();
    public RobotAutoBehaviour[] getBehaviourChain();
    public boolean isCompleted();
}
