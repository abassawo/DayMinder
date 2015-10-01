package abassawo.c4q.nyc.dayminder.Fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;
import abassawo.c4q.nyc.dayminder.R;

/**
 * Created by c4q-Abass on 9/17/15.
 */
public class CalendarFragment extends Fragment {
    private CalendarUtil calUtil;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month_view, container, false);
        new AsyncCal().execute();
        return view;
    }



    public class AsyncCal extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            calUtil =  new CalendarUtil();
            calUtil.fetchCalendars();
            calUtil.fetchEvents(new Date());
            calUtil.insertEvent("Testing");
           // calUtil.updateEvent(255);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            calUtil.insertEvent("testing something");
            super.onPostExecute(aVoid);
        }

    }

    private class CalendarUtil{


            public void fetchCalendars(){
                Uri uri = CalendarContract.Calendars.CONTENT_URI;
                String[] columns = new String[]{
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                        CalendarContract.Calendars.CALENDAR_COLOR_KEY};

                Cursor cursor = getActivity().getContentResolver().
                        query(uri, columns, CalendarContract.Calendars.ACCOUNT_NAME + " = ?", new String[]{"abass.bayo@gmail.com"}, null);
                // = ? is for filtering. it means gimme only the calendars associated with that email

                ContentResolver cr = getActivity().getContentResolver();


                while(cursor.moveToNext()){
                    long id = cursor.getLong(cursor.getColumnIndex(CalendarContract.Calendars._ID));
                    String accountName = cursor.getString(1);
                    String displayName = cursor.getString(2);
                    String owner = cursor.getString(3);
                    Log.v("contentProvider", "ID: " + id +
                            "accountName" + accountName +
                            "displayName" + displayName +
                            "owner" + owner);


                }
            }

            public void fetchEvents(Date date){
                //fixme - set this so the events returned are the ones that match date in parameter.
                Uri uri = CalendarContract.Events.CONTENT_URI;
                String[] columns = new String[] {
                        CalendarContract.Events._ID,
                        CalendarContract.Events.CALENDAR_ID,
                        CalendarContract.Events.ORGANIZER,
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.EVENT_LOCATION,
                        CalendarContract.Events.DESCRIPTION,
                        CalendarContract.Events.DTSTART,
                        CalendarContract.Events.DTEND,
                };

                String filter = CalendarContract.Events.CALENDAR_ID + " = ?";
                String[] filterArgs = new String[]{"5"}; //PUT FILTERARGS #5INTO A CONSTANT.


                String sortOrder = CalendarContract.Events.DTSTART + " DESC";

                Cursor cursor = getActivity().getContentResolver().query(uri, columns, filter, filterArgs, sortOrder);

            }

            public void insertEvent(String title){
                Calendar startTime = Calendar.getInstance();
                startTime.set(2015, Calendar.JULY, 1, 22, 0);
                long startMillis = startTime.getTimeInMillis();

                Calendar endTime = Calendar.getInstance();
                long endMillis = endTime.getTimeInMillis();


                ContentValues values = new ContentValues();
                //
                values.put(CalendarContract.Events.TITLE, title);
                values.put(CalendarContract.Events.DESCRIPTION, "additional test");
                values.put(CalendarContract.Events.DTSTART, startMillis);
                values.put(CalendarContract.Events.DTEND, endMillis);
                values.put(CalendarContract.Events.CALENDAR_ID, 5);
                values.put(CalendarContract.Events.EVENT_LOCATION, "LIC");
                values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/New York");
                Uri uri = getActivity().getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
            }

            public void updateEvent(long id){
                ContentValues values = new ContentValues();
                Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);

            }
        }


}
