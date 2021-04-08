package com.berkantcanerkanat.cuisine.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.berkantcanerkanat.cuisine.Singleton.User;

public class DatabaseOperations {

    Context context;
    SQLiteDatabase db;
    User user;
    public DatabaseOperations(Context context) {
        this.context = context;
        user = User.getInstance();
    }
    public void getTheIds(){
        System.out.println("db calıstı");
        db = context.openOrCreateDatabase("MyMeals", Context.MODE_PRIVATE,null);
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS mymealIds (id VARCHAR)");
            Cursor cursor = db.rawQuery("SELECT * FROM mymealIds",null);
            int idIx = cursor.getColumnIndex("id");
            while(cursor.moveToNext()){
                user.getIds().add(cursor.getString(idIx));
            }
            System.out.println(user.getIds().size());
            cursor.close();
        }catch (Exception ee){
            ee.printStackTrace();
        }
    }
    public void putTheIds(){
        try{
            db = context.openOrCreateDatabase("MyMeals", Context.MODE_PRIVATE,null);
            db.execSQL("DELETE FROM mymealIds");
            db.execSQL("CREATE TABLE IF NOT EXISTS mymealIds (id VARCHAR)");
            String sqlStatement = "INSERT INTO mymealIds (id) VALUES (?)";
            SQLiteStatement sqLiteStatement = db.compileStatement(sqlStatement);
            for(String s: user.getIds()){
                sqLiteStatement.bindString(1,s);
                sqLiteStatement.execute();
            }
            System.out.println("koyduk");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
