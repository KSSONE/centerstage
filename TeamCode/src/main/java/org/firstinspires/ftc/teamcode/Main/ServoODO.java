package org.firstinspires.ftc.teamcode.Main;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
@Autonomous(name = "ServoODO")
public class ServoODO extends LinearOpMode {
    Servo claw, angle;
    DcMotorEx LL, LR;

    @Override
    public void runOpMode() throws InterruptedException {

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
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(0, 0, 0);

        drive.setPoseEstimate(startPose);

        waitForStart();

        if (isStopRequested()) return;

        while (!isStopRequested()) {
            TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                    .addTemporalMarker(0, () -> {
                        clawclose();
                        angleclose();
                    })
                    .forward(30)
                    .addTemporalMarker(1, () -> {
                        clawopen();
                        angleopen();
                        linearup();
                    })
                    .addTemporalMarker(2, () -> {
                        clawopen();
                        angleopen();
                    })
                    .back(30)
                    .build();
            drive.followTrajectorySequence(trajSeq);
            sleep(30000);
        }
    }

    void linearup(){
        LL.setTargetPosition(2900);
        LR.setTargetPosition(2900);
        LL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LR.setPower(.5);
        LL.setPower(.5);
    }
    void lineardown(){
        LL.setTargetPosition(5);
        LR.setTargetPosition(5);
        LL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LR.setPower(.5);
        LL.setPower(.5);
    }

    void clawopen(){
        claw.setPosition(0.01);
    }
    void clawclose(){
        claw.setPosition(0.03);
    }
    void angleopen(){
        angle.setPosition(0.41);
    }
    void angleclose(){
        angle.setPosition(0.31);
    }
}