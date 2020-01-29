package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="DriverControlledOpMode", group="DriverOpModes")

public class DriverController extends LinearOpMode {
    private Servo hand;
    private CRServo wristServo;
    private CRServo elbowServo;
    private CRServo shoulderRotate;
    private DcMotor shoulderElevate;

    //private TCA9548 multiplexer;
    private LSM303a compass0;
    private LSM303a compass1;
    private LSM303a compass2;

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private DcMotor brickLoaderMotor;

    private Claw claw;
    private Wheels wheels;
    private BrickLoader brickLoader;

    //private EncodedServo wrist;
    //private EncodedServo elbow;

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
        //multiplexer = hardwareMap.get(TCA9548.class, "multiplexer");

        //compass0 = new LSM303a(multiplexer, (byte) 0);
        //compass1 = new LSM303a(multiplexer, (byte) 1);
        //compass2 = new LSM303a(multiplexer, (byte) 2);

        //wrist = new EncodedServo(wristServo, compass2, compass1);
        //elbow = new EncodedServo(elbowServo, compass1, compass0);

        claw = new Claw(hand, wristServo, elbowServo, shoulderRotate, shoulderElevate);
        wheels = new Wheels(leftMotor, rightMotor);
        wheels.start();
        brickLoader = new BrickLoader(brickLoaderMotor);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.left_trigger < 0.7) {
                brickLoader.setSpeed(gamepad2.left_trigger);
            } else {
                brickLoader.setSpeed(gamepad1.left_trigger);
            }


            /*if((Math.abs(gamepad1.right_stick_x)) > 0.2){
                wheels.turn(gamepad1.left_stick_y *(22.5 * Math.PI), (gamepad1.right_stick_x>0), (Math.abs(gamepad1.right_stick_x))*100);
            }
            else{
                wheels.drive(gamepad1.left_stick_y *(22.5 * Math.PI));
            }*/

            //claw.move((int)gamepad2.right_stick_y, (int)gamepad2.left_stick_y, (gamepad2.left_stick_x)*360);

            wheels.rightMotor.setPower(gamepad1.right_stick_y);
            wheels.leftMotor.setPower(gamepad1.left_stick_y);

            claw.shoulderElevate.setPower(gamepad2.left_stick_y);
            claw.elbow.setPower(gamepad2.right_stick_y);
            claw.shoulderRotate.setPower(gamepad2.left_stick_x);
            claw.wrist.setPower(gamepad2.right_stick_x);
            claw.setHandPosition(gamepad2.right_trigger);
        }
    }

}
