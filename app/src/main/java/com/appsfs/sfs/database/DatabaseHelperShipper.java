package com.appsfs.sfs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.appsfs.sfs.Objects.Shipper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by longdv on 4/18/16.
 */
public class DatabaseHelperShipper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "shipper.db";
    private static final String TABLE_NAME = "shipper_info";
    private static final int DATABASE_VERSION = 1;


    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_MONEY = "money";
    private static final String COLUMN_MONEY_SHIP = "money_ship";
    private static final String COLUMN_LAT = "latitude";
    private static final String COLUMN_LNG = "longitude";

    public static final String[] COLS = {COLUMN_ID, COLUMN_NAME,COLUMN_PHONE,COLUMN_ADDRESS,
            COLUMN_MONEY, COLUMN_MONEY_SHIP,COLUMN_LAT,COLUMN_LNG};

    private static DatabaseHelperShipper helper;
    private static SQLiteDatabase mDb;

    private static String FILE_DIR = "SFS";

    public static synchronized DatabaseHelperShipper getInstance(Context context)
    {
        if(helper == null)
        {
            helper = new DatabaseHelperShipper(context);
        }
        return helper;
    }

    private DatabaseHelperShipper(Context context)
    {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + FILE_DIR
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSessionTable = "create table if not exists shipper_info" +
                "( id integer primary key autoincrement,name text,phone text,address text UNIQUE,money int,money_ship int,latitude text,longitude text)";
        sqLiteDatabase.execSQL(createSessionTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
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


    /***********************************************************************************************
     * Insert shipper when register
     ************************************************************************************************/

    public long insertShipperInfo(Shipper shipperInfo)
    {
        boolean isOpenDb = openDatabase();
        if (!isOpenDb) {
            return -1;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLS[1], shipperInfo.getName());
            contentValues.put(COLS[2], shipperInfo.getPhoneNumber());
            contentValues.put(COLS[3], shipperInfo.getAddress());
            contentValues.put(COLS[4], shipperInfo.getMoney());
            contentValues.put(COLS[5], shipperInfo.getMoneyShip());
            contentValues.put(COLS[6], shipperInfo.getLatitude());
            contentValues.put(COLS[7], shipperInfo.getLongitude());
            return mDb.insert(TABLE_NAME, null, contentValues);
        }


    }

    /***********************************************************************************************
     * Delete shipper info
     ************************************************************************************************/

    public int deleteShipperInfo(long rowId)
    {
        openDatabase();
        return mDb.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(rowId)});
    }
    public int deleteShipperInfo(String phone)
    {
        openDatabase();
        return mDb.delete(TABLE_NAME, "phone=?", new String[]{phone});
    }

    /***********************************************************************************************
     * Update information shippers
     ************************************************************************************************/
    public boolean updateItems(Shipper shipperInfo){
        openDatabase();
        try {
            final ContentValues contentValues = new ContentValues();
            contentValues.put(COLS[1], shipperInfo.getName());
            contentValues.put(COLS[2], shipperInfo.getPhoneNumber());
            contentValues.put(COLS[3], shipperInfo.getAddress());
            contentValues.put(COLS[4], shipperInfo.getMoney());
            contentValues.put(COLS[5], shipperInfo.getMoneyShip());
            contentValues.put(COLS[6], shipperInfo.getLatitude());
            contentValues.put(COLS[7], shipperInfo.getLongitude());

            mDb.update(TABLE_NAME, contentValues, COLS[1] + "= ?", new String[]{shipperInfo.getName()});
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
    /***********************************************************************************************
     * Get all shipper infomation
     ************************************************************************************************/
    public ArrayList<Shipper> getAllShipper(){
        ArrayList<Shipper> ret = new ArrayList<Shipper>();
        Cursor cursor = null;
        openDatabase();
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_NAME;
            cursor = mDb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Shipper shipper = new Shipper();
                    shipper.setName(cursor.getString(1));
                    shipper.setPhoneNumber(cursor.getString(2));
                    shipper.setAddress(cursor.getString(3));
                    shipper.setMoney(cursor.getInt(4));
                    shipper.setMoneyShip(cursor.getInt(5));
                    shipper.setLatitude(cursor.getDouble(6));
                    shipper.setLongitude(cursor.getDouble(7));
                    ret.add(shipper);
                } while(cursor.moveToNext());
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
     * Get shipper item
     ************************************************************************************************/
    public Shipper getShipperItem(String phone){
        Cursor cursor = null;
        openDatabase();
        try{
            String SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLS[2] + " = '" + phone +"'";
            cursor = mDb.rawQuery(SQL, null);
            if(cursor.moveToFirst()){
                Shipper ret = new Shipper();
                ret.setName(cursor.getString(1));
                ret.setPhoneNumber(cursor.getString(2));
                ret.setAddress(cursor.getString(3));
                ret.setMoney(cursor.getInt(4));
                ret.setMoneyShip(cursor.getInt(5));
                ret.setLatitude(cursor.getDouble(6));
                ret.setLongitude(cursor.getDouble(7));
                return ret;
            }
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            if(cursor != null) cursor.close();
        }
        return null;
    }

    /***********************************************************************************************
     * Insert Location shipper
     ************************************************************************************************/

    public boolean updateLocation(String phoneNumber,double lat,double lng) {
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LAT, lat);
        contentValues.put(COLUMN_LNG, lng);
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

    public boolean updatePhoneNumber(String oldPhone,String newPhone) {
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PHONE, newPhone);
        long ret = mDb.update(TABLE_NAME, contentValues,COLS[2] + "=?",new String[]{oldPhone});
        if (ret == -1) {
            return false;
        } else {
            return true;
        }

    }


}
