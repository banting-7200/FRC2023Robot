package frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.Core.Utility.CoPilotControls;
import frc.robot.Core.Utility.PilotControls;
import frc.robot.Interfaces.RobotBehaviour;

/*
 * Author: Jonathon
 * Date Created: February 17, 2023
 * 
 * Used for control of LEDs behind the kickerplate
 */
public final class Lights implements RobotBehaviour {
  private int lightsMode= 0;//integer used for changing light mode

  public int m_rainbowFirstPixelHue = 0;

  AddressableLED m_led = new AddressableLED(7);//where LEDs are plugged in
  AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(11);//3 real LEDs = 1 instance in code

  @Override
  public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
    m_led.setLength(m_ledBuffer.getLength());
    m_led.start();
  }

  @Override
  public void BehaviourPeriodic() {
      if (PilotControls.JOYSTICK_PILOT.getRawButtonPressed(PilotControls.LIGHTS_SWITCHMODE)){
        lightsMode++;
        if(lightsMode >= 4){
          lightsMode = 0;
        }
      }

      switch(lightsMode) {
        case 0: lightsOff(); break;
        case 1: redLights(); break;
        case 2: blueLights(); break;
        case 3: rainbowLights(); break;
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
          m_ledBuffer.setRGB(i, 0, 255, 0);
       }
       
       m_led.setData(m_ledBuffer);
      }
    
      private void rainbowLights() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
          m_ledBuffer.setHSV(i, hue, 255, 128);
        }
        m_rainbowFirstPixelHue += 3;
      m_rainbowFirstPixelHue %= 180;
       m_led.setData(m_ledBuffer);
       
    }

      private void lightsOff() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          m_ledBuffer.setRGB(i, 0, 0, 0);
       }
       
       m_led.setData(m_ledBuffer);
    }
}