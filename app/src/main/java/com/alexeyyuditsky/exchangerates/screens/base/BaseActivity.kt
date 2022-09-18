package com.alexeyyuditsky.exchangerates.screens.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity() {

    abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.showErrorMessage.collectLatest {
                Toast.makeText(this@BaseActivity, it, Toast.LENGTH_LONG).show()
            }
        }
    }

}