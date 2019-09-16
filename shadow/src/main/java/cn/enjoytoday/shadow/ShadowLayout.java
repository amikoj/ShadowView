package cn.enjoytoday.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Android Studio.
 * User: caihaifei
 * Date: 2019/9/10
 * Time: 10:27
 * WebBlog:http://www.enjoytoday.cn
 *
 *  阴影布局，只可放一个子view
 *
 */
public class ShadowLayout extends LinearLayout {

    //默认单边
    public static final int SHADOW_NORMAL = 0 ;
    //单边模式
    public static final int SHADOW_UNILATERAL = 1 ;
    //邻边
    public static final int SHADOW_NEIGHBOR = 2;
    //多边
    public static final int SHADOW_MORE =  3;
    //左边的边显示阴影
    public static final int SHADOW_ON_LEFT = 0;

    //左边的边显示阴影
    public static final int SHADOW_ON_RIGHT =  1;

    //上面的边显示阴影
    public static final int SHADOW_ON_TOP =  2;


    //下面的边显示阴影
    public static final int SHADOW_ON_BOTTOM = 3;


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

    /**
     *
     * @see #shadowType 为 {@link #SHADOW_UNILATERAL}   类型时,显示阴影的边，默认为
     * @see #SHADOW_ON_BOTTOM
     *
     */
    private int side =  SHADOW_ON_BOTTOM;


    /**
     * 当 {@link #shadowType}  为  {@link #SHADOW_NEIGHBOR} 竖直方向阴影的半径
     */
    private float verticalRadius = SHADOW_DEFAULT_RADIUS;


    /**
     * 当 {@link #shadowType}  为  {@link #SHADOW_NEIGHBOR} 水平方向阴影的半径
     */
    private float horizontalRadius = SHADOW_DEFAULT_RADIUS;

    //左边阴影半径
    private float leftRadius = SHADOW_DEFAULT_RADIUS;

    //右边阴影半径
    private float rightRadius = SHADOW_DEFAULT_RADIUS;

    //顶部阴影半径
    private float topRadius = SHADOW_DEFAULT_RADIUS;

    //底部阴影半径
    private float bottomRadius = SHADOW_DEFAULT_RADIUS;

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
        super(context, attrs,0);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray =  context.obtainStyledAttributes(attrs,R.styleable.ShadowLayout);
        int couter = typedArray.getIndexCount();

