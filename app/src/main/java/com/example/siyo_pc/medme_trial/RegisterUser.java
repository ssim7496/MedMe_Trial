package com.example.siyo_pc.medme_trial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siyo_pc.medme_trial.classes.MM_Person;
import com.example.siyo_pc.medme_trial.db.BusinessLogic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity {

    Button btnRegister, btnCancel;
    EditText edtName, edtSurname, edtEmail, edtCellNo, edtPass, edtConfirmPass, edtRecovQues, edtRecovAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        addButtonEvents();
    }

    public void addButtonEvents(){
        btnRegister = (Button)findViewById(R.id.btnRegisRegister);
        btnCancel = (Button)findViewById(R.id.btnRegisCancel);

        //addNextActivityOnClickListener(btnRegister, RegisterUser.class);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPerson();
            }
        });
        previousActivity(btnCancel);
    }

    public void addNextActivityOnClickListener(View view, final Class nextClass) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), nextClass);
                startActivity(intent);
            }
        });
    }

    public void previousActivity(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(Start.class);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed(Start.class);

        }

        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed(final Class previousClass) {
        Intent myIntent = new Intent(getApplicationContext(), previousClass);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
        finish();
        return;
    }

    private void registerPerson() {
        edtName = (EditText)findViewById(R.id.edtRegisName);
        edtSurname = (EditText)findViewById(R.id.edtRegisSurname);
        edtEmail = (EditText)findViewById(R.id.edtRegisEmail);
        edtCellNo = (EditText)findViewById(R.id.edtRegisCellNumber);
        edtPass = (EditText)findViewById(R.id.edtRegisPassword);
        edtConfirmPass = (EditText)findViewById(R.id.edtRegisConfirmPassword);
        edtRecovQues = (EditText)findViewById(R.id.edtRegisRecoveryQuestion);
        edtRecovAns = (EditText)findViewById(R.id.edtRegisRecoveryAnswer);

        try {
            boolean checkData = checkFields(edtName, edtSurname, edtEmail, edtCellNo, edtPass, edtConfirmPass, edtRecovQues, edtRecovAns);

            if (checkData) {
                MM_Person person = new MM_Person();
                person.SetPersonName(edtName.getText().toString());
                person.SetPersonSurname(edtSurname.getText().toString());
                person.SetPersonEmailAddress(edtEmail.getText().toString());
                person.SetPersonCellphoneNumber(edtCellNo.getText().toString());
                person.SetPersonPassword(edtPass.getText().toString());
                person.SetPersonRecoveryQuestion(edtRecovQues.getText().toString());
                person.SetPersonRecoveryAnswer(edtRecovAns.getText().toString());

                BusinessLogic bll = new BusinessLogic(this);
                bll.addPerson(person);

                Intent intent = new Intent(getApplicationContext(), Start.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Oops. Something went wrong and we will get to it very soon.", Toast.LENGTH_LONG).show();
        }



    }

    private boolean checkFields(EditText edtName, EditText edtSurname, EditText edtEmail, EditText edtCellNo, EditText edtPass, EditText edtConfirmPass,
                                EditText edtRecovQues, EditText edtRecovAns) {
        boolean result = false;

        //perform empty fields check
        boolean emptyName = checkEmpty(edtName);
        boolean emptySurname = checkEmpty(edtSurname);
        boolean emptyEmail = checkEmpty(edtEmail);
        boolean emptyCellNo = checkEmpty(edtCellNo);
        boolean emptyPass = checkEmpty(edtPass);
        boolean emptyConfirmPass = checkEmpty(edtConfirmPass);
        boolean emptyRecovQues = checkEmpty(edtRecovQues);
        boolean emptyRecovAns = checkEmpty(edtRecovAns);

        if (emptyName == true || emptySurname == true || emptyEmail == true || emptyCellNo == true || emptyPass == true || emptyConfirmPass == true ||
                emptyRecovQues == true || emptyRecovAns == true) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
            result = false;
        } else {
            boolean validEmail = checkValidEmail(edtEmail.getText().toString());

            if (validEmail == false) {
                Toast.makeText(getApplicationContext(), "The e-mail address entered is invalid.", Toast.LENGTH_LONG).show();
                result = false;
            } else {
                boolean validCell = checkValidCell(edtCellNo.getText().toString());

                if (validCell == false) {
                    Toast.makeText(getApplicationContext(), "The cellphone number entered is invalid.", Toast.LENGTH_LONG).show();
                    result = false;
                } else {
                    String validPassword = checkValidPassword(edtPass.getText().toString());

                    if ("".equals(validPassword.trim())) {
                        if (edtPass.getText().toString().equals(edtConfirmPass.getText().toString())){
                            result = true;
                        } else {
                            Toast.makeText(getApplicationContext(), "The passwords entered do not match.", Toast.LENGTH_LONG).show();
                            result = false;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), validPassword, Toast.LENGTH_LONG).show();
                        result = false;
                    }
                }
            }
        }
        return  result;
    }

    private boolean checkEmpty(EditText value) {
        boolean result = false;

        if ("".equals(value.getText().toString().trim()) || value.getText().toString().trim() == null){
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    private boolean checkValidEmail(String email) {
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean checkValidCell(String cell) {
        boolean result = false;
        String x = cell.substring(0,1);
        int y = cell.length();
        if ("0".equals(cell.substring(0,1)) && cell.length() == 10){
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    private String checkValidPassword(String password) {
        String errorMsg = "";

        if ("".equals(password.trim()) || password.trim() == null) {
            errorMsg = "Please enter a password";
        } else if (password.length() < 6) {
            errorMsg = "The password must be at least 6 characters in length.";
        } else if (checkPasswordCharacters(password) == false) {
            errorMsg = "The password must contains lowercase and uppercase characters, numbers, special characters and no spaces.";
        }

        return errorMsg;
    }

    private boolean checkPasswordCharacters(String password) {
        boolean result = false;

        int upper=0,lower=0,nums=0, spaces=0, special=0;
        char letter;

        for (int i = 0; i < password.length(); i++) {
            letter = password.charAt(i);

            if(letter>=65 && letter<=90) // Condition for Uppercase letters
                upper++;
            if(letter>='a' && letter <='z')
                lower++;
            if(letter>='0' && letter<='9')
                nums++;
            if(letter==' ') // Condition for spaces
                spaces++;
        }

        special = password.length() - (upper + lower + nums + spaces);

        if (upper > 0 && lower > 0 && nums > 0 && spaces == 0 && special > 0){
            result = true;
        } else {
            result = false;
        }
        return  result;
    }

    //DEFAULT CODE
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_user, menu);
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
