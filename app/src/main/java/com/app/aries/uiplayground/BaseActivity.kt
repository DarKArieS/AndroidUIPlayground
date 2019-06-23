package com.app.aries.uiplayground

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

//===================================================================================
    //點一下EditTextView外面收起鍵盤
    //ver2.0: 拖曳事件就不收
    //ver3.0: 新增點了不收的View白名單，記得在onCreateView時加入以及onDestroyView時移除

    private var isLongPressListening = false
    private var hasPressed = false
    var noHideSoftInputViewList = mutableListOf<View>()

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Timber.tag("BaseActivity").d(
            "action: ${ev?.action} , ${ev?.actionMasked}"
        )

        Timber.tag("BaseActivity").d(
            "position: ${ev?.pointerCount} - ${ev?.x}, ${ev?.y}"
        )

        val v : View? = this.currentFocus
        if (ev?.action == MotionEvent.ACTION_DOWN){
            if ( !isTouchNoHideSoftInputView(ev.x,ev.y) && this.isShouldHideInput(v, ev))
                isLongPressListening = true
        }
        if (ev?.action == MotionEvent.ACTION_MOVE && isLongPressListening)
            hasPressed = true

        if (ev?.action == MotionEvent.ACTION_UP && isLongPressListening){
            if(!hasPressed){
                hideSoftInput(v!!.windowToken)
                //v.clearFocus()
            }else hasPressed = false
            isLongPressListening = false
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideInput(v : View?, event: MotionEvent): Boolean{
        if (v!= null && (v is EditText) )
            return !(isViewTouched(v,event.x,event.y))
        return false
    }

    private fun hideSoftInput(token: IBinder?) {
        if (token != null) {
            val im: InputMethodManager =  getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun isTouchNoHideSoftInputView(x:Float, y:Float): Boolean{
        for (view in noHideSoftInputViewList){
            if(view.isAttachedToWindow)
                if (isViewTouched(view,x,y))
                    return true
        }
        return false
    }

    private fun isViewTouched(v: View, x:Float, y:Float):Boolean{
        val l : IntArray  = intArrayOf( 0, 0 )
        v.getLocationInWindow(l)
        val left = l[0]
        val top = l[1]
        val bottom = top + v.height
        val right = left + v.width
        return (x > left && x < right && y > top && y < bottom)
    }
    //===================================================================================
}