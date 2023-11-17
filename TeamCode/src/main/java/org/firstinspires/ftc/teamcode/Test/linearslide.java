package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
public class linearslide extends LinearOpMode {
    private DcMotor motor;

    @Override
    public void runOpMode() {
        // Initialize the motor
        motor = hardwareMap.get(DcMotor.class, "motor");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                if (motor.getCurrentPosition() < 5 * 1120) { // encoder resolution
                    motor.setPower(0.5);
                } else {
                    motor.setPower(0.0);
                }
            }
            else if (gamepad1.x) {
                if (motor.getCurrentPosition() > 0) {
                    motor.setPower(-0.5);
                } else {
                    motor.setPower(0.0);
                }
            } else {
                motor.setPower(0.0);
            }
            telemetry.addData("Motor Position", motor.getCurrentPosition());
        }
    }
}
