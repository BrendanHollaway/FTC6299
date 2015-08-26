package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.helperBuffers_4211Libs.BufferSupportedRunnable;
import com.qualcomm.ftcrobotcontroller.helperBuffers_4211Libs.BufferedInterpolatedMotor;
import com.qualcomm.ftcrobotcontroller.helperBuffers_4211Libs.BufferedMotor;
import com.qualcomm.ftcrobotcontroller.helperBuffers_4211Libs.BufferedServo;
import com.qualcomm.ftcrobotcontroller.helperBuffers_4211Libs.BufferedSmartMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by FTC Robot Team 4211 on 8/11/2015.
 */
public class Drive_BufferedRunnable extends BufferSupportedRunnable {

    BufferedInterpolatedMotor driveLeft;
    BufferedInterpolatedMotor driveRight;
    Gamepad localGamepad1;
    Gamepad localGamepad2;
    Object gamepadLock = new Object();

    public Drive_BufferedRunnable(HardwareMap map) {
        super(map);

        driveLeft = BufferedInterpolatedMotor.createBufferedInterpolatedMotor(map, "Left");
        driveLeft.attachEncoder();
        driveLeft.hardwareResetBothAttachedEncodersValues();

        driveRight = BufferedInterpolatedMotor.createBufferedInterpolatedMotor(map, "Right");
        driveRight.attachEncoder();
        driveRight.hardwareResetBothAttachedEncodersValues();

        driveLeft.setBufferMode(BufferedMotor.BUFFER_METHOD.POWER_AND_ENCODER);
        driveLeft.resetEncoderValue();
        driveRight.setBufferMode(BufferedMotor.BUFFER_METHOD.POWER_AND_ENCODER);
        driveRight.resetEncoderValue();

    }

    @Override
    public void newDataReceived() { // NOTE: Not called from this thread.  Called from opMode thread
        super.newDataReceived();
    }

    public void passNewJoystickInfo(Gamepad g1,Gamepad g2) // taking advantage of copy by value to decouple
    {
        synchronized (gamepadLock) { // ensure no collisions
            localGamepad1 = g1;
            localGamepad2 = g2;
        }
        newDataReceived(); // call b/c new data to use
    }

    @Override
    public void run() {
        while (true) // perfectly valid to put infinite loop here or can run linear
        {
            double left;
            double right;
            synchronized (gamepadLock)//get joystick values safely
            {
                left = localGamepad1.left_stick_y;
                right = localGamepad1.right_stick_y;
            }

            if (Math.abs(left)<0.07) // deadband
            {
                left=0;
            }
            if (Math.abs(right)<0.07) // deadband
            {
                right=0;
            }

            driveLeft.setPower(left);
            driveRight.setPower(right);

            newDataWait(5);
        }
    }
}
