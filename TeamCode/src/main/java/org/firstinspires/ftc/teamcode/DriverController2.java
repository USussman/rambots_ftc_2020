package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="DriverControlledOpMode2", group="DriverOpModes")

public class DriverController2 extends LinearOpMode {
    private Servo hand;
    private CRServo wristServo;
    private CRServo elbowServo;
    private CRServo shoulderRotate;
    private DcMotor shoulderElevate;

    //private LSM303a compass0;
    //private LSM303a compass1;
    //private LSM303a compass2;

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private DcMotor brickLoaderMotor;

    private Claw2 claw;
    private Wheels2 wheels;
    private BrickLoader2 brickLoader;

    private Servo grabber;

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        brickLoaderMotor  = hardwareMap.get(DcMotor.class, "brickLoader");
        leftMotor  = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor  = hardwareMap.get(DcMotor.class, "rightMotor");
        shoulderElevate  = hardwareMap.get(DcMotor.class, "shoulderElevate");
        wristServo  = hardwareMap.get(CRServo.class, "wrist");
        elbowServo  = hardwareMap.get(CRServo.class, "elbow");
        shoulderRotate  = hardwareMap.get(CRServo.class, "shoulderRotate");
        hand = hardwareMap.get(Servo.class, "hand");
        grabber = hardwareMap.get(Servo.class, "graber");


        claw = new Claw2(hand, wristServo, elbowServo, shoulderRotate, shoulderElevate);
        wheels = new Wheels2(leftMotor, rightMotor);
        wheels.start();
        brickLoader = new BrickLoader2(brickLoaderMotor);
        boolean grabberOpen = false;

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
//            if (gamepad1.right_trigger > 0.3) {
//                brickLoader.setSpeed(-gamepad1.right_trigger);
//            }
//            if (gamepad1.left_trigger < 0.7) {
//                brickLoader.setSpeed(gamepad2.left_trigger);
//            } else {
//                brickLoader.setSpeed(gamepad1.left_trigger);
//            }


            wheels.rightMotor.setPower(gamepad1.right_stick_y);
            wheels.leftMotor.setPower(gamepad1.left_stick_y);

            if (gamepad1.right_trigger != 0) {
                if (grabberOpen) {
                    grabber.setPosition(0);
                } else {
                    grabber.setPosition(1.0 / 6);
                }
            }

//            claw.shoulderElevate.setPower(-gamepad2.left_stick_y);
//            claw.elbow.setPower(gamepad2.right_stick_y);//2
//            claw.shoulderRotate.setPower(gamepad2.left_stick_x);//0
//            claw.wrist.setPower(gamepad2.right_stick_x);//1
//            claw.setHandPosition(gamepad2.right_trigger);//3
        }
    }

}
