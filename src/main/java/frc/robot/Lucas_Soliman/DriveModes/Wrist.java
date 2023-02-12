package frc.robot.Lucas_Soliman.DriveModes;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class Wrist implements DriveMode {
    private boolean canRotate;
    private PWMSparkMax wristMotorController;
    //private Input inputDevice;
    private double cyclesComplete = 0;
    private Joystick stick;

    public Wrist(int pwmPort, Joystick coPilotInputDevice) {
        wristMotorController = new PWMSparkMax(pwmPort);
        stick = coPilotInputDevice;
    }

    @Override
    public void DriveModeInit() {
        System.out.println("Wrist Init...");
        canRotate = true;
    }

    @Override
    public void DriveModePeriodic() {
        if(stick.getRawButtonReleased(3)) {
            rotate90();
        }

        if(!canRotate) {
            cyclesComplete += 0.008;

            if(cyclesComplete >= 0.25) {
                wristMotorController.set(0);
                canRotate = true;
            }
        }
    }

    public void rotate90() {
        if(!canRotate) {
            System.out.println("Wrist is busy...");
            return;
        }

        canRotate = false;
        cyclesComplete = 0;

        wristMotorController.set(1);
    }
}
