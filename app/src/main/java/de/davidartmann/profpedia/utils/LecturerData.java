package de.davidartmann.profpedia.utils;

import android.content.Context;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.davidartmann.profpedia.model.Lecturer;

/**
 * Created by braunpet on 09.09.15.
 */
public class LecturerData {

    private static LecturerData INSTANCE;

    public static LecturerData getInstance( Context context )
    {
        INSTANCE = new LecturerData(context);
        return INSTANCE;
    }

    /*
    public static LecturerData getInstance()
    {
        return INSTANCE;
    }
    */

    private List<Lecturer> setOfLecturers;

    private Map<Integer,Lecturer> mapIdToLecturer;

    public LecturerData( Context context )
    {
        this.setOfLecturers = new LinkedList<>( );
        this.mapIdToLecturer = new HashMap<>();
        loadResources( context );
    }

    public Lecturer getLecturerById( int id )
    {
        return this.mapIdToLecturer.get( id );
    }
    public Lecturer getLecturer(int position)
    {
        return this.setOfLecturers.get(position);
    }

    public int size()
    {
        return this.setOfLecturers.size();
    }

    public List<Lecturer> getLecturers()
    {
        List<Lecturer> returnValue = new LinkedList<>();

        returnValue.addAll(this.setOfLecturers);

        return returnValue;
    }

    public final Collection<Lecturer> findByFirstNameAndLastName( String firstName, String lastName )
    {
        Collection<Lecturer> returnValue = new LinkedList<>( );

        for ( Lecturer lecturer : this.setOfLecturers )
        {
            if ( lecturer.getFirstName( ).equalsIgnoreCase( firstName )
                    && lecturer.getLastName( ).equalsIgnoreCase( lastName ) )
            {
                returnValue.add( lecturer );
            }
        }

        return returnValue;
    }

    /**
     * Method for searchquery in the searchview of the toolbar.
     * @param query the search string
     * @return {@link Collection} of {@link Lecturer}
     */
    public Collection<Lecturer> findByLastnameOrFirstname(String query) {
        Collection<Lecturer> returnValue = new LinkedList<>( );
        for (Lecturer lecturer : this.setOfLecturers) {
            if (lecturer.getFirstName().equalsIgnoreCase(query) ||
                    lecturer.getLastName().equalsIgnoreCase(query)) {
                returnValue.add(lecturer);
            }
        }
        return returnValue;
    }

    private void loadResources( Context context )
    {
        try
        {
            final InputStream is =  context.getAssets().open("lecturers.csv");
            final InputStreamReader inputReader = new InputStreamReader( is );
            final CSVParser csvParser = new CSVParser( inputReader, CSVFormat.newFormat(';') );
            int i = 0;

            for ( CSVRecord lecturerRecord : csvParser )
            {
                readLecturer( lecturerRecord, i );
                i++;
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace( );
        }
    }

    private void readLecturer( CSVRecord record, int id )
    {
        final Lecturer lecturer = new Lecturer( );
        int column = 0;

        lecturer.setId( id );
        lecturer.setFirstName(record.get(column++));
        lecturer.setLastName(record.get(column++));
        lecturer.setTitle(record.get(column++));
        lecturer.setEmail(record.get(column++));
        lecturer.setPhone(record.get(column++));
        lecturer.setUrlWelearn(record.get(column++));
        lecturer.setUrlProfileImage(record.get(column++));
        lecturer.setPhone(record.get(column++));
        lecturer.setAddress(record.get(column++));
        lecturer.setRoomNumber(record.get(column));

        this.setOfLecturers.add( lecturer );
        this.mapIdToLecturer.put( id, lecturer );

    }
}
