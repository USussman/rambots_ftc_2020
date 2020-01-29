/*
 * MIT License
 *
 * Copyright (c) 2016 Chris D
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceImpl;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.hardware.configuration.I2cSensor;

/**
 * Created by Chris D on 10/5/2016.
 *
 * Partially based on:
 * https://github.com/OliviliK/FTC_Library/blob/master/TCS34725_ColorSensor.java
 */

@I2cSensor(name = "TCA9548 I2C Multiplexer2", description = "I2C Multiplexer from Adafruit", xmlTag = "TCA9548_2")
public class MultiplexAccelerometer {
    AdafruitBNO055IMU a;

    // Registers
    public enum Register {
        // Currently only for accelerometer
        FIRST (0),
        ID(0x0F),
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
//    static final int CTRL_REG1_A = 0x20;
//    static final int CTRL_REG2_A = 0x21;
//    static final int CTRL_REG3_A = 0x22;
//    static final int CTRL_REG4_A = 0x23;
//    static final int CTRL_REG5_A = 0x24;
//    static final int CTRL_REG6_A = 0x25;
//    static final int ATIME = 0x81;
//    static final int CONTROL = 0x8F;
//    static final int ID = 0x92;
//    static final int STATUS = 0x93;
//    static final int CDATAL = 0x94;

    // Default I2C address for multiplexer. The address can be changed to any
    // value from 0x70 to 0x77, so this line would need to be changed if a
    // non-default address is to be used.
    static final I2cAddr MUX_ADDRESS = new I2cAddr(0x70);
    private I2cDevice mux;
    private I2cDeviceSynch muxReader;

    // Only one color sensor is needed in code as the multiplexer switches
    // between the physical sensors
    private byte[] adaCache;
    // I2C address for color sensor
    static final I2cAddr ADA_ADDRESS = new I2cAddr(0x19);
    private I2cDevice ada;
    private I2cDeviceSynch adaReader;

    private int[] sensorPorts;

    public static int SENSITIVITY_2G =  0x00;
    public static int SENSITIVITY_4G =  0x01;
    public static int SENSITIVITY_8G =  0x02;
    public static int SENSITIVITY_18G = 0x03;

    /**
     * Initializes Adafruit color sensors on the specified ports of the I2C
     * multiplexer.
     *
     * @param hardwareMap  hardwareMap from OpMode
     * @param muxName      Configuration name of I2CDevice for multiplexer
     * @param colorName    Configuration name of I2CDevice for color sensor
     * @param ports        Out ports on multiplexer with color sensors attached
     * @param milliSeconds Integration time in milliseconds
     * @param gain         Gain (GAIN_1X, GAIN_4X, GAIN_16X, GAIN_60X)
     */
    public MultiplexAccelerometer(HardwareMap hardwareMap,
                                String muxName,
                                String colorName,
                                int[] ports,
                                double milliSeconds,
                                int gain) {
        sensorPorts = ports;

        hardwareMap.i2cDevice.put(muxName, mux);
        mux = hardwareMap.i2cDevice.get(muxName);
//        mux = hardwareMap.get(I2cSensor.class, muxName);
        muxReader = new I2cDeviceSynchImpl((I2cDevice) mux, MUX_ADDRESS, false);
        muxReader.engage();

        // Loop over the ports activating each color sensor
        for (int portNumber: sensorPorts) {
            // Write to given output port on the multiplexer
            muxReader.write8(0x0, 1 << portNumber); // , true);

            ada = hardwareMap.i2cDevice.get(colorName);
            adaReader = new I2cDeviceSynchImpl(ada, ADA_ADDRESS, false);
            adaReader.engage();

            final int time = integrationByte(milliSeconds);
            adaReader.read8(Register.ID.bVal);                   // Read device ID
            adaReader.write8(Register.ACCEL_CTRL_REG1_A.bVal, 0b10010111);
            adaReader.write8(Register.ACCEL_CTRL_REG4_A.bVal, gain << 4); // , true); // Set gain
        }
    }

    /**
     * Set the integration time on all the color sensors
     * @param milliSeconds Time in millseconds
     */
    public void setIntegrationTime(double milliSeconds) {
        int val = integrationByte(milliSeconds);

        for (int portNumber: sensorPorts) {
            muxReader.write8(0x0, 1 << portNumber);  // , true);
//            adaReader.write8(ATIME, val);  // , true);
        }
    }

    private int integrationByte(double milliSeconds) {
        int count = (int)(milliSeconds/2.4);
        if (count<1)    count = 1;   // Clamp the time range
        if (count>256)  count = 256;
        return (256 - count);
    }

    // Un-needed?
    public void startPolling() {
        for (int portNumber: sensorPorts) {
            muxReader.write8(0x0, 1 << portNumber);  // , true);
            adaReader.read8(Register.ACCEL_STATUS_REG_A.bVal);
        }
    }

    /**
     * Retrieve the color read by the given color sensor
     *
     * @param port Port on multiplexer of given color sensor
     * @return Array containing the Clear, Red, Green, and Blue color values
     */
    public int[] getAccelerometerRaw(int port) {
        // Write to I2C port on the multiplexer
        muxReader.write8(0x0, 1 << port);  // , true);

        // Read color registers
        byte xL = adaReader.read8(Register.ACCEL_OUT_X_L_A.bVal);
        byte xH = adaReader.read8(Register.ACCEL_OUT_X_H_A.bVal);
        byte yL = adaReader.read8(Register.ACCEL_OUT_Y_L_A.bVal);
        byte yH = adaReader.read8(Register.ACCEL_OUT_Y_H_A.bVal);
        byte zL = adaReader.read8(Register.ACCEL_OUT_Z_L_A.bVal);
        byte zH = adaReader.read8(Register.ACCEL_OUT_Z_H_A.bVal);

        adaCache = new byte[] {xL,xH,yL,yH,zL,zH};

        // Combine high and low bytes
        int[] accel = new int[3];
        for (int i=0; i<4; i++) {
            accel[i] = (adaCache[2*i+1] << 8) | adaCache[2*i];
        }
        return accel;
    }
}
