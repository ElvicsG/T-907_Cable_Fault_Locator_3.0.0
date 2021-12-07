package net.kehui.www.t_907_origin_V3.application;

/**
 * @author common
 * @date 2019/7/8
 */
public class Constant {
    public static final String SSID = "T-907";
    public static final String DEVICE_IP = "192.168.5.143";
    public static final String BASE_API = "http://cfl.kehui.cn/";
    public static final String PARAM_INFO_KEY = "param_info_key";
    public static final String PULSE_WIDTH_INFO_KEY = "pulse_width_info_key";
    /**
     * 定义数据库显示的广播  //GC20190713
     */
    public static final String DISPLAY_ACTION = "display_action";

    public static final int MI_UNIT = 0;
    public static final int FT_UNIT = 1;
    public static int CurrentUnit = MI_UNIT;
    public static int CurrentSaveUnit = MI_UNIT;

    public static int ModeValue;
    public static int RangeValue;
    public static int Gain;
    public static int SaveToDBGain;
    public static double Velocity;
    public static int DensityMax;
    public static String Date;
    public static String Time;
    public static int Mode;//16进制指令
    public static int Range;
    public static int Phase;//代表相位编码
    public static int PositonV;//虚光标
    public static int PositionR;//实光标
    public static double CurrentLocation;
    public static double SaveLocation;
    public static int[] Para;

    /**
     * 全自动高压参数  //GC21211203
     */
    public static int WorkingMode = 0;
    public static int WorkingModeCommand;
    public static int gear = 2; //初始化档位为32kV
    public static int setVoltage = 0;
    public static int time = 5;

    /**
     * 非SIM波形和SIM第一条波形
     */
    public static int[] WaveData;
    /**
     * SIM第二条波形  SIM共接收9条波形（1+8的组合共8组）
     */
    public static int[] SimData;
    public static int[] TempData1;
    public static int[] TempData2;
    public static int[] TempData3;
    public static int[] TempData4;
    public static int[] TempData5;
    public static int[] TempData6;
    public static int[] TempData7;
    public static int[] TempData8;

    public static String currentLanguage = "";

    /**
     * 脉宽是否设置    //GC20200331
     */
    public static boolean hasSavedPulseWidth;

    /**
     * 电量状态记录   //GC20200314
     */
    public static int batteryValue = -1;

    //TODO 20200407 增加测试中控制，测试中未绘制波形，不要请求电量。
    public static boolean isTesting = false;

    /**
     * 接收的非SIM波形数据长度、单条SIM波形数据长度    //GC20200428    //GC? 与恩诺不同
     */
    public static int waveLen = 549;
    public static int waveSimLen = 549;

    /**
     * 是否需要补齐波形数据    //GC20200428   //GC? 与恩诺不同
     */
    public static boolean needAddData = false;

}
