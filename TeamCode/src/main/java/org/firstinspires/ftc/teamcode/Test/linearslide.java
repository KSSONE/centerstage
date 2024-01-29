package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Jovi's Tele")

public class linearslide extends LinearOpMode {
    public DcMotorEx LL;
    public DcMotorEx LR;
    public DigitalChannel slideLimitSwitch;
    int manual = -1;


    //private DigitalChannel slideLimitSwitch;

    @Override
    public void runOpMode() {
        LL = hardwareMap.get(DcMotorEx.class, "LL");
        slideLimitSwitch = hardwareMap.get(DigitalChannel.class, "LS");
        LL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LL.setDirection(DcMotor.Direction.FORWARD);


        LR = hardwareMap.get(DcMotorEx.class, "RL");
        LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LR.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad2.back){
                manual = manual * -1;
                sleep (100);
            }
            if (slideLimitSwitch.getState()) {
                LL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            if (gamepad1.dpad_down && manual < 0){
                LL.setTargetPosition(3500);
                LR.setTargetPosition(3500);
                LL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LR.setPower(0.7);
                LL.setPower(0.7);
            }
            else if (gamepad1.dpad_up && manual < 0){
                LL.setTargetPosition(0);
                LR.setTargetPosition(0);
                LL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LR.setPower(0.7);
                LL.setPower(0.7);
            }
            else if (gamepad2.right_bumper && manual > 0){
                LR.setPower(0.7);
                LL.setPower(0.7);
            }
            else if (gamepad1.left_bumper && manual > 0){
                LR.setPower(-0.7);
                LL.setPower(-0.7);
            }
            else {
                LR.setPower(0);
                LL.setPower(0);
            }

        }
    }
}
