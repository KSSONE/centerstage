package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@Disabled
@Autonomous
public class Odometerytest extends LinearOpMode {


    // Define constants for your robot's measurements
    final double ticksPerRotation = 1440.0;
    final double wheelCircumference = 31.415; // in cm
    final double robotWheelbase = 40.0; // in cm

    // Variables to store current position and orientation
    double robotX = 0;
    double robotY = 0;
    double robotHeading = 0;

    // Initialize hardware components (motors, encoders, IMU)
    DcMotor leftFront, rightFront, leftRear, rightRear;


    // Initialize encoder objects for each motor


    @Override
    public void runOpMode() {
        // Initialize hardware
        leftFront = hardwareMap.dcMotor.get("left_front");
        rightFront = hardwareMap.dcMotor.get("right_front");
        leftRear = hardwareMap.dcMotor.get("left_rear");
        rightRear = hardwareMap.dcMotor.get("right_rear");
        // Initialize encoder objects

        // Set motor directions
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);

        // Wait for the start button to be pressed
        waitForStart();

        while (opModeIsActive()) {
            // Get encoder values from motors
            int leftFrontTicks = leftFront.getCurrentPosition();
            int rightFrontTicks = rightFront.getCurrentPosition();
            int leftRearTicks = leftRear.getCurrentPosition();
            int rightRearTicks = rightRear.getCurrentPosition();

            // Calculate distance traveled by each wheel
            double leftFrontDistance = (leftFrontTicks / ticksPerRotation) * wheelCircumference;
            double rightFrontDistance = (rightFrontTicks / ticksPerRotation) * wheelCircumference;
            double leftRearDistance = (leftRearTicks / ticksPerRotation) * wheelCircumference;
            double rightRearDistance = (rightRearTicks / ticksPerRotation) * wheelCircumference;

            // Calculate average distance moved by the robot
            double distanceMoved = (leftFrontDistance + rightFrontDistance + leftRearDistance + rightRearDistance) / 4.0;

            // Calculate heading change
            double headingChange = (rightFrontDistance - leftFrontDistance + rightRearDistance - leftRearDistance) / (2 * robotWheelbase);

            // Update robot's position and orientation
            robotX += distanceMoved * Math.cos(robotHeading + (headingChange / 2.0));
            robotY += distanceMoved * Math.sin(robotHeading + (headingChange / 2.0));
            robotHeading += headingChange;

            // Update telemetry to display position and orientation
            telemetry.addData("X Position", robotX);
            telemetry.addData("Y Position", robotY);
            telemetry.addData("Heading", robotHeading);
            telemetry.update();
        }
    }
}
