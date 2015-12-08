package com.prosto.mynotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String PATH = "data.txt";
    public static final int LAYOUT = R.layout.activity_main;
    public Drawer result;
    public Intent intent;
    public Toolbar toolbar;
    public TextView noteText;

    int size;
    int counter = 0;
    //CardView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";



    FileOutputStream outputStream;
    FileInputStream inputStream;

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        noteText = (TextView)findViewById(R.id.noteText);
        noteText.setText(getIntent().getStringExtra("note"));

        readFromFile();
        initList();
        addNote();
    //    noteCheck();
        notesHide();
        initToolbar();
        initNavigation(toolbar);
    }

    public void notesHide(){
        TextView noNotes = (TextView)findViewById(R.id.noNotes);
        TextView noNotes2 = (TextView)findViewById(R.id.noNotes2);
        TextView noNotes3 = (TextView)findViewById(R.id.noNotes3);
            noNotes.setText("");
            noNotes2.setText("");
            noNotes3.setText("");
    }
    public void noteCheck(){
        TextView noNotes = (TextView)findViewById(R.id.noNotes);
        TextView noNotes2 = (TextView)findViewById(R.id.noNotes2);
        TextView noNotes3 = (TextView)findViewById(R.id.noNotes3);
        if(arrayList.size()!=0){
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
        counter = 0;
        BufferedReader bufferedReader = null;
        String line;
        try {
            inputStream = openFileInput(PATH);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("")) {
                    arrayList.add(line);
                }
            }inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initList() {
        final ListView listView = (ListView) findViewById(R.id.listView);
        adapterView = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrayList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,  long id) {
                view.setSelected(true);
                arrayList.remove(position);
                deleteElement();
                initList();
                listView.setAdapter(adapterView);
            }
        });
        setTitle(""+arrayList.size());
        getDataSet();
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
      //  listView.setAdapter(adapterView);
    }

    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        if(arrayList.size()!=0){
        for (int index = 0; index < arrayList.size(); index++) {
        // setTitle("в циклі " + arrayList.size());
            DataObject obj = new DataObject("note" + (index+1),
                  arrayList.get(index));
            results.add(index, obj);
        }}
        return results;
    }

    public void addNote() {
        if(!noteText.getText().equals("")){
            arrayList.add(noteText.getText().toString());
            adapterView.notifyDataSetChanged();
            writeToFile();
            initList();
        }
    }
    public void deleteElement(){
        try {
            outputStream = openFileOutput(PATH, Context.MODE_PRIVATE);
            outputStream.write("".getBytes());
            outputStream.close();
            outputStream = openFileOutput(PATH, Context.MODE_APPEND);
            for (int i = 0; i<arrayList.size();i++){
                outputStream.write(arrayList.get(i).getBytes());
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
    private void writeToFile() {
        counter = arrayList.size() - 1;
        try {
            outputStream = openFileOutput(PATH, Context.MODE_PRIVATE);
            outputStream.write("".getBytes());
            outputStream.close();
            outputStream = openFileOutput(PATH, Context.MODE_APPEND);
            for (int i = 0; i<arrayList.size();i++){
                outputStream.write(arrayList.get(i).getBytes());
                outputStream.write(("\n").getBytes());
            }
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
                        new SecondaryDrawerItem().withName(R.string.archive).withIdentifier(4),
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
