package com.easynotes.mynotesapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.easynotes.mynotesapp.R;
import com.easynotes.mynotesapp.GetDataInterfaces;
import com.easynotes.mynotesapp.model.Notes;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class Notes_Adapter extends FirestoreRecyclerAdapter<Notes, Notes_Adapter.NotesViewHolder> {

    private final GetDataInterfaces getdatainterfaces;


    public Notes_Adapter(@NonNull @NotNull FirestoreRecyclerOptions<Notes> options, GetDataInterfaces getdatainterfaces) {
        super(options);
        this.getdatainterfaces = getdatainterfaces;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull Notes_Adapter.NotesViewHolder holder, int position, @NonNull @NotNull Notes model) {

        try {
            holder.notes.setText(model.getNote_title());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @NonNull
    @NotNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout, parent, false);
        return new NotesViewHolder(view);
    }


    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView notes;
        AppCompatImageButton delete;
        int position;


        public NotesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            notes = itemView.findViewById(R.id.notesValue);
            delete = itemView.findViewById(R.id.delete);
            position = getAbsoluteAdapterPosition();

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    deleteNote(position);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && getdatainterfaces != null) {
                                getdatainterfaces.passData(getSnapshots().getSnapshot(position));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }

        private void deleteNote(int position) {
            try {
                getSnapshots().getSnapshot(position).getReference().delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


}
