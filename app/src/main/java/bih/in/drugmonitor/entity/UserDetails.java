package bih.in.drugmonitor.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;

import bih.in.drugmonitor.security.Encriptor;
import bih.in.drugmonitor.utility.CommonPref;

public class UserDetails implements KvmSerializable, Serializable {
    public static Class<UserDetails> USER_CLASS = UserDetails.class;

    private boolean isAuthenticated = true;
    private String IsAuth="";
    private String Password = "";


    private String LastVisitedOn = "";
    private String UserType = "";
    private String userid = "";
    private String designation = "";
    private String mobileno = "";
    private String statecode = "";
    private String distcode = "";
    private String changepassword = "";
    private String login_attempt = "";
    private String isactive = "";


    private String _TOKEN = "";

    Encriptor _encrptor;
    private String skey="";
    private String CapId="";

    public UserDetails() {
        _encrptor=new Encriptor();
    }

//
//    public UserDetails() {
//    }

    @SuppressWarnings("deprecation")
    public UserDetails(SoapObject obj) {

        _encrptor = new Encriptor();
        try {

            this.skey = _encrptor.Decrypt(obj.getProperty("skey").toString(), CommonPref.CIPER_KEY);
            this.IsAuth = _encrptor.Decrypt(obj.getProperty("isAuthenticated").toString(), skey);

            if(obj.getProperty("Cap").toString()!=null)
            {
                this.CapId = _encrptor.Decrypt(obj.getProperty("Cap").toString(), skey);
            }
            //  this.setAuthenticated(Boolean.parseBoolean(obj.getProperty("IS_authenticate").toString()));
         //   this.setUserid(obj.getProperty("ID").toString());
         //   this.setPassword(obj.getProperty("password").toString());

         //   this.setLastVisitedOn(obj.getProperty("Name").toString());
            this.setUserType(_encrptor.Decrypt(obj.getProperty("UserType").toString(), skey));
            this.setDesignation(_encrptor.Decrypt(obj.getProperty("designation").toString(), skey));
            this.setMobileno(_encrptor.Decrypt(obj.getProperty("mobileno").toString(), skey));
            this.setStatecode(_encrptor.Decrypt(obj.getProperty("statecode").toString(), skey));
            this.setDistcode(_encrptor.Decrypt(obj.getProperty("distcode").toString(), skey));
            this.setChangepassword(_encrptor.Decrypt(obj.getProperty("changepassword").toString(), skey));
            this.setLogin_attempt(_encrptor.Decrypt(obj.getProperty("login_attempt").toString(), skey));
            this.setIsactive(_encrptor.Decrypt(obj.getProperty("isactive").toString(), skey));
            this.set_TOKEN(_encrptor.Decrypt(obj.getProperty("Token").toString(), skey));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<UserDetails> getUserClass()
    {
        return USER_CLASS;
    }

    public static void setUserClass(Class<UserDetails> userClass)
    {
        USER_CLASS = userClass;
    }


    @Override
    public Object getProperty(int i)
    {
        return null;
    }

    @Override
    public int getPropertyCount()
    {
        // TODO Auto-generated method stub
        return 8;
    }

    @Override
    public void setProperty(int i, Object o)
    {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo)
    {

    }


    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getIsAuth() {
        return IsAuth;
    }

    public void setIsAuth(String isAuth) {
        IsAuth = isAuth;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLastVisitedOn() {
        return LastVisitedOn;
    }

    public void setLastVisitedOn(String lastVisitedOn) {
        LastVisitedOn = lastVisitedOn;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getDistcode() {
        return distcode;
    }

    public void setDistcode(String distcode) {
        this.distcode = distcode;
    }

    public String getChangepassword() {
        return changepassword;
    }

    public void setChangepassword(String changepassword) {
        this.changepassword = changepassword;
    }

    public String getLogin_attempt() {
        return login_attempt;
    }

    public void setLogin_attempt(String login_attempt) {
        this.login_attempt = login_attempt;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String get_TOKEN() {
        return _TOKEN;
    }

    public void set_TOKEN(String _TOKEN) {
        this._TOKEN = _TOKEN;
    }

    public Encriptor get_encrptor() {
        return _encrptor;
    }

    public void set_encrptor(Encriptor _encrptor) {
        this._encrptor = _encrptor;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getCapId() {
        return CapId;
    }

    public void setCapId(String capId) {
        CapId = capId;
    }
}

