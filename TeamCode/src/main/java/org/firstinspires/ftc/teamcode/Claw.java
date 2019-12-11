package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    private Servo hand;
    private Servo wrist
    private Servo shoulderRotate
    private DcMotor shoulderElevate;


    public Claw(Servo hand, Servo wrist, Servo shoulderRotate, DcMotor shoulderElevate){
        this.hand = hand;
        this.wrist = wrist;
        this.shoulderRotate = shoulderRotate;
        this.shoulderElevate = shoulderElevate;
        shoulderElevate.setDirection(DcMotor.Direction.FORWARD); //might need to reverse
    }

    /**
     *
     * @param forwardSpeed
     * @param upSpeed
     * @param rotateSpeed between 0 and 1
     */
    public void move(float forwardSpeed, float upSpeed, float rotateSpeed){
        shoulderElevate.setPower(rotateSpeed)
    }
}
