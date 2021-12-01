// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {

  private final TalonFX elevatorMotor = new TalonFX(Constants.ELEVATOR_MOTOR);
  //private final DoubleSolenoid elevatorPiston = new DoubleSolenoid(Constants.ELEVATOR_AIR_IN,Constants.ELEVATOR_AIR_OUT);
  
  // enc ticks per revolution * gear ratio / phi * drum diameter
  private static final double ELEVATOR_POSITION_SENSOR_COEFFICIENT = 2048 * 20 / 3.14 * 1;


  public ElevatorSubsystem() {

    elevatorMotor.configFactoryDefault();
    elevatorMotor.setNeutralMode(NeutralMode.Brake);
    elevatorMotor.setInverted(TalonFXInvertType.Clockwise);

		/* Config the sensor used for Primary PID and sensor direction */
    elevatorMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx,
    Constants.kTimeoutMs);

    /* Ensure sensor is positive when output is positive */
		elevatorMotor.setSensorPhase(Constants.kSensorPhase);

		/* Config Position Closed Loop gains in slot0, typically kF stays zero. */
		elevatorMotor.config_kF(Constants.kPIDLoopIdx, Constants.kGains.kF, Constants.kTimeoutMs);
		elevatorMotor.config_kP(Constants.kPIDLoopIdx, Constants.kGains.kP, Constants.kTimeoutMs);
		elevatorMotor.config_kI(Constants.kPIDLoopIdx, Constants.kGains.kI, Constants.kTimeoutMs);
		elevatorMotor.config_kD(Constants.kPIDLoopIdx, Constants.kGains.kD, Constants.kTimeoutMs);

  }

 //TODO: Joystick to run elevator

  //TODO: Reset encoder to 0 when switch is triggered


  public void stopElevator() {
    elevatorMotor.set(TalonFXControlMode.PercentOutput, 0);
  }

  public void elevatorTop() {
    elevatorMotor.set(TalonFXControlMode.Position, Constants.TARGET_POSITION_TOP 
      * ELEVATOR_POSITION_SENSOR_COEFFICIENT);
  }

  public void elevatorMid() {
    elevatorMotor.set(TalonFXControlMode.Position, Constants.TARGET_POSITION_MID
      * ELEVATOR_POSITION_SENSOR_COEFFICIENT);
  }
  
  public void elevatorBottom() {
    elevatorMotor.set(TalonFXControlMode.Position, Constants.TARGET_POSITION_BOTTOM
      * ELEVATOR_POSITION_SENSOR_COEFFICIENT);

  }

  /*
  public void elevatorPistonOn() {
    elevatorPiston.set(Value.kReverse);
  }

  public void elevatorPistonOff() {
    elevatorPiston.set(Value.kForward);
  }
*/
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


}


