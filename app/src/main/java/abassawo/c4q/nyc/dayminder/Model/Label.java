package abassawo.c4q.nyc.dayminder.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by c4q-Abass on 7/19/15.
 */
//do labels contain notes, or do the notes contain the labels?

public class Label{
    private String color;
    private UUID id;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }
    private String title;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Label(String title) {

        this.title = title;
    }

    public Label() {

    }

    @Override
    public String toString(){
        return this.getTitle();
    }

    public String getColor() {
        return color;
    }
}

