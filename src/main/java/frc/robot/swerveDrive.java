package frc.robot;

import frc.robot.controls.driverControls;
import frc.robot.controls.logitechControls;

public class swerveDrive {
    public static final double LENGTH = 1.41; //TODO change this based on actual size of robot
    public static final double WIDTH = 1.41;
    public static boolean frontRight = true;
    public static boolean frontLeft = false;
    public static boolean backRight = false;
    public static boolean backLeft = false;

    public static double r = Math.sqrt(((LENGTH / 2) * (LENGTH / 2)) + ((WIDTH / 2) * (WIDTH / 2))); // diagonal 
    public static double circumference = round(r * Math.PI * 2);

    private driveTrain backRightMotor; 
    private driveTrain backLeftMotor;
    private driveTrain frontRightMotor;
    private driveTrain frontLeftMotor;
    
    public swerveDrive (driveTrain backRight, driveTrain backLeft, driveTrain frontRight, driveTrain frontLeft) {
        this.backRightMotor = backRight;
        this.backLeftMotor = backLeft;
        this.frontRightMotor = frontRight;
        this.frontLeftMotor = frontLeft;
    }

    public void swerve() {
        
        double clockwise = logitechControls.getClock();
        double cClockwise = logitechControls.getCounterC();
        double x = logitechControls.getStrafe();
        double y = logitechControls.getForward();

        double translationalSpeed = driverControls.getTransSpeed(x, y);
        double rotationalSpeed = driverControls.getRotationalSpeed(clockwise, cClockwise);
        double translationalAngle = driverControls.getTransAngle(x, y);
        double rotationalDirection = driverControls.getRotationalDirection(clockwise, cClockwise);
          
        double rotationalSpeedFeet = rotationalSpeed * circumference;

        double angleWeKnow = Math.atan(LENGTH/WIDTH);
        angleWeKnow = Math.toDegrees(angleWeKnow);
        double tangent = 90 - angleWeKnow;

        double xOfTranslational = 0;
        double yOfTranslational = 0;
        double frontRightAngle;
        double frontLeftAngle;
        double backRightAngle;
        double backLeftAngle;
        double xOfRotationalBackRight = 0;
        double yOfRotationalBackRight = 0;
        double xOfRotationalBackLeft = 0;
        double yOfRotationalBackLeft = 0;
        double xOfRotationalFrontRight = 0;
        double yOfRotationalFrontRight = 0;
        double xOfRotationalFrontLeft = 0;
        double yOfRotationalFrontLeft = 0;
        double finalVectorXBackRight = 0;
        double finalVectorYBackRight = 0;
        double finalVectorXBackLeft = 0;
        double finalVectorYBackLeft = 0;
        double finalVectorXFrontRight = 0;
        double finalVectorYFrontRight = 0;
        double finalVectorXFrontLeft = 0;
        double finalVectorYFrontLeft = 0;

        if (frontRight == true) {
            frontRightAngle = 180 - tangent;
            frontRightAngle = Math.toRadians(frontRightAngle);
            frontRight = false;
            frontLeft = true;
        } if (frontLeft == true) {
            frontLeftAngle = 270 - tangent;
            frontLeftAngle = Math.toRadians(frontLeftAngle);
            frontLeft = false;
            backLeft = true;
        } if (backLeft == true) {
            backLeftAngle = 360 - tangent;
            backLeftAngle = Math.toRadians(backLeftAngle);
            backLeft = false;
            backRight = true;
        } if (backRight == true) {
            backRightAngle = 90 - tangent;
            backRightAngle = Math.toRadians(backRightAngle);
            backRight = false;
        }

        tangent = Math.toRadians(tangent);
        
        if (rotationalDirection == 0) {
        double xOfRotational = rotationalSpeedFeet * Math.cos(tangent);
        double yOfRotational = rotationalSpeedFeet * Math.sin(tangent);

        //setting each motor to true to assign them x and y rotational speeds moving counterclockwise
            xOfRotationalFrontRight = xOfRotational * -1; //x is negative
            yOfRotationalFrontRight = yOfRotational; //y is positive
          
            xOfRotationalFrontLeft = xOfRotational * -1; //x is negative
            yOfRotationalFrontLeft = yOfRotational * -1; //y is negative
      
            xOfRotationalBackLeft = xOfRotational; //x is positive
            yOfRotationalBackLeft = yOfRotational * -1; //y is negative
    
            xOfRotationalBackRight = xOfRotational; //x is positive
            yOfRotationalBackRight = yOfRotational; //y is positive
       
        }
        
        if (rotationalDirection == 1) {
            double xOfRotational = rotationalSpeedFeet * Math.cos(tangent);
            double yOfRotational = rotationalSpeedFeet * Math.sin(tangent);
    
            //setting each motor to true to assign them x and y rotational speeds moving clockwise
                xOfRotationalFrontRight = xOfRotational; //x is positive
                yOfRotationalFrontRight = yOfRotational * -1; //y is negative
              
                xOfRotationalFrontLeft = xOfRotational; //x is positive
                yOfRotationalFrontLeft = yOfRotational; //y is positive
          
                xOfRotationalBackLeft = xOfRotational * -1; //x is negative
                yOfRotationalBackLeft = yOfRotational; //y is positive
        
                xOfRotationalBackRight = xOfRotational * -1; //x is negative
                yOfRotationalBackRight = yOfRotational * -1; //y is negative
        }

        if (translationalAngle == 360) {
            translationalAngle = 0;
        }

        //now making sure the translational speed and angle create the right x and y values to add to rotational x and y values
        if (translationalAngle == 90) {
            xOfTranslational = 0;
            yOfTranslational = translationalSpeed;
        }
        else if (translationalAngle == 180) {
            xOfTranslational = translationalSpeed * -1;
            yOfTranslational = 0;
        } 
        else if (translationalAngle == 270) {
            xOfTranslational = 0;
            yOfTranslational = translationalSpeed * -1;
        } 
        else if (translationalAngle == 0) {
            xOfTranslational = translationalSpeed;
            yOfTranslational = 0;

        }
        //continuing to do that
        else if (translationalAngle > 0 && translationalAngle < 90) {
            translationalAngle = Math.toRadians(translationalAngle);
            xOfTranslational = translationalSpeed * Math.sin(translationalAngle);
            yOfTranslational = translationalSpeed * Math.cos(translationalAngle);
            translationalAngle = Math.toDegrees(translationalAngle);
        }
        else if (translationalAngle > 90 && translationalAngle < 180) {
            translationalAngle = Math.toRadians(translationalAngle);
            xOfTranslational = translationalSpeed * Math.sin(translationalAngle) * -1;
            yOfTranslational = translationalSpeed * Math.cos(translationalAngle);
            translationalAngle = Math.toDegrees(translationalAngle);
        } 
        else if (translationalAngle > 180 && translationalAngle < 270) {
            translationalAngle = Math.toRadians(translationalAngle);
            xOfTranslational = translationalSpeed * Math.sin(translationalAngle) * -1;
            yOfTranslational = translationalSpeed * Math.cos(translationalAngle) * -1;
            translationalAngle = Math.toDegrees(translationalAngle);
        } 
        else if (translationalAngle > 270 && translationalAngle < 360) {
            translationalAngle = Math.toRadians(translationalAngle);
            xOfTranslational = translationalSpeed * Math.sin(translationalAngle);
            yOfTranslational = translationalSpeed * Math.cos(translationalAngle) * -1;
            translationalAngle = Math.toDegrees(translationalAngle);
        }

        //now that we've got the x and y of the translational and rotational motions adjusted for what motors, we can calculate the final vectors
        //these are x and y speeds for each of the motors
        finalVectorXBackRight = xOfTranslational + xOfRotationalBackRight;
        finalVectorXBackRight = round(finalVectorXBackRight);
        finalVectorXBackRight = checkForNegativeZero(finalVectorXBackRight);

        finalVectorYBackRight = yOfTranslational + yOfRotationalBackRight;
        finalVectorYBackRight = round(finalVectorYBackRight);
        finalVectorYBackRight = checkForNegativeZero(finalVectorYBackRight);
      
        finalVectorXBackLeft = xOfTranslational + xOfRotationalBackLeft;
        finalVectorXBackLeft = round(finalVectorXBackLeft);
        finalVectorXBackLeft = checkForNegativeZero(finalVectorXBackLeft);

        finalVectorYBackLeft = yOfTranslational + yOfRotationalBackLeft;
        finalVectorYBackLeft = round(finalVectorYBackLeft);
        finalVectorYBackLeft = checkForNegativeZero(finalVectorYBackLeft);

        finalVectorXFrontRight = xOfTranslational + xOfRotationalFrontRight;
        finalVectorXFrontRight = round(finalVectorXFrontRight);
        finalVectorXFrontRight = checkForNegativeZero(finalVectorXFrontRight);

        finalVectorYFrontRight = yOfTranslational + yOfRotationalFrontRight;
        finalVectorYFrontRight = round(finalVectorYFrontRight);
        finalVectorYFrontRight = checkForNegativeZero(finalVectorYFrontRight);

        finalVectorXFrontLeft = xOfTranslational + xOfRotationalFrontLeft;
        finalVectorXFrontLeft = round(finalVectorXFrontLeft);
        finalVectorXFrontLeft = checkForNegativeZero(finalVectorXFrontLeft);

        finalVectorYFrontLeft = yOfTranslational + yOfRotationalFrontLeft;
        finalVectorYFrontLeft = round(finalVectorYFrontLeft);
        finalVectorYFrontLeft = checkForNegativeZero(finalVectorYFrontLeft);

        //taking the x and y for each speed and coming up with the final speed for each motor
        double finalSpeedFrontLeft = Math.sqrt((finalVectorXFrontLeft * finalVectorXFrontLeft) + (finalVectorYFrontLeft * finalVectorYFrontLeft));
        finalSpeedFrontLeft = round(finalSpeedFrontLeft);
        
        double finalSpeedFrontRight = Math.sqrt((finalVectorXFrontRight * finalVectorXFrontRight) + (finalVectorYFrontRight * finalVectorYFrontRight));
        finalSpeedFrontRight = round(finalSpeedFrontRight);
        
        double finalSpeedBackLeft = Math.sqrt((finalVectorXBackLeft * finalVectorXBackLeft) + (finalVectorYBackLeft * finalVectorYBackLeft));
        finalSpeedBackLeft = round(finalSpeedBackLeft);

        double finalSpeedBackRight = Math.sqrt((finalVectorXBackRight * finalVectorXBackRight) + (finalVectorYBackRight * finalVectorYBackRight));
        finalSpeedBackRight = round(finalSpeedBackRight);

        //finding the angle of each motor
        double finalAngleFrontLeft = calcAngle(finalVectorXFrontLeft, finalVectorYFrontLeft);
        double finalAngleFrontRight = calcAngle(finalVectorXFrontRight, finalVectorYFrontRight);
        double finalAngleBackLeft = calcAngle(finalVectorXBackLeft, finalVectorYBackLeft);
        double finalAngleBackRight = calcAngle(finalVectorXBackRight, finalVectorYBackRight);

        backRightMotor.drive(finalSpeedBackRight, finalAngleBackRight);
        backLeftMotor.drive(finalSpeedBackLeft, finalAngleBackLeft);
        frontRightMotor.drive(finalSpeedFrontRight, finalAngleFrontRight);
        frontLeftMotor.drive(finalSpeedFrontLeft, finalAngleFrontLeft);
    }

