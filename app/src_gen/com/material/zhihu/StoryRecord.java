package com.material.zhihu;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "STORY_RECORD".
 */
public class StoryRecord {

    private String time;
    private String story_id;
    private String story_title;
    private String stroy_image_url;
    private Boolean mark_read;

    public StoryRecord() {
    }

    public StoryRecord(String story_id) {
        this.story_id = story_id;
    }

    public StoryRecord(String time, String story_id, String story_title, String stroy_image_url, Boolean mark_read) {
        this.time = time;
        this.story_id = story_id;
        this.story_title = story_title;
        this.stroy_image_url = stroy_image_url;
        this.mark_read = mark_read;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStory_id() {
        return story_id;
    }

    public void setStory_id(String story_id) {
        this.story_id = story_id;
    }

    public String getStory_title() {
        return story_title;
    }

    public void setStory_title(String story_title) {
        this.story_title = story_title;
    }

    public String getStroy_image_url() {
        return stroy_image_url;
    }

    public void setStroy_image_url(String stroy_image_url) {
        this.stroy_image_url = stroy_image_url;
    }

    public Boolean getMark_read() {
        return mark_read;
    }

    public void setMark_read(Boolean mark_read) {
        this.mark_read = mark_read;
    }

}
