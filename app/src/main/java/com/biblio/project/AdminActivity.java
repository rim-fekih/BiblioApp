package com.biblio.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener mAuthStateListenner;
    private Toolbar mToolbar;

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    CustomAdapter customAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(AdminActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(AdminActivity.this,this, book_id, book_title, book_author, book_pages);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 ){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount()==0){
            Toast.makeText(AdminActivity.this, "No Book!", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:{
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(AdminActivity.this,LoginActivity.class);
                startActivity(intToMain);
            }

            case R.id.delete_all: {
                Toast.makeText(AdminActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                confirmDialog();
            }
            case R.id.hello:{
                Toast.makeText(AdminActivity.this, "contact us : rimfekih0@gmail.com", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All books ?");
        builder.setMessage("Are you sure you want to delete all data");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AdminActivity.this);
                myDB.deleteAllData();
                // refresh
                Intent intent = new Intent (AdminActivity.this,AdminActivity.class);
                startActivity(intent);
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}