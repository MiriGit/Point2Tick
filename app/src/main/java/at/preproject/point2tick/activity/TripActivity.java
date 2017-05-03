package at.preproject.point2tick.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import at.preproject.point2tick.Application;
import at.preproject.point2tick.R;
import at.preproject.point2tick.fragments.TripTabFragment;
import at.preproject.point2tick.fragments.TripLocationTripTabFragment;
import at.preproject.point2tick.fragments.TripOptionsTripTabFragment;
import at.preproject.point2tick.fragments.TripRepeatTripTabFragment;
import at.preproject.point2tick.trip.BaseTrip;

/**
 * Created by Mike on 02.05.2017.
 */

public class TripActivity extends AppCompatActivity {
    // key fürs bundle
    public static final String KEY_ARG_TRIP_NEW = "newtrip";
    // edit instance
    //FIXME local ref from bundel is bessa
    public static BaseTrip baseTrip = BaseTrip.newDefaultInstance();
    // ui handler..
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        //actionbar init
        setupActionBar();
        //check ob ma an neune trip machen
        if(getIntent().getExtras().getBoolean(KEY_ARG_TRIP_NEW, true) || baseTrip == null) {
            baseTrip = BaseTrip.newDefaultInstance();
        }
        //tabs
        final ArrayList<TripTabFragment> fragments = new ArrayList<>();
        fragments.add(TripLocationTripTabFragment.newInstance(getString(R.string.tab_location_title)));
        fragments.add(TripRepeatTripTabFragment.newInstance(getString(R.string.tab_repeat_title)));
        fragments.add(TripOptionsTripTabFragment.newInstance(getString(R.string.tab_options_title)));
        // create adapter
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        //get viewPager and set adapter
        mViewPager = (ViewPager) findViewById(R.id.activity_trip_pager);
        mViewPager.setAdapter(mViewPagerAdapter);
        //init tablayout w viewpager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_trip_tab);
        tabLayout.setupWithViewPager(mViewPager);
    }
    /**
     * request exit is ok
     */
    private void requestExit(boolean isFinishButton) {
        if(!isFinishButton) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.alert_close_trip_title)
                    .setMessage(R.string.alert_close_trip_content)
                    .setNegativeButton(R.string.alert_close_trip_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            final Intent intent = new Intent();
                            setResult(RESULT_CANCELED, intent);
                            finish();
                        }
                    })
                    .setPositiveButton(R.string.alert_close_trip_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();;
        } else {
            //ist finish button, check trip and save if possible
            //TODO ...

            //TODO ...
            final Intent intent = new Intent();
            final Bundle args = new Bundle();
            args.putSerializable(Application.RESULT_KEY_TRIP, baseTrip);
            intent.putExtras(args);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
    /************************ ACTIONBAR STUFF **************************/
    /**
     * setup action bar
     */
    private void setupActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_trip_finish) {
            requestExit(true);
        }
        return super.onOptionsItemSelected(item);
    }

    /********** UNSER tap adapter *******************/
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final ArrayList<TripTabFragment> mFragments;

        public ViewPagerAdapter(FragmentManager fm, final ArrayList<TripTabFragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragments.get(position).getTitle();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    // back handle
    @Override
    public void onBackPressed() {
        requestExit(false);
    }
}
