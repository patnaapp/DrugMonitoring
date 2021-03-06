package bih.in.drugmonitor.web_services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.protocol.HTTP;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Node;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import bih.in.drugmonitor.entity.DistributorsListForAdc_Entity;
import bih.in.drugmonitor.entity.District;
import bih.in.drugmonitor.entity.DrugApproval_Entity;
import bih.in.drugmonitor.entity.DrugIssuedDetailsList_Entity;
import bih.in.drugmonitor.entity.FilterOptionEntity;
import bih.in.drugmonitor.entity.PanchayatEntity;
import bih.in.drugmonitor.entity.PatientDetailsList_Entity;
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
import static org.apache.http.util.EntityUtils.getContentCharSet;

public class WebServiceHelper {

    //public static final String SERVICENAMESPACE = "http://minorirrigation.bihar.gov.in/";
    public static final String SERVICENAMESPACE = CommonPref.SERVICENAMESPACE;

    public static final String SERVICEURL1 = CommonPref.SERVICEURL;


    public static final String APPVERSION_METHOD = "getAppLatest";
    public static final String AUTHENTICATE_METHOD = "Authenticate";
    public static final String GetReqList_Adc = "getDDCPendingList";
    public static final String GetDistributorsMapped_Adc = "gethospitalfortheissuanceofmedicineList";
    public static final String GetIssuedDrugsDetails = "getDrugissuedHospitalByDistributorList";
    public static final String ApproveDrugReq = "DDCApprovalForm";
    public static final String GetPatientDetails_List = "getPopupList";



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

//        request.addProperty("user_type",user.getUserrole());
//        request.addProperty("user_ID",user.getUserID());
//        request.addProperty("Password",user.getPassword());
        request.addProperty("SCHEME_ID",data.getSCHEME_ID());
        request.addProperty("INSPECTION_BY_NAME",data.getSurveyorName());
        request.addProperty("INSPECTION_BY_Phone",data.getSurvyorPhone());
        request.addProperty("DESIGNATION", data.getInspectionBy());
        request.addProperty("INSPECTION_DATE", data.getInspectionDate());
        request.addProperty("COMMENT", data.getComment1());
        request.addProperty("COMMENT2", data.getComment2());
        request.addProperty("COMMENT3", data.getComment3());
        request.addProperty("COMMENT4", data.getComment4());

       // request.addProperty("USER_ID",user.getUserID());
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
            res1 = getServerData(GetReqList_Adc, RequisitionListForAdc_Entity.REQ_CLASS, "skey","_datae","_hospitalid","_drugid", "_distcode","_UserTypeId", "cap",  _encrptor.Encrypt(randomNo, CommonPref.CIPER_KEY),"",hospid,drugid, distCode,usertypeid, capId);
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
            res1 = getServerData(GetDistributorsMapped_Adc, DistributorsListForAdc_Entity.DISTRIBUTOR_CLASS, "skey","_DistCode","_StateCode","_drugid", "cap",  _encrptor.Encrypt(randomNo, CommonPref.CIPER_KEY),distCode,stateid,drugid,capId);
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


