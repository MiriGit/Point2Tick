package at.preproject.point2tick.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
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

    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_trip_repeat, container, false);
        init(mView);
        return mView;
    }

    private void init(View view) {
        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_monday)).setOnCheckedChangeListener(this);
        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_monday)).setChecked(TripActivity.baseTrip.flags.isFlagSet(BaseTrip.FLAG_DAY_MONDAY));

        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_tuesday)).setOnCheckedChangeListener(this);
        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_tuesday)).setChecked(TripActivity.baseTrip.flags.isFlagSet(BaseTrip.FLAG_DAY_TUESDAY));

        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_wednesday)).setOnCheckedChangeListener(this);
        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_wednesday)).setChecked(TripActivity.baseTrip.flags.isFlagSet(BaseTrip.FLAG_DAY_WEDNESDAY));

        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_thursday)).setOnCheckedChangeListener(this);
        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_thursday)).setChecked(TripActivity.baseTrip.flags.isFlagSet(BaseTrip.FLAG_DAY_THURSDAY));

        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_friday)).setOnCheckedChangeListener(this);
        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_friday)).setChecked(TripActivity.baseTrip.flags.isFlagSet(BaseTrip.FLAG_DAY_FRIDAY));

        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_saturday)).setOnCheckedChangeListener(this);
        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_saturday)).setChecked(TripActivity.baseTrip.flags.isFlagSet(BaseTrip.FLAG_DAY_SATURDAY));

        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_sunday)).setOnCheckedChangeListener(this);
        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_sunday)).setChecked(TripActivity.baseTrip.flags.isFlagSet(BaseTrip.FLAG_DAY_SUNDAY));

        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_daily)).setOnCheckedChangeListener(this);
        ((CheckBox) view.findViewById(R.id.fragment_trip_repeat_daily)).setChecked(TripActivity.baseTrip.flags.isFlagSet(BaseTrip.FLAG_DAY_DAILY));
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
        //FIXME !!!DRY!!!
        //final int flag; switch(..)... ; setFlag... sollte besser sein aber nja... :)
        switch (compoundButton.getId()) {
            case R.id.fragment_trip_repeat_monday:
                TripActivity.baseTrip.flags.setFlag(BaseTrip.FLAG_DAY_MONDAY, b);
                break;
            case R.id.fragment_trip_repeat_tuesday:
                TripActivity.baseTrip.flags.setFlag(BaseTrip.FLAG_DAY_TUESDAY, b);
                break;
            case R.id.fragment_trip_repeat_wednesday:
                TripActivity.baseTrip.flags.setFlag(BaseTrip.FLAG_DAY_WEDNESDAY, b);
                break;
            case R.id.fragment_trip_repeat_thursday:
                TripActivity.baseTrip.flags.setFlag(BaseTrip.FLAG_DAY_THURSDAY, b);
                break;
            case R.id.fragment_trip_repeat_friday:
                TripActivity.baseTrip.flags.setFlag(BaseTrip.FLAG_DAY_FRIDAY, b);
                break;
            case R.id.fragment_trip_repeat_saturday:
                TripActivity.baseTrip.flags.setFlag(BaseTrip.FLAG_DAY_SATURDAY, b);
                break;
            case R.id.fragment_trip_repeat_sunday:
                TripActivity.baseTrip.flags.setFlag(BaseTrip.FLAG_DAY_SUNDAY, b);
                break;
            case R.id.fragment_trip_repeat_daily:
                TripActivity.baseTrip.flags.setFlag(BaseTrip.FLAG_DAY_DAILY, b);
                break;
            default:
                break;
        }
    }
}
