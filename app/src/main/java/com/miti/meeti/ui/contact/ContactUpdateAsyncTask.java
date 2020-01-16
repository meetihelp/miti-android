package com.miti.meeti.ui.contact;

import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.miti.meeti.MainActivity;
import com.miti.meeti.database.Contact.ContactDb;
import com.miti.meeti.database.Contact.ContactDbViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactUpdateAsyncTask extends AsyncTask<Void,Void,Void> {
    public ContactUpdateAsyncTask() {
        super();
    }
    public List<ContactDb> getContact(){
        List<ContactDb> arrayList=new ArrayList<>();
        Cursor cursor=MainActivity.MainActivityContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            ContactDb temp=new ContactDb(phone,name,-1);
            arrayList.add(temp);
        }
        return arrayList;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        ContactDbViewModel contactDbViewModel= MainActivity.contactDbViewModel;
        List<ContactDb>temp=getContact();
        contactDbViewModel.insert(temp.toArray(new ContactDb[temp.size()]));
        return null;
    }
}
