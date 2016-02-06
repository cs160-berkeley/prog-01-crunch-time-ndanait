package com.example.neildanait.calorieconverter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalorieHomeScreen extends AppCompatActivity {

    public Button convertButtonClick;
    public Button viewCalorieComparisons;
    public Spinner exerciseSpinner;
    public String exerciseSelected;
    public Float weightScalar;
    public Float caloriesBurned;
    public Float valueEquivalencyInt;
    public HashMap<String, String> exerciseAssociations;
    public HashMap<String, Float> exerciseConversions;
    public HashMap<String, Float> exerciseEquivalencies;
    public List<String> calorieArray;
    public ListView calorieComparisons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Construct a ListView for Calorie Comparisons and Equivalencies
        calorieComparisons = (ListView) findViewById(R.id.calorieComparisons);
        calorieArray = new ArrayList<>(); //Generate a CalorieArray
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, calorieArray);
        calorieComparisons.setAdapter(arrayAdapter);

        //Item Selected Listener for Spinner
        exerciseSpinner = (Spinner)findViewById(R.id.exerciseList);
        exerciseSpinner.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exerciseSelected = exerciseSpinner.getSelectedItem().toString(); //String Value of Selected Exercise
                if (!exerciseSelected.equals("None Selected")) {
                    ((TextView) findViewById(R.id.duration)).setText("Duration (" + exerciseAssociations.get(exerciseSelected) + ")"); //Relabel TextView Using HashMap Association
                }
                String itemSelected = parent.getItemAtPosition(position).toString();
                if (!itemSelected.equals("None Selected")) {
                    Toast.makeText(getBaseContext(), itemSelected + " has been selected", Toast.LENGTH_LONG).show(); //Prompt User with Selected Exercise
                }
                arrayAdapter.clear(); //Clear Old Equivalencies
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        }));

        //Insert Associations for Exercises To Guide User
        exerciseAssociations = new HashMap<>();
        exerciseAssociations.put("Pushup", "Reps");
        exerciseAssociations.put("Situp", "Reps");
        exerciseAssociations.put("Squats", "Reps");
        exerciseAssociations.put("Leg-Lift", "Minutes");
        exerciseAssociations.put("Plank", "Minutes");
        exerciseAssociations.put("Jumping Jacks", "Minutes");
        exerciseAssociations.put("Pullup", "Reps");
        exerciseAssociations.put("Cycling", "Minutes");
        exerciseAssociations.put("Walking", "Minutes");
        exerciseAssociations.put("Jogging", "Minutes");
        exerciseAssociations.put("Swimming", "Minutes");
        exerciseAssociations.put("Stair-Climbing", "Minutes");

        //Insert Conversions for Exercises (Exercise, Calories to Duration Ratio)
        exerciseConversions = new HashMap<>();
        exerciseConversions.put("Pushup", (float) 100/350);
        exerciseConversions.put("Situp", (float) 100/200);
        exerciseConversions.put("Squats", (float) 100/225);
        exerciseConversions.put("Leg-Lift", (float) 100/25);
        exerciseConversions.put("Plank", (float) 100/25);
        exerciseConversions.put("Jumping Jacks", (float) 100/10);
        exerciseConversions.put("Pullup", (float) 1);
        exerciseConversions.put("Cycling", (float) 100/12);
        exerciseConversions.put("Walking", (float) 100/20);
        exerciseConversions.put("Jogging", (float) 100/12);
        exerciseConversions.put("Swimming", (float) 100/13);
        exerciseConversions.put("Stair-Climbing", (float) 100/15);

        //Insert Conversions for Equivalencies (Exercise Reps/Mins, Calories to Duration Ratio)
        exerciseEquivalencies = new HashMap<>();
        exerciseEquivalencies.put("Pushup Reps: ", (float) 100/350);
        exerciseEquivalencies.put("Situp Reps: ", (float) 100/200);
        exerciseEquivalencies.put("Squats Reps: ", (float) 100/225);
        exerciseEquivalencies.put("Leg-Lift Mins: ", (float) 100/25);
        exerciseEquivalencies.put("Plank Mins: ", (float) 100/25);
        exerciseEquivalencies.put("Jumping Jacks Mins: ", (float) 100/10);
        exerciseEquivalencies.put("Pullup Reps: ", (float) 1);
        exerciseEquivalencies.put("Cycling Mins: ", (float) 100/12);
        exerciseEquivalencies.put("Walking Mins: ", (float) 100/20);
        exerciseEquivalencies.put("Jogging Mins: ", (float) 100/12);
        exerciseEquivalencies.put("Swimming Mins: ", (float) 100/13);
        exerciseEquivalencies.put("Stair-Climbing Mins: ", (float) 100/15);

        //Convert Button Click Event Listener
        convertButtonClick = (Button) findViewById(R.id.convertButton); //Find Convert Button
        convertButtonClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == convertButtonClick) {

                    //Duration Variables
                    EditText durationInput = ((EditText) findViewById(R.id.durationElement)); //Acquire Input Value in EditText form
                    String durationStringInput = durationInput.getText().toString(); //Convert EditText Input to String Value
                    int durationIntInput = new Integer(durationStringInput).intValue(); //Convert String Value to Integer Value

                    //Weight Variables
                    EditText weightInput = ((EditText) findViewById(R.id.weightElement)); //Acquire Input Value in EditText form
                    if (!weightInput.getText().toString().matches("")) { //Confirm That User Entered a Weight Value
                        String weightStringInput = weightInput.getText().toString(); //Convert EditText Input to String Value
                        int weightIntInput = new Integer(weightStringInput).intValue(); //Convert String Value to Integer Value
                        weightScalar = (float) weightIntInput / 150; //Calculate a Scalar Weight Ratio
                    } else {
                        weightScalar = (float) 1; //Weight Scalar is 1 if Not Provided
                    }

                    //Controls the Calories Burned Display and Value
                    if (!exerciseSelected.equals("None Selected")) {
                        caloriesBurned = weightScalar * durationIntInput * exerciseConversions.get(exerciseSelected); // Calculate Calories Burned Float Value (takes in weight, duration)
                        ((EditText) findViewById(R.id.caloriesElement)).setText(Float.toString(caloriesBurned)); //Set Text to Equal String Value of Calories Burned Float
                    } else {
                        Toast.makeText(getBaseContext(), "Please select an exercise", Toast.LENGTH_LONG).show();
                    }
                }
                arrayAdapter.clear(); //Clear Old Equivalencies
                return;
            }
        });

        //Comparison Button Click Event Listener
        viewCalorieComparisons = (Button) findViewById(R.id.viewEquivalencies); //Find Convert Button
        viewCalorieComparisons.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == viewCalorieComparisons) {
                    arrayAdapter.clear(); //Clear Old Equivalencies
                    for (String exerciseLabel : exerciseEquivalencies.keySet()) {
                        EditText weightInput = ((EditText) findViewById(R.id.weightElement)); //Acquire Input Value in EditText form
                        if (!weightInput.getText().toString().matches("")) { //Confirm That User Entered a Weight Value
                            String weightStringInput = weightInput.getText().toString(); //Convert EditText Input to String Value
                            int weightIntInput = new Integer(weightStringInput).intValue(); //Convert String Value to Integer Value
                            weightScalar = (float) weightIntInput / 150; //Calculate a Scalar Weight Ratio
                        } else {
                            weightScalar = (float) 1; //Weight Scalar is 1 if Not Provided
                        }
                        valueEquivalencyInt = caloriesBurned / (weightScalar * exerciseEquivalencies.get(exerciseLabel)); //Divide the Calories Burned by the Ratio Associated with Exercise
                        String finalConversionValue = exerciseLabel + valueEquivalencyInt.toString(); //Final Value for each Exercise
                        arrayAdapter.add(finalConversionValue); //Add the Value to the Adapter to Show Later
                    }
                }
                return;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calorie_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
