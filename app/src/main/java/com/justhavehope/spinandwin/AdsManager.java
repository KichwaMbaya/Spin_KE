package com.justhavehope.spinandwin;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.anythink.banner.api.ATBannerListener;
import com.anythink.banner.api.ATBannerView;
import com.anythink.core.api.ATAdConst;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialListener;
import com.anythink.nativead.api.ATNative;
import com.anythink.nativead.api.ATNativeAdView;
import com.anythink.nativead.api.ATNativeDislikeListener;
import com.anythink.nativead.api.ATNativeEventListener;
import com.anythink.nativead.api.ATNativeNetworkListener;
import com.anythink.nativead.api.NativeAd;
import com.anythink.rewardvideo.api.ATRewardVideoAd;

import java.util.HashMap;

public class AdsManager {

    //Native variables
    public static RelativeLayout adContainer;
    private static int adViewHeight;
    private static int adViewWidth;
    private static ATNativeAdView anyThinkNativeAdView;
    private static ATNative atNatives;
    private static NativeAd mNativeAd;
    //End

    public static int Adcount = 2;
    public static int adstart = 2;
    private static ATInterstitial TopOnInterstitialAd;
    private static ATRewardVideoAd TopOnRewardedAd;
    public static void LoadBannerAd(Activity activity) {
        ATBannerView mBannerView = new ATBannerView(activity);
        mBannerView.setPlacementId(activity.getString(R.string.TopOnBanner));
        int width = activity.getResources().getDisplayMetrics().widthPixels;//Set a width value, such as screen width
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mBannerView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        final FrameLayout frameLayout = activity.findViewById(R.id.my_banner);
        frameLayout.addView(mBannerView);
        mBannerView.setBannerAdListener(new ATBannerListener() {
            @Override
            public void onBannerLoaded() {
                mBannerView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onBannerFailed(AdError adError) {
            }
            @Override
            public void onBannerClicked(ATAdInfo atAdInfo) {
            }
            @Override
            public void onBannerShow(ATAdInfo atAdInfo) {
            }
            @Override
            public void onBannerClose(ATAdInfo atAdInfo) {
            }
            @Override
            public void onBannerAutoRefreshed(ATAdInfo atAdInfo) {
            }
            @Override
            public void onBannerAutoRefreshFail(AdError adError) {
            }
        }); mBannerView.loadAd();

    }
    public static void LoadInterstitialAd(Activity activity) {
        TopOnInterstitialAd = new ATInterstitial(activity, activity.getString(R.string.TopOnInterstitial));
        TopOnInterstitialAd.setAdListener(new ATInterstitialListener() {
            @Override
            public void onInterstitialAdLoaded() {}
            @Override
            public void onInterstitialAdLoadFail(AdError adError) {}
            @Override
            public void onInterstitialAdClicked(ATAdInfo atAdInfo) {}
            @Override
            public void onInterstitialAdShow(ATAdInfo atAdInfo) {}
            @Override
            public void onInterstitialAdClose(ATAdInfo atAdInfo) {
                TopOnInterstitialAd.load();
            }
            @Override
            public void onInterstitialAdVideoStart(ATAdInfo atAdInfo) { TopOnInterstitialAd.load();}
            @Override
            public void onInterstitialAdVideoEnd(ATAdInfo atAdInfo) {}
            @Override
            public void onInterstitialAdVideoError(AdError adError) {}
        });
        TopOnInterstitialAd.load();

    }
    public static void ShowInterstitialAd(Activity activity, Class Second){
        if (adstart == Adcount) {
            adstart = 2;
            if (TopOnInterstitialAd.isAdReady()) {
                TopOnInterstitialAd.show(activity);
                TopOnInterstitialAd.setAdListener(new ATInterstitialListener() {
                    @Override
                    public void onInterstitialAdLoaded() {

                    }

                    @Override
                    public void onInterstitialAdLoadFail(AdError adError) {

                    }

                    @Override
                    public void onInterstitialAdClicked(ATAdInfo atAdInfo) {

                    }

                    @Override
                    public void onInterstitialAdShow(ATAdInfo atAdInfo) {

                    }

                    @Override
                    public void onInterstitialAdClose(ATAdInfo atAdInfo) {
                        Intent intent = new Intent(activity, Second);
                        activity.startActivity(intent);
                    }

                    @Override
                    public void onInterstitialAdVideoStart(ATAdInfo atAdInfo) {

                    }

                    @Override
                    public void onInterstitialAdVideoEnd(ATAdInfo atAdInfo) {

                    }

                    @Override
                    public void onInterstitialAdVideoError(AdError adError) {

                    }
                });
            } else {
                Intent intent = new Intent(activity, Second);
                activity.startActivity(intent);
                TopOnInterstitialAd.load();
            }

        } else {
            Intent intent = new Intent(activity, Second);
            activity.startActivity(intent);
            adstart++;
        }
    }

    public static void loadTopOnNative(Activity activity) {

        adContainer = (RelativeLayout) activity.findViewById(R.id.my_native);
        atNatives = new ATNative(activity,activity.getResources().getString(R.string.TopOnNative), new ATNativeNetworkListener() {
            public void onNativeAdLoaded() {
                showNativeAd();
            }

            public void onNativeAdLoadFail(AdError adError) {

            }
        });
        if (anyThinkNativeAdView == null) {
            anyThinkNativeAdView = new ATNativeAdView(activity);
        }
        if (atNatives != null) {
            int dip2px = dip2px(10.0f, activity) * 2;
            adViewWidth = activity.getResources().getDisplayMetrics().widthPixels - dip2px;
            adViewHeight = dip2px(250.0f, activity) - dip2px;
            HashMap hashMap = new HashMap();
            hashMap.put(ATAdConst.KEY.AD_WIDTH, Integer.valueOf(adViewWidth));
            hashMap.put(ATAdConst.KEY.AD_HEIGHT, Integer.valueOf(adViewHeight));
            atNatives.setLocalExtra(hashMap);
            atNatives.makeAdRequest();
        }
    }
    public static void showNativeAd() {
        NativeAd nativeAd;
        ATNative aTNative = atNatives;
        if (aTNative != null && (nativeAd = aTNative.getNativeAd()) != null) {
            ATNativeAdView aTNativeAdView = anyThinkNativeAdView;
            if (aTNativeAdView != null) {
                aTNativeAdView.removeAllViews();
                if (anyThinkNativeAdView.getParent() == null) {
                    adContainer.addView(anyThinkNativeAdView, new FrameLayout.LayoutParams(adViewWidth, adViewHeight));
                }
            }
            mNativeAd = nativeAd;
            nativeAd.setNativeEventListener(new ATNativeEventListener() {
                public void onAdImpressed(ATNativeAdView aTNativeAdView, ATAdInfo aTAdInfo) {
                }
                public void onAdClicked(ATNativeAdView aTNativeAdView, ATAdInfo aTAdInfo) {
                }
                public void onAdVideoStart(ATNativeAdView aTNativeAdView) {
                }
                public void onAdVideoEnd(ATNativeAdView aTNativeAdView) {
                }
                public void onAdVideoProgress(ATNativeAdView aTNativeAdView, int i) {
                }
            });
            mNativeAd.setDislikeCallbackListener(new ATNativeDislikeListener() {
                public void onAdCloseButtonClick(ATNativeAdView aTNativeAdView, ATAdInfo aTAdInfo) {
                    if (aTNativeAdView.getParent() != null) {
                        ((ViewGroup) aTNativeAdView.getParent()).removeView(aTNativeAdView);
                    }
                }
            });
            TopOnNativeRender nativeDemoRender = new TopOnNativeRender(adContainer.getContext());
            nativeAd.renderAdView(anyThinkNativeAdView, nativeDemoRender);
            nativeAd.prepare(anyThinkNativeAdView, nativeDemoRender.getClickView(), (FrameLayout.LayoutParams) null);
        }
    }
    public static int dip2px(float f, Activity activity) {
        return (int) ((f * activity.getResources().getDisplayMetrics().density) + 0.5f);


    }


}
