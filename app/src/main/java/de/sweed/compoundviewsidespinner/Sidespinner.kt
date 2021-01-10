package de.sweed.compoundviewsidespinner


import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

const val NOT_INITIALIZED = -1

class Sidespinner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var mPreviousButton: Button
    private lateinit var mNextButton: Button
    private lateinit var mCurrentItem: TextView

    private var mSpinnerValues: Array<CharSequence>? = null
    private var _mSelectedIndex = NOT_INITIALIZED
    val mSelectedIndex: Int
        get() = _mSelectedIndex


    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.sidespinner_view, this)
        val typedArray: TypedArray = context
            .obtainStyledAttributes(attrs, R.styleable.Sidespinner);

        mSpinnerValues = typedArray
            .getTextArray(R.styleable.Sidespinner_values)
        _mSelectedIndex = typedArray.getInteger(R.styleable.Sidespinner_index, NOT_INITIALIZED)


        Log.i("Sidespinner init", _mSelectedIndex.toString())

        typedArray.recycle();

//        val inflater = context
//            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        inflater.inflate(R.layout.side, this)
    }

    fun setValues(values: Array<CharSequence>) {
        mSpinnerValues = values

        // Select the first item of the string array by default since
        // the list of value has changed.

        if (_mSelectedIndex == NOT_INITIALIZED){
            setSelectedIndex(0)
        } else {
            setSelectedIndex(_mSelectedIndex)
        }

    }

    fun setSelectedIndex(index: Int) {
        // If no values are set for the spinner, do nothing.
        if (mSpinnerValues.isNullOrEmpty())
            return;

        // If the index value is invalid, do nothing.
        if (index < 0 || index >= mSpinnerValues?.size ?: NOT_INITIALIZED)
            return;
        // Set the current index and display the value.
        _mSelectedIndex = index
        mCurrentItem.text = mSpinnerValues!![index]

//        if first item is show hide previous, if size -1 *last, then hide next
        if (index == 0) {
            mPreviousButton.visibility = View.GONE
        } else {
            mPreviousButton.visibility = View.VISIBLE
        }
        if (index == mSpinnerValues!!.size - 1) {
            mNextButton.visibility = View.GONE
        } else {
            mNextButton.visibility = View.VISIBLE
        }
    }

    /**
     * Gets the selected value of the spinner, or null if no valid
     * selected index is set yet.
     *
     * @return the selected value of the spinner.
     */
    fun getSelectedValue(): CharSequence? {
        // If no values are set for the spinner, return an empty string.
        if (mSpinnerValues.isNullOrEmpty()) return ""

        // If the current index is invalid, return an empty string.
        return if (_mSelectedIndex < 0 || _mSelectedIndex >= mSpinnerValues!!.size) ""
        else mSpinnerValues!![_mSelectedIndex]
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mCurrentItem = findViewById<View>(R.id.sidespinner_view_current_item) as TextView
        mPreviousButton = findViewById(R.id.sidespinner_view_previous)
        mPreviousButton.setBackgroundResource(android.R.drawable.ic_media_previous)
        mNextButton = findViewById(R.id.sidespinner_view_next)
        mNextButton.setBackgroundResource(android.R.drawable.ic_media_next)

        mPreviousButton.setOnClickListener {
            if (mSpinnerValues != null) {
                _mSelectedIndex = mSelectedIndex.minus(1)
                setSelectedIndex(mSelectedIndex)
            }
        }
        mNextButton.setOnClickListener {
            if (mSpinnerValues != null) {
                _mSelectedIndex = mSelectedIndex.plus(1)
                setSelectedIndex(mSelectedIndex)
            }
        }

        Log.i("Sidespinner onFinInf", _mSelectedIndex.toString())
//        first item as default
        setSelectedIndex(_mSelectedIndex)

    }

}
