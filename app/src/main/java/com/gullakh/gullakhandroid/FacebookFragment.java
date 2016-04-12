package com.gullakh.gullakhandroid;

import android.support.v4.app.Fragment;

//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.Profile;
//import com.facebook.ProfileTracker;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
public class FacebookFragment extends Fragment {

  /*  ProfileTracker[] mProfileTracker;
   // TextView mTextDetails;
    LoginButton loginButton;
    CallbackManager callbackManager;
    private Profile profile;
    public FacebookFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        View view = inflater.inflate(R.layout.fragment_facebook, container, false);
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setBackgroundResource(R.drawable.fb);
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("user_birthday");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            //LoginManager.getInstance().registerCallback(callbackManager,new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                fetchUserInfo();
            }
            @Override
            public void onCancel()
            {

            }
            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        //mTextDetails = (TextView) view.findViewById(R.id.user);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void fetchUserInfo()
    {
        //  mTextDetails.setText(profile.getName());
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback()
            {
                @Override
                public void onCompleted(JSONObject me, GraphResponse response)
                {
                    JSONObject users = me;
                    String userInfo=users.optString("id") +"\n" + users.optString("name") +
                            "\n"+ users.optString("email") + users.optString("gender")
                            + "\n" + users.opt("birthday");
                    //mTextDetails.setText(userInfo);
                }
            });
            Bundle parameters = new Bundle();
            //parameters.putString(FIELDS, REQUEST_FIELDS);
            request.setParameters(parameters);
            GraphRequest.executeBatchAsync(request);
        }
    }
    */
}
