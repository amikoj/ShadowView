package cn.enjoytoday.shadowview

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.FocusFinder
import android.view.View
import android.widget.SeekBar
import cn.enjoytoday.shadow.Shadow
import cn.enjoytoday.shadow.ShadowLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

        when(seekBar?.id){
            R.id.blurRadiusSeekBar -> {
                //模糊半径改变
                blurRadius =  progress.toFloat()
                blurRadiusTv.text = "$progress dp"
            }

            R.id.xOffsetSeekBar -> {
                //水平位移
                val p = progress - 21
                xoffset = p.toFloat()
                xOffsetTv.text = "$p dp"
            }


            R.id.yOffsetSeekBar -> {
                //竖直位移
                val p = progress - 21
                yoffset = p.toFloat()
                yOffsetTv.text = "$p dp"
            }

        }

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }


    private var shadowConfig: Shadow?=null

    //水平偏移
    private var xoffset= 0f

    //竖直偏移
    private var yoffset = 0f

    //模糊半径
    private var blurRadius = 5f

    //是否是圆角，默认圆角为5dp
    private var shadowRadius = 0f

    //阴影色
    private var shadowColor = Color.parseColor("#bebebe")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shadowConfig = shadowLayout.shadowConfig
        initView()
    }



    private fun initView(){


        //是否为圆角
        isRoundRect.setOnCheckedChangeListener { buttonView, isChecked ->
            shadowRadius = if (isChecked){
                5f
            }else{
                0f
            }
        }


        //xOffset
        xOffsetSeekBar.setOnSeekBarChangeListener(this)
        yOffsetSeekBar.setOnSeekBarChangeListener(this)
        blurRadiusSeekBar.setOnSeekBarChangeListener(this)


        colorPicker.setOnColorPickerChangeListener(object :ColorPickerView.OnColorPickerChangeListener{
            override fun onColorChanged(picker: ColorPickerView?, color: Int) {
                shadowColor = color
                val str = "#"+Integer.toHexString(color)
                colorTv.text = str

            }

            override fun onStartTrackingTouch(picker: ColorPickerView?) {

            }

            override fun onStopTrackingTouch(picker: ColorPickerView?) {

            }

        })

    }



    /**
     * view onClick
     */
    fun onClick(view: View){
        when(view.id){
            R.id.submit ->{
                //更新阴影点击事件
                shadowConfig?.apply {
                     setBlurRadius(blurRadius)
                         .setXOffset(xoffset)
                         .setYOffset(yoffset)
                         .setShadowRadius(shadowRadius)
                         .setShadowColor(shadowColor)
                         .commit()

                }
            }


            R.id.goTo -> {
                //阴影设置方法
                startActivity(Intent(this,Main2Activity::class.java))

            }
        }

    }
















}
