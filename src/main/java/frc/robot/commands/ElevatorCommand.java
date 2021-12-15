package frc.robot.commands;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ElevatorPosition;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorCommand extends CommandBase {
        private final ElevatorSubsystem elevator;
        private final ElevatorPosition position;

    public ElevatorCommand(ElevatorSubsystem elevator, ElevatorPosition position) {
        this.elevator = elevator;
        this.position = position;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (position == ElevatorPosition.stop) {
                elevator.stopElevator();
        }
         else if (position == ElevatorPosition.bottom) {
                    elevator.elevatorBottom();
        }
         else if (position == ElevatorPosition.middle) {
                    elevator.elevatorMid();
        }
         else if (position == ElevatorPosition.up) {
                    elevator.elevatorTop();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
