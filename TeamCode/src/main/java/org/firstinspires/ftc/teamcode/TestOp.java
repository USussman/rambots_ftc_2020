package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.concurrent.TimeUnit;

/**
 * Created 11/13/19 by Rambots Team
 */

@TeleOp

public class TestOp extends LinearOpMode {
    private TCA9548 multiplexer;
    private final byte TCAADDR = 0x70;


    void tcaselect(TCA9548 multiplexer, int i) {
        if (i > 7) return;

        multiplexer.write((byte) (1 << i), (byte) 0);
    }


    @Override
    public void runOpMode() {
        /**
         * TCA9548 I2CScanner.pde -- I2C bus scanner for Arduino
         *
         * Based on code c. 2009, Tod E. Kurt, http://todbot.com/blog/
         *
         */

        multiplexer = hardwareMap.get(TCA9548.class, "multiplexer");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        telemetry.addData("Got here", true);
        telemetry.update();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                telemetry.addData("Error", e);
                telemetry.update();
            }

            telemetry.addData("TCAScanner ready", true);
            telemetry.update();

            for (int t = 0; t < 8; t++) {
                tcaselect(multiplexer, t);
                telemetry.addLine("TCA Port #" + t);
                telemetry.update();

                for (int addr = 0; addr <= 127; addr++) {
                    if (addr == TCAADDR) continue;

                    byte data = 0;
                    multiplexer.write((byte) addr, data);
                    if (multiplexer.read((byte) 0) != 0) {
                        telemetry.addLine("Found I2C 0x" + addr);
                    }
                }
            }
            telemetry.addData("done", true);
            telemetry.update();


    }
}
