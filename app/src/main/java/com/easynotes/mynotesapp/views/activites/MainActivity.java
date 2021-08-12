package com.easynotes.mynotesapp.views.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easynotes.mynotesapp.GetDataInterfaces;
import com.easynotes.mynotesapp.R;
import com.easynotes.mynotesapp.adapters.Notes_Adapter;
import com.easynotes.mynotesapp.model.Notes;

import com.easynotes.mynotesapp.views.fragments.CreateNote_Fragment;
import com.easynotes.mynotesapp.views.fragments.ViewNotes_Fragment;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements GetDataInterfaces {

    FloatingActionButton create;
    RecyclerView recyclerView;
    AppCompatImageButton logout;

    private Notes_Adapter adapter;
    private  FirebaseUser user;
    private FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create = findViewById(R.id.createNote_btn);
        recyclerView = findViewById(R.id.notes_reycycler_view);
        logout = findViewById(R.id.logout);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


       user = fAuth.getCurrentUser();

        String currentID = null;
        if (user != null) {
            currentID = user.getUid();
        }


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.mainFragment_container, new CreateNote_Fragment()).commit();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        getnotes(currentID);

    }

    public void getnotes(String currentID) {

           try {
               Query query = fStore.collection("easynotes").document(currentID).collection("notes").orderBy("timestamp", Query.Direction.DESCENDING);

               FirestoreRecyclerOptions<Notes> options = new FirestoreRecyclerOptions.Builder<Notes>().setQuery(query, Notes.class).build();
               adapter = new Notes_Adapter(options, this);

               recyclerView.setLayoutManager(new LinearLayoutManager(this));
               recyclerView.setHasFixedSize(true);
               recyclerView.setAdapter(adapter);
           } catch (Exception e){
               e.printStackTrace();
           }


    }


    @Override
    protected void onStart() {
        super.onStart();
        try {
            adapter.startListening();

            if (user == null){
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            adapter.stopListening();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void passData(DocumentSnapshot documentSnapshot) {

        try {
            Notes notes = documentSnapshot.toObject(Notes.class);
            String titlesValue = null;
            String notesValue= null;
            if (notes != null) {
                titlesValue = notes.getNote_title();
                notesValue = notes.getNotes();
            }


            Bundle bundle = new Bundle();
            Fragment fragment = new ViewNotes_Fragment();

            bundle.putString("titles", titlesValue);
            bundle.putString("notes", notesValue);

            fragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.mainFragment_container, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}