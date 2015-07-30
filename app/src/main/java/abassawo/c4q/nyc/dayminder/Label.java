package abassawo.c4q.nyc.dayminder;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by c4q-Abass on 7/30/15.
 */
public class Label implements ParentObject {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;


    @Override
    public List<Object> getChildObjectList() {
        return null;
    }

    @Override
    public void setChildObjectList(List<Object> list) {

    }
}
