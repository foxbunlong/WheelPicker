package com.aigestudio.wheelpicker.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author AigeStudio 2015-12-06
 * @author AigeStudio 2016-07-08
 */
public class PreviewActivity extends Activity implements WheelPicker.OnItemSelectedListener, View.OnClickListener {

    private WheelPicker wheelLeft;
    private WheelPicker wheelCenter;
    private WheelPicker wheelRight;

    private Button gotoBtn;
    private Integer gotoBtnItemIndex;

    private int currentDay = 0;
    private int currentMonth = 0;
    private int currentYear = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_preview);

        wheelLeft = (WheelPicker) findViewById(R.id.main_wheel_left);
        // Populate data for Year
        int YEAR_COUNT = 50;
        List<String> yearList = new ArrayList<>();
        for (int i = 0; i < YEAR_COUNT; i++) {
            yearList.add((Calendar.getInstance().get(Calendar.YEAR) - i) + "年");
        }
        wheelLeft.setData(yearList);
        wheelLeft.setOnItemSelectedListener(this);
        currentYear = Calendar.getInstance().get(Calendar.YEAR);

        wheelCenter = (WheelPicker) findViewById(R.id.main_wheel_center);
        // Populate data for Month
        int MONTH_COUNT = 12;
        List<String> monthList = new ArrayList<>();
        for (int i = 0; i < MONTH_COUNT; i++) {
            int currentMonth = (Calendar.getInstance().get(Calendar.MONTH) - i + 1);
            if (currentMonth < 0) {
                currentMonth = 12 + currentMonth;
            }
            if (currentMonth != 0) {
                monthList.add(currentMonth + "月");
            } else {
                monthList.add(12 + "月");
            }
        }
        wheelCenter.setData(monthList);
        wheelCenter.setOnItemSelectedListener(this);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);

        wheelRight = (WheelPicker) findViewById(R.id.main_wheel_right);
        populateDayData(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH)); // Today month
        wheelRight.setOnItemSelectedListener(this);
        currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        gotoBtn = (Button) findViewById(R.id.goto_btn);
        randomlySetGotoBtnIndex();
        gotoBtn.setOnClickListener(this);
    }

    private void populateDayData(int year, int month) {
        // Populate data for Day
        Calendar myCal = new GregorianCalendar(year, month, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        int daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<String> dayList = new ArrayList<>();
        for (int i = 0; i < daysInMonth; i++) {
            int currentDay = (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - i);
            if (currentDay < 0) {
                currentDay = daysInMonth + currentDay;
            }
            if (currentDay != 0) {
                dayList.add(currentDay + "日");
            } else {
                dayList.add(daysInMonth + "日");
            }
        }
        wheelRight.setData(dayList);
    }

    private void randomlySetGotoBtnIndex() {
        gotoBtnItemIndex = (int) (Math.random() * wheelCenter.getData().size());
        gotoBtn.setText("Goto '" + wheelCenter.getData().get(gotoBtnItemIndex) + "'");
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        String text = "";
        switch (picker.getId()) {
            case R.id.main_wheel_left:
                text = "Left:";
                // Year
                currentYear = Integer.parseInt(String.valueOf(data).replace("年", ""));
                break;
            case R.id.main_wheel_center:
                text = "Center:";
                // Month
                currentMonth = Integer.parseInt(String.valueOf(data).replace("月", "")) - 1;
                populateDayData(currentYear, currentMonth);
                break;
            case R.id.main_wheel_right:
                text = "Right:";
                // Day
                currentDay = Integer.parseInt(String.valueOf(data).replace("日", ""));
                break;
        }
        Log.d("AAAAAAAAAAAAAAAA", currentYear + "/" + (currentMonth + 1) + "/" + currentDay);
    }

    @Override
    public void onClick(View v) {
        wheelCenter.setSelectedItemPosition(gotoBtnItemIndex);
        randomlySetGotoBtnIndex();
    }

}