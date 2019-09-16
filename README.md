# ShadowView 的使用


### 使用说明
Android 控件阴影使用，采用类似于css的Box Shadow 效果的阴影效果和设置方式,使用如下:




##### 添加依赖

```gradle


```







```xml

   <cn.enjoytoday.shadow.ShadowLayout
        android:orientation="vertical"
        android:id="@+id/shadowLayout"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:gravity="center"
        app:shadowRadius="10dp"
        app:shadowColor="#bebebe"
        app:bgColor="#fff"
        app:xOffset="10dp"
        app:yOffset="0dp"
        app:blurRadius="5dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">

   ......

    </cn.enjoytoday.shadow.ShadowLayout>
```




包含属性如下:

 |   属性名      | 类型    |  说明  |
 | --------   | -----:   | :----: |
 |   shadowColor      | color      |   阴影渲染颜色   |
 | shadowRadius        | dimension      |   背景圆角半径(0为矩形)    |
 | blurRadius        | dimension      |   模糊半径    |
 | xOffset        | dimension      |   水平位移  |
 | yOffset        | dimension      |   竖直位移  |
 | bgColor        | color      |       |






