package at.preproject.point2tick.manager;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import at.preproject.point2tick.activity.TripActivity;
import at.preproject.point2tick.trip.BaseTrip;

/**
 * Created by Mike on 03.05.2017.
 */

public class TripManager {
    //pref keys
    private static final String KEY_COUNT = "c";
    //file name
    public static final String PREFERENCES_FILE = TripManager.class.getSimpleName() + "_PREFERENCES";
    //prefs..
    private final SharedPreferences mPreferences;
    // mem collection
    private ArrayList<BaseTrip> mTrips = new ArrayList<>();

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


}
