package de.davidartmann.profpedia.transition;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

/**
 * Transition for the animation of swiping a view in the viewpager of the mensa activity.
 * Created by david on 11.01.16.
 */
public class DepthPageTransformer implements PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        if (position < -1) {
            page.setAlpha(0);
        } else if (position <= 0) {
            page.setAlpha(1);
            page.setTranslationX(0);
            page.setScaleX(1);
            page.setScaleY(1);
        } else if (position <= 1) {
            page.setAlpha(1 - position);
            page.setTranslationX(page.getWidth() * -position);
            float scaleFactor = 0.75f + (1 - 0.75f) * (1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else {
            page.setAlpha(0);
        }
    }
}
