package com.example.schooltips;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Quizz {
    static private final HashMap<ThemeQuizz, ArrayList<Question>> QUIZZ_LIST = new HashMap<>();

    private static final Random random = new Random();
    private final ThemeQuizz theme;
    private final int nbQuestions;
    private final ArrayList<Question> listeQuestionsDejaPosees = new ArrayList<>();
    private final ArrayList<Integer> listeErreurs = new ArrayList<>();
    private int[] listeQuestions; //liste prédéfinie de questions quand on corrige ses erreurs
    private String solution;
    private int questionNb = 0;
    private Question questionEnCours;

    public Quizz(ThemeQuizz theme, int nbQuestions) {
        this.theme = theme;
        this.nbQuestions = nbQuestions;
    }

    public Quizz(ThemeQuizz theme, int[] listeQuestions) {
        this.theme = theme;
        this.nbQuestions = listeQuestions.length;
        this.listeQuestions = listeQuestions;
    }

    static private String loadJSONFromAsset(String filename, AssetManager assets) {
        String json = null;
        try {
            InputStream is = assets.open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    static public void loadQuizz(AssetManager assets) {
        class LoadJSON extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                for (ThemeQuizz theme : ThemeQuizz.values()) {
                    ArrayList<Question> questions = new ArrayList<>();
                    try {
                        JSONArray questionsJ = new JSONArray(loadJSONFromAsset(theme.name() + ".json", assets));

                        for (int i = 0; i < questionsJ.length(); i++) {
                            JSONObject qj = questionsJ.getJSONObject(i);

                            JSONArray propositionsJ = qj.getJSONArray("propositions");
                            String[] propositions = new String[propositionsJ.length()];
                            for (int j = 0; j < propositionsJ.length(); j++)
                                propositions[j] = propositionsJ.getString(j);

                            Question question = new Question(qj.getInt("id"), qj.getString("question"), propositions, qj.getString("difficulty"));
                            questions.add(question);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    QUIZZ_LIST.put(theme, questions);
                }
                return null;
            }
        }
        LoadJSON lj = new LoadJSON();
        lj.execute();
    }

    public boolean nextQuestion(String previousAnswer) {
        if (previousAnswer != null) {
            questionNb++;
            if (!previousAnswer.equals(solution)) {
                listeErreurs.add(questionEnCours.getId());
            }
        }

        if (questionNb < nbQuestions) {
            if (listeQuestions != null) {
                //get question from list
                questionEnCours = QUIZZ_LIST.get(theme).get(listeQuestions[questionNb]);
            } else {
                //get random question from theme
                do
                    questionEnCours = QUIZZ_LIST.get(theme).get(random.nextInt(QUIZZ_LIST.get(theme).size()));
                while (listeQuestionsDejaPosees.contains(questionEnCours));

                listeQuestionsDejaPosees.add(questionEnCours);
            }

            solution = questionEnCours.getPropositions()[0];

            return true;
        } else {
            return false;
        }
    }

    public Question getQuestion() {
        return questionEnCours;
    }

    public int getNbQuestions() {
        return nbQuestions;
    }

    public int getQuestionNb() {
        return questionNb;
    }

    public int[] getListeErreurs() {
        int[] ret = new int[listeErreurs.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = listeErreurs.get(i);
        }
        return ret;
    }

    public ThemeQuizz getTheme() {
        return theme;
    }
}
