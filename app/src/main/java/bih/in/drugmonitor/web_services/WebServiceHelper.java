package bih.in.drugmonitor.web_services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Node;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import bih.in.drugmonitor.entity.DistributorsListForAdc_Entity;
import bih.in.drugmonitor.entity.District;
import bih.in.drugmonitor.entity.DrugIssuedDetailsList_Entity;
import bih.in.drugmonitor.entity.FilterOptionEntity;
import bih.in.drugmonitor.entity.PanchayatEntity;
import bih.in.drugmonitor.entity.PlantationDetail;
import bih.in.drugmonitor.entity.PlantationReportEntity;
import bih.in.drugmonitor.entity.PlantationSiteEntity;
import bih.in.drugmonitor.entity.PondEncroachmentEntity;
import bih.in.drugmonitor.entity.RequisitionListForAdc_Entity;
import bih.in.drugmonitor.entity.SanrachnaTypeEntity;
import bih.in.drugmonitor.entity.SignUp;
import bih.in.drugmonitor.entity.SurfaceInspectionDetailEntity;
import bih.in.drugmonitor.entity.SurfaceInspectionEntity;
import bih.in.drugmonitor.entity.SurfaceInspectionResponse;
import bih.in.drugmonitor.entity.SurfaceSchemeEntity;
import bih.in.drugmonitor.entity.UserDetails;
import bih.in.drugmonitor.entity.Versioninfo;
import bih.in.drugmonitor.entity.VillageListEntity;
import bih.in.drugmonitor.entity.ward;
import bih.in.drugmonitor.security.Encriptor;
import bih.in.drugmonitor.utility.CommonPref;

import static bih.in.drugmonitor.utility.CommonPref.SERVICEURL;

public class WebServiceHelper {

    //public static final String SERVICENAMESPACE = "http://minorirrigation.bihar.gov.in/";
    public static final String SERVICENAMESPACE = CommonPref.SERVICENAMESPACE;

    public static final String SERVICEURL1 = CommonPref.SERVICEURL;


    public static final String APPVERSION_METHOD = "getAppLatest";
    public static final String AUTHENTICATE_METHOD = "Login";
    public static final String GetReqList_Adc = "getDDCPendingList";
    public static final String GetDistributorsMapped_Adc = "gethospitalfortheissuanceofmedicineList";
    public static final String GetIssuedDrugsDetails = "getDrugissuedHospitalByDistributorList";
    public static final String ApproveDrugReq = "getDrugissuedHospitalByDistributorList";



    private static final String FIELD_METHOD = "getFieldInformation";
    private static final String SPINNER_METHOD = "getSpinnerInformation";
    //private static final String UPLOAD_METHOD = "InsertData";
    private static final String REGISTER_USER = "RegisterUser";

    private static final String BLOCK_METHOD = "getBlock";

    private static final String GETINITIALPLANTATIONDATA = "getInitialDetailRDDPlantation";
    private static final String PONDLAKEENCRCHMNTDATA = "getInitialDetailsPondLakeDataCoVerified";
    private static final String WELLNCRCHMNTDATA = "getInitialDetailsWellDataCoVerified";
    private static final String GETPLANTATIONINSPECTIONDETAIL = "getPlantationInspdetails";
    private static final String WELLINSPECTIONLIST = "getWellInspectionList";
    private static final String UPLOADPLANTATIONINSPECTIONDETAIL = "PlantationInspDetails";
    private static final String UPLOADSCHEMEINSPECTIONDETAIL = "Inspection_Insert";
    private static final String GETVILLAGELIST = "getVillageList";
    private static final String GETPLANATATIONSITELIST = "getPlantationSite";
    private static final String GETSANRACHNATYPELIST = "getTypesOfSanrchnaList";
    private static final String GETWARDLIST = "getWardList";
    private static final String GETPANCHAYATLIST = "getPanchayatList";
    private static final String GETDISTRICTLIST = "Districts_Select";
    private static final String GETSURFACESCHEMELIST = "Surface_Search";
    private static final String GETOPTOINFILTERLIST = "Options_Filter";
    private static final String GETSURFACESCHEMEINSPECTIONLIST = "Inspection_Search";
    private static final String GETSURFACESCHEMEINSPECTIONDETAIL = "Inspection_Search_On_Inspection_ID";

