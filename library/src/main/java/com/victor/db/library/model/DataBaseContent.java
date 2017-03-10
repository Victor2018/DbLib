package com.victor.db.library.model;import android.content.ContentProvider;import android.content.ContentUris;import android.content.ContentValues;import android.content.UriMatcher;import android.database.Cursor;import android.database.sqlite.SQLiteDatabase;import android.database.sqlite.SQLiteQueryBuilder;import android.net.Uri;import android.support.annotation.Nullable;import android.text.TextUtils;import android.util.Log;import com.victor.db.library.util.Constant;/** * Created by victor on 2017/3/8. */public class DataBaseContent extends ContentProvider{    private String TAG = "DataBaseContent";    protected DataBase db;    private static final int TB_MOVIE 					= 1;    private static final int TB_MOVIE_ID 				= 2;    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);    private static final String CALLED_URI = Constant.DbConfig.CALLED_URI_PATH + Constant.TB.MOVIE;    static {        uriMatcher.addURI(Constant.DbConfig.AUTHORITY,Constant.TB.MOVIE,TB_MOVIE);        uriMatcher.addURI(Constant.DbConfig.AUTHORITY,Constant.TB.MOVIE + "/#",TB_MOVIE_ID);    }    @Override    public boolean onCreate() {        db = new DataBase(getContext(),null,null,0);        return false;    }    @Nullable    @Override    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {        int type = uriMatcher.match(uri);        Log.e(TAG,"type = " + type);        String id = "";        if (type % 2 == 0) {            if (uri.getPathSegments().size() > 1) {                id = uri.getPathSegments().get(1);            }        }        if (!TextUtils.isEmpty(id)) {            if (selection == null) {                selection = "_id" + id;            } else {                selection = selection + "and _id" + id;            }        }        SQLiteDatabase sdb = db.getReadableDatabase();        SQLiteQueryBuilder sqb = new SQLiteQueryBuilder();        sqb.setTables(getTableName(uri));        Cursor cursor = sqb.query(sdb,projection,selection,selectionArgs,null,null,sortOrder);        return cursor;    }    @Nullable    @Override    public String getType(Uri uri) {        return null;    }    @Nullable    @Override    public Uri insert(Uri uri, ContentValues values) {        SQLiteDatabase sdb = db.getWritableDatabase();        String intsertUri = Constant.DbConfig.URI_PATH + getTableName(uri);        long id = sdb.insert(intsertUri, null, values);        uri = ContentUris.withAppendedId(uri, id);        getContext().getContentResolver().notifyChange(uri, null);        return uri;    }    @Override    public int delete(Uri uri, String selection, String[] selectionArgs) {        SQLiteDatabase sdb = db.getWritableDatabase();        String rowId = uri.getPathSegments().get(1);        return sdb.delete(getTableName(uri), "_id=" + rowId, null);    }    @Override    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {        SQLiteDatabase sdb = db.getWritableDatabase();        String rowId = uri.getPathSegments().get(1);        return sdb.update(getTableName(uri), values, "_id = " + rowId, null);    }    private String getTableName (Uri uri) {        int type = uriMatcher.match(uri);        String tableName = "";        switch (type) {            case TB_MOVIE:                tableName = Constant.TB.MOVIE;                break;            case TB_MOVIE_ID:                tableName = Constant.TB.MOVIE;                break;            default:                break;        }        return tableName;    }    /*@Nullable    @Override    public Bundle call(String method, String arg, Bundle extras) {        if (TextUtils.isEmpty(method)) {            return null;        }        if (method.equals("RESULT")) {            if (extras != null) {                if (extras.containsKey("RESULT")) {                    boolean result = extras.getBoolean("RESULT");                    Log.e(TAG,"---------------------- result-------------------------");                    Log.e(TAG,"result = ");                }            }        } else if (method.equals("DELETE")) {        } else if (method.equals("UPDATE")) {        } else if (method.equals("QUERY")) {        }        return null;    }*/}