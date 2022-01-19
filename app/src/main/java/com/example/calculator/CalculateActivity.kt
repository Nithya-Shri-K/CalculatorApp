package com.example.calculator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.calculator.databinding.ActivityCalculateBinding
import java.lang.NumberFormatException
import kotlin.math.roundToInt



class CalculateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val button = binding.button
        when (intent.getSerializableExtra(OPERATION)) {
            Operation.ADD -> {
                button.text = getString(R.string.button_add)
                button.setOnClickListener {
                    calculate(Operation.ADD)
                }
            }
            Operation.SUBTRACT -> {
                button.text = getString(R.string.button_subtract)
                button.setOnClickListener {
                    calculate(Operation.SUBTRACT)
                    }
                }
            Operation.MULTIPLY -> {
                button.text = getString(R.string.button_multiply)
                button.setOnClickListener {
                    calculate(Operation.MULTIPLY)
                    }
                }
            Operation.DIVIDE -> {
                button.text = getString(R.string.button_divide)
                button.setOnClickListener {
                    calculate(Operation.DIVIDE)
                        }
                    }
                }
            }

    private fun returnResult(result: Float, operand1: String, operand2: String, operation: String) {
        if (result - result.toInt() > 0)
            intent.putExtra(RESULT, result.toString())
            else
            intent.putExtra(RESULT, result.roundToInt().toString())
        intent.putExtra(OPERAND_1, operand1)
        intent.putExtra(OPERAND_2, operand2)
        intent.putExtra(OPERATION, operation)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun getOperand1(): String {
        try {
            val operand1 = binding.operand1.text.toString().toFloat()
            return if (operand1 - operand1.toInt() > 0)
                operand1.toString()
            else
                operand1.roundToInt().toString()

        } catch (e: NumberFormatException) {
            Toast.makeText(
                this,
                getString(R.string.toast_valid_input),
                Toast.LENGTH_SHORT
            ).show()
        }
        return ""
    }

    private fun getOperand2(): String {
        try {
            val operand2 = binding.operand2.text.toString().toFloat()
            return if (operand2 - operand2.toInt() > 0)
                operand2.toString()
            else
                operand2.roundToInt().toString()

        } catch (e: NumberFormatException) {
            Toast.makeText(
                this,
                getString(R.string.toast_valid_input),
                Toast.LENGTH_SHORT
            ).show()
        }
        return ""
    }

    private fun calculate(operation: Operation) {
        val operand1: String = getOperand1()
        val operand2: String = getOperand2()
        if (operand1.isNotEmpty() && operand2.isNotEmpty()) {
            if(operation == Operation.DIVIDE && operand2.toFloat() == 0.0f)
            {
                Toast.makeText(this,getString(R.string.toast_divide_error_message),Toast.LENGTH_SHORT).show()
            }
            else {
                val result = when (operation) {
                    Operation.ADD -> operand1.toFloat() + operand2.toFloat()
                    Operation.SUBTRACT -> operand1.toFloat() - operand2.toFloat()
                    Operation.MULTIPLY -> operand1.toFloat() * operand2.toFloat()
                    Operation.DIVIDE -> operand1.toFloat() / operand2.toFloat()
                }
                returnResult(result, operand1, operand2, operation.name)
            }

        }
    }
}