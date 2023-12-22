package net.kehui.www.t_907_origin_V3.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import net.kehui.www.t_907_origin_V3.R;
import net.kehui.www.t_907_origin_V3.util.ScreenUtils;

/**
 * @author gong //GC20221206
 * @date 2022/12/06
 */
public class WarningNoteDialog extends BaseDialog implements View.OnClickListener {

    public TextView tvNote;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(getContext(), R.layout.dialog_warning, null);
        setContentView(view);
        initView();

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) ( ScreenUtils.getScreenWidth(getContext()) * 0.5);
        params.height = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.4);
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void initView() {
        tvNote = view.findViewById(R.id.tv_note);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    public WarningNoteDialog(@NonNull Context context) {
        super(context);
    }
}
