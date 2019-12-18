
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

swerveDrive swerve;  
driveTrain backRightMotor;
driveTrain backLeftMotor;
driveTrain frontLeftMotor;
driveTrain frontRightMotor;


  @Override
  public void robotInit() {
     //TODO change this: angle motor port, forward motor port, zeroAdjustment
    backRightMotor = new driveTrain(0,0,0);
    backLeftMotor = new driveTrain(2, 3, 1);
    frontRightMotor = new driveTrain(4, 5, 2);
    frontLeftMotor = new driveTrain(6, 7, 3);
    swerve = new swerveDrive(backRightMotor, backLeftMotor, frontRightMotor, frontLeftMotor);
  }
    //TODO 2910-2017
  @Override
    public void robotPeriodic() {
      
    }

  @Override
    public void autonomousInit() {

    } 

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopPeriodic() {
    swerve.swerve();

  }

  @Override
  public void testPeriodic() {
  }
}
