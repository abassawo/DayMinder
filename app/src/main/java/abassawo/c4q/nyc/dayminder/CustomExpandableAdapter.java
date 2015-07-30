package abassawo.c4q.nyc.dayminder;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;


import java.util.List;



import java.util.List;

/**
 * Created by c4q-Abass on 7/30/15.
 */
public class CustomExpandableAdapter extends ExpandableRecyclerAdapter<CustomParentViewHolder, CustomChildViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private ExpandCollapseListener mExpandCollapseListener;
    private LayoutInflater mInflater;

    /**
     * Public primary constructor.
     *
     * @param context        for inflating views
     * @param parentItemList the list of parent items to be displayed in the RecyclerView
     */
    public CustomExpandableAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        mInflater = LayoutInflater.from(context);
    }


    public void addExpandCollapseListener(ExpandCollapseListener expandCollapseListener) {
        mExpandCollapseListener = expandCollapseListener;
    }


    /**
     * Public secondary constructor. This constructor adds the ability to add a custom triggering
     * view when the adapter is created without having to set it later. This is here for demo
     * purposes.
     *
     * @param context               for inflating views
     * @param parentItemList        the list of parent items to be displayed in the RecyclerView
     * @param customClickableViewId the id of the view that triggers the expansion
     */
    public CustomExpandableAdapter(Context context, List<ParentObject> parentItemList,
                                   int customClickableViewId) {
        super(context, parentItemList, customClickableViewId);
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Public secondary constructor. This constructor adds the ability to add a custom triggering
     * view and a custom animation duration when the adapter is created without having to set them
     * later. This is here for demo purposes.
     *
     * @param context               for inflating views
     * @param parentItemList        the list of parent items to be displayed in the RecyclerView
     * @param customClickableViewId the id of the view that triggers the expansion
     * @param animationDuration     the duration (in ms) of the rotation animation
     */
    public CustomExpandableAdapter(Context context, List<ParentObject> parentItemList,
                                   int customClickableViewId, long animationDuration) {
        super(context, parentItemList, customClickableViewId, animationDuration);
        mInflater = LayoutInflater.from(context);
    }

    /**
     * OnCreateViewHolder implementation for parent items. The desired ParentViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public CustomParentViewHolder onCreateParentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.recycler_cards_parent, parent, false);
        return new CustomParentViewHolder(view);
    }

    /**
     * OnCreateViewHolder implementation for child items. The desired ChildViewHolder should
     * be inflated here
     *
     * @param parent for inflating the View
     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
     */
    @Override
    public CustomChildViewHolder onCreateChildViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.recycler_cards_child, parent, false);
        return new CustomChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(CustomParentViewHolder customParentViewHolder, int i, Object o) {
        CustomParentObject customParentObject = (CustomParentObject) o;
        customParentViewHolder.numberText.setText(Integer.toString(customParentObject.getParentNumber()));
        customParentViewHolder.dataText.setText(customParentObject.getParentText());
    }

    @Override
    public void onBindChildViewHolder(CustomChildViewHolder customChildViewHolder, int i, Object o) {
        CustomChildObject customChildObject = (CustomChildObject) o;
        customChildViewHolder.dataText.setText(customChildObject.getChildText());
    }

    /**
     * OnBindViewHolder implementation for parent items. Any data or view modifications of the
     * parent view should be performed here.
     *
     * @param parentViewHolder the ViewHolder of the parent item created in OnCreateParentViewHolder
     * @param position         the position in the RecyclerView of the item
     */


    /**
     * OnBindViewHolder implementation for child items. Any data or view modifications of the
     * child view should be performed here.
     *
     * @param childViewHolder the ViewHolder of the child item created in OnCreateChildViewHolder
     * @param position        the position in the RecyclerView of the item
     */












}