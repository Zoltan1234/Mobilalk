package com.example.android_ordering;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class OrderingActivity extends AppCompatActivity {
    private static final String LOG_TAG = OrderingActivity.class.getName();
    private EditText mFullName;
    private EditText mEmail;
    private EditText mAddress;
    private FirebaseUser mUser;
    private CollectionReference mUserRef;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);
        mFirestore = FirebaseFirestore.getInstance();
        mFullName = findViewById(R.id.editUsername);
        mEmail = findViewById(R.id.editEmail);
        mAddress = findViewById(R.id.editAddress);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    public void fillDetails(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        Log.e(LOG_TAG," USER "+ mUser.getEmail());
        if (checked) {
            mUserRef.whereEqualTo("usrname", FirebaseAuth.getInstance().getCurrentUser().getEmail()).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            User user = document.toObject(User.class);
                            Log.e(LOG_TAG,""+ user);
                            mFullName.setText(user.getUsrname());
                            mEmail.setText(user.getEmail());
                            mAddress.setText(user.getAddress());
                        }
                    }).addOnFailureListener(e -> Log.e(LOG_TAG, "" + e.getMessage()));
        } else {
            mFullName.setText("");
            mEmail.setText("");
            mAddress.setText("");
        }
    }
}