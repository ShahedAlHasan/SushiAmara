package sushi.amara.com.customarapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import sushi.amara.com.customarapp.Common.Common;
import sushi.amara.com.customarapp.R;
import sushi.amara.com.customarapp.pojo.User;

public class RegisterActivity extends NaviBase {

    Button btnSignIn,btnRegister;
    RelativeLayout rootLayout;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    TextView txtForgotPass;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


//        mtollbarText=(TextView) findViewById(R.id.toolbar_text_base);
//        mtollbarText.setText("Register");
//        mtollbarText.setCompoundDrawablesWithIntrinsicBounds( R.drawable.store, 0, 0, 0);

        //initial direbase
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Customers");

        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);
        rootLayout = findViewById(R.id.root_layout);
        txtForgotPass = findViewById(R.id.txt_forgot_password);



        //Check already session , if ok-> DashBoard
        if(auth.getCurrentUser() != null){

            FirebaseDatabase.getInstance().getReference("Customers").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);

                    if (user.getStatus().equals("Active")) {
                        startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Your profile Not Active yet...!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        //Event

        txtForgotPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                showDilogForgotPass();
                return false;
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDilog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDilog();
            }
        });

    }


    private void showDilogForgotPass() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Passwort Vergessen?");
        dialog.setMessage("Gib Deine E-Mail-Adresse ein und wir schicken Dir einen Link zum Zurücksetzen Deines Passworts.");

        LayoutInflater inflater = LayoutInflater.from(this);
        View forgotPass_layout = inflater.inflate(R.layout.layout_forgot_pwd,null);

        final MaterialEditText editEmail = forgotPass_layout.findViewById(R.id.editEmail);
        dialog.setView(forgotPass_layout);

        dialog.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage("loading...");
                mDialog.show();

                auth.sendPasswordResetEmail(editEmail.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                dialog.dismiss();
                                mDialog.dismiss();
                                Snackbar.make(rootLayout,"Check Your mail inbox ",Snackbar.LENGTH_LONG)
                                        .show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        mDialog.dismiss();
                        Snackbar.make(rootLayout,""+e.getMessage(),Snackbar.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });
        dialog.setNegativeButton("Storno", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();



    }

    private void showLoginDilog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Anmeldung");
        dialog.setMessage("Bitte Gib Deine Anmeldendaten Ein:");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login,null);

        final MaterialEditText editEmail = login_layout.findViewById(R.id.editEmail);
        final MaterialEditText editPassword = login_layout.findViewById(R.id.editPassword);

        dialog.setView(login_layout);

        //set button
        dialog.setPositiveButton("Anmeldung", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                //set disabale sing in button if it processisng
                btnSignIn.setEnabled(false);

                //check validition
                if (TextUtils.isEmpty(editEmail.getText().toString())){
                    Snackbar.make(rootLayout,"Bitte geben Sie Ihre E-Mail-Adresse ein",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(editPassword.getText().toString())){
                    Snackbar.make(rootLayout,"Bitte Passwort eingeben",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                if (editPassword.getText().toString().length()<6){
                    Snackbar.make(rootLayout,"Passwort zu kurz..!!",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                //log in

                final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage("loading...");
                mDialog.show();

                auth.signInWithEmailAndPassword(editEmail.getText().toString(),editPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                mDialog.dismiss();

                                FirebaseDatabase.getInstance().getReference("Customers")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                user = dataSnapshot.getValue(User.class);
                                                Common.currentUser = dataSnapshot.getValue(User.class);

                                                if (user.getStatus().equals("Active")) {
                                                    startActivity(new Intent(RegisterActivity.this,ProfileActivity.class));
                                                    finish();
                                                }
                                                else {
                                                    Toast.makeText(RegisterActivity.this, "Your profile Not Active yet...!", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mDialog.dismiss();
                                Snackbar.make(rootLayout,"Failed: "+e.getMessage(),Snackbar.LENGTH_LONG)
                                        .show();
                                btnSignIn.setEnabled(true);
                            }
                        });
            }
        });
        dialog.setNegativeButton("Storno", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }



    private void showRegisterDilog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Registrieren");
        dialog.setMessage("Bitte Gib Deine Registrieren");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_layout = inflater.inflate(R.layout.layout_register,null);

        final MaterialEditText editEmail = register_layout.findViewById(R.id.editEmail);
        final MaterialEditText editPassword = register_layout.findViewById(R.id.editPassword);
        final MaterialEditText editName = register_layout.findViewById(R.id.editName);
        final MaterialEditText editPhone = register_layout.findViewById(R.id.editPhone);

        dialog.setView(register_layout);
        //set button
        dialog.setPositiveButton("Registrieren", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //check validition
                if (TextUtils.isEmpty(editEmail.getText().toString())){
                    Snackbar.make(rootLayout,"Bitte geben Sie Ihre E-Mail-Adresse ein",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(editPhone.getText().toString())){
                    Snackbar.make(rootLayout,"Bitte Telefonnummer eingeben",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(editPassword.getText().toString())){
                    Snackbar.make(rootLayout,"Bitte Passwort eingeben",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                if (editPassword.getText().toString().length()<6){
                    Snackbar.make(rootLayout,"Passwort zu kurz..!!",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                //register new user

                auth.createUserWithEmailAndPassword(editEmail.getText().toString(),editPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //save user to db
                                User user = new User();
                                user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                user.setEmail(editEmail.getText().toString());
                                user.setName(editName.getText().toString());
                                user.setPhone(editPhone.getText().toString());
                                user.setPassword(editPassword.getText().toString());
                                user.setStatus("Pending");

                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(rootLayout,"Registration successful....pending for approval..!!",Snackbar.LENGTH_LONG)
                                                        .show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Snackbar.make(rootLayout,"Failed: "+e.getMessage(),Snackbar.LENGTH_SHORT)
                                                        .show();
                                            }
                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(rootLayout,"Failed: "+e.getMessage(),Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        });
            }
        });
        dialog.setNegativeButton("Storno", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}
