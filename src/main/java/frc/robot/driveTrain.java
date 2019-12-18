package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class driveTrain {

    private TalonSRX angleMotor; //talons are spinning and they go with the 775s
    private CANSparkMax speedMotor; //spark maxes are for moving translationally and they go with the neos
    private int zeroAdjustment;
    private CANPIDController pidController;

    public driveTrain (int angleMotor, int speedMotor, int zeroAdjustment) {
        this.angleMotor = new TalonSRX(angleMotor);
        this.angleMotor.config_kP(0,0.0);
        this.angleMotor.config_kI(0, 0.0);
        this.angleMotor.config_kD(0, 0.0);
        this.angleMotor.config_kF(0, 0.0); //do not change this
        this.zeroAdjustment = zeroAdjustment;
        this.speedMotor = new CANSparkMax(speedMotor, MotorType.kBrushless);

        pidController = this.speedMotor.getPIDController();
        pidController.setP(0.0);
        pidController.setI(0.0);
        pidController.setD(0.0);
        pidController.setFF(0.0);
        //pidController.setSmartMotionMaxAccel(x, y); //TODO change later?
        //pidController.setSmartMotionMaxVelocity(x, y); //TODO change later?
        pidController.setSmartMotionAccelStrategy(AccelStrategy.kTrapezoidal, 0);


    }

    public void drive (double speed, double angle) {
        pidController.setReference(speed, ControlType.kSmartVelocity);    
        //speedMotor.set(speed);  
        angleMotor.set(ControlMode.Position, (int) (angle*360.0/4096)-zeroAdjustment); //TODO zeroAdjustment
    }
}