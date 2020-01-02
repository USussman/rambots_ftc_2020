package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.I2cSensor;
import com.qualcomm.robotcore.util.TypeConversion;

@SuppressWarnings({"unused"})

// While LSM303 has a compass, only the Accelerometer was implemented here.
@I2cSensor(name = "LSM303 Accelerometer", description = "Accelerometer from Adafruit", xmlTag = "LSM303")
public class LSM303 extends I2cDeviceSynchDevice<I2cDeviceSynch> {

    public final static I2cAddr ADDRESS_I2C_DEFAULT = new I2cAddr(0b11001);

    public enum Register {
        FIRST (0),
        CTRL_REG1_A (0x20),   // 00000111   rw
        CTRL_REG2_A (0x21),   // 00000000   rw
        CTRL_REG3_A (0x22),   // 00000000   rw
        CTRL_REG4_A (0x23),   // 00000000   rw
        CTRL_REG5_A (0x24),   // 00000000   rw
        CTRL_REG6_A (0x25),   // 00000000   rw
        REFERENCE_A (0x26),   // 00000000   r
        STATUS_REG_A (0x27),   // 00000000   r
        OUT_X_L_A (0x28),
        OUT_X_H_A (0x29),
        OUT_Y_L_A (0x2A),
        OUT_Y_H_A (0x2B),
        OUT_Z_L_A (0x2C),
        OUT_Z_H_A (0x2D),
        FIFO_CTRL_REG_A (0x2E),
        FIFO_SRC_REG_A (0x2F),
        INT1_CFG_A (0x30),
        INT1_SOURCE_A (0x31),
        INT1_THS_A (0x32),
        INT1_DURATION_A (0x33),
        INT2_CFG_A (0x34),
        INT2_SOURCE_A (0x35),
        INT2_THS_A (0x36),
        INT2_DURATION_A (0x37),
        CLICK_CFG_A(0x38),
        CLICK_SRC_A(0x39),
        CLICK_THS_A(0x3A),
        TIME_LIMIT_A (0x3B),
        TIME_LATENCY_A (0x3C),
        TIME_WINDOW_A (0x3D),
        LAST(TIME_WINDOW_A.bVal);

        public int bVal;

        Register (int bVal) {
            this.bVal = bVal;
        }
    }

    public LSM303(I2cDeviceSynch deviceClient)
    {
        super(deviceClient, true);

        this.setOptimalReadWindow();
        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
    }

    protected void writeByte(final Register reg, byte value)
    {
        deviceClient.write8(reg.bVal,value);
    }

    protected byte readByte(Register reg)
    {
        return deviceClient.read8(reg.bVal);
    }

    protected void setOptimalReadWindow() {
        // Sensor registers are read repeatedly and stored in a register. This method specifies the
        // registers and repeat read mode
        I2cDeviceSynch.ReadWindow readWindow = new I2cDeviceSynch.ReadWindow(
                Register.FIRST.bVal,
                Register.LAST.bVal - Register.FIRST.bVal + 1,
                I2cDeviceSynch.ReadMode.REPEAT);
        this.deviceClient.setReadWindow(readWindow);
    }

    @Override
    public Manufacturer getManufacturer()
    {
        return Manufacturer.Adafruit;
    }

    @Override
    protected synchronized boolean doInitialize()
    {
        return true;
    }

    @Override
    public String getDeviceName()
    {
        return "Adafruit LSM303 Accelerometer";
    }
}
