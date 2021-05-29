package bih.in.drugmonitor.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import bih.in.drugmonitor.R;

public class AdcHome_Acitivity extends AppCompatActivity {

    TextView tv_username,tv_role,tv_desig,tv_mob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adc_home__acitivity);

        initialise();
    }

    public void initialise()

    {
        tv_username=findViewById(R.id.tv_username);
        tv_role=findViewById(R.id.tv_role);
        tv_desig=findViewById(R.id.tv_desig);
        tv_mob=findViewById(R.id.tv_mob);
    }

    public void on_ApproveRequisition(View view)
    {
        Intent i=new Intent(AdcHome_Acitivity.this,RequisitiionLListPendingForAdc_Activity.class);
        startActivity(i);
    }


    public void on_DrugApprovalSummary(View view)
    {

    }
}