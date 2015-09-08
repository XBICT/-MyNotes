package com.prosto.mynotes;

import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public Drawer result;
    public Intent intent;
    public Toolbar toolbar;
    public TextView cardText;

    final String LOG_TAG = "myLogs";
    final String FILENAME = "file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView cardText = (TextView) findViewById(R.id.cardText);
        cardText.setText(getIntent().getStringExtra("note"));

        readFile();
        initToolbar();
        initNavDrawer(toolbar);
    }

    void readFile() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, str);
                if (str.equals("")){
                    toolbar.setTitle("ok");
                    TextView noNotes = (TextView) findViewById(R.id.noNotes);
                    noNotes.setText(R.string.noNotes);
                    TextView noNotes2 = (TextView) findViewById(R.id.noNotes2);
                    noNotes2.setText(R.string.noNotes2);
                    TextView noNotes3 = (TextView) findViewById(R.id.noNotes3);
                    noNotes3.setText(R.string.noNotes3);
                    TextView space = (TextView) findViewById(R.id.space);
                    space.setPadding(0,0,0,0);
                    cardText.setPadding(0,0,0,0);
                }else{
                    TextView noNotes = (TextView) findViewById(R.id.noNotes);
                    noNotes.setText("");
                    TextView noNotes2 = (TextView) findViewById(R.id.noNotes2);
                    noNotes2.setText("");
                    TextView noNotes3 = (TextView) findViewById(R.id.noNotes3);
                    noNotes3.setText("");
                    TextView space = (TextView) findViewById(R.id.space);
                    space.setPadding(10,10,756,10);
                    TextView cardText = (TextView) findViewById(R.id.cardText);
                    cardText.setPadding(20, 10, 10, 10);
                    cardText.setText(str);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initNavDrawer(final Toolbar toolbar){
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
                                Toast toast = Toast.makeText(getApplicationContext(), "В розробці", Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                            case 3:
                                intent = new Intent(MainActivity.this, TestActivity.class);
                                startActivity(intent);
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
    private void initToolbar(){
        toolbar.findViewById(R.id.toolbar);
        if (toolbar!= null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
    @Override
    public void onBackPressed() {
        if(result != null && result.isDrawerOpen()){
            result.closeDrawer();
        }else{
            super.onBackPressed();
        }
    }
    public void noteCreate(View view){
        Intent intent = new Intent(this, NewNoteActivity.class);
        TextView cardText = (TextView) findViewById(R.id.cardText);
        intent.putExtra("note", cardText.getText());
        startActivity(intent);

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
