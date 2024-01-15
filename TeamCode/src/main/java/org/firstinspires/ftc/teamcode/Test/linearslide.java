package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Jovi's Tele")
public class linearslide extends LinearOpMode {
    private DcMotorEx linearmotor; // right
    private DcMotorEx linearmotor2; // left
    private DcMotorEx frontright;
    private DcMotorEx frontleft;
    private DcMotorEx backright;
    private DcMotorEx backleft;
    private DcMotorEx intake;
    private Servo claw;
    //private DigitalChannel slideLimitSwitch;

    @Override
    public void runOpMode() {
        linearmotor = hardwareMap.get(DcMotorEx.class, "motor");
        //slideLimitSwitch = hardwareMap.get(DigitalChannel.class, "slideLimitSwitch");
        linearmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearmotor2 = hardwareMap.get(DcMotorEx.class, "motor2");
        /*frontright = hardwareMap.get(DcMotorEx.class, "frontright");
        frontleft = hardwareMap.get(DcMotorEx.class, "frontleft");
        backright = hardwareMap.get(DcMotorEx.class, "backright");
        backleft = hardwareMap.get(DcMotorEx.class, "backleft");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        claw = hardwareMap.get(Servo.class, "Servo");*/

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                linearmotor.setPower(-.666);
            }

        }
    }
}
