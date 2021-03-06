package br.pucrio.inf.lac.mhub;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import org.acra.ACRA;

import br.pucrio.inf.lac.mhub.injection.component.ApplicationComponent;
import br.pucrio.inf.lac.mhub.injection.component.DaggerApplicationComponent;
import br.pucrio.inf.lac.mhub.injection.component.DaggerInjectionComponent;
import br.pucrio.inf.lac.mhub.injection.component.InjectionComponent;
import br.pucrio.inf.lac.mhub.injection.module.ApplicationModule;

public class MobileHub extends Application {
    /** Component that build the dependencies */
    private static InjectionComponent mComponent;

	@Override
    public void onCreate() {
        super.onCreate();
        ACRA.init( this );

        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( this ) )
                .build();

        mComponent = DaggerInjectionComponent.builder()
                .applicationComponent( appComponent )
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext( base );
        MultiDex.install( this );
    }

    public static InjectionComponent getComponent() {
        return mComponent;
    }


}
