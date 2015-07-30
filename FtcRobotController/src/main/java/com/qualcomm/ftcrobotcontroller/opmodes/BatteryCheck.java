package com.qualcomm.ftcrobotcontroller.opmodes;


import com.qualcomm.robotcore.eventloop.EventLoopManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Joshua on 7/29/2015.
 */
public class BatteryCheck extends OpMode{

    //Declare variables here


    public BatteryCheck(){}

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        telemetry.addData("Voltage","" + EventLoopManager.ROBOT_BATTERY_LEVEL_KEY);
    }

    @Override
    public void stop() {

    }
}
