package com.example.calculator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.calculator.databinding.ActivityMainBinding

const val OPERATION = "operation"
const val RESULT = "result"
const val OPERAND_1 = "operand_1"
const val OPERAND_2 = "operand_2"
const val IS_RESULT_PAGE = "isResultPage"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, CalculateActivity::class.java)
        val getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    modifyActivityToDisplayResult()
                    binding.resultText.text = getString(R.string.text_result,result.data?.getStringExtra(RESULT),result.data?.getStringExtra(OPERAND_1),result.data?.getStringExtra(OPERAND_2),result.data?.getStringExtra(OPERATION))
                }
            }
        binding.add.setOnClickListener { launchActivity(Operation.ADD,getResult,intent) }
        binding.subtract.setOnClickListener { launchActivity(Operation.SUBTRACT,getResult,intent) }
        binding.multiply.setOnClickListener { launchActivity(Operation.MULTIPLY,getResult,intent) }
        binding.divide.setOnClickListener { launchActivity(Operation.DIVIDE,getResult,intent) }
        binding.reset.setOnClickListener {
            binding.operationButtons.visibility = View.VISIBLE
            binding.resultViews.visibility=View.GONE
            binding.resultText.text=""
        }
    }
    private fun launchActivity(operation: Operation, getResult : ActivityResultLauncher<Intent>, intent : Intent ){
        intent.putExtra(OPERATION,operation)
        getResult.launch(intent)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState.getInt(IS_RESULT_PAGE)==1){
            modifyActivityToDisplayResult()
           binding.resultText.text = savedInstanceState.getString(RESULT)

       }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(binding.resultViews.visibility == View.VISIBLE) {
            outState.putInt(IS_RESULT_PAGE, 1)
            outState.putString(RESULT, binding.resultText.text.toString())
        }
    }
    private fun modifyActivityToDisplayResult(){
        binding.operationButtons.visibility = View.GONE
        binding.resultViews.visibility= View.VISIBLE
    }
}
