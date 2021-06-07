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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.drugmonitor.R;
import bih.in.drugmonitor.RandomString;
import bih.in.drugmonitor.adapter.RequisitionListForAdcApproval_Adaptor;
import bih.in.drugmonitor.entity.RequisitionListForAdc_Entity;
import bih.in.drugmonitor.security.Encriptor;
import bih.in.drugmonitor.utility.Utiilties;
import bih.in.drugmonitor.web_services.WebServiceHelper;

public class RequisitiionLListPendingForAdc_Activity extends AppCompatActivity
{
    RecyclerView listviewshow;
    TextView tv_Norecord;
    ArrayList<RequisitionListForAdc_Entity> data;

    RequisitionListForAdcApproval_Adaptor requisitionAdaptor;

    Encriptor _encrptor;
    String CapId="";
    String distcode="",state_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisitiion_l_list_pending_for_adc_);

        initialise();

        _encrptor=new Encriptor();
        distcode=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Dist_Code", "");
        state_id=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("state_Code", "");


        new LoadRequisitionListForApproval().execute();
    }

    public void initialise()
    {
        listviewshow=findViewById(R.id.listviewshow);
        tv_Norecord=findViewById(R.id.tv_Norecord);
    }



    private class LoadRequisitionListForApproval extends AsyncTask<String, Void, ArrayList<RequisitionListForAdc_Entity>>
    {
        ArrayList<RequisitionListForAdc_Entity> blocklist = new ArrayList<>();
        private final ProgressDialog dialog = new ProgressDialog(RequisitiionLListPendingForAdc_Activity.this);

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Pending Requsitions...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<RequisitionListForAdc_Entity> doInBackground(String... param)
        {
            CapId= RandomString.randomAlphaNumeric(8);
            // CapId= "wZWV8HB10WGccFXPUJIyRw==";
            // CapId= "FNX4XDEG";
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("CAPID", CapId).commit();

            String _encptdist = Utiilties.cleanStringForVulnerability(distcode);
            String _capId = Utiilties.cleanStringForVulnerability(CapId);
            String _date = Utiilties.getCurrentDate();
            String _hosp_id = "";
            String _userttype = "2";
            String _drug_id = "1";

            String randomnum = Utiilties.getTimeStamp();
            //  String randomnum ="-1049096725";
            try {
                _encptdist = _encrptor.Encrypt(_encptdist, randomnum);
                _capId = _encrptor.Encrypt(_capId, randomnum);
                _date = _encrptor.Encrypt(_date, randomnum);
                _drug_id = _encrptor.Encrypt(_drug_id, randomnum);
                _hosp_id = _encrptor.Encrypt(_hosp_id, randomnum);
                _userttype = _encrptor.Encrypt(_userttype, randomnum);

            } catch (Exception e) {
                Log.e("EXCEPTION", "EXCEP while Encription on Login");
            }
            this.blocklist = WebServiceHelper.GetReqListForADcApproval(RequisitiionLListPendingForAdc_Activity.this,_date,_hosp_id,_drug_id,_encptdist,_userttype,randomnum,_capId);

            return this.blocklist;
        }

        @Override
        protected void onPostExecute(ArrayList<RequisitionListForAdc_Entity> result)
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
                    Toast.makeText(getApplicationContext(), "No Pending Requisition Found",Toast.LENGTH_LONG).show();

                }
            }

        }

    }

    public void populateData() {
        if (data != null && data.size() > 0) {
            Log.e("data", "" + data.size());

            tv_Norecord.setVisibility(View.GONE);
            listviewshow.setVisibility(View.VISIBLE);

            requisitionAdaptor = new RequisitionListForAdcApproval_Adaptor(this, data);
            listviewshow.setLayoutManager(new LinearLayoutManager(this));
            listviewshow.setAdapter(requisitionAdaptor);

        } else {
            listviewshow.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }
}