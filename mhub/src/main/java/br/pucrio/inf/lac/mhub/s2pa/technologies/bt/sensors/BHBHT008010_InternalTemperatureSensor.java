/**
 *
 */
package br.pucrio.inf.lac.mhub.s2pa.technologies.bt.sensors;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.pucrio.inf.lac.mhub.models.locals.SensorData;

/**
 * @author bertodetacio
 */
public class BHBHT008010_InternalTemperatureSensor extends BHBHT008010_AbstractSensor {


    private static BHBHT008010_InternalTemperatureSensor instance;

    /**
     *
     */
    public BHBHT008010_InternalTemperatureSensor() {
        // TODO Auto-generated constructor stub
    }

    public static BHBHT008010_InternalTemperatureSensor getInstance() {
        if (instance == null) {
            instance = new BHBHT008010_InternalTemperatureSensor();
        }
        return instance;
    }

    @Override
    public UUID getCalibration() {
        return null;
    }

    @Override
    public void setCalibrationData(byte[] value) throws UnsupportedOperationException {

    }

    @Override
    public Double[] convert(byte[] bytes) {
        double batteryLevel = getSummaryPacketInfo().GetDevice_Internal_Temperature(bytes);
        Double[] values = new Double[]{batteryLevel};
        return values;
    }

    public SensorData convertToSensorData(byte[] bytes) throws UnsupportedOperationException {
        double batteryLevel = getSummaryPacketInfo().GetDevice_Internal_Temperature(bytes);
        Double[] values = new Double[]{batteryLevel};
        SensorData sensorData = new SensorData();
        sensorData.setSensorName(getName());
        sensorData.setSensorValue(values);

        Map<String, String> properties = new HashMap<>();
        properties.put("measurementTime", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        sensorData.setProperties(properties);

        return sensorData;
    }

    @Override
    public int getPacketMsgID() {
        // TODO Auto-generated method stub
        return SUMMARY_MSG_ID;
    }

}
