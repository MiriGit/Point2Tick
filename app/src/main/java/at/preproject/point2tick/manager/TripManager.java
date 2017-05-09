package at.preproject.point2tick.manager;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import at.preproject.point2tick.activity.TripActivity;
import at.preproject.point2tick.trip.BaseTrip;

/**
 * Created by Mike on 03.05.2017.
 */

public class TripManager {
    //pref keys
    private static final String KEY_COUNT = "c";
    private static final String KEY_PREFIX = "t";
    //file name
    public static final String PREFERENCES_FILE = TripManager.class.getSimpleName() + "_PREFERENCES";
    //prefs..
    private final SharedPreferences mPreferences;
    // mem collection
    private final ArrayList<BaseTrip> mTrips = new ArrayList<>();

    public TripManager(final SharedPreferences preferences) {
        this.mPreferences = preferences;
    }
    /**
     * gets the actual saved count
     * @return count of saved trips
     */
    public int getSavedCount() {
        return mPreferences.getInt(KEY_COUNT, 0);
    }
    /**
     * gets the count of the trips which are currently in memory
     * @return count of trips in memory
     */
    public int getMemCount() {
        return mTrips.size();
    }
    /**
     * get trips
     * @return trips
     */
    public ArrayList<BaseTrip> getTrips() {
        return mTrips;
    }
    /**
     * add trip
     * @param trip trip
     */
    public void addTrip(@NonNull BaseTrip trip) {
        if(trip.id == -1) {
            trip.id = getMemCount();
        }
        mTrips.add(trip);
    }


    private class LoadTripsTask extends AsyncTask<Void, Integer, Boolean> {

        private final OnUpdateListener mListener;

        private LoadTripsTask(final OnUpdateListener listener) {
            this.mListener = listener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            synchronized (mTrips) {
                mTrips.clear();
                final int total = getSavedCount();
                String encoded;
                JSONObject object;
                BaseTrip trip;
                for (int i = 0; i < total; i++) {
                    encoded = mPreferences.getString(KEY_PREFIX + i, "");
                    if (!encoded.isEmpty()) {
                        encoded = new String(Base64.decode(encoded.getBytes(), Base64.DEFAULT));
                        try {
                            object = new JSONObject(encoded);
                            trip = BaseTrip.parseJson(object);
                            trip.id = i;
                            mTrips.add(i, BaseTrip.parseJson(object));
                        } catch (JSONException jse) {
                            Log.e(getClass().getSimpleName(), "json", jse);
                        }
                        publishProgress(i, total);
                    }
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mListener.onFinish(aBoolean);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mListener.onUpdate(values[0], values[1]);
        }
    }

    private class SaveTripsTask extends AsyncTask<BaseTrip, Integer, Boolean> {

        private final OnUpdateListener mListener;

        private SaveTripsTask(final OnUpdateListener listener) {
            this.mListener = listener;
        }

        @Override
        protected Boolean doInBackground(BaseTrip... baseTrips) {
            final SharedPreferences.Editor editor = mPreferences.edit();
            editor.clear();
            String encoded;
            for (int i = 0; i < baseTrips.length; i++) {
                encoded = Base64.encodeToString(baseTrips[i].toJson().toString().getBytes(), Base64.DEFAULT);
                editor.putString(KEY_PREFIX + i, encoded);
                //publish progress {current, total}
                publishProgress(i, baseTrips.length);
            }
            editor.putInt(KEY_COUNT, baseTrips.length);
            editor.apply();
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mListener.onUpdate(values[0], values[1]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mListener.onFinish(aBoolean);
        }
    }

    public interface OnUpdateListener {
        /**
         * wird aufgerufen wenn der task fertig ist
         * @param success ob es erfolgreich war
         */
        public void onFinish(boolean success);

        /**
         * on update
         * @param current wo es gerade ist
         * @param total wie viel es total gibt
         */
        public void onUpdate(int current, int total);
    }
}
