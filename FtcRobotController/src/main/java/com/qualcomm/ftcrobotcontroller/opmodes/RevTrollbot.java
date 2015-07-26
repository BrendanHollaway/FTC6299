package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
/**
 * Created by Joshua on 7/26/2015.
 */
public class RevTrollbot extends OpMode{

    DcMotor motorBL;
    DcMotor motorBR;
    DcMotor motorFL;
    DcMotor motorFR;



    public RevTrollbot(){


    }

    @Override
    public void start() {
        motorBL = hardwareMap.dcMotor.get("motor_1");
        motorBR = hardwareMap.dcMotor.get("motor_2");
        motorFL = hardwareMap.dcMotor.get("motor_3");
        motorFR = hardwareMap.dcMotor.get("motor_4");


    }

    @Override
    public void loop() {
        if (Math.abs(gamepad1.left_stick_y) > .1) {
            motorFL.setPower(Math.abs(gamepad1.left_stick_y) * 100);
            motorBL.setPower(Math.abs(gamepad1.left_stick_y) * 100);
        }

        else if(Math.abs(gamepad1.right_stick_y) > .1) {
            motorFR.setPower(Math.abs(gamepad1.right_stick_y) * 100);
            motorBR.setPower(Math.abs(gamepad1.right_stick_y) * 100);
        }

        else {
            motorFL.setPower(0);
            motorFR.setPower(0);
            motorBL.setPower(0);
            motorBR.setPower(0);
        }

    }

    @Override
    public void stop() {

    }
}
