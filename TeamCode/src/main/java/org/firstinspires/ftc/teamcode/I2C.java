package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.I2cSensor;

@I2cSensor(name = "TCA9548 I2C Multiplexer2", description = "I2C Multiplexer from Adafruit", xmlTag = "TCA9548_2")
public class I2C extends I2cDeviceSynchDevice<I2cDeviceSynch> {
    @Override
    protected boolean doInitialize() {
        return false;
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.Adafruit;
    }

    @Override
    public String getDeviceName() {
        return "Adafruit TCA9548 I2C Multiplexer";
    }

    private I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x70);
    public I2C(I2cDeviceSynch deviceClient) {
        super(deviceClient, false);

        deviceClient.setI2cAddr(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
    }
}
