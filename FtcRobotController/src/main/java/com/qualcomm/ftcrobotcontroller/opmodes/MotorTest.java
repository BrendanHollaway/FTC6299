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

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftcrobotcontroller.helperBuffers_4211Libs.BufferSupportedRunnable;
import com.qualcomm.ftcrobotcontroller.helperBuffers_4211Libs.BufferedDeviceManager;
import com.qualcomm.ftcrobotcontroller.helperBuffers_4211Libs.BufferedIrSeekerSensor;
import com.qualcomm.ftcrobotcontroller.helperBuffers_4211Libs.BufferedMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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
public class MotorTest extends OpMode {

  final static double MOTOR_POWER = 0.15; // Higher values will cause the robot to move faster

  final static double HOLD_IR_SIGNAL_STRENGTH = 0.20; // Higher values will cause the robot to follow closer

    Thread t;
    Thread t2;
    SimpleAutoTest runnable;
    SimpleAutoTest runnable2;
    int prediction=0;

  public MotorTest() {
  }

  @Override
  public void start() {
      runnable = new SimpleAutoTest(hardwareMap);
      runnable2 = new SimpleAutoTest(hardwareMap);
      t = new Thread(runnable);
      t.start();
  }

  @Override
  public void loop() {
      runnable.bufferedDeviceManager.swapInputBuffers();
      telemetry.addData("Position",runnable.motorRight.getCurrentPosition()+" "+prediction);
      telemetry.addData("DT",runnable.motorRight.getAverageDtInterval());
      telemetry.addData("Error",runnable.motorRight.getAveragePredictedValueError()+" "+(prediction-runnable.motorRight.getCurrentPosition()));
      telemetry.addData("Predicted Power",runnable.motorRight.getPredictedPowerToReachPositionDtFromNow(-200,runnable.motorRight.getAverageDtInterval()));
      prediction=runnable.motorRight.getPredictedEncoderValue(runnable.motorRight.getAverageDtInterval());
      runnable.bufferedDeviceManager.swapOutputBuffers();
  }

  @Override
  public void stop() {
      t.interrupt();
    // no action needed
  }
}
