package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.EventLoopManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.AccelerationSensor.Acceleration;



/**
 * QuadX 6299
 */
public class RevTrollbotSense extends OpMode{

    DcMotor motorBL;
    DcMotor motorBR;
    DcMotor motorFL;
    DcMotor motorFR;
    double x = 0, y = 0, z = 0;



    public RevTrollbotSense(){
    }

    @Override
    public void start() {
        motorBL = hardwareMap.dcMotor.get("motor_1");
        motorBR = hardwareMap.dcMotor.get("motor_2");
        motorFL = hardwareMap.dcMotor.get("motor_3");
        motorFR = hardwareMap.dcMotor.get("motor_4");

        Acceleration test = new Acceleration(x, y, z);
    }

    @Override
    public void loop() {
        if (Math.abs(gamepad1.left_stick_y) > .1 || Math.abs(gamepad1.right_stick_y) > .1) {
            motorFL.setPower(-gamepad1.left_stick_y);
            motorBL.setPower(-gamepad1.left_stick_y);
            motorFR.setPower(gamepad1.right_stick_y);
            motorBR.setPower(gamepad1.right_stick_y);
        }

        else {

            motorFL.setPower(0);
            motorFR.setPower(0);
            motorBL.setPower(0);
            motorBR.setPower(0);
        }

        telemetry.addData("Voltage", EventLoopManager.ROBOT_BATTERY_LEVEL_KEY);
        telemetry.addData("distance", String.format("%4d", x) + " " + String.format("%4d", y) + " " + String.format("%4d", z));


    }

    @Override
    public void stop() {
    }
}
