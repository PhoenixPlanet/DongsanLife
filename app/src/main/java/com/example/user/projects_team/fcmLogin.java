package com.example.user.projects_team;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fcmLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "EmailPasswordRegister";

    Boolean isSigned;

    ProgressDialog dialog2;

    Register dialog = new Register();

    Button register, login;
    TextView showUserName, isRegister, showInfo;
    TextInputEditText editID, editPS;
    TextInputLayout ti_ps, ti_email;

    private boolean readyForSignIn() {
        return !(editID.getEditableText().toString().equals("") || editPS.getEditableText().toString().equals(""));
    }

    TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE)
            {
                return true;
            }
            return false;
        }
    };

    View.OnClickListener registerlistener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction tr = fm.beginTransaction();
            Fragment prev = fm.findFragmentByTag("dialog");

            if(prev != null){
                tr.remove(prev);
            }
            dialog.show(tr, "dialog");
        }
    };

    View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isSigned){
                FirebaseAuth.getInstance().signOut();
                finish();
            }
            else{
                dialog2.show();
                if (readyForSignIn()){
                    mAuth.signInWithEmailAndPassword(editID.getEditableText().toString(), editPS.getEditableText().toString())
                            .addOnCompleteListener(fcmLogin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                                        Toast.makeText(fcmLogin.this, R.string.fail_sign_in,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(fcmLogin.this, "로그인 되었습니다.",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    dialog2.dismiss();
                                }
                            });

                }
                else {
                    Toast.makeText(fcmLogin.this, R.string.check_email_or_password,
                            Toast.LENGTH_SHORT).show();

                }

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm_login);

        dialog2 = new ProgressDialog(this);
        dialog2.setMessage(getString(R.string.please_wait));
        dialog2.setCancelable(false);

        register = (Button) findViewById(R.id.FcmLogin_register);
        login = (Button) findViewById(R.id.FcmLoginButton);
        showUserName = (TextView) findViewById(R.id.login_user_name);
        editID = (TextInputEditText) findViewById(R.id.imail_id);
        editPS = (TextInputEditText) findViewById(R.id.email_password);
        isRegister = (TextView) findViewById(R.id.did_you_register);
        showInfo = (TextView) findViewById(R.id.show_grade_and_class);
        ti_ps = (TextInputLayout) findViewById(R.id.ti_2);
        ti_email = (TextInputLayout) findViewById(R.id.ti_1);
        String username;
        showUserName.setText("로딩중..");

        editID.setOnEditorActionListener(editorListener);
        register.setOnClickListener(registerlistener);
        login.setOnClickListener(loginListener);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = firebaseDatabase.getReference("user");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                if (user1 != null) {
                    //작업 성공시
                    Log.d(TAG, "Signed In-Listener: " + user1.getUid());

                    dialog2.show();
                    DatabaseReference userRef2 = firebaseDatabase.getReference("user");

                    final String userId = user1.getUid();
                    userRef2.child(user1.getUid()).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    UserData user = dataSnapshot.getValue(UserData.class);
                                    showUserName.setText(user.getUserName());
                                    showInfo.setText(user.getGradeNum()+" "+user.getClassNum());
                                    dialog2.dismiss();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                                    showUserName.setText("오류");
                                }
                            });
                    Log.d(TAG, "showdd");
                    showInfo.setVisibility(View.VISIBLE);
                    ti_email.setVisibility(View.GONE);
                    ti_ps.setVisibility(View.GONE);
                    isRegister.setVisibility(View.GONE);
                    login.setText("로그아웃");
                    register.setClickable(false);
                    register.setVisibility(View.GONE);
                    isSigned = true;

                } else {
                    showUserName.setText(R.string.state_signed_out);
                    ti_email.setVisibility(View.VISIBLE);
                    ti_ps.setVisibility(View.VISIBLE);
                    isRegister.setVisibility(View.VISIBLE);
                    login.setText("로그인");
                    showInfo.setVisibility(View.GONE);
                    register.setClickable(true);
                    register.setVisibility(View.VISIBLE);
                    showUserName.setText("비로그인 상태입니다");
                    isSigned = false;
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
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


    public void selectEmail(View view) {
        Intent intent = new Intent(fcmLogin.this, RegisterEmailActivity.class);
        dialog.dismiss();
        startActivity(intent);
    }

    public void selectGoogle(View view) {
        Intent intent = new Intent(fcmLogin.this, LoginActivity.class);
        startActivity(intent);
        dialog.dismiss();
    }

    public static class Register extends DialogFragment {

        public Dialog onCreateDialog(Bundle savedInstanceState){

            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.select_register_dialog);

            dialog.setTitle("어떤 방법으로 연결하시겠어요?");

            return dialog;

        }

    }

}
