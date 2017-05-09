package at.preproject.point2tick.fragments;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import at.preproject.point2tick.activity.TripActivity;
import at.preproject.point2tick.trip.BaseTrip;

/**
 * Created by Mike on 03.05.2017.
 */

public abstract class TripTabFragment extends Fragment {
    //keys
    public static final String KEY_ARG_TITLE = "title";
    /**
     *  get trip
     * @return trip|null
     */
    @Nullable
    public BaseTrip getTrip() {
        Activity activity = getActivity();
        if(activity != null && activity instanceof TripActivity) {
            return ((TripActivity) activity).getTrip();
        }
        return null;
    }
    /**
     * get title
     * @return title
     */
    public String getTitle() {
        return getArguments().getString(KEY_ARG_TITLE, "default");
    }
}
