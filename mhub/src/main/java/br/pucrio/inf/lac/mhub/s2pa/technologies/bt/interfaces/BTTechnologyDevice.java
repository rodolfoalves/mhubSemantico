package br.pucrio.inf.lac.mhub.s2pa.technologies.bt.interfaces;

import java.util.List;

import br.pucrio.inf.lac.mhub.models.locals.SensorData;
import br.pucrio.inf.lac.mhub.s2pa.base.TechnologySensor;

public interface BTTechnologyDevice {

    List<BTTechnologySensor> getServices();

    List<SensorData> convertToSensorDataList(byte[] bytes);
}
