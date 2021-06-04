package bih.in.drugmonitor.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import bih.in.drugmonitor.R;

import bih.in.drugmonitor.RandomString;
import bih.in.drugmonitor.database.DataBaseHelper;
import bih.in.drugmonitor.entity.UserDetails;
import bih.in.drugmonitor.security.Encriptor;
import bih.in.drugmonitor.utility.CommonPref;
import bih.in.drugmonitor.utility.GlobalVariables;
import bih.in.drugmonitor.utility.Utiilties;
import bih.in.drugmonitor.web_services.WebServiceHelper;


public class LoginActivity extends Activity {

    ConnectivityManager cm;
    public static String UserPhoto;
    String version;
    TelephonyManager tm;
    private static String imei;
    //TODO setup Database
    //DatabaseHelper1 localDBHelper;
    Context context;
    String uid = "";
    String pass = "";
    EditText userName;
    EditText userPass;
    String[] param;
    DataBaseHelper localDBHelper;

    UserDetails userInfo;
    Encriptor _encrptor;
    String CapId="";
    private SharedPreferences prefs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefs = getSharedPreferences("file", Context.MODE_PRIVATE);
        _encrptor=new Encriptor();

        Button loginBtn = (Button) findViewById(R.id.btn_login);
        TextView signUpBtn = (TextView) findViewById(R.id.tv_signup);

        CapId= RandomString.randomAlphaNumeric(8);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(LoginActivity.this,AdcHome_Acitivity.class);
//                startActivity(i);
                userName = (EditText) findViewById(R.id.et_username);
                userPass = (EditText) findViewById(R.id.et_password);
                param = new String[2];
                param[0] = userName.getText().toString();
                param[1] = userPass.getText().toString();

                if (param[0].length() < 1){
                    Toast.makeText(LoginActivity.this, "Enter Valid User Id", Toast.LENGTH_SHORT).show();
                }else if (param[1].length() < 1){
                    Toast.makeText(LoginActivity.this, "Enter Valid Password", Toast.LENGTH_SHORT).show();
                }else{
                    new LoginTask(param[0], param[1]).execute(param);
                }

            }
        });

//        signUpBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent singUpInt = new Intent(LoginActivity.this, SignUpActivity.class);
//                LoginActivity.this.startActivity(singUpInt);
//            }
//        });

        try {
            version = getPackageManager().getPackageInfo(getPackageName(),
                    0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void getUserDetail(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        //userInfo = localDBHelper.getUserDetails(username.toLowerCase(), password);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getIMEI();

    }


    private class LoginTask extends AsyncTask<String, Void, UserDetails>
    {
        String username,password;

        LoginTask(String username, String password)
        {
            this.username = username;
            this.password = password;
        }

        private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();

        @Override
        protected void onPreExecute()
        {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage(getResources().getString(R.string.authenticating));
            this.dialog.show();
        }

        @Override
        protected UserDetails doInBackground(String... param)
        {
            if (!Utiilties.isOnline(LoginActivity.this))
            {
                UserDetails userDetails = new UserDetails();
                userDetails.setAuthenticated(true);
                return userDetails;
            }
            else
            {
                String _encptuid = Utiilties.cleanStringForVulnerability(username);
               // String _encptpwd = Utiilties.cleanStringForVulnerability(password);
                String _encptpwd =password;
                String _capId = Utiilties.cleanStringForVulnerability(CapId);
                // PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("CAPID", RandomString.randomAlphaNumeric(8)).commit();
                String randomnum = Utiilties.getTimeStamp();

                try
                {
                    _encptuid = _encrptor.Encrypt(_encptuid, randomnum);
                    _encptpwd = _encrptor.Encrypt(_encptpwd, randomnum);
                    _capId = _encrptor.Encrypt(_capId, randomnum);
                }
                catch (Exception e)
                {
                    Log.e("EXCEPTION", "EXCEP while Encription on Login");
                }

                return WebServiceHelper.Login(_encptuid, _encptpwd,_capId,randomnum);
            }

        }

        @Override
        protected void onPostExecute(final UserDetails result) {

            if (this.dialog.isShowing()) this.dialog.dismiss();

            if (result != null)
            {
                if (result.getIsAuth().equalsIgnoreCase("true"))
                {
                    if(result.getCapId().equalsIgnoreCase(CapId))
                    {

                        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
                        try
                        {
                            // dataBaseHelper.insertUserDetails(result, _encrptor.Encrypt(email.getText().toString().toLowerCase(), CommonPref.CIPER_KEY), _encrptor.Encrypt(password.getText().toString().toLowerCase(), CommonPref.CIPER_KEY));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        try
                        {
                            GlobalVariables.LoggedUser = result;
                            GlobalVariables.LoggedUser.setUserid(_encrptor.Encrypt(username, CommonPref.CIPER_KEY));

                            GlobalVariables.LoggedUser.setPassword(_encrptor.Encrypt(password, CommonPref.CIPER_KEY));
                            GlobalVariables.LoggedUser.set_TOKEN(result.get_TOKEN());
                           // PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Role", (_encrptor.Encrypt("CSC", CommonPref.CIPER_KEY))).commit();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserId", (_encrptor.Encrypt(username, CommonPref.CIPER_KEY))).commit();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("password", (_encrptor.Encrypt(password, CommonPref.CIPER_KEY))).commit();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("TOKENNO", result.get_TOKEN().toLowerCase()).commit();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Dist_Code", result.getDistcode().toLowerCase()).commit();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("state_Code", result.getStatecode().toLowerCase()).commit();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("mob", result.getMobileno().toLowerCase()).commit();

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                            Intent i = new Intent();
                            i.setComponent(new ComponentName("bih.in.drugmonitor", "bih.in.drugmonitor.ui.AdcHome_Acitivity"));
                            startActivity(i);



                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Invalid User Id and Password ", Toast.LENGTH_LONG).show();
                    }
                }
                else if (result.getIsAuth().equalsIgnoreCase("locked"))
                {
                    Toast.makeText(getApplicationContext(), "Your Account has been locked for 5 Minutes .Please Try Again Later", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid User Id and Password ", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    private long setLoginStatus(UserDetails details) {
        details.setPassword(pass);
        SharedPreferences.Editor editor = SplashActivity.prefs.edit();
        editor.putBoolean("username", true);
        editor.putBoolean("password", true);
        editor.putString("uid", uid.toLowerCase());
        editor.putString("pass", pass);
       // editor.putString("role", details.getUserrole());
        editor.commit();
        //PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_ID", uid).commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("uid", uid).commit();
        localDBHelper = new DataBaseHelper(getApplicationContext());
        //long c = localDBHelper.insertUserDetails(details);

        return 0;
    }

    public void start() {
        //getUserDetail();
        //new SyncPanchayatData().execute("");
        Intent iUserHome = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(iUserHome);
        finish();
    }

}
