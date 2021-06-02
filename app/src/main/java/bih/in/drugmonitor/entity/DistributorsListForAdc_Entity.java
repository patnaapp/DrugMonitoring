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

public class DistributorsListForAdc_Entity implements KvmSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	public static Class<DistributorsListForAdc_Entity> DISTRIBUTOR_CLASS = DistributorsListForAdc_Entity.class;

	private String _DistrubutorName = "";
	private String _ContactPerson = "";
	private String _ContactNo= "";
	private String _PendingforIssue = "";
	private String _AvailableQtyinStock;

	Encriptor _encrptor;
	private String _Skey="";
	private String CapId="";
	Context context;

	public DistributorsListForAdc_Entity() {
		super();
	}

	public DistributorsListForAdc_Entity(Context context1, SoapObject obj) {
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
				this._DistrubutorName = _encrptor.Decrypt(obj.getProperty("DistrubutorName").toString(), _Skey);
				this._ContactPerson = _encrptor.Decrypt(obj.getProperty("ContactPerson").toString(), _Skey);
				this._ContactNo = _encrptor.Decrypt(obj.getProperty("ContactNo").toString(), _Skey);
				this._PendingforIssue = _encrptor.Decrypt(obj.getProperty("PendingforIssue").toString(), _Skey);
				this._AvailableQtyinStock = _encrptor.Decrypt(obj.getProperty("AvailableQtyinStock").toString(), _Skey);

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

	public String get_DistrubutorName() {
		return _DistrubutorName;
	}

	public void set_DistrubutorName(String _DistrubutorName) {
		this._DistrubutorName = _DistrubutorName;
	}

	public String get_ContactPerson() {
		return _ContactPerson;
	}

	public void set_ContactPerson(String _ContactPerson) {
		this._ContactPerson = _ContactPerson;
	}

	public String get_ContactNo() {
		return _ContactNo;
	}

	public void set_ContactNo(String _ContactNo) {
		this._ContactNo = _ContactNo;
	}

	public String get_PendingforIssue() {
		return _PendingforIssue;
	}

	public void set_PendingforIssue(String _PendingforIssue) {
		this._PendingforIssue = _PendingforIssue;
	}

	public String get_AvailableQtyinStock() {
		return _AvailableQtyinStock;
	}

	public void set_AvailableQtyinStock(String _AvailableQtyinStock) {
		this._AvailableQtyinStock = _AvailableQtyinStock;
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
