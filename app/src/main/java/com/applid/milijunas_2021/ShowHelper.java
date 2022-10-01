package com.applid.milijunas_2021;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class ShowHelper {

    int waitQuestion = 2500;
    int newQuestionTime = 2500;
    static Activity activity;


    ArrayList<QuestionsClass> easyQuestionsArray = new ArrayList<>();
    ArrayList<QuestionsClass> hardQuestionsArray = new ArrayList<>();
    ArrayList<QuestionsClass> noramlQuestionsArray = new ArrayList<>();

    Button a;
    Button b;
    Button c;
    Button d;

    //public static int time = 0;
   // public static Timer timer = new Timer();

    Button withdrawButton;
    static String moneyEarned;
    TextView sourceContainer;
    QuestionsClass question;
    TextView timeCounter;
    ImageButton halfHalf;
    ImageButton phoneImg;
    ImageButton publicImg;
    static String publicAnswer;
    Button[] moneyButton;

   // public static CountDownTimer countDownTimer;
    int index;
    int numOfQuestions = 0;
    int questionPosition = 0;

    boolean publicBoolean = true;

    public static MediaPlayer mediaPlayer;
    static String moneyRound = "0";
    static int status = 0;


    public Button getWithdrawButton() {
        return withdrawButton;
    }

    public void setWithdrawButtonu(Button withdrawButtonu) {
        this.withdrawButton = withdrawButtonu;
    }



    public ImageButton getPublicImg() {
        return publicImg;
    }

    public void setPublicImg(ImageButton publicImg) {
        this.publicImg = publicImg;
    }

    public ImageButton getPhoneImg() {
        return phoneImg;
    }

    public void setPhoneImg(ImageButton phoneImg) {
        this.phoneImg = phoneImg;
    }

    static String telephoneJokerSays;

    public ImageButton getHalfHalf() {
        return halfHalf;
    }

    public void setHalfHalf(ImageButton halfHalf) {
        this.halfHalf = halfHalf;
    }

    public Button[] getMoneyButton() {
        return moneyButton;
    }

    public void setMoneyButton(Button[] moneyButton) {
        this.moneyButton = moneyButton;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public ShowHelper(final Activity activity, Button a, Button b, Button c, Button d, TextView sourceContainer) {
        this.activity = activity;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.sourceContainer = sourceContainer;
        hardQuestionsArray = getHardQuestions();
        easyQuestionsArray = getEasyQuestions();
        noramlQuestionsArray = getNormalQuestions();
        mediaPlayer = MediaPlayer.create(activity, R.raw.show_music);

        SharedPreferences sharedPreferences=activity.getSharedPreferences("ses", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("ses","").equals("ok")) {

           // new Handler().postDelayed(new Runnable() {
               // @Override
             //   public void run() {
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                    mediaPlayer.setVolume(0.3f, 0.3f);
               // }
           // }, 2500);

        }

    }

    public void play() {

        SharedPreferences prefs = activity.getSharedPreferences("Language", Context.MODE_PRIVATE);
        int i = prefs.getInt("LangValue", 0);
        if(i == 1)
        {
            getWithdrawButton().setText("WITHDRAW");
        }


        getWithdrawButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callShowWithdraw();
            }
        });



        questionChecker(questionPosition, new answerInterface() {
            @Override
            public void trueAnswer() {

            }

            @Override
            public void wrongAnswer() {
                showWrongAnswerDialog();

            }
        });

    }

    public ArrayList<QuestionsClass> getEasyQuestions() {
        ArrayList<QuestionsClass> easyQuestionsList = new ArrayList<>();
        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            SharedPreferences prefs = activity.getSharedPreferences("Language", Context.MODE_PRIVATE);
            InputStream in_s;
           if( prefs.getInt("LangValue", 0) == 0) {
               in_s = activity.getApplicationContext().getAssets().open("easyquestions.xml");
           }
           else{
               in_s = activity.getApplicationContext().getAssets().open("easyquestionseng.xml");
           }
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            easyQuestionsList = questionParser(parser);
            for (QuestionsClass questionsClass : easyQuestionsList) {



            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(easyQuestionsList);
        return easyQuestionsList;
    }

    public ArrayList<QuestionsClass> getNormalQuestions() {
        ArrayList<QuestionsClass> normalQuestionsList = new ArrayList<>();
        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            SharedPreferences prefs = activity.getSharedPreferences("Language", Context.MODE_PRIVATE);
            InputStream in_s;
            if( prefs.getInt("LangValue", 0) == 0) {
                in_s = activity.getApplicationContext().getAssets().open("normalquestions.xml");
            }
            else
            {
                in_s = activity.getApplicationContext().getAssets().open("normalquestionseng.xml");
            }
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            normalQuestionsList = questionParser(parser);
            for (QuestionsClass questionsClass : normalQuestionsList) {



            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.shuffle(normalQuestionsList);
        return normalQuestionsList;
    }

    public ArrayList<QuestionsClass> getHardQuestions() {
        ArrayList<QuestionsClass> hardQuestionsList = new ArrayList<>();
        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            SharedPreferences prefs = activity.getSharedPreferences("Language", Context.MODE_PRIVATE);
            InputStream in_s;
            if( prefs.getInt("LangValue", 0) == 0) {
                in_s = activity.getApplicationContext().getAssets().open("hardquestions.xml");
            }
            else
            {
                in_s = activity.getApplicationContext().getAssets().open("hardquestionseng.xml");
            }
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            hardQuestionsList = questionParser(parser);
            for (QuestionsClass questionsClass : hardQuestionsList) {


            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(hardQuestionsList);
        return hardQuestionsList;
    }

    void questionChecker(int position, final answerInterface answerInterface) {
        index = position;

        if (index < 4) question = easyQuestionsArray.get(index);
        else if (index > 3 && index < 8) question = noramlQuestionsArray.get(index - 4);
        else question = hardQuestionsArray.get(index - 8);
        Map<String, String> correctAnswers = new HashMap<>();
        correctAnswers = question.getAnswer();
        askPublic(getHalfHalf(), question.getCorrectAnswer());
        telephonJokerStrings(question.getCorrectAnswer(), getPhoneImg());
        showJokerDialog(getPublicImg(), question.getCorrectAnswer());

        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);

        a.setText("A:  " + correctAnswers.get("1"));
        b.setText("B:  " + correctAnswers.get("2"));
        c.setText("C:  " + correctAnswers.get("3"));
        d.setText("D:  " + correctAnswers.get("4"));

        a.setEnabled(true);
        b.setEnabled(true);
        c.setEnabled(true);
        d.setEnabled(true);



        moneyEarned = setEarnedMoney(index);
        a.setBackgroundResource(R.drawable.money_border);
        a.setTextColor(Color.WHITE);
        b.setBackgroundResource(R.drawable.money_border);
        b.setTextColor(Color.WHITE);
        c.setBackgroundResource(R.drawable.money_border);
        c.setTextColor(Color.WHITE);
        d.setBackgroundResource(R.drawable.money_border);
        d.setTextColor(Color.WHITE);
        sourceContainer.setText(question.getQuestion());

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startMusic(R.raw.new_question_sound);
                RightButtonIsAlwaysGreen(question.getCorrectAnswer());
               // ct.cancel();
                a.setEnabled(false);
                b.setEnabled(false);
                c.setEnabled(false);
                d.setEnabled(false);
                a.setBackgroundResource(R.drawable.neutral_answer);
                a.setTextColor(Color.BLACK);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (a.getTag().equals(question.getCorrectAnswer())) {
                            startMusic(R.raw.applaus);
                            a.setBackgroundResource(R.drawable.correct_answer);
                            a.setTextColor(Color.WHITE);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answerInterface.trueAnswer();
                                    if (index < (getNumOfQuestions() - 1)) {
                                        index++;
                                        questionChecker(index, new answerInterface() {
                                            @Override
                                            public void trueAnswer() {

                                            }

                                            @Override
                                            public void wrongAnswer() {
                                                showWrongAnswerDialog();

                                            }
                                        });
                                    } else {
                                        showWinnerDialog();
                                    }
                                }
                            }, newQuestionTime);
                        } else {
                          startMusic(R.raw.wrong_answer_sound2);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answerInterface.wrongAnswer();

                                }
                            }, newQuestionTime);
                            a.setBackgroundResource(R.drawable.wrong_answer);
                            a.setTextColor(Color.WHITE);
                        }
                    }
                }, waitQuestion);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusic(R.raw.new_question_sound);

                RightButtonIsAlwaysGreen(question.getCorrectAnswer());
              //  ct.cancel();
                b.setEnabled(false);
                a.setEnabled(false);
                c.setEnabled(false);
                d.setEnabled(false);
                b.setBackgroundResource(R.drawable.neutral_answer);
                b.setTextColor(Color.BLACK);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (b.getTag().equals(question.getCorrectAnswer())) {
                            startMusic(R.raw.applaus);
                            b.setBackgroundResource(R.drawable.correct_answer);
                            b.setTextColor(Color.WHITE);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answerInterface.trueAnswer();
                                    if (index < (getNumOfQuestions() - 1)) {
                                        index++;
                                        questionChecker(index, new answerInterface() {
                                            @Override
                                            public void trueAnswer() {

                                            }

                                            @Override
                                            public void wrongAnswer() {
                                               showWrongAnswerDialog();

                                            }
                                        });
                                    } else {
                                        showWinnerDialog();
                                    }
                                }
                            }, newQuestionTime);
                        } else {
                             startMusic(R.raw.wrong_answer_sound2);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answerInterface.wrongAnswer();
                                }
                            }, newQuestionTime);
                            b.setBackgroundResource(R.drawable.wrong_answer);
                            b.setTextColor(Color.WHITE);
                        }
                    }
                }, waitQuestion);
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusic(R.raw.new_question_sound);

                RightButtonIsAlwaysGreen(question.getCorrectAnswer());
              //  ct.cancel();
                c.setEnabled(false);
                b.setEnabled(false);
                a.setEnabled(false);
                d.setEnabled(false);
                c.setBackgroundResource(R.drawable.neutral_answer);
                c.setTextColor(Color.BLACK);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (c.getTag().equals(question.getCorrectAnswer())) {
                            startMusic(R.raw.applaus);
                            c.setBackgroundResource(R.drawable.correct_answer);
                            c.setTextColor(Color.WHITE);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answerInterface.trueAnswer();
                                    if (index < (getNumOfQuestions() - 1)) {
                                        index++;
                                        questionChecker(index, new answerInterface() {
                                            @Override
                                            public void trueAnswer() {

                                            }

                                            @Override
                                            public void wrongAnswer() {
                                                showWrongAnswerDialog();

                                            }
                                        });
                                    } else {
                                        showWinnerDialog();
                                    }
                                }
                            }, newQuestionTime);
                        } else {
                            startMusic(R.raw.wrong_answer_sound2);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answerInterface.wrongAnswer();
                                }
                            }, newQuestionTime);
                            c.setBackgroundResource(R.drawable.wrong_answer);
                            c.setTextColor(Color.WHITE);
                        }
                    }
                }, waitQuestion);
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusic(R.raw.new_question_sound);
                RightButtonIsAlwaysGreen(question.getCorrectAnswer());
              //  ct.cancel();
                d.setEnabled(false);
                b.setEnabled(false);
                c.setEnabled(false);
                a.setEnabled(false);
                d.setBackgroundResource(R.drawable.neutral_answer);
                d.setTextColor(Color.BLACK);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (d.getTag().equals(question.getCorrectAnswer())) {
                            startMusic(R.raw.applaus);
                            d.setBackgroundResource(R.drawable.correct_answer);
                            d.setTextColor(Color.WHITE);
                            final Handler handler = new Handler();
                            answerInterface.trueAnswer();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if (index < (getNumOfQuestions() - 1)) {
                                        index++;
                                        questionChecker(index, new answerInterface() {
                                            @Override
                                            public void trueAnswer() {

                                            }

                                            @Override
                                            public void wrongAnswer() {
                                                showWrongAnswerDialog();

                                            }
                                        });
                                    } else {
                                        showWinnerDialog();
                                    }
                                }
                            }, newQuestionTime);
                        } else {
                            startMusic(R.raw.wrong_answer_sound2);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    answerInterface.wrongAnswer();
                                }
                            }, newQuestionTime);
                            d.setBackgroundResource(R.drawable.wrong_answer);
                            d.setTextColor(Color.WHITE);
                        }
                    }
                }, waitQuestion);
            }
        });


    }

    private ArrayList<QuestionsClass> questionParser(XmlPullParser parser) throws XmlPullParserException, IOException {
        String text = "";
        ArrayList<QuestionsClass> questions = new ArrayList<>();
        int eventType = parser.getEventType();
        QuestionsClass questionsClass = null;
        Map<String, String> answers = new HashMap<>();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagname = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagname.equalsIgnoreCase("questionBlock")) {
                        questionsClass = new QuestionsClass();
                    }
                    break;

                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;

                case XmlPullParser.END_TAG:
                    if (tagname.equalsIgnoreCase("questionBlock")) {
                        questionsClass.setAnswer(answers);
                        answers = new HashMap<>();
                        questions.add(questionsClass);
                    } else if (tagname.equalsIgnoreCase("question")) {
                        questionsClass.setQuestion(text);
                    } else if (tagname.equalsIgnoreCase("a")) {
                        answers.put("1", text);
                    } else if (tagname.equalsIgnoreCase("b")) {
                        answers.put("2", text);
                    } else if (tagname.equalsIgnoreCase("c")) {
                        answers.put("3", text);
                    } else if (tagname.equalsIgnoreCase("d")) {
                        answers.put("4", text);
                    } else if (tagname.equalsIgnoreCase("answer")) {
                        questionsClass.setCorrectAnswer(text);
                    }
                    break;

                default:
                    break;
            }
            eventType = parser.next();
        }
        return questions;
    }

    public interface answerInterface {
        void trueAnswer();

        void wrongAnswer();
    }

    public String setEarnedMoney(int QQ) {
        String earnedMoney = "0/0";

        for (int i = 0; i < getMoneyButton().length; i++) {

            if (i == QQ) {
                getMoneyButton()[i].setBackgroundResource(R.drawable.neutral_border);
            } else if ((i == 1 || i == 5 || i == 8) && i != QQ) {
                getMoneyButton()[i].setBackgroundResource(R.drawable.money_border_blue);
            } else {
                getMoneyButton()[i].setBackgroundResource(R.drawable.money_border);
            }

        }

        switch (QQ) {
            case 0:
                earnedMoney = "0";
                moneyRound = "0";
                break;
            case 1:
                earnedMoney = "500";
                moneyRound = "0";
                break;
            case 2:
                earnedMoney = "1.000";
                moneyRound = "1.000";
                break;
            case 3:
                earnedMoney = "2.000";
                moneyRound = "1.000";
                break;
            case 4:
                earnedMoney = "4.000";
                moneyRound = "1.000";
                break;
            case 5:
                earnedMoney = "8.000";
                moneyRound = "1.000";
                break;
            case 6:
                earnedMoney = "16.000";
                moneyRound = "16.000";
                break;
            case 7:
                earnedMoney = "32.000";
                moneyRound = "16.000";
                break;
            case 8:
                earnedMoney = "64.000";
                moneyRound = "16.000";
                break;
            case 9:
                earnedMoney = "125.000";
                moneyRound = "125.000";
                break;
            case 10:
                earnedMoney = "250.000";
                moneyRound = "125.000";
                break;
            case 11:
                earnedMoney = "500.000";
                moneyRound = "125.000";
                break;

        }

        return earnedMoney;
    }


    public static void showWrongAnswerDialog() {
        FragmentManager fm = activity.getFragmentManager();

        if(mediaPlayer != null)
        mediaPlayer.stop();
        wrongAnswerDialog dialogFragment = new wrongAnswerDialog();
        try{dialogFragment.show(fm, "Sample Fragment");
        }
        catch (Exception e){

        }

    }


    public static void showWinnerDialog() {
        FragmentManager fm = activity.getFragmentManager();

        if(mediaPlayer != null)
        mediaPlayer.stop();
        winnerDialog dialogFragment = new winnerDialog();
        dialogFragment.show(fm, "Sample Fragment");
    }


    public void RightButtonIsAlwaysGreen(final String rightAnswer) {
        final Button rightAnswerButton;

        if (a.getTag().equals(rightAnswer)) {
            rightAnswerButton = a;
        } else if (b.getTag().equals(rightAnswer)) {
            rightAnswerButton = b;
        } else if (c.getTag().equals(rightAnswer)) {
            rightAnswerButton = c;
        } else {
            rightAnswerButton = d;
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rightAnswerButton.setBackgroundResource(R.drawable.correct_answer);
            }
        }, waitQuestion);

    }

    public void askPublic(final ImageButton publicImg, final String rightAnswer) {

        publicImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (publicBoolean) {
                    startMusic(R.raw.new_question_sound);

                    publicImg.setEnabled(false);
                    publicImg.setVisibility(View.GONE);

                    ArrayList<Button> diagramLines = new ArrayList<Button>();
                    diagramLines.add(a);
                    diagramLines.add(b);
                    diagramLines.add(c);
                    diagramLines.add(d);
                    ArrayList<Button> writeLines = new ArrayList<Button>();
                    for (int i = 0; i < diagramLines.size(); i++) {
                        if (!diagramLines.get(i).getTag().equals(rightAnswer)) {
                            writeLines.add(diagramLines.get(i));
                        }
                    }
                    Collections.shuffle(writeLines);
                    writeLines.get(0).setText("");
                    writeLines.get(1).setText("");
                }
            }
        });

    }


    public static class telephoneJoker extends DialogFragment {
        ImageView ia, ib, ic, id;
        ImageView lineImg;
        Button dismiss;
        int total = 10;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.diagram_dialog, container, false);
            getDialog().setTitle(" ");
            findDiagramViews(rootView);

            ArrayList<ImageView> lines = new ArrayList<ImageView>();
            lines.add(ia);
            lines.add(ib);
            lines.add(ic);
            lines.add(id);
            ArrayList<ImageView> xd = new ArrayList<ImageView>();

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).getTag().equals(publicAnswer)) {
                    lineImg = lines.get(i);
                    Random r = new Random();
                    int Low = 4;
                    int High = 8;
                    int Result = r.nextInt(High - Low) + Low;
                    total = total - Result;
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                            lineImg.getLayoutParams();
                    params.weight = (float) Result;
                    lineImg.setLayoutParams(params);
                } else {
                    xd.add(lines.get(i));
                }
            }
            int[] gg = diagramCounter(total, 3);
            for (int i = 0; i < xd.size(); i++) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                        xd.get(i).getLayoutParams();
                params.weight = (float) gg[i];
                xd.get(i).setLayoutParams(params);
            }

            dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

            return rootView;
        }

        void findDiagramViews(View view) {

            dismiss = (Button) view.findViewById(R.id.dismiss);
            ia = (ImageView) view.findViewById(R.id.ia);
            ib = (ImageView) view.findViewById(R.id.ib);
            ic = (ImageView) view.findViewById(R.id.ic);
            id = (ImageView) view.findViewById(R.id.id);
        }

    }

    public static class makeJokerDialog extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.phone_call_dialog, container, false);
            getDialog().setTitle(" ");

            Button okButton = (Button) rootView.findViewById(R.id.thank_you_button);
            TextView jokerSay = (TextView) rootView.findViewById(R.id.telephone_desc);
            jokerSay.setText(telephoneJokerSays);

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

            return rootView;
        }
    }


    public void callShowWithdraw() {

        FragmentManager fm = activity.getFragmentManager();
        showWithdrawDialog dialogFragment = new showWithdrawDialog();
        dialogFragment.show(fm, "Sample Fragment");

    }


    public static class showWithdrawDialog extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.withdraw_dialog, container, false);
            getDialog().setTitle(" ");
            Button yesButton = (Button) rootView.findViewById(R.id.yes_button);
            Button noButton = (Button) rootView.findViewById(R.id.no_button);

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    status = 1;
                    showWrongAnswerDialog();

                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });


            return rootView;
        }
    }

    public void telephonJokerStrings(final String answer, final ImageButton jokerButton) {
        jokerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusic(R.raw.new_question_sound);

                String jokerSays = "....";
                String extra;

                if (answer.equals("1")) extra = "A";
                else if (answer.equals("2")) extra = "B";
                else if (answer.equals("3")) extra = "C";
                else extra = "D";

                Random random = new Random();
                int i = random.nextInt(8);

                switch (i) {

                    case 0:
                        jokerSays = activity.getResources().getString(R.string.joker1);
                        break;

                    case 1:
                        jokerSays = activity.getResources().getString(R.string.joker2) + " " + extra;
                        break;

                    case 2:
                        jokerSays = activity.getResources().getString(R.string.joker3) + " " + extra + ".";
                        break;

                    case 3:
                        jokerSays = activity.getResources().getString(R.string.joker4) + " " + extra + ".";
                        break;

                    case 4:
                        jokerSays = activity.getResources().getString(R.string.joker5) + " " + extra + ".";
                        break;

                    case 5:
                        jokerSays = activity.getResources().getString(R.string.joker6) + " " + extra + ".";
                        break;

                    case 6:
                        jokerSays = activity.getResources().getString(R.string.joker7)+ " " + extra + ".";
                        break;

                    case 7:
                        jokerSays = activity.getResources().getString(R.string.joker8)+ " " + extra + activity.getResources().getString(R.string.joker8helper);
                        break;

                }
                telephoneJokerSays = jokerSays;
                FragmentManager fm = activity.getFragmentManager();
                makeJokerDialog dialogFragment = new makeJokerDialog();
                dialogFragment.show(fm, "Joker");

                jokerButton.setEnabled(false);
                jokerButton.setVisibility(View.GONE);

            }
        });

    }

    public static int[] diagramCounter(int number, int numberOfParts) {
        Random r = new Random();
        HashSet<Integer> uniqueInts = new HashSet<Integer>();
        uniqueInts.add(0);
        uniqueInts.add(number);
        int array_size = numberOfParts + 1;
        while (uniqueInts.size() < array_size) {
            uniqueInts.add(1 + r.nextInt(number - 1));
        }
        Integer[] dividers = uniqueInts.toArray(new Integer[array_size]);
        Arrays.sort(dividers);
        int[] results = new int[numberOfParts];
        for (int i = 1, j = 0; i < dividers.length; ++i, ++j) {
            results[j] = dividers[i] - dividers[j];
        }
        return results;
    }

    public void showJokerDialog(final ImageButton publicImg, String answer) {

        publicAnswer = answer;
        publicImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMusic(R.raw.new_question_sound);
                FragmentManager fm = activity.getFragmentManager();
                telephoneJoker dialogFragment = new telephoneJoker();
                dialogFragment.show(fm, "Joker");
                publicImg.setEnabled(false);
                publicImg.setVisibility(View.GONE);

            }
        });
    }

    public static class wrongAnswerDialog extends DialogFragment {
        String earned = "";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.game_end_dialog, container, false);
            getDialog().setTitle("");

            TextView moneyTxt = (TextView) rootView.findViewById(R.id.desc_won);


            if (status == 1)
                earned = moneyEarned;
            else if (status == 0)
                earned = moneyRound;

            moneyTxt.setText(earned + " " + getActivity().getResources().getString(R.string.currency));

            if(mediaPlayer != null)
            mediaPlayer.stop();


            //umjesto 1 je bio time
            LeaderboardHelper leaderboardHelper = new LeaderboardHelper(earned, 1);



            TextView desc = (TextView) rootView.findViewById(R.id.desc_view);
            desc.setText(getActivity().getResources().getString(R.string.game_over));
            this.setCancelable(false);

            LeaderboardDatabase leaderboardDatabase = new LeaderboardDatabase(activity);
            leaderboardDatabase.addScore(leaderboardHelper);

            Button backToMainButton = (Button) rootView.findViewById(R.id.go_back_to_main);
            backToMainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    getActivity().finish();
                }
            });

            status = 0;
            return rootView;
        }
    }


    public static class winnerDialog extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.game_end_dialog, container, false);
            getDialog().setTitle("");

            TextView money = (TextView) rootView.findViewById(R.id.desc_won);
            String oneM = "1.000.000";

            money.setText(oneM + " " + getActivity().getResources().getString(R.string.currency));

            if(mediaPlayer != null)
            mediaPlayer.stop();

            //umjesto 1 je bio time
            LeaderboardHelper leaderboardHelper = new LeaderboardHelper(oneM, 1);

            TextView desc = (TextView) rootView.findViewById(R.id.desc_view);
            desc.setText(getActivity().getResources().getString(R.string.game_over_final));
            this.setCancelable(false);

            LeaderboardDatabase leaderboardDatabase = new LeaderboardDatabase(activity);
            leaderboardDatabase.addScore(leaderboardHelper);

            Button backToMainButton = (Button) rootView.findViewById(R.id.go_back_to_main);
            backToMainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });

            status = 0;

            return rootView;
        }
    }


    public void startMusic(int i) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("ses", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("ses", "").equals("ok")) {
            final MediaPlayer mp = MediaPlayer.create(activity, i);
            mp.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mp.stop();
                }
            }, 2200);
        }
    }


}

