package frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.Core.Utility.CoPilotControls;
import frc.robot.Interfaces.RobotBehaviour;

/*
 * Author: Jonathon
 * Date Created: February 17, 2023
 * 
 * Used for control of LEDs behind the kickerplate
 */
public final class Lights implements RobotBehaviour {
  private int lightsMode= 0;

  AddressableLED m_led = new AddressableLED(7);
  AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(11);//3 LED = 1 instance

  @Override
  public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
    m_led.setLength(m_ledBuffer.getLength());
    m_led.start();
  }

  @Override
  public void BehaviourPeriodic() {
      if (CoPilotControls.JOYSTICK_COPILOT.getRawButtonPressed(CoPilotControls.LIGHTS_SWITCHMODE)){
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