    public static double calcAngle(double adjacent, double opposite) { //y is opposite and x is adjacent
        if (opposite == 0 && Math.abs(adjacent) == adjacent) {
            return 0.0;
        }
        else if (opposite == 0 && Math.abs(adjacent) != adjacent) {
            return 180.0;
        }
        else if (adjacent == 0 && Math.abs(opposite) == opposite) {
            return 90.0;
        }
        else if (adjacent == 0 && Math.abs(opposite) != opposite) {
            return 270.0;
        } 
        else if (Math.abs(opposite) == opposite && Math.abs(adjacent) == adjacent) {
            double t = 0;
            t = Math.atan(opposite/adjacent);
            t = Math.toDegrees(t);
            t = round(t);
            t = checkForNegativeZero(t);
            return t;
        } 
        else if (Math.abs(opposite) == opposite && Math.abs(adjacent) != adjacent) {      
            adjacent = Math.abs(adjacent);
            double i = Math.atan(opposite/adjacent);
            i = Math.toDegrees(i);
            double j = 0;
            j = 180 - 90 - i;
            j = j + 90;
            j = round(j);
            j = checkForNegativeZero(j);
            return j;
        }
        else if (Math.abs(opposite) != opposite && Math.abs(adjacent) != adjacent) {
            adjacent = Math.abs(adjacent);
            opposite = Math.abs(opposite);
            double l = Math.atan(opposite/adjacent);
            l = Math.toDegrees(l);
            double m = 0;
            m = m + 180;
            m = round(m);
            m = checkForNegativeZero(m);
            return m;
        }
        //else if (Math.abs(opposite) != opposite && Math.abs(adjacent) == adjacent) {
            else {
            opposite = Math.abs(opposite);
            double n = Math.atan(opposite/adjacent);
            n = Math.toDegrees(n);
            double w = 0;
            w = 180 - 90 - n;
            w = w + 270;
            w = round(w);
            w = checkForNegativeZero(w);
            return w;
        }
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