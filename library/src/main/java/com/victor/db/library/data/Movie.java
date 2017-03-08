package com.victor.db.library.data;import android.content.ContentValues;import android.database.Cursor;import java.lang.reflect.Field;/** * Created by victor on 2015/12/25. */public class Movie {    private String name;    private String icon;    private String img;    private String server;    private String lang;    private String time;    private String area;    private String channelId;    private String year;    private String type;    private String director;    private String memo;    private int progress;    private int category_type;    private int action_type;    private int current;    public int getCategory_type() {        return category_type;    }    public int getAction_type() {        return action_type;    }    public int getCurrent() {        return current;    }    public void setCategory_type(int category_type) {        this.category_type = category_type;    }    public void setAction_type(int action_type) {        this.action_type = action_type;    }    public void setCurrent(int current) {        this.current = current;    }    public void setName(String name) {        this.name = name;    }    public void setIcon(String icon) {        this.icon = icon;    }    public void setImg(String img) {        this.img = img;    }    public void setServer(String server) {        this.server = server;    }    public void setLang(String lang) {        this.lang = lang;    }    public void setTime(String time) {        this.time = time;    }    public void setArea(String area) {        this.area = area;    }    public void setChannelId(String channelId) {        this.channelId = channelId;    }    public void setYear(String year) {        this.year = year;    }    public void setType(String type) {        this.type = type;    }    public void setDirector(String director) {        this.director = director;    }    public void setMemo(String memo) {        this.memo = memo;    }    public void setProgress(int progress) {        this.progress = progress;    }    public String getName() {        return name;    }    public String getIcon() {        return icon;    }    public String getImg() {        return img;    }    public String getServer() {        return server;    }    public String getLang() {        return lang;    }    public String getTime() {        return time;    }    public String getArea() {        return area;    }    public String getChannelId() {        return channelId;    }    public String getYear() {        return year;    }    public String getType() {        return type;    }    public String getDirector() {        return director;    }    public String getMemo() {        return memo;    }    public int getProgress() {        return progress;    }    public ContentValues translate2ContentValues () {        ContentValues values = new ContentValues();        Field[] fields = getClass().getDeclaredFields();        for (Field info : fields) {            if (!info.isAccessible()) {                info.setAccessible(true);            }            String name = info.getName();            Object value;            try {                value = info.get(this);                if (value instanceof Byte) {                    values.put(name, (Byte) value);                } else if (value instanceof Short) {                    values.put(name, (Short) value);                } else if (value instanceof Integer) {                    values.put(name, (Integer) value);                } else if (value instanceof Long) {                    values.put(name, (Long) value);                } else if (value instanceof String) {                    values.put(name, (String) value);                } else if (value instanceof byte[]) {                    values.put(name, (byte[]) value);                } else if (value instanceof Boolean) {                    values.put(name, (Boolean) value);                } else if (value instanceof Character) {                    values.put(name, (String)value);                } else if (value instanceof Float) {                    values.put(name, (Float) value);                } else if (value instanceof Double) {                    values.put(name, (Double) value);                }            } catch (IllegalArgumentException e) {                // TODO Auto-generated catch block                e.printStackTrace();            } catch (IllegalAccessException e) {                // TODO Auto-generated catch block                e.printStackTrace();            }        }        return values;    }    public Movie cursor2Bean (Cursor cursor) throws IllegalAccessException {        if (cursor.isBeforeFirst()) {            cursor.moveToFirst();        }        Field[] fields = getClass().getDeclaredFields();        for (Field info : fields) {            String columnName = info.getName();            int columnIdx = cursor.getColumnIndex(columnName);            if (columnIdx != -1) {                if (!info.isAccessible()) {                    info.setAccessible(true);                }                Class<?> type = info.getType();                if (type == byte.class) {                    info.set(this, (byte) cursor.getShort(columnIdx));                } else if (type == short.class) {                    info.set(this, cursor.getShort(columnIdx));                } else if (type == int.class) {                    info.set(this, cursor.getInt(columnIdx));                } else if (type == long.class) {                    info.set(this, cursor.getLong(columnIdx));                } else if (type == String.class) {                    info.set(this, cursor.getString(columnIdx));                } else if (type == byte[].class) {                    info.set(this, cursor.getBlob(columnIdx));                } else if (type == boolean.class) {                    info.set(this, cursor.getInt(columnIdx) == 1);                } else if (type == Character.class) {                    info.set(this, cursor.getString(columnIdx));                } else if (type == float.class) {                    info.set(this, cursor.getFloat(columnIdx));                } else if (type == double.class) {                    info.set(this, cursor.getDouble(columnIdx));                }            }        }        return this;    }}