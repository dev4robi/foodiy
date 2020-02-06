package com.robi.foodiy.data.dao;

import java.sql.Date;
import java.sql.Time;

import lombok.Data;

@Data
public class RecordsDao {

    private long id;                // 고유 ID
    private String writeUserId;     // 기록 작성자 회원ID
    private String title;           // 제목
    private Date whenDate;          // 일자(yyyy-MM-dd)
    private Time whenTime;          // 시간(HH:mm:ss)
    private String wherePlace;      // 장소명
    private float whereLati;        // 위도
    private float whereLongi;       // 경도
    private String whoWith;         // 같이있던 사람
    private String picUrls;         // 사진URL들
    private String menuIds;         // 링크된 메뉴ID들
}