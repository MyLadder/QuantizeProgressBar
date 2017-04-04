package in.adzlab.quantizeprogress;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import travel.ithaka.quantizeprogressbar.QuantizeProgressBar;

public class MainActivity extends AppCompatActivity {
    QuantizeProgressBar quantizeProgressBar;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantizeProgressBar = (QuantizeProgressBar) findViewById(R.id.progressBar);
        quantizeProgressBar.setCount(10);
        quantizeProgressBar.setCurrent(0);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                while (i < 10){
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("rr", String.valueOf(i));
                                quantizeProgressBar.setCurrent(i);
                                i++;
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }.execute();
    }
}
