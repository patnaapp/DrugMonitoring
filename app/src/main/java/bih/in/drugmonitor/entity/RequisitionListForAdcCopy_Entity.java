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

public class RequisitionListForAdcCopy_Entity implements KvmSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	public static Class<RequisitionListForAdcCopy_Entity> REQCopy_CLASS = RequisitionListForAdcCopy_Entity.class;

	private  String Req_Id = "";
	private String Req_date = "";
	private String Hosp_Name= "";
	private String Hosp_Id = "";
	private String Drug_Name;
	private String Drug_Id;
	private String Req_qty;
	private String Already_approved_qty;
	private String Pending_approval_qty;
	// Encriptor _encrptor;
	private String _Skey="";
	private String CapId="";
	Context context;

	public RequisitionListForAdcCopy_Entity() {
		super();
	}

	public RequisitionListForAdcCopy_Entity(Context context1, SoapObject obj) {
		this.context=context1;
	//	_encrptor=new Encriptor();
		try
		{
//			Log.d("commonpref",CommonPref.CIPER_KEY);
//			this._Skey=_encrptor.Decrypt(obj.getProperty("skey").toString(), CommonPref.CIPER_KEY);
//			//this._Skey=_encrptor.Decrypt("T/0e2rl0kHIvBtEas5Dv4g==", CommonPref.CIPER_KEY);
//			Log.d("skey",this.get_Skey());
//			 this.CapId=_encrptor.Decrypt(obj.getProperty("Cap").toString(),_Skey);
//			//this.CapId=_encrptor.Decrypt("wZWV8HB10WGccFXPUJIyRw==",_Skey);
//
//			if(CapId.equalsIgnoreCase(PreferenceManager.getDefaultSharedPreferences(context).getString("CAPID", ""))) {
//				this.Req_Id = _encrptor.Decrypt(obj.getProperty("ReqId").toString(), _Skey);
//				this.Req_date = _encrptor.Decrypt(obj.getProperty("RequestDate").toString(), _Skey);
//				this.Hosp_Name = _encrptor.Decrypt(obj.getProperty("HospitalName").toString(), _Skey);
//				this.Drug_Name = _encrptor.Decrypt(obj.getProperty("DrugName").toString(), _Skey);
//				this.Req_qty = _encrptor.Decrypt(obj.getProperty("ReqQty").toString(), _Skey);
//				this.Already_approved_qty = _encrptor.Decrypt(obj.getProperty("AlreadyApprovedQty").toString(), _Skey);
//				this.Pending_approval_qty = _encrptor.Decrypt(obj.getProperty("PendingForApproval").toString(), _Skey);
//			}
//			else {
//				PreferenceManager.getDefaultSharedPreferences(context).edit().putString("UserId","").commit();
//				PreferenceManager.getDefaultSharedPreferences(context).edit().putString("password","").commit();
//				PreferenceManager.getDefaultSharedPreferences(context).edit().putString("Role","").commit();
//				PreferenceManager.getDefaultSharedPreferences(context).edit().putString("District","").commit();
//
//				Intent intent=new Intent(context, LoginActivity.class);
//				context.startActivity(intent);
//				((Activity) context).finish();
//			}



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

	public String getReq_Id() {
		return Req_Id;
	}

	public void setReq_Id(String req_Id) {
		Req_Id = req_Id;
	}

	public String getReq_date() {
		return Req_date;
	}

	public void setReq_date(String req_date) {
		Req_date = req_date;
	}

	public String getHosp_Name() {
		return Hosp_Name;
	}

	public void setHosp_Name(String hosp_Name) {
		Hosp_Name = hosp_Name;
	}

	public String getHosp_Id() {
		return Hosp_Id;
	}

	public void setHosp_Id(String hosp_Id) {
		Hosp_Id = hosp_Id;
	}

	public String getDrug_Name() {
		return Drug_Name;
	}

	public void setDrug_Name(String drug_Name) {
		Drug_Name = drug_Name;
	}

	public String getDrug_Id() {
		return Drug_Id;
	}

	public void setDrug_Id(String drug_Id) {
		Drug_Id = drug_Id;
	}

	public String getReq_qty() {
		return Req_qty;
	}

	public void setReq_qty(String req_qty) {
		Req_qty = req_qty;
	}

	public String getAlready_approved_qty() {
		return Already_approved_qty;
	}

	public void setAlready_approved_qty(String already_approved_qty) {
		Already_approved_qty = already_approved_qty;
	}

	public String getPending_approval_qty() {
		return Pending_approval_qty;
	}

	public void setPending_approval_qty(String pending_approval_qty) {
		Pending_approval_qty = pending_approval_qty;
	}

//	public Encriptor get_encrptor() {
//		return _encrptor;
//	}
//
//	public void set_encrptor(Encriptor _encrptor) {
//		this._encrptor = _encrptor;
//	}

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
