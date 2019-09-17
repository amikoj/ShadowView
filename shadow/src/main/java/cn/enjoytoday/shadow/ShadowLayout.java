package cn.enjoytoday.shadow;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;

/**
 * Created by Android Studio.
 * User: caihaifei
 * Date: 2019/9/10
 * Time: 10:27
 * WebBlog:http://www.enjoytoday.cn
 *
 *  阴影布局
 *
 */
public class ShadowLayout extends LinearLayout {


    private static  final  String TAG = "ShadowLayout";

    //默认阴影半径
    public static final float SHADOW_DEFAULT_RADIUS =  DimenUtil.INSTANCE.dp2px(5);

    //阴影最大偏移量
    public static final float SHADOW_MAX_OFFSET = DimenUtil.INSTANCE.dp2px(20);

    //阴影最大模糊半径
    public static final float SHADOW_MAX_BLUR = DimenUtil.INSTANCE.dp2px(20);



    //默认模糊半径
    public static final float SHADOW_DEFAULT_BLUR_RADIUS = DimenUtil.INSTANCE.dp2px(5);


    //阴影颜色
    private int shadowColor = Color.parseColor("#333333");

    //阴影类型,0:默认为单边 1:单边 2:邻边 3:四边所有
    private int shadowType;

    //阴影半径
    private float shadowRadius = 0f;

    //模糊度半径
    private  float blurRadius = SHADOW_DEFAULT_BLUR_RADIUS ;

    //水平位移
    private float xOffset  = DimenUtil.INSTANCE.dp2px(10);


    //竖直方向位移
    private float yOffset = DimenUtil.INSTANCE.dp2px(10);

    //背景色
    private int bgColor = Color.WHITE;

    //是否有点击效果
    private boolean hasEffect =  false ;


    int left =0 ,right =0,top = 0,bottom = 0 ;

    //代理方式
    private Shadow shadow = new ShadowConfig(this);

    private float mWidthMode;
    private float mHeightMode;
    private Paint mPaint = new Paint();
    private Paint locationPaint = new Paint();

    public ShadowLayout(Context context) {
        super(context,null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);//取消硬件加速
        TypedArray typedArray =  context.obtainStyledAttributes(attrs,R.styleable.ShadowLayout);
        shadowColor = typedArray.getColor(R.styleable.ShadowLayout_shadowColor, Color.BLUE);
        blurRadius = typedArray.getDimension(R.styleable.ShadowLayout_blurRadius, SHADOW_DEFAULT_BLUR_RADIUS);
        shadowRadius =  typedArray.getDimension(R.styleable.ShadowLayout_shadowRadius,0);
        hasEffect = typedArray.getBoolean(R.styleable.ShadowLayout_hasEffect, false);
        xOffset = typedArray.getDimension(R.styleable.ShadowLayout_xOffset,DimenUtil.INSTANCE.dp2px(10));
        yOffset = typedArray.getDimension(R.styleable.ShadowLayout_yOffset,DimenUtil.INSTANCE.dp2px(10));
        bgColor = typedArray.getColor(R.styleable.ShadowLayout_bgColor,Color.WHITE);
        typedArray.recycle();

        if (shadowRadius<0){
            shadowRadius = -shadowRadius;
        }
        if (blurRadius < 0) {
            blurRadius = -blurRadius;
        }

        blurRadius = Math.min(SHADOW_MAX_BLUR,blurRadius);

        if (Math.abs(xOffset)> SHADOW_MAX_OFFSET){
            xOffset = xOffset/Math.abs(xOffset) * SHADOW_MAX_OFFSET;
        }

        if (Math.abs(yOffset) > SHADOW_MAX_OFFSET){
            yOffset = yOffset/Math.abs(yOffset) * SHADOW_MAX_OFFSET;
        }
        setBackgroundColor(Color.parseColor("#00ffffff"));
        init();
    }

    private void init(){
        if (xOffset>0){
            //水平偏移量为正数，右侧有阴影，阴影长度为blurRadius+|xOffset|
            right = (int)(blurRadius + Math.abs(xOffset));
        }else if (xOffset==0){
            //水平偏移为0,水平间距为blurRadius
            left = (int)blurRadius;
            right = (int)blurRadius;
        }else {
            //水平偏移为负数,左侧有阴影，阴影长度为blurRadius+|xOffset|
            left = (int)(blurRadius + Math.abs(xOffset));
        }
        if (yOffset>0){
            //竖直偏移量为正数，底部有阴影，阴影长度为blurRadius+|yOffset|
            bottom = (int)(blurRadius + Math.abs(yOffset));
        }else if (yOffset==0){
            //竖直偏移量为0，竖直间距为blurRadius
            top = (int)blurRadius;
            bottom = (int)blurRadius;
        }else {
            //竖直偏移量为负数，顶部有阴影，阴影长度为blurRadius+|yOffset|
            top = (int)(blurRadius + Math.abs(yOffset));
        }
        setPadding(left,top,right,bottom);
    }





