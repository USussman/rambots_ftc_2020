package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;

class EncodedServo extends Thread{
    private CRServo motor;
    private LSM303a above;
    private LSM303a below;
    private double targetPosition;
    private double position;

    public EncodedServo(CRServo motor, LSM303a above, LSM303a below){
        this.motor = motor;
        this.above = above;
        this.below = below;
    }

    public void setTargetPosition(double target) {
        this.targetPosition = target;
    }

    public double getTargetPosition() {
        return targetPosition;
    }

    public double getPosition()  {
        return position;
    }

    @Override
    public void run() {
        //Update position
        position = (double) (below.getAccelerometer()[1] - above.getAccelerometer()[1]);

        if(position > targetPosition)   {
            motor.setPower(-1);
        }
        if(position <targetPosition)    {
            motor.setPower(1);
        }
        if(position == targetPosition)  {
            motor.setPower(0);
        }
    }
}
