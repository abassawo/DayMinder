package abassawo.c4q.nyc.dayminder;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c4q-Abass on 7/19/15.
 */
public class JSONParser {
    private Context mContext;
    private String mFileName;

    public JSONParser(Context c, String f) {
        mContext = c;
        mFileName = f;
    }


    private String readStream(InputStream in) throws IOException {
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(in, "UTF8");
        StringWriter writer = new StringWriter();
        int n;
        while ((n = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, n);
        }
        return writer.toString();
    }

    public void saveNotes(ArrayList<Note> notes)
            throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (Note c : notes)
            array.put(c.toJSON());

        // Write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }

    }

    public ArrayList<JLabel> loadLabels()throws IOException, JSONException {
        ArrayList<JLabel> labels = new ArrayList<JLabel>();
        BufferedReader reader = null;
        try {
            // Open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //Build the array of notes from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                labels.add(new JLabel(array.getJSONObject(i).toString()));
            }
        } catch (FileNotFoundException e) {
            // Ignore this one; it happens when starting fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return labels;
    }


    public ArrayList<Note> loadNotes() throws IOException, JSONException {
        ArrayList<Note> notes = new ArrayList<Note>();
        BufferedReader reader = null;
        try {
            // Open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //Build the array of notes from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                notes.add(new Note(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // Ignore this one; it happens when starting fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return notes;
    }
}