    /**
     * 获取阴影设置
     * @return 返回阴影设置配置
     */
    public  Shadow getShadowConfig(){
        return shadow;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);//放在super前是后景，相反是前景，前景会覆盖子布局
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }




    //绘制背景色(在子view底部)
    private void drawBackground(Canvas canvas){

        mWidthMode = getMeasuredWidth();
        mHeightMode =  getMeasuredHeight();
        float startX = 0;
        float startY = 0;
        float endX = 0;
        float endY = 0;

        if (xOffset==0){
            startX = right;
            endX = mWidthMode-blurRadius;
        }else {
            startX = right+blurRadius;
            endX = mWidthMode-left-blurRadius;
        }

        if (yOffset==0){
            startY = bottom;
            endY = mHeightMode-blurRadius;
        }else {
            startY = bottom+blurRadius;
            endY = mHeightMode-top-blurRadius;
        }
//        mPaint.setShadowLayer(blurRadius,0,0,shadowColor);
        if (blurRadius>0){
            mPaint.setMaskFilter(new BlurMaskFilter(blurRadius,BlurMaskFilter.Blur.NORMAL));
        }
        mPaint.setColor(shadowColor);
        mPaint.setAntiAlias(true);

        RectF shadowRect = new  RectF(startX,startY,endX,endY);

        RectF locationRectF = new RectF(left,top,mWidthMode-right,mHeightMode-bottom);
        if (shadowRadius==0){
            //不是圆角
            canvas.drawRect(shadowRect,mPaint);
        }else {
            //圆角，角度为shadowRadius
            canvas.drawRoundRect(shadowRect,shadowRadius,shadowRadius,mPaint);
        }

        locationPaint.setColor(bgColor);
        locationPaint.setAntiAlias(true);

        if (shadowRadius==0){
            //不是圆角
            canvas.drawRect(locationRectF,locationPaint);
        }else {
            //圆角，角度为shadowRadius
            canvas.drawRoundRect(locationRectF,shadowRadius,shadowRadius,locationPaint);
        }
    }



    /**
     * 阴影配置
     */
    class ShadowConfig implements Shadow{

        //代理
        private ShadowLayout shadow;

        private ShadowConfig(ShadowLayout shadow) {
            this.shadow = shadow;
        }

        @Override
        public Shadow setShadowRadius(float radius) {
            return  setShadowRadius(TypedValue.COMPLEX_UNIT_DIP,radius);
        }

        @Override
        public Shadow setShadowRadius(int unit, float radius) {
            Context c = getContext();
            Resources r;

            if (c == null) {
                r = Resources.getSystem();
            } else {
                r = c.getResources();
            }
            shadow.shadowRadius =  Math.abs(TypedValue.applyDimension(unit,radius,r.getDisplayMetrics()));
            return this;
        }

        @Override
        public Shadow setShadowColor(int color) {
            shadow.shadowColor = color;
            return this;
        }

        @Override
        public Shadow setShadowColorRes(int colorRes) {
            shadow.shadowColor = shadow.getResources().getColor(colorRes);
            return this;
        }

        @Override
        public Shadow setBlurRadius(float radius) {
          return  setBlurRadius(TypedValue.COMPLEX_UNIT_DIP,radius);
        }

        @Override
        public Shadow setBlurRadius(int unit, float radius) {
            Context c = getContext();
            Resources r;
            if (c == null) {
                r = Resources.getSystem();
            } else {
                r = c.getResources();
            }
            shadow.blurRadius = Math.min(SHADOW_MAX_BLUR,Math.abs(TypedValue.applyDimension(unit,radius,r.getDisplayMetrics())));
            return this;
        }

        @Override
        public Shadow setXOffset(float offset) {
            return setXOffset(TypedValue.COMPLEX_UNIT_DIP,offset);
        }

        @Override
        public Shadow setXOffset(int unit, float offset) {
            Context c = getContext();
            Resources r;
            if (c == null) {
                r = Resources.getSystem();
            } else {
                r = c.getResources();
            }

            float x = TypedValue.applyDimension(unit,offset,r.getDisplayMetrics());
            if (Math.abs(x)> SHADOW_MAX_OFFSET){
                x = x/Math.abs(x) * SHADOW_MAX_OFFSET;
            }
            shadow.xOffset = x;
            return  this;
        }

        @Override
        public Shadow setYOffset(float offset) {
           return   setYOffset(TypedValue.COMPLEX_UNIT_DIP,offset);
        }

        @Override
        public Shadow setYOffset(int unit, float offset) {
            Context c = getContext();
            Resources r;
            if (c == null) {
                r = Resources.getSystem();
            } else {
                r = c.getResources();
            }

            float y = TypedValue.applyDimension(unit,offset,r.getDisplayMetrics());
            if (Math.abs(y)> SHADOW_MAX_OFFSET){
                y = y/Math.abs(y) * SHADOW_MAX_OFFSET;
            }
            shadow.yOffset = y;
            return this;
        }

        @Override
        public void commit() {
            shadow.init();
            shadow.requestLayout();
            shadow.postInvalidate();
        }
    }


}
