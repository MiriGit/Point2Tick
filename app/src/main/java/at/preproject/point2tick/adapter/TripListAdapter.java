package at.preproject.point2tick.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.preproject.point2tick.R;
import at.preproject.point2tick.trip.BaseTrip;

/**
 * Created by Mike on 03.05.2017.
 */

public class TripListAdapter extends ArrayAdapter<BaseTrip> {

    private final OnEditClickListener mOnEditClickListener;
    private Context mContext;

    public TripListAdapter(final OnEditClickListener onEditClickListener, Context context, ArrayList<BaseTrip> trips) {
        super(context, R.layout.item_list_trip, trips);
        this.mOnEditClickListener = onEditClickListener;
        this.mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView,@NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_trip, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.item_list_trip_title);
            viewHolder.state = (Switch) convertView.findViewById(R.id.item_list_trip_state);
            viewHolder.edit = (ImageButton) convertView.findViewById(R.id.item_list_trip_edit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final BaseTrip trip = getItem(position);
        if(trip != null) {
            viewHolder.title.setText(trip.name);
            viewHolder.state.setChecked(trip.flags.isFlagSet(BaseTrip.FLAG_ACTIVE));
            viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnEditClickListener.onEdit(trip);
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {
        public TextView title;
        public Switch state;
        public ImageButton edit;
    }

    public interface OnEditClickListener {
        public void onEdit(BaseTrip trip);
    }
}
