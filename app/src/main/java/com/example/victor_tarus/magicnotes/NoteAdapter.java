package com.example.victor_tarus.magicnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
private List<Note>notes = new ArrayList<>();
    private ItemClickedlistener itemClickedlistener;
Context context;
Note note ;
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new NoteHolder(noteView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        holder.tv_title.setText(notes.get(position).getTitle());
        holder.tv_content.setText(notes.get(position).getContent());
        holder.tv_date.setText(notes.get(position).getDatetime());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    public void  setNotes(List<Note>notes){
        this.notes = notes;
        notifyDataSetChanged();
    }


    public class NoteHolder  extends  RecyclerView.ViewHolder{
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_date;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.txt_title);
            tv_content = itemView.findViewById(R.id.txt_content);
            tv_date = itemView.findViewById(R.id.txt_date);
itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        int position = getAdapterPosition();
        if (itemClickedlistener != null && position != RecyclerView.NO_POSITION) {
            itemClickedlistener.onItemClick(notes.get(position));

        }
    }
});

        }

    }
    public Note getNoteposition(int position){
        return  notes.get(position);
    }
    public  interface  ItemClickedlistener{
        void onItemClick(Note note);
    }
    public  void setOnItemClicklistener(ItemClickedlistener listener){
this.itemClickedlistener = listener;
    }
}
