package abassawo.c4q.nyc.dayminder;

/**
 * Created by c4q-Abass on 7/19/15.
 */
//do labels contain notes, or do the notes contain the labels?

public class JLabel {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;


    public JLabel(String title) {
        this.title = title;
    }
    public JLabel() {

    }

}