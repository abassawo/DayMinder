package abassawo.c4q.nyc.dayminder.Fragments;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import abassawo.c4q.nyc.dayminder.Activities.MainActivity;
import abassawo.c4q.nyc.dayminder.R;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusOneButton;

/**
 * A fragment with a Google +1 button.
 */
public class LoginFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {


    //Constants
    private static final int SIGNED_IN = 0;
    private static final int STATE_SIGNING_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;
    private static final int RC_SIGN_IN = 0;

    //Class variables
    private GoogleApiClient mGoogleApiClient;
    private int mSignInProgress;
    private PendingIntent mSignInIntent;

    //Views
    @Bind(R.id.sign_in_button)
    SignInButton mSignInButton;
    @Bind(R.id.sign_out_button)
    Button mSignOutButton;
    @Bind(R.id.revoke_access_button) Button mRevokeButton;
    @Bind(R.id.statuslabel)
    TextView mStatus;



    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = buildGoogleApiClient();
    }
    public void initViewsAndListeners(){
        mSignInButton.setOnClickListener(this);
        mSignOutButton.setOnClickListener(this);
        mRevokeButton.setOnClickListener(this);

    }
    private GoogleApiClient buildGoogleApiClient() {
        return new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(new Scope("email"))
                .build();
    }

    public void revokeAccess(){
        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
        Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
        mGoogleApiClient = buildGoogleApiClient();
        mGoogleApiClient.connect();
    }
    @Override
    public void onClick(View v) {
        if (!mGoogleApiClient.isConnecting()) {
            switch (v.getId()) {
                case R.id.sign_in_button:
                    mStatus.setText("Signing In");
                    resolveSignInError();
                    break;
                case R.id.sign_out_button:
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    mGoogleApiClient.connect();
                    break;
                case R.id.revoke_access_button:
                    revokeAccess();
                    break;
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInButton.setEnabled(false);
        mSignOutButton.setEnabled(true);
        mRevokeButton.setEnabled(true);

        // Indicate that the sign in process is complete.
        mSignInProgress = SIGNED_IN;

        try {
            String emailAddress = Plus.AccountApi.getAccountName(mGoogleApiClient);
            mStatus.setText(String.format("Signed In as %s", emailAddress));
        }
        catch(Exception ex){
            String exception = ex.getLocalizedMessage();
            String exceptionString = ex.toString();
            Log.d("Sign-in failed", exception + exceptionString);
            // Note that you should log these errors in a ‘real’ app to aid in debugging
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        }, 100);


    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (mSignInProgress != STATE_IN_PROGRESS) {
            mSignInIntent = result.getResolution();
            if (mSignInProgress == STATE_SIGNING_IN) {
                resolveSignInError();
            }
        }
        // Will implement shortly
        onSignedOut();
    }
    private void onSignedOut() {
        // Update the UI to reflect that the user is signed out.
        mSignInButton.setEnabled(true);
        mSignOutButton.setEnabled(false);
        mRevokeButton.setEnabled(false);

        mStatus.setText("Signed out");
    }

    private void resolveSignInError() {
        if (mSignInIntent != null) {
            try {
                mSignInProgress = STATE_IN_PROGRESS;
                getActivity().startIntentSenderForResult(mSignInIntent.getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                mSignInProgress = STATE_SIGNING_IN;
                mGoogleApiClient.connect();
            }
        } else {
            // You have a play services error -- inform the user
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        initViewsAndListeners();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_SIGN_IN:
                if (resultCode == getActivity().RESULT_OK) {
                    mSignInProgress = STATE_SIGNING_IN;
                } else {
                    mSignInProgress = SIGNED_IN;
                }

                if (!mGoogleApiClient.isConnecting()) {
                    mGoogleApiClient.connect();
                }
                break;
        }
    }

}
