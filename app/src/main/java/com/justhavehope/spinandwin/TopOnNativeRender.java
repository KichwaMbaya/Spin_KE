package com.justhavehope.spinandwin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.anythink.nativead.api.ATNativeAdRenderer;
import com.anythink.nativead.api.ATNativeImageView;
import com.anythink.nativead.unitgroup.api.CustomNativeAd;

import java.util.ArrayList;
import java.util.List;

public class TopOnNativeRender implements ATNativeAdRenderer<CustomNativeAd> {
    List<View> mClickView = new ArrayList();
    Context mContext;
    View mDevelopView;
    int mNetworkFirmId;


    public TopOnNativeRender(Context context) {
        this.mContext = context;
    }

    public View createView(Context context, int i) {
        if (this.mDevelopView == null) {
            this.mDevelopView = LayoutInflater.from(context).inflate(R.layout.topon_native_ad_item, (ViewGroup) null);
        }
        this.mNetworkFirmId = i;
        if (this.mDevelopView.getParent() != null) {
            ((ViewGroup) this.mDevelopView.getParent()).removeView(this.mDevelopView);
        }
        return this.mDevelopView;
    }

    public void renderAdView(View view, CustomNativeAd customNativeAd) {
        View view2 = view;
        this.mClickView.clear();
        TextView textView = (TextView) view2.findViewById(R.id.native_ad_title);
        TextView textView2 = (TextView) view2.findViewById(R.id.native_ad_desc);
        TextView textView3 = (TextView) view2.findViewById(R.id.native_ad_install_btn);
        TextView textView4 = (TextView) view2.findViewById(R.id.native_ad_from);
        FrameLayout frameLayout = (FrameLayout) view2.findViewById(R.id.native_ad_content_image_area);
        FrameLayout frameLayout2 = (FrameLayout) view2.findViewById(R.id.native_ad_image);
        ATNativeImageView aTNativeImageView = (ATNativeImageView) view2.findViewById(R.id.native_ad_logo);
        String str = "";
        textView.setText(str);
        textView2.setText(str);
        textView3.setText(str);
        textView4.setText(str);
        textView.setText(str);
        frameLayout.removeAllViews();
        frameLayout2.removeAllViews();
        aTNativeImageView.setImageDrawable((Drawable) null);
        View adMediaView = customNativeAd.getAdMediaView(frameLayout, Integer.valueOf(frameLayout.getWidth()));
        if (customNativeAd.isNativeExpress()) {
            textView.setVisibility(8);
            textView2.setVisibility(8);
            textView3.setVisibility(8);
            aTNativeImageView.setVisibility(8);
            frameLayout2.setVisibility(8);
            if (adMediaView.getParent() != null) {
                ((ViewGroup) adMediaView.getParent()).removeView(adMediaView);
            }
            frameLayout.addView(adMediaView, new FrameLayout.LayoutParams(-1, -2));
            return;
        }
        textView.setVisibility(0);
        textView2.setVisibility(0);
        textView3.setVisibility(0);
        aTNativeImageView.setVisibility(0);
        frameLayout2.setVisibility(0);
        View adIconView = customNativeAd.getAdIconView();
        ATNativeImageView aTNativeImageView2 = new ATNativeImageView(this.mContext);
        if (adIconView == null) {
            frameLayout2.addView(aTNativeImageView2);
            aTNativeImageView2.setImageURI(Uri.parse(customNativeAd.getIconImageUrl()));
            this.mClickView.add(aTNativeImageView2);
        } else {
            frameLayout2.addView(adIconView);
        }
        if (!TextUtils.isEmpty(customNativeAd.getAdChoiceIconUrl())) {
            aTNativeImageView.setImageURI(Uri.parse(customNativeAd.getAdChoiceIconUrl()));
        }
        if (adMediaView != null) {
            if (adMediaView.getParent() != null) {
                ((ViewGroup) adMediaView.getParent()).removeView(adMediaView);
            }
            frameLayout.addView(adMediaView, new FrameLayout.LayoutParams(-1, -2));
        } else {
            ATNativeImageView aTNativeImageView3 = new ATNativeImageView(this.mContext);
            aTNativeImageView3.setImageURI(Uri.parse(customNativeAd.getMainImageUrl()));
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            aTNativeImageView3.setLayoutParams(layoutParams);
            frameLayout.addView(aTNativeImageView3, layoutParams);
            this.mClickView.add(aTNativeImageView3);
        }
        textView.setText(customNativeAd.getTitle());
        textView2.setText(customNativeAd.getDescriptionText());
        textView3.setText(customNativeAd.getCallToActionText());
        if (!TextUtils.isEmpty(customNativeAd.getAdFrom())) {
            if (customNativeAd.getAdFrom() != null) {
                str = customNativeAd.getAdFrom();
            }
            textView4.setText(str);
            textView4.setVisibility(0);
        } else {
            textView4.setVisibility(8);
        }
        this.mClickView.add(textView);
        this.mClickView.add(textView2);
        this.mClickView.add(textView3);
    }

    public List<View> getClickView() {
        return this.mClickView;
    }
}
