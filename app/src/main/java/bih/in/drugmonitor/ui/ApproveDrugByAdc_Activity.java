package bih.in.drugmonitor.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import bih.in.drugmonitor.R;

public class ApproveDrugByAdc_Activity extends AppCompatActivity {

    TextView tv_hosp_name,tv_hosp_address,tv_nodal_off_name,tv_cntct,tv_govt_pvt,tv_beds,tv_requst_date,tv_drugname,tv_req_qty,tv_already_approve,tv_pending_approve_qty;
    EditText edt_qty_tobe_approved;
    RecyclerView rv_distributors,rv_issued_medicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_drug_by_adc_);

        initialise();
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
}