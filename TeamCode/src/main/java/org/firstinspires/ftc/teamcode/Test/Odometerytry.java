package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous
public class Odometerytry extends LinearOpMode {
    DcMotorEx rightFront, leftRear, rightRear, leftFront, rightEn, leftEn, frontEn;

    double RADIUS = 0.94488;
    double TICKS = 2000;

    double LATERAL_DISTANCE = 8.5;
    double FORWARD_OFFSET = 4.72;

    double X_MULTI = 1;
    double Y_MULTI = 1;

    double MAX_POWER = 0.8;
    double MAX_ACC = 0.2;

    double PosX = 0;
    double PosY = 0;
    double PosAngle = Math.toRadians(0);

    double lastrightX = 0;
    double lastleftX = 0;

    double lastY = 0;



    //Encoder rightEn, leftEn, frontEn;


    @Override
    public void runOpMode() {

        rightFront = hardwareMap.get(DcMotorEx.class,"right_front");
        leftRear = hardwareMap.get(DcMotorEx.class,"left_rear");
        rightRear = hardwareMap.get(DcMotorEx.class,"right_rear");
        leftFront = hardwareMap.get(DcMotorEx.class,"left_front");
        rightEn = hardwareMap.get(DcMotorEx.class, "right_rear");
        leftEn = hardwareMap.get(DcMotorEx.class, "left_rear");
        frontEn = hardwareMap.get(DcMotorEx.class, "right_front");

        leftFront.setDirection(DcMotorEx.Direction.FORWARD);
        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
        leftRear.setDirection(DcMotorEx.Direction.FORWARD);
        rightRear.setDirection(DcMotorEx.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {

            double leftX = (leftEn.getCurrentPosition() / TICKS) * 2 * RADIUS * Math.PI;
            double rightX = (rightEn.getCurrentPosition() /TICKS) * 2 * RADIUS * Math.PI;
            double Y = (frontEn.getCurrentPosition() / TICKS) * 2 * RADIUS * Math.PI;

            ODOREAD();

            telemetry.addData("X","%.2f", PosX);
            telemetry.addData("Y","%.2f", PosY);
            telemetry.addData("Ang","%.2f", PosAngle);

            telemetry.addData("Front Position","%.2f", Y);
            telemetry.addData("Left Position","%.2f", leftX);
            telemetry.addData("Right Position","%.2f", Math.toDegrees(rightX));

            telemetry.addData("FL P: ", leftFront.getPower());
            telemetry.addData("FR P: ", rightFront.getPower());
            telemetry.addData("RL P: ", leftRear.getPower());
            telemetry.addData("RR P: ", rightRear.getPower());
            telemetry.update();
        }
    }

    public void ODOREAD(){
        double leftX = (leftEn.getCurrentPosition() / TICKS) * 2 * RADIUS * Math.PI;
        double rightX = (rightEn.getCurrentPosition() /TICKS) * 2 * RADIUS * Math.PI;
        double Y = (frontEn.getCurrentPosition() / TICKS) * 2 * RADIUS * Math.PI;

        double currentleftX = leftX- lastleftX;
        double currentrightX = rightX- lastrightX;
        double currentY = leftX- lastY;


        /*if (leftX == rightX){
            if (PosAngle == Math.toRadians(0)){
                PosX = PosX + leftX;
                PosY = PosY + Y;
            }
            else if (PosAngle != Math.toRadians(0)){

            }*/
        double currentang = ((currentleftX-currentrightX)/LATERAL_DISTANCE);
        PosAngle = PosAngle + currentang;
        PosX = PosX +((currentleftX+currentrightX)/2);
        PosY = PosY + (currentY-(FORWARD_OFFSET * currentang));
        }
    }



