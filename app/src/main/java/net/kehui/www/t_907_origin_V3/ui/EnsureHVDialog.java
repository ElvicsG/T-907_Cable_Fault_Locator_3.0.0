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
 * @date //GC20220726
 */
public class EnsureHVDialog extends BaseDialog implements View.OnClickListener {

    public TextView tvNoteEnsure;
    public ImageView ivScanSwitchOn;
    public TextView tvEnsure;
    public TextView tvBack;

    private View view;
    private ValueAnimator valueAnimator;
    private int[] scoreText = {R.drawable.ic_wait_empty, R.drawable.ic_wait_1, R.drawable.ic_wait_2, R.drawable.ic_wait_3}; //GC20220919

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(getContext(), R.layout.dialog_ensure_hv, null);
        setContentView(view);
        initView();

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) ( ScreenUtils.getScreenWidth(getContext()) * 0.47);
        params.height = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.3);
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void initView() {
        //添加对话框,动画图片初始化 //GC20220919
        tvNoteEnsure = view.findViewById(R.id.tv_note_ensure);
        ivScanSwitchOn = view.findViewById(R.id.iv_scan_switch_on);
        tvEnsure = view.findViewById(R.id.tv_ensure);
        tvBack = view.findViewById(R.id.tv_back);

        //画动画 ...   //GC20220919
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(0, 4).setDuration(1000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int i = (int) animation.getAnimatedValue();
                    ivScanSwitchOn.setImageResource(scoreText[i % scoreText.length]);
                }
            });
        }
        valueAnimator.start();
        tvBack.setOnClickListener(this);
    }

    /**
     * 点击“确认”按钮事件
     */
    public void setTvEnsure(View.OnClickListener clickListener) {
        tvEnsure.setOnClickListener(clickListener);
    }

    /**
     * 点击“返回”按钮事件
     */
    public void setTvBack(View.OnClickListener clickListener) {
        tvBack.setOnClickListener(clickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
//                dismiss();    //GC20221019
                break;
            default:
                break;
        }
    }

    public EnsureHVDialog(@NonNull Context context) {
        super(context);
    }
}
