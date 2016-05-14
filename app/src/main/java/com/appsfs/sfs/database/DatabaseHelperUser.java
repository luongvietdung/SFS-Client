package com.appsfs.sfs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.appsfs.sfs.Objects.User;
import com.appsfs.sfs.api.sync.UserSync;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by longdv on 4/9/16.
 */
public class DatabaseHelperUser extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_NAME = "user_info";
    private static final int DATABASE_VERSION = 1;


    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_STATUS = "status";

    public static final String[] COLS = {COLUMN_ID, COLUMN_EMAIL,COLUMN_PHONE,
                                            COLUMN_PASSWORD, COLUMN_ROLE,COLUMN_STATUS};

    private static DatabaseHelperUser helper;
    private static SQLiteDatabase mDb;

    private static String FILE_DIR = "SFS";

    public static synchronized DatabaseHelperUser getInstance(Context context)
    {
        if(helper == null)
        {
            helper = new DatabaseHelperUser(context);
        }
        return helper;
    }

    private DatabaseHelperUser(Context context)
    {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + FILE_DIR
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

        String createSessionTable = "create table if not exists user_info" +
                "( id integer primary key autoincrement,email text,phone text UNIQUE,password text,role int,status int)";
        sqLiteDatabase.execSQL(createSessionTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    @Override
    protected void finalize() throws Throwable {
        if(helper != null) helper.close();
        super.finalize();
    }

    private static boolean openDatabase()
    {
        try
        {
            if ((mDb == null) || ((mDb != null) && (!mDb.isOpen())))
            {
                mDb = helper.getWritableDatabase();
            }
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public long insertUserInfo(User userInfo)
    {
        boolean isOpenDb = openDatabase();
        if (!isOpenDb) {
            return -1;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLS[1], userInfo.getEmail());
            contentValues.put(COLS[2], userInfo.getPhoneNumbers());
            contentValues.put(COLS[3], userInfo.getPassword());
            contentValues.put(COLS[4], userInfo.getRole());
            contentValues.put(COLS[5], userInfo.getStatus());
            return mDb.insert(TABLE_NAME, null, contentValues);
        }
    }

    public long insertUserInfo(UserSync user) {
        boolean isOpenDb = openDatabase();
        if (!isOpenDb) {
            return -1;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLS[1], "");
            contentValues.put(COLS[2], user.getPhone());
            contentValues.put(COLS[3], "");
            contentValues.put(COLS[4], user.getRole());
            contentValues.put(COLS[5], 0);
            return mDb.insert(TABLE_NAME, null, contentValues);
        }
    }

    public int deleteUserInfo(long rowId)
    {
        openDatabase();
        return mDb.delete(TABLE_NAME, "_id=?", new String[]{String.valueOf(rowId)});
    }
    public int deleteUserInfo(String phoneNumber)
    {
        openDatabase();
        return mDb.delete(TABLE_NAME, "phone=?", new String[]{phoneNumber});
    }

    public boolean updateItems(User userInfo){
        openDatabase();
        try {
            final ContentValues contentValues = new ContentValues();
            contentValues.put(COLS[1], userInfo.getEmail());
            contentValues.put(COLS[2], userInfo.getPhoneNumbers());
            contentValues.put(COLS[3], userInfo.getPassword());
            contentValues.put(COLS[4], userInfo.getRole());
            contentValues.put(COLS[5], userInfo.getStatus());

            mDb.update(TABLE_NAME, contentValues, COLS[2] + "= ?", new String[]{userInfo.getPhoneNumbers()});
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> ret = null;
        Cursor cursor = null;
        openDatabase();
        try {
            cursor = mDb.rawQuery("select *from" + TABLE_NAME, null);
            if(cursor != null && cursor.getCount() > 0) {
                ret = new ArrayList<User>();
                while(cursor.moveToNext()){
                    User user = new User();
                    user.setEmail(cursor.getString(1));
                    user.setPhoneNumbers(cursor.getString(2));
                    user.setPassword(cursor.getString(3));
                    user.setRole(cursor.getInt(4));
                    user.setStatus(cursor.getInt(5));
                    ret.add(user);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            return ret;
        } finally{
            if(cursor != null) cursor.close();
        }
        return ret;
    }

    /***********************************************************************************************
    * Insert State Online user
    ************************************************************************************************/

    public boolean updateStatus(String phoneNumber,Integer status) {
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        long ret = mDb.update(TABLE_NAME, contentValues,COLS[2] + "=?",new String[]{phoneNumber});
        if (ret == -1) {
            return false;
        } else {
            return true;
        }

    }

    /***********************************************************************************************
     * Insert phone number when edit
     ************************************************************************************************/

    public boolean updatePhoneNumber(String oldPhone,String newPhone,String email) {
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PHONE, newPhone);
        contentValues.put(COLUMN_EMAIL, email);
        long ret = mDb.update(TABLE_NAME, contentValues,COLS[2] + "=?",new String[]{oldPhone});
        if (ret == -1) {
            return false;
        } else {
            return true;
        }

    }

    /***********************************************************************************************
     * Check login user
     ************************************************************************************************/

    public boolean checkLogin(String phone_number, String password)
    {
        Cursor mCursor = null;
        openDatabase();
//        String sql = "select role from" + TABLE_NAME + " WHERE phone=? AND password=?";
//
//        mCursor = mDb.rawQuery(sql,new String[]{phone_number,password});

        mCursor = mDb.query(TABLE_NAME,null,"phone=? AND password=?", new String[]{phone_number,password},null,null,null);
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    /***********************************************************************************************
     * Check User exist
     ************************************************************************************************/

    public boolean checkUser(String phone_number)
    {
        Cursor mCursor = null;
        openDatabase();
        mCursor = mDb.query(TABLE_NAME, null, "phone=?", new String[]{phone_number}, null, null, null);
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }


    /***********************************************************************************************
     * Get  Users
     ************************************************************************************************/

    public User getUserItem(String phone,String password){
        Cursor mCursor = null;
        openDatabase();
        try{
            mCursor = mDb.query(TABLE_NAME, null, "phone=? AND password=?", new String[]{phone, password}, null, null, null);
            if(mCursor.moveToFirst()){
                User user = new User();
                user.setEmail(mCursor.getString(1));
                user.setPhoneNumbers(mCursor.getString(2));
                user.setPassword(mCursor.getString(3));
                user.setRole(mCursor.getInt(4));
                user.setStatus(mCursor.getInt(5));
                return user;
            }
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            if(mCursor != null) mCursor.close();
        }
        return null;
    }

}
