package frc.robot.Core;

import java.util.HashMap;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Interfaces.RobotAutoMaster;

/*
 * Author: Lucas Soliman
 * Date Created: February 23, 2023
 * 
 * This class is what will run all RobotAutoMaster behaviour macros
 * Only one macro can play at a time. (Mainly lift and shoulder macros)
 */
public class RobotMacroRunner {
    private HashMap<Integer, Supplier<RobotAutoMaster>> masterMap;
    private RobotAutoMaster currentAutoBehaviour;    
    private Joystick inputMaster;

    private int currentAutoBind;

    public RobotMacroRunner(HashMap<Integer, Supplier<RobotAutoMaster>> masterMap, int inputMaster) {
        this.masterMap = masterMap;
        this.inputMaster = new Joystick(inputMaster);
    }

    public void robotMacroRunnerTeleop() {
        if(currentAutoBehaviour != null) {
            if(inputMaster.getRawButton(currentAutoBind)) {
                currentAutoBehaviour.runAuto();
            }
        }

        boolean canSelectAuto = currentAutoBehaviour == null || currentAutoBehaviour.isCompleted() || !inputMaster.getRawButton(currentAutoBind);
        if(canSelectAuto) {
            currentAutoBehaviour = null;

            for(Integer buttonBind : masterMap.keySet()) {
                if(inputMaster.getRawButton(buttonBind)) {
                    currentAutoBehaviour = masterMap.get(buttonBind).get();
                }
            }
        }
    }
}
