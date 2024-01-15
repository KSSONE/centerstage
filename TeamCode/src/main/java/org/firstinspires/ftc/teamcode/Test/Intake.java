package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Intake")
public class Intake extends LinearOpMode {
    DcMotorEx intake;



    @Override
    public void runOpMode() {

        intake = hardwareMap.get(DcMotorEx.class,"intake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setDirection(DcMotorEx.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.y){
                intake.setPower(0.25);
            }
            else if (gamepad1.b){
                intake.setPower(0.5);
            }
            else if (gamepad1.a){
                intake.setPower(0.75);
            }
            else if (gamepad1.x){
                intake.setPower(1);
            }
            else {
                intake.setPower(0);
            }





        }
    }

}