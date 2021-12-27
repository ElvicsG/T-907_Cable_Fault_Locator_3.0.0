package net.kehui.www.t_907_origin_V3.base;

import net.kehui.www.t_907_origin_V3.adpter.DataAdapter;
import net.kehui.www.t_907_origin_V3.adpter.MyChartAdapterBase;
import net.kehui.www.t_907_origin_V3.dao.DataDao;
import net.kehui.www.t_907_origin_V3.global.BaseAppData;
import net.kehui.www.t_907_origin_V3.util.MultiLanguageUtil;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

/**
 * @author Gong
 * @date 2019/07/15
 */

public class BaseActivity extends AppCompatActivity {

    /**
     * sparkView图形绘制部分
     */
    public MyChartAdapterBase myChartAdapterMainWave;

    public boolean gainButtonChanged;
    public boolean balanceButtonChanged;
    public boolean waveButtonChanged = true;

    /**
     * 波形参数
     */
    public int mode;
    public int modeBefore;
    public int range;
    public int rangeBefore;
    public int rangeState;
    public int gain;
    public double velocity;
    public int density;
    public int densityMax;
    public int balance;
    public int delay;
    public int inductor;

    /**
     * 波宽度全局变量
     */
    public int pulseWidth;
    /**
     * 0xF7,0xF7,0xED,0xBF,0xA5,0x4D,0x00,0x00
     *  247, 247, 237, 191, 165,  77,   0,   0
     *  320, 320, 720,2560,3600,7120,   10200      //GC20200527    255-X/40;X为输入值  二次脉冲脉宽命令发送值 （按照这个比例选择pulseRemove）
     */
    public int pulseWidthSim;
    public int selectSim;
    public boolean isCom;
    public boolean isMemory;
    public boolean isDatabase;
    public int[] pulseRemove = {75, 75, 75, 169, 600, 844, 1669, 2391, 2391};
    public int[] pulsetdrRemove11 = {22,22,36,36,76,92,138,288,576}; //做判断使用，是否在零点附近
    public int[] pulsetdrRemove1 = {4,4,8,16,32,64,128,256,512}; //jk20210309 判断向上向下
    public int[] pulsetdrRemove = {10,10,15,18,32,64,128,256,512};//判断向上向下后的 已经知道波形向上 去除点数，
    public int[] pulselongtdrRemove = {45,45,66,74,86,92,138,288,576};  //jk202210303 低压脉冲去除波头  切换范围使用
    public int[] tdrPoint = {4,5,6,8,10,25,25,25,25};  //自动测距在对长距离因取点问题与实际距离不符的现象时的程序去除点数
    public int[] tdrPointuse = {4,4,8,16,32,64,128,256,512}; //脉宽里的点归零

    public int g;  // 低压脉冲极值最大或最小点
    public int u;  //低压脉冲 曲线拟合脉冲起始点
    public int autoLocation; //低压脉冲故障点位置
    public int autoLocation1; //过渡使用

    public int[] search_start_list = {1,1,400,800,1600,3200,6400,12800,25600};//sc TDR自动测试使用
    public int[] search_end_list = {251,501,1000,2000,4000,8000,16000,32000,64000};//sc TDR自动测试使用
    public int[] gain_value_list = {13,13,13,10,10,10,9,9,9};
    public int step = 8;
    public int count = 6;
    public int balanceState;
    public boolean isLongClick;
    public boolean longTestInit;
    public boolean balanceIsReady;
    public boolean rangeIsReady;
    public int rangeAdjust = 0;
    public int secondMAx;
    public int secondMIn;
    public int secondMAxPos;
    public int secondMInPos;
    public int Median_value = 128; //jk20210519  基准数
    /**
     * 波形原始数据数组
     */
    public int dataMax;
    public int dataLength;
    public int[] waveArray;
    public int[] simArray1;
    public int[] simArray2;
    public int[] simArray3;
    public int[] simArray4;
    public int[] simArray5;
    public int[] simArray6;
    public int[] simArray7;
    public int[] simArray8;
    public int[] simArray;
    /**
     * SIM筛选  //GC20200529
     */
    public int[] overlapNum = new int[8];
    public int[] simSum = new int[9];
    public double[] simArray0Filter = new double[65560];
    public double[] simArray1Filter = new double[65560];
    public double[] simArray2Filter = new double[65560];
    public double[] simArray3Filter = new double[65560];
    public double[] simArray4Filter = new double[65560];
    public double[] simArray5Filter = new double[65560];
    public double[] simArray6Filter = new double[65560];
    public double[] simArray7Filter = new double[65560];
    public double[] simArray8Filter = new double[65560];
    public boolean receiveSimOver;

