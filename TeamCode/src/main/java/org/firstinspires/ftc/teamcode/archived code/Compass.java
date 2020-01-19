package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.I2cSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@SuppressWarnings({"WeakerAccess", "unused"})
@I2cSensor(name = "BNO055 IMU I2cDeviceSynchDeviceSynchDevice", description = "IM2", xmlTag = "BNO055a")
public class Compass extends I2cDeviceSynchDevice<I2cDeviceSynch> {
    //////////////////////////////////////////////////////////////////////////////////////////
    // User Methods
    //////////////////////////////////////////////////////////////////////////////////////////

    public void setMode(Mode mode) {
        int _mode = mode.bVal;
        this.mode = mode;
        deviceClient.write8(Register.BNO055_OPR_MODE_ADDR.bVal, _mode);
    }

    public void setNormalPowerMode() {
        deviceClient.write8(Register.BNO055_PWR_MODE_ADDR.bVal, Power.NORMAL.bVal);
    }

    public double heading() {
        int[] magnetic = getMagnetometerRaw();
        return Math.atan2(magnetic[1], magnetic[0]);
    }

    public double[] getAccelerometer() {
        return angles(getAccelerometerRaw());
    }

    public double[] getMagnetometer() {
        return angles(getMagnetometerRaw());
    }

