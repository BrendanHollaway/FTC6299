package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.EventLoopManager;

/**
 * Created by Joshua on 7/29/2015.
 */
public class AutonomousThread implements Runnable{
    DcMotor motorBL;
    DcMotor motorBR;
    DcMotor motorFL;
    DcMotor motorFR;

    private void setSpeed(int left, int right) {
        motorBL.setPower(left);
        motorFL.setPower(left);
        motorBR.setPower(right);
        motorFR.setPower(right);
    }

    private void stop() {
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }



    public void run() {
        setSpeed(80, 80);
        try {
            Thread.sleep(1000);
        }

        catch (InterruptedException ex) {
            System.out.println("halp");
        }
        stop();
        setSpeed(-80, -80);
        try {
            Thread.sleep(1000);
        }

        catch (InterruptedException ex) {
            System.out.println("halp");
        }
        stop();
    }
}