    /**
     * 波形绘制数据数组（抽点510个）
     */
    public int[] waveDraw;
    public int[] waveCompare;
    public int[] waveCompareDraw;
    public int[] simDraw1;
    public int[] simDraw2;
    public int[] simDraw3;
    public int[] simDraw4;
    public int[] simDraw5;
    public int[] simDraw6;
    public int[] simDraw7;
    public int[] simDraw8;

    /**
     * 不同范围和方式下，波形数据的点数、需要去掉的冗余点数、比例值
     */
    public final static int[] READ_TDR_SIM = {540, 540, 1052, 2076, 4124, 8220, 16412, 32796, 65556};
    public final static int[] READ_ICM_DECAY = {2068, 2068, 4116, 8212, 16404, 32788, 65556, 32788, 65556};
    public int[] removeTdrSim = {30, 30, 32, 36, 44, 60, 92, 156, 276};
    public int[] removeIcmDecay = {28, 28, 36, 52, 84, 148, 276, 148, 276};
    public int[] densityMaxTdrSim = {1, 1, 2, 4, 8, 16, 32, 64, 128};
    public int[] densityMaxIcmDecay = {4, 4, 8, 16, 32, 64, 128, 64, 128};

    /**
     * 光标参数
     */
    public int simOriginalZero;
    public int zero;
    public int pointDistance;
    public int simStandardZero;
    public int positionReal;
    public int positionVirtual;
    public int positionVirtualChange;
    public int positionSim;

    /**
     * 波形滑动相关
     */
    public int currentStart = 0;
    public int currentMoverPosition510 = 0;
    public int moverMoveValue = 0;

    /**
     * ICM自动测距参数
     */
    public int gainState;
    public int breakdownPosition;
    public int breakBk;
    public int faultResult;
    public double[] waveArrayFilter = new double[65560];
    public double[] waveArrayIntegral = new double[65560];
    public double[] s1 = new double[65560];
    public double[] s2 = new double[65560];

    /**
     * ICM自动测距参数    //GC20191231
     */
    public boolean breakDown;

    /**
     * 测试缆信息添加    //GC20200103
     */
    public double leadLength;
    public double leadVop;

