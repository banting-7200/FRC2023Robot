package frc.robot.Interfaces;

/*
 * This should be a base class that can be inherited, not an interface.
 * Using polymorphism will eliminate boilerplate code that needs to be written.
 */
public interface RobotAutoMaster {
    public void runAuto();
    public void resetAuto();
    public RobotAutoBehaviour[] getBehaviourChain();
    public boolean isCompleted();
}
