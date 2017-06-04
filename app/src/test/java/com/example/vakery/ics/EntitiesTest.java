package com.example.vakery.ics;


import com.example.vakery.ics.Application.ListAdapters.LecturersListAdapter;
import com.example.vakery.ics.Domain.Entities.ICSSubject;
import com.example.vakery.ics.Domain.Entities.Lecturer;
import com.example.vakery.ics.Domain.Entities.Mark;
import com.example.vakery.ics.Domain.Entities.Notification;
import com.example.vakery.ics.Domain.Entities.Schedule;
import com.example.vakery.ics.Domain.Entities.Subject;
import com.example.vakery.ics.Domain.Entities.SubjectForScheduleList;
import com.example.vakery.ics.Domain.Entities.SubjectForSubjectsList;
import com.example.vakery.ics.Domain.Entities.Time;
import com.example.vakery.ics.Domain.Entities.TimeSchedule;
import com.example.vakery.ics.Domain.Entities.Visiting;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EntitiesTest {

    ICSSubject icsSubject  = new ICSSubject();
    Lecturer lecturer = new Lecturer();
    Mark mark = new Mark();
    Notification notification = new Notification();
    Schedule schedule = new Schedule();
    Subject subject = new Subject();
    SubjectForScheduleList subjectForScheduleList = new SubjectForScheduleList();
    SubjectForSubjectsList subjectForSubjectsList = new SubjectForSubjectsList();
    Time time = new Time();
    TimeSchedule timeSchedule = new TimeSchedule();
    Visiting visiting = new Visiting();

    @Test
    public void icsSubjectTestMethodsCall(){
        icsSubject.setmExtraInfo("123");
        assertEquals("123",icsSubject.getmExtraInfo());
    }

    @Test
    public void lecturerTestMethodsCall(){
        lecturer.setmName("123");
        assertEquals("123",lecturer.getmName());
    }

    @Test
    public void markTestMethodsCall(){
        mark.setmChapter1(999);
        assertEquals(999,mark.getmChapter1());
    }

    @Test
    public void notificationTestMethodsCall(){
        notification.setmContent("text");
        assertEquals("text",notification.getmContent());
    }

    @Test
    public void scheduleTestMethodsCall(){
        schedule.setmKindOfWeek(66);
        assertEquals(66,schedule.getmKindOfWeek());
    }

    @Test
    public void subjectTestMethodsCall(){
        subject.setmFullTitle("title");
        assertEquals("title",subject.getmFullTitle());
    }

    @Test
    public void subjectForScheduleListTestMethodsCall(){
        subjectForScheduleList.setmName("name");
        assertEquals("name",subjectForScheduleList.getmName());
    }

    @Test
    public void subjectForSubjectsListTestMethodsCall(){
        subjectForSubjectsList.setmName("name");
        assertEquals("name",subjectForSubjectsList.getmName());
    }

    @Test
    public void timeTestMethodsCall(){
        time.setmNumberOfSubject(6);
        assertEquals(6,time.getmNumberOfSubject());
    }

    @Test
    public void timeScheduleTestMethodsCall(){
        timeSchedule.setmStart("123");
        assertEquals("123",timeSchedule.getmStart());
    }

    @Test
    public void visitingTestMethodsCall(){
        visiting.setmNumberOfdays(999);
        assertEquals(999,visiting.getmNumberOfdays());
    }
}
