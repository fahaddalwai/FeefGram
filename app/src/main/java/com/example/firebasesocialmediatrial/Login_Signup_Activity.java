package com.example.firebasesocialmediatrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login_Signup_Activity extends AppCompatActivity {
    Button authenticate;
    EditText username;
    EditText password;
    TextView switcher;
    EditText name;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginsignup);

        username=findViewById(R.id.usernamefield);
        password=findViewById(R.id.passwordfield);
        name=findViewById(R.id.namefield);
        authenticate=findViewById(R.id.loginorsignup);
        switcher=findViewById(R.id.switcher);

        mAuth = FirebaseAuth.getInstance();
        authenticate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {


                if(authenticate.getText().equals("signup")){
                    String e=username.getText().toString();
                    String p=password.getText().toString();
                    mAuth.createUserWithEmailAndPassword(e,p)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        nextActivity();
                                    }else{
                                        //print error
                                    }
                                }
                            });




                }else{
                    String e=username.getText().toString();
                    String p=password.getText().toString();
                    mAuth.signInWithEmailAndPassword(e,p)
                            .addOnCompleteListener(Login_Signup_Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.i("success","login is successful");
                                        nextActivity();

                                    } else {
                                        Log.i("failure","login failed :(");
                                    }
                                }
                            });
                }
            }
        });

        switcher.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(authenticate.getText().equals("signup")){
                    authenticate.setText("login");
                    switcher.setText("signup");

                }else{
                    authenticate.setText("signup");
                    switcher.setText("login");
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            //handle already logged in
        }
    }

    public void nextActivity(){
        Intent intent=new Intent(this,Upload_photos.class);
        intent.putExtra("USERNAME", name.getText().toString());
        startActivity(intent);
    }
}