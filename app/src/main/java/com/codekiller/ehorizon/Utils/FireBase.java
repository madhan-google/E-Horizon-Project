package com.codekiller.ehorizon.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBase {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public FireBase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Admin");
    }
    public FirebaseDatabase getFireDB(){
        return this.firebaseDatabase;
    }
    public DatabaseReference getDB(){
        return this.databaseReference;
    }
}
