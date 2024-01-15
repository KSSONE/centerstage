package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class motorTest extends LinearOpMode {
    DcMotorEx rightFront, leftRear, rightRear, leftfront;


    @Override
    public void runOpMode() {

        rightFront = hardwareMap.get(DcMotorEx.class,"right_front");
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftRear = hardwareMap.get(DcMotorEx.class,"left_rear");
        leftRear.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        rightRear = hardwareMap.get(DcMotorEx.class,"right_rear");
        rightRear.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        leftfront = hardwareMap.get(DcMotorEx.class,"left_front");
        leftfront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Directon
        leftfront.setDirection(DcMotorEx.Direction.FORWARD);
        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
        leftRear.setDirection(DcMotorEx.Direction.FORWARD);
        rightRear.setDirection(DcMotorEx.Direction.FORWARD);


        waitForStart();
        while (opModeIsActive()) {
            leftfront.setPower(0.1);
            rightFront.setPower(0.1);
            leftRear.setPower(0.1);
            rightRear.setPower(0.1);


        }
    }

}
