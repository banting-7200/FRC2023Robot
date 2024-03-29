package frc.robot.RobotBehaviours.CoPilotBehaviours;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Core.Utility.CoPilotControls;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;
import frc.robot.RobotBehaviours.CoPilotBehaviours.Macros.TalonFXMoveTo;

import java.util.HashMap;

public class CoPilotAutoRunner implements RobotBehaviour{
    public static boolean runningMacro;
    private HashMap<Integer, RobotAutoMaster> autoMasters;
    private RobotAutoMaster currentMacro;

    @Override
    public void BehaviourInit(RobotBehaviour[] defaultBehaviours) {
        Lift liftInstance = null;
        Shoulder shoulderInstance = null;

        for(RobotBehaviour b : defaultBehaviours) {
            if(b instanceof Lift) {
                liftInstance = (Lift)b;
            } else if(b instanceof Shoulder) {
                shoulderInstance = (Shoulder)b;
            }
        }

        autoMasters = new HashMap<>();
        autoMasters.put(CoPilotControls.MACRO_PICKUP.get(), new TalonFXMoveTo(liftInstance, shoulderInstance, CoPilotControls.MACRO_PICKUP.get()));
        autoMasters.put(CoPilotControls.MACRO_CARRY.get(), new TalonFXMoveTo(liftInstance, shoulderInstance, CoPilotControls.MACRO_CARRY.get()));
        autoMasters.put(CoPilotControls.MACRO_LEVEL1.get(), new TalonFXMoveTo(liftInstance, shoulderInstance, CoPilotControls.MACRO_LEVEL1.get()));
        autoMasters.put(CoPilotControls.MACRO_LEVEL2.get(), new TalonFXMoveTo(liftInstance, shoulderInstance, CoPilotControls.MACRO_LEVEL2.get()));
        autoMasters.put(CoPilotControls.MACRO_LEVEL3.get(), new TalonFXMoveTo(liftInstance, shoulderInstance, CoPilotControls.MACRO_LEVEL3.get()));
    }

    @Override
    public void BehaviourPeriodic() {
        Joystick inputDevice = CoPilotControls.JOYSTICK_COPILOT;

        for(int bind : autoMasters.keySet()) {
            if(inputDevice.getRawButton(bind)) {
                currentMacro = autoMasters.get(bind);
                if(!currentMacro.isCompleted()) { 
                    currentMacro.runAuto(); 
                }

                break;
            }

            if(currentMacro != null) {
                currentMacro.resetAuto();
                currentMacro = null;
            }
        }

        runningMacro = currentMacro != null;
    }
}
