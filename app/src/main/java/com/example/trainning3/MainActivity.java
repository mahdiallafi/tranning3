package com.example.trainning3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<Contacts> arrayList=new ArrayList<Contacts>();
    ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycle);
        //check permission
        checkPermission();
    }

    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]
                    {Manifest.permission.READ_CONTACTS},100);
        }else
        {
            getContactList();
        }
    }

    private void getContactList() {
        //iniliaize uri
        Uri uri= ContactsContract.Contacts.CONTENT_URI;
        //sort by ascendong
        String sort=ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+"ASC";
        //inilialize coursore
        Cursor cursor=getContentResolver().query(uri,null,
                null,
        null,sort);
        //check constion
        if(cursor.getCount()>0){
            //we use loop
            while (cursor.moveToNext()){
                //get id of contact
                @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts._ID
                ));
                ///get contact name
                @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                ));
                //inilizae phone uri
                Uri uri1=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                //inilizae selection
                String selection=ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                        +"=?";
                //iniliaze phone cursor
                Cursor phonecurosr=getContentResolver().query(uri1,null,selection,
                        new String[]{id},null);
                //check condtion
                if(phonecurosr.moveToNext()){
                   ///when phone cursor move to nex
                   @SuppressLint("Range") String number=phonecurosr.getString(phonecurosr.getColumnIndex(
                           ContactsContract.CommonDataKinds.Phone.NUMBER
                   )) ;
                   //initialize contact model
                    Contacts model=new Contacts();
                    //set name
                    model.setName(name);
                    model.setNumber(number);
                arrayList.add(model);
                phonecurosr.close();

                }
            }
            cursor.close();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter=new ContactAdapter(this,arrayList);
        recyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //check
        if(requestCode == 100 && grantResults.length>0 && grantResults[0]==
        PackageManager.PERMISSION_GRANTED){
            getContactList();
        }else
        {
            Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
            checkPermission();
        }

    }
}