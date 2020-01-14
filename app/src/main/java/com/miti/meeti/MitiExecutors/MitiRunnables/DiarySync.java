package com.miti.meeti.MitiExecutors.MitiRunnables;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.ImageUploadResponse;
import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.database.Diary.Moodboard;
import com.miti.meeti.database.Diary.MoodboardViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.UploadImage;

import java.util.List;

public class DiarySync implements Runnable {
    @Override
    public void run() {
        Gson gson=new Gson();
        MoodboardViewModel moodboardViewModel= MainActivity.moodboardViewModel;
        List<Moodboard>temp=moodboardViewModel.getallnotsynced();
        if(temp==null||temp.size()==0){
            return;
        }
        for(Moodboard tempx:temp){
            if(tempx.Mimetype.contains("text")){

            }else if(tempx.Mimetype.contains("image")){
                //param1 suburl, param2 fileaddress, param3 filename, param4 requestid, param5 public
                try{
                    String res=new UploadImage().execute("uploadImage",tempx.ImagePath,tempx.ImagePath,tempx.RequestId
                            ,"private").get();
                    if(res==null){
                        return;
                    }
                    ImageUploadResponse imgur=gson.fromJson(res,ImageUploadResponse.class);
                    if(imgur==null){
                        return;
                    }
                    if(imgur.Code==200){
                        //String requestId,String imageId
                        moodboardViewModel.updateimage(tempx.RequestId,imgur.ImageId);
                        Mlog.e("moodboard synced->",tempx.RequestId,imgur.ImageId);
                    }
                }catch (Exception e){

                }
            }
        }
    }
}
