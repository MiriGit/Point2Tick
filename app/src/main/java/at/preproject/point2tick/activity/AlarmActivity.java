package at.preproject.point2tick.activity;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import at.preproject.point2tick.R;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {
    //bundle key..
    public static final String BUNDLE_KEY_TRIP = "trip";
    // vibrate pattern, einfach so ka. is redundant eig. haha egaaaaaaal thug life
    public static final long[] VIBRATE_PATTERN = new long[] {
            100L, 100L, 100L
    };
    // unser vibrator haha :)
    @Nullable
    private Vibrator mVibrator;
    // unser media player
    @Nullable
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //wenn ma a actionBar haben dann versteckn mas, weils besser auschaut
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //na unsere view
        setContentView(R.layout.activity_alarm);
        //Da de activity auch übern lockscreen undso angezeigt werden soll
        //muss ma a paar window flags setzen ...
        getWindow()
                .addFlags(
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                );

        //FIXME ----->
        if(true) {
            //init vibrator hehe
            mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            // start vibration ... mit repeat index 0 dass wider am anfang anfängt wenn fertig
            mVibrator.vibrate(VIBRATE_PATTERN, 0);
        }

        if(true) {
            //checken ist vil redundant, außer jemand baut scheiße ...
            //nja wenns so ist setzt mas auf default
            //if(mTrip.alarmType != RingtoneManager.TYPE_ALARM && mTrip.alarmType != RingtoneManager.TYPE_RINGTONE) {
            //    Log.w(AlarmActivity.class.getSimpleName(), "onCreate() -> unbekannter alarm type!!! : " + mTrip.alarmType);
            //    mTrip.alarmType = RingtoneManager.TYPE_ALARM;
            //}
            //init media player
            mMediaPlayer = MediaPlayer.create(this, RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM));
            // music soll im loop abgespielt werden .. und start
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        }

        //lassen ma a fanzy animation starten haha ....
        final ImageView imageView = (ImageView) findViewById(R.id.activity_alarm_root_img);
        imageView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.alarm_image_anim));

        //unser button den ma zum bestätigen haben
        //TODO das könnt ma durch "slide to unlick view" ersätzen ... wenn wir a seekbar umbauen wollen :3
        final Button button = (Button) findViewById(R.id.activity_alarm_ok);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //alles stoppen und finish()
        //finish() ist sowas wie System.exit(code) nur für android ...
        if(mVibrator != null)
            mVibrator.cancel();
        if(mMediaPlayer != null)
            mMediaPlayer.stop();
        finish();
    }
}
