package com.miti.meeti.ui.login;

import androidx.navigation.Navigation;

import com.miti.meeti.R;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;

public class ProfilePostRequest extends POSTRequest {
    @Override
    protected void onPostExecute(RequestHelper result) {
        Navigation.findNavController(profile_creation.v1).navigate(R.id.action_profile_creation_to_social_pref_interest2);
    }
}
