package a1.cak.moore.cak_finale;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Moore on 9/24/2015.
 */
public class CustomSwipeAdapter extends PagerAdapter {



    private int [] images={R.drawable.final_superman, R.drawable.final_apple, R.drawable.final_bmw, R.drawable.final_ck, R.drawable.final_dd, R.drawable.final_droid, R.drawable.final_facebook, R.drawable.final_handicapped, R.drawable.final_i_heart_ny, R.drawable.final_il, R.drawable.final_low_battery, R.drawable.final_minion, R.drawable.final_nike, R.drawable.final_no_entry, R.drawable.final_no_smoking, R.drawable.final_tommy, R.drawable.final_wifi};
    private String [] patterns={"Superman","Apple","BMW","Calvin Klien","Dunkin' Donuts","Android","Facebook","Handicapped Sign","I <3 NY","Israel","Low Battery","Minion","Nike","No Entry","No Smoking","Tommy Hilfiger","WiFi"};//{R.string.superman, R.string.apple, R.string.bmw, R.string.ck, R.string.dd, R.string.droid, R.string.facebook, R.string.handicapped, R.string.iHeartNy, R.string.il, R.string.lowBattery, R.string.minion, R.string.nike, R.string.noEntry, R.string.noSmoking, R.string.tommy, R.string.wifi};
    private Context ctx;
    private LayoutInflater layoutInflater;
    //String s= getResources().getString(R.string.superman);
    //getstring(R.string,ID);
    //getResources().getString(R.string.mess_1);

    public CustomSwipeAdapter(Context co)
    {
        Log.d("TKT_swipeAdapter","customSwiperAdapter");
        ctx=co;
    }

    @Override
    public int getCount() {
        Log.d("TKT_swipeAdapter","getCount");
        return images.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.d("TKT_swipeAdapter","instantiateItem");
        layoutInflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.imageViewSwipe);
        TextView textView=(TextView)itemView.findViewById(R.id.textViewSwipe);
        imageView.setImageResource(images[position]);
        textView.setText(patterns[position] + "");
        container.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Log.d("TKT_swipeAdapter","isViewFromObject");
        return view==(LinearLayout)object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d("TKT_swipeAdapter","destroyItem");
        container.removeView((LinearLayout)object);
    }
}