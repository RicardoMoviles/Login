package com.restrepo.ricardo.login;






import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.app.FragmentManager;
import android.app.Fragment;
import android.app.FragmentTransaction;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import com.restrepo.ricardo.login.formulario.FormularioListener;

import java.util.Map;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener,FormularioListener{


    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusUser/*, mStatusEmail*/;
    private ProgressDialog mProgressDialog;

    private Firebase mRef;
    private Firebase ejemplo1;
    private String llave2="";
    private String llave1="";

    private EditText Nombre;
    private EditText Contraseña;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        //Boton para abrir fragment formulario
        Button registrarse = (Button) findViewById(R.id.bRegistro);
        Button conectar = (Button) findViewById(R.id.bConectar);
        Button desconectar = (Button) findViewById(R.id.bDesconectar);

        //Datos de autenticacion
        Nombre = (EditText) findViewById(R.id.eUser);
        Contraseña = (EditText) findViewById(R.id.ePassword);

        registrarse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                Fragment editor = fm.findFragmentByTag("editor");
                if (null == editor) {
                    findViewById(R.id.principal).setVisibility(View.GONE);
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.add(R.id.contendorFormulario, new formulario(), "editor");
                    ft.commit();
                }
            }
        });

        conectar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //No funciona//
//                Firebase ref = new Firebase("https://loginservices.firebaseio.com");
//                ejemplo1 =  ref.child("users").child("uid");
//                ref.createUser("bobtony@firebase.com", "correcthorsebatterystaple", new Firebase.ValueResultHandler<Map<String, Object>>() {
//                    @Override
//                    public void onSuccess(Map<String, Object> result) {
//                        System.out.println("Successfully created user account with uid: " + result.get("uid"));
//                        result.get("uid");
//                    }
//                    @Override
//                    public void onError(FirebaseError firebaseError) {
//                        // there was an error
//                    }
//                });

                mRef=new Firebase("https://loginservices.firebaseio.com/Usuarios");

                final String pwd = Contraseña.getText().toString();


                ConsultarNombre();


                Query query1 =mRef.orderByChild("contraseña").equalTo(pwd);

                query1.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    llave2= dataSnapshot.getKey();
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                confirmar();

            }
        });


        desconectar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                        llave1="";
                        mStatusUser.setText("Nombre de Usuario");
                        findViewById(R.id.lUser).setVisibility(View.VISIBLE);
                        findViewById(R.id.lPassword).setVisibility(View.VISIBLE);
                        findViewById(R.id.bConectar).setVisibility(View.VISIBLE);
                        findViewById(R.id.bDesconectar).setVisibility(View.GONE);
            }
        });




        //Views
        mStatusUser = (TextView) findViewById(R.id.dataStatus);
        //mStatusEmail = (TextView) findViewById(R.id.id_tvStatusEmail);

        //Button listeners.
        findViewById(R.id.id_sign_in_button).setOnClickListener(this);
        findViewById(R.id.id_sign_out_button).setOnClickListener(this);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /*FragmentActivity*/ ,this /*OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    public void confirmar(){
        if (llave1.equals(llave2) && llave1!="") {
            mStatusUser.setText("Bienvenido: " + llave1);
            findViewById(R.id.lUser).setVisibility(View.GONE);
            findViewById(R.id.lPassword).setVisibility(View.GONE);
            findViewById(R.id.bConectar).setVisibility(View.GONE);
            findViewById(R.id.bDesconectar).setVisibility(View.VISIBLE);
        } else
        {
            mStatusUser.setText("Error de Autenticación");
        }
    }

    public void ConsultarNombre(){

        final String name = Nombre.getText().toString();
        Query query = mRef.orderByChild("nombre").equalTo(name);
        query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                llave1= dataSnapshot.getKey();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
    }



    @Override
    public void pulsado(int resultado,String control, String log,String correo,String contraseña) {
        TextView tv = (TextView) findViewById(R.id.dataStatus);
        if (resultado == formulario.OK) {
            tv.setText(control);
            //Mi base de Datos
            mRef=new Firebase("https://loginservices.firebaseio.com/");
            //Muestra Tiempo Real
            ejemplo1 =  mRef.child("Usuarios").child(log);
            Firebase ejemplo2 =  mRef.child("Usuarios").child(log).child("nombre");
            ejemplo2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String text = (String) dataSnapshot.getValue();
                    mStatusUser.setText(text);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            Cliente nuevo = new Cliente(log,correo,contraseña);
            ejemplo1.setValue(nuevo);

        }else if(resultado == formulario.Error){
            tv.setText(control);
        }
        FragmentManager fm = getFragmentManager();
        Fragment editor = fm.findFragmentByTag("editor");
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(editor);
        ft.commit();
        findViewById(R.id.principal).setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.id_sign_in_button:
                signIn();
                break;
            case R.id.id_sign_out_button:
                signOut();
                break;

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });

    }

    private void signIn(){
        Intent signinintent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signinintent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from
        //   GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            mStatusUser.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            Toast.makeText(this,"In",Toast.LENGTH_LONG).show();
            findViewById(R.id.id_sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.id_sign_out_button).setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this,"Out",Toast.LENGTH_LONG).show();
            //Put into status view...
            mStatusUser.setText(R.string.signed_out);
            findViewById(R.id.id_sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.id_sign_out_button).setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}