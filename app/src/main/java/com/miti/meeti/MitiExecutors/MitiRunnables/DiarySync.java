package com.miti.meeti.MitiExecutors.MitiRunnables;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.GetImageUrl;
import com.miti.meeti.NetworkObjects.ImageUploadResponse;
import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.database.Diary.Moodboard;
import com.miti.meeti.database.Diary.MoodboardViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.DownloadAndSaveImage;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.network.UploadImage;
import com.miti.meeti.mitiutil.try123;

import java.util.List;

public class DiarySync implements Runnable {
    Gson gson=new Gson();
    MoodboardViewModel moodboardViewModel= MainActivity.moodboardViewModel;
    public class requestget{
        public String CreatedAt;
        public requestget(String createdAt){
            this.CreatedAt=createdAt;
        }
    }
    public class boardContentList{
        public String UserId;
        public String ContentId;
        public String BoardId;
        public String ContentText;
        public String ContentImageId;
        public String CreatedAt;
        public String AccessType;
    }
    public class responseget{
        public Integer Code;
        public String Message;
        List<boardContentList>BoardContentList;
    }
    public void run1(){
        Moodboard temp=moodboardViewModel.getmax();
        String max;
        if(temp==null){
            max="";
        }else{
            max=temp.CreatedAt;
        }
        String req=gson.toJson(new requestget(max));
        RequestHelper data=new SimplePOST().execute("getBoardContent",req,MainActivity.MeetiCookie);
        if(data==null){
            return;
        }

        String resp=data.getData();
        responseget rg=gson.fromJson(resp,responseget.class);
        if(rg==null){
            return;
        }
        if(rg.Code==200){
            List<boardContentList> tempy=rg.BoardContentList;
            for(boardContentList tempz:tempy){
                Moodboard moodboard=new Moodboard();
                moodboard.BoardId="Default";
                moodboard.UserCreatedAt= try123.mitidt();
                moodboard.Tag="received";
                moodboard.Sync=1;
                moodboard.Contentid=tempz.ContentId;
                moodboard.CreatedAt=tempz.CreatedAt;
                if(tempz.ContentImageId.length()==0){
                    //text
                    moodboard.Mimetype="text";
                    moodboard.Content=tempz.ContentText;
                    moodboardViewModel.insert(moodboard);
                }else{
                    //image
                    moodboard.Mimetype="image";
                    moodboard.ImageId=tempz.ContentImageId;
                    String json=gson.toJson(new GetImageUrl().new request_body(tempz.ContentImageId));
                    RequestHelper respo=new SimplePOST().execute("getImageById",json,MainActivity.MeetiCookie);
                    if(respo==null){
                        continue;
                    }
                    String datx=respo.getData();
                    Mlog.e("inDiarySync85",datx);
                    if(datx==null){
                        continue;
                    }
                    GetImageUrl.response_body rb=gson.fromJson(datx,GetImageUrl.response_body.class);
                    if(rb==null){
                        continue;
                    }
                    if(rb.Code==200){
                        if(rb.ImageURL.length()==0 || rb.ImageURL==null){
                            Mlog.e("inDiarySync","image url empty");
                            continue;
                        }
                        String path=try123.pathhelper("Received","Diary");
                        try{
                            Boolean ast=new DownloadAndSaveImage(path).execute(rb.ImageURL).get();
                            if(ast){
                                moodboard.ImagePath=path;
                                moodboardViewModel.insert(moodboard);
                            }
                        }catch (Exception e){
                            continue;
                        }

                    }
                }
            }
        }

    }
    public class request{
        public String RequestId;
        public String Date;
        public String ContentText;
        public String ContentImageId;
        public String BoardId;
        public request(){}
        public request(String requestId,String date,String contentText, String contentImageId, String boardId){
            this.RequestId=requestId;
            this.Date=date;
            this.ContentText=contentText;
            this.ContentImageId=contentImageId;
            this.BoardId="Default";
        }
    }
    public class response{
        public Integer Code;
        public String Message;
        public String CreatedAt;
        public String RequestId;
        public String BoardId;
        public String ContentId;
    }
    @Override
    public void run(){
        run1();
        run2();
    }
    public void run2() {
        Gson gson=new Gson();
        MoodboardViewModel moodboardViewModel= MainActivity.moodboardViewModel;
//        Mlog.e("uploadBoardContent","entered");
        List<Moodboard>temp=moodboardViewModel.getallnotsynced();
        if(temp==null||temp.size()==0){
            return;
        }
        Mlog.e("uploadBoardContent","started");
        for(Moodboard tempx:temp){
            new Mlog<Moodboard>().e1(tempx);
            if(tempx.Mimetype.contains("text")){
                Mlog.e("uploadBoardContent","in text block");
                //String requestId,String date,String contentText, String contentImageId
                DiarySync.request reqp=new request(tempx.RequestId,tempx.UserCreatedAt,tempx.Content,"",tempx.CreatedAt);
                Mlog.e("uploadBoardContent","in line 54");
                String json=gson.toJson(reqp);
                Mlog.e(json);
                RequestHelper requestHelper=new SimplePOST().execute("uploadBoardContent",json,MainActivity.MeetiCookie);
                Mlog.e("uploadBoardContent","in line 55");
                if(requestHelper==null){
                    continue;
                }
                String res=requestHelper.getData();
                DiarySync.response resobj=gson.fromJson(res,DiarySync.response.class);
                if(resobj==null){
                    continue;
                }
                if(resobj.Code==200){
                    //String requestId,String contentid,String createdAt
                    moodboardViewModel.update(tempx.RequestId,resobj.ContentId,resobj.CreatedAt);
                }
            }else if(tempx.Mimetype.contains("image")){
                Mlog.e("uploadBoardContent","in image block");
                //param1 suburl, param2 fileaddress, param3 filename, param4 requestid, param5 public
                try{
                    String res=new UploadImage().execute("uploadImage",tempx.ImagePath,tempx.ImagePath,tempx.RequestId
                            ,"Private").get();
                    if(res==null){
                        Mlog.e("uploadBoardContent","image upload failed");
                        return;
                    }
                    ImageUploadResponse imgur=gson.fromJson(res,ImageUploadResponse.class);
                    Mlog.e("uploadBoardContent","after image upload");
                    if(imgur==null){
                        return;
                    }
                    if(imgur.Code==200){
                        Mlog.e("uploadBoardContent","image upload 200");
                        //String requestId,String imageId
                        String json=gson.toJson(new request(tempx.RequestId,tempx.UserCreatedAt,tempx.Content,imgur.ImageId,tempx.CreatedAt));
                        RequestHelper requestHelper=new SimplePOST().execute("uploadBoardContent",json,MainActivity.MeetiCookie);
                        if(requestHelper==null){
                            continue;
                        }
                        String resx=requestHelper.getData();
                        Mlog.e("moodboard syncing->",resx);
                        DiarySync.response resobj=gson.fromJson(resx,DiarySync.response.class);
                        if(resobj==null){
                            Mlog.e("moodboard syncing->","found null");
                            continue;
                        }
                        if(resobj.Code==200){
                            Mlog.e("moodboard syncing->","200 wala block ye");
                            Mlog.e(tempx.RequestId,imgur.ImageId,resobj.ContentId,resobj.CreatedAt);
                            //String requestId,String contentid,String createdAt
                            moodboardViewModel.updateimage(tempx.RequestId,imgur.ImageId);
                            moodboardViewModel.update(tempx.RequestId,resobj.ContentId,resobj.CreatedAt);
                            Mlog.e("moodboard updated211",resobj.CreatedAt);
                        }
                        Mlog.e("moodboard synced->",tempx.RequestId,imgur.ImageId);
                    }
                }catch (Exception e){

                }
            }
        }
    }
}
