package at.preproject.point2tick.utils;

/**
 * Created by Mike on 03.05.2017.
 */

import java.io.Serializable;

/**
 * Low level bit flags helfer
 */
public class IntBitFlags implements Serializable {
    // flags
    private int flags = 0;

    public IntBitFlags() {}

    /**
     * get flags
     * @return flags
     */
    public int getFlags() { return flags; }

    /**
     * set flags
     * @param flags flags
     */
    public void setFlags(int flags) { this.flags = flags; }

    /**
     * Ob ein flag gesetzt ist
     * @param flag flag
     * @return true wenn gesetzt
     */
    public boolean isFlagSet(int flag) {
        return (flags & flag) == flag;
    }

    /**
     * Setzt entfehrnt ein flag
     * @param state state
     * @param flag flag
     */
    public void setFlag(int flag, boolean state) {
        if(state)flags |= flag;
        else flags &= ~flag;
    }
}
