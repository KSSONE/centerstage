package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;




@TeleOp

public class Servoencode extends LinearOpMode {

    private Servo myServo;

    @Override
    public void runOpMode() {
        myServo = hardwareMap.servo.get("servo"); // Replace with your servo name

        waitForStart();

        myServo.setPosition(1.0); // Set servo position to 0 (minimum)
        sleep(2000); // Pause for 2 seconds

        myServo.setPosition(0.0); // Set servo position to 1 (maximum)
        sleep(2000); // Pause for 2 seconds
        

    }
}