package pl.tajchert.autoretwitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Switch;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityMain extends AppCompatActivity {
    private static final String TAG = ActivityMain.class.getSimpleName();
    public static final String KEY_FAVORITE = "favoriteStatus";
    public static final String KEY_SHARED_PREFS = "pl.tajchert.autoretwitter";
    SharedPreferences sharedPreferences;
    @Bind(R.id.switchFavorite)
    Switch switchFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(KEY_SHARED_PREFS, MODE_PRIVATE);
    }

    @OnClick(R.id.switchFavorite)
    public void switchFavClick() {
        sharedPreferences.edit().putBoolean(KEY_FAVORITE, switchFavorite.isChecked()).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        switchFavorite.setChecked(sharedPreferences.getBoolean(KEY_FAVORITE, false));
        checkPermission();
    }

    private void checkPermission() {
        if(Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners") != null) {
            if (Settings.Secure.getString(this.getContentResolver(),"enabled_notification_listeners").contains(getApplicationContext().getPackageName())) {
                //service is enabled do nothing
            } else {
                //service is not enabled try to enabled
                getApplicationContext().startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        } else {
            Log.d(TAG, "onResume no Google Play Services");
        }
    }
}