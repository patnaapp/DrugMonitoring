package bih.in.drugmonitor.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.drugmonitor.R;
import bih.in.drugmonitor.adapter.DistributorListForDrugIssue_adaptor;
import bih.in.drugmonitor.adapter.IssuedDrugsListByDistributor_adaptor;
import bih.in.drugmonitor.adapter.MyInterface;
import bih.in.drugmonitor.adapter.RequisitionListForAdcApproval_Adaptor;
import bih.in.drugmonitor.entity.DistributorsListForAdc_Entity;
import bih.in.drugmonitor.entity.DrugIssuedDetailsList_Entity;
import bih.in.drugmonitor.entity.RequisitionListForAdc_Entity;
import bih.in.drugmonitor.security.Encriptor;
import bih.in.drugmonitor.utility.Utiilties;
import bih.in.drugmonitor.web_services.WebServiceHelper;

public class ApproveDrugByAdc_Activity extends AppCompatActivity implements MyInterface {

    TextView tv_hosp_name,tv_hosp_address,tv_nodal_off_name,tv_cntct,tv_govt_pvt,tv_beds,tv_requst_date,tv_drugname,tv_req_qty,tv_already_approve,tv_pending_approve_qty;
    EditText edt_qty_tobe_approved;
    RecyclerView rv_distributors,rv_issued_medicines;
    RequisitionListForAdc_Entity entity;
    String _req_id="",req_date="",hosp_name="",_hosp_id="",drug_name="",_req_qty="",_already_approved="",pendinng_approvfed="";
    ArrayList<DistributorsListForAdc_Entity> data;
    ArrayList<DrugIssuedDetailsList_Entity> data1;

    DistributorListForDrugIssue_adaptor distributorAdaptor;
    IssuedDrugsListByDistributor_adaptor drugIssuedAdaptor;

