package com.justhavehope.spinandwin;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class deposit extends AppCompatActivity {

    TextView depos_statement;
    Button depos_but, depos_till;
    EditText depos_message, depos_amount;
    String tillname, till_numb, least, mpesa_message, acount_bal;
    int mpesa_amount;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        depos_statement = findViewById(R.id.depos_statement);
        depos_till = findViewById(R.id.depos_copy);
        depos_but = findViewById(R.id.depos_but);
        depos_message = findViewById(R.id.depos_message);
        depos_amount = findViewById(R.id.depos_amount);

        AdsManager.LoadBannerAd(this);
        AdsManager.LoadInterstitialAd(this);

        sharedPreferences = getApplicationContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        acount_bal = sharedPreferences.getString("Points_Earned", "0");
        tillname = sharedPreferences.getString("till_name", "");
        till_numb = sharedPreferences.getString("till_no", "");
        least = sharedPreferences.getString("least", "150");

        depos_statement.setText("You can top up your account balance here to continue winning. To make a deposit, go to your M-Pesa or " +
                "Airtel Money and make a deposit of not less than Ksh."+least+" to till number "+ till_numb +"("+ tillname +").\n\n" +
                "Then come back to this page and enter the amount you deposited " +
                "below and paste the M-Pesa message you received below it.\n\n" +
                "Click on the button below to copy till number to clipboard.");

        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Deposit")
                .document("Till_strings");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot dsnaps = task.getResult();

                String tillname = dsnaps.getString("Till_name");
                String tillnumb = dsnaps.getString("Till_number");
                String minim = dsnaps.getString("Least");

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("till_name", tillname);
                editor.putString("till_no", tillnumb);
                editor.putString("least", minim);
                editor.commit();

                till_numb = tillnumb;

                depos_statement.setText("You can top up your account balance here to continue winning. To make a deposit, go to your M-Pesa or " +
                        "Airtel Money and make a deposit of not less than Ksh."+least+" to till number "+ till_numb +"("+ tillname +").\n\n" +
                        "Then come back to this page and enter the amount you deposited " +
                        "below and paste the M-Pesa message you received below it.\n\n" +
                        "Click on the button below to copy till number to clipboard.");
            }
        });

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        depos_till.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clipboardManager.setPrimaryClip(ClipData.newPlainText("TILL", till_numb));
                Toast.makeText(deposit.this, "TILL Number Copied to Clipboard", Toast.LENGTH_LONG).show();
            }
        });

        depos_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mpesa_message = depos_message.getText().toString();
                String mp_amount = depos_amount.getText().toString();
                int minimun = Integer.parseInt(least);

                if (mp_amount.isEmpty()){
                    depos_amount.setError("Enter amount");
                    Toast.makeText(deposit.this, "Enter the amount you deposited", Toast.LENGTH_SHORT).show();
                    return;
                }
                mpesa_amount = Integer.parseInt(mp_amount);

                if (mpesa_amount < minimun) {
                    Toast.makeText(deposit.this, "Amount should be atleast Ksh." +least , Toast.LENGTH_SHORT).show();
                } else if (mpesa_message.isEmpty()) {
                    depos_message.setError("Paste your M-Pesa message");
                    Toast.makeText(deposit.this, "Paste your M-Pesa message", Toast.LENGTH_SHORT).show();
                } else if (mpesa_message.length() < 50) {
                    depos_message.setError("Enter correct M-Pesa message");
                    Toast.makeText(deposit.this, "Paste your M-Pesa massage", Toast.LENGTH_SHORT).show();
                } else if ((mpesa_amount >= minimun) && mpesa_message.length() > 50){

                    int add = Integer.parseInt(acount_bal) + mpesa_amount;
                    String result = String.valueOf(add);


                    ProgressDialog progressDialog = new ProgressDialog(deposit.this);
                    progressDialog.setTitle("Please wait, it takes less than a minute");
                    progressDialog.setMessage("Submitting...");
                    progressDialog.setProgressStyle(0);
                    progressDialog.setMax(100);
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Points_Earned", result);
                                editor.putInt("Deposit", 1);
                                editor.commit();

                                Thread.sleep(13000);

                                AdsManager.ShowInterstitialAd(deposit.this, MainActivity.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();

                }
            }
        });
    }
}