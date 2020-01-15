package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TCA9548", group = "What?")
public class TestOp2 extends LinearOpMode {

    MultiplexAccelerometer multiplexAccelerometer;

    @Override
    public void runOpMode() {
        telemetry.addLine("Initializing");
        multiplexAccelerometer = hardwareMap.get(MultiplexAccelerometer.class, "multiplexer");
        telemetry.addData("Initialized", true);
        telemetry.update();
        waitForStart();

        int[] raw = multiplexAccelerometer.getAccelerometerRaw(7);
        String[] axes = {"x", "y", "z"};

        for (int i=0; i<raw.length; i++) {
            telemetry.addData(axes[i], raw[i]);
        }
        telemetry.update();
    }
}
