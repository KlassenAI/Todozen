package com.android.todozen.core

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.android.todozen.R
import com.android.todozen.core.domain.ListItem

class CustomRecyclerView(
    context: Context,
    attrs: AttributeSet?,
    dfa: Int,
    dfr: Int
) : FrameLayout(context, attrs, dfa, dfr) {

    var title: String = ""
        set(value) {
            field = value
            tvTitle.text = title
        }

    var isHide: Boolean = false
        set(value) {
            field = value
            btnHide.isSelected = isHide
            recycler.isVisible = isHide.not()
        }
    var adapter: BaseAdapterDelegate? = null
        set(value) {
            field = value
            value?.let { recycler.initVertical(value) }
        }
    var items: List<ListItem> = emptyList()
        set(value) {
            field = value
            adapter?.items = items
            tvCounter.isVisible = items.isNotEmpty()
            tvCounter.text = items.size.toString()
        }

    lateinit var llContainer: LinearLayout
    lateinit var tvTitle: TextView
    lateinit var tvCounter: TextView
    lateinit var btnHide: ImageButton
    lateinit var recycler: RecyclerView

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, dsa: Int) : this(context, attrs, dsa, 0)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.layout_recycler, this, true)
        initViews()
        initAttrs(attrs, dfa, dfr)
//        initListeners()
    }

    private fun initViews() {
        llContainer = findViewById(R.id.llContainer)
        tvTitle = findViewById(R.id.tvTitle)
        tvCounter = findViewById(R.id.tvCounter)
        btnHide = findViewById(R.id.btnHide)
        recycler = findViewById(R.id.recycler)
    }

    private fun initAttrs(attrs: AttributeSet?, dfa: Int, dfr: Int) {
        if (attrs == null) return
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomRecyclerView, dfa, dfr)
        initParams(typedArray)
        typedArray.recycle()
    }

    private fun initParams(typedArray: TypedArray) = with(typedArray) {
        title = getString(R.styleable.CustomRecyclerView_title) ?: ""
        isHide = getBoolean(R.styleable.CustomRecyclerView_isHide, false)
    }

//    private fun initListeners() {
//        btnPrimary.setOnClickListener(primaryBtnParams?.listener)
//        btnSecondary.setOnClickListener(secondaryBtnParams?.listener)
//        btnClose.setOnClickListener { isVisible = false }
//    }
//
//    fun show(
//        message: String, type: AlertType = INFO, closable: Boolean = false,
//        primaryBtnParams: AlertButtonParams? = null, secondaryBtnParams: AlertButtonParams? = null,
//    ) {
//        this.message = message
//        this.closable = closable
//        this.type = type
//        this.primaryBtnParams = primaryBtnParams
//        this.secondaryBtnParams = secondaryBtnParams
//        isVisible = true
//    }
//
//    fun hide() {
//        isVisible = false
//    }
}