package a1.cak.moore.cak_finale;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ZoomControls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class Create extends AppCompatActivity {



    View undo;
    public static int currentColor, color;
    Stack<Button> stacie = new Stack<Button>();
    Stack<Integer>prevColors = new Stack<Integer>();
    public static int flag=0;
    ZoomControls zoom;//##
    public static int howManyZooms=0;//##
    ScrollView scrollView;
    String TAG="negro";
    String fileName = "pattern";
    Context context;
    Menu MENU;
    private View menuView;

    HashMap<Integer, String> state = new HashMap<Integer, String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuView =getLayoutInflater().inflate(R.layout.activity_create, null);
        Log.d("TKT_create","onCreate");
        setContentView(R.layout.activity_create);
        zoom = (ZoomControls)findViewById(R.id.zoomControls);
        currentColor= ContextCompat.getColor(this, R.color.negro);
        scrollView=(ScrollView)findViewById(R.id.scrollVista);
        context = this;
        //Log.d("TKT_create","path: "+context.getFilesDir());
        //Log.d("TKT_create","getFileDir: "+context.getFilesDir().toString());
        //Log.d("TKT_create","getAbs: "+context.getFilesDir().getAbsolutePath());

        //relativeLayout=(RelativeLayout)findViewById(R.id.createXML);//##


        ///////////////////////
        zoom.setOnZoomInClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("TKT_create","zoomin clicked");
                float x = scrollView.getScaleX();
                float y = scrollView.getScaleY();

                if(howManyZooms<5)
                {
                    Log.d("TKT_create","zoomIn howManyZooms");
                    scrollView.setScaleX((float) (x + 0.2));
                    scrollView.setScaleY((float) (y + 0.2));
                    howManyZooms++;
                }
            }
        });

        zoom.setOnZoomOutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("TKT_create","zoomout clicked");

                float x = scrollView.getScaleX();
                float y = scrollView.getScaleY();
                if(howManyZooms>0) {
                    Log.d("TKT_create","zoomout howManyZooms");
                    scrollView.setScaleX((float) (x - 0.2));
                    scrollView.setScaleY((float) (y - 0.2));
                    howManyZooms--;
                }
            }
        });
    }

    public int getColor(String tag)
    {

        switch (tag)
        {
            case "white":
            {
                return  ContextCompat.getColor(this,R.color.blanco);
            }
            case "negro":
            {
                return  ContextCompat.getColor(this,R.color.negro);
            }
            case "lightGray":
            {
                return  ContextCompat.getColor(this,R.color.lightGray);

            }
            case "darkGray":
            {
                return  ContextCompat.getColor(this,R.color.darkGray);
            }
            case "brown":
            {
                return  ContextCompat.getColor(this,R.color.brown);
            }
            case "mochaBrown":
            {
                return  ContextCompat.getColor(this,R.color.mochaBrown);
            }
            case "amarillo":
            {
                return  ContextCompat.getColor(this,R.color.amarillo);
            }
            case "orange":
            {
                return  ContextCompat.getColor(this,R.color.orange);
            }
            case "rojo":
            {
                return  ContextCompat.getColor(this,R.color.rojo);
            }
            case "royal":
            {
                return  ContextCompat.getColor(this,R.color.royal);
            }
            case "seaBlue":
            {
                return  ContextCompat.getColor(this,R.color.seaBlue);
            }
            case "cyan":
            {
                return  ContextCompat.getColor(this,R.color.cyan);
            }
            case "turquoise":
            {
                return  ContextCompat.getColor(this,R.color.turquoise);
            }
            case "androidGreen":
            {
                return  ContextCompat.getColor(this,R.color.androidGreen);

            }
            case "grassGreen":
            {
                return  ContextCompat.getColor(this,R.color.grassGreen);
            }
            case "git":
            {
                return  ContextCompat.getColor(this,R.color.git);
            }
            case "fuchsiaPink":
            {
                return  ContextCompat.getColor(this,R.color.fuchsiaPink);

            }
            case "pink":
            {
                return  ContextCompat.getColor(this,R.color.pink);
            }
            case "oldPink":
            {
                return  ContextCompat.getColor(this,R.color.oldPink);
            }
            case "red":
            {
                return  ContextCompat.getColor(this,R.color.red);
            }
        }
        return  ContextCompat.getColor(this,R.color.blanco);
    }

    public void blackening(View v) {

        Log.d("TKT_create","blackening");
        Log.d("TKT_create","tag: "+v.getTag().toString());
        int prevColor = getColor(v.getTag().toString());
        Log.d("TKT_create","prevColor: "+prevColor);
        Log.d("TKT_create","currColor: "+currentColor);
        if(prevColor != currentColor)
        {//if they are different, color it
            Log.d("TKT_create","prev != curr");
            v.setBackgroundColor(currentColor);
            stacie.add((Button)v);
            prevColors.add(prevColor);
            v.setTag(TAG);
            state.put(v.getId(), TAG);
            Log.d("TKT_create","TAG: "+TAG);
        }
        else {//color it with prevColor
            Log.d("TKT_create","prev == curr");
            v.setBackgroundColor(ContextCompat.getColor(this, R.color.blanco));
            stacie.add((Button)v);
            prevColors.add(currentColor);
            v.setTag("white");
            state.put(v.getId(), "white");
        }
        //currentColor - color chosen by user
        /*
        Log.d("TKT_create","blackening");
        String t = (String) v.getTag();
        int white = ContextCompat.getColor(this, R.color.blanco);
        int backgroundColor = white;

        if (t.equalsIgnoreCase("white")) {
            if (white != currentColor)
            {
                v.setBackgroundColor(currentColor);
                //backgroundColor = currentColor;
            }
            else
            {
                v.setBackgroundColor(white);
                //backgroundColor = white;;
            }
            v.setTag("black");

        }
        else
        {
            if (currentColor == white)
            {
                v.setBackgroundColor(white);
                //backgroundColor = white;
            }
            else
            {
                v.setBackgroundColor(currentColor);
                //backgroundColor = currentColor;
            }

            v.setTag("white");


        }

        */

    }

    public void erase(MenuItem m)
    {//erase one
        Log.d("TKT_create","erase");
        currentColor=ContextCompat.getColor(this, R.color.blanco); //getResources().getColor(R.color.blanco);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("TKT_create","onCreateOptionMenu");
        getMenuInflater().inflate(R.menu.menu_create, menu);


        MENU = menu;
        return true;
    }

    public void clearAll(MenuItem m) {
        Log.d("TKT_create","clearAll");
        showDialog();


    }

    public void showDialog()
    {
        Log.d("TKT_create","showDialog");
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.clear_dialog);
        dialog.setCanceledOnTouchOutside(false);

        final Button yes = (Button)dialog.findViewById(R.id.hellYeah);
        final Button no = (Button)dialog.findViewById(R.id.heavensNo);

        yes.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                saveDialog();
                cleanSlate();
            }
        });

        no.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                cleanSlate();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    public void saveDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.file_naming);
        dialog.setCanceledOnTouchOutside(false);
        final Button save = (Button)dialog.findViewById(R.id.saveMe);
        final Button cancel = (Button)dialog.findViewById(R.id.cancel);
        final EditText patternName = (EditText)dialog.findViewById(R.id.patternNameText);
        //patternName.setText(fileName+i);

        cancel.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                if(patternName.getText().length() == 0)
                    Toast.makeText(Create.this, R.string.ChooseName, Toast.LENGTH_LONG).show();
                else
                {
                    writeToFile(patternName.getText().toString());
                    Log.d("TKT_create", "quit try");
                    //cleanSlate();
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }

    public void writeToFile(String fileName)
    {
       //// TODO: 8/21/2017 what if name is taken
        File file = new File(context.getFilesDir().toString(), fileName);
        try
        {
            file.createNewFile();
            Log.d("TKT_create", "writeToFile try");
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));//context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(state);
            outputStream.flush();
            outputStream.close();

        }
        catch (IOException e)
        {
            Log.d("TKT_create", "writeToFile exception");
            e.printStackTrace();
        }
    }
    public void cleanSlate()
    {
        Log.d("TKT_create","cleanSlate");
        Iterator it = state.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            Log.d("TKT_create","id: "+pair.getKey());
            Button temp = (Button)findViewById((Integer)pair.getKey());
            temp.setBackgroundColor(ContextCompat.getColor(this, R.color.blanco));
            temp.setTag("white");
        }
        state.clear();

    }
    public void save()
    {
        Log.d("TKT_create","save");
        saveDialog();
    }

    public void openFile()
    {
        Log.d("TKT_create","openFile");
        /* TODO: 8/21/2017 load all saved files from folder (maybe add a flag before files so system'll distinguish between saved file and files that were already there.
            put those files in the save icon dropdown menu
            also, after saving, file must be added to dropdown menu - sharedpref with fileNames?
        */

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.open_file);
        final ListView listView = (ListView) dialog.findViewById(R.id.listView);
        ArrayList<String>listItem = new ArrayList<String>(Arrays.asList(fileList()));
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_list_view, listItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cleanSlate();
                String fileName = listView.getItemAtPosition(position).toString();
                Log.d("TKT_create","fileName: "+fileName);
                getFileFromDatabase(fileName);
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    public void getFileFromDatabase(String fileName)
    {
        ObjectInputStream objectInputStream;
        File file = new File(getFilesDir(), fileName);
        try
        {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            state = (HashMap<Integer, String>)objectInputStream.readObject();
            displayFile();
        }
        catch(Exception e)
        {
            Log.d("TKT_create","halt&catch file :/");
            e.printStackTrace();
        }
    }

    public void displayFile()//HashMap<Integer, String> chosenPattern)
    {
        Log.d("TKT_create","isMT: "+state.isEmpty());
        Iterator it = state.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            Button temp = (Button)findViewById((Integer)pair.getKey());
            temp.setBackgroundColor(getColor((String)pair.getValue()));
            temp.setTag((String)pair.getValue());
            Log.d("TKT_create","temp.tag " +(Integer)pair.getKey());
            Log.d("TKT_create","temp.tag " +(String)pair.getValue());
        }
    }

    public void undo(MenuItem m)
    {
        Log.d("TKT_create","undo");
        if(!stacie.isEmpty()) {
            Button remove = stacie.pop();
            Log.d("TKT_create","removeTagB4: "+remove.getTag().toString());
            int prevCol = prevColors.pop();
            remove.setBackgroundColor(prevCol);
            remove.setTag(getTag(prevCol));
            Log.d("TKT_create","getTagFunc: "+getTag(prevCol));
            Log.d("TKT_create","removeTagAfter: "+remove.getTag().toString());
        }

    }

    public String getTag(int prevCol)
    {
        switch (prevCol)
        {
            case R.color.blanco:
            {
                return  "white";
            }
            case R.color.negro:
            {
                return "negro";
            }
            case R.color.lightGray:
            {

                return "lightGray";

            }
            case R.color.darkGray:
            {
                return  "darkGray";
            }
            case R.color.brown:
            {
                return  "brown";
            }
            case R.color.mochaBrown:
            {
                return "mochaBrown";
            }
            case R.color.amarillo:
            {
                return  "amarillo";
            }
            case R.color.orange:
            {
                return  "orange";
            }
            case R.color.rojo:
            {
                return  "rojo";
            }
            case R.color.royal:
            {
                return  "royal";
            }
            case R.color.seaBlue:
            {
                return  "seaBlue";
            }
            case R.color.cyan:
            {
                return "cyan";
            }
            case R.color.turquoise:
            {
                return  "turquoise";
            }
            case R.color.androidGreen:
            {
                return  "androidGreen";

            }
            case R.color.grassGreen:
            {
                return  "grassGreen";
            }
            case R.color.git:
            {
                return  "git";
            }
            case R.color.fuchsiaPink:
            {
                return  "fuchsiaPink";

            }
            case R.color.pink:
            {
                return  "pink";
            }
            case R.color.oldPink:
            {
                return  "oldPink";
            }
            case R.color.red:
            {
                return  "red";
            }
        }
        return  "white";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("TKT_create","onOptionItemSelected");
        flag=0;
        int id = item.getItemId();
        Log.d("TKT_create","id: "+id);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.save)
        {
            save();
        }
        if(id == R.id.open)
        {
            openFile();
        }
        if(id == R.id.newFile)
        {
            cleanSlate();
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeColor(MenuItem v)
    {

        // /*
        switch (v.getItemId()) {
            case R.id.cWhite: {
                currentColor = ContextCompat.getColor(this,R.color.blanco);
                Log.d("TKT_create","white");
                TAG="white";
                break;
            }
            case R.id.cBlack: {
                currentColor = ContextCompat.getColor(this,R.color.negro);
                Log.d("TKT_create","negro");
                TAG="negro";
                break;
            }
            case R.id.cLightGray: {
                currentColor = ContextCompat.getColor(this,R.color.lightGray);
                Log.d("TKT_create","lightGray");
                TAG="lightGray";
                break;
            }
            case R.id.cDarkGray: {
                currentColor = ContextCompat.getColor(this,R.color.darkGray);
                Log.d("TKT_create","darkGray");
                TAG="darkGray";
                break;
            }
            case R.id.cBrown: {
                currentColor = ContextCompat.getColor(this,R.color.brown);
                Log.d("TKT_create","brown");
                TAG="brown";
                break;
            }
            case R.id.cLightBrown: {
                currentColor = ContextCompat.getColor(this,R.color.mochaBrown);
                Log.d("TKT_create","mochaBrown");
                TAG="mochaBrown";
                break;
            }
            case R.id.cYellow: {
                currentColor = ContextCompat.getColor(this,R.color.amarillo);
                Log.d("TKT_create","amarillo");
                TAG="amarillo";
                break;
            }
            case R.id.cOrange: {
                currentColor = ContextCompat.getColor(this,R.color.orange);
                Log.d("TKT_create","orange");
                TAG="orange";
                break;
            }
            case R.id.cRojo: {
                currentColor = ContextCompat.getColor(this,R.color.rojo);
                Log.d("TKT_create","rojo");
                TAG="rojo";
                break;
            }
            case R.id.cRed: {
                currentColor = ContextCompat.getColor(this,R.color.red);
                Log.d("TKT_create","red");
                TAG="red";
                break;
            }
            case R.id.cBlue: {
                currentColor = ContextCompat.getColor(this,R.color.royal);
                Log.d("TKT_create","royal");
                TAG="royal";
                break;
            }
            case R.id.cLightBlue: {
                currentColor = ContextCompat.getColor(this,R.color.seaBlue);
                Log.d("TKT_create","seaBlue");
                TAG="seaBlue";
                break;
            }
            case R.id.cCyan: {
                currentColor = ContextCompat.getColor(this,R.color.cyan);
                Log.d("TKT_create","cyan");
                TAG="cyan";
                break;
            }
            case R.id.cTurq: {
                currentColor = ContextCompat.getColor(this,R.color.turquoise);
                Log.d("TKT_create","turqouise");
                TAG="turquoise";
                break;
            }
            case R.id.cAndroidGreen: {
                currentColor = ContextCompat.getColor(this,R.color.androidGreen);
                Log.d("TKT_create","androidGreen");
                TAG="androidGreen";
                break;
            }
            case R.id.cGrassGreen: {
                currentColor = ContextCompat.getColor(this,R.color.grassGreen);
                Log.d("TKT_create","grassGreen");
                TAG="grassGreed";
                break;
            }
            case R.id.cGit: {
                currentColor = ContextCompat.getColor(this,R.color.git);
                Log.d("TKT_create","git");
                TAG="git";
                break;
            }
            case R.id.cFuchsiaPink: {
                currentColor = ContextCompat.getColor(this,R.color.fuchsiaPink);
                Log.d("TKT_create","fuchsia");
                TAG="fuchsia";
                break;
            }
            case R.id.cPink: {
                currentColor = ContextCompat.getColor(this,R.color.pink);
                Log.d("TKT_create","pink");
                TAG="pink";
                break;
            }
            case R.id.cOldPink: {
                currentColor = ContextCompat.getColor(this,R.color.oldPink);
                Log.d("TKT_create","oldPink");
                TAG="oldPink";
                break;
            }

        }
        }
}
