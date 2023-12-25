package org.firstinspires.ftc.teamcode.FeedForward;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class feedForward extends LinearOpMode {

    // Define your motor variables
    DcMotor leftMotor;
    DcMotor rightMotor;
    // ...

    // Constants for the feedforward control
    final double kV = 0.05; // Velocity constant
    final double kA = 0.01; // Acceleration constant

    // Other variables to track state
    double lastError = 0;
    double lastTime = 0;

    @Override
    public void runOpMode() {
        // Initialize your hardware
        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        // Set motor modes and direction (if needed)
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // ...

        waitForStart();

        while (opModeIsActive()) {
            double targetVelocity = 50.0; // Example target velocity in units/s
            setMotorPower(targetVelocity);

            // Display telemetry
            telemetry.addData("Target Velocity", targetVelocity);
            telemetry.addData("Left Motor Power", leftMotor.getPower());
            telemetry.addData("Right Motor Power", rightMotor.getPower());
            telemetry.addData("Current Velocity", getCurrentVelocity());
            telemetry.update();

            // Other operations or control logic within the loop

            idle();
        }
    }

    // Method to calculate motor power using feedforward control
    public void setMotorPower(double targetVelocity) {
        double currentTime = System.currentTimeMillis() / 1000.0; // Current time in seconds

        // Calculate elapsed time since the last iteration
        double deltaTime = currentTime - lastTime;

        // Calculate acceleration
        double error = targetVelocity - getCurrentVelocity();
        double acceleration = (error - lastError) / deltaTime;

        // Calculate the feedforward term
        double feedforward = kV * targetVelocity + kA * acceleration;

        // Set motor powers
        double leftPower = feedforward;
        double rightPower = feedforward;

        // Set the motor powers accordingly
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);

        // Update state variables for the next iteration
        lastError = error;
        lastTime = currentTime;
    }

    // Method to get the current velocity using encoder readings
    private double getCurrentVelocity() {
        // Get current encoder positions
        int leftEncoder = leftMotor.getCurrentPosition();
        int rightEncoder = rightMotor.getCurrentPosition();

        // Assuming your encoder counts correspond to distance, convert it to velocity
        // Here's a simplified example (considering encoder counts per unit distance):
        double countsPerRevolution = 1440; // Example encoder counts per revolution
        double wheelDiameter = 4.0; // Example wheel diameter in inches
        double gearRatio = 2.0; // Example gear ratio

        double distancePerCount = (wheelDiameter * Math.PI) / (countsPerRevolution * gearRatio);
        double leftVelocity = leftEncoder * distancePerCount / getCurrentTimeInSeconds();
        double rightVelocity = rightEncoder * distancePerCount / getCurrentTimeInSeconds();

        // Average the velocities for tank drive
        return (leftVelocity + rightVelocity) / 2.0;
    }

    // Method to get the current time in seconds
    private double getCurrentTimeInSeconds() {
        return System.currentTimeMillis() / 1000.0;
    }

    // Other methods and functions for robot control
}

