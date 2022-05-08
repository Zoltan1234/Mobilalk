package com.example.android_ordering;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private FirebaseAuth auth;
    private MyNotificationManager mNotificationManager;

    Vibrator vibrator1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        mNotificationManager = new MyNotificationManager(this);

    }

    public void login(View view) {
        EditText emailaddress = findViewById(R.id.editEmailAdd);
        EditText passWord = findViewById(R.id.EditTextPassword);
        String emailaddressStr = emailaddress.getText().toString();
        String passwordStr = passWord.getText().toString();
        if (emailaddressStr.isEmpty() || passwordStr.isEmpty()) {
            Toast.makeText(this, "Kérem adja meg a felhasználó nevet és a jelszót is!", Toast.LENGTH_LONG).show();
            return;
        }
        auth.signInWithEmailAndPassword(emailaddressStr,passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    vibrator1 = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                    vibrator1.vibrate(500);
                    mNotificationManager.send("Sikeres bejelentkezés!");
                    Startshopping();
                }else {
                    Toast.makeText(MainActivity.this, "A bejelentkezés sikertelen volt." + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    relog();
                }
            }
        });
    }
    public void loginasquest(View view) {
        auth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    vibrator1 = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                    vibrator1.vibrate(500);
                    Startshopping();
                } else {
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Startshopping(){
        Intent intent = new Intent(this,shop.class);
        startActivity(intent);

    }
    private void relog(/*data*/){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent1 = new Intent(this,registration.class);
        startActivity(intent1);
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
}