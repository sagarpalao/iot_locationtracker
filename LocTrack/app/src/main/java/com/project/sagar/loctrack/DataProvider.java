package com.project.sagar.loctrack;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class DataProvider extends android.content.ContentProvider {
    public DataProvider() {
    }

    @Override
    public int delete(android.net.Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(android.net.Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public android.net.Uri insert(android.net.Uri uri, android.content.ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public android.database.Cursor query(android.net.Uri uri, String[] projection, String selection,
                                         String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(android.net.Uri uri, android.content.ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static class DbHelper extends android.database.sqlite.SQLiteOpenHelper {

        private static final String DATABASE_NAME = "instachat.db";
        private static final int DATABASE_VERSION = 2;

        public DbHelper(android.content.Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(android.database.sqlite.SQLiteDatabase db) {
            db.execSQL("create table messages (_id integer primary key autoincrement, msg text, from_cid text, to_cid text, at datetime default current_timestamp);");
            db.execSQL("create table profile (_id integer primary key autoincrement, name text, chat_id text unique, count integer default 0, is_group integer default 0);");
        }

        @Override
        public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists messages");
            db.execSQL("drop table if exists profile");
            onCreate(db);
        }
    }
}
