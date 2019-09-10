package cn.enjoytoday.shadow;

/**
 * Created by Android Studio.
 * User: caihaifei
 * Date: 2019/9/10
 * Time: 16:20
 * WebBlog:http://www.enjoytoday.cn
 * 代理调用
 */
public class ShadowConfig implements Shadow{

    //代理
    private Shadow shadow;

    public ShadowConfig(Shadow shadow) {
        this.shadow = shadow;
    }

    @Override
    public void setShadowRadius(float radius) {
        shadow.setShadowRadius(radius);
    }

    @Override
    public void setShadowColor(int color) {
        shadow.setShadowColor(color);
    }

    @Override
    public void setShadowColorRes(int colorRes) {
        shadow.setShadowColorRes(colorRes);
    }

    @Override
    public void setBlurRadius(float radius) {
        shadow.setBlurRadius(radius);

    }
}
