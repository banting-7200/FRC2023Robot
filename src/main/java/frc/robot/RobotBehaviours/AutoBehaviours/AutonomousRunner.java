package frc.robot.RobotBehaviours.AutoBehaviours;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Core.RobotDrive;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.RobotBehaviours.AutoBehaviours.Behaviours.Pos1Auto;
import frc.robot.RobotBehaviours.AutoBehaviours.Behaviours.Pos2Auto;
import frc.robot.RobotBehaviours.AutoBehaviours.Behaviours.Pos3Auto;

public class AutonomousRunner {
    private SendableChooser<String> autonomousSelect;
    private RobotAutoMaster[] autonomousBehaviours;

    public AutonomousRunner(RobotDrive driver) {
        autonomousSelect = new SendableChooser<>();

        autonomousSelect.setDefaultOption("No Auto", "-1");
        autonomousSelect.addOption("Position 1", "0");
        autonomousSelect.addOption("Position 2", "1");
        autonomousSelect.addOption("Position 3", "2");
        SmartDashboard.putData("Auto Select", autonomousSelect);
        autonomousBehaviours = new RobotAutoMaster[] {
            new Pos1Auto(driver),
            new Pos2Auto(driver),
            new Pos3Auto(driver)
        };
    }

    public void runAuto() {
        int chosenIndex = Integer.parseInt(autonomousSelect.getSelected());
        if(chosenIndex == -1) {
            return;
        }

        if(!autonomousBehaviours[chosenIndex].isCompleted()) {
            autonomousBehaviours[chosenIndex].runAuto();
        }
    }

    public void resetAuto() {
        for(RobotAutoMaster behaviour : autonomousBehaviours) {
            behaviour.resetAuto();
        }
    }
}
