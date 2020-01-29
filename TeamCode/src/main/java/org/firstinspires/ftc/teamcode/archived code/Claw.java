package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {

    private Servo hand;
    public CRServo wrist;
    public CRServo elbow;
    public CRServo shoulderRotate;
    public DcMotor shoulderElevate;
    private static final int armA = 420;//mm
    private static final int armB = 225;//mm

    public Claw(Servo hand, CRServo wrist, CRServo elbow, CRServo shoulderRotate, DcMotor shoulderElevate){
        this.hand = hand;
        this.wrist = wrist;
        this.elbow = elbow;
        //this.wrist.start();
        //this.elbow.start();
        this.shoulderRotate = shoulderRotate;
        this.shoulderElevate = shoulderElevate;
        shoulderElevate.setDirection(DcMotor.Direction.FORWARD); //might need to reverse
        shoulderElevate.setPower(0);
        //this.wrist.setTargetPosition(0);
        //this.elbow.setTargetPosition(0);
        elbow.setDirection(DcMotorSimple.Direction.FORWARD);
        elbow.setPower(0);
        wrist.setDirection(DcMotorSimple.Direction.FORWARD);
        wrist.setPower(0);
        shoulderRotate.setDirection(DcMotorSimple.Direction.FORWARD);
        shoulderRotate.setPower(0);
        //shoulderElevate.setTargetPosition(0);
        //shoulderElevate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        this.telemetry = telemetry;
    }

    /**
     *
     * @param moveX in mm
     * @param moveY in mm
     * @param rotateSpeed between -1 and 1
     */
    /*public void move(int moveX, int moveY, double rotateSpeed){
        int r = armA * armA + armB * armB - 2 * armA * armB * (int) Math.cos(elbow.getPosition());
        int x = (int) Math.cos(shoulderElevate.getCurrentPosition()) * r;
        int y = (int) Math.sin(shoulderElevate.getCurrentPosition()) * r;
        x+=moveX;
        y+=moveY;
//        telemetry.addLine("Let's go");
//        telemetry.update();
        if (x != 0) {
            shoulderElevate.setTargetPosition((int) ((Math.atan(y / x) / (2 * Math.PI)) * 288));
        }
//        telemetry.addLine("Shoulder works");
//        telemetry.update();
        elbow.setTargetPosition(Math.acos(x*x+y*y-armA*armA-armB*armB+2*armA*armB));
//        telemetry.addLine("Elbow works");
//        telemetry.update();
        shoulderRotate.setPower(rotateSpeed);
//        telemetry.addLine("Shoulder rotate works");
//        telemetry.update();
        wrist.setTargetPosition(-(elbow.getTargetPosition()+shoulderElevate.getTargetPosition()*2*Math.PI));
//        telemetry.addLine("Wrist works");
//        telemetry.update();
    }*/

    public void setHandPosition(double position){
        hand.setPosition(position);
    }
}