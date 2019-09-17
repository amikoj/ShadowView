# ShadowView 的使用

[![](https://jitpack.io/v/amikoj/ShadowView.svg)](https://jitpack.io/#amikoj/ShadowView)
### 使用说明
Android 控件阴影使用，采用类似于css的Box Shadow 效果的阴影效果和设置方式,使用如下:

<img src="./recorder.gif." width = 30% height = 30% />


##### 添加依赖

```gradle
repositories {
	//...
	maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.amikoj:ShadowView:1.0.1'
}
```



#### 使用

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

   <!--嵌套需要添加阴影的布局 -->

    </cn.enjoytoday.shadow.ShadowLayout>
```




#### 代码设置

```java
shadowLayout.getShadowConfig()   //获取配置类
            . setBlurRadius(blurRadius)  //设置模糊半径
             .setXOffset(xoffset)   //设置水平位移，最大为20dp
             .setYOffset(yoffset)   //设置竖直位移，最大为20dp
             .setShadowRadius(shadowRadius) //设置圆角半径，为0时不是圆角
             .setShadowColor(shadowColor)    //设置阴影颜色
             .commit();             //生效修改

```




包含属性如下:

 |   属性名      | 类型    |  说明  |
 | --------   | -----:   | :----: |
 |   shadowColor      | color      |   阴影渲染颜色   |
 | shadowRadius        | dimension      |   背景圆角半径(0为矩形)    |
 | blurRadius        | dimension      |   模糊半径    |
 | xOffset        | dimension      |   水平位移  |
 | yOffset        | dimension      |   竖直位移  |
 | bgColor        | color      |     背景色  |






