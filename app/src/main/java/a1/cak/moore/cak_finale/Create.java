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
import android.widget.TextView;
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

    /* TODO: 8/25/2017 problem with coloring and decoloring - state isn't mt when needs to be mt: happens when coloring with different colors, mb not though, seemed fine after second trial - check this
                        check state after eraser was chosen
                        check what happens when undo pressed and supposedly updates state and stacie
                        problem with brown and mocha brown
                        consider centering toast text
                        on actionBar backpress and backPress - if canvas is clean - don't show save changes dialog



     */

    /**
     * currentColor: the color that was chosen from the color box; default is black
     * stacie: the undo stack, stores buttons that were colored
     * prevColors: the color stack used by stacie, for every button that is changed, it's color is saved in prevColors before changing it to the new color
     * TAG: keeps track of button tags
     * state: hashmap that stores the current state of the gridView, and when user wants to save pattern, state is saved as a file in internal db, to be retrieved later when user wants to
     */
    public static int currentColor;
    Stack<Button> stacie = new Stack<Button>();
    Stack<Integer>prevColors = new Stack<Integer>();
    ZoomControls zoom;//##
    public static int howManyZooms=0;//##
    ScrollView scrollView;
    String TAG="negro";
    Context context;
    HashMap<Integer, String> state = new HashMap<Integer, String>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("TKT_create","onCreate");
        setContentView(R.layout.activity_create);
        zoom = (ZoomControls)findViewById(R.id.zoomControls);
        currentColor= ContextCompat.getColor(this, R.color.negro);
        scrollView=(ScrollView)findViewById(R.id.scrollVista);
        context = this;

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


    /**
     * This function handles coloring of boxes
     * @param v the button that was pressed and the one that will change its color
     *          if currentColor is different than the prevColor of that button, the the button will be colored and will be added to stacie and state
     *          if currentColor equals prevColor of that button, then button will be whitened and removed from state
     *          everytime currentColor is white, button will be removed from state
     */
    public void blackening(View v)
    {

        Log.d("TKT_create","blackening");
        int prevColor = getColor(v.getTag().toString());
        if(prevColor != currentColor)
        {//color it currentColor
            Log.d("TKT_create","prev != curr");
            v.setBackgroundColor(currentColor);
            stacie.add((Button)v);
            prevColors.add(prevColor);
            v.setTag(TAG);
            if(TAG != "white")
                state.put(v.getId(), TAG);
            else
                state.remove(v.getId());
            Log.d("TKT_create","isStateMT: "+state.isEmpty());
            Log.d("TKT_create","TAG: "+TAG);
        }
        else
            {//color it white
            Log.d("TKT_create","prev == curr");
            v.setBackgroundColor(ContextCompat.getColor(this, R.color.blanco));
            stacie.add((Button)v);
            prevColors.add(currentColor);
            v.setTag("white");
            state.remove(v.getId());
            Log.d("TKT_create","isMTState: "+state.isEmpty());
            //state.put(v.getId(), "white");
        }


    }

    //Menu item pressed------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        Log.d("TKT_create","onCreateOptionMenu");
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }


    /**
     * when menuItem eraser is being pressed, this means user wants to erase a single (maybe more) boxes, but not the entire canvas
     * @param m the eraser option in the garbage-can icon menuItem
     */
    public void erase(MenuItem m)
    {//erase one
        Log.d("TKT_create","erase");
        currentColor=ContextCompat.getColor(this, R.color.blanco); //getResources().getColor(R.color.blanco);
        TAG = "white";
        Toast.makeText(this, R.string.clearOne, Toast.LENGTH_SHORT).show();
    }

    /**
     * Clear the entire canvas
     * @param m the clearAll option in the garbage-can icon menuItem
     *          if state is empty, there is no need to call saveChanges and later cleanSlate
     *          if state isn't empty, show the saveChanges dialog, then cleanSlate
     *
     */
    public void clearAll(MenuItem m)
    {
        Log.d("TKT_create","clearAll");
        if(!state.isEmpty()) {
            Log.d("TKT_create","isnt empty");
            saveChanges(1);
        }
        else
            Log.d("TKT_create","empty");


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d("TKT_create","onOptionItemSelected");
        int id = item.getItemId();
        Log.d("TKT_create","id: "+id);

        switch (id)
        {
            case android.R.id.home:
            {
                /**
                 * when actionBar back arrow is pressed
                 */
                onBackPressed();
                Log.d("TKT_create","home");
                return true;
            }
            case R.id.save:
            {
                /**
                 * when save menuItem is pressed
                 * -if canvas is empty, just display toast
                 * -else, show saveDialog
                 */
                Log.d("TKT_create","save");
                if(state.isEmpty())
                {
                    Log.d("TKT_create","save: state isMT");
                    Toast.makeText(context, R.string.emptyPattern, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.d("TKT_create","save: state isntMT");
                    saveDialog(1);
                }


                return true;
            }
            case R.id.open:
            {
                /**
                 * open file from database and display it
                 */
                openFile();
                Log.d("TKT_create","open");
                return true;
            }
            case R.id.newFile:
            {
                /**
                 * create new file
                 * -if canvas is empty, display toast
                 * -else, go to saveChanges with flag 2, which means that **after** saving (or not), toast of 'new pattern' will be displayed
                 */
                if(!state.isEmpty())
                {
                    saveChanges(2);
                    Log.d("TKT_create","newFile not mt");

                }
                else
                {
                    Log.d("TKT_create","newFile is mt");
                    Toast.makeText(context, R.string.newFile, Toast.LENGTH_SHORT).show();
                }


                return true;
            }
            case R.id.undo:
            {
                /**
                 * undo using stacie
                 * -if stacie is empty, do nothing
                 * -else, go to undo
                 */
                Log.d("TKT_create","undo");
                if(!stacie.isEmpty())
                {

                    undo();
                    Log.d("TKT_create","undo isnt MT");
                }
                else
                    Log.d("TKT_create","undo is MT");

                return true;
            }

        }

        return false;
        //return super.onOptionsItemSelected(item);
    }

    /**
     * Choose color from colorBox
     * @param v the color that was chosen from the colorBox menuItem
     */
    public void changeColor(MenuItem v)
    {

        switch (v.getItemId()) {
            case R.id.cWhite: {
                currentColor = ContextCompat.getColor(this,R.color.blanco);
                Log.d("TKT_create","white");
                TAG="white";
                Toast.makeText(context, R.string.white, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cBlack: {
                currentColor = ContextCompat.getColor(this,R.color.negro);
                Log.d("TKT_create","negro");
                TAG="negro";
                Toast.makeText(context, R.string.black, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cLightGray: {
                currentColor = ContextCompat.getColor(this,R.color.lightGray);
                Log.d("TKT_create","lightGray");
                TAG="lightGray";
                Toast.makeText(context, R.string.lightGray, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cDarkGray: {
                currentColor = ContextCompat.getColor(this,R.color.darkGray);
                Log.d("TKT_create","darkGray");
                TAG="darkGray";
                Toast.makeText(context, R.string.darkGray, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cBrown: {
                currentColor = ContextCompat.getColor(this,R.color.brown);
                Log.d("TKT_create","brown");
                TAG="brown";
                Toast.makeText(context, R.string.darkBrown, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cLightBrown: {
                currentColor = ContextCompat.getColor(this,R.color.mochaBrown);
                Log.d("TKT_create","mochaBrown");
                TAG="mochaBrown";
                Toast.makeText(context, R.string.lightBrown, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cYellow: {
                currentColor = ContextCompat.getColor(this,R.color.amarillo);
                Log.d("TKT_create","amarillo");
                TAG="amarillo";
                Toast.makeText(context, R.string.yellow, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cOrange: {
                currentColor = ContextCompat.getColor(this,R.color.orange);
                Log.d("TKT_create","orange");
                TAG="orange";
                Toast.makeText(context, R.string.orange, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cRojo: {
                currentColor = ContextCompat.getColor(this,R.color.rojo);
                Log.d("TKT_create","rojo");
                TAG="rojo";
                Toast.makeText(context, R.string.burgundy, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cRed: {
                currentColor = ContextCompat.getColor(this,R.color.red);
                Log.d("TKT_create","red");
                TAG="red";
                Toast.makeText(context, R.string.red, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cBlue: {
                currentColor = ContextCompat.getColor(this,R.color.royal);
                Log.d("TKT_create","royal");
                TAG="royal";
                Toast.makeText(context, R.string.royal, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cLightBlue: {
                currentColor = ContextCompat.getColor(this,R.color.seaBlue);
                Log.d("TKT_create","seaBlue");
                TAG="seaBlue";
                Toast.makeText(context, R.string.lightBlue, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cCyan: {
                currentColor = ContextCompat.getColor(this,R.color.cyan);
                Log.d("TKT_create","cyan");
                TAG="cyan";
                Toast.makeText(context, R.string.cyan, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cTurq: {
                currentColor = ContextCompat.getColor(this,R.color.turquoise);
                Log.d("TKT_create","turqouise");
                TAG="turquoise";
                Toast.makeText(context, R.string.turq, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cAndroidGreen: {
                currentColor = ContextCompat.getColor(this,R.color.androidGreen);
                Log.d("TKT_create","androidGreen");
                TAG="androidGreen";
                Toast.makeText(context, R.string.darkGreen, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cGrassGreen: {
                currentColor = ContextCompat.getColor(this,R.color.grassGreen);
                Log.d("TKT_create","grassGreen");
                TAG="grassGreed";
                Toast.makeText(context, R.string.lightGreen, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cGit: {
                currentColor = ContextCompat.getColor(this,R.color.git);
                Log.d("TKT_create","git");
                TAG="git";
                Toast.makeText(context, R.string.git, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cFuchsiaPink: {
                currentColor = ContextCompat.getColor(this,R.color.fuchsiaPink);
                Log.d("TKT_create","fuchsia");
                TAG="fuchsia";
                Toast.makeText(context, R.string.fuchsiaPink, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cPink: {
                currentColor = ContextCompat.getColor(this,R.color.pink);
                Log.d("TKT_create","pink");
                TAG="pink";
                Toast.makeText(context, R.string.pink, Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cOldPink: {
                currentColor = ContextCompat.getColor(this,R.color.oldPink);
                Log.d("TKT_create","oldPink");
                TAG="oldPink";
                Toast.makeText(context, R.string.morePink, Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }


    /**
     * Undo last box coloring
     * stacie: used to retrieve the last button that was colored
     * prevColors: used to retrieve tha prev color of the button that was retrieved from stacie
     *
     */
    public void undo()
    {
        Log.d("TKT_create","undo function");
            Toast.makeText(this, R.string.undo, Toast.LENGTH_SHORT).show();
            Button remove = stacie.pop();
            int prevCol = prevColors.pop();
            remove.setBackgroundColor(prevCol);
            remove.setTag(getTag(prevCol));
    }


    /**
     * Load file from db
     * -short click displays the file
     * -long click displays deleteFile option
     * fileList() is where files ae located on device db
     */
    public void openFile()
    {
        Log.d("TKT_create","openFile");
        final Dialog openDialog = new Dialog(this);
        openDialog.setContentView(R.layout.open_file);
        final ListView listView = (ListView) openDialog.findViewById(R.id.listView);
        ArrayList<String>listItem = new ArrayList(Arrays.asList(fileList()));
        ArrayAdapter<String>adapter = new ArrayAdapter(getApplicationContext(), R.layout.custom_list_view, listItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cleanSlate();
                String fileName = listView.getItemAtPosition(position).toString();
                Log.d("TKT_create","fileName: "+fileName);
                getFileFromDatabase(fileName);
                openDialog.dismiss();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TKT_create","OnItemLongClick");
                String fileName = listView.getItemAtPosition(position).toString();
                deleteFileDialog(fileName, openDialog);
                return true;
            }
        });


        openDialog.show();


    }

    //database related functions=====================================================


    /**
     * Ask user if s/he wants to save changes
     * if s/he does - redirect to saveDialog
     * else - redirect to cleanSlate and handle flag i
     * @param i this flag is used to distinguish between different functions that called saveChanged
     *          i = 0: meaning it was redirected here from onBackPressed to indicate that after saving(not saving), do super.onBackPressed
     *          i = 2: redirected from newFile option on menuBar to indicated to toast 'New pattern'
     *          i = 1: the rest of the functions - here there is nothing special to be called
     */
    public void saveChanges(final int i)
    {
        Log.d("TKT_create","saveChanges");
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
                saveDialog(i);
            }
        });

        no.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                cleanSlate();
                if(i == 0)
                    Create.super.onBackPressed();
                if(i == 2)
                    Toast.makeText(context, R.string.newFile, Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        dialog.show();
    }


    /**
     * Save changes to db
     * if user press SAVE - redirect to writeToFile
     * else - show toast and dismiss dialog
     * @param i this flag is used to distinguish between different functions that called saveChanged
     *          i = 0: meaning it was redirected here from onBackPressed to indicate that after saving(not saving), do super.onBackPressed
     *          i = 2: redirected from newFile option on menuBar to indicated to toast 'New pattern'
     *          i = 1: the rest of the functions - here there is nothing special to be called
     */
    public void saveDialog(final int i)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.file_naming);
        dialog.setCanceledOnTouchOutside(false);
        final Button save = (Button)dialog.findViewById(R.id.saveMe);
        final Button cancel = (Button)dialog.findViewById(R.id.cancel);
        final EditText patternName = (EditText)dialog.findViewById(R.id.patternNameText);

        cancel.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                if(i == 0)
                    Create.super.onBackPressed();
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                if(patternName.getText().length() == 0) {
                    Toast.makeText(Create.this, R.string.ChooseName, Toast.LENGTH_LONG).show();
                }
                else if (Arrays.asList(fileList()).contains(patternName.getText().toString()))
                {
                    Toast.makeText(Create.this, R.string.fileExists, Toast.LENGTH_SHORT).show();
                    patternName.setText("");
                }
                else
                {
                    writeToFile(patternName.getText().toString());
                    Log.d("TKT_create", "quit try");
                    cleanSlate();//put it here instead of the showDialog because it cleaned slate b4 saving slate
                    if(i == 0)
                        Create.super.onBackPressed();
                    if(i == 2)
                        Toast.makeText(context, R.string.newFile, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }

            }
        });
        dialog.show();

    }

    /**
     * Writing file to db
     * @param fileName the name of the file the user had chosen on saveDialog
     */
    public void writeToFile(String fileName)
    {
        File file = new File(context.getFilesDir().toString(), fileName);
        try
        {
            file.createNewFile();
            Log.d("TKT_create", "writeToFile try");
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));//context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(state);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(this, R.string.fileSaved, Toast.LENGTH_SHORT).show();

        }
        catch (IOException e)
        {
            Log.d("TKT_create", "writeToFile exception");
            e.printStackTrace();
        }
    }


    /**
     * Deleting file: when pressing openFile on menuBar, dialog appears with files saved in db
     * if redirected here, it means user had longPressed on of the fileNames
     * @param fileName fileName to be deleted from db
     * @param openDialog the dialog that redirected user to this dialog
     *                   if user decided to erase, openDialog will be dismissed and replaced with a new dialog only without the file that was just deleted (happens in deleteFromDB)
     */
    public void deleteFileDialog(final String fileName, final Dialog openDialog)
    {
        Log.d("TKT_create","deleteFileDialog");
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_dialog);
        dialog.setCanceledOnTouchOutside(false);
        TextView textView = (TextView)dialog.findViewById(R.id.deleteText);
        textView.setText(getResources().getString(R.string.deleteFile) + " '"+fileName+"' ?");

        Button yes = (Button)dialog.findViewById(R.id.hellYeahDelete);
        Button no = (Button)dialog.findViewById(R.id.heavensNoDelete);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TKT_create","onClick: NO");
                dialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TKT_create","onClick: YES");
                //removeFrom database
                deleteFromDB(fileName, openDialog);
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    /**
     *Deleting file from db
     * @param fileName fileName to be erased from db
     * @param openDialog the dialog that redirected user to this dialog
     *                   if user decided to erase, openDialog will be dismissed and replaced with a new dialog only without the file that was just deleted
     */
    public void deleteFromDB(String fileName, Dialog openDialog)
    {
        File file = new File(getFilesDir(), fileName);
        boolean deleted = file.delete();
        if(openDialog.isShowing()) {
            Log.d("TKT_create","openDialog.isShowing");
            openDialog.dismiss();
        }
        openFile();
        Log.d("TKT_create","deleted? "+deleted);
    }

    /**
     * Load file from db
     * @param fileName the fileName to be retrieved from db,
     *                 the file is actually an object (hashmap) that later is saved to state
     */
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

    /**
     * Displaying retrieved file from db on the canvas
     */
    public void displayFile()
    {
        Log.d("TKT_create","isMT: "+state.isEmpty());
        Iterator it = state.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            Button temp = (Button)findViewById((Integer)pair.getKey());
            temp.setBackgroundColor(getColor((String)pair.getValue()));
            temp.setTag(pair.getValue());
            Log.d("TKT_create","temp.tag " +pair.getKey());
            Log.d("TKT_create","temp.tag " +pair.getValue());
        }
    }

    /**
     * Clear canvas
     * run on state until it's empty and:
     *      change button color to blanco
     *      change button tag to white
     * set TAG to negro
     * set currentColor to negro
     * clear state
     * clear stacie
     */
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
        TAG = "negro";
        currentColor=ContextCompat.getColor(this, R.color.negro);
        state.clear();
        stacie.clear();

    }




    //convertions===============================================


    /**
     * convert int color tp String tag
     * @param prevCol color to convert
     * @return the corresponding tag
     */
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


    /**
     * Convert String tag to int color
     * @param tag tag to convert
     * @return the corresponding color
     */
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



    //=========================================================

    /**
     * OnBackPressed; before going to back act, see if user wants to save changes
     */
    @Override
    public void onBackPressed()
    {
        Log.d("TKT_create","onBackPressed");
        saveChanges(0);
        //super.onBackPressed();
    }

}
