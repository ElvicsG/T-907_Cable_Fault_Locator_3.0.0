/**
 * Copyright (C) 2016 Robinhood Markets, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.kehui.www.t_907_origin_V3.ui.SparkView;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.RectF;
import androidx.annotation.VisibleForTesting;

import java.util.List;

/**
 * A simple adapter class - evenly distributes your points along the x axis, does not draw a base
 * line, and has support for registering/notifying {@link DataSetObserver}s when data is changed.
 *
 * @author Gong
 * @date 2018/12/23
 */
public abstract class BaseSparkAdapter {
    private final DataSetObservable observable = new DataSetObservable();

    /**
     * @return the number of points to be drawn
     */
    public abstract int getCount();

    /**
     * @return the object at the given index
     */
    public abstract Object getItem(int index);

    /**
     * @return the float representation of the X value of the point at the given index.
     */
    public float getX(int index) {
        return index;
    }

    /**
     * @return the float representation of the Y value of the point at the given index.
     */
    public abstract float getY(int index);

    /**
     * Gets the float representation of the boundaries of the entire dataset. By default, this will
     * be the min and max of the actual data points in the adapter. This can be overridden for
     * custom behavior. When overriding, make sure to set RectF's values such that:
     * <p>
     * <ul>
     * <li>left = the minimum X value</li>
     * <li>top = the minimum Y value</li>
     * <li>right = the maximum X value</li>
     * <li>bottom = the maximum Y value</li>
     * </ul>
     *
     * @return a RectF of the bounds desired around this adapter's data.
     */
    public RectF getDataBounds() {
        final int count = getCount();
        final boolean hasBaseLine = hasBaseLine();
        float minY = hasBaseLine ? getBaseLine() : 0;
        float maxY = hasBaseLine ? minY : 255;
        float minX = 0;
        float maxX = 510;
        for (int i = 0; i < count; i++) {
            final float x = getX(i);
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);

            final float y = getY(i);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }

        // set values on the return object
        return createRectF(minX, minY, maxX, maxY);
    }

    /**
     * Hook for unit tests
     */
    @VisibleForTesting
    RectF createRectF(float left, float top, float right, float bottom) {
        return new RectF(left, top, right, bottom);
    }

    /**
     * @return true if you wish to draw a "base line" - a horizontal line across the graph used
     * to compare the rest of the graph's points against.
     */
    public boolean hasBaseLine() {
        return false;
    }

    /**
     * @return the float representation of the Y value of the desired baseLine.
     */
    public float getBaseLine() {
        return 0;
    }

    /**
     * Notifies the attached observers that the underlying data has been changed and any View
     * reflecting the data set should refresh itself.
     */
    public final void notifyDataSetChanged() {
        observable.notifyChanged();
    }

    /**
     * Notifies the attached observers that the underlying data is no longer valid or available.
     * Once invoked this adapter is no longer valid and should not report further data set
     * changes.
     */
    public final void notifyDataSetInvalidated() {
        observable.notifyInvalidated();
    }

    /**
     * Register a {@link DataSetObserver} to listen for updates to this adapter's data.
     *
     * @param observer the observer to register
     */
    public final void registerDataSetObserver(DataSetObserver observer) {
        observable.registerObserver(observer);
    }

    /**
     * Unregister a {@link DataSetObserver} from updates to this adapter's data.
     *
     * @param observer the observer to unregister
     */
    public final void unregisterDataSetObserver(DataSetObserver observer) {
        observable.unregisterObserver(observer);
    }

    /**
     * @return  返回是否显示比较的数据
     */
    public final boolean getIsShowCompare() {
        return false;
    }

    /**
     * @return  返回第二条线的数据
     */
    public final List<Integer> getLine2Data() {
        return null;
    }

    /**
     * @return the float representation of the Y value of the point at the given index.
     */
    public abstract float getY1(int index);

    public abstract boolean getCompare();

}
