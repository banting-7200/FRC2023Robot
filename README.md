# 2023 Night Light Parade Robot
 This is the robot code for the 2023 Alliston Night Light Parade. 
 Changes that have been made:
  - Remapped Lift Up and Down controls to Y(Up) and A(Down) on the Logitech f310 controller (src\main\java\frc\robot\Core\Utility.java)
  - Remapped Wrist rotate left and right controls to B(left) and X(right) on the Logitech F310 Controller (src\main\java\frc\robot\Core\Utility.java)
  - Remapped Shoulder Rotate Up and Down, Right bumper (Up) and Right trigger (Down) on the Logitech F310 Controller (src\main\java\frc\robot\Core\Utility.java)
  - Made changes to the kick light toggling as we need lights to always be on and haven't been mapped to the F310 Controller (src\main\java\frc\robot\RobotBehaviours\CoPilotBehaviours\DefaultModes\Lights.java)

# Libraries
The libraries below are being used in this project:

 - https://software-metadata.revrobotics.com/REVLib-2023.json
 - https://maven.ctr-electronics.com/release/com/ctre/phoenix/Phoenix5-frc2023-latest.json
