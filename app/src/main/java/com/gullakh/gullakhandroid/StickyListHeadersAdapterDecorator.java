/*
 * Copyright 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gullakh.gullakhandroid;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;


public class StickyListHeadersAdapterDecorator extends BaseAdapterDecorator implements StickyListHeadersAdapter {

    /**
     * Alpha property.
     */
    private static final String ALPHA = "alpha";


    @NonNull
    private final StickyListHeadersAdapter mStickyListHeadersAdapter;

    @Nullable
    private ViewAnimator mViewAnimator;


    public StickyListHeadersAdapterDecorator(@NonNull final BaseAdapter baseAdapter) {
        super(baseAdapter);

        BaseAdapter adapter = baseAdapter;
        while (adapter instanceof BaseAdapterDecorator) {
            adapter = ((BaseAdapterDecorator) adapter).getDecoratedBaseAdapter();
        }

        if (!(adapter instanceof StickyListHeadersAdapter)) {
            throw new IllegalArgumentException(adapter.getClass().getCanonicalName() + " does not implement StickyListHeadersAdapter");
        }

        mStickyListHeadersAdapter = (StickyListHeadersAdapter) adapter;
    }


    public void setStickyListHeadersListView(@NonNull final StickyListHeadersListView listView) {
        ListViewWrapper stickyListHeadersListViewWrapper = new StickyListHeadersListViewWrapper(listView);
        setListViewWrapper(stickyListHeadersListViewWrapper);
    }


    @Nullable
    public ViewAnimator getViewAnimator() {
        return mViewAnimator;
    }

    @Override
    public void setListViewWrapper(@NonNull final ListViewWrapper listViewWrapper) {
        super.setListViewWrapper(listViewWrapper);
        mViewAnimator = new ViewAnimator(listViewWrapper);
    }

    @Override
    public View getHeaderView(final int position, final View convertView, final ViewGroup parent) {
        if (getListViewWrapper() == null) {
            throw new IllegalStateException("Call setStickyListHeadersListView() on this AnimationAdapter first!");
        }

        if (convertView != null) {
            assert mViewAnimator != null;
            mViewAnimator.cancelExistingAnimation(convertView);
        }

        View itemView = mStickyListHeadersAdapter.getHeaderView(position, convertView, parent);

        animateViewIfNecessary(position, itemView, parent);
        return itemView;
    }

    /**
     * Animates given View if necessary.
     *
     * @param position the position of the item the View represents.
     * @param view     the View that should be animated.
     * @param parent   the parent the View is hosted in.
     */
    private void animateViewIfNecessary(final int position, @NonNull final View view, @NonNull final ViewGroup parent) {
        Animator[] childAnimators;
        if (getDecoratedBaseAdapter() instanceof AnimationAdapter) {
            childAnimators = ((AnimationAdapter) getDecoratedBaseAdapter()).getAnimators(parent, view);
        } else {
            childAnimators = new Animator[0];
        }
        Animator alphaAnimator = ObjectAnimator.ofFloat(view, ALPHA, 0, 1);

        assert mViewAnimator != null;
        mViewAnimator.animateViewIfNecessary(position, view, AnimatorUtil.concatAnimators(childAnimators, new Animator[0], alphaAnimator));
    }

    @Override
    public long getHeaderId(final int position) {
        return mStickyListHeadersAdapter.getHeaderId(position);
    }
}
