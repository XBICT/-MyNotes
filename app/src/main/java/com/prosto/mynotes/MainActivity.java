package com.prosto.mynotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   // File notes = new File( PATH);
  //  File title = new File( PATH_TITLES);
    private static final String PATH = "data.txt";
    private static final String PATH_TITLES = "title.txt";
    private static String LOG_TAG = "CardViewActivity";
    public static final int LAYOUT = R.layout.activity_main;
    public Drawer result;
    public Intent intent;
    public Toolbar toolbar;
    public TextView noteText;
    public TextView noteTitle;
    public CardView cardView;

     //CardView
    private RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    FileOutputStream outputStream;
    FileInputStream inputStream;

    private ArrayList<String> arrayNotesList = new ArrayList<>();
    private ArrayList<String> arrayTitlesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        cardView = (CardView)findViewById(R.id.card_view);

        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        noteText = (TextView)findViewById(R.id.noteText);
        noteTitle = (TextView)findViewById(R.id.noteTitle);
        noteText.setText(getIntent().getStringExtra("note"));
        noteTitle.setText(getIntent().getStringExtra("title"));

        readFromFile();
        initList();
        addNote();
        noteCheck();

        initToolbar();
        initNavigation(toolbar);
    }

    public void noteCheck(){
        TextView noNotes = (TextView)findViewById(R.id.noNotes);
        TextView noNotes2 = (TextView)findViewById(R.id.noNotes2);
        TextView noNotes3 = (TextView)findViewById(R.id.noNotes3);
        if(arrayNotesList.size()!=0){
            noNotes.setText("");
            noNotes2.setText("");
            noNotes3.setText("");}
        else{
            noNotes.setText(getString(R.string.noNotes));
            noNotes2.setText(getString(R.string.noNotes2));
            noNotes3.setText(getString(R.string.noNotes3));
        }
    }
    private void readFromFile() {
        BufferedReader bufferedReader = null;
        String line;

        try {
            inputStream = openFileInput(PATH_TITLES);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("")) {
                    arrayTitlesList.add(line);
                }
            }inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream = openFileInput(PATH);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("")) {
                    arrayNotesList.add(line);
                }
            }inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addNote() {
        if(!noteText.getText().equals("")){
            arrayTitlesList.add(noteTitle.getText().toString());
            arrayNotesList.add(noteText.getText().toString());
            writeToFile();
            initList();
        }
    }
    private void writeToFile() {
        try {
            outputStream = openFileOutput(PATH_TITLES, Context.MODE_PRIVATE);
            outputStream.write("".getBytes());
            outputStream.close();

            outputStream = openFileOutput(PATH_TITLES, Context.MODE_APPEND);
            for (int i = 0; i<arrayNotesList.size();i++){
                outputStream.write(arrayTitlesList.get(i).getBytes());
                outputStream.write(("\n").getBytes());
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream = openFileOutput(PATH, Context.MODE_PRIVATE);
            outputStream.write("".getBytes());
            outputStream.close();
            outputStream = openFileOutput(PATH, Context.MODE_APPEND);
            for (int i = 0; i<arrayNotesList.size();i++){
                outputStream.write(arrayNotesList.get(i).getBytes());
                outputStream.write(("\n").getBytes());
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initList() {
        getDataSet();
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                noteText.setText(arrayNotesList.get(position));
                noteTitle.setText(arrayTitlesList.get(position));
                intent.putExtra("note", noteText.getText().toString());
                intent.putExtra("title", noteTitle.getText().toString());
                startActivity(intent);
                arrayTitlesList.remove(position);
                arrayNotesList.remove(position);
                writeToFile();
            }
        });
    }

    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        if(arrayNotesList.size()!=0){
        for (int index = 0; index < arrayNotesList.size(); index++) {
            DataObject obj = new DataObject(arrayTitlesList.get(index),
                    arrayNotesList.get(index));
            results.add(index, obj);
        }}
        return results;
    }

    public void deleteElement(){
        try {
            outputStream = openFileOutput(PATH, Context.MODE_PRIVATE);
            outputStream.write("".getBytes());
            outputStream.close();
            outputStream = openFileOutput(PATH, Context.MODE_APPEND);
            for (int i = 0; i<arrayNotesList.size();i++){
                outputStream.write(arrayNotesList.get(i).getBytes());
                outputStream.write(("\n").getBytes());
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        noteCheck();

    }
    public void cleanFile(View view){
        try {
            outputStream = openFileOutput(PATH, Context.MODE_PRIVATE);
            outputStream.write("".getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //toolbar
    private void initToolbar() {
        toolbar.findViewById(R.id.toolbar);
        if (toolbar != null) {
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

    public void onclick(View view) {
        Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
        startActivity(intent);
    }
    private void initNavigation(final Toolbar toolbar){
        final AccountHeader headerResult = initNavHeader();
        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withDisplayBelowStatusBar(false)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.notes).withIdentifier(1).withIcon(FontAwesome.Icon.faw_sticky_note),
                        new PrimaryDrawerItem().withName(R.string.alarm).withIdentifier(2).withIcon(GoogleMaterial.Icon.gmd_alarm),
                        new PrimaryDrawerItem().withName(R.string.reminder).withIdentifier(3).withIcon(FontAwesome.Icon.faw_calendar),
                        new DividerDrawerItem(),
                      // new SecondaryDrawerItem().withName(R.string.archive).withIdentifier(4),
                        new SecondaryDrawerItem().withName(R.string.info).withIdentifier(5),
                        new SecondaryDrawerItem().withName(R.string.settings).withIdentifier(6))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {
                        switch (result.getCurrentSelection()){
                            case 1:
                                intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case 2:
                                Toast toast = Toast.makeText(getApplicationContext(),  "Coming soon", Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            case 3:
                                toast = Toast.makeText(getApplicationContext(), "Coming soon", Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            case 6:
                                intent = new Intent(MainActivity.this, SettingsActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                })
                .build();
    }
    private AccountHeader initNavHeader() {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.headerbgn)
                .addProfiles(
                        new ProfileDrawerItem().withName("Rostyk Boyko").withEmail("rosstyk@gmail.com").withIcon(getResources().getDrawable(R.drawable.face))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        return headerResult;
    }
    @Override
    public void onBackPressed() {
        if(result != null && result.isDrawerOpen()){
            result.closeDrawer();
        }else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
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
