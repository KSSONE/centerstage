package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;



@TeleOp(name= "Servotest")
public class Servotest extends LinearOpMode {
    private Servo claw;

    @Override
    public void runOpMode() {
        claw = hardwareMap.servo.get("launcher");
        double pos = 0.4;


        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.y){
                pos += 0.1;
                sleep(300);
            }
            else if (gamepad1.a){
                pos -= 0.1;
                sleep(300);
            }
            else if (gamepad1.x){
                pos += 0.01;
                sleep(300);
            }
            else if (gamepad1.b){
                pos -= 0.01;
                sleep(300);
            }

            claw.setPosition(pos);

            /*if (gamepad1.y) {
                claw.setPosition(0.61);
            } else if (gamepad1.a) {
                claw.setPosition(0.1);
            }*/

                telemetry.addData("Servo Position", "%.2f", claw.getPosition());

                telemetry.update();
            }

        }


    }