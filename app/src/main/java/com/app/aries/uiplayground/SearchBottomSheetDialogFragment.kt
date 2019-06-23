package com.app.aries.uiplayground

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.widget.EditText

import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_searchbottomsheet_dialog.view.*
import timber.log.Timber

import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    TestBottomSheetListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 *
 * You activity (or fragment) needs to implement [SearchBottomSheetListDialogFragment.Listener].
 */
//class SearchBottomSheetListDialogFragment : BottomSheetDialogFragment() {
class SearchBottomSheetListDialogFragment : AppCompatDialogFragment() {
    private var mListener: Listener? = null
    private lateinit var rootView : View
    private var searchText = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_searchbottomsheet_dialog, container, false)
        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        if (parent != null) {
            mListener = parent as Listener
        } else {
            mListener = context as Listener
        }
    }

    override fun onStart() {
        super.onStart()
        val bottomSheetBehavior = (dialog as BottomSheetDialog).behavior
//        val layouteP = LayoutParams(0,500)
//        bottomSheetBehavior.onAttachedToLayoutParams(layouteP)
        //bottomSheetBehavior.
//        .peekHeight = convertDpToPixel(500f,this.context!!)

//        rootView.webView.loadUrl("https://www.google.com.tw/search?q=$searchText")
        rootView.webView.loadUrl("https://pjreddie.com/darknet/")
        //rootView.webView.isNestedScrollingEnabled = true

//        if(android.os.Build.VERSION.SDK_INT>22){
//            rootView.webView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//                Timber.tag("bottomsheet").d("$oldScrollX $oldScrollY -> $scrollX $scrollY" )
//
//            }
//        }


//        rootView.webView.loadUrl("https://www.google.com.tw/")

        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.skipCollapsed=true
        bottomSheetBehavior.peekHeight=0
        bottomSheetBehavior.state = STATE_EXPANDED

        bottomSheetBehavior.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

                if (newState == BottomSheetBehavior.STATE_DRAGGING && rootView.webView.scrollY > 0) {
                    bottomSheetBehavior.state = STATE_EXPANDED
                }
            }
        })
    }

    fun convertDpToPixel(dp: Float, context: Context): Int {
        return (dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

//    fun convertPixelsToDp(px: Float, context: Context): Float {
//        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
//    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }

    interface Listener {
        fun onTestBottomSheetClicked(position: Int)
    }

    companion object {
        fun newInstance(): SearchBottomSheetListDialogFragment =
            SearchBottomSheetListDialogFragment()
    }

    //===================================================================================
    //點一下EditTextView外面收起鍵盤
    //ver2.0: 拖曳事件就不收
    //ver3.0: 新增點了不收的View白名單，記得在onCreateView時加入以及onDestroyView時移除
    //ver Frag: 作用於 Fragment上

    //TODO haven't down!
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : BottomSheetDialog(context!!, theme){
            override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
                val v : View? = this.currentFocus
//                Timber.tag("bottomsheet").d("dispatchTouchEvent")
//                Timber.tag("bottomsheet").d(v.toString())
                if (ev?.action == MotionEvent.ACTION_DOWN){
                    if (isShouldHideInput(v, ev))
                        isLongPressListening = true
                }
                if (ev?.action == MotionEvent.ACTION_MOVE && isLongPressListening)
                    hasPressed = true

                if (ev?.action == MotionEvent.ACTION_UP && isLongPressListening){
                    if(!hasPressed){
                        hideSoftInput(v!!.windowToken)
                        v.clearFocus()
                    }else hasPressed = false
                    isLongPressListening = false
                }

                return super.dispatchTouchEvent(ev)
            }
        }

    }

    private var isLongPressListening = false
    private var hasPressed = false
    var noHideSoftInputViewList = mutableListOf<View>()

    private fun isShouldHideInput(v : View?, event: MotionEvent): Boolean{
        if (v!= null && (v is EditText) )
            return !(isViewTouched(v,event.x,event.y))
        return false
    }

    private fun hideSoftInput(token: IBinder?) {
        if (token != null) {
            val im: InputMethodManager =  activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
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

}