    public static ArrayList<DrugIssuedDetailsList_Entity> GetIssuedDrugDetails(Context context, String hosp_id, String randomNo, String capId,String hreqid)
    {
        Encriptor _encrptor = new Encriptor();
        SoapObject res1 = null;
        try {
            //  res1 = getServerData(GetReqList_Adc, RequisitionListForAdc_Entity.REQ_CLASS, "skey", "District_Code", "cap", _encrptor.Encrypt(randomNo, CommonPref.CIPER_KEY), distCode, capId);
            res1 = getServerData(GetIssuedDrugsDetails, DrugIssuedDetailsList_Entity.DRUG_ISSUED_CLASS, "skey","_HospitalID","_hreqid", "cap",  _encrptor.Encrypt(randomNo, CommonPref.CIPER_KEY),hosp_id,hreqid,capId);
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


    public static ArrayList<PatientDetailsList_Entity> GetPatientDetails(Context context, String hosp_id, String randomNo, String capId, String hreqid)
    {
        Encriptor _encrptor = new Encriptor();
        SoapObject res1 = null;
        try {
            res1 = getServerData(GetPatientDetails_List, PatientDetailsList_Entity.PATIENT_CLASS, "skey","_HospitalID","_hreqid", "cap",_encrptor.Encrypt(randomNo, CommonPref.CIPER_KEY),hosp_id,hreqid,capId);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PatientDetailsList_Entity> fieldList = new ArrayList<PatientDetailsList_Entity>();

        for (int i = 0; i < TotalProperty; i++)
        {
            if (res1.getProperty(i) != null)
            {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject)
                {
                    SoapObject final_object = (SoapObject) property;
                    PatientDetailsList_Entity block= new PatientDetailsList_Entity(context,final_object);
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


    public static String ApproveDrugReq(String distibutor_id,String approved_qty,String randomNo,String Token,String userid,String hosp_id,String drug_id,String distcode,String statecode,String hreqid,String enc_pwd) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,ApproveDrugReq);
            Encriptor _encrptor = new Encriptor();
          //  request.addProperty("skey", _encrptor.Encrypt(randomNo, CommonPref.CIPER_KEY));
            request.addProperty("skey","T/0e2rl0kHIvBtEas5Dv4g==");
            request.addProperty("_DistCode",distcode);
            request.addProperty("_statecode",statecode);
            request.addProperty("_drugid",drug_id);
            request.addProperty("_HospitalID",hosp_id);
            request.addProperty("_hreqid",hreqid);
            request.addProperty("_pdrid",hreqid);
            request.addProperty("_UserId",userid);
            request.addProperty("_distributorcode",distibutor_id);
            request.addProperty("_actionqty",approved_qty);
            request.addProperty("_approvedqty",approved_qty);
            request.addProperty("_issuedqty",approved_qty);

            org.kxml2.kdom.Element[] header = new org.kxml2.kdom.Element[1];
            header[0] = new org.kxml2.kdom.Element().createElement(SERVICENAMESPACE, "SecuredTokenWebservice");
            org.kxml2.kdom.Element token = new org.kxml2.kdom.Element().createElement(SERVICENAMESPACE, "AuthenticationToken");
            token.addChild(Node.TEXT, Token);
            header[0].addChild(Node.ELEMENT, token);

            org.kxml2.kdom.Element uid = new org.kxml2.kdom.Element().createElement(SERVICENAMESPACE, "UserId");
            uid.addChild(Node.TEXT, userid);
            header[0].addChild(Node.ELEMENT, uid);

            org.kxml2.kdom.Element pwd = new org.kxml2.kdom.Element().createElement(SERVICENAMESPACE, "pwd");
            pwd.addChild(Node.TEXT, enc_pwd);
            header[0].addChild(Node.ELEMENT, pwd);


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

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static String UploadApprovalData(Context context, ArrayList<DrugApproval_Entity> checkbox, String AppVersion, String Devicetype, String randomNo, String capId, String token) {

        context=context;
        String skey="";
        Encriptor _encrptor = new Encriptor();
        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        dbfac.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;
        try
        {
            docBuilder = dbfac.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "0";
        }
        DOMImplementation domImpl = docBuilder.getDOMImplementation();
        //Document doc = domImpl.createDocument("http://mobapp.bih.nic.in/",	UPLOAD_TRACKING_DATA_ARRAY, null);
        Document doc = domImpl.createDocument(SERVICENAMESPACE,ApproveDrugReq, null);
        doc.setXmlVersion("1.0");
        doc.setXmlStandalone(true);

		/*org.kxml2.kdom.Element[] header = new org.kxml2.kdom.Element[1];
		header[0] = new org.kxml2.kdom.Element().createElement(SERVICENAMESPACE, "SecuredTokenWebservice");
		org.kxml2.kdom.Element uid = new org.kxml2.kdom.Element().createElement(SERVICENAMESPACE, "AuthenticationToken");
		uid.addChild(Node.TEXT, token);
		header[0].addChild(Node.ELEMENT, uid);*/

        Element poleElement = doc.getDocumentElement();
        //--------------
        Element pdlsElement = doc.createElement("UploadDDCapprovalValues");
        ArrayList<DrugApproval_Entity> poleDetail = checkbox;
        // Upload(poleDetail, res);
        // Element pdElement = doc.getDocumentElement();

        for(int x=0;x<poleDetail.size();x++)
        {

            try {Element pdElement = doc.createElement("approval");


//                Element fid = doc.createElement("_DistCode");
//                fid.appendChild(doc.createTextNode(_encrptor.Encrypt(poleDetail.get(x).get_DistCode(),randomNo)));
//                pdElement.appendChild(fid);
                Element vLebel = doc.createElement("_statecode");
                vLebel.appendChild(doc.createTextNode(_encrptor.Encrypt(poleDetail.get(x).get_statecode(),randomNo)));
                //vLebel.appendChild(doc.createTextNode("1234"));
                pdElement.appendChild(vLebel);
                Element vLebel2 = doc.createElement("_drugid");
                vLebel2.appendChild(doc.createTextNode(_encrptor.Encrypt(poleDetail.get(x).get_drugid(),randomNo)));
                pdElement.appendChild(vLebel2);
                Element vLebel3 = doc.createElement("_HospitalID");
                vLebel3.appendChild(doc.createTextNode(_encrptor.Encrypt(poleDetail.get(x).get_HospitalID(),randomNo)));
                pdElement.appendChild(vLebel3);
                Element vLebel4 = doc.createElement("_hreqid");
                vLebel4.appendChild(doc.createTextNode(_encrptor.Encrypt(poleDetail.get(x).get_hreqid(),randomNo)));
                pdElement.appendChild(vLebel4);

                Element vLebel5 = doc.createElement("_pdrid");
                vLebel5.appendChild(doc.createTextNode(_encrptor.Encrypt(poleDetail.get(x).get_pdrid(),randomNo)));
                pdElement.appendChild(vLebel5);

                Element vLebel6 = doc.createElement("_ApprQty");
                vLebel6.appendChild(doc.createTextNode(_encrptor.Encrypt(poleDetail.get(x).get_ApprQty(),randomNo)));
                pdElement.appendChild(vLebel6);


                Element vLebel7 = doc.createElement("_UserId");
                vLebel7.appendChild(doc.createTextNode(_encrptor.Encrypt(poleDetail.get(x).get_UserId(),randomNo)));
                pdElement.appendChild(vLebel7);

                Element vLebel8 = doc.createElement("_distributorcode");
                vLebel8.appendChild(doc.createTextNode(_encrptor.Encrypt(poleDetail.get(x).get_distributorcode(),randomNo)));
                pdElement.appendChild(vLebel8);

                Element vLebel9 = doc.createElement("_Qtytobeapproved");
                vLebel9.appendChild(doc.createTextNode(_encrptor.Encrypt(poleDetail.get(x).get_QtyToBe_Approved(),randomNo)));
                pdElement.appendChild(vLebel9);

                Element vLebel10 = doc.createElement("_approvedqty");
                vLebel10.appendChild(doc.createTextNode(_encrptor.Encrypt(poleDetail.get(x).get_approvedqty(),randomNo)));
                pdElement.appendChild(vLebel10);

                Element vLebel11 = doc.createElement("_RequstedQty");
                vLebel11.appendChild(doc.createTextNode(_encrptor.Encrypt(poleDetail.get(x).get_RequstedQty(),randomNo)));
                pdElement.appendChild(vLebel11);


                Element vLebel12 = doc.createElement("skey");
                vLebel12.appendChild(doc.createTextNode(_encrptor.Encrypt(randomNo,CommonPref.CIPER_KEY)));
                skey=_encrptor.Encrypt(randomNo,CommonPref.CIPER_KEY);
                pdElement.appendChild(vLebel12);

//                Element vLebel12 = doc.createElement("Cap");
//                vLebel12.appendChild(doc.createTextNode(capId));
//                pdElement.appendChild(vLebel12);
//
//                Element vLebel8 = doc.createElement("Token");
//                vLebel8.appendChild(doc.createTextNode(token));
//                pdElement.appendChild(vLebel8);

                pdlsElement.appendChild(pdElement);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        poleElement.appendChild(pdlsElement);
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = null;
        String res = "0";
        try {

            try
            {
                trans = transfac.newTransformer();
            }
            catch (TransformerConfigurationException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "0";
            }

            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            // create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            BasicHttpResponse httpResponse = null;
            try
            {
                trans.transform(source, result);
            }
            catch (TransformerException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "0";
            }
            String SOAPRequestXML = sw.toString();
            String startTag = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                    + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchem\" "
                    + "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"   >  "
                    + "<soap:Body > ";
            String endTag = "</soap:Body > " + "</soap:Envelope>";
//			HttpPost httppost = new HttpPost("http://mobapp.bih.nic.in/locationcapturewebservice.asmx");

            HttpPost httppost = new HttpPost(SERVICEURL);
            // Log.i("Request: ", "XML Request= " + startTag + SOAPRequestXML
            // + endTag);
            StringEntity sEntity = new StringEntity(startTag + SOAPRequestXML+ endTag, HTTP.UTF_8);

            sEntity.setContentType("text/xml");
            //httppost.setHeader("Content-Type","application/soap+xml;charset=UTF-8");
            //httppost.addHeader("AuthenticationToken",token);
            httppost.setEntity(sEntity);


            HttpClient httpclient = new DefaultHttpClient();

            httpResponse = (BasicHttpResponse) httpclient.execute(httppost);
            HttpEntity entity = httpResponse.getEntity();
            Log.i("Responddddddddse: ", httpResponse.getStatusLine().toString());

            if (httpResponse.getStatusLine().getStatusCode() == 200
                    || httpResponse.getStatusLine().getReasonPhrase()
                    .toString().equals("OK")) {

                String output = _getResponseBody(entity);
                Log.d("egffgfdg",output);

                res = parseRespnse(output);
                Log.d("dfggdg",res);


              ////  String result1 = output.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><DDCApprovalFormResponse xmlns=\"http://164.100.251.52/\"><DDCApprovalFormResult>", "");
             //   result1 = result1.replace("</DDCApprovalFormResult></DDCApprovalFormResponse></soap:Body></soap:Envelope>","");

                Log.e("Result", res);

               // res = "1";
             //   res = "1";
            } else {
                res = "0";
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "0";
        }


        // response.put("HTTPStatus",httpResponse.getStatusLine().toString());

        return res;

    }
    public static String _getResponseBody(final HttpEntity entity) throws IOException, ParseException {

        if (entity == null) { throw new IllegalArgumentException("HTTP entity may not be null"); }

        InputStream instream = entity.getContent();

        if (instream == null) { return ""; }

        if (entity.getContentLength() > Integer.MAX_VALUE) { throw new IllegalArgumentException(

                "HTTP entity too large to be buffered in memory"); }

        String charset = getContentCharSet(entity);

        if (charset == null) {

            charset = org.apache.http.protocol.HTTP.DEFAULT_CONTENT_CHARSET;

        }

        Reader reader = new InputStreamReader(instream, charset);

        StringBuilder buffer = new StringBuilder();

        try {

            char[] tmp = new char[1024];

            int l;

            while ((l = reader.read(tmp)) != -1) {

                buffer.append(tmp, 0, l);

            }

        } finally {

            reader.close();

        }

        return buffer.toString();

    }

    public static String parseRespnse(String xml)
    {
        String result = "Failed to parse";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        InputSource is;
        try
        {
            builder = factory.newDocumentBuilder();
            is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);
            NodeList list = doc.getElementsByTagName("DDCApprovalFormResult");
            result = list.item(0).getTextContent();
            //System.out.println(list.item(0).getTextContent());
        }
        catch (ParserConfigurationException e)
        {

        }
        catch (SAXException e)
        {

        }
        catch (IOException e)
        {

        }
        return result;
    }


}