    Encriptor _encrptor;
    String CapId="";
    String distcode="208";
    String _distributor_id="";
    Boolean isDistributor_selected=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_drug_by_adc_);

        _encrptor=new Encriptor();
        _req_id=getIntent().getExtras().getString("reqid");
        req_date=getIntent().getExtras().getString("req_date");
        hosp_name=getIntent().getExtras().getString("hospname");
        _hosp_id=getIntent().getExtras().getString("hosp_id");
        drug_name=getIntent().getExtras().getString("drugname");
        _req_qty=getIntent().getExtras().getString("reqqty");
        _already_approved=getIntent().getExtras().getString("approve_qty");
        pendinng_approvfed=getIntent().getExtras().getString("pending_qty");

        initialise();
        new LoadDistributorMappedWithAdc().execute();
        new LoadIssuedDrugList().execute();
        //  entity=(RequisitionListForAdc_Entity)getIntent().getSerializableExtra("req_data");

        setdata();
    }

    public void on_Approve(View view)
    {
        if (isDistributor_selected && edt_qty_tobe_approved.getText().length()>0)
        {
            if(Integer.parseInt(edt_qty_tobe_approved.getText().toString())<=Integer.parseInt(tv_pending_approve_qty.getText().toString()))
            {
                Toast.makeText(getApplicationContext(),"Distributor Name-"+_distributor_id,Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Quantity to be approved should be less than or equals to pending quantity to be approved",Toast.LENGTH_SHORT).show();

            }

        }
        else {
            Toast.makeText(getApplicationContext(),"select distributor",Toast.LENGTH_SHORT).show();
        }
    }

    public void initialise()
    {
        tv_hosp_name=findViewById(R.id.tv_hosp_name);
        tv_hosp_address=findViewById(R.id.tv_hosp_address);
        tv_nodal_off_name=findViewById(R.id.tv_nodal_off_name);
        tv_cntct=findViewById(R.id.tv_cntct);
        tv_govt_pvt=findViewById(R.id.tv_govt_pvt);
        tv_beds=findViewById(R.id.tv_beds);
        tv_requst_date=findViewById(R.id.tv_requst_date);
        tv_drugname=findViewById(R.id.tv_drugname);
        tv_req_qty=findViewById(R.id.tv_req_qty);
        tv_already_approve=findViewById(R.id.tv_already_approve);
        tv_pending_approve_qty=findViewById(R.id.tv_pending_approve_qty);
        edt_qty_tobe_approved=findViewById(R.id.edt_qty_tobe_approved);
        rv_distributors=findViewById(R.id.rv_distributors);
        rv_issued_medicines=findViewById(R.id.rv_issued_medicines);
    }

    public void setdata()
    {
        tv_hosp_name.setText(hosp_name);
        tv_hosp_address.setText("");
        tv_nodal_off_name.setText("");
        tv_cntct.setText("");
        tv_govt_pvt.setText("");
        tv_beds.setText("");
        tv_requst_date.setText(req_date);
        tv_drugname.setText(drug_name);
        tv_req_qty.setText(_req_qty);
        tv_already_approve.setText(_already_approved);
        tv_pending_approve_qty.setText(pendinng_approvfed);
    }

    @Override
    public void onCheckedDistributor(int position,String distributorid, Boolean isChecked) {
        isDistributor_selected=isChecked;
        _distributor_id=distributorid;

    }

    private class LoadDistributorMappedWithAdc extends AsyncTask<String, Void, ArrayList<DistributorsListForAdc_Entity>>
    {
        ArrayList<DistributorsListForAdc_Entity> blocklist = new ArrayList<>();
        private final ProgressDialog dialog = new ProgressDialog(ApproveDrugByAdc_Activity.this);

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Distributors List...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<DistributorsListForAdc_Entity> doInBackground(String... param)
        {
            // CapId= RandomString.randomAlphaNumeric(8);
            // CapId= "wZWV8HB10WGccFXPUJIyRw==";
            CapId= "FNX4XDEG";
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("CAPID", CapId).commit();

            String _encptdist = Utiilties.cleanStringForVulnerability(distcode);
            String _capId = Utiilties.cleanStringForVulnerability(CapId);
//            String _date = Utiilties.getCurrentDate();
            String _hosp_id = "597";
//            String _userttype = "6";
            String _drug_id = "1";
            String _state_id = "5";

            //  String randomnum = Utiilties.getTimeStamp();
            String randomnum ="-1049096725";
            try {
                _encptdist = _encrptor.Encrypt(_encptdist, randomnum);
                _capId = _encrptor.Encrypt(_capId, randomnum);
                _drug_id = _encrptor.Encrypt(_drug_id, randomnum);
                //  _hosp_id = _encrptor.Encrypt(_hosp_id, randomnum);
                _state_id = _encrptor.Encrypt(_state_id, randomnum);

            } catch (Exception e) {
                Log.e("EXCEPTION", "EXCEP while Encription on Login");
            }
            this.blocklist = WebServiceHelper.GetDistributorsMappedWithAdc(ApproveDrugByAdc_Activity.this,_encptdist,_state_id,_drug_id,randomnum,_capId);

            return this.blocklist;
        }

        @Override
        protected void onPostExecute(ArrayList<DistributorsListForAdc_Entity> result)
        {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();

            }

            if (result != null)
            {
                if (result.size() > 0)
                {
                    data=result;

                    if (result.size() > 0)
                    {
                        populateData();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No Distributors Mapped",Toast.LENGTH_LONG).show();

                }
            }

        }

    }

    public void populateData()
    {
        if (data != null && data.size() > 0) {
            Log.e("data", "" + data.size());

            // tv_Norecord.setVisibility(View.GONE);
            rv_distributors.setVisibility(View.VISIBLE);

            distributorAdaptor = new DistributorListForDrugIssue_adaptor(this, data);
            rv_distributors.setLayoutManager(new LinearLayoutManager(this));
            rv_distributors.setAdapter(distributorAdaptor);

        } else {
            rv_distributors.setVisibility(View.GONE);
            // tv_Norecord.setVisibility(View.VISIBLE);
        }
    }


    //Issued Drug List


    private class LoadIssuedDrugList extends AsyncTask<String, Void, ArrayList<DrugIssuedDetailsList_Entity>>
    {
        ArrayList<DrugIssuedDetailsList_Entity> blocklist = new ArrayList<>();
        private final ProgressDialog dialog = new ProgressDialog(ApproveDrugByAdc_Activity.this);

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Issued Drugs Details...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<DrugIssuedDetailsList_Entity> doInBackground(String... param)
        {
            // CapId= RandomString.randomAlphaNumeric(8);
            // CapId= "wZWV8HB10WGccFXPUJIyRw==";
            CapId= "FNX4XDEG";
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("CAPID", CapId).commit();

            String _encptdist = Utiilties.cleanStringForVulnerability(distcode);
            String _capId = Utiilties.cleanStringForVulnerability(CapId);
//            String _date = Utiilties.getCurrentDate();
            String _hosp_id = "597";
//            String _userttype = "6";
            String _drug_id = "1";
            String _state_id = "5";

            //  String randomnum = Utiilties.getTimeStamp();
            String randomnum ="-1049096725";
            try {
                _capId = _encrptor.Encrypt(_capId, randomnum);
                _hosp_id = _encrptor.Encrypt(_hosp_id, randomnum);

            } catch (Exception e) {
                Log.e("EXCEPTION", "EXCEP while Encription on Login");
            }
            this.blocklist = WebServiceHelper.GetIssuedDrugDetails(ApproveDrugByAdc_Activity.this,_hosp_id,randomnum,_capId);

            return this.blocklist;
        }

        @Override
        protected void onPostExecute(ArrayList<DrugIssuedDetailsList_Entity> result)
        {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();

            }

            if (result != null)
            {
                if (result.size() > 0)
                {
                    data1=result;

                    if (result.size() > 0)
                    {
                        populateData1();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No Issued Drug Details",Toast.LENGTH_LONG).show();

                }
            }

        }

    }

    public void populateData1()
    {
        if (data1 != null && data1.size() > 0) {
            Log.e("data", "" + data1.size());

            // tv_Norecord.setVisibility(View.GONE);
            rv_issued_medicines.setVisibility(View.VISIBLE);

            drugIssuedAdaptor = new IssuedDrugsListByDistributor_adaptor(this, data1);
            rv_issued_medicines.setLayoutManager(new LinearLayoutManager(this));
            rv_issued_medicines.setAdapter(drugIssuedAdaptor);

        } else {
            rv_issued_medicines.setVisibility(View.GONE);
            // tv_Norecord.setVisibility(View.VISIBLE);
        }
    }
}