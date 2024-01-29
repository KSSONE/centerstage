package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
<<<<<<< HEAD
<<<<<<< HEAD
//@TeleOp(name= "launcher")
=======
@Disabled
@TeleOp(name= "Servo")
>>>>>>> 5cd8ae410786cfbc1f0e08f5ee04e97f8d06e9e7
=======
@TeleOp(name= "launcher")
>>>>>>> parent of 5cd8ae4 (jovi code)
public class servo extends LinearOpMode {
    private Servo claw, clawa;

    @Override
    public void runOpMode() {

        // Claw
        claw = hardwareMap.servo.get("claw");
        clawa = hardwareMap.servo.get("clawa");


        waitForStart();

        while (opModeIsActive()) {
         claws();
        }

    }


    private void claws() {
        if (gamepad1.x) {
            claw.setPosition(0.3);
            telemetry.addData("Claw Position", "Open");
        }
        else if (gamepad1.y) {
            claw.setPosition(0);
            clawa.setPosition(0);
            telemetry.addData("Claw Position", "Closed");
        }
        else if (gamepad1.a) {
            claw.setPosition(1000);
            clawa.setPosition(-1000);
        }
        else if (gamepad1.b) {
            claw.setPosition(-1000);
            clawa.setPosition(1000);
        }
        telemetry.addData("Servo Position", "%.2f", claw.getPosition());

        telemetry.update();
    }
}
