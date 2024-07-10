package com.example.uassql;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements siswaAdapter.OnStudentDeleteListener {

    private dbHelper databaseHelper;
    private RecyclerView recyclerView;
    private siswaAdapter studentAdapter;
    private List<siswa> studentList;

    private EditText nameEditText, nimEditText, ipkEditText, subjectEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new dbHelper(this);

        nameEditText = findViewById(R.id.edit_name);
        nimEditText = findViewById(R.id.edit_nim);
        ipkEditText = findViewById(R.id.edit_ipk);
        subjectEditText = findViewById(R.id.edit_subject);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {
        studentList = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllStudents();
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_NAME));
                String nim = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_NIM));
                double ipk = cursor.getDouble(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_IPK));
                String subject = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_SUBJECT));

                studentList.add(new siswa(id, name, nim, ipk, subject));
            } while (cursor.moveToNext());
        }
        cursor.close();

        studentAdapter = new siswaAdapter(studentList, this);
        recyclerView.setAdapter(studentAdapter);
    }

    public void addStudent(View view) {
        String name = nameEditText.getText().toString();
        String nim = nimEditText.getText().toString();
        double ipk = Double.parseDouble(ipkEditText.getText().toString());
        String subject = subjectEditText.getText().toString();

        if (name.isEmpty() || nim.isEmpty() || ipkEditText.getText().toString().isEmpty() || subject.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long id = databaseHelper.addStudent(name, nim, ipk, subject);
        if (id != -1) {
            studentList.add(new siswa(id, name, nim, ipk, subject));
            studentAdapter.notifyItemInserted(studentList.size() - 1);
            clearFields();
        } else {
            Toast.makeText(this, "Error adding student", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        nameEditText.setText("");
        nimEditText.setText("");
        ipkEditText.setText("");
        subjectEditText.setText("");
    }

    @Override
    public void onStudentDelete(long id) {
        databaseHelper.deleteStudent(id);
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getId() == id) {
                studentList.remove(i);
                studentAdapter.notifyItemRemoved(i);
                break;
            }
        }
    }
}
