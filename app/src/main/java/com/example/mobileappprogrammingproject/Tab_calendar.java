package com.example.mobileappprogrammingproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.format.DateUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Tab_calendar extends Fragment {

    private CalendarView calendarView;
    private EditText editText;
    private TextView textView;

    private CalendarEventDAO eventDAO;

    public Tab_calendar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create an instance of the CalendarEventDAO
        eventDAO = new CalendarEventDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_calendar, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        editText = view.findViewById(R.id.eventEditText);
        textView = view.findViewById(R.id.eventTextView);

        // Set a listener to the calendar view
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                String eventText = eventDAO.getEventText(date);
                editText.setText(eventText);
                textView.setText(eventText != null ? eventText : "김치찌개"); // eventText가 null인 경우 빈 문자열로 설정

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = getSelectedDate();
                String text = editText.getText().toString().trim();

                if (!text.isEmpty()) {
                    eventDAO.saveEventText(date, text);
                    Toast.makeText(getActivity(), "Event text saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Please enter event text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.updateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = getSelectedDate();
                String text = editText.getText().toString().trim();

                if (!text.isEmpty()) {
                    eventDAO.updateEventText(date, text);
                    Toast.makeText(getActivity(), "Event text updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Please enter event text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = getSelectedDate();
                eventDAO.deleteEventText(date);
                editText.setText("");
                Toast.makeText(getActivity(), "Event text deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getSelectedDate() {
        long selectedDateMillis = calendarView.getDate();
        // Convert the date in milliseconds to a string format: "YYYY-MM-DD"
        return DateUtils.formatDateTime(getActivity(), selectedDateMillis, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NUMERIC_DATE);
    }

    @Override
    public void onResume() {
        super.onResume();
        eventDAO.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        eventDAO.close();
    }

    public class CalendarEventDAO {
        private static final String DATABASE_NAME = "calendar.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_EVENTS = "events";
        private static final String COLUMN_DATE = "date";
        private static final String COLUMN_TEXT = "text";

        private DatabaseHelper databaseHelper;
        private SQLiteDatabase database;

        public CalendarEventDAO(Context context) {
            databaseHelper = new DatabaseHelper(context);
        }

        public void open() throws SQLException {
            database = databaseHelper.getWritableDatabase();
        }

        public void close() {
            databaseHelper.close();
        }

        public void saveEventText(String date, String text) {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_DATE, date);
            values.put(COLUMN_TEXT, text);

            int rowsAffected = db.update(TABLE_EVENTS, values, COLUMN_DATE + "=?", new String[]{date});

            if (rowsAffected == 0) {
                db.insert(TABLE_EVENTS, null, values);
            }
        }

        public String getEventText(String date) {
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            String[] projection = {
                    COLUMN_TEXT
            };

            String selection = COLUMN_DATE + " = ?";
            String[] selectionArgs = {date};

            Cursor cursor = db.query(
                    TABLE_EVENTS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(COLUMN_TEXT);
                if (columnIndex != -1) {
                    String eventText = cursor.getString(columnIndex);
                    cursor.close();
                    return eventText;
                }
            }

            if (cursor != null) {
                cursor.close();
            }

            return null;
        }

        public void updateEventText(String date, String newText) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TEXT, newText);
            String whereClause = COLUMN_DATE + " = ?";
            String[] whereArgs = {date};
            database.update(TABLE_EVENTS, values, whereClause, whereArgs);
        }

        public void deleteEventText(String date) {
            String whereClause = COLUMN_DATE + " = ?";
            String[] whereArgs = {date};
            database.delete(TABLE_EVENTS, whereClause, whereArgs);
        }

        private class DatabaseHelper extends SQLiteOpenHelper {

            DatabaseHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                String createTableQuery = "CREATE TABLE " + TABLE_EVENTS + " (" +
                        COLUMN_DATE + " TEXT PRIMARY KEY, " +
                        COLUMN_TEXT + " TEXT)";
                db.execSQL(createTableQuery);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
                onCreate(db);
            }
        }
    }
}