package com.miti.meeti.ui.security;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.database.Contact.ContactDb;
import com.miti.meeti.database.Contact.ContactDbViewModel;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.try123;

public class DeleteContact{
    public class request{
        public String Phone;
        public String ChainId;
        public String Name;
        public String RequestId;
    }
    public class response{
        public Integer Code;
        public String Message;
        public String UpdatedAt;
        public String RequestId;
    }
    public static void helper(ContactDb contact){
        Gson gson=new Gson();
        String json=gson.toJson(contact);
        ContactDbViewModel contactDbViewModel= MainActivity.contactDbViewModel;
        ContactDb contactDb=contact;
        contactDbViewModel.delete(contact);
        contactDb.Tag="deleted";
        contactDb.Status=-1;
        contactDb.Requestid= try123.randomAlphaNumeric(32);
        contactDbViewModel.insert(contactDb);
    }
}
