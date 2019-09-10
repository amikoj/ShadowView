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
    void setShadowRadius(float radius);

    //设置应用颜色
    void setShadowColor(int color);

    //设置阴影颜色资源文件id
    void setShadowColorRes(@ColorRes int colorRes);

    //设置模糊半径
    void setBlurRadius(float radius);

    //更新界面
    void commit();

}
