package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


@Disabled

@Autonomous(name="movi")

public class Movement extends LinearOpMode {
    DcMotorEx rightFront, leftRear, rightRear, leftFront;
    @Override
    public void runOpMode(){

        rightFront = hardwareMap.get(DcMotorEx.class,"right_front");
        rightFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftRear = hardwareMap.get(DcMotorEx.class,"left_rear");
        leftRear.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightRear = hardwareMap.get(DcMotorEx.class,"right_rear");
        rightRear.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFront = hardwareMap.get(DcMotorEx.class,"left_front");
        leftFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Directon
        leftFront.setDirection(DcMotorEx.Direction.FORWARD);
        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
        leftRear.setDirection(DcMotorEx.Direction.FORWARD);
        rightRear.setDirection(DcMotorEx.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()){

            /*forward(71,0.7);
            sleep(100);
            turn_right(1060,0.3);
            sleep(100);
            backward(68,0.7);*/

            forward(152,0.7);
            sleep(100);
            turn_right(1060,0.3);
            sleep(100);
            backward(202,0.7);
            sleep(100);
            Strafe_right(100,0.5);
            sleep(30000);
        }

    }

    public void forward(int cm, double speed){

        int distance = (cm*538)/30;
        int lf = leftFront.getCurrentPosition() + distance;
        int lr = leftRear.getCurrentPosition() + distance;
        int rf = rightFront.getCurrentPosition() + distance;
        int rr = rightRear.getCurrentPosition() + distance;

        telemetry.addData("Target: ", lf);

        leftFront.setTargetPosition(lf);
        leftRear.setTargetPosition(lr);
        rightFront.setTargetPosition(rf);
        rightRear.setTargetPosition(rr);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(speed);
        leftRear.setPower(speed);
        rightFront.setPower(speed);
        rightRear.setPower(speed);

        while  ((leftRear.getCurrentPosition()!= lr) && (leftFront.getCurrentPosition()!=lf) && (rightRear.getCurrentPosition()!= rr) && (rightFront.getCurrentPosition()!=rf)){
            telemetry.addData("DF: ", cm);
            telemetry.addData("LF: ",leftFront.getCurrentPosition());
            telemetry.addData("LR: ", leftRear.getCurrentPosition());
            telemetry.addData("RF: ", rightFront.getCurrentPosition());
            telemetry.addData("RR: ", rightRear.getCurrentPosition());
            telemetry.update();
        }
    }


