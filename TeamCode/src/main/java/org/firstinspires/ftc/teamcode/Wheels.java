package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Wheels {
    //two hex motors 150rpm 90pi mm circumference
    private double speed; //positive is forward, negative is backward. measured in cm/sec
    private static final int width= 360; //in mm

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

    public void turn(double outerSpeed, double angle, double turnRadius){
        double innerSpeed = outerSpeed/(1+width/turnRadius);
        ((angle < 0) ? leftMotor : rightMotor).setPower(innerSpeed/(22.5 * Math.PI)); //divide by max speed in order to get number in range [-1.0, 1.0]
        ((angle > 0) ? leftMotor : rightMotor).setPower(outerSpeed/(22.5 * Math.PI)); //divide by max speed in order to get number in range [-1.0, 1.0]
        ElapsedTime time = new ElapsedTime();
        time.reset();
        //wait to complete turn
        double radians = (angle*Math.PI)/180
        while(time.time() < (speed/(radians*(turnRadius + width))));
        //go back to driving
        leftMotor.setPower(this.speed/(22.5 * Math.PI)); //divide by max speed in order to get number in range [-1.0, 1.0]
        rightMotor.setPower(this.speed/(22.5 * Math.PI)); //divide by max speed in order to get number in range [-1.0, 1.0]
    }

    /**
     *
     * @param outerSpeed cm/s
     * @param turnDirection true is right. false is left
     * @param turnRadius in mm
     */
    public void turn(double outerSpeed, boolean turnDirection, double turnRadius){
        double innerSpeed = outerSpeed/(1+width/turnRadius);
        (!turnDirection ? leftMotor : rightMotor).setPower(innerSpeed/(22.5 * Math.PI)); //divide by max speed in order to get number in range [-1.0, 1.0]
        (turnDirection ? leftMotor : rightMotor).setPower(outerSpeed/(22.5 * Math.PI)); //divide by max speed in order to get number in range [-1.0, 1.0]
    }
}
