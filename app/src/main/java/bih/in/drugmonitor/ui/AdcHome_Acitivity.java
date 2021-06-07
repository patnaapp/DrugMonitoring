package bih.in.drugmonitor.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bih.in.drugmonitor.R;
import bih.in.drugmonitor.security.Encriptor;
import bih.in.drugmonitor.utility.CommonPref;

public class AdcHome_Acitivity extends AppCompatActivity {

    TextView tv_username,tv_role,tv_desig,tv_mob;
    String username="",userrole="",designation="",mobile="";
    String decryptcode="";
    Encriptor _encrptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adc_home__acitivity);

        _encrptor=new Encriptor();

        try {
            decryptcode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("skey", "");
            username= _encrptor.Decrypt(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", ""), CommonPref.CIPER_KEY);
            // userrole= _encrptor.Decrypt(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", ""),decryptcode);
            designation= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("desig", "");
            // designation=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("desig", "");
            mobile=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("mob", "");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        initialise();

        tv_username.setText(username);
        tv_role.setText("ADC");
        tv_desig.setText(designation);
        tv_mob.setText(mobile);
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
        Toast.makeText(getApplicationContext(),"coming soon",Toast.LENGTH_SHORT).show();

    }

    public void OnClickLogout(View view)
    {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserId","").commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("password","").commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("TOKENNO","").commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Dist_Code","").commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("state_Code","").commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("mob","").commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("desig","").commit();
        Intent intent=new Intent(AdcHome_Acitivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}