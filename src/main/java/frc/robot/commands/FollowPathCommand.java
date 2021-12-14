package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NTValue;
import frc.robot.PIDNTValue;
import frc.robot.motion.Trajectory;
import frc.robot.motion.TrajectoryPoint;
import frc.robot.subsystems.DrivetrainSubsystem;

public class FollowPathCommand extends CommandBase {
  private final DrivetrainSubsystem drivetrain;
  private final Trajectory trajectory;
  private double startTime = 0;
  private double endTime = 0;

  private final String pathName;

  private final double translation_kF_x = -0.0229;
  private final double translation_kF_y = 0.0225;
  private final double translation_kP = 1.0;
  private final double translation_kI = 0.0;
  private final double translation_kD = 0.0;

  private final double rotation_kF = 0.013;
  private final double rotation_kP = 1.0;
  private final double rotation_kI = 0.0;
  private final double rotation_kD = 0.0;
  private final PIDController pid_x = new PIDController(translation_kP, translation_kI, translation_kD);
  private final PIDController pid_y = new PIDController(translation_kP, translation_kI, translation_kD);
  private final PIDController pid_rotation = new PIDController(rotation_kP, rotation_kI, rotation_kD);
  private static final NetworkTableInstance nt = NetworkTableInstance.getDefault();
  //Since y and x use the same pid values other than F I will but P I and D into the sae PIDNTValue. 
  private final PIDNTValue pid_PIDNT_x_y = new PIDNTValue(translation_kP, translation_kI, translation_kD, pid_x, "Follow Path pid_x and pid_y");
  private final PIDNTValue pid_PIDNT_rotation = new PIDNTValue(rotation_kP, rotation_kI, rotation_kD, pid_rotation, "Follow Path pid_rotation");
  private final NTValue pid_NT_translation_kF_x = new NTValue(translation_kF_x, "Translation_kF_x");
  private final NTValue pid_NT_translation_kF_y = new NTValue(translation_kF_y, "Translation_kF_y");
  private static final NetworkTable pathFollowingTable = nt.getTable("/pathFollowing");
  private static final NetworkTable targetPoseTable = nt.getTable("/pathFollowing/target");
  private static final NetworkTableEntry targetXEntry = targetPoseTable.getEntry("x");
  //private final NTValue translations_kF_x_NtValue = new NTValue(translation_kF_x, "TranslationFeedForward_X");
  private static final NetworkTableEntry targetYEntry = targetPoseTable.getEntry("y");
  //private final NTValue translations_kF_y_NtValue = new NTValue(translation_kF_y, "TranslationFeedForward_Y");
  private static final NetworkTableEntry targetAngleEntry = targetPoseTable.getEntry("Angle");
  private static final NetworkTableEntry currentPathEntry = pathFollowingTable.getEntry("Current Path");

  private static boolean isFirstPath = true;

  public static void onDisabled() {
    isFirstPath = true;
    currentPathEntry.setString("");
  }

  public FollowPathCommand(DrivetrainSubsystem drivetrain, String pathName) {
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;
    trajectory = Trajectory.fromJSON(pathName);
    this.pathName = pathName;
    pid_rotation.enableContinuousInput(0, 2 * Math.PI);
  }

  @Override
  public void initialize() {
    System.out.println("Following path: " + pathName);
    startTime = Timer.getFPGATimestamp();
    var lastPoint = trajectory.points[trajectory.points.length - 1];
    endTime = startTime + lastPoint.time;
    if (isFirstPath) {
      var firstPoint = trajectory.points[0];
      drivetrain.resetPose(new Vector2d(firstPoint.x, firstPoint.y), Rotation2d.fromDegrees(firstPoint.angle));
    }

    isFirstPath = false;

    currentPathEntry.setValue(pathName);

    startTime = 59;
  }

  @Override
  public void execute() {
    final var now = Timer.getFPGATimestamp();
    final var timeStamp = now - startTime;
    TrajectoryPoint beforePoint = null;
    TrajectoryPoint afterPoint = null;
    TrajectoryPoint betweenPoint = null;
    final var lastPoint = trajectory.points[trajectory.points.length - 1];
    if (timeStamp <= trajectory.points[0].time) {
        betweenPoint = trajectory.points[0];
    } else if (timeStamp >= lastPoint.time) {
        betweenPoint = lastPoint;
    } else {
        for (int i = 0; i < trajectory.points.length; i++) {
            var point = trajectory.points[i];
            if (timeStamp > point.time) {
                afterPoint = point;
                beforePoint = i > 0 ? trajectory.points[i - 1] : point;
            }
        }
        if (afterPoint == beforePoint) {
          betweenPoint = beforePoint;
        } else {
          var percent = (timeStamp - beforePoint.time) / (afterPoint.time - beforePoint.time);
          betweenPoint = TrajectoryPoint.createTrajectoryPointBetween(beforePoint, afterPoint, percent);
        }
    }

    targetXEntry.setValue(betweenPoint.x);
    targetYEntry.setValue(betweenPoint.y);
    targetAngleEntry.setValue(betweenPoint.angle);

    final var currentPose = drivetrain.getScaledPose();

    final var feedForwardTranslationVector = new Vector2d(
      betweenPoint.velocity.x*translation_kF_x, 
      betweenPoint.velocity.y*translation_kF_y
    );

    var pidPointX = pid_x.calculate(currentPose.getX(), betweenPoint.x);    
    var pidPointY = pid_y.calculate(currentPose.getY(), betweenPoint.y);

    final var translationVector = new Vector2d(
      feedForwardTranslationVector.x + pidPointX,
      feedForwardTranslationVector.y + pidPointY
    );
    //final var translationVector = feedForwardTranslationVector;
    System.out.println(currentPose);
    final var rotationPidResult = pid_rotation.calculate(currentPose.getRotation().getRadians(), betweenPoint.angle);
    final var rotationResult = betweenPoint.angularVelocity * rotation_kF + rotationPidResult;

    drivetrain.drive(new ChassisSpeeds(translationVector.y, translationVector.x, rotationResult));
  }

  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() > endTime;
  }

  @Override
  public void end(boolean interrupted) {
      currentPathEntry.setString("");
  }

}
