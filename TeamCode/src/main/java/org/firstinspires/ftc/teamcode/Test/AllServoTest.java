package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;



@TeleOp(name= "AllServoTest")
public class AllServoTest extends LinearOpMode {
    private Servo box,side;

    @Override
    public void runOpMode() {
        box = hardwareMap.servo.get("box");
        side = hardwareMap.servo.get("drop");



        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.y){
                boxopen();
                telemetry.addData("box","Open");
            }
            else if (gamepad1.a){
                boxclose();
                telemetry.addData("box","close");
            }
            if (gamepad1.x){
                drop();
                telemetry.addData("drop","Yes");
            }
            else if (gamepad1.b){
                hold();
                telemetry.addData("Drop","NO");
            }
            telemetry.update();

        }

    }
    void boxopen(){
        box.setPosition(0.8);
    }
    void boxclose(){
        box.setPosition(0.5);
    }
    void drop(){
        side.setPosition(0.05);
    }
    void hold(){
        side.setPosition(0.1);
    }


}