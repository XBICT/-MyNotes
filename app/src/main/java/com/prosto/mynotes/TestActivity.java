package com.prosto.mynotes;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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


public class TestActivity extends AppCompatActivity{

    public Drawer result;
    public Intent intent;
    public Toolbar toolbar;
    public CardView cardView;
    public TextView cardText;

    final String LOG_TAG = "myLogs";
    final String FILENAME = "file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        cardView = (CardView) findViewById(R.id.cardView);
        cardText = (TextView) findViewById(R.id.cardText);

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
                    cardText.setText(str);
                }else {toolbar.setTitle("ok");
                    TextView space = (TextView) findViewById(R.id.space);
                    space.setPadding(10,10,756,10);
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


    private void initToolbar(){
        toolbar.findViewById(R.id.toolbar);
        toolbar.setTitle("CardView");
        if (toolbar!= null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
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
                                intent = new Intent(TestActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case 2:
                                intent = new Intent(TestActivity.this, NewNoteActivity.class);
                                startActivity(intent);
                                break;
                            case 3:
                                intent = new Intent(TestActivity.this, TestActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                })
                .build();
    }
    public void noteCreate(View view){
        Intent intent = new Intent(this, NewNoteActivity.class);
        startActivity(intent);

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
}
