package com.example.ezast.neptune;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Animation Variables declared
    public AnimationDrawable idleFaceMovement;
    public AnimationDrawable sleepFaceMovement;
    public AnimationDrawable energyBarMovement;
    public AnimationDrawable hungerBarMovement;
    public AnimationDrawable eatingMovement;
    public AnimationDrawable treatMovement;
    public AnimationDrawable talkFaceMovement;
    public AnimationDrawable studyFaceMovement;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    private String gradeText;

    //Age and treats / reward variables
    TextView ageValue, treatValue, knowledgeValue, gradeLevel, eatClickValue, studyClickValue;
    int age, treat, eatClicks, studyClicks;
    Switch switch1;

    @SuppressLint({"WrongViewCast", "RestrictedApi"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Method for the switch to toggle music on and off
        switch1 = findViewById(R.id.togglebutton);

        //Link for the background music while starting.
        final MediaPlayer themeSong;
        themeSong = MediaPlayer.create(this, R.raw.tucyutetheme);
        themeSong.setLooping(false);
        themeSong.setVolume(40,40);
        //themeSong.start();

        //Face Views assigned
        final ImageView sleepFace = findViewById(R.id.sleepface);
        final ImageView eatingFace = findViewById(R.id.eatingface);
        final ImageView studyFace = findViewById(R.id.studyface);
        final ImageView face = findViewById(R.id.mainface);

        //Button assignments
        final Button feedButton = findViewById(R.id.eatButton);
        final Button restButton = findViewById(R.id.restButton);
        final Button wakeButton = findViewById(R.id.wakeButton);
        final Button studyButton = findViewById(R.id.bookButton);
        final Button studyButton2 = findViewById(R.id.bookButton2);
        final Button playButton = findViewById(R.id.playButton);
        final Button mapButton = findViewById(R.id.mapButton);
        final Button feedButtonDisabled = findViewById(R.id.eatButtonDisabled);
        final Button restButtonDisabled = findViewById(R.id.restButtonDisabled);
        final Button studyButtonDisabled = findViewById(R.id.bookButtonDisabled);
        final Button playButtonDisabled = findViewById(R.id.playButtonDisabled);
        final Button mapButtonDisabled = findViewById(R.id.mapButtonDisabled);

        //Assigning sounds and music
        final MediaPlayer talkSound = MediaPlayer.create(this, R.raw.fullintro);
        final MediaPlayer eatingSound = MediaPlayer.create(this, R.raw.tucyuteate);
        final MediaPlayer sleepSound = MediaPlayer.create(this, R.raw.sleeping);
        final MediaPlayer readSound = MediaPlayer.create(this, R.raw.readingsound);
        final MediaPlayer gradeSound = MediaPlayer.create( this, R.raw.gradelevelsound);
        final MediaPlayer noticeSound = MediaPlayer.create(this, R.raw.notification);
        final MediaPlayer treatSound = MediaPlayer.create( this, R.raw.treatsound);

        //Volume Adjustments
        noticeSound.setVolume(40,40);

        //Links to text views
        ageValue = findViewById(R.id.agetext);
        treatValue = findViewById(R.id.treatText);
        knowledgeValue = findViewById(R.id.knowledgetext);
        gradeLevel = findViewById(R.id.gradeLevel);
        eatClickValue = findViewById(R.id.eatClickText);
        studyClickValue = findViewById(R.id.studyClickText);

        // Treat animation flying across screen.
        final ImageView treatsFlying = findViewById(R.id.treats);
        treatsFlying.setBackgroundResource(R.drawable.treatanimation);
        treatMovement = (AnimationDrawable) treatsFlying.getBackground();

        // Rest Bar Decrease over time animation
        final ImageView energyLevel = findViewById(R.id.energyBar);
        energyLevel.setBackgroundResource(R.drawable.energybaranimation);
        energyBarMovement = (AnimationDrawable) energyLevel.getBackground();

        energyBarMovement.start();

        // Hunger Bar Decrease over time animation
        final ImageView hungerLevel = findViewById(R.id.hungerBar);
        hungerLevel.setBackgroundResource(R.drawable.hungerbaranimation);
        hungerBarMovement = (AnimationDrawable) hungerLevel.getBackground();

        //Start hunger bar drop animation
        hungerBarMovement.start();

        //start with the following buttons invisible
        wakeButton.setVisibility(View.INVISIBLE);
        mapButtonDisabled.setVisibility(View.INVISIBLE);
        playButtonDisabled.setVisibility(View.INVISIBLE);
        studyButton2.setVisibility(View.INVISIBLE);

        //Start with the following buttons visible
        studyButton.setVisibility(View.VISIBLE);
        playButton.setVisibility(View.VISIBLE);
        mapButton.setVisibility(View.VISIBLE);

        //Set the image on startup to the idle face by default
        face.setBackgroundResource(R.drawable.idlefaceanimation);
        idleFaceMovement = (AnimationDrawable) face.getBackground();

        //Load Age
        SharedPreferences myAge = this.getSharedPreferences("MyAge", MODE_PRIVATE);
        age = myAge.getInt("age", 0);
        ageValue.setText("" + age);

        //Load Treats
        SharedPreferences myTreat = this.getSharedPreferences("MyTreat", MODE_PRIVATE);
        treat = myTreat.getInt("treat", 0);
        treatValue.setText("" + treat);

        //Load Grade
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        gradeText = sharedPreferences.getString(TEXT, "");
        gradeLevel.setText(gradeText);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            //override allows for the music to play in background. The switch is invisible and cannot be accessed.
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked) {
                    //themeSong.start();
                }
            }
        });

        //Feed Button
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Stops the idle face animation
                idleFaceMovement.stop();
                face.setBackgroundResource(R.drawable.blank);

                //Stops the sleep face animation
                sleepFace.setBackgroundResource(R.drawable.blank);
                //Creates eating animation

                eatingFace.setBackgroundResource(R.drawable.eatinganimation);
                eatingMovement = (AnimationDrawable) eatingFace.getBackground();

                studyFace.setBackgroundResource(R.drawable.blank);

                //starts eating animation
                eatingMovement.stop();
                eatingMovement.start();

                //Reset Hunger
                hungerBarMovement.stop();
                hungerBarMovement.start();

                //Play the sound of eating
                eatingSound.start();
            }
        });

        //Rest Button
        restButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"RestrictedApi", "SetTextI18n"})
            public void onClick(View sleeping) {

                //Buttons no longer visible until the wake button is pressed.
                restButton.setVisibility(View.INVISIBLE);
                feedButton.setVisibility(View.INVISIBLE);
                studyButton.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.INVISIBLE);
                mapButton.setVisibility(View.INVISIBLE);

                wakeButton.setVisibility(View.VISIBLE);
                mapButtonDisabled.setVisibility(View.VISIBLE);
                feedButtonDisabled.setVisibility(View.VISIBLE);
                studyButtonDisabled.setVisibility(View.VISIBLE);
                playButtonDisabled.setVisibility(View.VISIBLE);

                //Stops the idle face animation when the button is pressed.
                idleFaceMovement.stop();
                face.setBackgroundResource(R.drawable.blank);

                //Stops and clears the eating face animation.
                eatingFace.setBackgroundResource(R.drawable.blank);

                studyFace.setBackgroundResource(R.drawable.blank);

                //Creates the sleep animation.
                ImageView sleepFace = findViewById(R.id.sleepface);
                sleepFace.setBackgroundResource(R.drawable.sleepanimation);
                sleepFaceMovement = (AnimationDrawable) sleepFace.getBackground();

                //Start the sleeping sound upon press.
                sleepSound.start();

                //Start the sleeping animation
                sleepFaceMovement.start();
                energyBarMovement.stop();

                //Counts aging and saves.
                age+=1;
                //Save age
                SharedPreferences myAge = getSharedPreferences("MyAge", MODE_PRIVATE);
                SharedPreferences.Editor ageEditor = myAge.edit();
                ageEditor.putInt("age", age);
                ageEditor.commit();
                ageValue.setText("" + age);

                //Counts treats and saves.
                treat+=10;
                //Save age
                SharedPreferences myTreat = getSharedPreferences("MyTreat", MODE_PRIVATE);
                SharedPreferences.Editor treatEditor = myTreat.edit();
                treatEditor.putInt("treat", treat);
                treatEditor.commit();
                treatValue.setText("" + treat);

                //Saves grade level when sleeping
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor gradeEditor = sharedPreferences.edit();
                gradeEditor.putString(TEXT, gradeLevel.getText().toString());
                gradeEditor.commit();

                //Test if age is a certain value, then give more treats
                //If age is a certain value, the background will change as the pet ages.

                if ((age == 5)) {

                    //Play the eating sound
                    //treatSound.start();
                    Toast.makeText(getApplicationContext(), "Toddler", Toast.LENGTH_SHORT).show();

                    //Adding treats upon sleep.
                    treatValue.setText(Integer.toString(treat));
                    treatMovement.stop();
                    treatMovement.start();

                    //Set the background
                    ImageView backgroundMain1 = findViewById(R.id.background2);
                    backgroundMain1.setVisibility(View.GONE);

                    //Clears the sleep face animation when the button is pressed.
                    sleepFace.setBackgroundResource(R.drawable.blank);

                    //Starts the talking face animation again
                    ImageView talk = findViewById(R.id.mainface);
                    talk.setBackgroundResource(R.drawable.talkinganimation);
                    talkFaceMovement = (AnimationDrawable) talk.getBackground();
                    talkFaceMovement.start();

                    //Start the talking sounds
                    sleepSound.pause();
                    talkSound.start();

                    //Starts the idle face animation again
                    ImageView face = findViewById(R.id.mainface);
                    idleFaceMovement = (AnimationDrawable) face.getBackground();
                }

                if ((age == 10)) {

                    //Change the background
                    ImageView backgroundMain2 = findViewById(R.id.background4);
                    backgroundMain2.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Teenager", Toast.LENGTH_SHORT).show();
                }

                if ((age == 20)) {

                    //Change the background
                    ImageView backgroundMain3 = findViewById(R.id.background5);
                    backgroundMain3.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Adulthood!", Toast.LENGTH_SHORT).show();
                }

                if ((age == 30)) {

                    //Change the background
                    ImageView backgroundMain4 = findViewById(R.id.background6);
                    backgroundMain4.setVisibility(View.GONE);
                }

                if ((age == 35)) {

                    //Change the background
                    ImageView backgroundMain5 = findViewById(R.id.background7);
                    backgroundMain5.setVisibility(View.GONE);
                }

                if ((age == 40)) {

                    //Change the background
                    ImageView backgroundMain7 = findViewById(R.id.background8);
                    backgroundMain7.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Senior Citizen!", Toast.LENGTH_SHORT).show();
                }

            }
    });

        //Wake button
        wakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Buttons now invisible until the wakeup button is pressed.
                restButton.setVisibility(View.VISIBLE);
                studyButton.setVisibility(View.VISIBLE);
                feedButton.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.VISIBLE);
                mapButton.setVisibility(View.VISIBLE);


                wakeButton.setVisibility(View.INVISIBLE);
                feedButtonDisabled.setVisibility(View.INVISIBLE);
                studyButtonDisabled.setVisibility(View.INVISIBLE);

                //Clears the sleep face animation
                sleepFace.setBackgroundResource(R.drawable.ic_blank);
                sleepFaceMovement.stop();

                //Starts the idle face animation again
                ImageView face = findViewById(R.id.mainface);
                face.setBackgroundResource(R.drawable.idlefaceanimation);
                idleFaceMovement = (AnimationDrawable) face.getBackground();
                idleFaceMovement.start();
                energyBarMovement.start();
            }
        });

        //Study Button 1st grade.
        studyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Clears any other animations.
                face.setBackgroundResource(R.drawable.ic_blank);
                eatingFace.setBackgroundResource(R.drawable.ic_blank);
                sleepFace.setBackgroundResource(R.drawable.ic_blank);

                //Creates study animation
                studyFace.setBackgroundResource(R.drawable.studyanimation);
                studyFaceMovement = (AnimationDrawable) studyFace.getBackground();
                studyFaceMovement.start();
                readSound.start();

                //Disable Buttons
                studyButton.setVisibility(View.INVISIBLE);
                mapButton.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.INVISIBLE);
                restButton.setVisibility(View.INVISIBLE);
                feedButton.setVisibility(View.INVISIBLE);

                //Shows Second study button
                studyButton2.setVisibility(View.VISIBLE);
                playButtonDisabled.setVisibility(View.VISIBLE);
                mapButtonDisabled.setVisibility(View.VISIBLE);

                //Counts study clicks and saves.
                studyClicks+=1;
                //Save study button clicks amount.
                SharedPreferences myEatClicks = getSharedPreferences("MyStudyClicks", MODE_PRIVATE);
                SharedPreferences.Editor ageEditor = myEatClicks.edit();
                ageEditor.putInt("MyStudyClicks", studyClicks);
                ageEditor.commit();
                studyClickValue.setText("" + studyClicks);

                if (studyClicks == 1){

                    noticeSound.start();
                    //Study Tutorial Dialog Box.
                    ImageView bookImage = new ImageView(MainActivity.this);
                    bookImage.setImageResource(R.drawable.ic_book);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Study!")
                            .setMessage("By studying, you reach new grade levels. Try tapping the study button a few times.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    //code if they select "yes"
                                }
                            })
                            .setView(bookImage);

                    AlertDialog alert = builder.create();
                    alert.show();
                }

                if (studyClicks == 3){

                    gradeLevel.setText("K");
                    gradeSound.start();
                    //Eating Tutorial Dialog Box.
                    ImageView gradeImage = new ImageView(MainActivity.this);
                    gradeImage.setImageResource(R.drawable.certificate);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Kindergartner!")
                            .setMessage("By studying, you reach new grade levels. Each grade level provides bonus treats and your exposure level is increased; allowing you to explore more of the world.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    //code if they select "yes"
                                }
                            })
                            .setView(gradeImage);

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        studyButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Clears any other animations.
                studyFace.setBackgroundResource(R.drawable.idlefaceanimation);
                studyFaceMovement.stop();

                //Enable Buttons back.
                studyButton.setVisibility(View.VISIBLE);
                mapButton.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.VISIBLE);
                restButton.setVisibility(View.VISIBLE);
                feedButton.setVisibility(View.VISIBLE);

                //Shows Second study button
                studyButton2.setVisibility(View.INVISIBLE);
                playButtonDisabled.setVisibility(View.INVISIBLE);
                mapButtonDisabled.setVisibility(View.INVISIBLE);

                idleFaceMovement.start();
            }
        });

        //House Button
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (age >= 3) {
                    //If player is older than 3, .....

                } else {
                    //themeSong.pause();
                    //Show age requirement dialog.
                    noticeSound.start();
                    ImageView clockImage = new ImageView(MainActivity.this);
                    clockImage.setImageResource(R.drawable.ic_timer);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Oops!")
                            .setMessage("Games available at age 3.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    //code if they select "Okay"
                                    //themeSong.start();
                                }
                            })
                            .setView(clockImage);

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        //Map button
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View map) {

                if (gradeLevel.getText().toString().contentEquals("5th")){
                    //If player is in 5th grade, .....

                } else {
                    //themeSong.pause();
                    //Show age requirement dialog.
                    noticeSound.start();
                    ImageView bookImage = new ImageView(MainActivity.this);
                    bookImage.setImageResource(R.drawable.ic_book);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Gain More Experience!")
                            .setMessage("You don't have enough knowledge to explore yet. Exploration is available at 5th grade.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    //code if they select "Okay"
                                    //themeSong.start();
                                }
                            })
                            .setView(bookImage);

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

    }
}