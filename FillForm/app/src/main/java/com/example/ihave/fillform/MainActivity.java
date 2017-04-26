package com.example.ihave.fillform;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText nameInput, phoneInput, emailInput;
    RadioGroup genderBtnGrp;
    Spinner bloodSpinner;
    ArrayAdapter<CharSequence> bloodTypeList;
    Button birthdaySelectBtn, sendBtn;
    TextView birthdayTextView;
    CheckBox checkDataChBox;


    String name, gender, bloodType, birthday, phoneNumber, emailAdr;
    private Calendar calendar = Calendar.getInstance();
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        setupWidget();
    }

    void findView() {
        setContentView(R.layout.activity_main);
        nameInput = (EditText)findViewById(R.id.nameInput);
        genderBtnGrp = (RadioGroup)findViewById(R.id.genderBtnGrp);
        bloodSpinner = (Spinner)findViewById(R.id.spinnerBloodType);
        birthdaySelectBtn = (Button)findViewById(R.id.birthdaySelectBtn);
        birthdayTextView = (TextView)findViewById(R.id.showSelectedBirthday);
        phoneInput = (EditText)findViewById(R.id.phoneInput);
        emailInput = (EditText)findViewById(R.id.emailInput);
        sendBtn = (Button)findViewById(R.id.sendBtn);
        checkDataChBox = (CheckBox)findViewById(R.id.checkData);
    }

    void setupWidget() {
        setGenderButtons();
        setBloodTypeSpinner();
        setSendBtn();
    }

    String getTextInput(EditText textInput, String inputName) {
        if (textInput.getText().toString().equals("")) {
            return "";
        }
        else
            return textInput.getText().toString();
    }

    void setGenderButtons() {
        genderBtnGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (genderBtnGrp.getCheckedRadioButtonId()) {
                    case R.id.femaleBtn:
                        gender = "女性";
                        break;
                    case R.id.maleBtn:
                        gender = "男性";
                        break;
                }
                Toast.makeText(MainActivity.this, "性別"+gender, Toast.LENGTH_LONG).show();
            }
        });
    }

    void setBloodTypeSpinner() {
        bloodTypeList = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.blood_type,
                android.R.layout.simple_spinner_dropdown_item);

        bloodSpinner.setAdapter(bloodTypeList);

        bloodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodType = parent.getSelectedItem().toString();
                Toast.makeText(MainActivity.this, "血型"+bloodType, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showDatePickerDialog(View v) {
        DatePickerDialog datePickDialog = new DatePickerDialog(MainActivity.this,
                dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickDialog.show();
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            birthday = year + "/" + (month + 1) + "/" + dayOfMonth;
            birthdayTextView.setText(birthday);
        }
    };

    void setSendBtn() {

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

    }

    boolean checkData() {
        boolean allFilled = true;
        // check all data filled or not
        if (name.equals("")) {
            Toast.makeText(MainActivity.this, "name null", Toast.LENGTH_LONG).show();
            allFilled = false;
        }
        if (gender == null) {
            Toast.makeText(MainActivity.this, "gender null", Toast.LENGTH_LONG).show();
            allFilled = false;
        }
        if (bloodType == null) {
            Toast.makeText(MainActivity.this, "bloodType null", Toast.LENGTH_LONG).show();
            allFilled = false;
        }
        if (birthday == null) {
            Toast.makeText(MainActivity.this, "birthday null", Toast.LENGTH_LONG).show();
            allFilled = false;
        }
        if (phoneNumber.equals("")) {
            Toast.makeText(MainActivity.this, "phoneNumber null", Toast.LENGTH_LONG).show();
            allFilled = false;
        }
        if (emailAdr.equals("")) {
            Toast.makeText(MainActivity.this, "emailAdr null", Toast.LENGTH_LONG).show();
            allFilled = false;
        }
        return allFilled;
    }

    void sendData() {
        name = getTextInput(nameInput, "name");
        phoneNumber = getTextInput(phoneInput, "phone");
        emailAdr = getTextInput(emailInput, "email");

        if (checkDataChBox.isChecked()) {
            if(checkData()) {
                String allData = name + '\n' + gender + '\n' + bloodType + '\n' +
                        birthday + '\n' +
                        phoneNumber + '\n' + emailAdr;
                useBundle(allData);
            }
        }
        else {
            Toast.makeText(MainActivity.this, "請勾選確認資料無誤", Toast.LENGTH_LONG).show();
        }

    }

    void useBundle(String toSendData) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, showDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("toSendData", toSendData);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
