package frc.robot.RobotBehaviours.AutoBehaviours.SubBehaviour;

import frc.robot.Core.RobotDrive;
import frc.robot.Interfaces.RobotAutoBehaviour;

public class AutoDrive implements RobotAutoBehaviour{
    private final int BACKUP_TICKS;

    private RobotDrive DRIVE;
    private final double DRIVE_TURN;
    private final double DRIVE_FWD;
    private int backupTime;

    public AutoDrive(RobotDrive driveInstance, double runSeconds, double turn, double fwd) {
        BACKUP_TICKS = (int)((runSeconds * 1000.0) / 20.0);
        DRIVE = driveInstance;
        DRIVE_TURN = turn;
        DRIVE_FWD = fwd;

        backupTime = BACKUP_TICKS;
    }

    @Override
    public void behaviourInit() {
        backupTime = BACKUP_TICKS;
    }

    @Override
    public void behaviourPeriodic() {
        if(backupTime > 0) {
            DRIVE.DriveRobot(DRIVE_TURN, DRIVE_FWD);
            backupTime--;
        }

        if(backupTime <= 0) {
            DRIVE.DriveRobot(0, 0);
        }
    }

    @Override
    public boolean isFinished() {
        return backupTime <= 0;
    }
    
}
