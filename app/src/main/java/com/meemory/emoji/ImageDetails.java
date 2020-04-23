package com.meemory.emoji;

/**
 * Created by liran yehudar on 4/8/2018.
 */

public class ImageDetails {

    private int backDrawableId;
    private boolean isFlipped;
    private boolean isMatch;

    ImageDetails(int backDrawableId) {
        this.backDrawableId = backDrawableId;
        this.isFlipped = false;
        this.isMatch = false;
    }

    int getBackDrawableId() {
        return backDrawableId;
    }

    boolean isFlipped() {
        return isFlipped;
    }

    boolean isMatch() {
        return isMatch;
    }

    void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    void setMatch(boolean match) {
        isMatch = match;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageDetails that = (ImageDetails) o;

        return backDrawableId == that.backDrawableId;
    }

}
