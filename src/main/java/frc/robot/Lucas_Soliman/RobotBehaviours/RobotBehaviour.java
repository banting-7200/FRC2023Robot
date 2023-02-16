package frc.robot.Lucas_Soliman.RobotBehaviours;

/*
 * Author: Lucas Soliman
 * Date Created: January 16, 2023
 * 
 * Aside from the Periodic function, all drive mode classes inheriting this interface
 * must contain a constructor that takes in a RobotDrive Instance.
 */
public interface RobotBehaviour {
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours);
    public void BehaviourPeriodic();
}
