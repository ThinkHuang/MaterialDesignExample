package com.aswifter.material.news;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.aswifter.material.MaterialApplication;
import com.aswifter.material.common.AppClient;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by erfli on 6/15/16.
 */
public class NewsSupplier implements Supplier<Result<List<Story>>> {

    private String newsKey = "latest";
    @NonNull
    @Override
    public Result<List<Story>> get() {
        List<Story> stories = getStoryList();
        if(stories == null){
            return Result.failure();
        }else{
            return Result.success(stories);
        }
    }

    private List<Story>getStoryList(){
        try {
            List<Story> stories = null;
            String saveKey;
            if(TextUtils.isEmpty(newsKey)){
                saveKey = NewsDataManager.getDateString(new Date());
                stories =  AppClient.httpService.getLatestNews().execute().body().getStories();
            }else{
                saveKey = String.valueOf(Long.valueOf(newsKey)-1);
                stories = NewsDataManager.getInstance(MaterialApplication.application)
                        .getStoryList(saveKey);
                if(stories != null && stories.size() > 0){
                    return stories;
                }
                stories =  AppClient.httpService.getHistoryNews(newsKey).execute().body().getStories();
            }
            NewsDataManager.getInstance(MaterialApplication.application).saveStoryList(stories, saveKey);
            return stories;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setNewsKey(@NonNull String key){
        if(TextUtils.isEmpty(key)){
            newsKey = "";
        }else {
            newsKey = key;
        }
    }
}
