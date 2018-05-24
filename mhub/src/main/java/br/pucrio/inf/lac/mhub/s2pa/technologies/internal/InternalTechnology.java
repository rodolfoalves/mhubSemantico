package br.pucrio.inf.lac.mhub.s2pa.technologies.internal;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.pucrio.inf.lac.mhub.components.MOUUID;
import br.pucrio.inf.lac.mhub.s2pa.base.Technology;
import br.pucrio.inf.lac.mhub.s2pa.base.TechnologyListener;
import br.pucrio.inf.lac.mhub.s2pa.technologies.TechnologyID;

public class InternalTechnology implements Technology {

    /** Service context */
    private Context ac;

    private String mMacAddress;

    /** Mobile Object Unique Identifier */
    private MOUUID mMOUUID;

    private String getMacAddress(Context ac) {
        final WifiManager wifiManager = (WifiManager) ac.getSystemService(Context.WIFI_SERVICE);
        final WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }

    /** Listener implemented by the S2PA service */
    private TechnologyListener listener;

    /** Flags */
    private boolean autoConnect = false;

    private SensorManager mSensorManager;

    /** Internal sensor list */
    private List<Sensor> mDeviceSensors;

    /** Services provided */
    private List<String> mServices;

    public InternalTechnology( Context context ) {
        this.ac = context;
        mMacAddress = getMacAddress(ac);
        mMOUUID = new MOUUID(TechnologyID.INTERNAL.id, mMacAddress);
    }

    @Override
    public boolean initialize() {
        mServices = new ArrayList<>();
        return true;
    }

    private void discoverServices() {


        for (Sensor s : mDeviceSensors) {
            mServices.add(s.getName());
        }

        listener.onMObjectServicesDiscovered( mMOUUID, mServices );

    }

    private void bootstrap() {
        if( listener != null ) {
            listener.onMObjectFound(mMOUUID, 0.0);
        }
        listener.onMObjectConnected( mMOUUID );

        mSensorManager = (SensorManager) ac.getSystemService(Context.SENSOR_SERVICE);
        mDeviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

    }

    @Subscribe
    public void on(InternalTechnologyCommand command) {

        if (command.getType().equals("start")) {
            for(Sensor s : command.getDeviceSensors()) {
                mSensorManager.registerListener(mSensorListener, s, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        else if (command.getType().equals("stop")) {
            for(Sensor s : command.getDeviceSensors()) {
                mSensorManager.unregisterListener(mSensorListener, s);
            }
        }
        else {
            new RuntimeException("Unknown InternalTechnologyCommand type: " + command.getType());
        }
    }

    @Override
    public void enable() throws NullPointerException {
        EventBus.getDefault().register(this);
        bootstrap();
        discoverServices();
    }

    @Override
    public void setListener(TechnologyListener listener) {
        this.listener = listener;
    }

    @Override
    public void startScan(boolean autoConnect) throws NullPointerException {
        this.autoConnect = autoConnect;
    }

    @Override
    public void stopScan() throws NullPointerException {

    }

    @Override
    public void readSensorValue(String macAddress, String serviceName) {

    }

    @Override
    public void writeSensorValue(String macAddress, String serviceName, Object value) {

    }

    @Override
    public boolean connect(String macAddress) {
        return true;
    }

    @Override
    public boolean disconnect(String macAddress) {
        return false;
    }

    @Override
    public void addToBlackList(String macAddress) {
    }

    @Override
    public boolean removeFromBlackList(String macAddress) {
        return false;
    }

    @Override
    public void clearBlackList() {
    }

    @Override
    public boolean isInBlackList(String macAddress) {
        return false;
    }

    @Override
    public void addToWhiteList(String macAddress) {

    }

    @Override
    public boolean removeFromWhiteList(String macAddress) {
        return false;
    }

    @Override
    public void clearWhiteList() {

    }

    @Override
    public boolean isInWhiteList(String macAddress) {
        return false;
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
        listener.onMObjectDisconnected( mMOUUID, mServices );
    }

    private SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float[] v = sensorEvent.values;
            final Double[] data = new Double[v.length];

            for(int i = 0; i < v.length; i++) {
                data[i] = (double) v[i];
            }

            Map<String, String> properties = new HashMap<>();
            properties.put("accuracy", String.valueOf(sensorEvent.accuracy));
            properties.put("resolution", String.valueOf(sensorEvent.sensor.getResolution()));
            properties.put("measurementTime", String.valueOf(sensorEvent.timestamp));

            // Inform to the S2PA Service
            if( data != null ) {
                listener.onMObjectValueRead(mMOUUID, 0.0, sensorEvent.sensor.getName(), data, properties);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    };


}
