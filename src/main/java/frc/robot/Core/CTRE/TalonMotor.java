package frc.robot.Core.CTRE;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

/*
 * Author: Lucas Soliman
 * Date Created: February 2, 2023
 * 
 * This class' encapsulates a TalonFX controller.
 * The main reason this class exists is due to
 * the config functions that are available for
 * - customizing
 * - tweaking
 * - calibrating
 * 
 * motor controllers.
 */
public class TalonMotor {
    private TalonFX talonMotor;

    public TalonMotor(int canID) {
        talonMotor = new TalonFX(canID);
        talonMotor.config_kF(0, 0);
        talonMotor.config_kP(0, 0.25);
        talonMotor.config_kI(0, 0.00025);
        talonMotor.config_kD(0, 0);
    }

    public void resetMotorPosition() {
        talonMotor.setSelectedSensorPosition(0);
    }

    public void set(ControlMode mode, double arg) {
        talonMotor.set(mode, arg);
    }

    public TalonFX getMotor() {
        return talonMotor;
    }
}
