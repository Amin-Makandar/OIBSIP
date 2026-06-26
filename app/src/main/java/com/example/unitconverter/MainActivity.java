package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spCategory, spFromUnit, spToUnit;
    EditText etValue;
    Button btnConvert;
    TextView tvResult;

    String[] categories = {"Length", "Weight", "Temperature"};

    String[] lengthUnits = {"Centimeter", "Meter", "Kilometer"};
    String[] weightUnits = {"Gram", "Kilogram", "Pound"};
    String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        spCategory = findViewById(R.id.spCategory);
        spFromUnit = findViewById(R.id.spFromUnit);
        spToUnit = findViewById(R.id.spToUnit);
        etValue = findViewById(R.id.etValue);
        btnConvert = findViewById(R.id.btnConvert);
        tvResult = findViewById(R.id.tvResult);

        ArrayAdapter<String> categoryAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        categories
                );

        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spCategory.setAdapter(categoryAdapter);

        loadUnits(lengthUnits);

        spCategory.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        if (position == 0) {
                            loadUnits(lengthUnits);
                        } else if (position == 1) {
                            loadUnits(weightUnits);
                        } else {
                            loadUnits(temperatureUnits);
                        }
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {

                    }
                });

        btnConvert.setOnClickListener(v -> {

            String input = etValue.getText().toString().trim();

            // Empty Input Validation
            if (input.isEmpty()) {
                Toast.makeText(
                        MainActivity.this,
                        "Please enter a value",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            // Non-Numeric Validation
            double value;

            try {
                value = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                Toast.makeText(
                        MainActivity.this,
                        "Please enter a valid number",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            String category = spCategory.getSelectedItem().toString();
            String fromUnit = spFromUnit.getSelectedItem().toString();
            String toUnit = spToUnit.getSelectedItem().toString();

            double result = convert(
                    category,
                    value,
                    fromUnit,
                    toUnit
            );

            tvResult.setText("Result: " + result + " " + toUnit);

        });
    }

    private void loadUnits(String[] units) {

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        units
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spFromUnit.setAdapter(adapter);
        spToUnit.setAdapter(adapter);
    }

    private double convert(
            String category,
            double value,
            String from,
            String to) {

        if (category.equals("Length")) {

            double meters = 0;

            switch (from) {
                case "Centimeter":
                    meters = value / 100;
                    break;

                case "Meter":
                    meters = value;
                    break;

                case "Kilometer":
                    meters = value * 1000;
                    break;
            }

            switch (to) {
                case "Centimeter":
                    return meters * 100;

                case "Meter":
                    return meters;

                case "Kilometer":
                    return meters / 1000;
            }
        }

        else if (category.equals("Weight")) {

            double kilograms = 0;

            switch (from) {
                case "Gram":
                    kilograms = value / 1000;
                    break;

                case "Kilogram":
                    kilograms = value;
                    break;

                case "Pound":
                    kilograms = value * 0.453592;
                    break;
            }

            switch (to) {
                case "Gram":
                    return kilograms * 1000;

                case "Kilogram":
                    return kilograms;

                case "Pound":
                    return kilograms / 0.453592;
            }
        }

        else if (category.equals("Temperature")) {

            if (from.equals("Celsius") && to.equals("Fahrenheit"))
                return (value * 9 / 5) + 32;

            if (from.equals("Fahrenheit") && to.equals("Celsius"))
                return (value - 32) * 5 / 9;

            if (from.equals("Celsius") && to.equals("Kelvin"))
                return value + 273.15;

            if (from.equals("Kelvin") && to.equals("Celsius"))
                return value - 273.15;

            if (from.equals("Fahrenheit") && to.equals("Kelvin"))
                return (value - 32) * 5 / 9 + 273.15;

            if (from.equals("Kelvin") && to.equals("Fahrenheit"))
                return (value - 273.15) * 9 / 5 + 32;

            return value;
        }

        return value;
    }
}