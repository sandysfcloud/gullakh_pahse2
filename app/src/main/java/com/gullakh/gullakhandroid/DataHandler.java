package com.gullakh.gullakhandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by njfernandis on 01/10/15.
 */
public class DataHandler extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "GULLAKH";
    public static int DATABASE_VERSION = 1;
    public Context context;
    public SQLiteDatabase db;
    public static String android_metadata;
    public static String metainsert;


    public static String product;
    public static String size;
    public Cursor cr;
    public DataHandler(Context c)
    {
        super(c,DATABASE_NAME,null,DATABASE_VERSION);
        context=c;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       // addTable();
    }

    public void addTable() {
        try {


            // Log.w("***********************addtable","");

            db = this.getWritableDatabase();

            android_metadata= "CREATE TABLE IF NOT EXISTS session (id INTEGER PRIMARY KEY AUTOINCREMENT,sessn VARCHAR );";

            db.execSQL(android_metadata );

            android_metadata= "CREATE TABLE IF NOT EXISTS userlogin (id INTEGER PRIMARY KEY AUTOINCREMENT,user_id VARCHAR,contact_id VARCHAR,useremail VARCHAR,usermobile VARCHAR,usersession VARCHAR,profile VARCHAR );";

            db.execSQL(android_metadata );
            android_metadata= "CREATE TABLE IF NOT EXISTS mysearch (id INTEGER PRIMARY KEY AUTOINCREMENT,loantype VARCHAR,questans VARCHAR,data VARCHAR,created_date DATETIME );";

            db.execSQL(android_metadata );

           // android_metadata= "CREATE TABLE IF NOT EXISTS signindetails (id INTEGER PRIMARY KEY AUTOINCREMENT,email VARCHAR,mobno VARCHAR );";

            db.execSQL(android_metadata );

           /* metainsert="INSERT INTO android_metadata VALUES ('en_US');";
            db.execSQL(metainsert);

            //db.execSQL("DROP TABLE IF EXISTS table_category");
            product = "CREATE TABLE IF NOT EXISTS product(group_id INTEGER PRIMARY KEY AUTOINCREMENT,group_name VARCHAR,style_no VARCHAR,pear_reg VARCHAR," +
                    "pear_wide VARCHAR ,pear_narrow VARCHAR,apple_reg VARCHAR,apple_wide VARCHAR ,apple_narrow VARCHAR,lemon_reg VARCHAR,lemon_wide VARCHAR ,lemon_narrow VARCHAR," +
                    "remarks VARCHAR,desc VARCHAR ,padded VARCHAR,non_padded VARCHAR,wired VARCHAR,wire_free VARCHAR ,coverage VARCHAR,add_feature VARCHAR" +
                    ",fabric VARCHAR,mrp VARCHAR ,category VARCHAR );";
            db.execSQL( product );

           // db.execSQL("DROP TABLE IF EXISTS table_product");
            size= "CREATE TABLE IF NOT EXISTS size(size_id INTEGER PRIMARY KEY AUTOINCREMENT,style_no VARCHAR,size VARCHAR);";
            db.execSQL(size);*/






        } catch (Exception e1)
        {
            Log.d("error in table creation", e1.toString());
            Toast.makeText(context, "errror in table creation .." + e1.toString(), Toast.LENGTH_LONG).show();
        }


    }
    public void insertVal() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put("group_name", "Core");
            values.put("style_no", "A001");
            values.put("pear_reg", "preg");
            values.put("pear_wide", "pwide");
            values.put("pear_narrow", "pnarrow");
            values.put("apple_reg", "areg");
            values.put("apple_wide", "awide");
            values.put("apple_narrow", "anarrow");
            values.put("lemon_reg", "lreg");
            values.put("lemon_wide", "lwide");
            values.put("lemon_narrow", "lnarrow");
            values.put("fabric", "Cotton");
            values.put("mrp", "845");
            values.put("category", "T-shirt");
            db.insert("product", null, values);


            ContentValues values2 = new ContentValues();
            values2.put("style_no", "A001");
            values2.put("size", "32B");
            db.insert("size", null, values2);


        }
        catch(Exception e1)
        {
            Toast.makeText(context, "errror in inserting .." + e1.toString(), Toast.LENGTH_LONG).show();
        }

    }
    public Cursor displayData(String query)
    {
        try
        {
            db=this.getReadableDatabase();
            //  Log.w("***********************d1","");
            //String[] ss={KEY_NAME,KEY_PHONE};
            //String cmd="select* from agenda order by AGENDA_DATE ASC ";
            String cmd=query;
            if(db != null)
            {
                //  Log.w("***********************d2","");
                cr =  db.rawQuery(cmd,null);
                if (cr.moveToFirst())
                {
                    //   Log.w("***********************d3","");
                    //cr.close();
                    //db.close();
                    return cr;
                }


            }


            else
            {
                // Emp e=new Emp(0,null,null," " ," "," "," ",0,0);
                cr=null;
                Toast.makeText(context, "Data not found ", Toast.LENGTH_LONG).show();
                //cr.close();
                //db.close();
                return cr;

            }


        }
        catch(Exception e)
        {
            System.out.println("error " + e.toString());

        }
        //cr.close();
        //db.close();
        return cr;
        /*if (cr != null && !cr.isClosed())
        {

            cr.close();
        }*/


    }

    public void insertdata(ContentValues values,String tablenam)
    {
        try {
            db = this.getReadableDatabase();
            db.insert(tablenam, null, values);
        }catch(Exception e)
        {
            System.out.println("error " + e.toString());
        }
    }

    public void query(String query)
    {
        try
        {
            db=this.getWritableDatabase();

            String cmd=query;
            if(db != null)
            {
                //  Log.w("***********************d2","");
                db.execSQL(cmd);
            }
        }
        catch(Exception e)
        {
            cr.close();
            db.close();

            System.out.println("error "+e.toString());

        }
        //cr.close();
        //db.close();

        /*if (cr != null && !cr.isClosed())
        {

            cr.close();
        }*/
        finally {
            //  cr.close();
            //  db.close();

        }


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void updateDatatoDB(String tablenam, ContentValues values, String loantype)
    {
            try {
                db = this.getReadableDatabase();
                db.update(tablenam, values, "loantype='"+loantype+"';",null);
                Log.d("mysearch updated successfully",loantype);
            }catch(Exception e)
            {
                System.out.println("error " + e.toString());
            }
    }
    public void updateDatatouserlogin(String tablenam, ContentValues values, String userid)
    {
        try {
            db = this.getReadableDatabase();
            db.update(tablenam, values, "user_id='"+userid+"';",null);
            Log.d("userlogin updated successfully",String.valueOf(values)+" userid"+userid);
        }catch(Exception e)
        {
            System.out.println("error " + e.toString());
        }
    }
}
