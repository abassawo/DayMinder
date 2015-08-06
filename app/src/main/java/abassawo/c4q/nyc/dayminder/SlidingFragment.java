package abassawo.c4q.nyc.dayminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.ali.android.client.customview.view.SlidingDrawer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-Abass on 8/6/15.
 */

public class SlidingFragment extends Fragment implements SlidingDrawer.OnInteractListener {
    public static final String TAG = "SlidingDrawerFragment";
    @Bind(R.id.slidingImage) ImageView mSlidingImage;
    @Bind(R.id.slidingDrawer) SlidingDrawer mSlidingDrawer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        ButterKnife.bind(this, view);
        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("Abass test", "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);

        mSlidingImage = (ImageView) view.findViewById(R.id.slidingImage);

        mSlidingDrawer = (SlidingDrawer) view.findViewById(R.id.slidingDrawer);
        mSlidingDrawer.setOnInteractListener(this);
    }

    @Override
    public void onOpened() {
        if (SlidingDrawer.DEBUG) Log.d("Open", "onOpened()");
        mSlidingImage.setImageResource(R.drawable.ic_arrow_down);
    }

    @Override
    public void onClosed() {
        if (SlidingDrawer.DEBUG) Log.d("Closed", "onClosed()");
        mSlidingImage.setImageResource(R.drawable.ic_arrow_up);
    }

    public SlidingDrawer getSlidingDrawer() {
        return mSlidingDrawer;
    }

}