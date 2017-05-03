package at.preproject.point2tick.utils;

import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Mike on 30.04.2017.
 */

public class ObjectUtils {

    /**
     * Deserialize einen String und cast zum ziel object type
     * @param object object stat
     * @param type cast object to
     * @param <T> ziel cast
     * @return object
     */
    @Nullable
    public static <T> T deserialize(String object, Class<T> type) {
        final byte[] data = Base64.decode(object, Base64.DEFAULT);
        try {
            ByteArrayInputStream bis = null;
            try {
                bis = new ByteArrayInputStream(data);
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(bis);
                    final Object obj = ois.readObject();
                    return type.cast(obj);
                } finally {
                    if(ois != null) {
                        ois.close();
                    }
                }
            } finally {
                if(bis != null) {
                    bis.close();
                }
            }
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            Log.e(ObjectUtils.class.getSimpleName(), "deserialize()", e);
        }
        return null;
    }

    /**
     * Serialize ein Object zu einem String
     * @param object object
     * @return serialized object
     */
    @Nullable
    public static String serialize(Serializable object) {
        try {
            ByteArrayOutputStream bos = null;
            try {
                bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(bos);
                    oos.writeObject(object);
                    oos.flush();
                } finally {
                    if(oos != null) {
                        oos.close();
                    }
                }
                bos.flush();
            } finally {
               if(bos != null) {
                   bos.close();
               }
            }
            if(bos.size() > 0) {
                return Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
            }
        } catch (IOException ioe) {
            Log.e(ObjectUtils.class.getSimpleName(), "serialize()", ioe);
        }
        return null;
    }
}
