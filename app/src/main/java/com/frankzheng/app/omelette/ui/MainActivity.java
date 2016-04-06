package com.frankzheng.app.omelette.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.log.ILogger;
import com.frankzheng.app.omelette.log.LogArchiver;
import com.frankzheng.app.omelette.log.LoggerFactory;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ILogger logger = LoggerFactory.getInstance().getLogger(TAG);

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.pager)
    ViewPager viewPager;

    @Bind(R.id.tabs)
    TabLayout tabs;

    PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.i("onCreate");

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        pagerAdapter = new PagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        tabs.setupWithViewPager(viewPager);

//        if (logger.logsAreSaved()) {
//            List<LogRecord> logs = logger.getAllRecords();
//            for (LogRecord record : logs) {
//                Log.i(TAG, record.toString());
//            }
//        }
    }


    private void uploadLog() {
        LogArchiver.ArchiveResult result = LogArchiver.getInstance().archive();
        Log.i(TAG, result == null ? "null" : result.toString());

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
        } else if (id == R.id.action_upload_log) {
            uploadLog();
        }
        return super.onOptionsItemSelected(item);
    }
}
