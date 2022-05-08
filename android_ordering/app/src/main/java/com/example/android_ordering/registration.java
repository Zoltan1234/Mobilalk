package com.example.android_ordering;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = registration.class.getName();
    private EditText editUsername;
    private EditText editPass;
    private EditText editPassAgain;
    private EditText editaddress;
    private EditText editEmailAdd;
    private RadioGroup radiogrouptype;
    private Vibrator vibrator;
    private Spinner spinner;

    private SharedPreferences preferences;
    private FirebaseAuth authorities;
    private FirebaseFirestore mFirestore;
    private CollectionReference mUserListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editUsername = findViewById(R.id.editUsername);
        editPass = findViewById(R.id.editPass);
        editPassAgain = findViewById(R.id.editPassAgain);
        editEmailAdd = findViewById(R.id.editEmailAdd);
        radiogrouptype = findViewById(R.id.radiogrouptype);
        radiogrouptype.check(R.id.eladobutton);
        radiogrouptype.check(R.id.vasarlobutton);
        spinner = findViewById(R.id.spinner);
        editaddress = findViewById(R.id.editAddress);
        authorities = FirebaseAuth.getInstance();
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.nem,android.R.layout.simple_selectable_list_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        authorities = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUserListRef = mFirestore.collection("Users");
        Log.i(LOG_TAG, "oncreate");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }

    public void regsend(View view) {
        String email = editEmailAdd.getText().toString();
        String password = editPass.getText().toString();
        String passwordagain = editPassAgain.getText().toString();
        String username = editUsername.getText().toString();
        String address = editaddress.getText().toString();
        int checkedId = radiogrouptype.getCheckedRadioButtonId();
        RadioButton radioButton = radiogrouptype.findViewById(checkedId);
        String accountype = radioButton.getText().toString();

        if (!password.equals(passwordagain)) {
            Toast.makeText(this, "A jelszavak nem egyeznek!", Toast.LENGTH_LONG).show();
            return;
        }
        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || accountype.isEmpty() || address.isEmpty()){
            Toast.makeText(this, "Minden mezőt kötelező kitölteni!", Toast.LENGTH_LONG).show();
            return;
        }
        Log.i(LOG_TAG, "Regisztrált: " + username + ", e-mail: " + email);

        authorities.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                    insertUser();
                    Startshopping();
                }else {
                    Toast.makeText(registration.this,"A regisztráció sikertelen volt." + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
    private void insertUser() {
        int radioButtonId = radiogrouptype.getCheckedRadioButtonId();
        RadioButton radioButton = radiogrouptype.findViewById(radioButtonId);
        String which_sex = (String) radioButton.getText();
        mUserListRef.add(new User(
                editUsername.getText().toString(),
                editEmailAdd.getText().toString().toLowerCase(),
                editPass.getText().toString(),
                spinner.getSelectedItem().toString(),
                editaddress.getText().toString(),
                which_sex,
                new ArrayList<>()
        )).addOnFailureListener(e -> {
            Log.e(LOG_TAG,""+e);
        });
    }
    private void Startshopping(/*data*/){
        Intent intent = new Intent(this,shop.class);
        startActivity(intent);

    }
    public void loginasquest(View view) {
        authorities.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                    Startshopping();
                } else {
                    Toast.makeText(registration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }

    public void login(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}