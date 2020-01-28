package com.miti.meeti.MitiExecutors.MitiRunnables.CleanJobs;

import com.miti.meeti.MainActivity;
import com.miti.meeti.database.DatabaseInit;
import com.miti.meeti.mitiutil.Logging.Mlog;

public class FeedClean implements Runnable{
    @Override
    public void run() {
        DatabaseInit databaseInit=DatabaseInit.getInstance(MainActivity.MainActivityContext);
        Integer temp=databaseInit.feedDbDao().getCount();
        if(temp>30){
            databaseInit.feedDbDao().deletelastx(temp-30);
        }
        Mlog.e("inFeedClean",temp.toString());
    }
}
