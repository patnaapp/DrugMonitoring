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

public class DrugIssuedDetailsList_Entity implements KvmSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	public static Class<DrugIssuedDetailsList_Entity> DRUG_ISSUED_CLASS = DrugIssuedDetailsList_Entity.class;

	private String _IssuedDate = "";
	private String _DistributorName = "";
	private String _HospitalName= "";
	private String _DrugName = "";
	private String _BatchNo;
	private String _IssuedQty;

	Encriptor _encrptor;
	private String _Skey="";
	private String CapId="";
	Context context;

	public DrugIssuedDetailsList_Entity() {
		super();
	}

	public DrugIssuedDetailsList_Entity(Context context1, SoapObject obj) {
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
				this._IssuedDate = _encrptor.Decrypt(obj.getProperty("IssuedDate").toString(), _Skey);
				this._DistributorName = _encrptor.Decrypt(obj.getProperty("DistributorName").toString(), _Skey);
				//this._HospitalName = _encrptor.Decrypt(obj.getProperty("HospitalName").toString(), _Skey);
				this._DrugName = _encrptor.Decrypt(obj.getProperty("DrugName").toString(), _Skey);
				this._BatchNo = _encrptor.Decrypt(obj.getProperty("BatchNo").toString(), _Skey);
				this._IssuedQty = _encrptor.Decrypt(obj.getProperty("IssuedQty").toString(), _Skey);

			}
			else {
				PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("UserId","").commit();
				PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("password","").commit();
				PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("TOKENNO","").commit();
				PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("Dist_Code","").commit();
				PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("state_Code","").commit();
				PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("mob","").commit();
				PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("desig","").commit();

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

	public String get_IssuedDate() {
		return _IssuedDate;
	}

	public void set_IssuedDate(String _IssuedDate) {
		this._IssuedDate = _IssuedDate;
	}

	public String get_DistributorName() {
		return _DistributorName;
	}

	public void set_DistributorName(String _DistributorName) {
		this._DistributorName = _DistributorName;
	}

	public String get_HospitalName() {
		return _HospitalName;
	}

	public void set_HospitalName(String _HospitalName) {
		this._HospitalName = _HospitalName;
	}

	public String get_DrugName() {
		return _DrugName;
	}

	public void set_DrugName(String _DrugName) {
		this._DrugName = _DrugName;
	}

	public String get_BatchNo() {
		return _BatchNo;
	}

	public void set_BatchNo(String _BatchNo) {
		this._BatchNo = _BatchNo;
	}

	public String get_IssuedQty() {
		return _IssuedQty;
	}

	public void set_IssuedQty(String _IssuedQty) {
		this._IssuedQty = _IssuedQty;
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
