package com.qualcomm.ftcrobotcontroller.helperBuffers_4211Libs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;

/**
 * Created by FTC Robot Team 4211 on 7/31/2015.
 */
public class BufferedIrSeekerSensor implements PhysicalDeviceBuffer{
    protected IrSeekerSensor sensor;
    public volatile String sensorName;
    protected Object bufferLock = new Object();
    protected double bufferedAngleReading =0;
    protected double bufferedStrengthReading =0;
    protected IrSeekerSensor.Mode bufferedMode = IrSeekerSensor.Mode.MODE_1200HZ_AC;
    protected boolean signalStatus =false;

    public BufferedIrSeekerSensor(IrSeekerSensor underlyingSensor)
    {
        this(underlyingSensor,"Default Light Sensor");
    }
    public BufferedIrSeekerSensor(IrSeekerSensor underlyingSensor, String cachedName)
    {
        sensor = underlyingSensor;
        sensorName = cachedName;
    }
    public synchronized static BufferedIrSeekerSensor createBufferedIRSeekerSensor(HardwareMap map, String name)
    {
        return new BufferedIrSeekerSensor(map.irSeekerSensor.get(name),name);
    }

    public double getBufferedAngleReading()
    {
        synchronized (bufferLock) {
            return bufferedAngleReading;
        }
    }

    public double getBufferedStrengthReading()
    {
        synchronized (bufferLock) {
            return bufferedStrengthReading;
        }
    }

    public boolean getBufferedSignalStatus()
    {
        synchronized (bufferLock) {
            return signalStatus;
        }
    }
    public void setBufferedSignalStatus(boolean status)
    {
        synchronized (bufferLock) {
            signalStatus =status;
        }
    }
    public IrSeekerSensor.Mode getBufferedMode()
    {
        synchronized (bufferLock) {
            return bufferedMode;
        }
    }

    public void setBufferedMode(IrSeekerSensor.Mode mode)
    {
        synchronized (bufferLock) {
            bufferedMode =mode;
        }
    }

    @Override
    public void swapInputBuffers() {
        synchronized (bufferLock)
        {
            bufferedAngleReading = sensor.getAngle();
            bufferedStrengthReading = sensor.getStrength();
            signalStatus = sensor.signalDetected();
        }
    }

    @Override
    public void swapOutputBuffers() {
        synchronized (bufferLock)
        {
            sensor.setMode(bufferedMode);
        }
    }

    @Override
    public Object getUnsafeBase() {
        synchronized (bufferLock) {
            return sensor;
        }
    }
}
