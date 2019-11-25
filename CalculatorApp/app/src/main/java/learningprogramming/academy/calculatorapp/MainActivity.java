package learningprogramming.academy.calculatorapp;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNum;
    private TextView displayOperation;

    //Variables to hold the operands & type of calculation.
    private Double operand1 = null;
    private String pendingOperation = "=";

    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        newNum = findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);

        Button button0 = findViewById(R.id.button);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);


        //Operands Button
        Button buttonDot = findViewById(R.id.buttonDot);
        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonPlus = findViewById(R.id.buttonPlus);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reads the caption of the button using the get text method, then appends it to any text in the new number text widget
                Button btn = (Button) v; //Casting the reference to a widget that does have the 'getText' method as reference doesn't.
                newNum.append(btn.getText().toString()); //When a button's tapped, the reference that's tapped is passed as an argument.

            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener operationsListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                String op = btn.getText().toString();
                String value = newNum.getText().toString();
                try{
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                }catch(NumberFormatException e){
                    newNum.setText("");
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };

        buttonEquals.setOnClickListener(operationsListner);
        buttonDivide.setOnClickListener(operationsListner);
        buttonMinus.setOnClickListener(operationsListner);
        buttonMultiply.setOnClickListener(operationsListner);
        buttonPlus.setOnClickListener(operationsListner);

        Button buttonNegative = findViewById(R.id.buttonClear);
        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newNum.getText().toString();
                if(value.length()==0)
                    newNum.setText("");
                else{
                    try{
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNum.setText(doubleValue.toString());
                    }catch (NumberFormatException e){
                        newNum.setText(""); //New number field was either a minus sign or a '.', so just clear it.
                    }
                }
            }
        });
        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentValue = newNum.getText().toString();
                Editable resultValue = result.getEditableText();
                if(currentValue.length() >= 0 && resultValue.length() > 0){
                   newNum.setText("");
                   //resultValue.delete(resultValue.length()-1, resultValue.length());
                   result.getText().clear();
                   displayOperation.setText("=");
                }
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if(operand1!=null)
            outState.putDouble(STATE_OPERAND1, operand1);
        super.onSaveInstanceState(outState);
    }

    private void performOperation(Double value, String operation){
        Double operand2;
        if(operand1 == null)
            operand1 = value;
        else {
            operand2 = value;
            if (pendingOperation.equals("="))
                pendingOperation = operation;
            switch (pendingOperation) {
                case "=":
                    operand1 = operand2;
                    break;
                case "/":
                    if (operand2 == 0)
                        operand1 = 0.0;
                    else
                        operand1 /= operand2;
                    break;
                case "*":
                    operand1 *= operand2;
                    break;
                case "-":
                    operand1 -= operand2;
                    break;
                case "+":
                    operand1 += operand2;
                    break;
            }
        }
        result.setText(operand1.toString());
        newNum.setText("");
    }





}
