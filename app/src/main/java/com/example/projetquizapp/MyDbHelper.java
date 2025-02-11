package com.example.projetquizapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QuizApp DB";
    public static final String TABLE_USERS = "USERS";
    public static final String TABLE_TOPIC = "TOPICS";
    public static final String TABLE_QUESTIONS = "QUESTIONS";

    public static final String TABLE_USERDATA = "USERDATA";
    public MyDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE USERS (username text PRIMARY KEY, password text);");
        sqLiteDatabase.execSQL("CREATE TABLE TOPICS (topicname text PRIMARY KEY);");
        sqLiteDatabase.execSQL("CREATE TABLE QUESTIONS (id integer PRIMARY KEY autoincrement, question text, a text, b text, c text, d text, Reponse text, topic text);");
        sqLiteDatabase.execSQL("CREATE TABLE USERDATA (id integer PRIMARY KEY autoincrement, username text, date text, marks text , topic text);");

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public long createUserAccount(String username,String password){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        long result = database.insert(TABLE_USERS,null,values);
        database.close();
        return  result;
    }


    public int loginUser(String username,String password){
        SQLiteDatabase database = this.getReadableDatabase();
        try (Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE username = '" + username + "' and password = '" + password + "';", null)) {
            if(cursor.getCount() == 1){
                cursor.close();
                return 1;
            }else {
                cursor.close();
                return -1;
            }
        }
    }
    public long createTopic(String topicName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("topicname",topicName);
        long result = database.insert(TABLE_TOPIC, null, contentValues);
        database.close();
        return result;
    }
    public ArrayList<String> getAllTopics(){
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<String> result = new ArrayList<>();
        try (Cursor cursor = database.rawQuery("SELECT * FROM TOPICS", null)) {
            if(cursor.moveToFirst()){
                do {
                    result.add(cursor.getString(0));
                }while (cursor.moveToNext());
            }
        }

        return result;
    }

    public int deleteTopic(String topic) {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_TOPIC,"topicname='"+topic+"';",null);
    }
    public long addQuestion(String question, String optionA, String optionB, String optionC, String optionD, String correctOption, String topic) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question",question);
        values.put("a",optionA);
        values.put("b",optionB);
        values.put("c",optionC);
        values.put("d",optionD);
        values.put("Reponse",correctOption);
        values.put("topic",topic);
        long result = database.insert(TABLE_QUESTIONS,null,values);
        database.close();
        return  result;
    }

    public ArrayList<Question> getQuestions(String topic){
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Question> result = new ArrayList<>();
        try (Cursor cursor = database.rawQuery("SELECT * FROM QUESTIONS WHERE topic='"+ topic +"'", null)) {
            if(cursor.moveToFirst()){
                do {
                    result.add(new Question(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6)));
                }while (cursor.moveToNext());
            }
        }
        return result;
    }

    public void addResult(String username, String date, String marks, String topic) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("date",date);
        values.put("marks",marks);
        values.put("topic",topic);
        database.insert(TABLE_USERDATA,null,values);
        database.close();
    }


    public void createDemoQuiz(){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("topicname","Android");
        database.insert(TABLE_TOPIC,null,values);
        values.clear();
        values.put("question"," Qu'est-ce qu'Android ?");
        values.put("a"," Un système d'exploitation mobile");
        values.put("b","Un langage de programmation");
        values.put("c"," Un navigateur web");
        values.put("d","Un réseau social");
        values.put("Reponse","A");
        values.put("topic","Android");
        database.insert(TABLE_QUESTIONS,null,values);
        values.clear();
        values.put("question","Quelle entreprise a développé Android ?");
        values.put("a"," Apple");
        values.put("b"," Microsoft");
        values.put("c","Samsung");
        values.put("d"," Google");
        values.put("Reponse","D");
        values.put("topic","Android");
        database.insert(TABLE_QUESTIONS,null,values);
        values.clear();
        values.put("question","Quel est le langage principal utilisé pour le développement d'applications Android ?");
        values.put("a","C++");
        values.put("b"," Python");
        values.put("c","Java");
        values.put("d","Ruby");
        values.put("Reponse","C");
        values.put("topic","Android");
        database.insert(TABLE_QUESTIONS,null,values);
        values.clear();

        values.put("question","Quel est le fichier de configuration principal d'une application Android ?");
        values.put("a","MainActivity.java");
        values.put("b"," build.gradle");
        values.put("c"," AndroidManifest.xml");
        values.put("d"," activity_main.xml");
        values.put("Reponse","C");
        values.put("topic","Android");
        database.insert(TABLE_QUESTIONS,null,values);
    }


}
