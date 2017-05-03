package at.preproject.point2tick.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.preproject.point2tick.R;
import at.preproject.point2tick.trip.BaseTrip;

/**
 * Created by Mike on 03.05.2017.
 */

public class TripOptionsTripTabFragment extends TripTabFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_options, container, false);

        return view;
    }

    public static TripOptionsTripTabFragment newInstance(String title) {
        TripOptionsTripTabFragment fragment = new TripOptionsTripTabFragment();
        Bundle args = new Bundle();
        args.putString(TripTabFragment.KEY_ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }
}
