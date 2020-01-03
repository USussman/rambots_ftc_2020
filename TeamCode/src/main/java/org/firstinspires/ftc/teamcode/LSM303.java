package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.I2cSensor;

@SuppressWarnings({"unused"})

// While LSM303 has a compass, only the Accelerometer was implemented here.
@I2cSensor(name = "LSM303 Accelerometer", description = "Accelerometer from Adafruit", xmlTag = "LSM303")
public class LSM303 extends I2cDeviceSynchDevice<I2cDeviceSynch> {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // User Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public int[] getAccelerometer() {
        // Get the raw data
        byte[] raw = getAccelerometerRaw();

        // Shift to create an int
        int x = ((raw[1] << 8) | raw[0]);
        int y = ((raw[3] << 8) | raw[2]);
        int z = ((raw[5] << 8) | raw[4]);
        return new int[]{x,y,z};
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Raw Register Reads
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public byte getCTRL_REG1_A()
    {
        // Method to test if everything is working. Should return 0b00000111 (7)
        return readByte(Register.ACCEL_CTRL_REG1_A);
    }

    public byte[] getAccelerometerRaw() {
        byte xL = readByte(Register.ACCEL_OUT_X_L_A);
        byte xH = readByte(Register.ACCEL_OUT_X_H_A);
        byte yL = readByte(Register.ACCEL_OUT_Y_L_A);
        byte yH = readByte(Register.ACCEL_OUT_Y_H_A);
        byte zL = readByte(Register.ACCEL_OUT_Z_L_A);
        byte zH = readByte(Register.ACCEL_OUT_Z_H_A);

        return new byte[]{xL,xH,yL,yH,zL,zH};
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Read and Write Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    protected void writeByte(final Register reg, byte value)
    {
        deviceClient.write8(reg.bVal,value);
    }

    protected byte readByte(Register reg)
    {
        return deviceClient.read8(reg.bVal);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Registers and Config Settings
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public enum Register {
        // Currently only for accelerometer
        FIRST (0),
        ACCEL_CTRL_REG1_A(0x20),   // 00000111   rw
        ACCEL_CTRL_REG2_A(0x21),   // 00000000   rw
        ACCEL_CTRL_REG3_A(0x22),   // 00000000   rw
        ACCEL_CTRL_REG4_A(0x23),   // 00000000   rw
        ACCEL_CTRL_REG5_A(0x24),   // 00000000   rw
        ACCEL_CTRL_REG6_A(0x25),   // 00000000   rw
        ACCEL_REFERENCE_A(0x26),   // 00000000   r
        ACCEL_STATUS_REG_A (0x27),   // 00000000   r
        ACCEL_OUT_X_L_A (0x28),
        ACCEL_OUT_X_H_A (0x29),
        ACCEL_OUT_Y_L_A (0x2A),
        ACCEL_OUT_Y_H_A (0x2B),
        ACCEL_OUT_Z_L_A (0x2C),
        ACCEL_OUT_Z_H_A (0x2D),
        ACCEL_FIFO_CTRL_REG_A (0x2E),
        ACCEL_FIFO_SRC_REG_A (0x2F),
        ACCEL_INT1_CFG_A (0x30),
        ACCEL_INT1_SOURCE_A (0x31),
        ACCEL_INT1_THS_A (0x32),
        ACCEL_INT1_DURATION_A (0x33),
        ACCEL_INT2_CFG_A (0x34),
        ACCEL_INT2_SOURCE_A (0x35),
        ACCEL_INT2_THS_A (0x36),
        ACCEL_INT2_DURATION_A (0x37),
        ACCEL_CLICK_CFG_A (0x38),
        ACCEL_CLICK_SRC_A (0x39),
        ACCEL_CLICK_THS_A (0x3A),
        ACCEL_TIME_LIMIT_A (0x3B),
        ACCEL_TIME_LATENCY_A (0x3C),
        ACCEL_TIME_WINDOW_A (0x3D),
        LAST(ACCEL_TIME_WINDOW_A.bVal);

        public int bVal;

        Register (int bVal) {
            this.bVal = bVal;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Construction and Initialization
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public I2cAddr I2cAddress = I2cAddr.create7bit(0b0011001);

    public LSM303(I2cDeviceSynch deviceClient)
    {
        super(deviceClient, true);

        this.setOptimalReadWindow();
        this.deviceClient.setI2cAddress(I2cAddress);

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
    }

    public LSM303(I2cDeviceSynch deviceClient, I2cAddr address)
    {
        super(deviceClient, true);

        this.setOptimalReadWindow();
        I2cAddress = address;
        this.deviceClient.setI2cAddress(I2cAddress);

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
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
    protected synchronized boolean doInitialize()
    {
        return true;
    }

    @Override
    public String getDeviceName()
    {
        return "Adafruit LSM303 Accelerometer";
    }

    @Override
    public Manufacturer getManufacturer()
    {
        return Manufacturer.Adafruit;
    }
}
