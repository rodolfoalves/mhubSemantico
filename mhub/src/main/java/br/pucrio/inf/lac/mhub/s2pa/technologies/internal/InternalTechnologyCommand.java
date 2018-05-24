package br.pucrio.inf.lac.mhub.s2pa.technologies.internal;

import android.hardware.Sensor;

import java.util.List;

public class InternalTechnologyCommand {

    final String type;
    final List<Sensor> deviceSensors;

    public InternalTechnologyCommand(final String type, final List<Sensor> deviceSensors) {
        this.type = type;
        this.deviceSensors = deviceSensors;
    }

    public String getType() {
        return type;
    }

    public List<Sensor> getDeviceSensors() {
        return deviceSensors;
    }

}
