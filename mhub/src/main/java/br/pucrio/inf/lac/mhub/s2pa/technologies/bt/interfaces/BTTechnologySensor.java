package br.pucrio.inf.lac.mhub.s2pa.technologies.bt.interfaces;

import br.pucrio.inf.lac.mhub.models.locals.SensorData;

public interface BTTechnologySensor {
    SensorData convertToSensorData(byte[] value) throws UnsupportedOperationException;
}