    public int[] wifiStream;
    /**
     * APP下发的命令协议(16进制)（8个字节）
     * 数据头     数据长度  指令  传输数据  校验和
     * eb90aa55     03      01      11       15
     * 指令0x01测试命令
     * eb90aa55 03 01 11 15	    测试0x11
     * eb90aa55 03 01 22 26	    取消测试0x22
     * 0x02方式
     * eb90aa55 03 02 11 16		TDR低压脉冲方式
     * eb90aa55 03 02 22 27		ICM脉冲电流方式
     * eb90aa55 03 02 33 38		SIM二次脉冲方式
     * 0x03范围
     * eb90aa55 03 03 11 17		范围500m
     * eb90aa55 03 03 22 28
     * eb90aa55 03 03 33 39
     * eb90aa55 03 03 44 4a
     * eb90aa55 03 03 55 5b
     * eb90aa55 03 03 66 6c
     * eb90aa55 03 03 77 7d
     * eb90aa55 03 03 88 8e		范围64km
     * 0x04增益
     * eb90aa55 03 04 11 18		增益+
     * eb90aa55 03 04 22 29		增益-
     * 0x05延时
     * eb90aa55 03 05 11 19		延时+
     * eb90aa55 03 05 22 2a		延时-
     * 0x06获取电池电量
     * eb90aa55 03 06 13 1c
     * 0x07平衡
     * eb90aa55 03 07 11 1b  	平衡+
     * eb90aa55 03 07 22 2c		平衡-
     * 0x09需要数据
     * eb90aa55 03 09 11 1d
     * 0x0a波宽度
     * eb90aa55 03 0a xx xx
     */
    public int command;
    public final static int COMMAND_TEST = 0x01;
    public final static int COMMAND_MODE = 0x02;
    public final static int COMMAND_RANGE = 0x03;
    public final static int COMMAND_GAIN = 0x04;
    public final static int COMMAND_DELAY = 0x05;
    public final static int COMMAND_BALANCE = 0x07;
    public final static int COMMAND_RECEIVE_WAVE = 0x09;
    public final static int COMMAND_PULSE_WIDTH = 0x0a;

    public int dataTransfer;
    public final static int TESTING = 0x11;
    public final static int CANCEL_TEST = 0x22;
    public final static int TDR = 0x11;
    public final static int ICM = 0x22;
    public final static int SIM = 0x33;
    public final static int DECAY     = 0x44;
    public final static int ICM_DECAY = 0x55;
    public final static int RANGE_250   = 0x99;
    public final static int RANGE_500   = 0x11;
    public final static int RANGE_1_KM  = 0x22;
    public final static int RANGE_2_KM  = 0x33;
    public final static int RANGE_4_KM  = 0x44;
    public final static int RANGE_8_KM  = 0x55;
    public final static int RANGE_16_KM = 0x66;
    public final static int RANGE_32_KM = 0x77;
    public final static int RANGE_64_KM = 0x88;
    /**
     * 发送高压模块指令协议  //GC20211206
     * 0x60 高压设定（10个字节）
     * eb90aa55 05 60 0c cc 01 sum   0x0ccc：32kV 0x02：30mA档位 / 8kV.120mA
     * eb90aa55 05 60 06 66 02 sum   0x0666：16kV 0x01：60mA档位 / 4kV.240mA
     * 0x61 开关指令（8个字节）
     * eb90aa55 03 61 01 sum    01:高压开 02：高压关
     * 0x62 查询指令
     * eb90aa55 03 62 00 sum
     * 0x70 工作方式指令
     * eb90aa55 03 70 00 sum    00：直流 01：单次 02：周期
     * 0x71 单次放电指令
     * eb90aa55 03 71 01 75    01：放电   //GC20211209
     */
    public final static int COMMAND_VOLTAGE_SET = 0x60;
    public final static int COMMAND_VOLTAGE_SWITCH = 0x61;
    public final static int COMMAND_VOLTAGE_QUERY = 0x62;
    public final static int COMMAND_WORKING_MODE = 0x70;
    public final static int COMMAND_SINGLE_PULSE = 0x71;

    public final static int OPEN = 0x01;
    public final static int CLOSE = 0x02;
    public final static int QUERY = 0x00;
    public final static int DC = 0x00;
    public final static int PULSE = 0x01;
    public final static int CYCLIC = 0x02;

    public int dataTransfer2;
    public int dataTransfer3;
    public final static int GEAR1 = 0x01;
    public final static int GEAR2 = 0x02;

