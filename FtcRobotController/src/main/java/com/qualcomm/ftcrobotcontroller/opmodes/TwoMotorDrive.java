package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.EventLoopManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 * QuadX 6299
 */
public class TwoMotorDrive extends OpMode{

    DcMotor motorBL;
    DcMotor motorBR;


    public TwoMotorDrive(){
    }

    @Override
    public void start() {
        motorBL = hardwareMap.dcMotor.get("motor_1");
        motorBR = hardwareMap.dcMotor.get("motor_2");
    }

    @Override
    public void loop() {
        if (Math.abs(gamepad1.left_stick_y) > .1 || Math.abs(gamepad1.right_stick_y) > .1) {
            motorBL.setPower(-gamepad1.left_stick_y);
            motorBR.setPower(gamepad1.right_stick_y);
        }

        else {
            motorBL.setPower(0);
            motorBR.setPower(0);
        }

        telemetry.addData("Voltage", EventLoopManager.ROBOT_BATTERY_LEVEL_KEY);


    }

    @Override
    public void stop() {
    }
}
