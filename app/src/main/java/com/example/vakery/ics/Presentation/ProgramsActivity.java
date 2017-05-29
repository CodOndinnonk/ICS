package com.example.vakery.ics.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.vakery.ics.EntropyCalculator;
import com.example.vakery.ics.Application.Functional.MyToolbar;
import com.example.vakery.ics.Application.Functional.Vars;
import com.example.vakery.ics.R;

public class ProgramsActivity extends MyToolbar {
    final String myLog = "myLog";
    //название активити отображаемое в Toolbar
    String activityTitle = Vars.getContext().getString(R.string.drawer_item_programs);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);

        //создание Toolbar
        this.createToolbar(activityTitle);

        TextView entropyBtn = (TextView)findViewById(R.id.activityProgramsEntropyBtn);
        entropyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vars.getContext(), EntropyCalculator.class);
                startActivity(intent);
            }
        });

    }
}
