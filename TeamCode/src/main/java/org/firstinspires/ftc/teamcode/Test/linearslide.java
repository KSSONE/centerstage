package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
public class linearslide extends LinearOpMode {
    private DcMotor motor;
    //private DigitalChannel slideLimitSwitch;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotor.class, "motor");
        //slideLimitSwitch = hardwareMap.get(DigitalChannel.class, "slideLimitSwitch");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                if (motor.getCurrentPosition() < 3700) { // encoder resolution
                    motor.setPower(0.2);
                } else {
                    motor.setPower(0.0);
                }
            }
            else if (gamepad1.b) {
                if (motor.getCurrentPosition() > 0) { //!slideLimitSwitch.getState()
                    motor.setPower(-0.2);
                } else {
                    motor.setPower(0.0);
                }
            }
            else {
                motor.setPower(0.0);
            }
            telemetry.addData("Motor Position", motor.getCurrentPosition());
        }
    }
}
