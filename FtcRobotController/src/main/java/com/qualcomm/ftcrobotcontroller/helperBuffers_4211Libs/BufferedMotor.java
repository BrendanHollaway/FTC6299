/*
 * Copyright (c) 2014 Qualcomm Technologies Inc
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * (subject to the limitations in the disclaimer below) provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of Qualcomm Technologies Inc nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.qualcomm.ftcrobotcontroller.helperBuffers_4211Libs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Follow an IR Beacon
 * <p>
 * How to use: <br>
 * Make sure the Modern Robotics IR beacon is off. <br>
 * Set it to 1200 at 180.  <br>
 * Make sure the side of the beacon with the LED on is facing the robot. <br>
 * Turn on the IR beacon. The robot will now follow the IR beacon. <br>
 * To stop the robot, turn the IR beacon off. <br>
 */
public class BufferedMotor implements PhysicalDeviceBuffer {

    private enum BUFFER_METHOD {
        POWER,
        POWER_AND_ENCODER,
        POWER_ENCODER_AND_TARGET,
        FULL
    }

    protected DcMotor motor;
    protected String motorName;
    protected DcMotorController.RunMode bufferedRunMode = DcMotorController.RunMode.RUN_WITHOUT_ENCODERS;
    protected int bufferedTargetEncoderPosition = 0;
    protected double bufferedPower = 0;
    protected int lastEncoderPosition = 0;
    protected int encoderOffset = 0;
    protected BUFFER_METHOD bufferMode = BUFFER_METHOD.FULL;
    protected Object bufferLock = new Object();


    public BufferedMotor(DcMotor underlyingMotor) {
        this(underlyingMotor, "Default Motor");
    }
    public BufferedMotor(DcMotor underlyingMotor, String cachedName) {
        motor = underlyingMotor;
        motorName = cachedName;
    }

    public synchronized static BufferedMotor createBufferedMotor(HardwareMap map, String name) {
        return new BufferedMotor(map.dcMotor.get(name),name);
    }

    public void setDirection(DcMotor.Direction direction) {
        synchronized (bufferLock) {
            motor.setDirection(direction);
        }
    }

    public DcMotor.Direction getDirection() {
        synchronized (bufferLock) {
            return motor.getDirection();
        }
    }

    public void setBufferMode(BUFFER_METHOD method) {
        synchronized (bufferLock) {
            bufferMode = method;
        }
    }

    public BUFFER_METHOD getBufferMode() {
        synchronized (bufferLock) {
            return bufferMode;
        }
    }

    public void setPower(double power) {
        synchronized (bufferLock) {
            bufferedPower = Math.max(Math.min(power,1),-1);
        }
    }

    public double getPower() {
        synchronized (bufferLock) {
            return bufferedPower;
        }
    }

    public void setTargetPosition(int position) {
        synchronized (bufferLock) {
            bufferedTargetEncoderPosition = position;
        }
    }

    public int getTargetPosition() {
        synchronized (bufferLock) {
            return bufferedTargetEncoderPosition;
        }
    }

    public int getCurrentPosition() {
        synchronized (bufferLock) {
            return lastEncoderPosition-encoderOffset;
        }
    }

    public void resetEncoderValue() {
        synchronized (bufferLock) {
            encoderOffset=lastEncoderPosition;
        }
    }

    public void setChannelMode(DcMotorController.RunMode mode) {
        synchronized (bufferLock) {
            bufferedRunMode = mode;
        }
    }

    public DcMotorController.RunMode getChannelMode() {
        synchronized (bufferLock) {
            return bufferedRunMode;
        }
    }


    @Override
    public void swapInputBuffers() {
        synchronized (bufferLock) {
            switch (bufferMode) {
                case POWER:
                    break;
                case POWER_AND_ENCODER:
                    lastEncoderPosition = motor.getCurrentPosition();
                    break;
                case POWER_ENCODER_AND_TARGET:
                    lastEncoderPosition = motor.getCurrentPosition();
                    break;
                case FULL:
                    lastEncoderPosition = motor.getCurrentPosition();
                    break;
            }
        }
    }

    @Override
    public void swapOutputBuffers() {
        synchronized (bufferLock) {
            switch (bufferMode) {
                case POWER:
                    motor.setPower(bufferedPower);
                    break;
                case POWER_AND_ENCODER:
                    motor.setPower(bufferedPower);
                    break;
                case POWER_ENCODER_AND_TARGET:
                    motor.setPower(bufferedPower);
                    motor.setTargetPosition(bufferedTargetEncoderPosition);
                    break;
                case FULL:
                    motor.setPower(bufferedPower);
                    motor.setTargetPosition(bufferedTargetEncoderPosition);
                    motor.setChannelMode(bufferedRunMode);
                    break;
            }
        }
    }

    @Override
    public Object getUnsafeBase() {
        synchronized (bufferLock) {
            return motor;
        }
    }

    @Override
    public String toString()
    {
        return motorName+": "+bufferMode.toString();
    }
}
