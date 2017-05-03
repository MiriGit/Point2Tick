package at.preproject.point2tick.trip;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import at.preproject.point2tick.utils.IntBitFlags;

/**
 * Created by Mike on 01.05.2017.
 */

public class BaseTrip implements Serializable {
    //json keys
    private static final String KEY_FLAGS = "a";
    private static final String KEY_LOC_START_LAT = "b";
    private static final String KEY_LOC_START_LNG = "c";
    private static final String KEY_LOC_DEST_LAT = "d";
    private static final String KEY_LOC_DEST_LNG = "e";
    private static final String KEY_TIME_START = "f";
    private static final String KEY_TIME_DEST = "g";
    private static final String KEY_NAME = "h";
    // flags
    public static final int FLAG_ACTIVE = 1; // 1 << 0
    public static final int FLAG_VIBRATE = 2; // 1 << 2
    public static final int FLAG_RINGTONE = 4; // 1 left shift 3
    public static final int FLAG_BASED_TIME = 8; // etc...
    public static final int FLAG_BASED_LOCATION = 16;
    public static final int FLAG_DAY_MONDAY = 32;
    public static final int FLAG_DAY_TUESDAY = 64;
    public static final int FLAG_DAY_WEDNESDAY = 128;
    public static final int FLAG_DAY_THURSDAY = 256;
    public static final int FLAG_DAY_FRIDAY = 512;
    public static final int FLAG_DAY_SATURDAY = 1024;
    public static final int FLAG_DAY_SUNDAY = 2048;
    public static final int FLAG_DAY_DAILY = 4096;
    //id wird nur im mem verwendet
    public int id = -1;
    // der name des trips ... .gg
    public String name = "default";
    // flags
    public IntBitFlags flags = new IntBitFlags();
    // locations
    public double locStartLat = 0d;
    public double locStartLng = 0d;
    public double locDestLat = 0d;
    public double locDestLng = 0d;
    // times
    public long timeStart = 0L;
    public long timeDest = 0L;
    // private constructor wir wollen nicht das jemand ds object erzeugt
    private BaseTrip() {}
    /**
     * zu json object
     * @return json object
     */
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_FLAGS, flags.getFlags());
            jsonObject.put(KEY_LOC_START_LAT, locStartLat);
            jsonObject.put(KEY_LOC_START_LNG, locStartLng);
            jsonObject.put(KEY_LOC_DEST_LAT, locDestLat);
            jsonObject.put(KEY_LOC_DEST_LNG, locDestLng);
            jsonObject.put(KEY_TIME_START, timeStart);
            jsonObject.put(KEY_TIME_DEST, timeDest);
            jsonObject.put(KEY_NAME, name);
        } catch (JSONException je) {
            Log.e(getClass().getSimpleName(), "toJson()", je);
        }
        return jsonObject;
    }
    /**
     * erstellt eine neue instanz mit den daten aus dem json object
     * @param jsonObject json
     * @return
     */
    public static BaseTrip parseJson(JSONObject jsonObject) {
        BaseTrip trip = new BaseTrip();
        trip.flags.setFlags(jsonObject.optInt(KEY_FLAGS, 0));
        trip.locStartLat = jsonObject.optDouble(KEY_LOC_START_LAT, 0d);
        trip.locStartLng = jsonObject.optDouble(KEY_LOC_START_LNG, 0d);
        trip.locDestLat = jsonObject.optDouble(KEY_LOC_DEST_LAT, 0d);
        trip.locDestLng = jsonObject.optDouble(KEY_LOC_DEST_LNG, 0d);
        trip.timeStart = jsonObject.optLong(KEY_TIME_START, 0l);
        trip.timeDest = jsonObject.optLong(KEY_TIME_DEST, 0l);
        trip.name = jsonObject.optString(KEY_NAME, "default");
        return trip;
    }
    // newDefault
    @NonNull
    public static BaseTrip newDefaultInstance() {
        return new BaseTrip();
    }
}
