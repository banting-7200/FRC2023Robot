package frc.robot.Lucas_Soliman.RobotBehaviours.CoPilotBehaviours;

import static frc.robot.Utility.*;

import frc.robot.Lucas_Soliman.RobotBehaviours.RobotBehaviour;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Joystick;

public final class Lights implements RobotBehaviour {

private final Joystick INPUTDEVICE = new Joystick(1);

//lights (move these later im just cracked out rn)
int lightToggle = 5;
private int lightsMode= 0;

  AddressableLED m_led = new AddressableLED(9);
  AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(11);//3 LED = 1 instance

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        m_led.setLength(m_ledBuffer.getLength());
        m_led.start();
    }

    @Override
    public void BehaviourPeriodic() {
      if (INPUTDEVICE.getRawButtonPressed(10)){
        lightsMode++;
        if(lightsMode >= 3){
          lightsMode = 0;
        }
      }

      switch(lightsMode) {
        case 0: lightsOff(); break;
        case 1: redLights(); break;
        case 2: blueLights(); break;
      }
    }

    private void redLights() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          // Sets the specified LED to the RGB values for red
          m_ledBuffer.setRGB(i, 255, 0, 0);
       }
       
       m_led.setData(m_ledBuffer);
      }
    
      private void blueLights() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          // Sets the specified LED to the RGB values for blue
          m_ledBuffer.setRGB(i, 0, 0, 255);
       }
       
       m_led.setData(m_ledBuffer);
      }
    
      private void lightsOff() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          m_ledBuffer.setRGB(i, 0, 0, 0);
       }
       
       m_led.setData(m_ledBuffer);
    }

}