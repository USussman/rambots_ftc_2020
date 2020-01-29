package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutoOpMode2", group="AutoOpModes")

public class Auto2 extends LinearOpMode {
    private Servo hand;
    private CRServo wristServo;
    private CRServo elbowServo;
    private CRServo shoulderRotate;
    private DcMotor shoulderElevate;

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private DcMotor brickLoaderMotor;

    private Claw2 claw;
    private Wheels2 wheels;
    private BrickLoader2 brickLoader;

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        brickLoaderMotor  = hardwareMap.get(DcMotor.class, "brickLoader");
        leftMotor  = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor  = hardwareMap.get(DcMotor.class, "rightMotor");
        shoulderElevate  = hardwareMap.get(DcMotor.class, "shoulderElevate");
        wristServo  = hardwareMap.get(CRServo.class, "wrist");
        elbowServo  = hardwareMap.get(CRServo.class, "elbow");
        shoulderRotate  = hardwareMap.get(CRServo.class, "shoulderRotate");
        hand = hardwareMap.get(Servo.class, "hand");

        wheels = new Wheels2(leftMotor, rightMotor);
        /*claw.claw.shoulderElevate.setPower(-1);
        claw.elbow.setPower(1);//2
        claw.shoulderRotate.setPower(0);//0
        claw.wrist.setPower(-1);//1*/
        waitForStart();
        runtime.reset();
        double speed =  60;
       // while (opModeIsActive()) {
            /*claw.claw.shoulderElevate.setPower(0);
            claw.elbow.setPower(0;//2
            claw.shoulderRotate.setPower(0);//0
            claw.wrist.setPower(0);//1*/

            //moveFoward(5, speed);
            runtime.reset();
            while(runtime.seconds()<20){}
                //turn(speed ,false,1);
            moveFoward(0.75, speed);
            turn(0.7 ,true);
            moveFoward(0.9, speed);

        //}
    }

    /**
     *
     * @param distance to move in 1 = 240
     * @param speed of the robot in cm/s
     */
    public void moveFoward(double distance, double speed){
        runtime.reset();
        wheels.rightMotor.setPower(1);
        wheels.leftMotor.setPower(1);
        while(runtime.seconds()<distance) {
            telemetry.addData("Time", runtime.time()-runtime.startTime());
            telemetry.update();
        }
        wheels.rightMotor.setPower(0);
        wheels.leftMotor.setPower(0);

    }
   /* public void forward(double speed){
        double starttime = runtime.time();
        wheels.leftMotor.setPower(1);
        wheels.rightMotor.setPower(1);
        while (starttime + 3 > runtime.time()){
        }
        wheels.leftMotor.setPower(0);
        wheels.rightMotor.setPower(0);
    }*/

    /**
     *
     * //@param distance to move in cm
     * @param speed of the robot in cm/s
     * @param turnDirection true if right
     * @param //angleAdjustFactor 1 for 90 degrees, 2 for 45, etc
     */
    public void turn(double speed, boolean turnDirection){//, double angleAdjustFactor){
        double distance = 36;//angleAdjustFactor;
        runtime.reset();
        wheels.rightMotor.setPower(turnDirection?-1:1);
        wheels.leftMotor.setPower(turnDirection?1:-1);
        while((runtime.seconds())<speed) {

        }
        wheels.rightMotor.setPower(0);
        wheels.leftMotor.setPower(0);

    }
}