        for (int i=0;i<couter;i++){
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.ShadowLayout_shadowColor) {//阴影颜色
                shadowColor = typedArray.getColor(R.styleable.ShadowLayout_shadowColor, getResources().getColor(R.color.defaultShadowColor));
            } else if (attr == R.styleable.ShadowLayout_shadowType) {//阴影类型
                shadowType = typedArray.getInt(R.styleable.ShadowLayout_shadowType, SHADOW_NORMAL);
                if (shadowType == SHADOW_MORE) {
                    //多边
                    leftRadius = typedArray.getDimension(R.styleable.ShadowLayout_leftRadius, SHADOW_DEFAULT_RADIUS);
                    rightRadius = typedArray.getDimension(R.styleable.ShadowLayout_rightRadius, SHADOW_DEFAULT_RADIUS);
                    topRadius = typedArray.getDimension(R.styleable.ShadowLayout_topRadius, SHADOW_DEFAULT_RADIUS);
                    bottomRadius = typedArray.getDimension(R.styleable.ShadowLayout_bottomRadius, SHADOW_DEFAULT_RADIUS);
                } else if (shadowType == SHADOW_NEIGHBOR) {
                    //邻边
                    horizontalRadius = typedArray.getDimension(R.styleable.ShadowLayout_horizontalRadius, SHADOW_DEFAULT_RADIUS);
                    verticalRadius = typedArray.getDimension(R.styleable.ShadowLayout_verticalRadius, SHADOW_DEFAULT_RADIUS);
                } else {
                    side = typedArray.getInt(R.styleable.ShadowLayout_side, SHADOW_ON_BOTTOM);

                }
            } else if (attr == R.styleable.ShadowLayout_shadowRadius) {
                shadowRadius = typedArray.getDimension(R.styleable.ShadowLayout_shadowRadius, SHADOW_DEFAULT_RADIUS);
            } else if (attr == R.styleable.ShadowLayout_blurRadius) {
                blurRadius = typedArray.getDimension(R.styleable.ShadowLayout_blurRadius, SHADOW_DEFAULT_BLUR_RADIUS);
            } else if (attr == R.styleable.ShadowLayout_hasEffect) {
                hasEffect = typedArray.getBoolean(R.styleable.ShadowLayout_hasEffect, false);
            }else if (attr ==  R.styleable.ShadowLayout_xOffset){
                xOffset = typedArray.getDimension(R.styleable.ShadowLayout_xOffset,DimenUtil.INSTANCE.dp2px(10));
            }else  if (attr == R.styleable.ShadowLayout_yOffset){
                yOffset = typedArray.getDimension(R.styleable.ShadowLayout_yOffset,DimenUtil.INSTANCE.dp2px(10));
            }else if (attr ==  R.styleable.ShadowLayout_bgColor){
                bgColor = typedArray.getColor(R.styleable.ShadowLayout_bgColor,Color.WHITE);
            }

        }
        typedArray.recycle();
        init();
    }

    private void init(){

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
        drawBackground(canvas);//放在super前是后景，相反是前景，前景会覆盖子布局
        super.dispatchDraw(canvas);
    }





    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    //绘制前景色(覆盖在子View上面)
    private void drawForegroundBg(Canvas canvas){

    }



    //绘制背景色(在子view底部)
    private void drawBackground(Canvas canvas){
        Log.e("ShadowLayout","width:"+getMeasuredWidth()+",height:"+getHeight());
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);//取消硬件加速
        mWidthMode = getMeasuredWidth();
        mHeightMode =  getMeasuredHeight();
        mPaint.setShadowLayer(blurRadius,0,0,shadowColor);
        mPaint.setColor(shadowColor);//绘制透明色
        mPaint.setAntiAlias(true);
//        float startX = xOffset<=0?0:xOffset;
//        float startY = yOffset<=0?0:yOffset;
//        float endX = xOffset<=0?(mWidthMode-Math.abs(xOffset)):mWidthMode;
//        float endY = yOffset<=0?(mWidthMode-Math.abs(yOffset)):mHeightMode;
        RectF shadowRect = new  RectF(xOffset,yOffset,mWidthMode-Math.abs(xOffset),mWidthMode-Math.abs(yOffset));
        if (shadowRadius==0){
            //不是圆角
            canvas.drawRect(shadowRect,mPaint);
        }else {
            //圆角，角度为shadowRadius
            canvas.drawRoundRect(shadowRect,shadowRadius,shadowRadius,mPaint);
        }

        float locationStartX = xOffset>=0?0:xOffset;
        float locationStartY = yOffset>=0?0:yOffset;
        float locationEndX = xOffset>=0?(mWidthMode-Math.abs(xOffset)):mWidthMode;
        float locationEndY = yOffset>=0?(mWidthMode-Math.abs(yOffset)):mHeightMode;

        RectF locationRectF = new RectF(locationStartX,locationStartY,locationEndX,locationEndY);
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






    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = (int)DimenUtil.INSTANCE.dp2px(250);
        int h = (int)DimenUtil.INSTANCE.dp2px(100);
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
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
        public void setShadowRadius(float radius) {
            shadow.shadowRadius =  radius;
        }

        @Override
        public void setShadowColor(int color) {
            shadow.shadowColor = color;
        }

        @Override
        public void setShadowColorRes(int colorRes) {
            shadow.shadowColor = shadow.getResources().getColor(colorRes);
        }

        @Override
        public void setBlurRadius(float radius) {
            shadow.blurRadius = radius;
        }

        @Override
        public void commit() {
            shadow.postInvalidate();
        }
    }


}
