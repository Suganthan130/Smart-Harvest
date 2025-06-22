package lk.sugaapps.smartharvest.di.hilt;

import android.content.Context;
import android.hardware.SensorManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import lk.sugaapps.smartharvest.data.repo.UserRepository;
import lk.sugaapps.smartharvest.utils.DialogUtils;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public UserRepository provideUserRepository(@ApplicationContext Context context) {
        return new UserRepository(context);
    }

    @Provides
    @Singleton
    public DialogUtils provideDialogUtils() {
        return new DialogUtils();
    }

    @Provides
    @Singleton
    public SensorManager provideSensorManager(@ApplicationContext Context context) {
        return (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

}