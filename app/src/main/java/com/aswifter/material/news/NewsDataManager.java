package com.aswifter.material.news;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.material.zhihu.DaoMaster;
import com.material.zhihu.DaoSession;
import com.material.zhihu.StoryRecord;
import com.material.zhihu.StoryRecordDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by erfli on 6/21/16.
 */
public class NewsDataManager {

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private StoryRecordDao storyRecordDao;
    private SQLiteDatabase sqLiteDatabase;

    private static NewsDataManager newsDataManager;

    public static NewsDataManager getInstance(Application context) {
        if (newsDataManager == null) {
            synchronized (NewsDataManager.class) {
                if (newsDataManager == null) {
                    newsDataManager = new NewsDataManager();
                    newsDataManager.init(context);
                }
            }
        }
        return newsDataManager;
    }

    private void init(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "com.material.zhihu", null);
        sqLiteDatabase = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(sqLiteDatabase);
        daoSession = daoMaster.newSession();
        storyRecordDao = daoSession.getStoryRecordDao();
    }

    public void saveStoryList(List<Story> storyList, String time) {
        storyRecordDao.insertOrReplaceInTx(convertStoryListToStoryRecordList(storyList, time));
    }

    public List<Story> getStoryList(String time) {
        QueryBuilder<StoryRecord> builder = storyRecordDao.queryBuilder();
        builder.where(StoryRecordDao.Properties.Time.eq(time));
        List<StoryRecord> storyRecords = builder.list();
        return convertStroyRecordListToStoryList(storyRecords);
    }

    public @NonNull StoryRecord getStoryRecordByStory(Story story){
        QueryBuilder<StoryRecord> queryBuilder = storyRecordDao.queryBuilder();
        queryBuilder.where(StoryRecordDao.Properties.Story_id.eq(String.valueOf(story.getId())));
        List<StoryRecord> storyRecords = queryBuilder.list();
        StoryRecord storyRecord = null;
        if(storyRecords.size() > 0){
            storyRecord = storyRecords.get(0);
        }
        return storyRecord;
    }

    public void markAsRead(Story story) {
        StoryRecord storyRecord = getStoryRecordByStory(story);
        if(storyRecord != null){
            storyRecord.setMark_read(true);
            storyRecordDao.insertOrReplace(storyRecord);
        }
    }

    private StoryRecord convertStoryToStoryRecord(Story story, String time) {
        StoryRecord storyRecord = new StoryRecord();
        storyRecord.setMark_read(false);
        storyRecord.setStory_id(String.valueOf(story.getId()));
        storyRecord.setStory_title(story.getTitle());
        storyRecord.setStroy_image_url(story.getImages().get(0));
        storyRecord.setTime(time);
        return storyRecord;
    }

    private List<StoryRecord> convertStoryListToStoryRecordList(List<Story> stories, String time) {
        List<StoryRecord> storyRecords = new ArrayList<>();
        for (Story story : stories) {
            storyRecords.add(convertStoryToStoryRecord(story, time));
        }
        return storyRecords;
    }

    private List<Story> convertStroyRecordListToStoryList(List<StoryRecord> storyRecords) {
        List<Story> stories = new ArrayList<>();
        for (StoryRecord storyRecord : storyRecords) {
            Story story = new Story();
            story.setTitle(storyRecord.getStory_title());
            story.setId(Long.valueOf(storyRecord.getStory_id()));
            List<String> images = new ArrayList<>();
            images.add(storyRecord.getStroy_image_url());
            story.setImages(images);
            stories.add(story);
        }
        return stories;
    }

    public static String getSaveTime(int page){
        String time = getDateString(new Date());
        String key = String.valueOf(Long.valueOf(time) - page);
        return key;
    }

    public static String getDateString(Date date){
        String year =(date.getYear()+1900)+"";
        String mm = (date.getMonth()+1)+"";
        if(Integer.valueOf(mm).intValue()<10){
            mm="0"+mm;
        }
        String day = date.getDate()+"";
        if(Integer.valueOf(day).intValue()<10)day="0"+day;
        return year+mm+day;
    }
}
