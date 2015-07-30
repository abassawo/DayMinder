package abassawo.c4q.nyc.dayminder;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by c4q-Abass on 7/30/15.
 */
public class CustomParentObject implements ParentObject {
    // A List<Object> or subclass of List must be added for the object to work correctly
    private List<Object> mChildObjectList;

    private String mParentText;
    private int mParentNumber;


    public CustomParentObject() {
    }

    public String getParentText() {
        return mParentText;
    }

    public void setParentText(String parentText) {
        mParentText = parentText;
    }

    public int getParentNumber() {
        return mParentNumber;
    }

    public void setParentNumber(int parentNumber) {
        mParentNumber = parentNumber;
    }

    /**
     * Getter method for the list of children associated with this parent object
     *
     * @return list of all children associated with this specific parent object
     */
    @Override
    public List<Object> getChildObjectList() {
        return mChildObjectList;
    }

    /**
     * Setter method for the list of children associated with this parent object
     *
     * @param childObjectList the list of all children associated with this parent object
     */
    @Override
    public void setChildObjectList(List<Object> childObjectList) {
        mChildObjectList = childObjectList;
    }
}

