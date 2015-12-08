package com.prosto.mynotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;



public class NewNoteActivity extends AppCompatActivity {
    public static final int LAYOUT = R.layout.new_note_layout;

    public Toolbar toolbar;
    private EditText noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        noteText = (EditText) findViewById(R.id.noteText);
        noteText.setText(getIntent().getStringExtra("note"));

        noteActivate();
        initToolbar();
    }
    private void noteActivate(){
        noteText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(noteText, 0);

            }
        }, 50);

        noteText.setFocusableInTouchMode(true);
        noteText.requestFocus();
    }

    public void onclick(View v) {
                Intent intent = new Intent(NewNoteActivity.this, MainActivity.class);
                intent.putExtra("note", noteText.getText().toString());
                startActivity(intent);
    }

    //toolbar
    private void initToolbar(){
        toolbar.findViewById(R.id.toolbar);
        if (toolbar!= null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        toolbar.setNavigationIcon(R.drawable.keyboard_backspace);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                onclick(v);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
