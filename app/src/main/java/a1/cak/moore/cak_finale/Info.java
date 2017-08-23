package a1.cak.moore.cak_finale;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;


public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Log.d("TKT_info","onCreate");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d("TKT_info","onOptionMenu");
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.d("TKT_info","packPressed");
        super.onBackPressed();
    }
}
