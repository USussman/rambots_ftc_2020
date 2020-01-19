package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Orange")
public class Pear extends LinearOpMode {
    LSM303 wheee;
    static Telemetry staticTelemetry;

    @Override
    public void runOpMode() throws InterruptedException {
        setTelemetry(telemetry);
        try {
            wheee = hardwareMap.get(LSM303.class, "whee");
        } catch (Exception e) {
            telemetry.addLine(e.getMessage());
            telemetry.update();
            waitForStart();
        }
        telemetry.addLine(wheee.getAccelerometer().toString());
        telemetry.update();
//        waitForStart();
    }

    public static void print(String string)
    {
        staticTelemetry.addLine(string);
        staticTelemetry.update();
    }

    public void setTelemetry(Telemetry telemetry) {
        Pear.staticTelemetry = telemetry;
    }
}
