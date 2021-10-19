package com.zobaer53.diseasesdetection;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
     EditText nameEditText,genderEditText,ageEditText;
    Spinner spin1,spin2,spin3,spin4;
    List<String> symptomList1 = Arrays.asList("Select Symptom","abdominal pain","abnormal_menstruation"
            );
    String symptom1;
    Button checkButton;
    int doubleBackToExitPressed = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nameEditText = findViewById(R.id.name);
        genderEditText = findViewById(R.id.gender);
        ageEditText = findViewById(R.id.age);
        checkButton = findViewById(R.id.checkButton);


        spin1 = findViewById(R.id.spinner1);
        spin2 = findViewById(R.id.spinner2);
        spin3 = findViewById(R.id.spinner3);
        spin4 = findViewById(R.id.spinner4);

        ArrayAdapter arrayAdapter = new ArrayAdapter( getApplicationContext(), android.R.layout.simple_spinner_item,symptomList1);

        arrayAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spin1.setAdapter(arrayAdapter);
        spin2.setAdapter(arrayAdapter);
        spin3.setAdapter(arrayAdapter);
        spin4.setAdapter(arrayAdapter);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                symptom1=symptomList1.get(i);
                Toast.makeText(getApplicationContext(), "Symptom selected"+symptom1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                if(nameEditText.getText().toString().isEmpty()){

                    nameEditText.setError("Please enter name");
                }
                else if(genderEditText.getText().toString().isEmpty()){

                    genderEditText.setError("Please enter gender");

                }
                else if(ageEditText.getText().toString().isEmpty()){

                    ageEditText.setError("Please enter age");

                }else {

                    NaiveBayes naiveBayesDemo = new NaiveBayes();
                    try {
                        naiveBayesDemo.getDataSet("Testing.arff");
                        naiveBayesDemo.process();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(HomeActivity.this,ResultActivity.class);
                    intent.putExtra("name",nameEditText.getText().toString());
                    intent.putExtra("gender",genderEditText.getText().toString());
                    intent.putExtra("age",ageEditText.getText().toString());
                    startActivity(intent);

                }

            }
        });





    }
    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressed == 2) {
            finishAffinity();
            System.exit(0);
        }
        else {
            doubleBackToExitPressed++;
            Toast.makeText(this, "Please press Back again to exit", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressed=1;
            }
        }, 2000);
    }
}