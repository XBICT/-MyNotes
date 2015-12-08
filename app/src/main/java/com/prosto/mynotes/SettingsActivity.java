package com.prosto.mynotes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    public static final int LAYOUT = R.layout.settings_layout;
    private ArrayList<String> settingsArray = new ArrayList<>();
    private ArrayAdapter<String> settingsAdapter;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        settingsOptions();
        initToolbar();
    }

    private void initSetList(){
        settingsArray.add(getString(R.string.themeChoice));
        settingsArray.add(getString(R.string.langChoice));
    }

    public void onSettingsItemClick(){
        final ListView listView = (ListView) findViewById(R.id.listView);
        settingsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, settingsArray);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,  long id) {
                view.setSelected(true);

                listView.setAdapter(settingsAdapter);
            }
        });
        listView.setAdapter(settingsAdapter);
    }

    private void settingsOptions(){
        initSetList();
        final ListView listSettings = (ListView)findViewById(R.id.listSettings);
        final ArrayAdapter<String> settingsAdapter;
        settingsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, settingsArray);
        listSettings.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                Toast toast = Toast.makeText(getApplicationContext(),  settingsAdapter.getItem(position), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        listSettings.setAdapter(settingsAdapter);
    }

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
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });

    }
}