    static String rest;

    public static Versioninfo CheckVersion(String version) {
        Versioninfo versioninfo;
        SoapObject res1;
        try {

            res1=getServerData(APPVERSION_METHOD, Versioninfo.Versioninfo_CLASS,"IMEI","Ver","0",version);
            SoapObject final_object = (SoapObject) res1.getProperty(0);

            versioninfo = new Versioninfo(final_object);

        } catch (Exception e) {

            return null;
        }
        return versioninfo;

    }


    public static String completeSignup(SignUp data, String imei, String version) {
        SoapObject request = new SoapObject(SERVICENAMESPACE, REGISTER_USER);
        request.addProperty("Name",data.getName());
        request.addProperty("DistrictCode",data.getDist_code());
        request.addProperty("BlockCode",data.getBlock_code());
        request.addProperty("MobileNo",data.getMobile());
        request.addProperty("Degignation",data.getDesignation());
        //request.addProperty("CreatedBy",data.getUpload_by());
        request.addProperty("IMEI",imei);
        request.addProperty("Appversion",version);
        request.addProperty("Pwd","abc");
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + REGISTER_USER,
                    envelope);
            // res2 = (SoapObject) envelope.getResponse();
            rest = envelope.getResponse().toString();

            // rest=res2.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return rest;
    }

    public static String resizeBase64Image(String base64image){
        byte [] encodeByte= Base64.decode(base64image.getBytes(), Base64.DEFAULT);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length,options);


        if(image.getHeight() <= 200 && image.getWidth() <= 200){
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, 100, 100, false);

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,100, baos);

        byte [] b=baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);

    }

