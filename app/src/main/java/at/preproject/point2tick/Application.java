package at.preproject.point2tick;

import android.Manifest;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import at.preproject.point2tick.activity.TripActivity;
import at.preproject.point2tick.adapter.TripListAdapter;
import at.preproject.point2tick.manager.TripManager;
import at.preproject.point2tick.service.BackgroundService;
import at.preproject.point2tick.trip.BaseTrip;

public class Application extends AppCompatActivity implements TripListAdapter.OnEditClickListener {
    //result keys
    public static final int RESULT_REQUEST_CODE = 1337;
    public static final String RESULT_KEY_TRIP = "trip";
    //manager
    public TripManager tripManager = null;
    // list adapter
    private TripListAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //action bar init
        setupActionBar();
        //unser main content view
        setContentView(R.layout.activity_application);
        //init
        if(savedInstanceState == null) {
            tripManager = new TripManager(getSharedPreferences(TripManager.PREFERENCES_FILE, MODE_PRIVATE));
            //FIXME remove
            tripManager.addTrip(BaseTrip.newDefaultInstance());
            tripManager.addTrip(BaseTrip.newDefaultInstance());
            tripManager.addTrip(BaseTrip.newDefaultInstance());
            tripManager.addTrip(BaseTrip.newDefaultInstance());
            tripManager.addTrip(BaseTrip.newDefaultInstance());
            tripManager.addTrip(BaseTrip.newDefaultInstance());
            //FIXME END

            mAdapter = new TripListAdapter(this, this, tripManager.getTrips());
            final ListView listView = (ListView) findViewById(R.id.activity_application_list);
            listView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Object obj = data.getExtras().getSerializable(RESULT_KEY_TRIP);
                if(obj != null) {
                    try {
                        BaseTrip trip = BaseTrip.class.cast(obj);
                        //TODO handle result

                        //FIXME tmp
                        tripManager.addTrip(trip);
                    } catch (ClassCastException cce) {
                        Log.w(getClass().getSimpleName(), "onActivityResult()", cce);
                    }
                }
            }
        }
    }

    @Override
    public void onEdit(BaseTrip trip) {
        showTripActivity(trip);
    }

    /**
     * Zeigt die {@link TripActivity} Activity
     * @param trip wenn nicht null werden die details der activity übergeben
     */
    public void showTripActivity(@Nullable BaseTrip trip) {
        final Intent intent = new Intent(this, TripActivity.class);
        final Bundle bundle = new Bundle();
        TripActivity.baseTrip = trip;
        bundle.putBoolean(TripActivity.KEY_ARG_TRIP_NEW, (trip == null));
        intent.putExtras(bundle);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Wenn man über android M sind check ma de permission und fragen auch obs gewährt is
        //unter M sind die permission schon alle akzeptier :)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, 1337);
            }
        }
    }

    /************************ PERMISSION STUFF ************************/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //FIXME da gibs n bug oder sowas beim erstn start wird die permission abgelehnt ka. wieso mal googlen später
        //Check ma de permissions mit unseren code 1337 hehehe elite HaXor ^^
        if(requestCode == 1337) {
            ArrayList<String> deniedPermissions = new ArrayList<>();
            for (int i = 0;i < permissions.length;i++) {
                if(grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    deniedPermissions.add(permissions[i]);
                }
            }
            //Oha da will jmd die permissions nicht akzeptieren
            //mussma handlen, so ein penner ...
            if(deniedPermissions.size() > 0) {
                handlePermissionDenied(deniedPermissions);
            }
        }
    }

    /**
     * Zeig einen alarmdialog und bietet die option zu beenden oder zu den einstellungen zu gehen
     * @param permissions denied permissions
     */
    private void handlePermissionDenied(ArrayList<String> permissions) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_permission_title)
                .setMessage(R.string.alert_permission_content)
                .setNegativeButton(R.string.alert_permission_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // da zeigen wir die app settings um dem benutzer die möglichkeit zu geben
                        // die permission settings zu ändern
                        //TODO zeigen welche permission verweigert wurden evntl.
                        final Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        final Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        finish();
                    }
                })
                .setPositiveButton(R.string.alert_permission_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    /******************************* MENU / ACTIONBAR STUFF *******************/
    /**
     * Initialisiert unsere ActionBar und macht sie "schön"
     */
    public void setupActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_launcher);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Menu inflate'n haha
        getMenuInflater().inflate(R.menu.menu_application, menu);
        //Wenn unser service läuft müss ma den text änder zu Auschalten oda so wie auch immer ds in der string file steht...
        if(isServiceRunning()) {
            menu.findItem(R.id.menu_application_service).setTitle(R.string.menu_application_service_disable);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_application_service:
                if(isServiceRunning()) {
                    stopService();
                    item.setTitle(R.string.menu_application_service_enable);
                } else {
                    startService();
                    item.setTitle(R.string.menu_application_service_disable);
                }
                return true;
            case R.id.menu_application_settings:
                //TODO view
                break;
            case R.id.menu_application_about:
                //TODO view
                break;
            case R.id.menu_application_add_trip:
                showTripActivity(null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //******************************* BackgroundService stuff *******************************//
    /**
     * Startet unseren {@link BackgroundService}
     */
    public void startService() {
        final Intent intent = new Intent(this, BackgroundService.class);
        startService(intent);
    }
    /**
     * Frägt ab ob unser {@link BackgroundService} läuft
     * @return true if running
     */
    public boolean isServiceRunning() {
        //hol ma uns an activityManager
        final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        // schauen ob unser BackgroundService dabei ist ....
        for (ActivityManager.RunningServiceInfo serviceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(BackgroundService.class.getName().equals(serviceInfo.service.getClassName())) {
                //OK service läuft \('_')/
                return true;
            }
        }
        return false;
    }
    /**
     * Stoppt unseren {@link BackgroundService}
     */
    public void stopService() {
        final Intent intent = new Intent(this, BackgroundService.class);
        stopService(intent);
    }

    /********************************** onBack() handle ****************/
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_exit_title)
                .setMessage(R.string.alert_exit_content)
                .setPositiveButton(R.string.alert_exit_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.alert_exit_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
}