package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class TeleOpMode extends LinearOpMode {

    private DcMotor FLeft, FRight, BLeft, BRight;
    private DcMotor slide;

    private Servo claw;

    @Override
    public void runOpMode() {
        // Wheels
        FLeft = hardwareMap.dcMotor.get("FLeft");
        FRight = hardwareMap.dcMotor.get("FRight");
        BLeft = hardwareMap.dcMotor.get("BLeft");
        BRight = hardwareMap.dcMotor.get("BRight");

        // Slide
        slide = hardwareMap.dcMotor.get("slide");

        // Claw
        claw = hardwareMap.servo.get("claw");

        // Direction
        FLeft.setDirection(DcMotor.Direction.REVERSE);
        BLeft.setDirection(DcMotor.Direction.REVERSE);
        FRight.setDirection(DcMotor.Direction.FORWARD);
        BRight.setDirection(DcMotor.Direction.FORWARD);

        // Wheels
        FLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Slide
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            driveRobot();
            linearslide();
        }

        stopMotors();
    }

    private void driveRobot() {
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        double frontLeftPower = drive + strafe + rotate;
        double frontRightPower = drive - strafe - rotate;
        double rearLeftPower = drive - strafe + rotate;
        double rearRightPower = drive + strafe - rotate;

        double maxPower = Math.max(
                Math.abs(frontLeftPower),
                Math.max(Math.abs(frontRightPower),
                        Math.max(Math.abs(rearLeftPower), Math.abs(rearRightPower))));

        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            rearLeftPower /= maxPower;
            rearRightPower /= maxPower;
        }

        FLeft.setPower(frontLeftPower);
        FRight.setPower(frontRightPower);
        BLeft.setPower(rearLeftPower);
        BRight.setPower(rearRightPower);

        sleep(20);
    }

    private void stopMotors() {
        FLeft.setPower(0);
        FRight.setPower(0);
        BLeft.setPower(0);
        BRight.setPower(0);
    }
    private void linearslide() {
        if (gamepad1.a) {
            if (slide.getCurrentPosition() < 3700) { // encoder resolution
                slide.setPower(0.2);
            } else {
                slide.setPower(0.0);
            }
        }
        else if (gamepad1.b) {
            if (slide.getCurrentPosition() > 0) { //!slideLimitSwitch.getState()
                slide.setPower(-0.2);
            } else {
                slide.setPower(0.0);
            }
        }
        else {
            slide.setPower(0.0);
        }
        telemetry.addData("Motor Position", slide.getCurrentPosition());
    }

    private void claws() {
        if (gamepad1.x) {
            claw.setPosition(0.5);
        } else if (gamepad1.y) {
            claw.setPosition(0);
        }
    }
}
