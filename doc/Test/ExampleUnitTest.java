package com.example.thuyvynguyen.my_friend_list_project;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    ArrayList<String> listCase = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_1(){
        listCase.add("Check day");
        int day = MainActivity.date.getDay();
        assertEquals(this.day, day);
    }

    @Test
    public void test_2(){
        listCase.add("Check month");
        int month = MainActivity.date.getMonth();
        assertEquals(this.month, month -1);//Because when display, must +1 the month
    }

    @Test
    public void test_3(){
        listCase.add("Check year");
        int year = MainActivity.date.getYear();
        assertEquals(this.year, year);
    }


}
