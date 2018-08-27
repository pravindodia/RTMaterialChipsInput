package com.rultech.View;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rultech.materialchipsample.R;

/**
 * Created by Jiyyo on 28/07/18.
 * <p>
 * How to use
 * <p>
 * Add your recycler view in the relativelayout if not
 * <RelativeLayout
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * android:layout_below="@+id/header"
 * android:layout_margin="10dp">
 * <p>
 * <com.example.emptyrecyclerview.EmptyRecyclerView
 * android:id="@+id/recyclerView"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent">
 * <p>
 * </com.example.emptyrecyclerview.EmptyRecyclerView>
 * </RelativeLayout>
 * <p>
 * Then you can use as below
 * <p>
 * For setting TextView Layout Gravity in the parent view
 * //recyclerView.setEmptyViewGravity(RelativeLayout.CENTER_HORIZONTAL);
 * For setting TextView Layout Params
 * recyclerView.setEmptyViewParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
 * <p>
 * Empty Recylerview and its EmptyTextView
 * EmptyRecyclerView recyclerView = findViewById(R.id.recyclerView);
 * EmptyRecyclerView.EmptyTextView emptyTextView = recyclerView.withEmptyView(this);
 * emptyTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
 * emptyTextView.setText("No Records found Click Load Data Button to show the data");
 * emptyTextView.setGravity(Gravity.CENTER);
 * emptyTextView.built(); IMPORTANT IS you call built after setting the property
 * recyclerView.setEmptyViewStr("No Records found Click Load Data Button to show the data");
 * <p>

/**
 * Empty Recycler view
 */
public class EmptyRecyclerView extends RecyclerView {
    private final Context mContext;
    private EmptyTextView mEmptyView;
    private String defaultStr = "No Records Found";
    private float defaultTextSize = 20;
    private int defaultTextColor = R.color.empty_textColor;
    private int emptyViewGravity = RelativeLayout.CENTER_IN_PARENT;
    private RelativeLayout.LayoutParams emptyViewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    private ViewGroup mParentView;
    /**
     *
     */
    private final AdapterDataObserver mObserver = new AdapterDataObserver() {
        /**
         *
         */
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        /**
         *
         * @param positionStart
         * @param itemCount
         */
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        /**
         *
         * @param positionStart
         * @param itemCount
         */
        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    /**
     *
     * @param context
     */
    public EmptyRecyclerView(Context context) {
        super(context);
        mContext = context;
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    /**
     * check if the view is empty
     */
    void checkIfEmpty() {
        if (mEmptyView == null) {
            withEmptyView(mContext).built();
        }
        if (mEmptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    /**
     *
     * @param adapter
     */
    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mObserver);
        }

        checkIfEmpty();
    }

    /**
     *
     * @param mContext
     * @return EmptyTextView
     */
    public EmptyTextView withEmptyView(Context mContext) {
        mParentView = (ViewGroup) getParent();
        mEmptyView = new EmptyTextView(mContext);
        mEmptyView.setGravity(Gravity.CENTER);
        mEmptyView.setText(defaultStr);
        mEmptyView.setTextSize(defaultTextSize);
        mEmptyView.setTextColor(ContextCompat.getColor(mContext, defaultTextColor));
        return mEmptyView;
    }

    /**
     *
     * @param mEmptyViewGravity
     * @return
     */
    public EmptyRecyclerView setEmptyViewGravity(int mEmptyViewGravity) {
        emptyViewGravity = mEmptyViewGravity;
        initEmptyView();
        return this;
    }

    /**
     *
     * @param mEmptyViewParams
     * @return
     */
    public EmptyRecyclerView setEmptyViewParams(RelativeLayout.LayoutParams mEmptyViewParams) {
        this.emptyViewLayoutParams = mEmptyViewParams;
        initEmptyView();
        return this;
    }

    /**
     *
     */
    private void initEmptyView() {
        if (mParentView != null && mEmptyView != null) {
            try {

                mParentView.removeView(mEmptyView);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            emptyViewLayoutParams.addRule(emptyViewGravity);
            mParentView.addView(mEmptyView, emptyViewLayoutParams);
        }
    }

    /**
     *
     */
    public class EmptyTextView extends android.support.v7.widget.AppCompatTextView {

        /**
         *
         * @param context
         */
        public EmptyTextView(Context context) {
            super(context);
        }

        /**
         *
         */
        public void built() {
            initEmptyView();
        }


    }


}
