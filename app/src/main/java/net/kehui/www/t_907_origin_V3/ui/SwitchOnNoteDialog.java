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
public class SwitchOnNoteDialog extends BaseDialog implements View.OnClickListener {

    public TextView tvNoteSwitchOn;
    public ImageView ivScanSwitchOn;
    public TextView tvYes;
    public TextView tvNo;

    private View view;
    private ValueAnimator valueAnimator;
    private int[] scoreText = {R.drawable.ic_wait_empty, R.drawable.ic_wait_1, R.drawable.ic_wait_2, R.drawable.ic_wait_3}; //GC20220919

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(getContext(), R.layout.dialog_switch_on_note, null);
        setContentView(view);
        initView();

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) ( ScreenUtils.getScreenWidth(getContext()) * 0.45);
        params.height = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.3);
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void initView() {
        //添加对话框,动画图片初始化 //GC20220919
        tvNoteSwitchOn = view.findViewById(R.id.tv_note_switch_on);
        ivScanSwitchOn = view.findViewById(R.id.iv_scan_switch_on);
        tvYes = view.findViewById(R.id.tv_yes);
        tvNo = view.findViewById(R.id.tv_no);

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
        tvNo.setOnClickListener(this);
    }

    /**
     * 点击“是，下一步”按钮事件
     */
    public void setTvYes(View.OnClickListener clickListener) {
        tvYes.setOnClickListener(clickListener);
    }

    /**
     * 点击“否，上一步”按钮事件
     */
    public void setTvNo(View.OnClickListener clickListener) {
        tvNo.setOnClickListener(clickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_no:
//                dismiss();    //GC20221019
                break;
            default:
                break;
        }
    }

    public SwitchOnNoteDialog(@NonNull Context context) {
        super(context);
    }
}
