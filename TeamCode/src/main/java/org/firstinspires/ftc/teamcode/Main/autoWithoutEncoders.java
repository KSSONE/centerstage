package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
@Autonomous
public class autoWithoutEncoders extends LinearOpMode {


    private DcMotor frontLeft, frontRight, rearLeft, rearRight;

    @Override
    public void runOpMode() {

        waitForStart();

        //Initialize motors from hardware map
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        rearLeft = hardwareMap.dcMotor.get("rearLeft");
        rearRight = hardwareMap.dcMotor.get("rearRight");

        // Set motor directions (adjust as needed based on your robot's configuration)
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        rearLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        rearRight.setDirection(DcMotor.Direction.FORWARD);

        // Set motor modes (RUN_USING_ENCODER if using encoders)
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Wait for the start button to be pressed on the driver station
        waitForStart();

        // Move forward for 3 seconds
        frontLeft.setPower(0.5);
        frontRight.setPower(-0.5);
        rearLeft.setPower(-0.5);
        rearRight.setPower(0.5);

        sleep(3000); // Keep moving for 3 seconds

        // Stop the motors
        frontLeft.setPower(0);
        frontRight.setPower(0);
        rearLeft.setPower(0);
        rearRight.setPower(0);

        sleep(1000);

        // Stop the motors
        frontLeft.setPower(0.5);
        frontRight.setPower(0.5);
        rearLeft.setPower(0.5);
        rearRight.setPower(0.5);

        sleep(4000);

        // Move forward for 3 seconds
        frontLeft.setPower(-0.5);
        frontRight.setPower(0.5);
        rearLeft.setPower(0.5);
        rearRight.setPower(-0.5);

        sleep(3000);

        // Stop the motors
        frontLeft.setPower(0.5);
        frontRight.setPower(0.5);
        rearLeft.setPower(0.5);
        rearRight.setPower(0.5);

        sleep(1000); // Keep moving for 3 seconds

        // Stop the motors
        frontLeft.setPower(0);
        frontRight.setPower(0);
        rearLeft.setPower(0);
        rearRight.setPower(0);
    }

}
