package com.fourB.library.StudyRoom;

import androidx.annotation.Nullable;

public class    StudyRoomItem {
    private int studyRoomId;
    private String studyRoomName;

    public StudyRoomItem(int studyRoomId, String studyRoomName) {
        this.studyRoomId = studyRoomId;
        this.studyRoomName = studyRoomName;
    }

    public boolean equals(@Nullable StudyRoomItem obj) {
        return this.studyRoomId == obj.getStudyRoomId();
    }

    public String getStudyRoomName() {
        return studyRoomName;
    }

    public int getStudyRoomId() {
        return studyRoomId;
    }
}
