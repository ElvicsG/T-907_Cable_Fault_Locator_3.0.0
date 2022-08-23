package net.kehui.www.t_907_origin_V3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import net.kehui.www.t_907_origin_V3.R;
import net.kehui.www.t_907_origin_V3.application.Constant;
import net.kehui.www.t_907_origin_V3.view.ModeActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Gong
 * @date 2022/05/19
 * 这是方式栏
 */
public class ModeFragment extends Fragment {
    @BindView(R.id.btn_tdr)
    public ImageView btnTdr;
    @BindView(R.id.btn_icm)
    public ImageView btnIcm;
    @BindView(R.id.btn_sim)
    public ImageView btnSim;
    @BindView(R.id.btn_decay)
    public ImageView btnDecay;
    @BindView(R.id.btn_icmc)
    public ImageView btnIcmc;
    @BindView(R.id.btn_locate)
    public ImageView btnIcml;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View modeLayout = inflater.inflate(R.layout.fragment_mode, container, false);
        unbinder = ButterKnife.bind(this, modeLayout);
        return modeLayout;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //按照TDR方式初始化    //GC20220819
        btnTdr.setEnabled(false);
        btnTdr.setImageResource(R.drawable.bg_tdr_mode_pressed);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_tdr, R.id.btn_icm, R.id.btn_sim, R.id.btn_decay, R.id.btn_icmc, R.id.btn_locate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_tdr:
                //当前电压大于2kV     //GC20220803
                if (Constant.currentVoltage > 2) {
                    ((ModeActivity) Objects.requireNonNull(getActivity())).dangerousNote();
                    return;
                }
                //点击方式选项记录  //GC20220726
                ((ModeActivity) Objects.requireNonNull(getActivity())).modeClick = 0x11;
                ((ModeActivity) Objects.requireNonNull(getActivity())).setMode(0x11);
                ((ModeActivity)getActivity()).modeTest();
                break;
            case R.id.btn_icm:
                //当前电压大于2kV     //GC20220803
                if (Constant.currentVoltage > 2) {
                    ((ModeActivity) Objects.requireNonNull(getActivity())).dangerousNote();
                    return;
                }
                //点击方式选项记录  //GC20220726
                ((ModeActivity) Objects.requireNonNull(getActivity())).modeClick = 0x22;
                //判断是否弹出合闸提示  //GC20220726
                if (((ModeActivity)getActivity()).isSwitchOn) {
                    //从TDR方式转过来必须弹出合闸提示
                    if (((ModeActivity) Objects.requireNonNull(getActivity())).modeMemory == 0x11){
                        ((ModeActivity)getActivity()).showSwitchOnNoteDialog();
                    } else {
                        ((ModeActivity) Objects.requireNonNull(getActivity())).setMode(0x22);
                        ((ModeActivity)getActivity()).modeTest();
                    }
                } else {
                    //WIFI重连后弹出合闸提示
                    ((ModeActivity)getActivity()).showSwitchOnNoteDialog();
                }
                break;
            case R.id.btn_sim:
                //当前电压大于2kV     //GC20220803
                if (Constant.currentVoltage > 2) {
                    ((ModeActivity) Objects.requireNonNull(getActivity())).dangerousNote();
                    return;
                }
                Constant.isClickSim = true;    //点击SIM方式记录  //GC20220806
                //点击方式选项记录  //GC20220726
                ((ModeActivity) Objects.requireNonNull(getActivity())).modeClick = 0x33;
                //判断是否弹出合闸提示  //GC20220726
                if (((ModeActivity)getActivity()).isSwitchOn) {
                    //从TDR方式转过来必须弹出合闸提示
                    if (((ModeActivity) Objects.requireNonNull(getActivity())).modeMemory == 0x11){
                        ((ModeActivity)getActivity()).showSwitchOnNoteDialog();
                    } else {
                        //点击SIM先自动寻找范围     //GC20220806
                        ((ModeActivity) Objects.requireNonNull(getActivity())).simAutoTest();
                    }
                } else {
                    //WIFI重连后提示合闸
                    ((ModeActivity)getActivity()).showSwitchOnNoteDialog();
                }
                break;
            case R.id.btn_icmc:
                ((ModeActivity) Objects.requireNonNull(getActivity())).setMode(0x55);
                ((ModeActivity)getActivity()).modeTest();
                break;
            case R.id.btn_decay:
                ((ModeActivity) Objects.requireNonNull(getActivity())).setMode(0x44);
                ((ModeActivity)getActivity()).modeTest();
                break;
            case R.id.btn_locate:
                //当前电压大于2kV
                if (Constant.currentVoltage > 2) {
                    ((ModeActivity) Objects.requireNonNull(getActivity())).dangerousNote();
                    return;
                }
                Constant.isClickLocate = true;    //点击定点方式记录（以下和ICM方式完全一样）    //GC20220809
                //点击方式选项记录  //GC20220726
                ((ModeActivity) Objects.requireNonNull(getActivity())).modeClick = 0x22;
                //判断是否弹出合闸提示  //GC20220726
                if (((ModeActivity)getActivity()).isSwitchOn) {
                    //从TDR方式转过来必须弹出合闸提示
                    if (((ModeActivity) Objects.requireNonNull(getActivity())).modeMemory == 0x11){
                        ((ModeActivity)getActivity()).showSwitchOnNoteDialog();
                    } else {
                        ((ModeActivity) Objects.requireNonNull(getActivity())).setMode(0x22);
                        ((ModeActivity)getActivity()).modeTest();
                    }
                } else {
                    //WIFI重连后提示合闸
                    ((ModeActivity)getActivity()).showSwitchOnNoteDialog();
                }
                break;
            default:
                break;
        }
    }
}
