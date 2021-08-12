package com.easynotes.mynotesapp.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easynotes.mynotesapp.R;


public class ViewNotes_Fragment extends Fragment {

    TextView back, titlesValue, notesValue;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_view_notes_, container, false);

        back = view.findViewById(R.id.back);
        titlesValue = view.findViewById(R.id.titleInput);
        notesValue = view.findViewById(R.id.notesInput);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.remove(ViewNotes_Fragment.this).commit();
            }
        });


        try {
            String titles = getArguments().getString("titles");
            String notes = getArguments().getString("notes");

            titlesValue.setText(titles);
            notesValue.setText(notes);

        } catch (Exception e){
            e.printStackTrace();
        }





       return view;
    }
}