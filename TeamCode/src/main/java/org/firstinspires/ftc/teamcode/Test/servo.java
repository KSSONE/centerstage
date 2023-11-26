package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class servo extends LinearOpMode {
    private Servo claw;

    @Override
    public void runOpMode() {

        // Claw
        claw = hardwareMap.servo.get("claw");


        waitForStart();

        while (opModeIsActive()) {
         claws();
        }

    }


    private void claws() {
        if (gamepad1.x) {
            claw.setPosition(0.5);
            telemetry.addData("Claw Position", "Open");
        } else if (gamepad1.y) {
            claw.setPosition(0);
            telemetry.addData("Claw Position", "Closed");
        }
    }
}
