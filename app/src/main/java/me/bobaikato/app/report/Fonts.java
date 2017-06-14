package me.bobaikato.app.report;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Author: Bobai Kato
 * Date: 6/11/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 * Email: bobai.Kato@gmail.com
 */

class Fonts {
    private Typeface custom_font;
    private Typeface custom_font_1;

    Fonts(Context context) {
        custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/LatoLight.ttf");
        custom_font_1 = Typeface.createFromAsset(context.getAssets(), "fonts/LatoRegular.ttf");
    }


    public Typeface getCustom_font() {
        return custom_font;
    }

    public Typeface getCustom_font_1() {
        return custom_font_1;
    }

}
