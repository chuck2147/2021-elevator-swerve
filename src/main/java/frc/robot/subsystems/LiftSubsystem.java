// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;



public class LiftSubsystem extends SubsystemBase {
  public TalonFX liftMotor = new TalonFX(1);
  public LiftSubsystem() {}

  public void stopLift() {
    liftMotor.set(0);
  }

  public void lifeMove() {
    liftMotor.set(Constants.Lift_Speed);
  }

  public void liftReverse() {
    liftMotor.set(-Constants.Lift_Speed);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
