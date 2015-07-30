package abassawo.c4q.nyc.dayminder;

/**
 * Created by c4q-Abass on 7/30/15.
 */
public interface ExpandCollapseListener {

    /**
     * Method called when an item in the ExpandableRecycleView is expanded
     */
    void onRecyclerViewItemExpanded(int position);

    /**
     * Method called when an item in the ExpandableRecyclerView is collapsed
     */
    void onRecyclerViewItemCollapsed(int position);
}
