package com.wenming.weiswift.fragment.home.imagedetaillist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.wenming.weiswift.R;
import com.wenming.weiswift.common.photoview.PhotoViewAttacher;

import java.util.ArrayList;


/**
 * Created by wenmingvs on 16/4/19.
 */
public class ImageDetailsActivity extends Activity implements ViewPagerAdapter.OnSingleTagListener {

    private ImageDetailViewPager mViewPager;
    private ImageDetailTopBar mImageDetailTopBar;
    private ArrayList<String> mDatas;
    private int mPosition;
    private int mImgNum;
    private ViewPagerAdapter mAdapter;
    private Context mContext;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_weiboitem_imagedetails);
        mContext = ImageDetailsActivity.this;
        mDatas = this.getIntent().getStringArrayListExtra("imagelist_url");
        mPosition = getIntent().getIntExtra("image_position", 0);
        mImgNum = mDatas.size();

        mViewPager = (ImageDetailViewPager) findViewById(R.id.viewpagerId);
        mImageDetailTopBar = (ImageDetailTopBar) findViewById(R.id.imageTopBar);
        mAdapter = new ViewPagerAdapter(mDatas, this);
        mAdapter.setOnSingleTagListener(this);
        mViewPager.setAdapter(mAdapter);

        if (mImgNum == 1) {
            mImageDetailTopBar.setPageNumVisible(View.GONE);
        } else {
            mImageDetailTopBar.setPageNum((mPosition + 1) + "/" + mImgNum);
        }


        mViewPager.setCurrentItem(mPosition);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 每当页数发生改变时重新设定一遍当前的页数和总页数
                mImageDetailTopBar.setPageNum((position + 1) + "/" + mImgNum);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mImageDetailTopBar.setOnMoreOptionsListener(new ImageDetailTopBar.OnMoreOptionsListener() {
            @Override
            public void onClick(View view) {
                ImageOptionPopupWindow mPopupWindow = ImageOptionPopupWindow.getInstance(mContext);
                setupPopDismissEvent(mPopupWindow);
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                } else {
                    mPopupWindow.showAtLocation(findViewById(R.id.frameLayout), Gravity.BOTTOM, 0, 0);
                    setOutBackground(0.5f);
                }
            }
        });

    }

    /**
     * 设置popwindow外部的alpha值
     *
     * @param alpha
     */
    private void setOutBackground(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置popwindow的dismiss监听事件
     *
     * @param mPopupWindow
     */
    private void setupPopDismissEvent(ImageOptionPopupWindow mPopupWindow) {
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setOutBackground(1.0f);
            }
        });
    }


    private PhotoViewAttacher.OnPhotoTapListener mPhotoTapListener = new PhotoViewAttacher.OnPhotoTapListener() {
        @Override
        public void onPhotoTap(View view, float v, float v1) {
            finish();
        }

        @Override
        public void onOutsidePhotoTap() {
            finish();
        }
    };


    @Override
    public void onTag() {
        finish();
    }

}
