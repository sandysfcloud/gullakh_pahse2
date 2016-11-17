package com.gullakh.gullakhandroidapp;

import android.app.Fragment;
/*
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
*/
public class FacebookFragment extends Fragment {
    /*   ProfileTracker[] mProfileTracker;
        TextView mTextDetails;
        LoginButton loginButton;
        CallbackManager callbackManager;
        private Profile profile;
        private Bitmap bmp;
        private ImageView imageView;
        private Button loginBut;
        String id = null;
    */
        public FacebookFragment()
        {

        }
    /*
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
            loginButton.setReadPermissions(Arrays.asList(
                    "public_profile", "email", "user_birthday", "user_friends"));
            loginButton.setFragment(this);
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
            {
                //LoginManager.getInstance().registerCallback(callbackManager,new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(final LoginResult loginResult) {
                    // fetchUserInfo();
                    getdataofprofile();
                    System.out.println("onSuccess");
                    String accessToken = loginResult.getAccessToken().getToken();
                    Log.i("accessToken", accessToken);

                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.i("LoginActivity", response.toString());
                            Log.i("LoginActivity", object.toString());
                            // Get facebook data from login
                            try {
                                mTextDetails.setText(object.getString("first_name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Bundle bFacebookData = getFacebookData(object);
                            Log.d("onCompleted",bFacebookData.toString());

                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                    request.setParameters(parameters);
                    request.executeAsync();
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

        Bundle getFacebookData(JSONObject object) {

            Bundle bundle = new Bundle();


            try {
                id= object.getString("id");
                bundle.putString("idFacebook", id);
                if (object.has("first_name"))
                    bundle.putString("first_name", object.getString("first_name"));
                if (object.has("last_name"))
                    bundle.putString("last_name", object.getString("last_name"));
                if (object.has("email"))
                    bundle.putString("email", object.getString("email"));
                if (object.has("gender"))
                    bundle.putString("gender", object.getString("gender"));
                if (object.has("birthday"))
                    bundle.putString("birthday", object.getString("birthday"));
                if (object.has("location"))
                    bundle.putString("location", object.getJSONObject("location").getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return bundle;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState)
        {
            mTextDetails = (TextView) view.findViewById(R.id.user);
            imageView=(ImageView) view.findViewById(R.id.profilepic);
            loginBut=(Button) view.findViewById(R.id.buttonLogin);
            loginBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginButton.performClick();
                }
            });

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
                        mTextDetails.setText(userInfo);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,gender");
                request.setParameters(parameters);
                GraphRequest.executeBatchAsync(request);
                getProfile();
            }
        }

        public void getProfile()
        {


            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        URL in = new URL("https://graph.facebook.com/"+id+"/picture?type=large");
                        bmp = BitmapFactory.decodeStream(in.openConnection().getInputStream());
                    } catch (Exception e) {
                        Log.d("error",e.toString());
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    if (bmp != null)
                        imageView.setImageBitmap(bmp);
                }

            }.execute();
        }

        public void getdataofprofile() {

        }*/
    }
