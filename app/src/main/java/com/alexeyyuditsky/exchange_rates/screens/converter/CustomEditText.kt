package com.alexeyyuditsky.exchange_rates.screens.converter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.accessibility.AccessibilityEvent
import androidx.appcompat.widget.AppCompatEditText
import com.alexeyyuditsky.exchange_rates.adapters.ConverterAdapter
import com.alexeyyuditsky.exchange_rates.adapters.cursorPosition
import com.alexeyyuditsky.exchange_rates.model.currencies.ConverterCurrency

class CustomEditText(
    context: Context,
    attributeSet: AttributeSet? = null
) : AppCompatEditText(context, attributeSet) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val line = s.toString()
                if (line.startsWith(".")) {
                    setText("0.")
                    setSelection(text?.length!!)
                } else if (line.length == 1 && line.startsWith("0")) {
                    setText("")
                }
            }
        })

        accessibilityDelegate = object : View.AccessibilityDelegate() {
            override fun sendAccessibilityEvent(host: View?, eventType: Int) {
                super.sendAccessibilityEvent(host, eventType)
                if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) setSelection(text?.length!!)
            }
        }

        setOnKeyListener { _, _, _ ->
            setSelection(text?.length!!)
            false
        }

        customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?) = false
            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = false
            override fun onDestroyActionMode(mode: ActionMode?) {}
        }
    }

    fun addTextChangedListener2(
        editText: CustomEditText,
        position: Int,
        currencies: List<ConverterCurrency>,
        code: String,
        adapter: ConverterAdapter
    ) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editText.isFocused) {
                    cursorPosition = position
                    if (s.toString().isBlank()) return
                    currencies.forEach {
                        if (code == it.code) {
                            it.valueShow = s.toString()
                        } else {
                            it.valueShow = (s.toString().toFloat() / it.valueToday.toFloat()).toString()
                        }
                    }
                    adapter.notifyItemRangeChanged(0, adapter.itemCount, arrayOf(0))
                }
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setOnTouchListener(
        editText: CustomEditText,
        position: Int,
        currencies: List<ConverterCurrency>,
        adapter: ConverterAdapter
    ) {
        editText.setOnTouchListener { v, event ->
            if (v is CustomEditText && !v.isFocused && v.text!!.isNotEmpty() && event.action == MotionEvent.ACTION_UP) {
                editText.setText("")
                cursorPosition = position
                currencies.forEach {
                    it.valueShow = ""
                }
                adapter.notifyItemRangeChanged(0, adapter.itemCount, arrayOf(0))
            }
            false
        }
    }
}