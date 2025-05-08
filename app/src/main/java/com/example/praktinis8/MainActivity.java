package com.example.praktinis8;



import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        ItemRepository.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Menu");
                    } else if (position == 1) {
                        tab.setText("Overview");
                    } else if (position == 2) {
                        tab.setText("Insight");
                    }
                }).attach();



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = adapter.getFragment(tab.getPosition());

                if (fragment instanceof InsightFragment) {
                    ((InsightFragment) fragment).refreshInsight();
                } else if (fragment instanceof OverViewFragment) {
                    ((OverViewFragment) fragment).refreshOverview();
                } else if (fragment instanceof MenuFragment) {

                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_next_day) {
            showStartNextDayDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showStartNextDayDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Start New Day")
                .setMessage("This will archive today's sales and reset the counter. Proceed?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    ItemRepository.startNextDay();
                    Toast.makeText(this, "New day started!", Toast.LENGTH_SHORT).show();


                    Fragment insight = adapter.getFragment(2);
                    Fragment overview = adapter.getFragment(1);

                    if (insight instanceof InsightFragment) {
                        ((InsightFragment) insight).refreshInsight();
                    }
                    if (overview != null) {
                        ((OverViewFragment) overview).refreshOverview();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void refreshFragments() {
        Fragment overview = adapter.getFragment(1);
        Fragment insight = adapter.getFragment(2);

        if (overview instanceof OverViewFragment) {
            ((OverViewFragment) overview).refreshOverview();
        }
        if (insight instanceof InsightFragment) {
            ((InsightFragment) insight).refreshInsight();
        }
    }
    public void reloadFragment(int position) {
        Fragment fragment = adapter.getFragment(position);
        getSupportFragmentManager()
                .beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commitNow();
    }

    }
