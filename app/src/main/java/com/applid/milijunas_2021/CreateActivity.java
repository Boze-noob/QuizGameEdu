package com.applid.milijunas_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText a, b, c, d, question;
    private String correctAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create);
        getSupportActionBar().hide();


        a = findViewById(R.id.create_a);
        b = findViewById(R.id.create_b);
        c = findViewById(R.id.create_c);
        d = findViewById(R.id.create_d);
        question = findViewById(R.id.question_text);

    }

    public void exitCreate(View view)
    {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void sendQuestion(View view)
    {
        checkEditTexts();
    }

    private void checkEditTexts()
    {

        if(a.getText().toString().trim().isEmpty() || b.getText().toString().trim().isEmpty() || c.getText().toString().trim().isEmpty() || d.getText().toString().trim().isEmpty() || question.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this, getResources().getString(R.string.fillAll), Toast.LENGTH_SHORT).show();
        }
        else
        {

            openDialog();
        }

    }

    private void sendToFirebase()
    {

        UserQuestion userQuestion = new UserQuestion(question.getText().toString().trim(), a.getText().toString().trim(), b.getText().toString().trim(), c.getText().toString().trim(), d.getText().toString().trim(), correctAnswer);
        db.collection("questions").add(userQuestion).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                Toast.makeText(CreateActivity.this, getResources().getString(R.string.questionSentStr), Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
        question.getText().clear();
        a.getText().clear();
        b.getText().clear();
        c.getText().clear();
        d.getText().clear();

    }

    private void openDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.correctAnswer));


        String[] answers = {a.getText().toString().trim(), b.getText().toString().trim(), c.getText().toString().trim(), d.getText().toString().trim()};
        boolean[] checkedItems = {false, false, false, false};
        builder.setMultiChoiceItems(answers, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // The user checked or unchecked a box
                if(isChecked)
                {
                    correctAnswer = answers[which];

                }

            }
        });


        builder.setPositiveButton(getResources().getString(R.string.send), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendToFirebase();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), null);


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = this.getSharedPreferences("Language", Context.MODE_PRIVATE);
        int i = prefs.getInt("LangValue", 0);
        if(i == 1)
        {
            changeLanguageCreate();
        }
    }

    public void changeLanguageCreate()
    {

        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
    }


}