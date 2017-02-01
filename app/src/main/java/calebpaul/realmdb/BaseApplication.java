package calebpaul.realmdb;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by calebpaul on 1/28/17.
 */

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        Realm realm = Realm.getDefaultInstance();
        try {
            // ... Do something ...
        } finally {
            realm.close();
        }

    }
}
