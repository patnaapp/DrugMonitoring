package bih.in.drugmonitor.entity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

import bih.in.drugmonitor.security.Encriptor;
import bih.in.drugmonitor.ui.LoginActivity;
import bih.in.drugmonitor.utility.CommonPref;

public class PatientDetailsList_Entity implements KvmSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	public static Class<PatientDetailsList_Entity> PATIENT_CLASS = PatientDetailsList_Entity.class;

	private String rowid = "";
	private String pdrid = "";
	private String patientname= "";
	private String age = "";
	private String gender;
	private String RequstedQty;
	private String AllreadyApproved;
	private String issuedqty;
	private String PenddingQty;
	private String recdqty;

	Encriptor _encrptor;
	private String _Skey="";
	private String CapId="";
	Context context;

	public PatientDetailsList_Entity() {
		super();
	}

	public PatientDetailsList_Entity(Context context1, SoapObject obj) {
		this.context=context1;
		_encrptor=new Encriptor();
		try
		{
			Log.d("commonpref",CommonPref.CIPER_KEY);
			this._Skey=_encrptor.Decrypt(obj.getProperty("skey").toString(), CommonPref.CIPER_KEY);
			//this._Skey=_encrptor.Decrypt("T/0e2rl0kHIvBtEas5Dv4g==", CommonPref.CIPER_KEY);
			Log.d("skey",this.get_Skey());
			 this.CapId=_encrptor.Decrypt(obj.getProperty("Cap").toString(),_Skey);
			//this.CapId=_encrptor.Decrypt("wZWV8HB10WGccFXPUJIyRw==",_Skey);

			if(CapId.equalsIgnoreCase(PreferenceManager.getDefaultSharedPreferences(context).getString("CAPID", ""))) {
			//	this.rowid = _encrptor.Decrypt(obj.getProperty("rowid").toString(), _Skey);
				this.pdrid = _encrptor.Decrypt(obj.getProperty("pdrid").toString(), _Skey);
				this.patientname = _encrptor.Decrypt(obj.getProperty("patientname").toString(), _Skey);
				this.age = _encrptor.Decrypt(obj.getProperty("age").toString(), _Skey);
				this.gender = _encrptor.Decrypt(obj.getProperty("gender").toString(), _Skey);
				this.RequstedQty = _encrptor.Decrypt(obj.getProperty("RequstedQty").toString(), _Skey);
				this.AllreadyApproved = _encrptor.Decrypt(obj.getProperty("AllreadyApproved").toString(), _Skey);
				this.issuedqty = _encrptor.Decrypt(obj.getProperty("issuedqty").toString(), _Skey);
				this.PenddingQty = _encrptor.Decrypt(obj.getProperty("PenddingQty").toString(), _Skey);
				this.recdqty = _encrptor.Decrypt(obj.getProperty("recdqty").toString(), _Skey);

			}
			else {
				PreferenceManager.getDefaultSharedPreferences(context).edit().putString("UserId","").commit();
				PreferenceManager.getDefaultSharedPreferences(context).edit().putString("password","").commit();
				PreferenceManager.getDefaultSharedPreferences(context).edit().putString("Role","").commit();
				PreferenceManager.getDefaultSharedPreferences(context).edit().putString("District","").commit();

				Intent intent=new Intent(context, LoginActivity.class);
				context.startActivity(intent);
				((Activity) context).finish();
			}



		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getPdrid() {
		return pdrid;
	}

	public void setPdrid(String pdrid) {
		this.pdrid = pdrid;
	}

	public String getPatientname() {
		return patientname;
	}

	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRequstedQty() {
		return RequstedQty;
	}

	public void setRequstedQty(String requstedQty) {
		RequstedQty = requstedQty;
	}

	public String getAllreadyApproved() {
		return AllreadyApproved;
	}

	public void setAllreadyApproved(String allreadyApproved) {
		AllreadyApproved = allreadyApproved;
	}

	public String getIssuedqty() {
		return issuedqty;
	}

	public void setIssuedqty(String issuedqty) {
		this.issuedqty = issuedqty;
	}

	public String getPenddingQty() {
		return PenddingQty;
	}

	public void setPenddingQty(String penddingQty) {
		PenddingQty = penddingQty;
	}

	public String getRecdqty() {
		return recdqty;
	}

	public void setRecdqty(String recdqty) {
		this.recdqty = recdqty;
	}

	public Encriptor get_encrptor() {
		return _encrptor;
	}

	public void set_encrptor(Encriptor _encrptor) {
		this._encrptor = _encrptor;
	}

	public String get_Skey() {
		return _Skey;
	}

	public void set_Skey(String _Skey) {
		this._Skey = _Skey;
	}

	public String getCapId() {
		return CapId;
	}

	public void setCapId(String capId) {
		CapId = capId;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