    public void backward(int cm, double speed){
        telemetry.addData("DB: ", cm);
        telemetry.addData("LF: ",leftFront.getCurrentPosition());
        telemetry.addData("LR: ", leftRear.getCurrentPosition());
        telemetry.addData("RF: ", rightFront.getCurrentPosition());
        telemetry.addData("RR: ", rightRear.getCurrentPosition());

        int distance = (cm*538)/30;

        int lf = leftFront.getCurrentPosition() - distance;
        int lr = leftRear.getCurrentPosition() - distance;
        int rf = rightFront.getCurrentPosition() - distance;
        int rr = rightRear.getCurrentPosition() - distance;

        telemetry.addData("Target: ", lf);

        leftFront.setTargetPosition(lf);
        leftRear.setTargetPosition(lr);
        rightFront.setTargetPosition(rf);
        rightRear.setTargetPosition(rr);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(speed);
        leftRear.setPower(speed);
        rightFront.setPower(speed);
        rightRear.setPower(speed);

        while  ((leftRear.getCurrentPosition()!= lr) && (leftFront.getCurrentPosition()!=lf) && (rightRear.getCurrentPosition()!= rr) && (rightFront.getCurrentPosition()!=rf)){
            telemetry.addData("DF: ", cm);
            telemetry.addData("LF: ",leftFront.getCurrentPosition());
            telemetry.addData("LR: ", leftRear.getCurrentPosition());
            telemetry.addData("RF: ", rightFront.getCurrentPosition());
            telemetry.addData("RR: ", rightRear.getCurrentPosition());
            telemetry.update();
        }
    }
    public void Strafe_right (int cm, double speed) {


        telemetry.addData("DR: ", cm);
        telemetry.addData("LF: ", leftFront.getCurrentPosition());
        telemetry.addData("LR: ", leftRear.getCurrentPosition());
        telemetry.addData("RF: ", rightFront.getCurrentPosition());
        telemetry.addData("RR: ", rightRear.getCurrentPosition());

        int distance = cm*20;

        int lf = leftFront.getCurrentPosition() + distance;
        int lr = leftRear.getCurrentPosition() - distance;
        int rf = rightFront.getCurrentPosition() - distance;
        int rr = rightRear.getCurrentPosition() + distance;

        telemetry.addData("Target: ", lf);

        leftFront.setTargetPosition(lf);
        leftRear.setTargetPosition(lr);
        rightFront.setTargetPosition(rf);
        rightRear.setTargetPosition(rr);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(speed);
        leftRear.setPower(speed);
        rightFront.setPower(speed);
        rightRear.setPower(speed);

        while  ((leftRear.getCurrentPosition()!= lr) && (leftFront.getCurrentPosition()!=lf) && (rightRear.getCurrentPosition()!= rr) && (rightFront.getCurrentPosition()!=rf)){
            telemetry.addData("DF: ", cm);
            telemetry.addData("LF: ",leftFront.getCurrentPosition());
            telemetry.addData("LR: ", leftRear.getCurrentPosition());
            telemetry.addData("RF: ", rightFront.getCurrentPosition());
            telemetry.addData("RR: ", rightRear.getCurrentPosition());
            telemetry.update();
        }
    }
    public void Strafe_left (int cm, double speed) {

        telemetry.addData("DL: ", cm);
        telemetry.addData("LF: ", leftFront.getCurrentPosition());
        telemetry.addData("LR: ", leftRear.getCurrentPosition());
        telemetry.addData("RF: ", rightFront.getCurrentPosition());
        telemetry.addData("RR: ", rightRear.getCurrentPosition());

        int distance = cm*20;

        int lf = leftFront.getCurrentPosition() - distance;
        int lr = leftRear.getCurrentPosition() + distance;
        int rf = rightFront.getCurrentPosition() + distance;
        int rr = rightRear.getCurrentPosition() - distance;

        telemetry.addData("Target: ", lf);

        leftFront.setTargetPosition(lf);
        leftRear.setTargetPosition(lr);
        rightFront.setTargetPosition(rf);
        rightRear.setTargetPosition(rr);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(speed);
        leftRear.setPower(speed);
        rightFront.setPower(speed);
        rightRear.setPower(speed);

        while  ((leftRear.getCurrentPosition()!= lr) && (leftFront.getCurrentPosition()!=lf) && (rightRear.getCurrentPosition()!= rr) && (rightFront.getCurrentPosition()!=rf)){
            telemetry.addData("DF: ", cm);
            telemetry.addData("LF: ",leftFront.getCurrentPosition());
            telemetry.addData("LR: ", leftRear.getCurrentPosition());
            telemetry.addData("RF: ", rightFront.getCurrentPosition());
            telemetry.addData("RR: ", rightRear.getCurrentPosition());
            telemetry.update();
        }
    }
    public void turn_left (int cm, double speed) {

        telemetry.addData("TL: ", cm);
        telemetry.addData("LF: ", leftFront.getCurrentPosition());
        telemetry.addData("LR: ", leftRear.getCurrentPosition());
        telemetry.addData("RF: ", rightFront.getCurrentPosition());
        telemetry.addData("RR: ", rightRear.getCurrentPosition());

        int distance = cm;

        int lf = leftFront.getCurrentPosition() - distance;
        int lr = leftRear.getCurrentPosition() - distance;
        int rf = rightFront.getCurrentPosition() + distance;
        int rr = rightRear.getCurrentPosition() + distance;

        telemetry.addData("Target: ", lf);

        leftFront.setTargetPosition(lf);
        leftRear.setTargetPosition(lr);
        rightFront.setTargetPosition(rf);
        rightRear.setTargetPosition(rr);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(speed);
        leftRear.setPower(speed);
        rightFront.setPower(speed);
        rightRear.setPower(speed);

        while  ((leftRear.getCurrentPosition()!= lr) && (leftFront.getCurrentPosition()!=lf) && (rightRear.getCurrentPosition()!= rr) && (rightFront.getCurrentPosition()!=rf)){
            telemetry.addData("DF: ", cm);
            telemetry.addData("LF: ",leftFront.getCurrentPosition());
            telemetry.addData("LR: ", leftRear.getCurrentPosition());
            telemetry.addData("RF: ", rightFront.getCurrentPosition());
            telemetry.addData("RR: ", rightRear.getCurrentPosition());
            telemetry.update();
        }
    }
    public void turn_right (int cm, double speed) {

        telemetry.addData("TR: ", cm);
        telemetry.addData("LF: ", leftFront.getCurrentPosition());
        telemetry.addData("LR: ", leftRear.getCurrentPosition());
        telemetry.addData("RF: ", rightFront.getCurrentPosition());
        telemetry.addData("RR: ", rightRear.getCurrentPosition());

        int distance = cm;

        int lf = leftFront.getCurrentPosition() + distance;
        int lr = leftRear.getCurrentPosition() + distance;
        int rf = rightFront.getCurrentPosition() - distance;
        int rr = rightRear.getCurrentPosition() - distance;

        telemetry.addData("Target: ", lf);

        leftFront.setTargetPosition(lf);
        leftRear.setTargetPosition(lr);
        rightFront.setTargetPosition(rf);
        rightRear.setTargetPosition(rr);

        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFront.setPower(speed);
        leftRear.setPower(speed);
        rightFront.setPower(speed);
        rightRear.setPower(speed);

        while  ((leftRear.getCurrentPosition()!= lr) && (leftFront.getCurrentPosition()!=lf) && (rightRear.getCurrentPosition()!= rr) && (rightFront.getCurrentPosition()!=rf)){
            telemetry.addData("DF: ", cm);
            telemetry.addData("LF: ",leftFront.getCurrentPosition());
            telemetry.addData("LR: ", leftRear.getCurrentPosition());
            telemetry.addData("RF: ", rightFront.getCurrentPosition());
            telemetry.addData("RR: ", rightRear.getCurrentPosition());
            telemetry.update();
        }
    }
}
