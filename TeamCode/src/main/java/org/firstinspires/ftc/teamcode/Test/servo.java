package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name= "Servo")

public class servo extends LinearOpMode {
    private Servo claw;

    @Override
    public void runOpMode() {

        // Claw
        claw = hardwareMap.servo.get("angle");


        waitForStart();

        while (opModeIsActive()) {
         claws();
            telemetry.addData("Servo Position", "%.2f", claw.getPosition());

            telemetry.update();
        }

    }


    private void claws() {
        if (gamepad1.x) {
            claw.setPosition(0.30);

        }
        else if (gamepad1.y) {
            claw.setPosition(0.50);
        }


    }
}