    /*** APP接收的命令（8个字节）
     * 数据头     数据长度  指令  传输数据  校验和
     * eb90aa55     03      01      33       ..  （0x33正确 0x44错误）
     * eb90aa55 03 08 11 1c		//接收到触发信号
     */
    public final static int COMMAND = 0x55;
    public final static int COMMAND_TRIGGER = 0x08;
    public final static int TRIGGERED = 0x11;
    /**
     * 接收高压模块反馈
     * eb90aa55 04 62 06 66 sum （9个字节）
     * eb90aa55 03 80 xx sum （8个字节）
     */
    public final static int COMMAND_VOLTAGE = 0x62;
    public final static int COMMAND_QUERY_FEEDBACK = 0x80;
    /**
     * 获取电池电量命令 传输数据2个字节（9个字节）
     * eb90aa55 04 06 0c 53 69		//0x0c53=3155
     */
    public final static int POWER_DISPLAY = 0x06;
    /**
     * APP接收到的波形数据头
     * 数据头      数据长度    传输数据    校验和
     * eb90aaXX    4个字节     X……X       xx
     * 以XX区分—— 非二次脉冲 和 二次脉冲 （0x88 0x99 0xaa 0xbb 分别为二次脉冲第一到底四条波形）
     */
    public final static int WAVE_TDR_ICM_DECAY = 0x66;
    public final static int WAVE_SIM = 0x77;

    /**
     * 数据库存储波形部分
     */
    public DataAdapter adapter;
    public DataDao dao;
    public int selectedId;

    public static BaseActivity baseActivity;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //传递给非activity的类使用（记录显示中英文切换资源获取）  //GC20200525
        baseActivity = this;
        mContext = this.getBaseContext();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //G??   让程序变卡？
        initParameter();
    }

    /**
     * 参数初始化
     */
    private void initParameter() {
        mode = TDR;
        range = RANGE_500;
        rangeState = 1;
        gain = 13;
        velocity = 172;
        density = 1;
        densityMax = 1;
        balance = 5;
        /*if(isLongClick = false){                   //jk20200716   平衡自动调整时平衡初值改变
        balance = 5;}else if(isLongClick = true){
            balance = 8;
        }*/
        inductor = 3;
        //二次脉冲多组数据选择
        selectSim = 1;

        dataMax = 540;
        dataLength = 510;
        waveArray = new int[540];
        waveDraw = new int[510];
        waveCompare = new int[510];
        waveCompareDraw = new int[510];
        simDraw1 = new int[510];
        simDraw2 = new int[510];
        simDraw3 = new int[510];
        simDraw4 = new int[510];
        simDraw5 = new int[510];
        simDraw6 = new int[510];
        simDraw7 = new int[510];
        simDraw8 = new int[510];

        //光标原始位置
        zero = 0;
        pointDistance = 255;
        //光标画布位置（变化范围0-509）
        positionReal = 0;
        positionVirtual = 255;
        //虚光标画布中变化的数值
        positionVirtualChange = 0;

        //增益大小状态
        gainState = 0;
        //平衡状态
        balanceState = 0;
        //故障击穿时刻对应的那一点
        breakdownPosition = 0;
        //击穿点
        breakBk = 0;
        //数据库相关 //20200520 //G?
        BaseAppData db = Room.databaseBuilder(getApplicationContext(), BaseAppData.class, "database-wave").allowMainThreadQueries().build();
        dao = db.dataDao();
    }

    /*
     ** 尝试关闭activity
     */
    public void tryclose(){
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));
    }

}

/*————enNuo————*/
//EN20200324    发送命令和获取电量修改，增加条件限制，避免极端条件下会多次尝试连接
//20200407  电量获取修改
//20200416  未连接不执行
//20200520  数据库相关
//20200521  界面相关
//20200522  单位转化逻辑修正
//20200523  其它优化

/*——————————其它——————————*/
//GC?  //G??
//GN界面优化可能用到
//GT 调试
//GT20200619    每个点高度显示
//GT20200629    数据库打开算法结果显示调试
/*——————————其它——————————*/

/*更改记录*/
//GC20190629 光标使用优化
//GC20190703 记忆比较功能
//GC20190704 增益、平衡、延时命令调节
//GC20190708 ICM自动测距                ***************
//GC20190709 距离计算，比例选择
//GC20190712 光标零点设置
//GC20190713 数据库波形显示

