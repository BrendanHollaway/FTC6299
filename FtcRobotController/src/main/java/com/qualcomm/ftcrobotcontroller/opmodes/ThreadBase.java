package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.EventLoopManager;

/**
 * Created by Joshua on 7/29/2015.
 */
public class ThreadBase implements Runnable{
    public void run() {

        try { Thread.sleep(10); }

        catch (InterruptedException ex)
        {Thread.currentThread().interrupt();}
    }
}
