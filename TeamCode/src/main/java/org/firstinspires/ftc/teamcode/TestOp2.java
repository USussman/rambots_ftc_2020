package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TCA95482")
public class TestOp2 extends LinearOpMode {

//    MultiplexAccelerometer multiplexAccelerometer;
    int[] ports = {0, 3};

    @Override
    public void runOpMode() {
        telemetry.addLine("Initializing");
        int milliSeconds = 48;
        String a = hardwareMap.i2cDevice.toString();
        telemetry.addLine(a);
        telemetry.addLine("banana"+hardwareMap.i2cDevice.size());
        telemetry.update();
//        waitForStart();
////        I2C mux2 = hardwareMap.get(I2C.class, "multiplexer");
//        multiplexAccelerometer = new MultiplexAccelerometer(hardwareMap, "multiplexer", "ada",
//                ports, milliSeconds,
//                MultiplexAccelerometer.SENSITIVITY_4G);
//        multiplexAccelerometer = hardwareMap.get(MultiplexAccelerometer.class, "multiplexer");
//        telemetry.addData("Initialized", true);
//        telemetry.update();
//        waitForStart();
//
//        int[] raw = multiplexAccelerometer.getAccelerometerRaw(7);
//        String[] axes = {"x", "y", "z"};
//
//        for (int i=0; i<raw.length; i++) {
//            telemetry.addData(axes[i], raw[i]);
//        }
//        telemetry.update();
    }
}
