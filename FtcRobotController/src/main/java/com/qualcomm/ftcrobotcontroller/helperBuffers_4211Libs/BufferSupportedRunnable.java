package com.qualcomm.ftcrobotcontroller.helperBuffers_4211Libs;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.lang.ref.Reference;

/**
 * Created by FTC Robot Team 4211 on 7/31/2015.
 */
public class BufferSupportedRunnable implements Runnable, physicalDataListener {
    public BufferedDeviceManager bufferedDeviceManager;
    public HardwareMap hMap;
    public BufferSupportedRunnable(HardwareMap map)
    {
        bufferedDeviceManager = new BufferedDeviceManager();
        bufferedDeviceManager.addPhysicalDataListener(this);
        hMap = map;
    }
    @Override
    public void newDataReceived() {

    }

    @Override
    public void run() {

    }
}
