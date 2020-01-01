package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;

class EncodedServo implements Runnable{
    private CRServo motor;
    private Compass above;
    private Compass below;
    private float targetPosition;
    private float position;

    public EncodedServo(CRServo motor, Compass above, Compass below){
        this.motor = motor;
        this.above = above;
        this.below = below;
    }

    public void setTargetPosition(float target) {
        this.targetPosition = target;
    }

    public float getTargetPosition() {
        return targetPosition;
    }

    public float getPosition()  {
        return position;
    }
    @Override
    public void run() {
        //Update position
        position = (float) (below.angles(below.acceleration())[1] - above.angles(above.acceleration())[1]);

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
