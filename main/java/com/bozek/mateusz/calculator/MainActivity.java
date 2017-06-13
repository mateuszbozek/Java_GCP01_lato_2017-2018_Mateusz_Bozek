package com.bozek.mateusz.calculator;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bozek.mateusz.calculator.R;
import com.bozek.mateusz.calculator.databinding.ActivityMainBinding;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final char ADD = '+';
    private static final char SUB = '-';
    private static final char MULTI = '*';
    private static final char DIV = '/';

    private char CURRENT_ACTION;

    private ActivityMainBinding bind;
    private double valueOne = Double.NaN;
    private double valueTwo;

    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        decimalFormat = new DecimalFormat("#.##########");

        bind = DataBindingUtil.setContentView(this, R.layout.activity_main);

        bind.buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.editText.setText(bind.editText.getText() + ".");
            }
        });

        bind.buttonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.editText.setText(bind.editText.getText() + "0");
            }
        });

        bind.buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.editText.setText(bind.editText.getText() + "1");
            }
        });

        bind.buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.editText.setText(bind.editText.getText() + "2");
            }
        });

        bind.buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.editText.setText(bind.editText.getText() + "3");
            }
        });

        bind.buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.editText.setText(bind.editText.getText() + "4");
            }
        });

        bind.buttonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.editText.setText(bind.editText.getText() + "5");
            }
        });

        bind.buttonSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.editText.setText(bind.editText.getText() + "6");
            }
        });

        bind.buttonSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.editText.setText(bind.editText.getText() + "7");
            }
        });

        bind.buttonEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.editText.setText(bind.editText.getText() + "8");
            }
        });

        bind.buttonNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.editText.setText(bind.editText.getText() + "9");
            }
        });

        bind.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computeCalculation();
                CURRENT_ACTION = ADD;
                bind.infoTextView.setText(decimalFormat.format(valueOne) + "+");
                bind.editText.setText(null);
            }
        });

        bind.buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computeCalculation();
                CURRENT_ACTION = SUB;
                bind.infoTextView.setText(decimalFormat.format(valueOne) + "-");
                bind.editText.setText(null);
            }
        });

        bind.buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computeCalculation();
                CURRENT_ACTION = MULTI;
                bind.infoTextView.setText(decimalFormat.format(valueOne) + "*");
                bind.editText.setText(null);
            }
        });

        bind.buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computeCalculation();
                CURRENT_ACTION = DIV;
                bind.infoTextView.setText(decimalFormat.format(valueOne) + "/");
                bind.editText.setText(null);
            }
        });

        bind.buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computeCalculation();
                bind.infoTextView.setText(bind.infoTextView.getText().toString() +
                        decimalFormat.format(valueTwo) + " = " + decimalFormat.format(valueOne));
                valueOne = Double.NaN;
                CURRENT_ACTION = '0';
            }
        });

        bind.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bind.editText.getText().length() > 0) {
                    CharSequence currentText = bind.editText.getText();
                    bind.editText.setText(currentText.subSequence(0, currentText.length()-1));
                }
                else {
                    valueOne = Double.NaN;
                    valueTwo = Double.NaN;
                    bind.editText.setText("");
                    bind.infoTextView.setText("");
                }
            }
        });
    }

    private void computeCalculation() {
        if(!Double.isNaN(valueOne)) {
            valueTwo = Double.parseDouble(bind.editText.getText().toString());
            bind.editText.setText(null);

            if(CURRENT_ACTION == ADD)
                valueOne = this.valueOne + valueTwo;
            else if(CURRENT_ACTION == SUB)
                valueOne = this.valueOne - valueTwo;
            else if(CURRENT_ACTION == MULTI)
                valueOne = this.valueOne * valueTwo;
            else if(CURRENT_ACTION == DIV)
                valueOne = this.valueOne / valueTwo;
        }
        else {
            try {
                valueOne = Double.parseDouble(bind.editText.getText().toString());
            }
            catch (Exception e){}
        }
    }
}
