package org.firstinspires.ftc.teamcode.Main.Auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

@Autonomous(name = "TOPRED")

public class TOPRED extends LinearOpMode {

    Servo box, side;
    DcMotorEx LL, LR;
    boolean USE_WEBCAM;
    TfodProcessor myTfodProcessor;
    VisionPortal myVisionPortal;

    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {

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



        USE_WEBCAM = true;

        initTfod();
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();


        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                int location = 3000;
                telemetryTfod();
                location = telemetryTfodmain();
                telemetry.addData("Location", location);
                telemetry.update();
                TrajectorySequence left = drive.trajectorySequenceBuilder(startPose)
                        .addTemporalMarker(0, () -> {
                            boxclose();
                            hold();
                        })
                        .forward(28)
                        .strafeLeft(4)
                        .addTemporalMarker(2.8, () -> {
                            drop();
                        })
                        .strafeRight(10)
                        .turn(Math.toRadians(-90))
                        .addTemporalMarker(5, () -> {
                            linearup();
                        })
                        .forward(35)
                        .strafeLeft(6)
                        .addTemporalMarker(11, () -> {
                            boxopen();
                        })
                        .addTemporalMarker(18, () -> {
                            lineardown();
                        })
                        .build();
                TrajectorySequence center = drive.trajectorySequenceBuilder(startPose)
                        .addTemporalMarker(0, () -> {
                            boxclose();
                            hold();
                        })
                        .forward(29)
                        .turn(Math.toRadians(-90))
                        .addTemporalMarker(3.2, () -> {
                            drop();
                            linearup();
                        })
                        .forward(40)
                        .addTemporalMarker(10.5, () -> {
                            boxopen();
                        })
                        .addTemporalMarker(17, () -> {
                            lineardown();
                        })

                        .build();
                TrajectorySequence right = drive.trajectorySequenceBuilder(startPose)
                        .addTemporalMarker(0, () -> {
                            boxclose();
                            hold();
                        })
                        .strafeRight(21)
                        .forward(29)
                        .turn(Math.toRadians(-90))
                        .addTemporalMarker(3.2, () -> {
                            drop();
                            linearup();
                        })
                        .forward(20)
                        .strafeRight(10)
                        .addTemporalMarker(10.5, () -> {
                            boxopen();
                        })
                        .addTemporalMarker(17, () -> {
                            lineardown();
                        })

                        .build();

                if (location == 1){
                    drive.followTrajectorySequence(left);
                }
                else if (location == 2){
                    drive.followTrajectorySequence(center);

                } else if (location == 3){
                    drive.followTrajectorySequence(right);
                }
                sleep(300000);

            }

        }
    }

    /**
     * Initialize TensorFlow Object Detection.
     */
    private void initTfod() {
        TfodProcessor.Builder myTfodProcessorBuilder;
        VisionPortal.Builder myVisionPortalBuilder;

        // First, create a TfodProcessor.Builder.

        myTfodProcessorBuilder = new TfodProcessor.Builder();
        // Set the name of the file where the model can be found.
        myTfodProcessorBuilder.setModelFileName("Red_Block.tflite");
        // Set the full ordered list of labels the model is trained to recognize.
        myTfodProcessorBuilder.setModelLabels(JavaUtil.createListWith("Red Block"));
        // Set the aspect ratio for the images used when the model was created.
        myTfodProcessorBuilder.setModelAspectRatio(16 / 9);
        // Create a TfodProcessor by calling build.
        myTfodProcessor = myTfodProcessorBuilder.build();

        // Next, create a VisionPortal.Builder and set attributes related to the camera.
        myVisionPortalBuilder = new VisionPortal.Builder();
        if (USE_WEBCAM) {
            // Use a webcam.
            myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            // Use the device's back camera.
            myVisionPortalBuilder.setCamera(BuiltinCameraDirection.BACK);
        }
        // Add myTfodProcessor to the VisionPortal.Builder.
        myVisionPortalBuilder.addProcessor(myTfodProcessor);
        // Create a VisionPortal by calling build.
        myVisionPortal = myVisionPortalBuilder.build();


    }

    /**
     * Display info (using telemetry) for a detected object
     */
    private void telemetryTfod() {
        List<Recognition> myTfodRecognitions;
        Recognition myTfodRecognition;
        float x;
        float y;

        // Get a list of recognitions from TFOD.
        myTfodRecognitions = myTfodProcessor.getRecognitions();

        telemetry.addData("# Objects Detected", JavaUtil.listLength(myTfodRecognitions));
        // Iterate through list and call a function to
        // display info for each recognized object.
        for (Recognition myTfodRecognition_item : myTfodRecognitions) {
            myTfodRecognition = myTfodRecognition_item;
            // Display info about the recognition.
            telemetry.addLine("");
            // Display label and confidence.
            // Display the label and confidence for the recognition.
            telemetry.addData("Image", myTfodRecognition.getLabel() + " (" + JavaUtil.formatNumber(myTfodRecognition.getConfidence() * 100, 0) + " % Conf.)");
            // Display position.
            x = (myTfodRecognition.getLeft() + myTfodRecognition.getRight()) / 2;
            y = (myTfodRecognition.getTop() + myTfodRecognition.getBottom()) / 2;
            // Display the position of the center of the detection boundary for the recognition
            telemetry.addData("- Position", JavaUtil.formatNumber(x, 0) + ", " + JavaUtil.formatNumber(y, 0));
            // Display size
            // Display the size of detection boundary for the recognition
            telemetry.addData("- Size", JavaUtil.formatNumber(myTfodRecognition.getWidth(), 0) + " x " + JavaUtil.formatNumber(myTfodRecognition.getHeight(), 0));
        }
    }




    private int telemetryTfodmain() {

        List<Recognition> myTfodRecognitions = myTfodProcessor.getRecognitions();

        int detectionBool = JavaUtil.listLength(myTfodRecognitions);
        telemetry.addData("Item numbers", myTfodRecognitions);
        telemetry.update();
        int position = 0;
        if (detectionBool != 0) {
            for (Recognition recognition : myTfodRecognitions) {

                float x = x = (recognition.getLeft() + recognition.getRight()) / 2;

                // Put ranges for x and y coordinates
                double xMaxRangec = 580; // Your minimum x value
                double xMinRangec = 250; // Your maximum x value

                // second
                double xMaxRangel = 249; // Your minimum x value
                double xMinRangel = 40; // Your maximum x value


                if (x <= xMaxRangec && x >= xMinRangec) {
                    position = 2;
                    telemetry.addData("Object is in the centre", "");
                    telemetry.addData("X:", x);
                    telemetry.update();
                }
                else if (x <= xMaxRangel && x >= xMinRangel) {
                    position = 1;
                    telemetry.addData("Object is in the left", "");
                    telemetry.addData("X:", x);
                    telemetry.update();
                }



            }
        } else {
            position = 3;
            telemetry.addData("Object is in the right", "");
            telemetry.update();

        }

        return position;
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