package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String calculation ="";
    private TextView textView;
    private String currentOperator = "";
    private double result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        textView = findViewById(R.id.textView);

    }
    public void result(View view){

        double result = eval(calculation);
        calculation = calculation + " = " + result;
        textView.setText(calculation);

        calculation = "";
    }
    public void add(View view){

        calculation = calculation + "+";
        setCurrentOperator("+");
        textView.setText(calculation);
    }
    public void subtract(View view){

        calculation = calculation + "-";
        setCurrentOperator("-");
        textView.setText(calculation);
    }
    public void multiply(View view){

        calculation = calculation + "*";
        setCurrentOperator("*");
        textView.setText(calculation);
    }

    public void division(View view){

        calculation = calculation + "/";
        setCurrentOperator("/");
        textView.setText(calculation);
    }
    public void zero(View view){

        calculation = calculation + "0";



        textView.setText(calculation);
    }
    public void one(View view){

        calculation = calculation + "1";

        textView.setText(calculation);
    }
    public void two(View view){

        calculation = calculation + "2";

        textView.setText(calculation);
    }
    public void three(View view){

        calculation = calculation + "3";

        textView.setText(calculation);
    }
    public void four(View view){

        calculation = calculation + "4";

        textView.setText(calculation);
    }
    public void five(View view){

        calculation = calculation + "5";

        textView.setText(calculation);
    }
    public void six(View view){

        calculation = calculation + "6";

        textView.setText(calculation);
    }
    public void seven(View view){

        calculation = calculation + "7";

        textView.setText(calculation);
    }
    public void eight(View view){

        calculation = calculation + "8";

        textView.setText(calculation);
    }
    public void nine(View view){

        calculation = calculation + "9";

        textView.setText(calculation);
    }


    public String getCurrentOperator() {
        return currentOperator;
    }

    public void setCurrentOperator(String currentOperator) {
        this.currentOperator = currentOperator;
    }
    // taken from stack overflow with slight modification
    public double eval(String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}