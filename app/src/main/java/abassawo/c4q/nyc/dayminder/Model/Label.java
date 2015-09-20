package abassawo.c4q.nyc.dayminder.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c4q-Abass on 7/19/15.
 */
//do labels contain notes, or do the notes contain the labels?

public class Label{
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    private String title;

    public Label(String title) {
        this.title = title;
    }

    public Label() {

    }
}

