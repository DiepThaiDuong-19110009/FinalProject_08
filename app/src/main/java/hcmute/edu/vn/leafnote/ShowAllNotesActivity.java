package hcmute.edu.vn.leafnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.leafnote.adapter.CustomRowNoteAdapter;
import hcmute.edu.vn.leafnote.database.DatabaseConnection;
import hcmute.edu.vn.leafnote.entity.Note;
import hcmute.edu.vn.leafnote.model.ContactRowNote;

public class ShowAllNotesActivity extends AppCompatActivity {

    private ListView lvShowAllNote;
    TextView txtDone;
    EditText edtTimKiem;
    Button btnTim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_notes);
        AnhXa();

        List<Note> noteList = DatabaseConnection.getInstance(this).noteDao().getAll();
        NoteAdapter adapter = new NoteAdapter(this, R.layout.custom_note, noteList);
        lvShowAllNote.setAdapter(adapter);

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAllNotesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search=edtTimKiem.getText().toString().trim();
                if(TextUtils.isEmpty(search)){
                    Toast.makeText(ShowAllNotesActivity.this, "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.equals(search,"all")){
                    lvShowAllNote.setAdapter(adapter);
                }
                else {
                    List<Note> listSearch = DatabaseConnection.getInstance(ShowAllNotesActivity.this).noteDao().searchNote(search);
                    NoteAdapter adapter = new NoteAdapter(ShowAllNotesActivity.this, R.layout.custom_note, listSearch);
                    lvShowAllNote.setAdapter(adapter);
                }
            }
        });
    }

    private void AnhXa() {
        txtDone = (TextView) findViewById(R.id.txtDone);
        edtTimKiem = (EditText) findViewById(R.id.edtTimKiem);
        btnTim = (Button) findViewById(R.id.btnTim);
        lvShowAllNote = (ListView) findViewById(R.id.lvShowAllNote);
    }
}