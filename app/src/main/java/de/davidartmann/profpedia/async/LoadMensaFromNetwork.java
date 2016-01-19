package de.davidartmann.profpedia.async;

import android.os.AsyncTask;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import de.davidartmann.profpedia.fragment.mensa.contract.IProgressBar;
import de.davidartmann.profpedia.model.Lecturer;
import de.davidartmann.profpedia.model.MensaMeal;

/**
 * AsyncTask for the fetching of data from the mensa API.
 * Created by david on 16.01.16.
 */
public class LoadMensaFromNetwork extends AsyncTask<String, Integer, List<MensaMeal>> {

    private IGetMensaDataFromNetwork iGetMensaDataFromNetwork;
    private IProgressBar iProgressBar;

    public LoadMensaFromNetwork(IGetMensaDataFromNetwork iGetMensaDataFromNetwork,
                                IProgressBar iProgressBar) {
        this.iGetMensaDataFromNetwork = iGetMensaDataFromNetwork;
        this.iProgressBar = iProgressBar;
    }

    @Override
    protected List<MensaMeal> doInBackground(String... params) {
        iProgressBar.showProgressBar(true);
        HttpURLConnection httpURLConnection = null;
        URL url = null;
        try {
            url = new URL(params[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            Genson genson = new Genson();
            return genson.deserialize(httpURLConnection.getInputStream(),
                    new GenericType<List<MensaMeal>>() {});
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<MensaMeal> mensaMeals) {
        super.onPostExecute(mensaMeals);
        iGetMensaDataFromNetwork.fetchMensaData(mensaMeals);
        iProgressBar.showProgressBar(false);
    }

    public interface IGetMensaDataFromNetwork {
        void fetchMensaData(List<MensaMeal> mensaMeals);
    }
}
