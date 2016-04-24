package db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by disagree on 2016/4/20.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HouseKeeping.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE resident (id INTEGER PRIMARY KEY , username VARCHAR, " +
                "password VARCHAR,realname VARCHAR,address VARCHAR,rank FLOAT,phone VARCHAR)");
        db.execSQL("CREATE TABLE order (id INTEGER PRIMARY KEY , res_id INTEGER, " +
                "pro_id INTEGER,od_id INTEGER,state VARCHAR,price FLOAT,time TIME,rank INT,remark VARCHAR)");
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE resident ADD COLUMN other STRING");
    }
}