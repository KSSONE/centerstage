package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.util.Encoder;

@Disabled
@TeleOp(name = "TestDead")
public class TestDead extends LinearOpMode {
    private Encoder leftEncoder, rightEncoder, frontEncoder;



    @Override
    public void runOpMode() {
        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "left_rear"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "right_rear"));
        frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "left_front"));
        leftEncoder.setDirection(Encoder.Direction.FORWARD);
        rightEncoder.setDirection(Encoder.Direction.REVERSE);
        frontEncoder.setDirection(Encoder.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {
            int leftPos = leftEncoder.getCurrentPosition();
            int rightPos = rightEncoder.getCurrentPosition();
            int frontPos = frontEncoder.getCurrentPosition();
            telemetry.addData("Left", leftPos);
            telemetry.addData("Right", rightPos);
            telemetry.addData("center", frontPos);
            telemetry.update();




        }
    }

}