package a1.cak.moore.cak_finale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MenuScreen extends AppCompatActivity {

    //InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        Log.d("TKT_menuScreen","onCreate");
        //mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        //ca-app-pub-3940256099942544/1033173712 -- test
        //ca-app-pub-3046870668985141/9109844715 -- mine

/*
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                begin();
            }
        });

        requestNewInterstitial();
        */
    }

    /*
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

*/
    public void begin()
    {
        //this is not the ideal way to have a splash, but that's the best for now...
        Log.d("TKT_menuScreen","begin");
        setContentView(R.layout.splash_screen);
        Intent intent = new Intent(this, Create.class);
        startActivity(intent);;
    }

    public void goToCreateScreen(View v)
    {
        Log.d("TKT_menuScreen","goToCreateScreen");

        //if (mInterstitialAd.isLoaded()) {
        //    mInterstitialAd.show();
       // }
      //  else {
            begin();
     //   }

    }
    public void goToDefaultPatternGallery(View v)
    {
        Log.d("TKT_menuScreen","goToDefaultGallery");
        Intent intent=new Intent(this, DefaultPatternsScreen.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("TKT_create","onCreateOptionMenu");
        getMenuInflater().inflate(R.menu.menu_menu_screen, menu);
        return true;
    }

    public void HowTo()
    {
        Log.d("TKT_menuScreen","howTo");
        Intent intent=new Intent(this, HowTo.class);
        startActivity(intent);
        //setContentView(R.layout.activity_how_to);
    }
    public void Info()
    {
        Log.d("TKT_menuScreen","info");
        Intent intent=new Intent(this, Info.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        setContentView(R.layout.activity_menu_screen);
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.info)
        {
            Info();
        }
        if(id == R.id.help)
        {
            HowTo();
        }

        return super.onOptionsItemSelected(item);

    }
}
