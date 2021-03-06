package rouchuan.customlayoutmanager;

import android.support.v7.widget.RecyclerView;


/**
 * Created by Dajavu on 16/8/18.
 */
public class CenterScrollListener extends RecyclerView.OnScrollListener {
    private boolean mAutoSet = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (!(layoutManager instanceof ViewPagerLayoutManager)) {
            mAutoSet = true;
            return;
        }

        final ViewPagerLayoutManager.OnPageChangeListener onPageChangeListener = ((ViewPagerLayoutManager) layoutManager).onPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(newState);
        }

        if (!mAutoSet) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                final int dx;
                dx = ((ViewPagerLayoutManager) layoutManager).getOffsetCenterView();
                recyclerView.smoothScrollBy(dx, 0);
            }
            mAutoSet = true;
        } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageSelected(((ViewPagerLayoutManager) layoutManager).getCurrentPosition());
            }
        }
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
            mAutoSet = false;
        }
    }
}
