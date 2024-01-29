package org.firstinspires.ftc.teamcode.Helpers;
// Import necessary FTC classes
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

// Define your op mode
public class OdometryMovement extends LinearOpMode {

    // Define your hardware variables
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    // Add other motors and encoders as needed

    // Constants for odometry calculations
    final double WHEEL_DIAMETER = 4.0; // Diameter of the wheels in inches
    final double TICKS_PER_REV = 1440.0; // Encoder ticks per revolution
    final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER; // Circumference of the wheel

    // Calculate encoder ticks per inch
    final double TICKS_PER_INCH = TICKS_PER_REV / WHEEL_CIRCUMFERENCE;

    @Override
    public void runOpMode() {
        // Initialize and map hardware devices
        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");
        // Initialize other motors and encoders

        // Set motor directions if needed
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        // Set other motors and encoders direction

        // Reset encoder values
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Reset other motor encoders

        // Set encoder mode
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Set other motors to run using encoders

        // Wait for the start button to be pressed
        waitForStart();

        // Read encoder values for left, right, and middle wheels
        int leftEncoderTicks = leftMotor.getCurrentPosition();
        int rightEncoderTicks = rightMotor.getCurrentPosition();
        // Read other encoder values if available

        // Calculate distances traveled by each wheel
        double leftDistance = leftEncoderTicks / TICKS_PER_INCH;
        double rightDistance = rightEncoderTicks / TICKS_PER_INCH;
        // Calculate middle distance if using a middle encoder

        // Calculate robot's overall displacement
        double robotDisplacement = (leftDistance + rightDistance) / 2.0;

        // Implement movement logic based on calculated distances

        // Move 4 inches forward
        moveForward(4);

        // Move 2 inches left
        moveLeft(2);

        // Move 2 inches back
        moveBack(2);
    }

    // Helper methods for movement actions
    void moveForward(double distance) {
        int targetTicks = (int) (distance * TICKS_PER_INCH);

        // Assuming you have properly configured and initialized motors
        leftMotor.setTargetPosition(targetTicks);
        rightMotor.setTargetPosition(targetTicks);

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(0.5);
        rightMotor.setPower(0.5);

        while (leftMotor.isBusy() && rightMotor.isBusy()) {
            // Update position here using odometry if needed
        }

        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    void moveLeft(double distance) {
        // Similar implementation to moveForward, adjusting motor powers for lateral movement
    }

    void moveBack(double distance) {
        // Similar implementation to moveForward, adjusting motor powers and direction for backward movement
    }
}
