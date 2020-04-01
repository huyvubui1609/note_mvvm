package com.example.note_mvvm.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_mvvm.Model.Note;
import com.example.note_mvvm.R;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };
    private onItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.title.setText(currentNote.getTitle());
        holder.description.setText(currentNote.getDescription());
        holder.priority.setText(String.valueOf(currentNote.getPriority()));
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;

    }

    public interface onItemClickListener {
        void onItemClick(Note note);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView priority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            priority = itemView.findViewById(R.id.tv_priority);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positon = getAdapterPosition();
                    if (listener != null && positon != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(positon));
                    }
                }
            });
        }
    }
}
