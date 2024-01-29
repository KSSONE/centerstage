package org.firstinspires.ftc.teamcode.Test;

import android.icu.text.Transliterator;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp(name = "LinearTest")
public class lineartest extends LinearOpMode {
    public DcMotorEx LL;
    public DcMotorEx LR;
    public DigitalChannel slideLimitSwitch;
    int manual = -1;


    //private DigitalChannel slideLimitSwitch;

    @Override
    public void runOpMode() {
        LL = hardwareMap.get(DcMotorEx.class, "LL");
        //slideLimitSwitch = hardwareMap.get(DigitalChannel.class, "LS");
        LL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LL.setDirection(DcMotor.Direction.REVERSE);


        LR = hardwareMap.get(DcMotorEx.class, "LR");
        LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LR.setDirection(DcMotor.Direction.FORWARD);
        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.y){
               LL.setTargetPosition(2900);
               LR.setTargetPosition(2900);
               LL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
               LR.setPower(0.5);
               LL.setPower(.5);
            }
            if (gamepad1.a) {
                LL.setTargetPosition(10);
                LR.setTargetPosition(10);
                LL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                LR.setPower(.5);
                LL.setPower(.5);
            }

            telemetry.addData("Right Linear positive", LR.getCurrentPosition());
            telemetry.addData("Left Linear positive", LL.getCurrentPosition());
            telemetry.update();

        }
    }
}

