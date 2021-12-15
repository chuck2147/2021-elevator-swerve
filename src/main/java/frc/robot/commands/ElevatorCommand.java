package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ElevatorPosition;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorCommand extends CommandBase {
        private final ElevatorSubsystem elevator;
        private final ElevatorPosition position;

    public ElevatorCommand(ElevatorSubsystem elevator) {
        this.elevator = elevator;
        this.ElevatorPosition = position;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (position == ElevatorPosition.stop) {
            
        }
        else if (position == ElevatorPosition.bottom) {

        }
        else if (position == ElevatorPosition.middle) {

        }
        else if (position == ElevatorPosition.up) {

        }
    }
}
