package com.appsfs.sfs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import com.appsfs.sfs.Objects.Shop;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by longdv on 4/18/16.
 */
public class DatabaseHelperShop extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "shop.db";
    private static final String TABLE_NAME = "shop_info";
    private static final int DATABASE_VERSION = 1;


    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PRODUCT = "product";
    private static final String COLUMN_MONEY = "money";
    private static final String COLUMN_LAT = "latitude";
    private static final String COLUMN_LONG = "longitude";

    public static final String[] COLS = {COLUMN_ID, COLUMN_NAME,COLUMN_PHONE,COLUMN_ADDRESS,
            COLUMN_PRODUCT,COLUMN_MONEY,COLUMN_LAT,COLUMN_LONG};

    private static DatabaseHelperShop helper;
    private static SQLiteDatabase mDb;

    private static String FILE_DIR = "SFS";

    public static synchronized DatabaseHelperShop getInstance(Context context)
    {
        if(helper == null)
        {
            helper = new DatabaseHelperShop(context);
        }
        return helper;
    }

    private DatabaseHelperShop(Context context)
    {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + FILE_DIR
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSessionTable = "create table if not exists shop_info" +
                "( id integer primary key autoincrement,name text,phone text,address text UNIQUE,product text,money int,latitude double,longitude double)";
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
     * Insert shop infomation when register
     ************************************************************************************************/
    public long insertShopInfo(Shop shopInfo)
    {
        boolean isOpenDb = openDatabase();
        if (!isOpenDb) {
            return -1;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLS[1], shopInfo.getName());
            contentValues.put(COLS[2], shopInfo.getPhoneNumber());
            contentValues.put(COLS[3], shopInfo.getAddress());
            contentValues.put(COLS[4], shopInfo.getProduct());
            contentValues.put(COLS[5], shopInfo.getMoney());
            contentValues.put(COLS[6], shopInfo.getLatitude());
            contentValues.put(COLS[7], shopInfo.getLongitude());
            return mDb.insert(TABLE_NAME, null, contentValues);
        }


    }

    /***********************************************************************************************
     * Delete shop infomation based on phone number
     ************************************************************************************************/
    public int deleteShopInfo(String phone)
    {
        openDatabase();
        return mDb.delete(TABLE_NAME, "phone=?", new String[]{phone});
    }

    /***********************************************************************************************
     * Update all shop
     ************************************************************************************************/
    public boolean updateItems(Shop shopInfo){
        openDatabase();
        try {
            final ContentValues contentValues = new ContentValues();
            contentValues.put(COLS[1], shopInfo.getName());
            contentValues.put(COLS[2], shopInfo.getPhoneNumber());
            contentValues.put(COLS[3], shopInfo.getAddress());
            contentValues.put(COLS[4], shopInfo.getProduct());
            contentValues.put(COLS[5], shopInfo.getMoney());
            contentValues.put(COLS[6], shopInfo.getLatitude());
            contentValues.put(COLS[7], shopInfo.getLongitude());

            mDb.update(TABLE_NAME, contentValues, COLS[1] + "= ?", new String[]{shopInfo.getName()});
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }

    /***********************************************************************************************
     * Get shop item
     ************************************************************************************************/
    public Shop getShopItem(String phone){
        Cursor cursor = null;
        openDatabase();
        try{
            String SQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLS[2] + " = '" + phone +"'";
            cursor = mDb.rawQuery(SQL, null);
            if(cursor.moveToFirst()){
                Shop ret = new Shop();
                ret.setName(cursor.getString(1));
                ret.setPhoneNumber(cursor.getString(2));
                ret.setAddress(cursor.getString(3));
                ret.setProduct(cursor.getString(4));
                ret.setMoney(cursor.getInt(5));
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
     * Get All Shop
     ************************************************************************************************/
    public ArrayList<Shop> getAllShops() {
        ArrayList<Shop> shopList = new ArrayList<Shop>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        openDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Shop ret = new Shop();
                ret.setName(cursor.getString(1));
                ret.setPhoneNumber(cursor.getString(2));
                ret.setAddress(cursor.getString(3));
                ret.setProduct(cursor.getString(4));
                ret.setMoney(cursor.getInt(5));
                ret.setLatitude(cursor.getDouble(6));
                ret.setLongitude(cursor.getDouble(7));
                // Adding contact to list
                shopList.add(ret);
            } while (cursor.moveToNext());
        }

        // return shop list
        return shopList;
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

    /***********************************************************************************************
     * Insert shop information when edit infomation
     ************************************************************************************************/

    public boolean updatePhoneInformation(String phone,Shop shop) {
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, shop.getName());
        contentValues.put(COLUMN_ADDRESS, shop.getAddress());
        contentValues.put(COLUMN_PRODUCT, shop.getProduct());
        contentValues.put(COLUMN_MONEY, shop.getMoney());
        contentValues.put(COLUMN_LAT, shop.getLatitude());
        contentValues.put(COLUMN_LONG, shop.getLongitude());
        long ret = mDb.update(TABLE_NAME, contentValues,COLS[2] + "=?",new String[]{phone});
        if (ret == -1) {
            return false;
        } else {
            return true;
        }

    }




}
