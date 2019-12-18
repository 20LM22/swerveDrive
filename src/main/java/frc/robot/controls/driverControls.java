package frc.robot.controls;

import frc.robot.swerveDrive;

public class driverControls extends logitechControls {

    final static public double MAXSPEED = 14; //TODO change this to whatever the max speed of the robot is in ft/sec
    final static public double multiplier = 14; //TODO change this to whatever value brings the input 1 to max speed
    //the multiplier should just be the max speed...
    final static public double MAXTURNING = MAXSPEED/swerveDrive.circumference;
    static double transAngle;
    public static final double LENGTH = 1.41; //TODO change this based on actual size of robot
    public static final double WIDTH = 1.41;
    public static double r = Math.sqrt(((LENGTH / 2) * (LENGTH / 2)) + ((WIDTH / 2) * (WIDTH / 2))); // diagonal 
    public static double circumference = round(r * Math.PI * 2);

    public static double translationalSpeed;
    public static double rotationalSpeed;
    public static double translationalAngle;
    public static double rotationalDirection;
    
    public driverControls(int port) {
        super(port);
    }
        
    public static double getTransAngle(double strafe, double forward) {
        double strafeForCalc = Math.abs(strafe);    
        double forwardForCalc = Math.abs(forward);
        double partAngle = Math.atan(forwardForCalc/strafeForCalc);
        partAngle = Math.toDegrees(partAngle);
        if (strafe == 0 && Math.abs(forward) == forward) { //if it's on the positive y axis
            return 90.0;
        }
        else if (strafe == 0 && Math.abs(forward) != forward) { //if it's on the negative y axis
            return 270.0;
        }
        else if (forward == 0 && Math.abs(strafe) == strafe) { //if it's on the positive x axis
            return 0.0;
        }   
        else if (forward == 0 && Math.abs(strafe) != strafe) { //if it's on the negative x axis
            return 180.0;
        }     
        else if (Math.abs(strafe) == strafe && Math.abs(forward) == forward) { //if it's in quad 1
            transAngle = partAngle;
            transAngle = round(transAngle);
            transAngle = checkForNegativeZero(transAngle);
            return transAngle;
        }
        else if (Math.abs(strafe) == strafe && Math.abs(forward) != forward) { //if it's in quad 4
            transAngle = 270 + (90 - partAngle);
            transAngle = round(transAngle);
            transAngle = checkForNegativeZero(transAngle);
            return transAngle;
        }
        else if (Math.abs(strafe) != strafe && Math.abs(forward) == forward) { //if it's in quad 2
            transAngle = 90 + (90 - partAngle);
            transAngle = round(transAngle);
            transAngle = checkForNegativeZero(transAngle);
            return transAngle;
        }
        //else if (Math.abs(strafe) != strafe && Math.abs(forward) != forward) { //if it's in quad 3
        else { //if it's in quad 3
            transAngle = 180 + partAngle;
            transAngle = round(transAngle);
            transAngle = checkForNegativeZero(transAngle);
            return transAngle;
        }
    }

    public static double getRotationalSpeed(double turnClockwise, double turnCounter) {
        if (turnClockwise > 0.0 && turnCounter > 0.0) {
            return 0.0;
        }
        else if (turnClockwise > 0.0) {
            return round(turnClockwise * MAXTURNING);
        }
        else if (turnCounter > 0.0) {
            return round(turnCounter * MAXTURNING);
        }
        else {
            return 0.0;
        }
    }

    public static double getTransSpeed(double strafe, double forward) {
        double x = strafe;
        double y = forward;
        x = Math.abs(x);
        y = Math.abs(y);
        double finalSpeed = round((Math.sqrt((x*x) + (y*y))));
        return toFeetSeconds(finalSpeed);
    }

    public static double getRotationalDirection(double turnClockwise, double turnCounter) {
        if (turnClockwise > 0.0) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public static double toFeetSeconds(double i) {
        return (i * multiplier);
    }

    public static double round(double x) {
        x = x*100;
        x = (int) (x + 0.5);
        x = x/100;
        return x;
    }

    public static double checkForNegativeZero(double x) {
        if (Math.abs(x) == 0) {
            return x = Math.abs(x);
        } else {
            return x;
        }
    }
}