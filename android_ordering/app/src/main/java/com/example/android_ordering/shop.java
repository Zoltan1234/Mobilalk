package com.example.android_ordering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class shop extends AppCompatActivity {
    private static final String LOG_TAG = shop.class.getName();
    private FirebaseAuth authorities;
    private FrameLayout redCircle;
    private TextView contentTextView;
    private FirebaseUser muser;
    private String mCurrentUserEmail;
    private int cartItems = 0;
    private RecyclerView Rview;
    private int gridNumber = 1;
    private ArrayList<Shoppingitem> mitemlist;
    private ShoppingAdapter itemAdapter;
    private MyNotificationManager mynotificationManager;
    private FirebaseFirestore mFirestore;
    private CollectionReference mitems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        authorities = FirebaseAuth.getInstance();
        muser = FirebaseAuth.getInstance().getCurrentUser();

        mitemlist = new ArrayList<>();
        Rview = findViewById(R.id.recycleview);
        Rview.setLayoutManager(new GridLayoutManager(this, gridNumber));
        itemAdapter = new ShoppingAdapter(this, mitemlist);
        Rview.setAdapter(itemAdapter);
        mynotificationManager = new MyNotificationManager(this);
        mFirestore = FirebaseFirestore.getInstance();
        mitems = mFirestore.collection("Products");
        query();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void query() {
        mitemlist.clear();

        mitems.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                Shoppingitem item = document.toObject(Shoppingitem.class);
                mitemlist.add(item);
            }

            if (mitemlist.size()==0){
                intializeData();
                query();
            }
            itemAdapter.notifyDataSetChanged();
        });

    }

    private void intializeData() {
        String[] itemlist = getResources().getStringArray(R.array.shopping_item_names);
        String[] iteminfo = getResources().getStringArray(R.array.shopping_item_desc);
        String[] itemprice = getResources().getStringArray(R.array.shoping_item_price);
        TypedArray itemImageres = getResources().obtainTypedArray(R.array.shopping_item_image);
        TypedArray itemRate = getResources().obtainTypedArray(R.array.shopping_item_rated);
        String[] id = getResources().getStringArray(R.array.shopping_item_id);

        for (int i = 0; i < itemlist.length; i++) {
            mitems.add(new Shoppingitem(id[i],itemlist[i], iteminfo[i], itemprice[i], itemRate.getFloat(i, 0), itemImageres.getResourceId(i, 0),0)).addOnFailureListener(e -> {
                Log.e(LOG_TAG,""+e);
            });
        }
        itemImageres.recycle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.shop_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(LOG_TAG, newText);
                itemAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.log_out_button:
                FirebaseAuth.getInstance().signOut();
                finish();
                mynotificationManager.send("Kijelentkezve");
                return true;
            case R.id.cart:
                goToCart();
                return true;
            case R.id.delete:
                deleteAccount();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAccount() {

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.cart);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();
        redCircle = (FrameLayout) rootView.findViewById(R.id.view_alert_red_circle);
        contentTextView = (TextView) rootView.findViewById(R.id.view_alert_count_textview);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(alertMenuItem);
            }
        });

        return super.onPrepareOptionsMenu(menu);
    }
    public void goToCart() {
        Log.d(LOG_TAG, "clicked cart button");
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void updateAlertIcon() {
        cartItems = (cartItems + 1);
        if (0 < cartItems) {
            contentTextView.setText(String.valueOf(cartItems));
        } else {
            contentTextView.setText("");
        }
    }
}