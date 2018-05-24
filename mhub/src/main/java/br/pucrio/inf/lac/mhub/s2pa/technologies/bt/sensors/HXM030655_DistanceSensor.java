package br.pucrio.inf.lac.mhub.s2pa.technologies.bt.sensors;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.pucrio.inf.lac.mhub.models.locals.SensorData;

public class HXM030655_DistanceSensor extends HXM030655_AbstractSensor {

    private static HXM030655_DistanceSensor instance;

    private HXM030655_DistanceSensor() {
        // TODO Auto-generated constructor stub
    }

    public static HXM030655_DistanceSensor getInstance() {
        if (instance == null) {
            instance = new HXM030655_DistanceSensor();
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
    public synchronized Double[] convert(byte[] bytes) {
        // TODO Auto-generated method stub
        double distance = getHRSpeedDistPacketInfo().GetDistance(bytes);
        return new Double[]{distance};
    }

    public SensorData convertToSensorData(byte[] bytes) throws UnsupportedOperationException {
        Double[] values = convert(bytes);
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
        return HR_SPD_DIST_PACKET;
    }


}
