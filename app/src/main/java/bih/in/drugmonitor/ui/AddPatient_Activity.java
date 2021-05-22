package bih.in.drugmonitor.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import bih.in.drugmonitor.R;

public class AddPatient_Activity extends AppCompatActivity
{

    EditText edt_admission_date,edt_patient_name,edt_mobile,edt_icmr_id,edt_testDate,edt_srf_id,edt_attendant_name,edt_att_contact,edt_residnt_address;
    ImageView img_admdate,img_date;
    Spinner sp_hour,sp_minutes,sp_am_pm,sp_gender,sp_age,sp_month,sp_day,sp_whether_positive,sp_state,sp_dist,sp_criticality;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_);

        initialise();
    }

    public void initialise()
    {
        edt_admission_date=findViewById(R.id.edt_admission_date);
        edt_patient_name=findViewById(R.id.edt_patient_name);
        edt_mobile=findViewById(R.id.edt_mobile);
        edt_icmr_id=findViewById(R.id.edt_icmr_id);
        edt_testDate=findViewById(R.id.edt_testDate);
        edt_srf_id=findViewById(R.id.edt_srf_id);
        edt_attendant_name=findViewById(R.id.edt_attendant_name);
        edt_att_contact=findViewById(R.id.edt_att_contact);
        edt_residnt_address=findViewById(R.id.edt_residnt_address);

        img_admdate=findViewById(R.id.img_admdate);
        img_date=findViewById(R.id.img_date);

        sp_hour=findViewById(R.id.sp_hour);
        sp_minutes=findViewById(R.id.sp_minutes);
        sp_am_pm=findViewById(R.id.sp_am_pm);
        sp_gender=findViewById(R.id.sp_gender);
        sp_age=findViewById(R.id.sp_age);
        sp_age=findViewById(R.id.sp_age);
        sp_month=findViewById(R.id.sp_month);
        sp_day=findViewById(R.id.sp_day);
        sp_whether_positive=findViewById(R.id.sp_whether_positive);
        sp_state=findViewById(R.id.sp_state);
        sp_dist=findViewById(R.id.sp_dist);
        sp_criticality=findViewById(R.id.sp_criticality);
        btn_submit=findViewById(R.id.btn_submit);
    }
}