//    public static UserDetails Login(String Enc_User_ID, String Enc_Pwd, String capId ,String randomenum) {
//        try {
//            SoapObject res1;
//            res1=getServerData(AUTHENTICATE_METHOD, UserDetails.getUserClass(),"user_ID","Password",Enc_User_ID,Enc_Pwd);
//            if (res1 != null) {
//                return new UserDetails(res1);
//            } else
//                return null;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static UserDetails Login(String EncUser_ID, String EncPwd, String capId ,String randomenum) {

        Encriptor _encrptor = new Encriptor();
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, AUTHENTICATE_METHOD);


            request.addProperty("skey", _encrptor.Encrypt(randomenum, CommonPref.CIPER_KEY));
            // Log.d("ffhgeyhbhbe",Login.appid+"%%"+_encrptor.Encrypt(randomenum, CommonPref.CIPER_KEY));
            request.addProperty("cap", capId);

            org.kxml2.kdom.Element[] header = new org.kxml2.kdom.Element[1];
            header[0] = new org.kxml2.kdom.Element().createElement(SERVICENAMESPACE, "SecuredTokenWebservice");
            org.kxml2.kdom.Element uid = new org.kxml2.kdom.Element().createElement(SERVICENAMESPACE, "UserId");
            uid.addChild(Node.TEXT, EncUser_ID);
            header[0].addChild(Node.ELEMENT, uid);

            org.kxml2.kdom.Element pwd = new org.kxml2.kdom.Element().createElement(SERVICENAMESPACE, "pwd");
            pwd.addChild(Node.TEXT, EncPwd);
            header[0].addChild(Node.ELEMENT, pwd);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.headerOut = header;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, UserDetails.USER_CLASS.getSimpleName(), UserDetails.USER_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + AUTHENTICATE_METHOD, envelope);

            Object result = envelope.getResponse();

            if (result != null) {
                return new UserDetails((SoapObject) result);
            } else
                return null;

        } catch (Exception e) {
            //e.printStackTrace();
            Log.d("TAG", "Error while Login");

            return null;
        }

    }


    public static ArrayList<FilterOptionEntity> getFilterOptionData(String optiontype) {

        SoapObject res1;
        res1=getServerData(GETOPTOINFILTERLIST, FilterOptionEntity.FilterOptionEntity_CLASS, "Option_Name", optiontype);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<FilterOptionEntity> fieldList = new ArrayList<FilterOptionEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    FilterOptionEntity plantationData= new FilterOptionEntity(final_object);
                    fieldList.add(plantationData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<SurfaceSchemeEntity> getSurfaceSchemeData(String usertype, String userId, String userpassword, String schemeId, String schemeType, String schemeName, String finanYr, String fundType) {

        SoapObject res1;
        res1=getServerData(GETSURFACESCHEMELIST, SurfaceSchemeEntity.SurfaceSchemeEntity_CLASS, "user_type", "user_ID","Password", "SCHEME_ID", "TYPE_OF_SCHEME","SCHEME_NAME", "FINANCIAL_YEAR", "Fund_Type", usertype,userId,userpassword,schemeId,schemeType,schemeName,finanYr,fundType);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<SurfaceSchemeEntity> fieldList = new ArrayList<SurfaceSchemeEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    SurfaceSchemeEntity plantationData= new SurfaceSchemeEntity(final_object);
                    fieldList.add(plantationData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static SurfaceInspectionEntity getSurfaceSchemeInspectionData(String usertype, String userId, String userpassword, String Inspection_ID){

        try {
            SoapObject res1;
            res1=getServerData(GETSURFACESCHEMEINSPECTIONDETAIL, SurfaceInspectionEntity.SurfaceInspectionEntity_CLASS, "user_type", "user_ID","Password", "Inspection_ID", usertype,userId,userpassword,Inspection_ID);

            if(res1!=null){
                return new SurfaceInspectionEntity(res1);
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<SurfaceInspectionDetailEntity> getSurfaceSchemeInspectionData(String usertype, String userId, String userpassword, String schemeId, String schemeName, String finanYr, String designation, String obsrvCat) {

        SoapObject res1;
        res1=getServerData(GETSURFACESCHEMEINSPECTIONLIST, SurfaceSchemeEntity.SurfaceSchemeEntity_CLASS, "user_type", "user_ID","Password", "SCHEME_ID", "SCHEME_NAME", "FINANCIAL_YEAR", "DESIGNATION", "Observetion_Category", usertype,userId,userpassword,schemeId,schemeName,finanYr,designation,obsrvCat);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<SurfaceInspectionDetailEntity> fieldList = new ArrayList<SurfaceInspectionDetailEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    SurfaceInspectionDetailEntity plantationData= new SurfaceInspectionDetailEntity(final_object);
                    fieldList.add(plantationData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<PondEncroachmentEntity> getPondLakeWellEncrhmntData(String blockId, String type) {

        SoapObject res1;
        res1=getServerData(type == "pond" ? PONDLAKEENCRCHMNTDATA : WELLNCRCHMNTDATA, PondEncroachmentEntity.PondEncroachmentEntity_CLASS,"blockid",blockId);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PondEncroachmentEntity> fieldList = new ArrayList<PondEncroachmentEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PondEncroachmentEntity pondData= new PondEncroachmentEntity(final_object);
                    fieldList.add(pondData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }


    public static ArrayList<PlantationDetail> getPlantationData(String disctrictCode, String userRole) {

        SoapObject res1;
        res1=getServerData(GETINITIALPLANTATIONDATA, PlantationDetail.PlantationDetail_CLASS, "DistCode", "UserRole", disctrictCode,userRole);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PlantationDetail> fieldList = new ArrayList<PlantationDetail>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PlantationDetail plantationData= new PlantationDetail(final_object);
                    fieldList.add(plantationData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<PlantationReportEntity> getePlantationReport(String userID) {

        SoapObject res1;
        res1=getServerData(GETPLANTATIONINSPECTIONDETAIL, PlantationReportEntity.PlantationReportEntity_CLASS, "UserId",userID);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PlantationReportEntity> fieldList = new ArrayList<PlantationReportEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PlantationReportEntity data= new PlantationReportEntity(final_object);
                    fieldList.add(data);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<ward> getWardListData(String BlockCode) {


        SoapObject res1;
        res1 = getServerData(GETWARDLIST, ward.ward_CLASS, "BlockCode", BlockCode);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();

        ArrayList<ward> fieldList = new ArrayList<ward>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    ward wardInfo = new ward(final_object);
                    fieldList.add(wardInfo);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<SanrachnaTypeEntity> getSanrachnaTypeData() {

        SoapObject res1;
        res1=getServerData(GETSANRACHNATYPELIST, SanrachnaTypeEntity.SanrachnaType_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<SanrachnaTypeEntity> fieldList = new ArrayList<SanrachnaTypeEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    SanrachnaTypeEntity villageData= new SanrachnaTypeEntity(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<PlantationSiteEntity> getPlantationSiteData() {

        SoapObject res1;
        res1=getServerData(GETPLANATATIONSITELIST, PlantationSiteEntity.PlantationSiteEntity_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PlantationSiteEntity> fieldList = new ArrayList<PlantationSiteEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PlantationSiteEntity data= new PlantationSiteEntity(final_object);
                    fieldList.add(data);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<VillageListEntity> getVillageListData(String BlockCode) {

        SoapObject res1;
        res1=getServerData(GETVILLAGELIST, VillageListEntity.VillageList_CLASS,"blockCode",BlockCode);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<VillageListEntity> fieldList = new ArrayList<VillageListEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    VillageListEntity villageData= new VillageListEntity(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<PanchayatEntity> getPanchayatList(String DistCode, String BlockCode) {

        SoapObject res1;
        res1=getServerData(GETPANCHAYATLIST, PanchayatEntity.PanchayatEntity_CLASS,"DistCode", "BlockCode", DistCode, BlockCode);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PanchayatEntity> fieldList = new ArrayList<PanchayatEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PanchayatEntity villageData= new PanchayatEntity(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static SurfaceInspectionResponse uploadSurfaceInspectionData(SurfaceSchemeEntity data, SurfaceSchemeEntity dataimg, UserDetails user) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, UPLOADSCHEMEINSPECTIONDETAIL);

        request.addProperty("user_type",user.getUserrole());
        request.addProperty("user_ID",user.getUserID());
        request.addProperty("Password",user.getPassword());
        request.addProperty("SCHEME_ID",data.getSCHEME_ID());
        request.addProperty("INSPECTION_BY_NAME",data.getSurveyorName());
        request.addProperty("INSPECTION_BY_Phone",data.getSurvyorPhone());
        request.addProperty("DESIGNATION", data.getInspectionBy());
        request.addProperty("INSPECTION_DATE", data.getInspectionDate());
        request.addProperty("COMMENT", data.getComment1());
        request.addProperty("COMMENT2", data.getComment2());
        request.addProperty("COMMENT3", data.getComment3());
        request.addProperty("COMMENT4", data.getComment4());

        request.addProperty("USER_ID",user.getUserID());
        request.addProperty("Work_Competion_In_Presentage", data.getWorkCompletionPer());
        request.addProperty("Observetion_Category", data.getObservationCategory());
        request.addProperty("Work_Done_as_Previous_Comment", data.getWorkDone());
        request.addProperty("Work_Status", data.getWorkStatus());
        request.addProperty("Cross_Verification", "");
        request.addProperty("S_W_Version", data.getAppVersion());
        request.addProperty("INSPECTION_PDF", "");

        request.addProperty("Inspection_Image1", dataimg.getPhoto1());
        request.addProperty("Inspection_Image2", dataimg.getPhoto2());
        request.addProperty("Inspection_Image3", dataimg.getPhoto3());
        request.addProperty("Inspection_Image4", dataimg.getPhoto4());
        request.addProperty("latitude", data.getLatitude());
        request.addProperty("longitude", data.getLongitude());

        SoapObject res1 = null;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + UPLOADSCHEMEINSPECTIONDETAIL,
                    envelope);
             res1 = (SoapObject) envelope.getResponse();
            //rest = envelope.getResponse().toString();
            if(res1 != null){
                return new SurfaceInspectionResponse(res1);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
            //return null;
    }

    public static String uploadPlantationDate(PlantationDetail data) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, UPLOADPLANTATIONINSPECTIONDETAIL);

        String Van_Posak_bhugtaan = data.getPosak_bhugtaanMonth()+"-"+data.getPosak_bhugtaanYear();

        request.addProperty("_DistCode",data.getDistID());
        request.addProperty("_BlockCode",data.getBlockID());
        request.addProperty("_PanchayatCode",data.getPanchayatID());
//        request.addProperty("_BhumiType",data.getBhumiType().trim());
//        request.addProperty("_Years",data.getFyear());
        request.addProperty("_Remarks",data.getRemarks());
        request.addProperty("_InspectionID",data.getInspectionID());
        request.addProperty("_Ropit_PlantNo",data.getRopit_PlantNo());
        request.addProperty("_Utarjibit_PlantNo", data.getUtarjibit_PlantNo());
        request.addProperty("_UtarjibitaPercent", data.getUtarjibitaPercent());
        request.addProperty("_Utarjibit_90PercentMore", data.getUtarjibit_90PercentMore());
        request.addProperty("_Utarjibit_75_90Percent", data.getUtarjibit_75_90Percent());
        request.addProperty("_Utarjibit_50_75Percent", data.getUtarjibit_50_75Percent());
        request.addProperty("_Utarjibit_25PercentLess", data.getUtarjibit_25PercentLess());

        request.addProperty("_IsInspected", "Y");
        request.addProperty("_IsInspectedDate",data.getVerifiedDate());
        request.addProperty("_IsInspectedBy", data.getVerifiedBy().toUpperCase());
        request.addProperty("_AppVersion", data.getAppVersion());
        request.addProperty("_photo", data.getPhoto());
        request.addProperty("_Photo1", data.getPhoto1());
        request.addProperty("_Latitude_Mob", data.getLatitude_Mob());
        request.addProperty("_Longitude_Mob", data.getLongitude_Mob());
        request.addProperty("_Userrole", data.getUserRole());

        request.addProperty("_Plantation_Site_Id", data.getPlantation_Site_Id());
        request.addProperty("_Van_Posako_No", data.getVan_Posako_No());
        request.addProperty("_Van_Posak_bhugtaan", Van_Posak_bhugtaan);
        request.addProperty("_gavyan_percentage", data.getGavyan_percentage());
        request.addProperty("_Average_height_Plant", data.getAverage_height_Plant());

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + UPLOADPLANTATIONINSPECTIONDETAIL,
                    envelope);
            // res2 = (SoapObject) envelope.getResponse();
            rest = envelope.getResponse().toString();

            // rest=res2.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return rest;
    }

    public static ArrayList<District> getDistrictList() {



        SoapObject request = new SoapObject(SERVICENAMESPACE,GETDISTRICTLIST);

        //request.addProperty("BlockCode", dist_Code);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE,District.DISTRICT_CLASS.getSimpleName(), District.DISTRICT_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + GETDISTRICTLIST,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<District> pvmArrayList = new ArrayList<District>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    District panchayat = new District(final_object);
                    pvmArrayList.add(panchayat);
                }
            } else
                return pvmArrayList;
        }

        return pvmArrayList;
    }

//    public static ArrayList<District> getDistrictList() {
//
//        SoapObject res1;
//        res1=getServerData(GETDISTRICTLIST, District.DISTRICT_CLASS);
//        int TotalProperty=0;
//        if(res1!=null) TotalProperty= res1.getPropertyCount();
//
//        ArrayList<District> fieldList = new ArrayList<District>();
//
//        for (int i = 0; i < TotalProperty; i++) {
//            if (res1.getProperty(i) != null) {
//                Object property = res1.getProperty(i);
//                if (property instanceof SoapObject) {
//                    SoapObject final_object = (SoapObject) property;
//                    District villageData= new District(final_object);
//                    fieldList.add(villageData);
//                }
//            } else
//                return fieldList;
//        }
//
//        return fieldList;
//    }


    public static SoapObject getServerData(String methodName, Class bindClass)
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }


    public static SoapObject getServerData(String methodName, Class bindClass, String param, String value )
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param,value);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }



    public static SoapObject getServerData(String methodName, Class bindClass, String param1, String param2, String value1, String value2 )
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param1,value1);
            request.addProperty(param2,value2);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }

    public static SoapObject getServerData(String methodName, Class bindClass, String param1, String param2, String param3, String value1, String value2, String value3 )
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param1,value1);
            request.addProperty(param2,value2);
            request.addProperty(param3,value3);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }
    public static SoapObject getServerData(String methodName, Class bindClass, String param1, String param2, String param3, String param4, String value1, String value2, String value3, String value4 )
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param1,value1);
            request.addProperty(param2,value2);
            request.addProperty(param3,value3);
            request.addProperty(param4,value4);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }

    public static SoapObject getServerData(String methodName, Class bindClass, String param1, String param2, String param3, String param4,String param5, String value1, String value2, String value3, String value4, String value5)
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param1,value1);
            request.addProperty(param2,value2);
            request.addProperty(param3,value3);
            request.addProperty(param4,value4);
            request.addProperty(param5,value5);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }
    public static SoapObject getServerData(String methodName, Class bindClass, String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8, String value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8)
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param1,value1);
            request.addProperty(param2,value2);
            request.addProperty(param3,value3);
            request.addProperty(param4,value4);
            request.addProperty(param5,value5);
            request.addProperty(param6,value6);
            request.addProperty(param7,value7);
            request.addProperty(param8,value8);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }


    public static SoapObject getServerData(String methodName, Class bindClass, String param1, String param2, String param3, String param4, String param5, String param6, String param7, String value1, String value2, String value3, String value4, String value5, String value6, String value7)
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param1,value1);
            request.addProperty(param2,value2);
            request.addProperty(param3,value3);
            request.addProperty(param4,value4);
            request.addProperty(param5,value5);
            request.addProperty(param6,value6);
            request.addProperty(param7,value7);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }
    public static ArrayList<RequisitionListForAdc_Entity> GetReqListForADcApproval(Context context,String date,String hospid,String drugid, String distCode,String usertypeid, String randomNo, String capId)
    {
        Encriptor _encrptor = new Encriptor();
        SoapObject res1 = null;
        try {
          //  res1 = getServerData(GetReqList_Adc, RequisitionListForAdc_Entity.REQ_CLASS, "skey", "District_Code", "cap", _encrptor.Encrypt(randomNo, CommonPref.CIPER_KEY), distCode, capId);
            res1 = getServerData(GetReqList_Adc, RequisitionListForAdc_Entity.REQ_CLASS, "skey","_datae","_hospitalid","_drugid", "_distcode","_UserTypeId", "cap", "T/0e2rl0kHIvBtEas5Dv4g==","",hospid,drugid, distCode,usertypeid, "wZWV8HB10WGccFXPUJIyRw==");
           // res1 = getServerData(GetReqList_Adc, RequisitionListForAdc_Entity.REQ_CLASS, "skey","_datae","_hospitalid","_drugid", "_distcode","_UserTypeId", "cap", "T/0e2rl0kHIvBtEas5Dv4g==","","597","2", "208","6", "wZWV8HB10WGccFXPUJIyRw==");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<RequisitionListForAdc_Entity> fieldList = new ArrayList<RequisitionListForAdc_Entity>();

        for (int i = 0; i < TotalProperty; i++)
        {
            if (res1.getProperty(i) != null)
            {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject)
                {
                    SoapObject final_object = (SoapObject) property;
                    RequisitionListForAdc_Entity block= new RequisitionListForAdc_Entity(context,final_object);
                    fieldList.add(block);
                }
            }
            else
            {
                return fieldList;
            }

        }

        return fieldList;
    }


    public static ArrayList<DistributorsListForAdc_Entity> GetDistributorsMappedWithAdc(Context context, String distCode, String stateid, String drugid,String randomNo, String capId)
    {
        Encriptor _encrptor = new Encriptor();
        SoapObject res1 = null;
        try {
            //  res1 = getServerData(GetReqList_Adc, RequisitionListForAdc_Entity.REQ_CLASS, "skey", "District_Code", "cap", _encrptor.Encrypt(randomNo, CommonPref.CIPER_KEY), distCode, capId);
            res1 = getServerData(GetDistributorsMapped_Adc, DistributorsListForAdc_Entity.DISTRIBUTOR_CLASS, "skey","_DistCode","_StateCode","_drugid", "cap", "T/0e2rl0kHIvBtEas5Dv4g==",distCode,stateid,drugid,"wZWV8HB10WGccFXPUJIyRw==");
            // res1 = getServerData(GetReqList_Adc, RequisitionListForAdc_Entity.REQ_CLASS, "skey","_datae","_hospitalid","_drugid", "_distcode","_UserTypeId", "cap", "T/0e2rl0kHIvBtEas5Dv4g==","","597","2", "208","6", "wZWV8HB10WGccFXPUJIyRw==");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<DistributorsListForAdc_Entity> fieldList = new ArrayList<DistributorsListForAdc_Entity>();

        for (int i = 0; i < TotalProperty; i++)
        {
            if (res1.getProperty(i) != null)
            {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject)
                {
                    SoapObject final_object = (SoapObject) property;
                    DistributorsListForAdc_Entity block= new DistributorsListForAdc_Entity(context,final_object);
                    fieldList.add(block);
                }
            }
            else
            {
                return fieldList;
            }

        }

        return fieldList;
    }


    public static ArrayList<DrugIssuedDetailsList_Entity> GetIssuedDrugDetails(Context context, String hosp_id, String randomNo, String capId)
    {
        Encriptor _encrptor = new Encriptor();
        SoapObject res1 = null;
        try {
            //  res1 = getServerData(GetReqList_Adc, RequisitionListForAdc_Entity.REQ_CLASS, "skey", "District_Code", "cap", _encrptor.Encrypt(randomNo, CommonPref.CIPER_KEY), distCode, capId);
            res1 = getServerData(GetIssuedDrugsDetails, DrugIssuedDetailsList_Entity.DRUG_ISSUED_CLASS, "skey","_HospitalID", "cap", "T/0e2rl0kHIvBtEas5Dv4g==",hosp_id,"wZWV8HB10WGccFXPUJIyRw==");
            // res1 = getServerData(GetReqList_Adc, RequisitionListForAdc_Entity.REQ_CLASS, "skey","_datae","_hospitalid","_drugid", "_distcode","_UserTypeId", "cap", "T/0e2rl0kHIvBtEas5Dv4g==","","597","2", "208","6", "wZWV8HB10WGccFXPUJIyRw==");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<DrugIssuedDetailsList_Entity> fieldList = new ArrayList<DrugIssuedDetailsList_Entity>();

        for (int i = 0; i < TotalProperty; i++)
        {
            if (res1.getProperty(i) != null)
            {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject)
                {
                    SoapObject final_object = (SoapObject) property;
                    DrugIssuedDetailsList_Entity block= new DrugIssuedDetailsList_Entity(context,final_object);
                    fieldList.add(block);
                }
            }
            else
            {
                return fieldList;
            }

        }

        return fieldList;
    }

    public static String ApproveDrugReq(String distibutor_id,String approved_qty,String randomNo,String Token,String userid) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,ApproveDrugReq);
            Encriptor _encrptor = new Encriptor();
          //  request.addProperty("skey", _encrptor.Encrypt(randomNo, CommonPref.CIPER_KEY));
            request.addProperty("skey","T/0e2rl0kHIvBtEas5Dv4g==");
//            request.addProperty("_BenId",id);
//            request.addProperty("_JeevanPramanStatus",status);

            org.kxml2.kdom.Element[] header = new org.kxml2.kdom.Element[1];
            header[0] = new org.kxml2.kdom.Element().createElement(SERVICENAMESPACE, "SecuredTokenWebservice");
            org.kxml2.kdom.Element uid = new org.kxml2.kdom.Element().createElement(SERVICENAMESPACE, "AuthenticationToken");
            uid.addChild(Node.TEXT, Token);
            header[0].addChild(Node.ELEMENT, uid);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.headerOut=header;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + ApproveDrugReq, envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