/*——————————自动测距调整——————————*/
//GC20191223    250m范围取点和距离计算
//GC20191231    自动测距修改
//GC20200103    测试缆信息添加
//GC20200106    光标定位修改
//GC20200109    DC方式下自动测距单独实现
//GC20200110    击穿点判断起始位置更改
//GC20200529    SIM自动筛选最优显示
//GC20200601    筛选显示优化
//GC20200606    ICM滤波、增益直流分量参数修改
//GC20200609    SIM自动筛选判断条件增加

//A20190821  a待定
//A20200527  增益大小判断微调
//A20200601  断线故障处理优化
//A20200606  重合系数微调
/*——————————自动测距调整——————————*/

//GC20200313    增益显示转为百分比
//GC20200314    模式界面电量图标同步主页界面
//GC20200319    “等待触发”对话框重连时不消掉BUG修改
//GC20200327    帮助功能添加
//GC20200330    SIM标记光标添加
//GC20200331    不同范围发射不同脉宽功能添加
//GC20200424    不同模式下初始化发射的不同命令
//GC20200428    连接线程的变量初始化修改
//GC20200525    界面布局优化
//GC20200527    SIM方式下脉宽命令发送
//GC20200528    波形滑动区域控制
//GC20200604    按钮状态用户交互优化      //后续优化保留  //GC20200604
//GC20200611    缩放后移动滑块时画光标bug修正
//GC20200612    SIM标记光标（可以自定义）
//GC20200613    延时修改、按钮状态
//GC20200630    离线状态按钮可操作调整
//GC20200716    添加版本号显示
//GC20200817    断线二次脉冲处理
//GC20200916    低压脉冲长按自动测试逻辑改进

//jk20200714    低压脉冲光标、距离修改测试、自动增益
//jk20200715    低压脉冲测试按键长按响应添加
//jk20200716    平衡自动调整
//jk20200804    二次脉冲光标定位
//jk20200904    更改起始判断

//jk20201022     低压脉冲自动定位以133为中心点
//jk20201023     去掉数据库打开波形自动定位
//jk20201130     多次脉冲增益判断数值更改
//jk20201130     多次脉冲延时间隔增加
//jk20201130     脉冲电流延长线不选就不计算
//jk20210420     脉冲电流容错处理  添加标志false_flag
//jk20210427     延长线界面，文件按钮不生效

//jk20210714  网络连接去除一个判断
//jk20210830balance  对平衡调整进行更改
//jk20210901range  对范围切换进行处理，尝试解决对于故障距离靠近临界值的波形进行范围切换

/**
 * 2.0.0版本整理
 */
//jk20210123    直接进入测试方式界面

/**
 * 3.0.0版本整理
 */
//GC20211201    UI：主界面信息栏
//GC20211202    UI：高压操作界面对话框
//GC20211203    UI：自定义旋钮控件、档位选择
//GC20211206    添加协议，增加高压设定指令//GN
//GC20211207    高压操作界面和信息栏：初始化、参数传递
//GC20211208    ConnectThread线程将收到的数据通过ConnectService服务传递给主界面处理//GN
//GC20211209    下发高压模块指令
//GC20211210    接收高压模块反馈
//GC20211213    高压操作、等待触发界面显示
//GC20211214    UI：高压数值进度条更新/服务中toast只可以跟随系统语言
//GC20211215    UI：重新自定义等待触发对话框

//GT屏蔽电量获取 GT自动测距运算屏蔽
//GTT   接收数据处理测试
//GC20211216    "取消测试"：电压归0
//GC20211220    UI：只有“单次”时单次放电按钮有效
//GC20211221    故障反馈处理
//GC20211222    切换工作方式：工作模式恢复为默认的单次
//GC20211223    周期时间指令下发
//GC20211227