package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="DriverControlledOpMode", group="DriverOpModes")

public class DriverController extends LinearOpMode {
    private Servo wrist;
    private Servo elbow;
    private Servo shoulderRotate;
    private DcMotor shoulderElevate;

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private DcMotor brickLoaderMotor;

    private Claw claw;
    private Wheels wheels;
    private BrickLoader brickLoader;

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
        wrist  = hardwareMap.get(Servo.class, "wrist");
        elbow  = hardwareMap.get(Servo.class, "elbow");
        shoulderRotate  = hardwareMap.get(Servo.class, "shoulderRotate");

        claw = new Claw(wrist, elbow, shoulderRotate, shoulderElevate, telemetry);
        wheels = new Wheels(leftMotor, rightMotor);
        brickLoader = new BrickLoader(brickLoaderMotor);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if(gamepad1.left_trigger < 0.7){
                brickLoader.setSpeed(gamepad2.left_trigger);
            }
            else{
                brickLoader.setSpeed(gamepad1.left_trigger);
            }


            if((Math.abs(gamepad1.right_stick_x)) > 0.2){
                wheels.turn(gamepad1.left_stick_y *(22.5 * Math.PI), (gamepad1.right_stick_x>0), (1-Math.abs(gamepad1.right_stick_x))*200);
            }
            else{
                wheels.drive(gamepad1.left_stick_y *(22.5 * Math.PI));
            }

            claw.move((int)gamepad2.right_stick_y, (int)gamepad2.left_stick_y, (gamepad2.left_stick_x)*360);
        }
    }

}
