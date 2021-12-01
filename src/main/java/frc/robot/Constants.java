// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    /**
     * The left-to-right distance between the drivetrain wheels
     *
     * Should be measured from center to center.
     */
    public static final double DRIVETRAIN_TRACKWIDTH_METERS = 0.3175;
    /**
     * The front-to-back distance between the drivetrain wheels.
     *
     * Should be measured from center to center.
     */
    public static final double DRIVETRAIN_WHEELBASE_METERS = 0.3429;

    public static final int DRIVETRAIN_PIGEON_ID = 0;

    public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 1;
    public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 2; 
    public static final int FRONT_LEFT_MODULE_STEER_ENCODER = 9; 
    public static final double FRONT_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(90);

    public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 3; 
    public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 4; 
    public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 10;
    public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(0);

    public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 5;
    public static final int BACK_LEFT_MODULE_STEER_MOTOR = 6;
    public static final int BACK_LEFT_MODULE_STEER_ENCODER = 11;
    public static final double BACK_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(75);

    public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 7;
    public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 8;
    public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 12;
    public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(130);

    public static final int ELEVATOR_MOTOR = 9;
	public static final int ELEVATOR_AIR_IN = 0;
	public static final int ELEVATOR_AIR_OUT = 1;

                                                // kP,  kI,  kD,  kF, kIzone, kPeakOutput
    public static final Gains kGains = new Gains(0.2, 0.0, 1.0, 0.0, 0, 1.0);
    public static final int kPIDLoopIdx = 0;
    public static final int kTimeoutMs = 30;
     
    public static final double TARGET_POSITION_TOP = 4;
	public static final double TARGET_POSITION_MID = 2;
	public static final double TARGET_POSITION_BOTTOM = 0;
    
    /* Choose so that Talon does not report sensor out of phase */
	public static boolean kSensorPhase = true;
}
