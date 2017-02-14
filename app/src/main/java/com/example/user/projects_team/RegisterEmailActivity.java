package com.example.user.projects_team;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterEmailActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    String passwordCheck;

    TextInputLayout ti2_1, ti2_2, ti2_3, ti2_4;
    AppCompatEditText email_id2, email_password2, email_password3, email_name;
    RadioGroup grade_radio;
    RadioButton first, second, third;
    ProgressDialog dialog;
    Spinner class_spinner;

    private static final String TAG = "EmailPasswordRegister";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.please_wait));
        dialog.setCancelable(false);

        class_spinner = (Spinner)findViewById(R.id.class_number_spinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.class_number,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        class_spinner.setAdapter(adapter);

        ti2_2 = (TextInputLayout) findViewById(R.id.ti2_2);
        ti2_3 = (TextInputLayout) findViewById(R.id.ti2_3);
        ti2_1 = (TextInputLayout) findViewById(R.id.ti2_1);
        ti2_4 = (TextInputLayout) findViewById(R.id.ti2_4);

        email_password2 = (AppCompatEditText) findViewById(R.id.email_password2);
        email_password3 = (AppCompatEditText) findViewById(R.id.email_password3);
        email_id2 = (AppCompatEditText) findViewById(R.id.imail_id2);
        email_name = (AppCompatEditText) findViewById(R.id.email_name);

        grade_radio = (RadioGroup) findViewById(R.id.grade_radio);

        first = (RadioButton) findViewById(R.id.first_grade);
        second = (RadioButton) findViewById(R.id.second_grade);
        third = (RadioButton) findViewById(R.id.third_grade);

        ti2_2.setErrorEnabled(true);
        ti2_3.setErrorEnabled(true);
        ti2_1.setErrorEnabled(true);
        ti2_4.setErrorEnabled(true);

        email_password2.addTextChangedListener(pwTextWatcher1);
        email_password3.addTextChangedListener(pwTextWatcher2);
        email_id2.addTextChangedListener(emailTextWatcher);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                if (user1 != null) {
                    //작업 성공시
                    Log.d(TAG, "Signed In");
                } else {

                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    TextWatcher pwTextWatcher1 = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }
        @Override
        public void afterTextChanged(Editable s)
        {
            if(s.length()<6) {
                ti2_2.setError("비밀번호는 6자리 이상이어야 합니다");
            }
            else
            {
                ti2_2.setError(null);
            }
            passwordCheck = s.toString();
        }
    };

    TextWatcher pwTextWatcher2 = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }
        @Override
        public void afterTextChanged(Editable s)
        {
            if(!(passwordCheck.equals(s.toString()))) {
                ti2_3.setError("비밀번호가 일치하지 않습니다.");
            }
            else
            {
                ti2_3.setError(null);
            }
        }
    };

    TextWatcher emailTextWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }
        @Override
        public void afterTextChanged(Editable s)
        {
            if(!(s.toString().contains("@"))) {
                ti2_1.setError(getString(R.string.email_error));
            }
            else
            {
                ti2_1.setError(null);
            }
        }
    };

    public void emailRegisterFinal(View view) {
        if (checkRegisterForm()){
            if((ti2_1.getError()==null)&&(ti2_2.getError()==null)&&(ti2_3.getError()==null)&&(ti2_4.getError()==null)&&(!(email_id2.getEditableText().toString().equals("")))&&(!(email_password2.getEditableText().toString().equals("")))&&(!(email_password3.getEditableText().toString().equals("")))){
                String email, password;

                email = email_id2.getEditableText().toString();
                password = email_password2.getEditableText().toString();

                dialog.show();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "onComplete: "+task.isSuccessful());

                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterEmailActivity.this, R.string.register_fail,
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    RadioButton rb = (RadioButton) findViewById(grade_radio.getCheckedRadioButtonId());
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(email_name.getEditableText().toString())
                                            .setPhotoUri(Uri.parse(""))
                                            .build();
                                    user.updateProfile(profileUpdates);

                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference databaseReference = firebaseDatabase.getReference();

                                    UserData userData = new UserData(email_name.getEditableText().toString(), class_spinner.getSelectedItem().toString(), rb.getText().toString());

                                    databaseReference.child("user").child(user.getUid()).setValue(userData);

                                    Toast.makeText(RegisterEmailActivity.this, R.string.register_success,
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                dialog.dismiss();
                            }
                        });
            }
            else{
                Toast.makeText(RegisterEmailActivity.this, R.string.check_email_or_password,
                        Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(RegisterEmailActivity.this, "모든 입력란을 채우셔야 합니다.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkRegisterForm() {
        if(first.isChecked()||second.isChecked()||third.isChecked()){
            if((!(email_name.getEditableText().toString().equals("")))){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /*public class UserData {

        private String username;
        private String classNum;
        private String gradeNum;

        public UserData(){}

        public UserData(String username, String classNum, String gradeNum) {
            this.username = username;
            this.classNum = classNum;
            this.gradeNum = gradeNum;
        }

        public String getUserName(){
            return username;
        }

        public String getClassNum(){
            return classNum;
        }

        public String getGradeNum(){
            return gradeNum;
        }
    }*/
}
