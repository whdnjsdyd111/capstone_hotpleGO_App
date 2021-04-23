package com.example.hotplego;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database{
    SQLiteDatabase mDB = null;
    DBOpenHelper mDBopenHelper = null;
    Cursor c;
    String sql;

    private static Database singletonDB;

    private Database(){

    }

    public static Database getInstance(){
        if(singletonDB == null) {
            singletonDB = new Database();
        }
        return singletonDB;

    }

    // 수정가능한 데이터베이스를 생성
    public SQLiteDatabase open(Context context){
        if(mDBopenHelper == null){
            mDBopenHelper = new DBOpenHelper(context);
            mDB = mDBopenHelper.getWritableDatabase();

        }
        return mDB;

    }

    // 아이디 검색
    public Cursor searchId(String id){
        sql = "SELECT id FROM " + mDBopenHelper.DB_TABLE_USER + " WHERE id = " + "'" + id + "'";
        // 입력한 아이디를 조건으로 테이블에서 아이디를 검색
        c = mDB.rawQuery(sql, null);
        c.moveToNext();

        return c; // 커서 리턴
    }

    // 비밀번호 검색
    public Cursor searchPw(String id){
        sql = "SELECT pw FROM " + mDBopenHelper.DB_TABLE_USER + " WHERE id = " + "'" + id + "'";
        // 입력한 아이디를 조건으로 테이블에서 비밀번호를 검색
        c = mDB.rawQuery(sql, null);
        c.moveToNext();

        return c; // 커서 리턴

    }

    // 이름 검색
    public Cursor searchName(String id){
        sql = "SELECT name FROM " + mDBopenHelper.DB_TABLE_USER + " WHERE id = " + "'" + id + "'";
        // 입력한 아이디를 조건으로 테이블에서 이름을 검색
        c = mDB.rawQuery(sql, null);
        c.moveToNext();

        return c; // 커서 리턴

    }

    //핸드폰 번호 검색
    public Cursor searchPhone(String id){
        sql = "SELECT phone FROM " + mDBopenHelper.DB_TABLE_USER + " WHERE id = " + "'" + id + "'";
        // 입력한 아이디를 조건으로 테이블에서 핸드폰번호를 검색
        c = mDB.rawQuery(sql, null);
        c.moveToNext();

        return c; // 커서 리턴

    }


    // 아이디 찾기
    public Cursor findId(String name, String brith){
        sql = "SELECT id FROM " + mDBopenHelper.DB_TABLE_USER + " WHERE name = " + "'" + name + "'" + " and " + "birth = " + "'" + brith + "'";
        c = mDB.rawQuery(sql, null);
        c.moveToNext();

        return c;
    }


    // 비밀번호 찾기
    public Cursor findPw(String id, String name, String birth){
        sql = "SELECT pw FROM " + mDBopenHelper.DB_TABLE_USER + " WHERE id = " + "'" + id + "'" + " and " + "name = "+ "'" + name + "'" + " and " + "birth = " + "'" + birth + "'";
        c = mDB.rawQuery(sql, null);
        c.moveToNext();

        return c;
    }

    // 이름 수정
    public void updateName(SQLiteDatabase db, String name, String id){
        String sql = "UPDATE " + mDBopenHelper.DB_TABLE_USER + " SET name='" + name + "' WHERE id='" + id + "';" ;
        db.execSQL(sql);
        // db.close();
    }

    // 핸드폰 번호 수정
    public void updatePhone(SQLiteDatabase db, String phoneNum, String id){
        String sql = "UPDATE " + mDBopenHelper.DB_TABLE_USER + " SET phone='" + phoneNum + "' WHERE id='" + id + "';" ;
        db.execSQL(sql);
        // db.close();
    }

    // 비밀번호 수정
    public void updatePw(SQLiteDatabase db, String pw, String id){
        String sql = "UPDATE " + mDBopenHelper.DB_TABLE_USER + " SET pw='" + pw + "' WHERE id='" + id + "';" ;
        db.execSQL(sql);
        // db.close();
    }
}
