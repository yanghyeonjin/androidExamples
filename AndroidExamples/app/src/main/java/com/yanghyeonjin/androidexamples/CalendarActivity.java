package com.yanghyeonjin.androidexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.OrientationHelper;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private CalendarView calendarView;
    private RadioGroup rgSelectionType;
    private Toolbar tbCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        /* 아이디 연결 */
        tbCalendar = findViewById(R.id.tb_calendar);
        setSupportActionBar(tbCalendar);

        initViews();
    }

    private void initViews() {

        /* 아이디 연결 */
        calendarView = findViewById(R.id.calendar_view);
        rgSelectionType = findViewById(R.id.rg_selection_type);


        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        rgSelectionType.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calendar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_selections:
                clearSelectionsMenuClick();
                return true;

            case R.id.show_selections:
                List<Calendar> days = calendarView.getSelectedDates();

                StringBuilder result = new StringBuilder();
                for (int i = 0; i < days.size(); i++) {
                    Calendar calendar = days.get(i);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                    final int month = calendar.get(Calendar.MONTH);
                    final int year = calendar.get(Calendar.YEAR);
                    String week = new SimpleDateFormat("EE", Locale.KOREA).format(calendar.getTime());
                    String day_full = year + "년 " + (month + 1) + "월 " + day + "일 " + week + "요일";
                    result.append(day_full).append("\n");
                }

                Toast.makeText(CalendarActivity.this, result, Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearSelectionsMenuClick() {
        calendarView.clearSelections();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        clearSelectionsMenuClick();

        switch (i) {
            case R.id.rb_single:
                calendarView.setSelectionType(SelectionType.SINGLE);
                break;

            case R.id.rb_multiple:
                calendarView.setSelectionType(SelectionType.MULTIPLE);
                break;

            case R.id.rb_range:
                calendarView.setSelectionType(SelectionType.RANGE);

            case R.id.rb_none:
                calendarView.setSelectionType(SelectionType.NONE);
                break;
        }
    }
}
