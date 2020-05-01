package com.project.sagar.loctrack;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v4.view.*;
import android.support.design.widget.*;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.widget.*;
import android.view.*;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;


public class GeoFenceActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MyViewPager viewPager;
    CityFragment city;
    CustomFragment custom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_fence);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (MyViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.plus);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageResource(com.project.sagar.loctrack.R.drawable.tagtext);
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();

        ImageView itemIcon2= new ImageView(this);
        itemIcon2.setImageResource(com.project.sagar.loctrack.R.drawable.mapmarker);
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .attachTo(actionButton)
                .build();

        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                if(viewPager.getCurrentItem()==0){
                    custom.handlePlaceT();
                }
                else{
                    city.handlePlaceT();
                }
            }
        });

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {
                if(viewPager.getCurrentItem()==0){
                    custom.handlePlaceP();
                }
                else{
                    city.handlePlaceP();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            //case R.id.action_switch:
              //  return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(custom =new CustomFragment(), "RED PLACES");
        adapter.addFragment(city =new CityFragment(), "GREEN PLACES");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final java.util.List<android.support.v4.app.Fragment> mFragmentList = new java.util.ArrayList<>();
        private final java.util.List<String> mFragmentTitleList = new java.util.ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
