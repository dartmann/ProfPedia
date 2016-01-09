package de.davidartmann.profpedia.async;

import android.content.Context;
import android.os.AsyncTask;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import de.davidartmann.profpedia.model.Lecturer;

/**
 * Asynctask for the Lecturerlist to load the data from the network.
 * Created by david on 09.01.16.
 */
public class LoadLecturerFromNetwork extends AsyncTask<Void, Void, List<Lecturer>> {

    private IGetLecturerDataFromNetwork iGetLecturerDataFromNetwork;
    private Context context;

    public LoadLecturerFromNetwork(IGetLecturerDataFromNetwork i, Context c) {
        //this.IGetLecturerDataFromNetwork = i;
        iGetLecturerDataFromNetwork = (IGetLecturerDataFromNetwork) context;
        this.context = c;
    }

    @Override
    protected List<Lecturer> doInBackground(Void... voids) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL("http://193.175.31.146:8080/fiwincoming/api/lecturers?size=10");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            Genson genson = new Genson();
            return genson.deserialize(httpURLConnection.getInputStream(),
                    new GenericType<List<Lecturer>>() {});
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<Lecturer> lecturers) {
        super.onPostExecute(lecturers);
        //Genson genson = new Genson();
        //List<Lecturer> lecturers = genson.deserialize(inputStream, new GenericType<List<Lecturer>>() {});
        iGetLecturerDataFromNetwork.getLecturers(lecturers);
    }

    public interface IGetLecturerDataFromNetwork {
        void getLecturers(List<Lecturer> lecturers);
    }
}
