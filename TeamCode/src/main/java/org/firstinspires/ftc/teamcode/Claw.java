package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {

    private Servo hand;
    private EncodedServo wrist;
    private EncodedServo elbow;
    private EncodedServo shoulderRotate;
    private DcMotor shoulderElevate;
    private static final int armA = 420;//mm
    private static final int armB = 225;//mm
    public Telemetry telemetry;

    public Claw(Servo hand, CRServo wrist, CRServo elbow, CRServo shoulderRotate, DcMotor shoulderElevate, Telemetry telemetry, Compass compass0, Compass compass1, Compass compass2){
        this.hand = hand;
        this.wrist = new EncodedServo(wrist, compass2, compass1);
        this.elbow = new EncodedServo(elbow, compass1, compass0);
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
        int r = armA * armA + armB * armB - 2 * armA * armB * (int) Math.cos(elbow.getTargetPosition());
        int x = (int) Math.cos(shoulderElevate.getTargetPosition()) * r;
        int y = (int) Math.sin(shoulderElevate.getTargetPosition()) * r;
        x+=moveX;
        y+=moveY;
        shoulderElevate.setTargetPosition((int)((Math.atan(y/x)/(2*Math.PI))*288));
        elbow.setPosition(Math.acos(x*x+y*y-armA*armA-armB*armB+2*armA*armB));
        shoulderRotate.setPosition(rotateDegrees/180*Math.PI);
        wrist.setTargetPosition(-(elbow.getTargetPosition()+shoulderElevate.getTargetPosition()*2*Math.PI));
    }
}
