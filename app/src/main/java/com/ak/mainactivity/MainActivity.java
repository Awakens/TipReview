package com.ak.TipReview;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {
    TextView splashTextView;
    TextView textbox;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3;
    String[] text;
    int index = 0;
    int[] points;
    ViewFlipper flip;
    RatingBar[] ratingBars;
    double rating = 0;
    RatingBar resultRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flip = (ViewFlipper) findViewById (R.id.flipper);
        splashTextView = (TextView) findViewById(R.id.splashTextView);
        text =  new String[]{getResources().getString(R.string.s0),
                getResources().getString(R.string.s1),getResources().getString(R.string.s2),
                getResources().getString(R.string.s3),
                getResources().getString(R.string.s4),
                getResources().getString(R.string.s5) };
         textbox = (TextView) findViewById(R.id.textView);
        textbox.setText(text[index]);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
       // radioButton1.setOnClickListener(new View.OnClickListener() {
       // @Override
       // public void onClick(View view) {
        //    badPoints = 0; } });
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        resultRatingBar = (RatingBar) findViewById(R.id.resultRatingBar);
        flip.setDisplayedChild(flip.indexOfChild(findViewById(R.id.splashView)));
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void bad0(View view) {
        points[index] = 5;
    }

    public void bad1(View view) {
        points[index] = 1;
    }

    public void badhalf(View view) {
        points[index] = 3;
    }

    public void next(View view) {
        if (index < text.length - 2) {
            textbox.setText(text[++index]);
            if(points[index] == 5)
            radioGroup.check(R.id.radioButton1);  //set the radiobuttonback to the answer
            else if(points[index] == 1)
                radioGroup.check(R.id.radioButton2);
            else if(points[index] == 3)
                radioGroup.check(R.id.radioButton3);
        }
        else if(index == text.length-2) {   //at last question -> go to result
            flip.setDisplayedChild(flip.indexOfChild(findViewById(R.id.resultView)));
            rating = 0;
            for(int g =0;g<points.length;g++){
                rating+=points[g];  }
            rating = rating / points.length;
            resultRatingBar.setRating((float) rating);
            ((TextView) findViewById(R.id.tipText)).clearComposingText();
            ((EditText) findViewById(R.id.editText)).clearComposingText();
            ((EditText) findViewById(R.id.editText2)).clearComposingText();
        }
    }

    public void prev(View view) {
        if (index != 0) {
            textbox.setText(text[--index]);
            if(points[index] == 5)
                radioGroup.check(R.id.radioButton1);  //set the radiobuttonback to the answer
            else if(points[index] == 1)
                radioGroup.check(R.id.radioButton2);
            else if(points[index] == 3)
                radioGroup.check(R.id.radioButton3);
        }
    }
    public void switchToScenic(View view) {
        flip.setDisplayedChild(flip.indexOfChild(findViewById(R.id.scenicView)));
        points = new int[text.length];
        for(int g = 0; g<text.length;g++)   //initialize to 5 for scenic
            points[g] = 5;
    }

    public void switchToQuick(View view) {
        flip.setDisplayedChild(flip.indexOfChild(findViewById(R.id.quickView)));
        ratingBars = new RatingBar[]{(RatingBar) findViewById(R.id.ratingBar0),
                (RatingBar) findViewById(R.id.ratingBar1),(RatingBar) findViewById(R.id.ratingBar2),
                (RatingBar) findViewById(R.id.ratingBar3),(RatingBar) findViewById(R.id.ratingBar4), (RatingBar) findViewById(R.id.ratingBar5)
        };
        for(int g = 0; g<text.length;g++)   //initialize to 5 for rating bars
        {
            ratingBars[g].setRating(5);
            //changing colors
            LayerDrawable stars = (LayerDrawable) ratingBars[g].getProgressDrawable();
            stars.getDrawable(0).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        } }

    public void switchToSplash(View view) {
        flip.setDisplayedChild(flip.indexOfChild(findViewById(R.id.resultView)));
        index = 0;
        textbox.setText(text[index]);
        points = new int[text.length];
        for(int g = 0; g < text.length; g++){
            points[g] = 5;
        }
    }
    public void quickResultPressed(View view) {
        rating =0;
        for(int g = 0; g < ratingBars.length; g++){
            rating+=ratingBars[g].getRating();
        }
        rating = rating/ratingBars.length;
        flip.setDisplayedChild(flip.indexOfChild(findViewById(R.id.resultView)));
        resultRatingBar.setRating((float) rating);
        if(rating<=2){
            ((TextView)findViewById(R.id.ResultText)).setText("Wow that\'s pretty bad. I\'d seriously consider just leaving without leaving any tip");}
        else if(rating<=4){
            ((TextView)findViewById(R.id.ResultText)).setText("Eh so average. I\'d recommend leaving maybe 10% to 15% tip");
        }
        else if(rating<=6){
            ((TextView)findViewById(R.id.ResultText)).setText("Fantastic! I\'d recommend leaving around 20% tip");
        }
    }

    public void calculate(View view) {
       try{
        ((TextView) findViewById(R.id.tipText)).setText
        (((Double.parseDouble(((EditText) findViewById(R.id.editText)).getText().toString()) / 100)
        * (Double.parseDouble(((EditText) findViewById(R.id.editText2)).getText().toString())))+"");
    } catch(Exception g){}
    }
}

