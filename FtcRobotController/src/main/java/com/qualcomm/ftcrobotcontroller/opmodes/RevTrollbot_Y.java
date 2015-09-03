/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class RevTrollbot_Y extends OpMode {

    DcMotor motorL;
    DcMotor motorR;
    DcMotor liftL;
    DcMotor liftR;
    Servo servoL;
    Servo servoR;

    @Override
    public void init() {
        motorL = hardwareMap.dcMotor.get("motor_1");
        motorR = hardwareMap.dcMotor.get("motor_2");
        liftL = hardwareMap.dcMotor.get("motor_3");
        liftR = hardwareMap.dcMotor.get("motor_4");
        servoL = hardwareMap.servo.get("servo_1");
        servoR = hardwareMap.servo.get("servo_2");
    }


    @Override
    public void loop() {
        if (Math.abs(gamepad1.left_stick_y) < .7 && Math.abs(gamepad1.right_stick_y) < .7 && Math.abs(gamepad2.left_stick_y) < .7) {
            motorL.setPower(0);
            motorR.setPower(0);
            liftL.setPower(0);
            liftR.setPower(0);
        }
        motorL.setPower(gamepad1.left_stick_y);
        motorR.setPower(-gamepad1.right_stick_y);
        liftL.setPower(-gamepad2.left_stick_y);
        liftR.setPower(gamepad2.left_stick_y);

        if (gamepad2.a) {
            servoL.setPosition(1.0);
            servoR.setPosition(0);
        }

        if (gamepad2.x) {
            servoL.setPosition(0);
            servoR.setPosition(1.0);
        }
    }

    @Override
    public void stop() {

    }
}
