package a1.cak.moore.cak_finale;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ZoomControls;

public class DefaultPatternsScreen extends AppCompatActivity {

    ViewPager viewPager;
    ZoomControls zoom;
    RelativeLayout relativeLayout;
    CustomSwipeAdapter my_adapter;
    public static int howManyZooms=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_default_patterns_screen);
        relativeLayout=(RelativeLayout)findViewById(R.id.default_patternsXML);
        zoom = new ZoomControls(DefaultPatternsScreen.this);
        Log.d("TKT_defPatScreen","onCreate");
        viewPager = (ViewPager) findViewById(R.id.viewPagerOfDefaultPatternScreen);
        my_adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(my_adapter);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);


        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.bottomMargin = 10;

        zoom.setLayoutParams(params);

        relativeLayout.addView(zoom);

        zoom.setOnZoomInClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //
                Log.d("TKT_defPatScreen","zoomIn");
                float x = viewPager.getScaleX();
                float y = viewPager.getScaleY();
                if(howManyZooms<5)
                {
                    Log.d("TKT_defPatScreen","zoomIn howManyZooms");
                    viewPager.setScaleX((float) (x + 0.2));
                    viewPager.setScaleY((float) (y + 0.2));
                    howManyZooms++;
                }
            }
        });

        zoom.setOnZoomOutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //

                Log.d("TKT_defPatScreen","zoomOut");
                float x = viewPager.getScaleX();
                float y = viewPager.getScaleY();
                if(howManyZooms!=0 && howManyZooms>-5)
                {
                    Log.d("TKT_defPatScreen","zoomOut howManyZooms");
                    viewPager.setScaleX((float) (x - 0.2));
                    viewPager.setScaleY((float) (y - 0.2));
                    howManyZooms--;
                }
            }
        });





    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("TKT_defPatScreen","onOptionItemSelect");
        return super.onOptionsItemSelected(item);
    }

}
