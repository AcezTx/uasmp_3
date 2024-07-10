package com.example.uassql;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class siswaAdapter extends RecyclerView.Adapter<siswaAdapter.siswaViewHolder> {

    private List<siswa> studentList;
    private OnStudentDeleteListener onStudentDeleteListener;

    public interface OnStudentDeleteListener {
        void onStudentDelete(long id);
    }

    public siswaAdapter(List<siswa> studentList, OnStudentDeleteListener listener) {
        this.studentList = studentList;
        this.onStudentDeleteListener = listener;
    }

    @NonNull
    @Override
    public siswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_siswa, parent, false);
        return new siswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull siswaViewHolder holder, int position) {
        siswa student = studentList.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class siswaViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, nimTextView, ipkTextView, subjectTextView;
        View deleteButton;

        siswaViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_name);
            nimTextView = itemView.findViewById(R.id.text_nim);
            ipkTextView = itemView.findViewById(R.id.text_ipk);
            subjectTextView = itemView.findViewById(R.id.text_subject);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }

        void bind(final siswa student) {
            nameTextView.setText(student.getName());
            nimTextView.setText(student.getNim());
            ipkTextView.setText(String.valueOf(student.getIpk()));
            subjectTextView.setText(student.getSubject());

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStudentDeleteListener.onStudentDelete(student.getId());
                }
            });
        }
    }
}
