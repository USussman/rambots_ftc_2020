package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class AutoRed extends LinearOpMode {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private Wheels2 wheels;

    private Servo grabber;

    private BNO055IMU imu;
    private Orientation angles;

    private Runnable compassReader;
    private double heading;
    private double maxSpeed;

    private Camera brick;


    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        // Initialize wheels
        leftMotor  = hardwareMap.get(DcMotor.class, "leftMotor");
        rightMotor  = hardwareMap.get(DcMotor.class, "rightMotor");
        wheels = new Wheels2(leftMotor, rightMotor);
        maxSpeed = 22.5 * Math.PI;

        // Initialize grabber
        grabber = hardwareMap.get(Servo.class, "graber");

        // Initialize BNO055 IMU
        imuInit();
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        heading = angles.firstAngle;

        brick.activate();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Find Brick
            // move toward brick
            turnInPlace((int) brick.angle);
            wheels.drive(maxSpeed);
            while (brick.distance < n) {}
            wheels.stopDriving();


            // regain heading
            turnInPlace(0);

            // grab
            grabber.setPosition(1/3);

            // back up
            wheels.drive(maxSpeed);
            sleep(100);
            wheels.stopDriving();

            // turn to 90 deg (direction dependent on alliance color)
            turnInPlace(90);

            //drive straight until line
            wheels.drive(maxSpeed);
            while (lineSensor.active == false) {}
            wheels.stopDriving();

            // lift grabber
            grabber.setPosition(0);

            // drive straight
            wheels.drive(maxSpeed);
            sleep(050);

            // turn toward base
            turnInPlace(180);

            // engage grabber
            grabber.setPosition(1/3);

            // turnInPlace 90 def away from base
            turnInPlace(90);

            // disengage grabber
            grabber.setPosition(0);

            // drive back until line
            wheels.drive(-maxSpeed);
            while (lineSensor.active == false) {}
            wheels.stopDriving();
        }
    }

    void imuInit() {
        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
//        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        compassReader = new Runnable() { @Override public void run()
            {
                // Acquiring the angles is relatively expensive; we don't want
                // to do that in each of the three items that need that info, as that's
                // three times the necessary expense.
                angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            }
        };

        // Uncomment if need to constantly use compass
        // compassReader.start();
    }

    /**
     *
     * @param degrees [-180, 180]
     */
    private void turnInPlace(int degrees) {
        while (!((angles.firstAngle - heading) > (degrees - 5) && (angles.firstAngle - heading) < (degrees + 5))) {
            wheels.turn((degrees - ((angles.firstAngle - heading + 180) % 360 - 180)) / 180 * maxSpeed);
        }
    }

}
