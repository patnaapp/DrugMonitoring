package bih.in.drugmonitor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import bih.in.drugmonitor.entity.Block;
import bih.in.drugmonitor.entity.District;
import bih.in.drugmonitor.entity.FilterOptionEntity;
import bih.in.drugmonitor.entity.LocalSpinnerData;
import bih.in.drugmonitor.entity.PanchayatData;
import bih.in.drugmonitor.entity.PanchayatEntity;
import bih.in.drugmonitor.entity.PanchayatWeb;
import bih.in.drugmonitor.entity.PondEncroachmentEntity;
import bih.in.drugmonitor.entity.PondInspectionDetail;
import bih.in.drugmonitor.entity.SectorWeb;
import bih.in.drugmonitor.entity.SurfaceSchemeEntity;
import bih.in.drugmonitor.entity.UserDetails;
import bih.in.drugmonitor.entity.VillageListEntity;
import bih.in.drugmonitor.entity.WellInspectionEntity;
import bih.in.drugmonitor.entity.ward;

public class DataBaseHelper extends SQLiteOpenHelper {
    //private static String DB_PATH = "";
    private static String DB_PATH = "/data/data/app.bih.in.nic.epacsmgmt/databases/";
    //private static String DB_NAME = "eCountingAC.sqlite";
    private static String DB_NAME = "PACSDB1";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    SQLiteDatabase db;

    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 2);
        if (android.os.Build.VERSION.SDK_INT >= 4.2) {


            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }



    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist


        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            //this.getReadableDatabase();

            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);


        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;

    }

    public boolean databaseExist() {


        File dbFile = new File(DB_PATH + DB_NAME);

        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        this.getReadableDatabase().close();
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();


    }

    public void openDataBase() throws SQLException {

        // Open the database
        this.getReadableDatabase();
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("ALTER TABLE Ward ADD COLUMN AreaType TEXT");
//        db.execSQL("ALTER TABLE OtherDept_Of_Rural_Dev_Dept ADD COLUMN VillageID TEXT");
//        db.execSQL("ALTER TABLE Menrega_Rural_Dev_Dept ADD COLUMN Work_Structure_Type_Name TEXT");
//        db.execSQL("ALTER TABLE Menrega_Rural_Dev_Dept ADD COLUMN Work_Structure_Type_Other_Name TEXT");
//        db.execSQL("ALTER TABLE OtherDept_Of_Rural_Dev_Dept ADD COLUMN Work_Structure_Type_Name TEXT");
//        db.execSQL("ALTER TABLE OtherDept_Of_Rural_Dev_Dept ADD COLUMN Work_Structure_Type_Other_Name TEXT");

        //modifyTable();
    }

    public void modifyTable(){

        if(isColumnExists("Ward", "AreaType") == false){
            AlterTable("Ward", "AreaType");
        }

//        if(!isColumnExists("OtherDept_Of_Rural_Dev_Dept", "VillageID")){
//            AlterTable("OtherDept_Of_Rural_Dev_Dept", "VillageID");
//        }
//
//
//        if(!isColumnExists("OtherDept_Of_Rural_Dev_Dept", "Work_Structure_Type_Name")){
//            AlterTable("OtherDept_Of_Rural_Dev_Dept", "Work_Structure_Type_Name");
//        }
//
//        if(!isColumnExists("OtherDept_Of_Rural_Dev_Dept", "Work_Structure_Type_Other_Name")){
//            AlterTable("OtherDept_Of_Rural_Dev_Dept", "Work_Structure_Type_Other_Name");
//        }

//        AlterManregTable("Menrega_Rural_Dev_Dept");
//        AlterManregTable("OtherDept_Of_Rural_Dev_Dept");
    }


    public void AlterTable(String tableName, String columnName)
    {
        db = this.getReadableDatabase();

        try{

            db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN "+columnName+" TEXT");
            Log.e("ALTER Done",tableName +"-"+ columnName);
        }
        catch (Exception e)
        {
            Log.e("ALTER Failed",tableName +"-"+ columnName);
        }
    }

    public boolean isColumnExists (String table, String column) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info("+ table +")", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                if (column.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        cursor.close();
        return false;
    }
    public long getUserCount() {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from UserLogin", null);

            x = cur.getCount();

            cur.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return x;
    }

    public long insertSurfaceUserDetails(UserDetails result) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();

            values.put("UserID", result.getUserID());
            values.put("UserName", result.getName());
            values.put("UserPassword", result.getPassword());
            //values.put("IMEI", result.getIMEI());
            values.put("RoleId", result.getUserroleId());
            values.put("Role", result.getUserrole());

            values.put("MobileNo", result.getMobileNo());
            values.put("Email", result.getEmail());


            String[] whereArgs = new String[]{result.getUserID()};

            c = db.update("UserDetail", values, "UserID=? ", whereArgs);

            if (!(c > 0)) {

                //c = db.insert("UserDetail", null, values);
                c = db.insertWithOnConflict("UserDetail", null, values, SQLiteDatabase.CONFLICT_REPLACE);
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return c;

    }

    public UserDetails getSurfaceUserDetails(String userId, String pass) {

        UserDetails userInfo = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{userId, pass};

            Cursor cur = db.rawQuery(
                    "Select * from UserDetail WHERE UserID=? and UserPassword=?",
                    params);
            int x = cur.getCount();

            while (cur.moveToNext()) {


                userInfo = new UserDetails();
                userInfo.setUserID(cur.getString(cur.getColumnIndex("UserID")));
                userInfo.setName(cur.getString(cur.getColumnIndex("UserName")));
                userInfo.setPassword(cur.getString(cur.getColumnIndex("UserPassword")));

                userInfo.setMobileNo(cur.getString(cur.getColumnIndex("MobileNo")));
                userInfo.setEmail(cur.getString(cur.getColumnIndex("Email")));

                userInfo.setUserroleId(cur.getString(cur.getColumnIndex("RoleId")));
                userInfo.setUserrole(cur.getString(cur.getColumnIndex("Role")));
                userInfo.setAuthenticated(true);
//                userInfo.setDistrictCode(cur.getString(cur.getColumnIndex("DistCode")));
//                userInfo.setDistName(cur.getString(cur.getColumnIndex("DistName")));
//                userInfo.setBlockCode(cur.getString(cur.getColumnIndex("BlockCode")));
//                userInfo.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
//                userInfo.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
//                userInfo.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            userInfo = null;
        }
        return userInfo;
    }

    public UserDetails getUserDetails(String userId, String pass) {

        UserDetails userInfo = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{userId.trim(), pass};

            Cursor cur = db.rawQuery(
                    "Select * from UserDetail WHERE UserID=? and UserPassword=?",
                    params);
            int x = cur.getCount();

            while (cur.moveToNext()) {


                userInfo = new UserDetails();
//                userInfo.setUserId(cur.getString(cur.getColumnIndex("UserID")));
//                userInfo.setUserId(cur.getString(cur.getColumnIndex("UserName")));
//                userInfo.setPassword(cur.getString(cur.getColumnIndex("UserPassword")));
//
////                userInfo.setPassword(cur.getString(cur.getColumnIndex("MobileNo")));
////                userInfo.setPassword(cur.getString(cur.getColumnIndex("Email")));
//
//                userInfo.setRoleId(cur.getString(cur.getColumnIndex("RoleId")));
//                userInfo.setRoleName(cur.getString(cur.getColumnIndex("Role")));
//                //userInfo.setAuthenticated(true);
//                userInfo.setDistCode(cur.getString(cur.getColumnIndex("DistCode")));
//                userInfo.setDistName(cur.getString(cur.getColumnIndex("DistName")));
//                userInfo.setDivisionCode(cur.getString(cur.getColumnIndex("DivisionCode")));
//                userInfo.setDivisionName(cur.getString(cur.getColumnIndex("DivisionName")));
//                userInfo.setZoneCode(cur.getString(cur.getColumnIndex("ZoneCode")));
//                userInfo.setZoneName(cur.getString(cur.getColumnIndex("ZoneName")));
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            userInfo = null;
        }
        return userInfo;
    }

    public long setFilterOptionDataToLocal(ArrayList<FilterOptionEntity> list, String optionTypeName) {
        String tableName = "FilterOption";

        long c = -1;

        ArrayList<FilterOptionEntity> info = list;

        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("optionId", info.get(i).getOptionText());
                    values.put("value", info.get(i).getOptionValue());
                    values.put("optionType", optionTypeName);

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getOptionText())};

                    c = db.update(tableName, values, "optionId=?", whereArgs);

                    if(c < 1){
                        c = db.insert(tableName, null, values);
                    }
                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public ArrayList<FilterOptionEntity> getSchemeFilterDataList(int type){
        String tableName = "FilterOption";
        switch (type){
            case 6:
                tableName = "SchemeName";
                break;
            case 7:
                tableName = "FinancialYear";
                break;
            case 8:
                tableName = "FundType";
                break;
            case 9:
                tableName = "SchemeType";
                break;
            default:
                tableName = "FilterOption";
                break;
        }

        ArrayList<FilterOptionEntity> infoList = new ArrayList<FilterOptionEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            //tring[] params = new String[]{typeName};

            Cursor cur = db.rawQuery(
                    "Select * from "+ tableName,null);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                FilterOptionEntity info = new FilterOptionEntity();

                info.setOptionText(cur.getString(cur.getColumnIndex("optionId")));
                info.setOptionValue(cur.getString(cur.getColumnIndex("value")));
                //info.setOptionType(cur.getString(cur.getColumnIndex("optionType")));

                infoList.add(info);
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();
        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public long setSchemeFilterDataToLocal(ArrayList<FilterOptionEntity> list, int optionTypeName) {
        String tableName = "FilterOption";
        switch (optionTypeName){
            case 6:
                tableName = "SchemeName";
                break;
            case 7:
                tableName = "FinancialYear";
                break;
            case 8:
                tableName = "FundType";
                break;
            case 9:
                tableName = "SchemeType";
                break;
            default:
                tableName = "FilterOption";
                break;
        }

        long c = -1;

        ArrayList<FilterOptionEntity> info = list;

        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("optionId", info.get(i).getOptionText());
                    values.put("value", info.get(i).getOptionValue());
                    //values.put("optionType", optionTypeName);

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getOptionText())};

                    c = db.update(tableName, values, "optionId=?", whereArgs);

                    if(c < 1){
                        c = db.insert(tableName, null, values);
                    }
                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long deleteSchemeRecord(){

        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from SurfaceSchemeDetail");

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long setSurfaceDataToLocal(ArrayList<SurfaceSchemeEntity> list) {
        String tableName = "SurfaceSchemeDetail";

        long c = -1;

        ArrayList<SurfaceSchemeEntity> info = list;

        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("SCHEME_ID", info.get(i).getSCHEME_ID());
                    values.put("SCHEME_NAME", info.get(i).getSCHEME_NAME());
                    values.put("TYPE_OF_SCHEME", info.get(i).getTYPE_OF_SCHEME());
                    values.put("District", info.get(i).getDistrict());
                    values.put("Block", info.get(i).getBlock());
                    values.put("Panchayat", info.get(i).getPanchayat());
                    values.put("SOURCE_OF_WATER", info.get(i).getSOURCE_OF_WATER());
                    values.put("Fund_Type", info.get(i).getFund_Type());
                    values.put("FINANCIAL_YEAR", info.get(i).getFINANCIAL_YEAR());
                    values.put("NIT_No", info.get(i).getNIT_No());
                    values.put("SC_ST_Majority_Village", info.get(i).getSC_ST_Majority_Village());

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getSCHEME_ID())};

                    c = db.update(tableName, values, "SCHEME_ID=?", whereArgs);

                    if(c < 1){
                        values.put("Updated", "0");
                        c = db.insert(tableName, null, values);
                    }
                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public ArrayList<SurfaceSchemeEntity> getSurfaceSchemeDetail(String finyr, String schemeid)
    {
        //PondInspectionDetail info = null;

        ArrayList<SurfaceSchemeEntity> infoList = new ArrayList<SurfaceSchemeEntity>();
        String whereCondition=getPostWhereConditionForStudentListForAttendance(finyr,schemeid);
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[]{"0"};
               //Cursor cur = db.rawQuery("Select * from SurfaceSchemeDetail WHERE "+ whereCondition +" AND Updated=?",params);
            Cursor cur = db.rawQuery("Select * from SurfaceSchemeDetail WHERE Updated=? "+ whereCondition +" ",params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext())
            {
                SurfaceSchemeEntity info = new SurfaceSchemeEntity();
                info.setSCHEME_ID(cur.getString(cur.getColumnIndex("SCHEME_ID")));
                info.setSCHEME_NAME(cur.getString(cur.getColumnIndex("SCHEME_NAME")));
                info.setTYPE_OF_SCHEME(cur.getString(cur.getColumnIndex("TYPE_OF_SCHEME")));
                info.setDistrict(cur.getString(cur.getColumnIndex("District")));
                info.setBlock(cur.getString(cur.getColumnIndex("Block")));
                info.setPanchayat(cur.getString(cur.getColumnIndex("Panchayat")));
                info.setSOURCE_OF_WATER(cur.getString(cur.getColumnIndex("SOURCE_OF_WATER")));
                info.setFund_Type(cur.getString(cur.getColumnIndex("Fund_Type")));
                info.setFINANCIAL_YEAR(cur.getString(cur.getColumnIndex("FINANCIAL_YEAR")));
                info.setNIT_No(cur.getString(cur.getColumnIndex("NIT_No")));
                info.setSC_ST_Majority_Village(cur.getString(cur.getColumnIndex("SC_ST_Majority_Village")));
                infoList.add(info);
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
            //info = null;
        }
        return infoList;
    }

    public ArrayList<SurfaceSchemeEntity> getInspectedSurfaceSchemeDetail(){
        //PondInspectionDetail info = null;

        ArrayList<SurfaceSchemeEntity> infoList = new ArrayList<SurfaceSchemeEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"1"};

            // Cursor cur = db.rawQuery("Select * from SurfaceSchemeDetail WHERE Updated=?",params);
            Cursor cur = db.rawQuery("Select SCHEME_ID, SCHEME_NAME, TYPE_OF_SCHEME, District, Block, Panchayat, SOURCE_OF_WATER, Fund_Type, FINANCIAL_YEAR, NIT_No, SC_ST_Majority_Village, Updated, CrossVerification, InspectionBy, SurveyorName, SurvyorPhone, InspectionDate, WorkStatus, WorkCompletionPer, WorkDone, ObservationCategory, Latitude, Longitude, Comment1, Comment2, Comment3, Comment4, AppVersion  from SurfaceSchemeDetail WHERE Updated=?",params);

            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                SurfaceSchemeEntity info = new SurfaceSchemeEntity();

                info.setSCHEME_ID(cur.getString(cur.getColumnIndex("SCHEME_ID")));
                info.setSCHEME_NAME(cur.getString(cur.getColumnIndex("SCHEME_NAME")));
                info.setTYPE_OF_SCHEME(cur.getString(cur.getColumnIndex("TYPE_OF_SCHEME")));
                info.setDistrict(cur.getString(cur.getColumnIndex("District")));
                info.setBlock(cur.getString(cur.getColumnIndex("Block")));
                info.setPanchayat(cur.getString(cur.getColumnIndex("Panchayat")));
                info.setSOURCE_OF_WATER(cur.getString(cur.getColumnIndex("SOURCE_OF_WATER")));
                info.setFund_Type(cur.getString(cur.getColumnIndex("Fund_Type")));
                info.setFINANCIAL_YEAR(cur.getString(cur.getColumnIndex("FINANCIAL_YEAR")));
                info.setNIT_No(cur.getString(cur.getColumnIndex("NIT_No")));
                info.setSC_ST_Majority_Village(cur.getString(cur.getColumnIndex("SC_ST_Majority_Village")));

                info.setUpdated(cur.getString(cur.getColumnIndex("Updated")));
                info.setCrossVerification(cur.getString(cur.getColumnIndex("CrossVerification")));
                info.setInspectionBy(cur.getString(cur.getColumnIndex("InspectionBy")));
                info.setSurveyorName(cur.getString(cur.getColumnIndex("SurveyorName")));
                info.setSurvyorPhone(cur.getString(cur.getColumnIndex("SurvyorPhone")));
                info.setInspectionDate(cur.getString(cur.getColumnIndex("InspectionDate")));
                info.setWorkStatus(cur.getString(cur.getColumnIndex("WorkStatus")));
                info.setWorkCompletionPer(cur.getString(cur.getColumnIndex("WorkCompletionPer")));
                info.setWorkDone(cur.getString(cur.getColumnIndex("WorkDone")));
                info.setObservationCategory(cur.getString(cur.getColumnIndex("ObservationCategory")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setComment1(cur.getString(cur.getColumnIndex("Comment1")));
                info.setComment2(cur.getString(cur.getColumnIndex("Comment2")));
                info.setComment3(cur.getString(cur.getColumnIndex("Comment3")));
                info.setComment4(cur.getString(cur.getColumnIndex("Comment4")));
                info.setAppVersion(cur.getString(cur.getColumnIndex("AppVersion")));

//                if (!cur.isNull(cur.getColumnIndex("Photo1"))) {
//
//                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//                    info.setPhoto1(encodedImg1);
//                }
//
//                if (!cur.isNull(cur.getColumnIndex("Photo2"))) {
//
//                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo2"));
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//                    info.setPhoto2(encodedImg1);
//                }
//
//                if (!cur.isNull(cur.getColumnIndex("Photo3"))) {
//
//                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo3"));
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);;
//                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//                    info.setPhoto3(encodedImg1);
//                }
//
//                if (!cur.isNull(cur.getColumnIndex("Photo4"))) {
//
//                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo4"));
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//                    info.setPhoto4(encodedImg1);
//                }

                infoList.add(info);
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();
        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public long ResetSchemeInspectionDetail(String schemeId) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();

            values.put("Updated", "0");
            values.putNull("CrossVerification");
            values.putNull("InspectionBy");
            values.putNull("SurveyorName");
            values.putNull("SurvyorPhone");
            values.putNull("InspectionDate");
            values.putNull("WorkStatus");
            values.putNull("WorkCompletionPer");
            values.putNull("WorkDone");
            values.putNull("ObservationCategory");
            values.putNull("Latitude");
            values.putNull("Longitude");
            values.putNull("Photo1");
            values.putNull("Photo2");
            values.putNull("Photo3");
            values.putNull("photo4");
            values.putNull("Comment1");
            values.putNull("Comment2");
            values.putNull("Comment3");
            values.putNull("Comment4");
            values.putNull("AppVersion");

            String[] whereArgs = new String[]{schemeId};

            c = db.update("SurfaceSchemeDetail", values, "SCHEME_ID=? ", whereArgs);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return 0;
        }
        return c;

    }

    public ArrayList<FilterOptionEntity> getFilterOptionList(String typeName){
        //PondInspectionDetail info = null;

        ArrayList<FilterOptionEntity> infoList = new ArrayList<FilterOptionEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{typeName};

            Cursor cur = db.rawQuery(
                    "Select * from FilterOption WHERE optionType=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                FilterOptionEntity info = new FilterOptionEntity();

                info.setOptionText(cur.getString(cur.getColumnIndex("optionId")));
                info.setOptionValue(cur.getString(cur.getColumnIndex("value")));
                info.setOptionType(cur.getString(cur.getColumnIndex("optionType")));

                infoList.add(info);
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();
        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }
//    public long UpdateInspectionCount(String inspectionCount, String UserId) {
//
//        long c = 0;
//        try {
//
//            SQLiteDatabase db = this.getReadableDatabase();
//
//            ContentValues values = new ContentValues();
//            values.put("TotInspection", inspectionCount);
//
//            String[] whereArgs = new String[]{UserId};
//
//            c = db.update("UserLogin", values, "UserID=? ", whereArgs);
//
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            // TODO: handle exception
//            return 0;
//        }
//        return c;
//
//    }

    public WellInspectionEntity getWellInspectionDetails(String inspId) {

        WellInspectionEntity info = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{inspId};

            Cursor cur = db.rawQuery(
                    "Select * from WellInspectionDetail WHERE id=?",
                    params);
            int x = cur.getCount();

            while (cur.moveToNext()) {


                info = new WellInspectionEntity();

                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setDistID(cur.getString(cur.getColumnIndex("DistrictCode")));
                info.setBlockID(cur.getString(cur.getColumnIndex("Block_Code")));
                info.setBlockName(cur.getString(cur.getColumnIndex("Block_Name")));

                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("Rajswa_Thana_No")));
                //info.setAuthenticated(true);
                info.setVillageID(cur.getString(cur.getColumnIndex("Village_Id")));
                info.setVillageName(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setPanchayat_Code(cur.getString(cur.getColumnIndex("Panchayat_Code")));
                info.setPanchayat_Name(cur.getString(cur.getColumnIndex("Panchayat_Name")));
                info.setKhata_Khesra_No(cur.getString(cur.getColumnIndex("Khata_Khesra_No")));
                info.setPrivate_or_Public(cur.getString(cur.getColumnIndex("Private_or_Public")));
                info.setOutside_Diamter(cur.getString(cur.getColumnIndex("Outside_Diamter")));

                info.setName_of_undertaken(cur.getString(cur.getColumnIndex("Name_Of_Undertaken_Dept")));
                info.setRequirement_Of_Flyer(cur.getString(cur.getColumnIndex("Requirement_Of_Flyer")));
                info.setStatus_of_Encroachment(cur.getString(cur.getColumnIndex("Status_Of_Encroachment")));
                info.setTypes_of_Encroachment(cur.getString(cur.getColumnIndex("Types_Of_Encroachment")));
                info.setRequirement_of_Renovation(cur.getString(cur.getColumnIndex("Requirement_Of_Renovation")));
                //info.setre(cur.getString(cur.getColumnIndex("Recommended_Execution_Dept")));
                info.setRequirement_of_machine(cur.getString(cur.getColumnIndex("Requirement_of_Machine")));
                //info.set(cur.getString(cur.getColumnIndex("Name_of_undertaken")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                info.setDistID(cur.getString(cur.getColumnIndex("DistrictCode")));

                info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                //info.setVi(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setVerified_Date(cur.getString(cur.getColumnIndex("Verified_Date")));
                info.setLatitude_Mob(cur.getString(cur.getColumnIndex("Latitude_Mob")));
                info.setLongitude_Mob(cur.getString(cur.getColumnIndex("Longitude_Mob")));
                info.setWellAvblValue(cur.getString(cur.getColumnIndex("WellAvblValue")));
                info.setWellOwnerOtherDeptName(cur.getString(cur.getColumnIndex("Photo3")));

                if (!cur.isNull(cur.getColumnIndex("Photo1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto2(encodedImg2);
                }

//                if (!cur.isNull(cur.getColumnIndex("Photo3"))) {
//
//                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo3"));
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    String encodedImg3 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//                    info.setPhoto3(encodedImg3);
//                }
//
//                if (!cur.isNull(cur.getColumnIndex("Photo4"))) {
//
//                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo4"));
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    String encodedImg4 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//                    info.setPhoto4(encodedImg4);
//                }
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            info = null;
        }
        return info;
    }

    public PondEncroachmentEntity getPondEncrhmntDetails(String inspId, String type) {

        String tablename = type.equals("pond") ? "CoPondEncroachmentReport" : "CoWellEncroachmentReport";
        PondEncroachmentEntity info = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{inspId};

            Cursor cur = db.rawQuery(
                    "Select * from "+tablename+" WHERE id=?",
                    params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                info = new PondEncroachmentEntity();

                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setDistID(cur.getString(cur.getColumnIndex("DistID")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                info.setBlockID(cur.getString(cur.getColumnIndex("BlockID")));
                info.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
                info.setPanchayatID(cur.getString(cur.getColumnIndex("PanchayatID")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));

                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("RajswaThanaNumber")));
                info.setKhaata_Kheshara_Number(cur.getString(cur.getColumnIndex("Khaata_Kheshara_Number")));

                info.setVillageID(cur.getString(cur.getColumnIndex("VillageID")));
                info.setVILLNAME(cur.getString(cur.getColumnIndex("VILLNAME")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setStatus_Of_Encroachment(cur.getString(cur.getColumnIndex("Status_Of_Encroachment")));
                info.setType_Of_Encroachment(cur.getString(cur.getColumnIndex("Type_Of_Encroachment")));

                info.setVerified_By(cur.getString(cur.getColumnIndex("Verified_By")));
                info.setIsInspected(cur.getString(cur.getColumnIndex("IsInspected")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                info.setVerified_Date(cur.getString(cur.getColumnIndex("Verified_Date")));

                info.setEnch_Verified_By(cur.getString(cur.getColumnIndex("Ench_Verified_By")));
                info.setEStatus(cur.getString(cur.getColumnIndex("EStatus")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                info.setEnchrochmentStartDate(cur.getString(cur.getColumnIndex("EnchrochmentStartDate")));
                info.setEnchrochmentEndDate(cur.getString(cur.getColumnIndex("EnchrochmentEndDate")));
                info.setNoticeDate(cur.getString(cur.getColumnIndex("NoticeDate")));
                info.setNoticeNo(cur.getString(cur.getColumnIndex("NoticeNo")));
                info.setAppVersion(cur.getString(cur.getColumnIndex("AppVersion")));
                info.setUploadType(cur.getString(cur.getColumnIndex("UploadType")));

            }
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            info = null;
        }
        return info;
    }


    public PondInspectionDetail getPondInspectionDetails(String inspId) {

        PondInspectionDetail info = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{inspId};

            Cursor cur = db.rawQuery(
                    "Select * from PondInspectionDetail WHERE id=?",
                    params);
            int x = cur.getCount();

            while (cur.moveToNext()) {


                info = new PondInspectionDetail();

                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setDistrictCode(cur.getString(cur.getColumnIndex("DistrictCode")));
                info.setBlockCode(cur.getString(cur.getColumnIndex("Block_Code")));
                info.setBlockName(cur.getString(cur.getColumnIndex("Block_Name")));

                info.setRajswaThanaNo(cur.getString(cur.getColumnIndex("Rajswa_Thana_No")));
                //info.setAuthenticated(true);
                info.setVillage(cur.getString(cur.getColumnIndex("Village_Id")));
                info.setVillageName(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setPanchayatCode(cur.getString(cur.getColumnIndex("Panchayat_Code")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("Panchayat_Name")));
                info.setKhataKhesraNo(cur.getString(cur.getColumnIndex("Khata_Khesra_No")));
                info.setPrivateOrPublic(cur.getString(cur.getColumnIndex("Private_or_Public")));
                info.setAreaByGovtRecord(cur.getString(cur.getColumnIndex("Area_by_Govt_Record")));

                info.setConnectedWithPine(cur.getString(cur.getColumnIndex("Connected_with_Pine")));
                info.setAvailiablityOfWater(cur.getString(cur.getColumnIndex("Availability_Of_Water")));
                info.setStatusOfEncroachment(cur.getString(cur.getColumnIndex("Status_of_Encroachment")));
                info.setTypesOfEncroachment(cur.getString(cur.getColumnIndex("Types_of_Encroachment")));
                info.setRequirementOfRenovation(cur.getString(cur.getColumnIndex("Requirement_of_Renovation")));
                info.setRecommendedExecutionDept(cur.getString(cur.getColumnIndex("Recommended_Execution_Dept")));
                info.setRequirementOfMachine(cur.getString(cur.getColumnIndex("Requirement_of_machine")));
                info.setOwnerName(cur.getString(cur.getColumnIndex("Name_of_undertaken")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));

                info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                //info.setVi(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                info.setVerified_Date(cur.getString(cur.getColumnIndex("Verified_Date")));
                info.setLatitude_Mob(cur.getString(cur.getColumnIndex("Latitude_Mob")));
                info.setLongitude_Mob(cur.getString(cur.getColumnIndex("Longitude_Mob")));
                info.setPondAvblValue(cur.getString(cur.getColumnIndex("PondAvblValue")));
                info.setPondCatValue(cur.getString(cur.getColumnIndex("PondCatValue")));

                info.setPondCatName(cur.getString(cur.getColumnIndex("Photo3")));
                info.setPondOwnerOtherDeptName(cur.getString(cur.getColumnIndex("Photo4")));

                if (!cur.isNull(cur.getColumnIndex("Photo1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto2(encodedImg2);
                }

//                if (!cur.isNull(cur.getColumnIndex("Photo3"))) {
//
//                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo3"));
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    String encodedImg3 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//                    info.setPhoto3(encodedImg3);
//                }
//
//                if (!cur.isNull(cur.getColumnIndex("Photo4"))) {
//
//                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photor4"));
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    String encodedImg4 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//                    info.setPhoto4(encodedImg4);
//                }


            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            info = null;
        }
        return info;
    }

    public long setDistrictToLocal(ArrayList<District> list) {
        // String tableName = type == "pond" ? "PondInspectionDetail" : "WellInspectionDetail";

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<District> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("DistCode", info.get(i).get_DistCode());
                    values.put("DistName", info.get(i).get_DistName());
                    values.put("DistCode3", info.get(i).get_DistNameHN());
                    values.put("Zone", info.get(i).get_DistNameHN());
                    values.put("Circle", info.get(i).get_DistNameHN());


                    String[] whereArgs = new String[]{String.valueOf(info.get(i).get_DistCode())};

                    c = db.update("DistrictMwrd", values, "DistCode=?", whereArgs);

                    if(c != 1){
                        c = db.insert("DistrictMwrd", null, values);
                    }



                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setPanchayatDataToLocal(UserDetails userInfo, ArrayList<PanchayatEntity> list) {
        // String tableName = type == "pond" ? "PondInspectionDetail" : "WellInspectionDetail";

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<PanchayatEntity> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("PanchayatCode", info.get(i).getPcode());
                    values.put("PanchayatName", info.get(i).getPname());
                    values.put("PACName", info.get(i).getAreaType());

                    values.put("BlockCode", userInfo.getBlockCode());
                    //values.put("Block Name", userInfo.getBlockName());
                    values.put("DistrictCode", userInfo.getDistrictCode());
                    values.put("DistrictName", userInfo.getDistName());
                    values.put("PartNo", "2");

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getPcode())};

                    c = db.update("Panchayat", values, "PanchayatCode=?", whereArgs);

                    if(c != 1){
                        c = db.insert("Panchayat", null, values);
                    }



                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public String getWellEncrhmntUpdatedDataCount(){
        String pondCount = "0", wellCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor curPond = db.rawQuery("Select * from CoWellEncroachmentReport WHERE isUpdated=?", params);

            pondCount = String.valueOf(curPond.getCount());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return pondCount;
    }

    public String getPondEncrhmntUpdatedDataCount(){
        String pondCount = "0", wellCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor curPond = db.rawQuery("Select * from CoPondEncroachmentReport WHERE isUpdated=?", params);
            pondCount = String.valueOf(curPond.getCount());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return pondCount;
    }

    public String getSchemeInspectionUpdatedDataCount(){
        String pondCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor curPond = db.rawQuery("Select * from SurfaceSchemeDetail WHERE Updated=?", params);

            pondCount = String.valueOf(curPond.getCount());

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return pondCount;
    }

    public int getSchemeDataCount(){
        int pondCount = 0;
        String[] params = new String[]{"0"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor curPond = db.rawQuery("Select * from SurfaceSchemeDetail WHERE Updated=?", params);

            pondCount = curPond.getCount();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return pondCount;
    }

    public String getPlantationUpdatedDataCount(){
        //ArrayList<String> List = new ArrayList<String>();
        String pondCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor curPond = db.rawQuery("Select * from PlantationDetail WHERE isUpdated=?", params);

            pondCount = String.valueOf(curPond.getCount());
            //wellCount = String.valueOf(curWell.getCount());


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return pondCount;
    }

    public String getWellUpdatedDataCount(){
        String wellCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            //Cursor curPond = db.rawQuery("Select * from PondInspectionDetail WHERE isUpdated=?", params);

            Cursor curWell = db.rawQuery("Select * from WellInspectionDetail WHERE isUpdated=?", params);


            wellCount = String.valueOf(curWell.getCount());


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return wellCount;
    }

    public String getManregadDataCount(){
        String wellCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            //Cursor curPond = db.rawQuery("Select * from PondInspectionDetail WHERE isUpdated=?", params);

            Cursor curWell = db.rawQuery("Select * from Menrega_Rural_Dev_Dept WHERE isUpdated=?", params);


            wellCount = String.valueOf(curWell.getCount());


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return wellCount;
    }

    public String getOtherSchemeDataCount(){
        String wellCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            //Cursor curPond = db.rawQuery("Select * from PondInspectionDetail WHERE isUpdated=?", params);

            Cursor curWell = db.rawQuery("Select * from OtherDept_Of_Rural_Dev_Dept WHERE isUpdated=?", params);


            wellCount = String.valueOf(curWell.getCount());


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return wellCount;
    }


    public ArrayList<LocalSpinnerData> getAwcList(String sectorCode) {

        // PlaceDataSQL placeData = new PlaceDataSQL(MainActivity.this);
        ArrayList<LocalSpinnerData> awcList = new ArrayList<LocalSpinnerData>();
        Cursor cur;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            if(sectorCode == null || sectorCode.equalsIgnoreCase("")|| sectorCode.equalsIgnoreCase("NA") || sectorCode.equalsIgnoreCase("0")){
                cur = db.rawQuery("Select * from Panchayat", null);
            }else {
                String[] params = new String[]{sectorCode};
                cur = db.rawQuery("Select * from Panchayat WHERE Sector_Code=?", params);
            }

            int x = cur.getCount();

            while (cur.moveToNext()) {

                LocalSpinnerData localSpinnerData = new LocalSpinnerData();
                localSpinnerData.setCode((cur.getString(cur.getColumnIndex("slno"))));
                localSpinnerData.setValue(cur.getString(cur.getColumnIndex("Name")));

                awcList.add(localSpinnerData);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return awcList;

    }


    public ArrayList<LocalSpinnerData> getSectorList(String projectCode) {

        // PlaceDataSQL placeData = new PlaceDataSQL(MainActivity.this);
        ArrayList<LocalSpinnerData> sectorList = new ArrayList<LocalSpinnerData>();
        Cursor cur;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[]{projectCode};
            cur = db.rawQuery("Select * from Sector WHERE Block_Code=?", params);


            int x = cur.getCount();

            while (cur.moveToNext()) {

                LocalSpinnerData localSpinnerData = new LocalSpinnerData();
                localSpinnerData.setCode((cur.getString(cur.getColumnIndex("Code"))));
                localSpinnerData.setValue(cur.getString(cur.getColumnIndex("Name")));

                sectorList.add(localSpinnerData);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return sectorList;

    }



//
//    public UserDetails getUserDetails(String userId, String pass) {
//
//        UserDetails userInfo = null;
//
//        try {
//
//            SQLiteDatabase db = this.getReadableDatabase();
//
//            String[] params = new String[]{userId.trim(), pass};
//
//            Cursor cur = db.rawQuery(
//                    "Select * from UserLogin WHERE UserID=? and Password=?",
//                    params);
//            int x = cur.getCount();
//            // db1.execSQL("Delete from UserDetail");
//
//            while (cur.moveToNext()) {
//
//
//                userInfo = new UserDetails();
//                userInfo.setUserID(cur.getString(cur.getColumnIndex("UserID")));
//                userInfo.setName(cur.getString(cur.getColumnIndex("UserName")));
//                userInfo.setPassword(cur.getString(cur.getColumnIndex("Password")));
//               // userInfo.setMobile(cur.getString(cur.getColumnIndex("ContactNo")));
//                userInfo.setUserrole(cur.getString(cur.getColumnIndex("Userrole")));
//                //userInfo.setIMEI(cur.getString(cur.getColumnIndex("IMEI")));
//                userInfo.setAuthenticated(true);
//                userInfo.setDistrictCode(cur.getString(cur.getColumnIndex("DistCode")));
//                userInfo.setDistName(cur.getString(cur.getColumnIndex("DistName")));
//                userInfo.setBlockCode(cur.getString(cur.getColumnIndex("BlockCode")));
//                userInfo.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
//                userInfo.setDegignation(cur.getString(cur.getColumnIndex("Degignation")));
//                userInfo.setMobileNo(cur.getString(cur.getColumnIndex("MobileNo")));
//            }
//
//            cur.close();
//            db.close();
//
//        } catch (Exception e) {
//            // TODO: handle exception
//            userInfo = null;
//        }
//        return userInfo;
//    }



    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        int h = cal.get(Calendar.HOUR);
        int m = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);

        String date = day + "/" + month + "/" + year + "  " + h + ":" + m + ":" + s;
        return date;

    }

    public long deletePendingUpload2(String pid, String userId) {
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(pid), userId};
            c = db.delete("UploadDataforGps", "sl_no=? and userId=?", DeleteWhere);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long deletePendingUpload3(String pid, String userId) {
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(pid), userId};
            c = db.delete("SevikaSahaika", "slno=? and userId=?", DeleteWhere);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }


    public int getNumberOfPendingData(String userId) {

        int x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {userId};
            Cursor cur = db.rawQuery("Select slno from Inspection where Latitude IS NOT NULL and Longitude IS NOT NULL and UploadBy =?", whereArgs);
            x = cur.getCount();
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;

    }

    public int getNumberTotalOfPendingData(String userId) {

        int x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {userId};
            Cursor cur = db.rawQuery("Select slno from UploadData where EntryBy =?", whereArgs);
            x = cur.getCount();
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;

    }

    public int getNumberOfPendingData2(String userId) {

        int x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {userId};
            Cursor cur = db.rawQuery("Select sl_no from UploadDataforGps where Latitude IS NOT NULL and Longitude IS NOT NULL and userId=? ", whereArgs);
            x = cur.getCount();
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;

    }
    public int getNumberOfPendingData2GPS(String userId) {

        int x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {userId};
            Cursor cur = db.rawQuery("Select sl_no from UploadDataforGps where userId=? ", whereArgs);
            x = cur.getCount();
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;

    }

    public int getNumberOfPendingData3() {

        int x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {null, null};
            Cursor cur = db.rawQuery("Select * from myVoutcher", null);
            x = cur.getCount();
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;

    }

    public long setDistrictDataLocalUserWise(ArrayList<District> distlist, String userid) {

        long c = -1;
        ArrayList<District> dist = distlist;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (dist != null) {
            try {
                for (int i = 0; i < dist.size(); i++) {
                    values.put("Code", dist.get(i).get_DistCode());
                    values.put("Name", dist.get(i).get_DistName());
                    values.put("userid", userid);
                    String[] param = {dist.get(i).get_DistCode()};
                    //long update = db.update("DistDetail", values, "Code = ?", param);
                    //  if (!(update > 0))
                    c = db.insert("DistDetailUserBy", null, values);
                }
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setBlockDataForDist(ArrayList<Block> blocklist, String distCode) {

        long c = -1;
        ArrayList<Block> block = blocklist;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("Delete from Block");
        if (block.size() > 0) {
            try {


                for (int i = 0; i < block.size(); i++) {

                    values.put("Code", block.get(i).getBlockCode());
                    values.put("Name", block.get(i).getBlockName());
                    values.put("District_Code", distCode);
                  /*  String[] param={block.get(i).getCode()};
                    long update = db.update("Block", values, "Code = ?", param);
                    if (!(update>0))*/
                    c = db.insert("Block", null, values);
                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }
    public long setSectorDataLocal(ArrayList<SectorWeb> sectorWebArrayList, String blockCode) {

        long c = -1;
        ArrayList<SectorWeb> sectorWebs = sectorWebArrayList;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("Delete from Sector");
        if (sectorWebs.size() > 0) {
            try {


                for (int i = 0; i < sectorWebs.size(); i++) {
                    values.put("Code", sectorWebs.get(i).getCode());
                    values.put("Name", sectorWebs.get(i).getValue());
                    values.put("Block_Code", blockCode);
                    /*String[] param={sectorWebs.get(i).getCode()};
                    long update = db.update("Sector", values, "Code = ?", param);
                    if (!(update>0))*/
                    c = db.insert("Sector", null, values);

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setPanchayatDataLocal(ArrayList<PanchayatWeb> panchayatlist, String sectorCode) {

        long c = -1;
        ArrayList<PanchayatWeb> panchayat = panchayatlist;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("Delete from Panchayat");
        if (panchayat.size() > 0) {
            try {


                for (int i = 0; i < panchayat.size(); i++) {

                    values.put("Code", panchayat.get(i).getCode());
                    values.put("slno", panchayat.get(i).getAWC_Code());
                    values.put("Name", panchayat.get(i).getValue());
                    values.put("Sector_Code", panchayat.get(i).getSectorCode());
                   /* String[] param={panchayat.get(i).getCode()};
                    long update = db.update("Panchayat", values, "Code = ?", param);
                    if (!(update>0))*/
                    c = db.insert("Panchayat", null, values);

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }
    public long setPanchayatDataForDistBlock(ArrayList<PanchayatData> panchayatlist, String distcode, String blkcode) {

        long c = -1;
        //CREATE TABLE `Panchayat1` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `DistCode` TEXT,
        // `BlockCode` TEXT, `PanchayatCode` TEXT, `PanchayatName` TEXT )
        ArrayList<PanchayatData> panchayat = panchayatlist;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("Delete from Panchayat1");
        if (panchayat.size() > 0) {
            try {


                for (int i = 0; i < panchayat.size(); i++) {

                    values.put("PanchayatCode", panchayat.get(i).getPcode());
                    values.put("PanchayatName", panchayat.get(i).getPname());
                    values.put("DistCode", distcode);
                    values.put("BlockCode", blkcode);
                    // values.put("Sector_Code", panchayat.get(i).getSectorCode());
                   /* String[] param={panchayat.get(i).getCode()};
                    long update = db.update("Panchayat", values, "Code = ?", param);
                    if (!(update>0))*/
                    c = db.insert("Panchayat1", null, values);

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public String getPanchayatName(String pcode, Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String kname = "";

        String[] whereArgs = new String[]{pcode};
        Cursor c = db.rawQuery(
                "select * from Panchayat where Code=?",
                whereArgs);

        if (c.getCount() > 0) {
            c.moveToNext();
            kname = c.getString(c.getColumnIndex("Name"));

        }
        c.close();


        return kname;

    }


    public String getDistrictName(String dcode, Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String name = "";

        String[] whereArgs = new String[]{dcode};
        Cursor c = db.rawQuery(
                "select * from District where Code=?",
                whereArgs);

        if (c.getCount() > 0) {
            c.moveToNext();
            name = c.getString(c.getColumnIndex("Name"));

        }
        c.close();
        return name;

    }


    public String getBlockName(String bcode, Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String name = "";

        String[] whereArgs = new String[]{bcode};
        Cursor c = db.rawQuery(
                "select * from Block where Code=?",
                whereArgs);

        if (c.getCount() > 0) {
            c.moveToNext();
            name = c.getString(c.getColumnIndex("Name"));

        }
        c.close();
        return name;

    }


    public String getSpinnerValue(Context context, String fieldName, String code) {

        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String name = "";

        String[] whereArgs = new String[]{fieldName, code};
        Cursor c = db.rawQuery(
                "select Value from SpinnerMaster where Field=? and Code=?",
                whereArgs);

        if (c.getCount() > 0) {
            c.moveToNext();
            name = c.getString(c.getColumnIndex("Value"));

        }
        c.close();
        return name;
    }


    public long deleteCredential(Context context, String pid) {

        DataBaseHelper placeData = new DataBaseHelper(context);
        SQLiteDatabase db = placeData.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("Block_Code", "");
        values.put("BlockName", "");
        values.put("District_Code", "");
        values.put("DistrictName", "");
        values.put("Panchayat_Code", "");
        values.put("PanchayatName", "");
        values.put("WardName", "");
        values.put("Ward_Code", "");
        values.put("Fyear", "");
        values.put("FyearName", "");
        String[] param = {"1"};

        long update = db.update("Credential", values, "slno = ?", param);
        if (!(update > 0)) {
            update = db.insert("Credential", null, values);
        }
        db.close();
        return update;
    }



    public ArrayList<ward> getWardList(String Pan_Code) {
        ArrayList<ward> deptList = new ArrayList<ward>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] { Pan_Code };

            Cursor cur = db
                    .rawQuery(
                            "SELECT * from Ward WHERE PanchayatCode= ?", params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                ward dept = new ward();
                dept.setWardCode(cur.getString(cur.getColumnIndex("WardCode")));
                dept.setWardname(cur.getString(cur.getColumnIndex("WardName")));
                dept.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                dept.setAreaType(cur.getString(cur.getColumnIndex("AreaType")));

                deptList.add(dept);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return deptList;
    }

    public ArrayList<VillageListEntity> getVillageList(String Pan_Code) {
        ArrayList<VillageListEntity> deptList = new ArrayList<VillageListEntity>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] { Pan_Code };

            Cursor cur = db
                    .rawQuery(
                            "SELECT * from VillageList WHERE PanchayatCode= ?", params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                VillageListEntity dept = new VillageListEntity();
                dept.setVillCode(cur.getString(cur.getColumnIndex("VillageCode")));
                dept.setVillName(cur.getString(cur.getColumnIndex("VillageName")));
                dept.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                dept.setBlockCode(cur.getString(cur.getColumnIndex("BLOCKCODE")));

                deptList.add(dept);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return deptList;
    }

    public ArrayList<PanchayatData> getPanchayt(String blockCode) {
        ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] { blockCode };

            Cursor cur = db
                    .rawQuery(
                            "SELECT PanchayatCode,PanchayatName,DistrictCode,BlockCode,PACName from Panchayat WHERE BlockCode = ? ORDER BY PanchayatName",
                            params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                PanchayatData panchayat = new PanchayatData();
                panchayat.setPcode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                panchayat.setPname(cur.getString(cur.getColumnIndex("PanchayatName")));
                panchayat.setBcode(cur.getString(cur.getColumnIndex("BlockCode")));
                panchayat.setDcode(cur.getString(cur.getColumnIndex("DistrictCode")));
                panchayat.setAreaType(cur.getString(cur.getColumnIndex("PACName")));

                panchayatList.add(panchayat);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return panchayatList;
    }


    public ArrayList<PanchayatData> getPanchaytAreawise(String blockCode, String areaType) {
        ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] { blockCode, areaType };

            Cursor cur = db
                    .rawQuery(
                            "SELECT PanchayatCode,PanchayatName,DistrictCode,BlockCode,PACName from Panchayat WHERE BlockCode = ? AND PACName = ? ORDER BY PanchayatName",
                            params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                PanchayatData panchayat = new PanchayatData();
                panchayat.setPcode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                panchayat.setPname(cur.getString(cur.getColumnIndex("PanchayatName")));
                panchayat.setBcode(cur.getString(cur.getColumnIndex("BlockCode")));
                panchayat.setDcode(cur.getString(cur.getColumnIndex("DistrictCode")));
                panchayat.setAreaType(cur.getString(cur.getColumnIndex("PACName")));

                panchayatList.add(panchayat);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return panchayatList;
    }


    public ArrayList<District> getDistrict() {
        ArrayList<District> districtList = new ArrayList<District>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db
                    .rawQuery(
                            "SELECT DistCode,DistName,DistCode3,Zone,Circle from DistrictMwrd ORDER BY DistName",
                            null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                District district = new District();
                district.set_DistCode(cur.getString(cur.getColumnIndex("DistCode")));
                district.set_DistName(cur.getString(cur.getColumnIndex("DistName")));
                district.setDistCode3(cur.getString(cur.getColumnIndex("DistCode3")));
                district.setZone(cur.getString(cur.getColumnIndex("Zone")));
                district.setCircle(cur.getString(cur.getColumnIndex("Circle")));

                districtList.add(district);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return districtList;
    }

    public ArrayList<District> getDistrictUserBy() {
        //CREATE TABLE "DistDetail" ( `Code` TEXT NOT NULL, `Name` TEXT, `slno`
        // INTEGER, PRIMARY KEY(`Code`) )
        ArrayList<District> districtList = new ArrayList<District>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db
                    .rawQuery(
                            "SELECT Code,Name from DistDetailUserBy ORDER BY Name",
                            null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                District district = new District();
                district.set_DistCode(cur.getString(cur
                        .getColumnIndex("Code")));
                district.set_DistName(cur.getString(cur
                        .getColumnIndex("Name")));

                districtList.add(district);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception

        }
        return districtList;

    }

    public ArrayList<Block> getBlock(String distCode) {

        ArrayList<Block> blockList = new ArrayList<Block>();
//CREATE TABLE `Block` ( `slno` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
// `District_Code` TEXT, `Code` TEXT, `Name` TEXT )
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] { distCode };
            Cursor cur = db
                    .rawQuery(
                            "SELECT BlockCode,DistCode,BlockName from Blocks WHERE DistCode = ? ORDER BY BlockName ",
                            params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                Block block = new Block();
                block.setBlockCode(cur.getString(cur
                        .getColumnIndex("BlockCode")));
                block.setBlockName(cur.getString(cur
                        .getColumnIndex("BlockName")));
                block.setDistCode(cur.getString(cur
                        .getColumnIndex("DistCode")));

                blockList.add(block);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
        return blockList;

    }
    public ArrayList<PanchayatData> getPanchayatLocal(String blkId) {
        //CREATE TABLE `Panchayat1` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `DistCode` TEXT,
        // `BlockCode` TEXT, `PanchayatCode` TEXT, `PanchayatName` TEXT )
        ArrayList<PanchayatData> pdetail = new ArrayList<PanchayatData>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM  Panchayat1 where BlockCode='" + blkId + "' order by PanchayatName", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                PanchayatData panchayat = new PanchayatData();
                panchayat.setPcode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                panchayat.setPname((cur.getString(cur.getColumnIndex("PanchayatName"))));
                pdetail.add(panchayat);
            }
            cur.close();
            db.close();
        }
        catch (Exception e) {
        }
        return pdetail;
    }



    public SurfaceSchemeEntity getInspectedSurfaceSchemeDetailForImage(String id){
        //PondInspectionDetail info = null;

        // ArrayList<SurfaceSchemeEntity> infoList = new ArrayList<SurfaceSchemeEntity>();
        SurfaceSchemeEntity info = new SurfaceSchemeEntity();
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{id};

            Cursor cur = db.rawQuery("Select Photo1,Photo2,Photo3,Photo4 from SurfaceSchemeDetail WHERE SCHEME_ID=?",params);
            // Cursor cur = db.rawQuery("Select SCHEME_ID, SCHEME_NAME, TYPE_OF_SCHEME, District, Block, Panchayat, SOURCE_OF_WATER, Fund_Type, FINANCIAL_YEAR, NIT_No, SC_ST_Majority_Village, Updated, CrossVerification, InspectionBy, SurveyorName, SurvyorPhone, InspectionDate, WorkStatus, WorkCompletionPer, WorkDone, ObservationCategory, Latitude, Longitude, Comment1, Comment2, Comment3, Comment4, AppVersion  from SurfaceSchemeDetail WHERE Updated=?",params);

            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {




//                info.setSCHEME_ID(cur.getString(cur.getColumnIndex("SCHEME_ID")));
//                info.setSCHEME_NAME(cur.getString(cur.getColumnIndex("SCHEME_NAME")));
//                info.setTYPE_OF_SCHEME(cur.getString(cur.getColumnIndex("TYPE_OF_SCHEME")));
//                info.setDistrict(cur.getString(cur.getColumnIndex("District")));
//                info.setBlock(cur.getString(cur.getColumnIndex("Block")));
//                info.setPanchayat(cur.getString(cur.getColumnIndex("Panchayat")));
//                info.setSOURCE_OF_WATER(cur.getString(cur.getColumnIndex("SOURCE_OF_WATER")));
//                info.setFund_Type(cur.getString(cur.getColumnIndex("Fund_Type")));
//                info.setFINANCIAL_YEAR(cur.getString(cur.getColumnIndex("FINANCIAL_YEAR")));
//                info.setNIT_No(cur.getString(cur.getColumnIndex("NIT_No")));
//                info.setSC_ST_Majority_Village(cur.getString(cur.getColumnIndex("SC_ST_Majority_Village")));
//
//                info.setUpdated(cur.getString(cur.getColumnIndex("Updated")));
//                info.setCrossVerification(cur.getString(cur.getColumnIndex("CrossVerification")));
//                info.setInspectionBy(cur.getString(cur.getColumnIndex("InspectionBy")));
//                info.setSurveyorName(cur.getString(cur.getColumnIndex("SurveyorName")));
//                info.setSurvyorPhone(cur.getString(cur.getColumnIndex("SurvyorPhone")));
//                info.setInspectionDate(cur.getString(cur.getColumnIndex("InspectionDate")));
//                info.setWorkStatus(cur.getString(cur.getColumnIndex("WorkStatus")));
//                info.setWorkCompletionPer(cur.getString(cur.getColumnIndex("WorkCompletionPer")));
//                info.setWorkDone(cur.getString(cur.getColumnIndex("WorkDone")));
//                info.setObservationCategory(cur.getString(cur.getColumnIndex("ObservationCategory")));
//                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
//                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
//                info.setComment1(cur.getString(cur.getColumnIndex("Comment1")));
//                info.setComment2(cur.getString(cur.getColumnIndex("Comment2")));
//                info.setComment3(cur.getString(cur.getColumnIndex("Comment3")));
//                info.setComment4(cur.getString(cur.getColumnIndex("Comment4")));
//                info.setAppVersion(cur.getString(cur.getColumnIndex("AppVersion")));

                if (!cur.isNull(cur.getColumnIndex("Photo1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto2(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo3"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo3"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);;
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto3(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo4"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo4"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto4(encodedImg1);
                }

                //  infoList.add(info);
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();
        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return info;
    }


    public String getPostWhereConditionForStudentListForAttendance(String finyr, String schemeid) {

        String subWhere=" ";

        if(!finyr.equalsIgnoreCase("0"))
        {
           subWhere += "AND FINANCIAL_YEAR='"+finyr+"'";
           // subWhere += "FINANCIAL_YEAR='"+finyr+"'";
        }
        if(!schemeid.equalsIgnoreCase(""))
        {
            subWhere += " AND SCHEME_ID='"+ schemeid +"'";
        }
//        if(!fund_id.equalsIgnoreCase(""))
//        {
//            subWhere += " AND Fund_Type='"+ fund_id  +"'";
//        }
//        if(!fyearid.equalsIgnoreCase("0"))
//        {
//            subWhere += " AND FYearID='"+ fyearid  +"'";
//        }

        Log.e("SUBQUERY",subWhere);
        return subWhere;
    }

    public String getPostWhereConditionForStudentListForAttendance1(String finyr, String schemeid, String fund_id, String scheme_type, String distid) {

        String subWhere=" ";

        if(!finyr.equalsIgnoreCase("0"))
        {
            subWhere += "AND FINANCIAL_YEAR='"+finyr+"'";
        }
        if(!schemeid.equalsIgnoreCase(""))
        {
            subWhere += " AND SCHEME_ID='"+ schemeid +"'";
        }
        if(!fund_id.equalsIgnoreCase("0"))
        {
            subWhere += " AND Fund_Type='"+ fund_id  +"'";
        }
        if(!scheme_type.equalsIgnoreCase("0"))
        {
            subWhere += " AND TYPE_OF_SCHEME='"+ scheme_type  +"'";
        }
        if(!distid.equalsIgnoreCase(""))
        {
            subWhere += " AND District='"+ distid  +"'";
        }

        Log.e("SUBQUERY",subWhere);
        return subWhere;
    }

    public ArrayList<SurfaceSchemeEntity> getSurfaceSchemeDetailAsPerAdditionalFilter(String finyr, String schemeid, String fund_id, String scheme_type, String distid){
        //PondInspectionDetail info = null;

        ArrayList<SurfaceSchemeEntity> infoList = new ArrayList<SurfaceSchemeEntity>();
        String whereCondition=getPostWhereConditionForStudentListForAttendance1(finyr,schemeid,fund_id,scheme_type,distid);
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"0"};

            //   Cursor cur = db.rawQuery("Select * from SurfaceSchemeDetail WHERE "+ whereCondition +" AND Updated=?",params);
            Cursor cur = db.rawQuery("Select * from SurfaceSchemeDetail WHERE Updated=? "+ whereCondition +" ",params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {

                SurfaceSchemeEntity info = new SurfaceSchemeEntity();
                info.setSCHEME_ID(cur.getString(cur.getColumnIndex("SCHEME_ID")));
                info.setSCHEME_NAME(cur.getString(cur.getColumnIndex("SCHEME_NAME")));
                info.setTYPE_OF_SCHEME(cur.getString(cur.getColumnIndex("TYPE_OF_SCHEME")));
                info.setDistrict(cur.getString(cur.getColumnIndex("District")));
                info.setBlock(cur.getString(cur.getColumnIndex("Block")));
                info.setPanchayat(cur.getString(cur.getColumnIndex("Panchayat")));
                info.setSOURCE_OF_WATER(cur.getString(cur.getColumnIndex("SOURCE_OF_WATER")));
                info.setFund_Type(cur.getString(cur.getColumnIndex("Fund_Type")));
                info.setFINANCIAL_YEAR(cur.getString(cur.getColumnIndex("FINANCIAL_YEAR")));
                info.setNIT_No(cur.getString(cur.getColumnIndex("NIT_No")));
                info.setSC_ST_Majority_Village(cur.getString(cur.getColumnIndex("SC_ST_Majority_Village")));
                infoList.add(info);
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();
        }
        catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }
}