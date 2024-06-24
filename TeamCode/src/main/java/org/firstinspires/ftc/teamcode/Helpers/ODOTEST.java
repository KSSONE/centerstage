package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Autonomous(name = "ODO-TEST")

public class ODOTEST extends LinearOpMode {

    DcMotorEx leftEncoder, rightEncoder, frontEncoder;

    @Override
    public void runOpMode() {
        leftEncoder = hardwareMap.get(DcMotorEx.class, "left_rear");
        rightEncoder = hardwareMap.get(DcMotorEx.class, "right_rear");
        frontEncoder = hardwareMap.get(DcMotorEx.class, "left_front");
        // TODO: reverse any encoders using Encoder.setDirection(Encoder.Direction.REVERSE)
        leftEncoder.setDirection(DcMotorEx.Direction.FORWARD);
        rightEncoder.setDirection(DcMotorEx.Direction.FORWARD);
        frontEncoder.setDirection(DcMotorEx.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("right",rightEncoder.getCurrentPosition());
            telemetry.addData("left",leftEncoder.getCurrentPosition());
            telemetry.addData("front",frontEncoder.getCurrentPosition());
            telemetry.update();
        }
    }
}
