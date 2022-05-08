package com.example.android_ordering;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private static final String LOG_TAG = CartActivity.class.getName();
    private FirebaseUser mUser;
    private String mCurrentUserEmail;
    private RecyclerView mRecyclerView;
    private List<Shoppingitem> mCartList;
    private CartAdapter mAdapter;
    private FirebaseFirestore mFirestore;
    private CollectionReference mUserRef;

    private TextView mEmptyCart;
    private View mContinueButton;
    private int mCartNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
        } else {
            finish();
        }

        mCurrentUserEmail = mUser.getEmail();
        mCartList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.cartRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mAdapter = new CartAdapter(this, (ArrayList<Shoppingitem>) mCartList);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mUserRef = mFirestore.collection("Users");

        mEmptyCart = findViewById(R.id.emptyCart);
        mContinueButton = findViewById(R.id.continueToOrder);

        queryCart();
    }

    private void queryCart() {
        mCartList.clear();
        mCartNumber = 1;
        mUserRef.whereEqualTo("email", mCurrentUserEmail).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        User user = document.toObject(User.class);
                        mCartList.addAll(user.getCartlist());
                        mCartNumber = mCartList.size();
                    }
                    handleContinueButton();
                    mAdapter.notifyDataSetChanged();
                });
    }

    private void handleContinueButton() {
        if (mCartNumber > 0) {
            mEmptyCart.setVisibility(GONE);
            mContinueButton.setVisibility(VISIBLE);
        } else {
            mEmptyCart.setVisibility(VISIBLE);
            mContinueButton.setVisibility(GONE);
        }
    }


    public void goToorder(View view) {
        Intent intent = new Intent(this,OrderingActivity.class);
        startActivity(intent);
    }
}