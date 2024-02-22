package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.Base64;


@Disabled

@Autonomous

public class  autoWithEncoders extends LinearOpMode {
    
    private DcMotor frontLeft, frontRight, rearLeft, rearRight;
    private OpenCvCamera camera;


    private static final double COUNTS_PER_MOTOR_REV = 537.7; // Replace with your motor's encoder counts per revolution
    private static final double WHEEL_DIAMETER_INCHES = 3.78; // Diameter of the wheel in inches
    private static final double COUNTS_PER_INCH = COUNTS_PER_MOTOR_REV / (Math.PI * WHEEL_DIAMETER_INCHES);

    @Override
    public void runOpMode() {


        // Initialize motors
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        rearLeft = hardwareMap.dcMotor.get("rearLeft");
        rearRight = hardwareMap.dcMotor.get("rearRight");

        // Set motor directions
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        rearLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        rearRight.setDirection(DcMotor.Direction.FORWARD);

        // Reset encoders
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motor mode to run using encoders
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        // Move forward for 24 inches using encoders
        encoderDrive(0.5, 24, 24, 5);

        // Move backward for 12 inches using encoders
        encoderDrive(0.5, -12, -12, 5);

        // Turn left for 12 inches using encoders
        encoderDrive(0.5, -12, 12, 5);

        // Turn right for 12 inches using encoders
        encoderDrive(0.5, 12, -12, 5);
    }

    // Your encoderDrive method here
    public void encoderDrive(double speed, double leftInches, double rightInches, double timeout) {
        // Existing encoderDrive implementation
    }
}

