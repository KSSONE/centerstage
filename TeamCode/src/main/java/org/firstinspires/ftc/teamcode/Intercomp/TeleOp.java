package org.firstinspires.ftc.teamcode.Intercomp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous (name = "TeleOp Mode", group = "Linear Opmode")
public class TeleOp extends LinearOpMode {
    public DcMotorEx slide;
    public DigitalChannel slideLimitSwitch;

    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    Servo turn;
    Servo claw;

    @Override
    public void runOpMode() {
        // Initialize the linear slide motor
        slide = hardwareMap.get(DcMotorEx.class, "slide");
        slideLimitSwitch = hardwareMap.get(DigitalChannel.class, "LS");

        // Reset and run using encoder
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide.setDirection(DcMotor.Direction.FORWARD);

        // Initialize the claw servo
        claw = hardwareMap.get(Servo.class, "claw");
        turn = hardwareMap.get(Servo.class, "turn");

        // Initialize the drive motors
        frontLeftMotor = hardwareMap.get(DcMotor.class, "front_left_motor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "front_right_motor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "back_left_motor");
        backRightMotor = hardwareMap.get(DcMotor.class, "back_right_motor");

        // Set motor directions
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            // Reset encoders if limit switch is pressed
            if (slideLimitSwitch.getState()) {
                slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            // Control the linear slide
            controlSlide();

            // Control the Mecanum drive
            controlDrive();

            // Update telemetry
            telemetry.update();
        }
    }

    private void controlSlide() {
        if (gamepad1.dpad_down) {
            slide.setTargetPosition(3000);
            slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slide.setPower(0.7);
        } else if (gamepad1.dpad_up) {
            slide.setTargetPosition(0);
            slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slide.setPower(0.7);
        } else {
            slide.setPower(0);
        }
    }

    private void controlDrive() {
        double y = -gamepad1.left_stick_y; // Forward/backward
        double x = gamepad1.left_stick_x * 1.1; // Strafe
        double rx = gamepad1.right_stick_x; // Rotate

        // Calculate motor powers
        double frontLeftPower = y + x + rx;
        double frontRightPower = y - x - rx;
        double backLeftPower = y - x + rx;
        double backRightPower = y + x - rx;

        // Set motor powers
        frontLeftMotor.setPower(frontLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backLeftMotor.setPower(backLeftPower);
        backRightMotor.setPower(backRightPower);

        // Send telemetry message to signify robot running
        telemetry.addData("Front Left Power", frontLeftPower);
        telemetry.addData("Front Right Power", frontRightPower);
        telemetry.addData("Back Left Power", backLeftPower);
        telemetry.addData("Back Right Power", backRightPower);
    }
    private void controlClaw() {
        if (gamepad1.a) {
            claw.setPosition(0.5); // Open claw
        } else if (gamepad1.b) {
            claw.setPosition(0.0); // Close claw
        }

        if (gamepad1.dpad_left) {
            turn.setPosition(0.0); // 0 degrees
        } else if (gamepad1.dpad_up) {
            turn.setPosition(0.5); // 90 degrees
        } else if (gamepad1.dpad_right) {
            turn.setPosition(1.0); // 180 degrees
        }
    }
}

