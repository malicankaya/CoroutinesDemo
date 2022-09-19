package com.malicankaya.coroutinesdemo

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    var customDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btnExecution: Button = findViewById(R.id.btnExecution)

        btnExecution.setOnClickListener {
            lifecycleScope.launch {
                showProgressDialog()
                execute("Task executed successfully")
            }
        }
    }

    private suspend fun execute(msg: String){
        withContext(Dispatchers.IO){
            for (i in 1..10000) {
                Log.e("delay:", "" + i)
            }
        }
        runOnUiThread {
            cancelProgressDialog()
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressDialog(){
        customDialog = Dialog(this@MainActivity)

        customDialog?.setContentView(R.layout.dialog_custom_progress)

        customDialog?.setCancelable(false)

        customDialog?.show()
    }

    private fun cancelProgressDialog(){
        if(customDialog != null){
            customDialog?.dismiss()
            customDialog = null
        }
    }
}