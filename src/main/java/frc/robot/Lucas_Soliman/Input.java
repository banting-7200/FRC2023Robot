package frc.robot.Lucas_Soliman;

import edu.wpi.first.wpilibj.Joystick;

/*
 * Author: Lucas Soliman
 * Date Created: January 10, 2023
 * 
 * This class will contain utility functions related to joystick input.
 * Most are shortened versions of functions for code readability and length.
 */
public class Input {
    private Joystick joystickInstance;
    public Input(Joystick joystickInstance) {
        this.joystickInstance = joystickInstance;
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

    //Abbreviation for getRawAxis();
    public double getAxis(int axis) {
        return joystickInstance.getRawAxis(axis);
    }
}
