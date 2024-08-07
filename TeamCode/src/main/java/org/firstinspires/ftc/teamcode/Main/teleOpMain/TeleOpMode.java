package org.firstinspires.ftc.teamcode.Main.teleOpMain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "FinalTeleOp")
public class TeleOpMode extends LinearOpMode {

    Servo claw,plane;
    DcMotorEx intake;
    DcMotorEx rightFront, leftRear, rightRear, leftFront, LL, LR;

    int open = 1;

    public DigitalChannel slideLimitSwitch, slideLimitSwitch1;
    boolean manual= false;
    double pos = 0.41;

    public void runOpMode() throws InterruptedException {

        slideLimitSwitch = hardwareMap.get(DigitalChannel.class, "LS1");
        slideLimitSwitch1 = hardwareMap.get(DigitalChannel.class, "LS2");
        LL = hardwareMap.get(DcMotorEx.class, "LL");
        LL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LL.setDirection(DcMotor.Direction.REVERSE);


        LR = hardwareMap.get(DcMotorEx.class, "LR");
        LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LR.setDirection(DcMotor.Direction.FORWARD);
        // Declare our servo

        claw = hardwareMap.get(Servo.class,"box");
        plane = hardwareMap.get(Servo.class,"plane");


        // Declare our motors

        //intake
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setDirection(DcMotorEx.Direction.REVERSE);

        // Make sure your ID's match your configuration
        rightFront = hardwareMap.get(DcMotorEx.class, "right_front");
        rightFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        leftRear = hardwareMap.get(DcMotorEx.class, "left_rear");
        leftRear.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        rightRear = hardwareMap.get(DcMotorEx.class, "right_rear");
        rightRear.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        leftFront = hardwareMap.get(DcMotorEx.class, "left_front");
        leftFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        //Directon
        leftFront.setDirection(DcMotorEx.Direction.FORWARD);
        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
        leftRear.setDirection(DcMotorEx.Direction.FORWARD);
        rightRear.setDirection(DcMotorEx.Direction.REVERSE);

        //Zero Stop
        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);



        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.back) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            leftFront.setPower(frontLeftPower);
            leftRear.setPower(backLeftPower);
            rightFront.setPower(frontRightPower);
            rightRear.setPower(backRightPower);
            Linearslide();
            Intake();
            claw();
            launch();
            //angle();
            telemetry.update();
        }
    }




    public void claw() {

            if (gamepad1.b){
                claw.setPosition(0.8);
                telemetry.addData("Claw Position", "Open");
            }
            if (gamepad1.a){
                claw.setPosition(0.5);
                telemetry.addData("Claw Position", "Close");
            }

        telemetry.addData("Claw Position", "%.2f", claw.getPosition());

    }

    public void Intake() {
        if (gamepad1.right_bumper) {
            intake.setPower(1);
            telemetry.addData("Intake:", "Forward");
            LL.setTargetPosition(50);
            LR.setTargetPosition(50);
            LL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setPower(.5);
            LL.setPower(.5);
        }
        else if (gamepad1.left_bumper){
            intake.setPower(-1);
            telemetry.addData("Intake:", "Reverse");
        }else{
            intake.setPower(0);
            telemetry.addData("Intake:", "Stopped");
        }


    }
    public void Linearslide() {
        /*telemetry.addData("Manual",manual);
        if (gamepad1.x){
            if (manual == false){
                manual = true;
            }
            else if (manual){
                manual = false;
            }
            sleep (150);
        }
        if (slideLimitSwitch.getState() || slideLimitSwitch1.getState()) {
            LL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        if (gamepad1.y && !manual){
            LL.setTargetPosition(3500);
            LR.setTargetPosition(3500);
            LL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setPower(0.7);
            LL.setPower(0.7);
        }
        else if (gamepad1.a && !manual){
            LL.setTargetPosition(-5);
            LR.setTargetPosition(-5);
            LL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setPower(0.7);
            LL.setPower(0.7);
        }
        else if (gamepad1.y && manual && LL.getCurrentPosition() < 3600){
            LR.setPower(0.7);
            LL.setPower(0.7);
        }
        else if (gamepad1.a && manual && (!slideLimitSwitch.getState() || !!slideLimitSwitch1.getState())){
            LR.setPower(-0.7);
            LL.setPower(-0.7);
        }
        else {
            LR.setPower(0);
            LL.setPower(0);
        }*/

        if (slideLimitSwitch.getState() == false || slideLimitSwitch1.getState() == false) {
            LL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


            LL.setTargetPosition(5);
            LR.setTargetPosition(5);
            LL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setPower(.5);
            LL.setPower(.5);
        }


        if (gamepad1.dpad_up){
            LL.setTargetPosition(2900);
            LR.setTargetPosition(2900);
            LL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setPower(.5);
            LL.setPower(.5);
        }
        if (gamepad1.dpad_down) {
            LL.setTargetPosition(-5);
            LR.setTargetPosition(-5);
            LL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setPower(.5);
            LL.setPower(.5);
        }

        telemetry.addData("Right Linear ", LR.getCurrentPosition());
        telemetry.addData("Left Linear ", LL.getCurrentPosition());
        telemetry.addData("LS", slideLimitSwitch1.getState());
        telemetry.addData("LS1", slideLimitSwitch.getState());

    }
    public void angle(){
        if (gamepad2.y){
            pos += 0.1;
            sleep(300);
        }
        else if (gamepad2.a){
            pos -= 0.1;
            sleep(300);
        }
        else if (gamepad2.x){
            pos += 0.01;
            sleep(300);

        }
        else if (gamepad2.b){
            pos -= 0.01;
            sleep(300);
        }

    }
    public void launch(){
        plane.setPosition(0.3);
        if(gamepad1.guide){
            plane.setPosition(0);
        }
    }
}