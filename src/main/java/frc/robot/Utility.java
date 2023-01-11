package frc.robot;

import frc.robot.Lucas_Soliman.*;

/*
 * Author: Lucas Soliman
 * Date Created: January 10, 2023
 * 
 * Stores constants that will be used project wide.
 * Will be updated by everyone working on project.
 */
public class Utility {
    // Integers storing ports that external drive devices are connected to on computer (USB)
    public static final int PORT_JOYSTICK = 0;

    // Integers storing ID of axis on Joystick
    public static final int JOYSTICK_X = 1;
    public static final int JOYSTICK_Y = 2;

    // Integers storing ports that drive motors are connected to on RoboRIO
    public static final int MOTOR_DRIVEFWDLEFT = 1;
    public static final int MOTOR_DRIVEBACKLEFT = 2;
    public static final int MOTOR_DRIVEFWDRIGHT = 3;
    public static final int MOTOR_DRIVEBACKRIGHT = 4;

    // Drivespeed being the speed of drive motors.
    // Creepspeed referring to a slower speed for finer drive adjustments.
    public static final double DRIVE_NORMALSPEED = 1.0;
    public static final double DRIVE_CREEPSPEED = 0.25;

    // Armspeed referring to the speed at which the arm would rotate
    // Creepspeed referring to a slower speed for finer arm adjustments.
    public static final double ARM_SPEED = 0.5;
    public static final double ARM_CREEPSPEED = 0.1;

    // Initialise Joystick for Input class
    public static final Input INPUT = new Input(PORT_JOYSTICK);
}