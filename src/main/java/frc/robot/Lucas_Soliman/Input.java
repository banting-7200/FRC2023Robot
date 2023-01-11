package frc.robot.Lucas_Soliman;

import edu.wpi.first.wpilibj.Joystick;

/*
 * Author: Lucas Soliman
 * Date Created: January 10, 2023
 * 
 * This class will contain utility functions related to joystick input.
 * Most are shortened versions of functions for code readability and length.
 * 
 * I dislike Long-lineitis
 */
public class Input {
    private final double JOYSTICK_DEADZONE = 0.25;
    private Joystick joystickInstance;

    public Input(int joystickPort) {
        joystickInstance = new Joystick(joystickPort);
    }

    // Uses two buttons to represent a 1D axis. (btnPositive returns +, btnNegative return -)
    public double getBtnAxis(int btnPositive, int btnNegative) {
        return getBtn(btnPositive) ? 1.0 : (getBtn(btnNegative) ? -1.0 : 0);
    }

    //Abbreviation for getRawbuttonPressed();
    public boolean getBtnPress(int btn) {
        return joystickInstance.getRawButtonPressed(btn);
    }

    //Abbreviation for getRawButtonReleased();
    public boolean getBtnRel(int btn) {
        return joystickInstance.getRawButtonReleased(btn);
    }

    //Abbreviation for getRawButton();
    public boolean getBtn(int btn) {
        return joystickInstance.getRawButton(btn);
    }

    public double stickX() {
        return joystickInstance.getX();
    }

    public double stickY() {
        return joystickInstance.getY();
    }

    //Takes in an input value that will be rounded to 0 if below defined deadzone.
    public double applyDeadZone(double value) {
        if(Math.abs(value) <= JOYSTICK_DEADZONE){
            return 0;
        }

        return value;
    }
}
