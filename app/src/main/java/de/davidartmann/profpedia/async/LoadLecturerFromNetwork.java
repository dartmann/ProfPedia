package de.davidartmann.profpedia.async;

import android.os.AsyncTask;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.davidartmann.profpedia.fragment.lecturer.LecturerListFragment;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Asynctask for the Lecturerlist to load the data from the network.
 * Created by david on 09.01.16.
 */
public class LoadLecturerFromNetwork extends AsyncTask<String, Void, List<Lecturer>> {

    private IGetLecturerDataFromNetwork iGetLecturerDataFromNetwork;
    private HttpURLConnection httpURLConnection;
    private LecturerListFragment.IProgressBar iProgressBar;

    public LoadLecturerFromNetwork(IGetLecturerDataFromNetwork i,
                                   LecturerListFragment.IProgressBar iProgressBar) {
        this.iGetLecturerDataFromNetwork = i;
        this.iProgressBar = iProgressBar;
    }

    @Override
    protected List<Lecturer> doInBackground(String... strings) {
        iProgressBar.showProgressBarForLecturerList(true);
        httpURLConnection = null;
        try {
            URL url = new URL(strings[0]);
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
        String nextUrl = checkIfMoreDataInBackend(httpURLConnection.getHeaderFields());
        int totalNumberOfDataInBackend =
                getTotalNumberOfDataSetsInBackend(httpURLConnection.getHeaderFields());
        iGetLecturerDataFromNetwork.fetchLecturers(lecturers, totalNumberOfDataInBackend, nextUrl);
        iProgressBar.showProgressBarForLecturerList(false);
    }

    private String checkIfMoreDataInBackend(Map<String, List<String>> headerFields) {
        Set<Map.Entry<String, List<String>>> entries = headerFields.entrySet();
        for (Map.Entry<String, List<String>> e : entries) {
            if (e.getKey() != null) {
                if (e.getKey().equals("Link")) {
                    for (String value : e.getValue()) {
                        if (value != null) {
                            if (value.toLowerCase().contains("rel=\"next\"")) {
                                return value.substring(value.indexOf("<")+1, value.indexOf(">"));
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    private int getTotalNumberOfDataSetsInBackend(Map<String, List<String>> headerFields) {
        Set<Map.Entry<String, List<String>>> entries = headerFields.entrySet();
        //Set<String> keys = headerFields.keySet();
        for (Map.Entry<String, List<String>> e : entries) {
            if (e.getKey() != null) {
                if (e.getKey().equals("X-totalnumberofresults")) {
                    for (String value : e.getValue()) {
                        if (value != null) {
                            return Integer.valueOf(value);
                        }
                    }
                }
            }
        }
        return 0;
    }

    public interface IGetLecturerDataFromNetwork {
        void fetchLecturers(List<Lecturer> lecturers, int numberOfBackenData, String nextUrl);
    }
}
