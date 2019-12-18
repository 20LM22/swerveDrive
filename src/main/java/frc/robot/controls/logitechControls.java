package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;

public class logitechControls {
   
    public static Joystick joy;

    public logitechControls(int port) {
        joy = new Joystick(port);
    }

    public static double getStrafe() {
        return joy.getRawAxis(0);
    }
    
    public static double getForward() {
        return joy.getRawAxis(1);
    }
    
    public static double getCounterC() {
        return joy.getRawAxis(3);
    }

    public static double getClock() {
        return joy.getRawAxis(2);
    }
}