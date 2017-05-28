package com.example.vakery.ics;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vakery.ics.Application.Functional.MyToolbar;

import java.util.ArrayList;

public class EntropyCalculator extends MyToolbar {
    final String myLog = "myLog";
    public double answer = 0;
    //для подсчета итогового результата (суммы)
    double totalAnswer = 0;
    // порядковый номер "а"
    int ai = 1;
    //список введенных вероятностей
    ArrayList chancesList = new ArrayList();
    //список высчитанных значений для каждой вероятности
    ArrayList answersList = new ArrayList();
    EditText aiEnterField;
    TextView answerField, aiShow;
    Button clearBtn, calculateBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entropy_calculator);

        //создание Toolbar
        this.createToolbar(getString(R.string.entropy_calculator));

        aiEnterField = (EditText)findViewById(R.id.activityEntropyCalculatorAiEnterField);
        answerField = (TextView)findViewById(R.id.activityEntropyCalculatorAnswerField);
        aiShow = (TextView)findViewById(R.id.activityEntropyCalculatorAiShowLable);
        aiShow.setText("a" + ai + "=");
        clearBtn = (Button)findViewById(R.id.activityEntropyCalculatorClearBtn);
        clearBtn.setOnClickListener(clearOnClickListener);
        calculateBtn = (Button)findViewById(R.id.activityEntropyCalculatorCalculateBtn);
        calculateBtn.setOnClickListener(calculateOnClickListener);
    }


    View.OnClickListener clearOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ai = 1;
            aiEnterField.setText("");
            answerField.setText("");
            aiShow.setText("a" + ai + "=");
            answer = 0;
            chancesList.clear();
            answersList.clear();
        }
    };


    View.OnClickListener calculateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String enteredAi = aiEnterField.getText().toString();
            aiEnterField.setText("");

            if (enteredAi.length() > 0) {// если ввели вероятность
                String answerStr = "";
                //очищаем ответ так как он высчитывается заново, с нововведенным значением
                totalAnswer = 0;
                //очищаем список ответов, в цикле он знавого высчитается
                answersList.clear();

                //берем введеное знчение и добавляем в список
                double chance = Double.parseDouble(enteredAi);
                chancesList.add(chance);

                //для кахдого введенного значения высчитываем ответ и записывем в список
                for (int i = 0; i < chancesList.size(); i++) {
                    //обнуляем так как ответы с разными парамтрами не маеют связи между собой
                    answer = 0;

                    answer = Double.parseDouble(chancesList.get(i).toString()) * (Math.abs(Math.log10(Double.parseDouble(chancesList.get(i).toString()))) * 3.32);
                    answer = answer * 1000;
                    answer = Math.round(answer);
                    answer = answer / 1000;
                    answersList.add(answer);

                    answerStr = answerStr + "H" + (i+1) + "= " + chancesList.get(i) + " * log " + chancesList.get(i) +
                     " = " + answer + "\n";
                }

                //считаем сумму всех ответов
                for (int i = 0; i < answersList.size() ; i++) {
                    totalAnswer += Double.parseDouble(answersList.get(i).toString());
                }

                answerStr = answerStr + "\n" + "HΣ = " + totalAnswer;
                answerField.setText(answerStr);

                //инкриментируем порядковый номер Ai чтоб отображать на экране
                ai++;
                aiShow.setText("a" + ai + "=");
            }
        }
    };


}
