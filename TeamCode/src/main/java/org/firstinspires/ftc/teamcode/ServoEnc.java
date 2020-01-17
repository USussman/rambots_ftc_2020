//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.hardware.CRServoImpl;
//import com.qualcomm.robotcore.hardware.Servo2;
//import com.qualcomm.robotcore.hardware.ServoController;
//import com.qualcomm.robotcore.util.Range;
//
//public class ServoEnc extends CRServoImpl {
//    public ServoEnc(ServoController controller, int portNumber)
//    {
//        super(controller, portNumber);
//    }
//
//    public ServoEnc(ServoController controller, int portNumber, Direction direction)
//    {
//        super(controller, portNumber, direction);
//    }
//
//    /**
//     * Returns the position to which the servo was last commanded, or Double.NaN if that is
//     * unavailable.
//     * @return the last commanded position
//     * @see #setPosition(double)
//     * @see Double#isNaN(double)
//     */
//    synchronized public double getPosition() {
//        double position = controller.getServoPosition(portNumber);
//        if (direction == Servo2.Direction.REVERSE) position = reverse(position);
//        double scaled = Range.scale(position, limitPositionMin, limitPositionMax, MIN_POSITION, MAX_POSITION);
//        return Range.clip(scaled, MIN_POSITION, MAX_POSITION);
//    }
//
//    /**
//     * Commands the servo to move to a designated position. This method initiates the movement;
//     * the servo will arrive at the commanded position at some later time.
//     *
//     * @param position the commanded servo position. Should be in the range [0.0, 1.0].
//     * @see #getPosition()
//     */
//    synchronized public void setPosition(double position) {
//        position = Range.clip(position, MIN_POSITION, MAX_POSITION);
//        if (direction == Servo2.Direction.REVERSE) position = reverse(position);
//        double scaled = Range.scale(position, MIN_POSITION, MAX_POSITION, limitPositionMin, limitPositionMax);
//        internalSetPosition(scaled);
//    }
//
//    private double reverse(double position) {
//        return MAX_POSITION - position + MIN_POSITION;
//    }
//
//
//}
