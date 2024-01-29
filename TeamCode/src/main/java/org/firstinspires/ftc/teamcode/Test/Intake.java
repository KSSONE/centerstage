package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
<<<<<<< HEAD


=======
@Disabled
@TeleOp(name = "Intake")
>>>>>>> 5cd8ae410786cfbc1f0e08f5ee04e97f8d06e9e7
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