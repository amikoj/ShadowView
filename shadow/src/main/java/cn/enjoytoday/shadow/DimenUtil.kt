package cn.enjoytoday.shadow

import android.content.res.Resources

/**
 * Created by Android Studio.
 * User: caihaifei
 * Date: 2019/9/10
 * Time: 14:46
 * WebBlog:http://www.enjoytoday.cn
 */
object DimenUtil {

    fun dp2px(dpValue:Float ):Float{
        return  (0.5f + dpValue * Resources.getSystem().displayMetrics.density)
    }

}