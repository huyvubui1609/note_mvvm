package com.example.note_mvvm.View.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.note_mvvm.R;
import com.example.note_mvvm.View.Dialog.AddNoteDialog;

import java.util.Objects;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.note_mvvm.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.note_mvvm.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.note_mvvm.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.note_mvvm.EXTRA_PRIORITY";

    private EditText editTexttitle;
    private EditText editTextdescription;
    private NumberPicker numberPickerpriority;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTexttitle = findViewById(R.id.edt_title);
        editTextdescription = findViewById(R.id.edt_description);
        numberPickerpriority = findViewById(R.id.number_picker_priority);

        numberPickerpriority.setMinValue(1);
        numberPickerpriority.setMaxValue(10);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editTexttitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextdescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerpriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = editTexttitle.getText().toString().trim();
        String description = editTextdescription.getText().toString().trim();
        int priority = numberPickerpriority.getValue();

        if (title.isEmpty() || description.isEmpty()) {
            new AddNoteDialog(AddEditNoteActivity.this);
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
}
