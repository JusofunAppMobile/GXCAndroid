package com.jusfoun.jusfouninquire.ui.activity;

import android.os.AsyncTask;
import android.widget.ScrollView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.widget.PullToRefreshBase;
import com.jusfoun.jusfouninquire.ui.widget.PullToRefreshScrollView;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/6.
 * Description
 */
public class ScrollRefreshActivity extends BaseActivity {

    private PullToRefreshScrollView scrollView;
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_scroll);
        scrollView= (PullToRefreshScrollView) findViewById(R.id.scrollView);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new GetDataTask().execute();
            }
        });
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            // Do some stuff here

            // Call onRefreshComplete when the list has been refreshed.
            scrollView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    @Override
    protected void initWidgetActions() {

    }
}