    public double[] getGyroscope() {
        return angles(getGyroscopeRaw());
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    // Raw Register Reads
    //////////////////////////////////////////////////////////////////////////////////////////

    int combineRead(Register msb, Register lsb) {
        byte MSB = deviceClient.read8(msb.bVal);
        byte LSB = deviceClient.read8(lsb.bVal);
        return ((MSB << 8) | LSB);
    }

    public double[] angles(int[] vector) {
        double theta = Math.atan2(vector[0], Math.sqrt(Math.pow(vector[1], 2) + Math.pow(vector[2], 2)));
        double psi = Math.atan2(vector[1], Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[2], 2)));
        double phi = Math.atan2(Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2)), vector[2]);
        return new double[] {theta, psi, phi};
    }

    public int[] getAccelerometerRaw() {
        int x = combineRead(Register.ACCEL_OFFSET_X_MSB_ADDR, Register.ACCEL_OFFSET_X_LSB_ADDR);
        int y = combineRead(Register.ACCEL_OFFSET_Y_MSB_ADDR, Register.ACCEL_OFFSET_Y_LSB_ADDR);
        int z = combineRead(Register.ACCEL_OFFSET_Z_MSB_ADDR, Register.ACCEL_OFFSET_Z_LSB_ADDR);
        return new int[] {x,y,z};
    }

    public int[] getMagnetometerRaw() {
        int x = combineRead(Register.MAG_OFFSET_X_MSB_ADDR, Register.MAG_OFFSET_X_LSB_ADDR);
        int y = combineRead(Register.MAG_OFFSET_Y_MSB_ADDR, Register.MAG_OFFSET_Y_LSB_ADDR);
        int z = combineRead(Register.MAG_OFFSET_Z_MSB_ADDR, Register.MAG_OFFSET_Z_LSB_ADDR);
        return new int[] {x,y,z};
    }

    public int[] getGyroscopeRaw() {
        int x = combineRead(Register.GYRO_OFFSET_X_MSB_ADDR, Register.GYRO_OFFSET_X_LSB_ADDR);
        int y = combineRead(Register.GYRO_OFFSET_Y_MSB_ADDR, Register.GYRO_OFFSET_Y_LSB_ADDR);
        int z = combineRead(Register.GYRO_OFFSET_Z_MSB_ADDR, Register.GYRO_OFFSET_Z_LSB_ADDR);
        return new int[] {x,y,z};
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    // Registers and Config Settings
    //////////////////////////////////////////////////////////////////////////////////////////

    public int CHIP_ID = 0xa0;

    public enum Register {

        FIRST(0),
        /* Page id register definition */
        BNO055_PAGE_ID_ADDR(0X07),

        /* PAGE0 REGISTER DEFINITION START*/
        BNO055_CHIP_ID_ADDR(0x00),
        BNO055_ACCEL_REV_ID_ADDR(0x01),
        BNO055_MAG_REV_ID_ADDR(0x02),
        BNO055_GYRO_REV_ID_ADDR(0x03),
        BNO055_SW_REV_ID_LSB_ADDR(0x04),
        BNO055_SW_REV_ID_MSB_ADDR(0x05),
        BNO055_BL_REV_ID_ADDR(0X06),

        /* Accel data register */
        BNO055_ACCEL_DATA_X_LSB_ADDR(0X08),
        BNO055_ACCEL_DATA_X_MSB_ADDR(0X09),
        BNO055_ACCEL_DATA_Y_LSB_ADDR(0X0A),
        BNO055_ACCEL_DATA_Y_MSB_ADDR(0X0B),
        BNO055_ACCEL_DATA_Z_LSB_ADDR(0X0C),
        BNO055_ACCEL_DATA_Z_MSB_ADDR(0X0D),

        /* Mag data register */
        BNO055_MAG_DATA_X_LSB_ADDR(0X0E),
        BNO055_MAG_DATA_X_MSB_ADDR(0X0F),
        BNO055_MAG_DATA_Y_LSB_ADDR(0X10),
        BNO055_MAG_DATA_Y_MSB_ADDR(0X11),
        BNO055_MAG_DATA_Z_LSB_ADDR(0X12),
        BNO055_MAG_DATA_Z_MSB_ADDR(0X13),

        /* Gyro data registers */
        BNO055_GYRO_DATA_X_LSB_ADDR(0X14),
        BNO055_GYRO_DATA_X_MSB_ADDR(0X15),
        BNO055_GYRO_DATA_Y_LSB_ADDR(0X16),
        BNO055_GYRO_DATA_Y_MSB_ADDR(0X17),
        BNO055_GYRO_DATA_Z_LSB_ADDR(0X18),
        BNO055_GYRO_DATA_Z_MSB_ADDR(0X19),

        /* Euler data registers */
        BNO055_EULER_H_LSB_ADDR(0X1A),
        BNO055_EULER_H_MSB_ADDR(0X1B),
        BNO055_EULER_R_LSB_ADDR(0X1C),
        BNO055_EULER_R_MSB_ADDR(0X1D),
        BNO055_EULER_P_LSB_ADDR(0X1E),
        BNO055_EULER_P_MSB_ADDR(0X1F),

        /* Quaternion data registers */
        BNO055_QUATERNION_DATA_W_LSB_ADDR(0X20),
        BNO055_QUATERNION_DATA_W_MSB_ADDR(0X21),
        BNO055_QUATERNION_DATA_X_LSB_ADDR(0X22),
        BNO055_QUATERNION_DATA_X_MSB_ADDR(0X23),
        BNO055_QUATERNION_DATA_Y_LSB_ADDR(0X24),
        BNO055_QUATERNION_DATA_Y_MSB_ADDR(0X25),
        BNO055_QUATERNION_DATA_Z_LSB_ADDR(0X26),
        BNO055_QUATERNION_DATA_Z_MSB_ADDR(0X27),

        /* Linear acceleration data registers */
        BNO055_LINEAR_ACCEL_DATA_X_LSB_ADDR(0X28),
        BNO055_LINEAR_ACCEL_DATA_X_MSB_ADDR(0X29),
        BNO055_LINEAR_ACCEL_DATA_Y_LSB_ADDR(0X2A),
        BNO055_LINEAR_ACCEL_DATA_Y_MSB_ADDR(0X2B),
        BNO055_LINEAR_ACCEL_DATA_Z_LSB_ADDR(0X2C),
        BNO055_LINEAR_ACCEL_DATA_Z_MSB_ADDR(0X2D),

        /* Gravity data registers */
        BNO055_GRAVITY_DATA_X_LSB_ADDR(0X2E),
        BNO055_GRAVITY_DATA_X_MSB_ADDR(0X2F),
        BNO055_GRAVITY_DATA_Y_LSB_ADDR(0X30),
        BNO055_GRAVITY_DATA_Y_MSB_ADDR(0X31),
        BNO055_GRAVITY_DATA_Z_LSB_ADDR(0X32),
        BNO055_GRAVITY_DATA_Z_MSB_ADDR(0X33),

        /* Temperature data register */
        BNO055_TEMP_ADDR(0X34),

        /* Status registers */
        BNO055_CALIB_STAT_ADDR(0X35),
        BNO055_SELFTEST_RESULT_ADDR(0X36),
        BNO055_INTR_STAT_ADDR(0X37),

        BNO055_SYS_CLK_STAT_ADDR(0X38),
        BNO055_SYS_STAT_ADDR(0X39),
        BNO055_SYS_ERR_ADDR(0X3A),

        /* Unit selection register */
        BNO055_UNIT_SEL_ADDR(0X3B),
        BNO055_DATA_SELECT_ADDR(0X3C),

        /* Mode registers */
        BNO055_OPR_MODE_ADDR(0X3D),
        BNO055_PWR_MODE_ADDR(0X3E),

        BNO055_SYS_TRIGGER_ADDR(0X3F),
        BNO055_TEMP_SOURCE_ADDR(0X40),

        /* Axis remap registers */
        BNO055_AXIS_MAP_CONFIG_ADDR(0X41),
        BNO055_AXIS_MAP_SIGN_ADDR(0X42),

        /* SIC registers */
        BNO055_SIC_MATRIX_0_LSB_ADDR(0X43),
        BNO055_SIC_MATRIX_0_MSB_ADDR(0X44),
        BNO055_SIC_MATRIX_1_LSB_ADDR(0X45),
        BNO055_SIC_MATRIX_1_MSB_ADDR(0X46),
        BNO055_SIC_MATRIX_2_LSB_ADDR(0X47),
        BNO055_SIC_MATRIX_2_MSB_ADDR(0X48),
        BNO055_SIC_MATRIX_3_LSB_ADDR(0X49),
        BNO055_SIC_MATRIX_3_MSB_ADDR(0X4A),
        BNO055_SIC_MATRIX_4_LSB_ADDR(0X4B),
        BNO055_SIC_MATRIX_4_MSB_ADDR(0X4C),
        BNO055_SIC_MATRIX_5_LSB_ADDR(0X4D),
        BNO055_SIC_MATRIX_5_MSB_ADDR(0X4E),
        BNO055_SIC_MATRIX_6_LSB_ADDR(0X4F),
        BNO055_SIC_MATRIX_6_MSB_ADDR(0X50),
        BNO055_SIC_MATRIX_7_LSB_ADDR(0X51),
        BNO055_SIC_MATRIX_7_MSB_ADDR(0X52),
        BNO055_SIC_MATRIX_8_LSB_ADDR(0X53),
        BNO055_SIC_MATRIX_8_MSB_ADDR(0X54),

        /* Accelerometer Offset registers */
        ACCEL_OFFSET_X_LSB_ADDR(0X55),
        ACCEL_OFFSET_X_MSB_ADDR(0X56),
        ACCEL_OFFSET_Y_LSB_ADDR(0X57),
        ACCEL_OFFSET_Y_MSB_ADDR(0X58),
        ACCEL_OFFSET_Z_LSB_ADDR(0X59),
        ACCEL_OFFSET_Z_MSB_ADDR(0X5A),

        /* Magnetometer Offset registers */
        MAG_OFFSET_X_LSB_ADDR(0X5B),
        MAG_OFFSET_X_MSB_ADDR(0X5C),
        MAG_OFFSET_Y_LSB_ADDR(0X5D),
        MAG_OFFSET_Y_MSB_ADDR(0X5E),
        MAG_OFFSET_Z_LSB_ADDR(0X5F),
        MAG_OFFSET_Z_MSB_ADDR(0X60),

        /* Gyroscope Offset register s*/
        GYRO_OFFSET_X_LSB_ADDR(0X61),
        GYRO_OFFSET_X_MSB_ADDR(0X62),
        GYRO_OFFSET_Y_LSB_ADDR(0X63),
        GYRO_OFFSET_Y_MSB_ADDR(0X64),
        GYRO_OFFSET_Z_LSB_ADDR(0X65),
        GYRO_OFFSET_Z_MSB_ADDR(0X66),

        /* Radius registers */
        ACCEL_RADIUS_LSB_ADDR(0X67),
        ACCEL_RADIUS_MSB_ADDR(0X68),
        MAG_RADIUS_LSB_ADDR(0X69),
        MAG_RADIUS_MSB_ADDR(0X6A),
        LAST(MAG_RADIUS_MSB_ADDR.bVal);

        public int bVal;

        Register(int bVal) {
            this.bVal = bVal;
        }
    }

    public enum Registers {
        FIRST(0x07),
        MODE(0x3d),
        PAGE(0x07),
        CALIBRATION(0x35),
        TRIGGER(0x3f),
        POWER(0x3e),
        ID(0x00),
        LAST(TRIGGER.bVal);

        public int bVal;

        Registers(int bVal) {
            this.bVal = bVal;
        }
    }
    public enum Mode {

        CONFIG_MODE(0x00),
        ACCONLY_MODE(0x01),
        MAGONLY_MODE(0x02),
        GYRONLY_MODE(0x03),
        ACCMAG_MODE(0x04),
        ACCGYRO_MODE(0x05),
        MAGGYRO_MODE(0x06),
        AMG_MODE(0x07),
        IMUPLUS_MODE(0x08),
        COMPASS_MODE(0x09),
        M4G_MODE(0x0a),
        NDOF_FMC_OFF_MODE(0x0b),
        NDOF_MODE(0x0c);

        public int bVal;


        Mode(int bVal) {
            this.bVal = bVal;
        }
    }

    public enum Power {
        NORMAL(0x00),
        LOW(0x01),
        SUSPEND(0x02);

        public int bVal;

        Power(int bVal) {
            this.bVal = bVal;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    // Construction and Initialization
    //////////////////////////////////////////////////////////////////////////////////////////
    public final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x28);
    public double theta, psi;
    public Mode mode;

    public Compass(I2cDeviceSynch deviceClient, Telemetry telemetry) {

        super(deviceClient, true);
        int i = 0;
        try {
            i++;
            this.setOptimalReadWindow();
            i++;
            telemetry.addLine("" +1);
            i++;
            this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

            i++;
            super.registerArmingStateCallback(false);
            i++;
            this.deviceClient.engage();

            i++;
            int[] acceleration = getAccelerometerRaw();
            i++;
            this.theta = Math.atan2(acceleration[0],
                    Math.sqrt(acceleration[1] * acceleration[1] + acceleration[2] * acceleration[2]));
            i++;
            this.psi = Math.atan2(acceleration[1],
                    Math.sqrt(acceleration[0] * acceleration[0] + acceleration[2] * acceleration[2]));

            i++;
            setMode(Mode.ACCMAG_MODE);
        } catch (Exception e) {
            telemetry.addLine("" + i);
            telemetry.addLine(e.toString());
            telemetry.update();
        }
    }

    protected void setOptimalReadWindow() {
        I2cDeviceSynch.ReadWindow readWindow = new I2cDeviceSynch.ReadWindow(
                Register.FIRST.bVal,
                Register.LAST.bVal - Register.FIRST.bVal + 1,
                I2cDeviceSynch.ReadMode.REPEAT);
        this.deviceClient.setReadWindow(readWindow);
    }

    @Override
    public synchronized boolean doInitialize() {
        return true;
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.Other;
    }

    @Override
    public String getDeviceName() {
        return "BOSCH BNO055 IMU";
    }
}