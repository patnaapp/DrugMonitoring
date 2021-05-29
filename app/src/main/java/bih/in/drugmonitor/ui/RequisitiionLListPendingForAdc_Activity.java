package bih.in.drugmonitor.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import bih.in.drugmonitor.R;
import bih.in.drugmonitor.adapter.RequisitionListForAdcApproval_Adaptor;

public class RequisitiionLListPendingForAdc_Activity extends AppCompatActivity
{
    RecyclerView listviewshow;
    TextView tv_Norecord;
    RequisitionListForAdcApproval_Adaptor requisitionAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisitiion_l_list_pending_for_adc_);

        initialise();
    }

    public void initialise()
    {
        listviewshow=findViewById(R.id.listviewshow);
        tv_Norecord=findViewById(R.id.tv_Norecord);
    }
}