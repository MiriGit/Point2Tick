package at.preproject.point2tick.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import at.preproject.point2tick.R;
import at.preproject.point2tick.activity.TripActivity;
import at.preproject.point2tick.trip.BaseTrip;

/**
 * Created by Mike on 03.05.2017.
 */

public class TripRepeatTripTabFragment extends TripTabFragment implements CompoundButton.OnCheckedChangeListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_trip_repeat, container, false);
        initCheckbox((CheckBox) view.findViewById(R.id.fragment_trip_repeat_monday), BaseTrip.FLAG_DAY_MONDAY);
        initCheckbox((CheckBox) view.findViewById(R.id.fragment_trip_repeat_tuesday), BaseTrip.FLAG_DAY_TUESDAY);
        initCheckbox((CheckBox) view.findViewById(R.id.fragment_trip_repeat_wednesday), BaseTrip.FLAG_DAY_WEDNESDAY);
        initCheckbox((CheckBox) view.findViewById(R.id.fragment_trip_repeat_thursday), BaseTrip.FLAG_DAY_THURSDAY);
        initCheckbox((CheckBox) view.findViewById(R.id.fragment_trip_repeat_friday), BaseTrip.FLAG_DAY_FRIDAY);
        initCheckbox((CheckBox) view.findViewById(R.id.fragment_trip_repeat_saturday), BaseTrip.FLAG_DAY_SATURDAY);
        initCheckbox((CheckBox) view.findViewById(R.id.fragment_trip_repeat_sunday), BaseTrip.FLAG_DAY_SUNDAY);
        initCheckbox((CheckBox) view.findViewById(R.id.fragment_trip_repeat_daily), BaseTrip.FLAG_DAY_DAILY);
        return view;
    }

    private void initCheckbox(CheckBox checkBox, int flag) {
        checkBox.setChecked(getTrip() != null && getTrip().flags.isFlagSet(flag));
        checkBox.setOnCheckedChangeListener(this);
    }

    public static TripRepeatTripTabFragment newInstance(String title) {
        TripRepeatTripTabFragment fragment = new TripRepeatTripTabFragment();
        Bundle args = new Bundle();
        args.putString(TripTabFragment.KEY_ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int flag;
        switch (compoundButton.getId()) {
            case R.id.fragment_trip_repeat_monday:
                flag = BaseTrip.FLAG_DAY_MONDAY;
                break;
            case R.id.fragment_trip_repeat_tuesday:
                flag = BaseTrip.FLAG_DAY_TUESDAY;
                break;
            case R.id.fragment_trip_repeat_wednesday:
                flag = BaseTrip.FLAG_DAY_WEDNESDAY;
                break;
            case R.id.fragment_trip_repeat_thursday:
                flag = BaseTrip.FLAG_DAY_THURSDAY;
                break;
            case R.id.fragment_trip_repeat_friday:
                flag = BaseTrip.FLAG_DAY_FRIDAY;
                break;
            case R.id.fragment_trip_repeat_saturday:
                flag = BaseTrip.FLAG_DAY_SATURDAY;
                break;
            case R.id.fragment_trip_repeat_sunday:
                flag = BaseTrip.FLAG_DAY_SUNDAY;
                break;
            case R.id.fragment_trip_repeat_daily:
                flag = BaseTrip.FLAG_DAY_DAILY;
                break;
            default:
                return;
        }
        if(getTrip() != null) {
            getTrip().flags.setFlag(flag, b);
        }
    }
}
