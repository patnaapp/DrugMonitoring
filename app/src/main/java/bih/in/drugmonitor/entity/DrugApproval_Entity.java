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

public class DrugApproval_Entity implements KvmSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	public static Class<DrugApproval_Entity> DRUGApproval_CLASS = DrugApproval_Entity.class;

	private String _DistCode = "";
	private String _statecode = "";
	private String _drugid = "";
	private String _HospitalID= "";
	private String _hreqid = "";
	private String _pdrid="";
	private String _UserId="";
	private String _distributorcode="";
	private String _approvedqty="";
	private String _RequstedQty="";
	private String _ApprQty="";
	private String _QtyToBe_Approved="";

	Encriptor _encrptor;
	private String _Skey="";
	private String CapId="";
	Context context;

	public DrugApproval_Entity() {
		super();
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
	public void setProperty(int arg0, Object arg1)
	{
		// TODO Auto-generated method stub

	}

	public String get_DistCode() {
		return _DistCode;
	}

	public void set_DistCode(String _DistCode) {
		this._DistCode = _DistCode;
	}

	public String get_statecode() {
		return _statecode;
	}

	public void set_statecode(String _statecode) {
		this._statecode = _statecode;
	}

	public String get_drugid() {
		return _drugid;
	}

	public void set_drugid(String _drugid) {
		this._drugid = _drugid;
	}

	public String get_HospitalID() {
		return _HospitalID;
	}

	public void set_HospitalID(String _HospitalID) {
		this._HospitalID = _HospitalID;
	}

	public String get_hreqid() {
		return _hreqid;
	}

	public void set_hreqid(String _hreqid) {
		this._hreqid = _hreqid;
	}

	public String get_pdrid() {
		return _pdrid;
	}

	public void set_pdrid(String _pdrid) {
		this._pdrid = _pdrid;
	}

	public String get_UserId() {
		return _UserId;
	}

	public void set_UserId(String _UserId) {
		this._UserId = _UserId;
	}

	public String get_distributorcode() {
		return _distributorcode;
	}

	public void set_distributorcode(String _distributorcode) {
		this._distributorcode = _distributorcode;
	}

	public String get_approvedqty() {
		return _approvedqty;
	}

	public void set_approvedqty(String _approvedqty) {
		this._approvedqty = _approvedqty;
	}

	public String get_RequstedQty() {
		return _RequstedQty;
	}

	public void set_RequstedQty(String _RequstedQty) {
		this._RequstedQty = _RequstedQty;
	}

	public String get_ApprQty() {
		return _ApprQty;
	}

	public void set_ApprQty(String _ApprQty) {
		this._ApprQty = _ApprQty;
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

	public String get_QtyToBe_Approved() {
		return _QtyToBe_Approved;
	}

	public void set_QtyToBe_Approved(String _QtyToBe_Approved) {
		this._QtyToBe_Approved = _QtyToBe_Approved;
	}
}
