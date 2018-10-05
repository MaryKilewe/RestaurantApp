package com.example.mary.foodapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    // Creating EditText .
    EditText email, password ;

    // Creating button.
    Button SignUp ;

    // Creating string to hold email and password .
    String EmailHolder, PasswordHolder ;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Creating FirebaseAuth object.
    FirebaseAuth firebaseAuth ;

    // Creating Boolean variable that holds EditText is empty or not status.
    Boolean EditTextStatus ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //must be called before setContentView(...)
        // This if the code for gradient colors in title bar and bar. Along wwith this code is app_bar.xml and relative layout layer in activity_sign_up.xml
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_sign_up);

        // Assigning layout email ID and Password ID.
        email = (EditText)findViewById(R.id.emailInput);
        password = (EditText)findViewById(R.id.passwordInput);

        // Assign button layout ID.
        SignUp = (Button)findViewById(R.id.buttonSignup);

        // Creating object instance.
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(SignupActivity.this);

        // Adding click listener to Sign Up Button.
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling method to check EditText is empty or no status.
                CheckEditTextIsEmptyOrNot();

                // If EditText is true then this block with execute.
                if(EditTextStatus){

                    // If EditText is not empty than UserRegistrationFunction method will call.
                    UserRegistrationFunction();

                }
                // If EditText is false then this block with execute.
                else {

                    Toast.makeText(SignupActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    // Creating UserRegistrationFunction
    public void UserRegistrationFunction(){

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Registering Your Data on Server");
        progressDialog.show();

        // Creating createUserWithEmailAndPassword method and pass email and password inside it.
        firebaseAuth.createUserWithEmailAndPassword(EmailHolder, PasswordHolder).
                addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // Checking if user is registered successfully.
                        if(task.isSuccessful()){

                            // If user registered successfully then show this toast message.
                            Toast.makeText(SignupActivity.this,"User Registration Successfully",Toast.LENGTH_LONG).show();

                        }else{

                            // If something goes wrong.
                            Toast.makeText(SignupActivity.this,"Something Went Wrong.",Toast.LENGTH_LONG).show();
                        }

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                    }
                });

    }

    public void CheckEditTextIsEmptyOrNot(){

        // Getting name and email from EditText and save into string variables.
        EmailHolder = email.getText().toString().trim();
        PasswordHolder = password.getText().toString().trim();

        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            EditTextStatus = false;

        }
        else {

            EditTextStatus = true ;
        }

    }

}