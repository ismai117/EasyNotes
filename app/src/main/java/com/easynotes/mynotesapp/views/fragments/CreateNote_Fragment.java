package com.easynotes.mynotesapp.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easynotes.mynotesapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CreateNote_Fragment extends Fragment{

    Button add, cancel;
    private EditText noteTitleInput, notesInput;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    public CreateNote_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_create_note_, container, false);

       noteTitleInput = view.findViewById(R.id.note_title_input);
       notesInput = view.findViewById(R.id.note_input);
       add = view.findViewById(R.id.add_note);
       cancel = view.findViewById(R.id.cancel_note);

       fAuth = FirebaseAuth.getInstance();
       fStore = FirebaseFirestore.getInstance();

       String userID = fAuth.getCurrentUser().getUid();


       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String noteTitleValue = noteTitleInput.getText().toString();
               String noteInputValue = notesInput.getText().toString();
               if (TextUtils.isEmpty(noteTitleValue) || TextUtils.isEmpty(noteInputValue)){
                   Toast.makeText(getContext(), "Can't submit empty  field", Toast.LENGTH_SHORT).show();
               }else {
                   noteTitleInput.getText().clear();
                   notesInput.getText().clear();
                   storeNote(noteTitleValue, noteInputValue, userID);
               }
           }
       });

       cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
               fragmentTransaction.remove(CreateNote_Fragment.this).commit();
           }
       });



       return view;
    }

    private void storeNote(String noteTitleValue, String noteValue, String userID) {

        Map<String, Object> note = new HashMap<>();
        note.put("note_title", noteTitleValue);
        note.put("notes", noteValue);
        note.put("timestamp", new Timestamp(new Date()));
        note.put("userID", userID);

        fStore.collection("easynotes").document(userID).collection("notes")
                .add(note)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            Log.d("notes", "success");
                            Log.d("userid", " " + userID);
                        }else {
                            Log.d("notes", "failed");
                        }
                    }
                });
    }

}