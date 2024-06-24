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
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

/*
 * Op mode for preliminary tuning of the follower PID coefficients (located in the drive base
 * classes). The robot drives in a DISTANCE-by-DISTANCE square indefinitely. Utilization of the
 * dashboard is recommended for this tuning routine. To access the dashboard, connect your computer
 * to the RC's WiFi network. In your browser, navigate to https://192.168.49.1:8080/dash if you're
 * using the RC phone or https://192.168.43.1:8080/dash if you are using the Control Hub. Once
 * you've successfully connected, start the program, and your robot will begin driving in a square.
 * You should observe the target position (green) and your pose estimate (blue) and adjust your
 * follower PID coefficients such that you follow the target position as accurately as possible.
 * If you are using SampleMecanumDrive, you should be tuning TRANSLATIONAL_PID and HEADING_PID.
 * If you are using SampleTankDrive, you should be tuning AXIAL_PID, CROSS_TRACK_PID, and HEADING_PID.
 * These coefficients can be tuned live in dashboard.
 */
@Config
@Autonomous(group = "PATH")
public class PATHMAKER extends LinearOpMode {
    Servo box, side;
    DcMotorEx LL, LR;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(128, 74, 180);

        drive.setPoseEstimate(startPose);


        LL = hardwareMap.get(DcMotorEx.class, "LL");
        LL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LL.setDirection(DcMotor.Direction.REVERSE);


        LR = hardwareMap.get(DcMotorEx.class, "LR");
        LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LR.setDirection(DcMotor.Direction.FORWARD);
        // Declare our servo

        box = hardwareMap.get(Servo.class,"box");
        side = hardwareMap.get(Servo.class,"drop");

        waitForStart();

        if (isStopRequested()) return;

        while (!isStopRequested()) {
            TrajectorySequence right = drive.trajectorySequenceBuilder(startPose)
                    .addTemporalMarker(0, () -> {
                        boxclose();
                        hold();
                    })
                    .forward(29)
                    .strafeRight(23)
                    .turn(Math.toRadians(-90))
                    .addTemporalMarker(3.2, () -> {
                        drop();
                        linearup();
                    })
                    .forward(17)
                    .strafeRight(10)
                    .addTemporalMarker(10.5, () -> {
                        boxopen();
                    })
                    .addTemporalMarker(17, () -> {
                        lineardown();
                    })

                    .build();
            drive.followTrajectorySequence(right);
            sleep(30000);
        }
    }
    void linearup(){
        LL.setTargetPosition(1800);
        LR.setTargetPosition(1800);
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
