package cn.enjoytoday.shadow;

import androidx.annotation.ColorRes;

/**
 * Created by Android Studio.
 * User: caihaifei
 * Date: 2019/9/10
 * Time: 10:26
 * WebBlog:http://www.enjoytoday.cn
 * 阴影抽象接口
 */
public interface Shadow {

    //设置阴影半径
    Shadow setShadowRadius(float radius);

    //添加单位设置
    Shadow setShadowRadius(int unit,float radius);

    //设置应用颜色
    Shadow setShadowColor(int color);

    //设置阴影颜色资源文件id
    Shadow setShadowColorRes(@ColorRes int colorRes);

    /**
     * 设置模糊半径
     * @param radius
     */
    Shadow setBlurRadius(float radius);

    /**
     *
     * @param unit @{@link android.util.TypedValue#TYPE_DIMENSION}
     * @param radius 模糊半径
     */
    Shadow  setBlurRadius(int unit,float radius);


    /**
     * 设置水平方向的偏移量
     * @param offset x轴偏移
     */
    Shadow setXOffset(float offset);


    /**
     * 设置x方向的偏移量,设置单位
     * @param unit @{@link android.util.TypedValue#TYPE_DIMENSION}
     * @param offset x轴偏移
     */
    Shadow setXOffset(int unit,float offset);

    /**
     * 设置竖直方向的偏移量
     * @param offset y轴偏移
     */
    Shadow setYOffset(float offset);

    /**
     * 设置竖直方向的偏移量,带单位
     * @param unit @{@link android.util.TypedValue#TYPE_DIMENSION}
     * @param offset  y轴偏移
     */
    Shadow setYOffset(int unit,float offset);

    /**
     * 更新绘制
     */
    void commit();

}
