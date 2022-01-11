package com.example.calculator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.calculator.databinding.ActivityCalculateBinding
import java.lang.NumberFormatException
import kotlin.math.roundToInt

class CalculateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCalculateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculate)
        val button = binding.button
        when (val operation = intent.getStringExtra("operation")) {
            "add" -> {
                button.text = getString(R.string.add)
                button.setOnClickListener {
                    val operand1 = getOperand1()
                    val operand2 = getOperand2()
                    if(operand1 != "" && operand2 != "") {
                        val result = operand1.toFloat() + operand2.toFloat()
                        returnResult(result,operand1,operand2,operation)
                    }
                }
            }
                "subtract" -> {
                    button.text = getString(R.string.subtract)
                    button.setOnClickListener {
                        val operand1 = getOperand1()
                        val operand2 = getOperand2()
                        if (operand1 != "" && operand2 != "") {
                            val result = operand1.toFloat() - operand2.toFloat()
                            returnResult(result,operand1,operand2,operation)
                        }
                    }
                }
            "multiply" -> {
                button.text = getString(R.string.multiply)
                button.setOnClickListener {
                    val operand1 = getOperand1()
                    val operand2 = getOperand2()
                    if (operand1 != "" && operand2 != "") {
                        val result = operand1.toFloat() * operand2.toFloat()
                        returnResult(result,operand1,operand2,operation)
                    }
                }
            }
            "divide" -> {
                button.text = getString(R.string.divide)
                button.setOnClickListener {
                    val operand1 = getOperand1()
                    val operand2 = getOperand2()
                    if (operand1 != "" && operand2 != "" ) {
                        if(operand2.toInt() !=0) {
                            val result = operand1.toFloat() / operand2.toFloat()
                            returnResult(result,operand1,operand2,operation)
                        }
                        else {
                            Toast.makeText(applicationContext,"A number cannot be divided by 0",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
   private fun returnResult(result : Float, operand1 : String, operand2 : String, operation : String){
        if(result - result.toInt() > 0)
            intent.putExtra("Result", result.toString())
        else
            intent.putExtra("Result", result.roundToInt().toString())
        intent.putExtra("Operand_1", operand1)
        intent.putExtra("Operand_2", operand2)
        intent.putExtra("Operation", operation)
        setResult(RESULT_OK, intent)
        finish()
    }
    private fun getOperand1() : String {
        try {
            val operand1 = binding.operand1.text.toString().toFloat()
            return if(operand1 - operand1.toInt() > 0 )
                operand1.toString()
            else
                operand1.roundToInt().toString()

        } catch (e: NumberFormatException) {
            Toast.makeText(
                applicationContext,
                "please enter a valid Number",
                Toast.LENGTH_SHORT
            ).show()
        }
       return ""
    }
    private fun getOperand2(): String {
        try {
                val operand2 = binding.operand2.text.toString().toFloat()
                return if(operand2 - operand2.toInt() > 0 )
                    operand2.toString()
                else
                    operand2.roundToInt().toString()

            } catch (e: NumberFormatException) {
                Toast.makeText(
                    applicationContext,
                    "please enter a valid Number",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return ""
        }
}