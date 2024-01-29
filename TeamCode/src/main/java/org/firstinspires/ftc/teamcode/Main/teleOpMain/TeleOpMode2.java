package org.firstinspires.ftc.teamcode.Main.teleOpMain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "FinalTeleOp2")
public class TeleOpMode2 extends LinearOpMode {

    Servo claw, angle;
    DcMotorEx intake;
    DcMotorEx rightFront, leftRear, rightRear, leftFront, LL, LR;

    int open = 1;

    public DigitalChannel slideLimitSwitch, slideLimitSwitch1;
    boolean manual= false;
    double pos = 0.41;

    float y;
    float x;
    double rx;

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

        claw = hardwareMap.get(Servo.class,"claw");
        angle = hardwareMap.servo.get("angle");

        // Declare our motors

        //intake
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setDirection(DcMotorEx.Direction.REVERSE);

        // Make sure your ID's match your configuration
        rightFront = hardwareMap.get(DcMotorEx.class, "right_front");
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftRear = hardwareMap.get(DcMotorEx.class, "left_rear");
        leftRear.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        rightRear = hardwareMap.get(DcMotorEx.class, "right_rear");
        rightRear.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        leftFront = hardwareMap.get(DcMotorEx.class, "left_front");
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Directon
        leftFront.setDirection(DcMotorEx.Direction.FORWARD);
        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
        leftRear.setDirection(DcMotorEx.Direction.FORWARD);
        rightRear.setDirection(DcMotorEx.Direction.REVERSE);



        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            mechanumDrive();
            Linearslide();
            Intake();
            claw();
            angle();
            telemetry.update();
        }
    }

    private void mechanumDrive() {
        y = -gamepad1.left_stick_y;
        x = gamepad1.left_stick_x;
        // Counteract imperfect strafing
        rx = gamepad1.right_stick_x * 1.1;

        // Denominator is the largest motor power
        // (absolute value) or 1.
        // This ensures all the powers maintain
        // the same ratio, but only when at least one is
        // out of the range [-1, 1].

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        // Make sure your ID's match your configuration
        leftFront.setPower((((y + x) + rx) / denominator) * ((0.5 * (gamepad1.left_trigger - 1) * (gamepad1.left_trigger - 1) + 0.4) * (0.4 * (gamepad1.right_trigger) * (gamepad1.right_trigger) + 0.6)));
        leftRear.setPower((((y - x) + rx) / denominator) * ((0.5 * (gamepad1.left_trigger - 1) * (gamepad1.left_trigger - 1) + 0.4) * (0.4 * (gamepad1.right_trigger) * (gamepad1.right_trigger) + 0.6)));
        rightFront.setPower((((y - x) - rx) / denominator) * ((0.5 * (gamepad1.left_trigger - 1) * (gamepad1.left_trigger - 1) + 0.4) * (0.4 * (gamepad1.right_trigger) * (gamepad1.right_trigger) + 0.6)));
        rightRear.setPower((((y + x) - rx) / denominator) * ((0.5 * (gamepad1.left_trigger - 1) * (gamepad1.left_trigger - 1) + 0.4) * (0.4 * (gamepad1.right_trigger) * (gamepad1.right_trigger) + 0.6)));

    }

    public void claw() {

            if (gamepad1.b){
                claw.setPosition(0.01);
                telemetry.addData("Claw Position", "Open");
            }
            if (gamepad1.a){
                claw.setPosition(0.03);
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
            angle.setPosition(0.41);
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
        if (gamepad1.y){
            angle.setPosition(0.41);
        }
        else if (gamepad1.x) {
            angle.setPosition(0.31);
        }
        telemetry.addData("Servo angle Position", "%.2f", angle.getPosition());

    }
}