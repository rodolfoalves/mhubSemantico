package br.pucrio.inf.lac.mhub.s2pa.technologies.bt.sensors;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.pucrio.inf.lac.mhub.models.locals.SensorData;

public class HXM030655_StridesSensor extends HXM030655_AbstractSensor {

    private static HXM030655_StridesSensor instance;

    private HXM030655_StridesSensor() {
        super();
    }

    public static HXM030655_StridesSensor getInstance() {
        if (instance == null) {
            instance = new HXM030655_StridesSensor();
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
        double strides = getHRSpeedDistPacketInfo().GetStrides(bytes);
        return new Double[]{strides};
    }

    public SensorData convertToSensorData(byte[] bytes) throws UnsupportedOperationException {
        Double[] values = convert(bytes);
        SensorData sensorData= new SensorData();
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
