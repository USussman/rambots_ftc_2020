package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;

class EncodedServo implements Runnable{
    private CRServo motor;
    private Compass above;
    private Compass below;
    private double targetPosition;
    private double position;

    public EncodedServo(CRServo motor, Compass above, Compass below){
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
        position = (double) (below.angles(below.acceleration())[1] - above.angles(above.acceleration())[1]);

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
