package bih.in.drugmonitor.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import bih.in.drugmonitor.R;

public class AddDrugRequisition_Activity extends AppCompatActivity
{
    TextView tv_centrename,tv_icmr_id,tv_patient_id,tv_patientName,tv_patientage,tv_adm_date,tv_address,tv_request_date,tv_attendant_name;
    TextView tv_attndnt_cntct,tv_attndnt_id,tv_critical_status,tv_drugname,tv_max_qty;
    Spinner sp_qty_to_request;
    Button btn_add_requesition;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug_requisition_);

        initialise();
    }

    public void initialise()
    {
        tv_centrename=findViewById(R.id.tv_centrename);
        tv_icmr_id=findViewById(R.id.tv_icmr_id);
        tv_patient_id=findViewById(R.id.tv_patient_id);
        tv_patientName=findViewById(R.id.tv_patientName);
        tv_patientage=findViewById(R.id.tv_patientage);
        tv_adm_date=findViewById(R.id.tv_adm_date);
        tv_address=findViewById(R.id.tv_address);
        tv_request_date=findViewById(R.id.tv_request_date);
        tv_attendant_name=findViewById(R.id.tv_attendant_name);
        tv_attndnt_cntct=findViewById(R.id.tv_attndnt_cntct);
        tv_attndnt_id=findViewById(R.id.tv_attndnt_id);
        tv_critical_status=findViewById(R.id.tv_critical_status);
        tv_drugname=findViewById(R.id.tv_drugname);
        tv_max_qty=findViewById(R.id.tv_max_qty);
        sp_qty_to_request=findViewById(R.id.sp_qty_to_request);
        btn_add_requesition=findViewById(R.id.btn_add_requesition);
    }
}