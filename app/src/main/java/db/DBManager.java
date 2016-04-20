package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.List;
/**
 * Created by disagree on 2016/4/19.
 */
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }
    /**
     * add lessons
     * @param lessons
     */
   /* public void add(List<Lesson> lessons) {
        db.beginTransaction();  //开始事务
        try {
            for (Lesson lesson : lessons) {
                db.execSQL("INSERT INTO lesson VALUES (NULL, ?, ?, ?, ?, ?)",
                        new Object[]{lesson.ClassName, lesson.ClassPlace,lesson.ClassTimeWeek,lesson.ClassTimeNode,lesson.ClassTeacher});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }*/



    /**
     * query all lessons, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM resident", null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
