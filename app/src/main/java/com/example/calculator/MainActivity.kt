package com.example.calculator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.resultText.visibility= View.GONE
        binding.reset.visibility= View.GONE
        val intent = Intent(this, CalculateActivity::class.java)
        val getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    modifyActivityToDisplayResult()
                    binding.resultText.text = "Your result is ${result.data?.getStringExtra("Result").toString()} for inputs ${result.data?.getStringExtra("Operand_1")} and ${result.data?.getStringExtra("Operand_2")} with action ${result.data?.getStringExtra("Operation")} "
                }
            }
        binding.add.setOnClickListener { intent.putExtra("operation","add")
                                       getResult.launch(intent)
        }
        binding.subtract.setOnClickListener { intent.putExtra("operation","subtract")
            getResult.launch(intent)}
        binding.multiply.setOnClickListener { intent.putExtra("operation","multiply")
            getResult.launch(intent)}
        binding.divide.setOnClickListener { intent.putExtra("operation","divide")
            getResult.launch(intent)}
        binding.reset.setOnClickListener {
            binding.add.visibility = View.VISIBLE
            binding.subtract.visibility = View.VISIBLE
            binding.multiply.visibility = View.VISIBLE
            binding.divide.visibility = View.VISIBLE
            binding.reset.visibility=View.GONE
            binding.resultText.text=""
            binding.resultText.visibility= View.GONE

        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
       if(savedInstanceState.getInt("isResultPage")==1){
           modifyActivityToDisplayResult()
           binding.resultText.text = savedInstanceState.getString("result")

       }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(binding.resultText.visibility == View.VISIBLE) {
            outState.putInt("isResultPage", 1)
            outState.putString("result", binding.resultText.text.toString())
        }
    }
    private fun modifyActivityToDisplayResult(){
        binding.add.visibility = View.GONE
        binding.subtract.visibility = View.GONE
        binding.multiply.visibility = View.GONE
        binding.divide.visibility = View.GONE
        binding.reset.visibility= View.VISIBLE
        binding.resultText.visibility= View.VISIBLE
    }

}
