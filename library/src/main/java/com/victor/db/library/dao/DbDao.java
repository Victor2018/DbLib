package com.victor.db.library.dao;import android.content.ContentValues;import android.content.Context;import android.database.Cursor;import android.database.sqlite.SQLiteDatabase;import android.net.Uri;import android.os.Bundle;import android.util.Log;import com.victor.db.library.data.Movie;import com.victor.db.library.interfaces.DbInterface;import com.victor.db.library.model.DataBase;import com.victor.db.library.util.Constant;import java.util.ArrayList;import java.util.List;/** * Created by victor on 2015/12/25. */public class DbDao implements DbInterface{    private String TAG = "DbDao";    protected Context mContext;    protected DataBase db;    public DbDao(Context context){        mContext = context;        db = new DataBase(mContext, null, null, 0);    }    @Override    public void insert(List<Movie> datas) {        Log.e(TAG, "insert()......");        if (!db.isTbExist(Constant.TB.MOVIE)) {            return;        }        long startTime = System.currentTimeMillis();        if(datas != null && datas.size() > 0){            SQLiteDatabase sdb = db.getWritableDatabase();            sdb.beginTransaction();            try {                for(Movie info:datas){                    ContentValues values = info.translate2ContentValues();                    Log.e(TAG,"insert-name = " + values.get("name"));                    values.put("category_type", String.valueOf(Constant.Action.LIVE_ACTION));                    values.put("action_type",String.valueOf(Constant.Action.LIVE_ACTION));                    values.put("current", String.valueOf(0));                    sdb.insert(Constant.TB.MOVIE, null, values);                }                sdb.setTransactionSuccessful();            } catch (Exception e) {                // TODO: handle exception            } finally {                if (sdb != null) {                    sdb.endTransaction();                    sdb.close();                }            }        }        Log.e(TAG, "insert time=" + (System.currentTimeMillis() - startTime));    }    @Override    public void delete(int categoryType, int actionType) {        Log.e(TAG, "delete()......");        if (!db.isTbExist(Constant.TB.MOVIE)) {            return;        }        SQLiteDatabase sdb = db.getReadableDatabase();        try {            sdb.delete(Constant.TB.MOVIE, "category_type = ? and action_type=?", new String[]{String.valueOf(categoryType),String.valueOf(actionType)});        } catch (Exception e) {            // TODO: handle exception        }finally {            if (sdb != null) {                sdb.close();            }        }    }    @Override    public void update(Movie channel) {        Log.e(TAG, "update()......");        if (!db.isTbExist(Constant.TB.MOVIE)) {            return;        }        if(channel != null){            SQLiteDatabase sdb = db.getReadableDatabase();            try {                String channelId = channel.getChannelId();                ContentValues values = channel.translate2ContentValues();                Log.e(TAG,"update-name = " + values.get("name"));                values.put("category_type", String.valueOf(Constant.Action.LIVE_ACTION));                values.put("action_type",String.valueOf(Constant.Action.LIVE_ACTION));                values.put("current", String.valueOf(channel.getProgress()));                sdb.update(Constant.TB.MOVIE, values, "channelid = ?", new String[]{channelId});            } catch (Exception e) {                // TODO: handle exception            }finally {                if (sdb != null) {                    sdb.close();                }            }        }    }    @Override    public List<Movie> query(int categoryType, int actionType,boolean isLocalDb) {        Log.e(TAG, "query()......");        List<Movie> favList = new ArrayList<Movie>();        if (!db.isTbExist(Constant.TB.MOVIE)) {            return favList;        }        Uri uri;        if (isLocalDb) {            uri = Uri.parse(Constant.DbConfig.URI_PATH + Constant.TB.MOVIE);        } else {            uri = Uri.parse(Constant.DbConfig.CALLED_URI_PATH + Constant.TB.MOVIE);//跨进程查询其他应用的uri        }        Cursor cursor = mContext.getContentResolver().query(uri, null,                "category_type = ? and action_type = ?" ,                new String[]{String.valueOf(categoryType),String.valueOf(actionType)}, null);        try {            int row = cursor.getCount();            if (row > 0) {                for (int i = 0; i < cursor.getCount(); i++) {                    cursor.moveToPosition(i);                    Movie channel = new Movie();                    channel = channel.cursor2Bean(cursor);                    favList.add(channel);                }            }        } catch (Exception e) {        } finally {            if (cursor != null) {                cursor.close();            }        }        return favList;    }    @Override    public List<Movie> queryOtherApp(int categoryType, int actionType) {        Log.e(TAG, "queryOtherApp()......");        List<Movie> favList = new ArrayList<Movie>();        if (!db.isTbExist(Constant.TB.MOVIE)) {            return favList;        }        Uri uri = Uri.parse(Constant.DbConfig.CALLED_URI_PATH + Constant.TB.MOVIE);        Bundle bundle = mContext.getContentResolver().call(uri,"QUERY",null,null);        String data = bundle.getString("DATA");        Log.e(TAG,"query other app DATA = " + data);        return favList;    }}