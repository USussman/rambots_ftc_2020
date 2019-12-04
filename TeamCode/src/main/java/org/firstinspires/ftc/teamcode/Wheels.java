package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Wheels {
    //two hex motors 150rpm 90pi mm circumference
    private double speed; //positive is forward, negative is backward. measured in cm/sec
    private static final int width= 36; //in cm

    private DcMotor leftMotor;
    private DcMotor rightMotor;


    public Wheels(DcMotor lm, DcMotor rm){
        leftMotor = lm;
        rightMotor = rm;
        leftMotor.setDirection(DcMotor.Direction.FORWARD); //might need to reverse
        rightMotor.setDirection(DcMotor.Direction.REVERSE); //might need to reverse
    }

    public void drive(double speed){
        this.speed = speed;
        leftMotor.setPower(speed/(22.5 * Math.PI)); //divide by max speed in order to get number in range [-1.0, 1.0]
        rightMotor.setPower(speed/(22.5 * Math.PI)); //divide by max speed in order to get number in range [-1.0, 1.0]
    }

    public void turn(double speed, double angle, double turnRadius){
        float innerSpeed = speed/(1+width/turnRadius);
        ((angle < 0) ? leftMotor : rightMotor).setPower(innerSpeed/(22.5 * Math.PI)); //divide by max speed in order to get number in range [-1.0, 1.0]
        ((angle > 0) ? leftMotor : rightMotor).setPower(this.speed/(22.5 * Math.PI)); //divide by max speed in order to get number in range [-1.0, 1.0]
        ElapsedTime time = new ElapsedTime();
        time.reset();
        while(time.time() < ());

        leftMotor.setPower(this.speed/(22.5 * Math.PI)); //divide by max speed in order to get number in range [-1.0, 1.0]
        rightMotor.setPower(this.speed/(22.5 * Math.PI)); //divide by max speed in order to get number in range [-1.0, 1.0]
    }
}
