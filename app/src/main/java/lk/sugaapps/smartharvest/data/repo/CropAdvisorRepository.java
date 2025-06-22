package lk.sugaapps.smartharvest.data.repo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CropAdvisorRepository implements SensorEventListener {
    private final MutableLiveData<Integer> luxData = new MutableLiveData<>();
    private final MutableLiveData<String> cropAdvice = new MutableLiveData<>();

    private final SensorManager sensorManager;
    private final Sensor lightSensor;

    @Inject
    public CropAdvisorRepository(SensorManager sensorManager, Sensor lightSensor) {
        this.sensorManager = sensorManager;
        this.lightSensor = lightSensor;
        luxData.setValue(0);
        cropAdvice.setValue("☀️ Waiting for light reading...");
        registerSensor();
    }

    private void registerSensor() {
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int lux = (int) event.values[0];
        updateLuxValue(lux);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void updateLuxValue(int lux) {
        luxData.setValue(lux);
        cropAdvice.setValue(getAdvice(lux));
    }

    public LiveData<Integer> getLuxData() {
        return luxData;
    }

    public LiveData<String> getCropAdvice() {
        return cropAdvice;
    }
    public static String getAdvice(int lux) {
        if (lux < 8000) {
            return "🔅 Low sunlight — not suitable\n" +
                    "குறைந்த ஒளி — பொருத்தமற்றது\n" +
                    "එළිය අඩුයි — වගාවට සුදුසු නැහැ";
        } else if (lux <= 15000) {
            return "🌱 Good for Beans\n" +
                    "Beans வளர்க்க நல்ல நேரம்\n" +
                    "Beans වගාවට හොඳයි";
        } else if (lux <= 20000) {
            return "🥬 Ideal for Cabbage\n" +
                    "Cabbage க்கு சரியான ஒளி\n" +
                    "Cabbage වලට හොඳ එළිය";
        } else if (lux <= 25000) {
            return "🥕 Best for Carrots\n" +
                    "Carrot வளர்க்க சிறந்த நேரம்\n" +
                    "Carrot වගාවට සුදුසු";
        } else if (lux <= 30000) {
            return "🍅🍆 Tomato & Brinjal thrive\n" +
                    "Tomato & Kathirikai உகந்தது\n" +
                    "Tomato හා Brinjal වගාවට හොඳයි";
        } else if (lux <= 35000) {
            return "🌶️ Good for Green Chilli\n" +
                    "Green Chilli க்கு உகந்தது\n" +
                    "Green Chilli වගාවට සුදුසු";
        } else {
            return "☀️ Too much sunlight — use shade\n" +
                    "அதிக ஒளி — நிழல் வலை பயன்படுத்தவும்\n" +
                    "එළිය වැඩියි — තාප ආවරණ භාවිත කරන්න";
        }
    }
}