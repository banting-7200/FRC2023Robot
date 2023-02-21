package frc.robot.ExternalIO;

import static frc.robot.Utility.*;

import java.nio.charset.StandardCharsets;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

/*
 * Author: Lucas Soliman
 * Date Created: January 26, 2023
 * Primary Source: https://github.com/FRC5188/ArduinoPixyAndRoboRIO/blob/master/src/main/M_I2C.java
 * 
 * This class allows for the roboRIO to communicate via the onboard I2C port.
 * There are known issues with 'system lockups' when using the I2C hence the ability to only read.
 * 
 * Any circumventions or solutions to the lockups are actively being researched.
 * For now, all data processing must occur on slave device and be sent to roboRIO.
 */
public class I2C_Interface {
    private I2C Wire;
    private int deviceAddress;
    private byte[] dataBuffer;

    public I2C_Interface(int deviceAddress) {
        Wire = new I2C(Port.kOnboard, deviceAddress);
        dataBuffer = new byte[I2C_MAXBYTESREAD];
    }

    public byte[] readI2C(int bytesToRead) {
        byte[] buffer = new byte[bytesToRead];
		boolean aborted = Wire.read(deviceAddress, bytesToRead, buffer);
        if(aborted) {
            System.out.println("I2C Read Aborted...");
            return null;
        }

        return buffer;
    }

    public byte[] readI2C() {
		boolean aborted = Wire.read(deviceAddress, I2C_MAXBYTESREAD, dataBuffer);
        if(aborted) {
            System.out.println("I2C Read Aborted...");
            return null;
        }

        return dataBuffer;
    }

    public String readI2CString() {
        byte[] data = readI2C();
        return data == null ? null : new String(dataBuffer, StandardCharsets.UTF_8);
    }
}
