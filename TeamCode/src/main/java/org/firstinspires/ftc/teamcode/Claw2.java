package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw2 {

    private Servo hand;
    public CRServo wrist;
    public CRServo elbow;
    public CRServo shoulderRotate;
    public DcMotor shoulderElevate;
    private static final int armA = 420;//mm
    private static final int armB = 225;//mm

    public Claw2(Servo hand, CRServo wrist, CRServo elbow, CRServo shoulderRotate, DcMotor shoulderElevate){
        this.hand = hand;
        this.wrist = wrist;
        this.elbow = elbow;
        this.shoulderRotate = shoulderRotate;
        this.shoulderElevate = shoulderElevate;
        shoulderElevate.setDirection(DcMotor.Direction.FORWARD); //might need to reverse
        shoulderElevate.setPower(0);
        elbow.setDirection(DcMotorSimple.Direction.FORWARD);
        elbow.setPower(0);
        wrist.setDirection(DcMotorSimple.Direction.FORWARD);
        wrist.setPower(0);
        shoulderRotate.setDirection(DcMotorSimple.Direction.FORWARD);
        shoulderRotate.setPower(0);
    }


    public void setHandPosition(double position){
        hand.setPosition(position);
    }
}