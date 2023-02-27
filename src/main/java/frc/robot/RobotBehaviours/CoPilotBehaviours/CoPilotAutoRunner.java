package frc.robot.RobotBehaviours.CoPilotBehaviours;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Core.Utility.CoPilotControls;
import frc.robot.Interfaces.RobotAutoMaster;
import frc.robot.Interfaces.RobotBehaviour;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Lift;
import frc.robot.RobotBehaviours.CoPilotBehaviours.DefaultModes.Shoulder;
import java.util.HashMap;

public class CoPilotAutoRunner implements RobotBehaviour{

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
        //autoMasters.put(CoPilotControls.MACRO_PICKUP, new Pickup(liftInstance, shoulderInstance));
        //autoMasters.put(CoPilotControls.MACRO_CARRY, new Carry(liftInstance, shoulderInstance));
        //autoMasters.put(CoPilotControls.MACRO_LEVEL1, new Level1(liftInstance, shoulderInstance));
        //autoMasters.put(CoPilotControls.MACRO_LEVEL2, new Level2(liftInstance, shoulderInstance));
        //autoMasters.put(CoPilotControls.MACRO_LEVEL3, new Level3(liftInstance, shoulderInstance));
    }

    @Override
    public void BehaviourPeriodic() {
        Joystick inputDevice = CoPilotControls.JOYSTICK_COPILOT;

        for(int bind : autoMasters.keySet()) {
            if(inputDevice.getRawButton(bind)) {
                currentMacro = autoMasters.get(bind);
                if(!currentMacro.isCompleted()) { currentMacro.runAuto(); }
                break;
            } else {
                if(currentMacro != null) currentMacro.resetAuto();
            }
        }
    }
}
