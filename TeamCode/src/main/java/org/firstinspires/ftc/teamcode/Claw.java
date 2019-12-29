package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {

    private Servo wrist;
    private Servo elbow;
    private Servo shoulderRotate;
    private DcMotor shoulderElevate;
    private int armA = 420;//mm
    private int armB = 225;//mm
    public Telemetry telemetry;

    public Claw(Servo wrist, Servo elbow, Servo shoulderRotate, DcMotor shoulderElevate, Telemetry telemetry){
        this.wrist = wrist;
        this.elbow = elbow;
        this.shoulderRotate = shoulderRotate;
        this.shoulderElevate = shoulderElevate;
        shoulderElevate.setDirection(DcMotor.Direction.FORWARD); //might need to reverse
        wrist.setPosition(0);
        elbow.setPosition(0);
        shoulderRotate.setPosition(0);
        shoulderElevate.setTargetPosition(0);
        shoulderElevate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.telemetry = telemetry;
    }

    /**
     *
     * @param moveX in mm
     * @param moveY in mm
     * @param rotateDegrees between -1 and 1
     */
    public void move(int moveX, int moveY, double rotateDegrees){
        int r = armA * armA + armB * armB - 2 * armA * armB * (int) Math.cos(elbow.getPosition());
        int x = (int) Math.cos(shoulderElevate.getTargetPosition()) * r;
        int y = (int) Math.sin(shoulderElevate.getTargetPosition()) * r;
        x+=moveX;
        y+=moveY;
        shoulderElevate.setTargetPosition((int)(Math.atan(y/x)/(2*Math.PI)));
        //elbow.setPosition(Math.acos(x*x+y*y-armA*armA-armB*armB+2*armA*armB)/(2*Math.PI));
        shoulderRotate.setPosition(rotateDegrees/360);
    }
}
