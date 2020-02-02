package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.I2cSensor;

@I2cSensor(name = "TCA9548 I2C Multiplexer", description = "I2C Multiplexer from Adafruit", xmlTag = "TCA9548")
public class TCA9548 extends I2cDeviceSynchDevice<I2cDeviceSynch> {
    byte read8(byte channel, byte register) {
        setChannel(channel);
        byte data = deviceClient.read8(register);
        setStandby();
        return data;
    }

    void write8(byte channel, byte register, byte data) {
        setChannel(channel);
        deviceClient.write8(register, data);
        setStandby();
    }

    byte read(byte register) {
        return deviceClient.read8(register);
    }

    void write(byte register, byte data) {
        deviceClient.write8(register, data);
    }

    private void setChannel(int channel) {
        if (channel > 7) return;

        byte[] data = {};
        deviceClient.write(1 << channel, data);
    }

    private void setStandby() {
        byte[] data = {};
        deviceClient.write(0, data);
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.Adafruit;
    }

    @Override
    protected synchronized boolean doInitialize() {
        return true;
    }

    @Override
    public String getDeviceName() {
        return "Adafruit TCA9548 I2C Multiplexer";
    }

    private I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x70);
    public TCA9548(I2cDeviceSynch deviceClient) {
        super(deviceClient, true);

        this.deviceClient.setI2cAddr(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
    }
}
