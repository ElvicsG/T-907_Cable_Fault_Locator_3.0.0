package net.kehui.www.t_907_origin_V3.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import net.kehui.www.t_907_origin_V3.R;
import net.kehui.www.t_907_origin_V3.util.ScreenUtils;

/**
 * @author gong
 * @date 2022/07/10 //GC20220710
 */
public class SwitchingDialog extends BaseDialog implements View.OnClickListener {

    public TextView tvNoteSw;
    public ImageView ivScanU;
    //等待动画添加    //GC20220711
    private ValueAnimator valueAnimator;
    private int[] scoreText = {R.drawable.ic_wait_empty, R.drawable.ic_wait_1, R.drawable.ic_wait_2, R.drawable.ic_wait_3};

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(getContext(), R.layout.dialog_switching, null);
        setContentView(view);
        initView();

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) ( ScreenUtils.getScreenWidth(getContext()) * 0.4);
        params.height = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.25);
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void initView() {
        //初始化
        tvNoteSw = view.findViewById(R.id.tv_note_sw);
        ivScanU = view.findViewById(R.id.iv_scan_u);
        //画动画1——正在测试中   ...     波纹
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(0, 4).setDuration(1000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int i = (int) animation.getAnimatedValue();
                    ivScanU.setImageResource(scoreText[i % scoreText.length]);
                }
            });
        }
        valueAnimator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    public SwitchingDialog(@NonNull Context context) {
        super(context);
    }
}
