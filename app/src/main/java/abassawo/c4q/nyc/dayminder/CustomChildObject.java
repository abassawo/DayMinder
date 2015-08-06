package abassawo.c4q.nyc.dayminder;

/**
 * Created by c4q-Abass on 7/30/15.
 */
public  class CustomChildObject {
    private String mChildText;

    public CustomChildObject() {
    }

    public CustomChildObject(String mChildText){
        this.mChildText = mChildText;
    }

    public String getChildText() {
        return mChildText;
    }

    public void setChildText(String childText) {
        mChildText = childText;
    }
}