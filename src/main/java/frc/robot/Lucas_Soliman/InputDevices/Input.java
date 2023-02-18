package frc.robot.Lucas_Soliman.InputDevices;

import static frc.robot.Utility.*;
import edu.wpi.first.wpilibj.Joystick;

/*
 * Author: Lucas Soliman
 * Date Created: January 10, 2023
 * 
 * This class will contain utility functions related to joystick input.
 * Most are shortened versions of functions for code readability and length.
 * Initialised in Utility.java
 * 
 * I try to avoid long-lineitis as much as I can for readability.
 */
public final class Input {
    public Joystick joystickInstance;
    public Input(int joystickPort) {
        joystickInstance = new Joystick(joystickPort);
    }

    public int pov() {
        return joystickInstance.getPOV();
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

    //Abbreviation for joystickInstance.getX();
    public double stickX() {
        return joystickInstance.getX();
    }

    //Abbreviation for joystickInstance.getX();
    public double stickY() {
        return joystickInstance.getY();
    }

    //Takes in an input value that will be rounded to 0 if below defined deadzone.
    public double applyDeadZone(double value) {
        if(Math.abs(value) <= JOYSTICK_DEADZONE) { return 0; }
        return value;
    }
}
