package de.davidartmann.profpedia.adapter.viewholder.lecturer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.viewholder.LecturerViewholder;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * ViewHolder for the address cardview of a lecturer. Extends the {@link LecturerViewholder}.
 * Created by david on 30.12.15.
 */
public class LecturerAdressViewholder extends LecturerViewholder implements View.OnClickListener {

    private static final String TAG = LecturerAdressViewholder.class.getSimpleName();

    private ImageView imageViewAddress, imageViewRoomnumber, imageViewWelearnurl;
    private TextView textViewAddress, textViewRoomnumber, textViewWelearnurl,
            textViewHeaderDescription;
    private Context context;
    private Lecturer lecturer;

    public LecturerAdressViewholder(View itemView, Context context) {
        super(itemView, context);
        this.context = context;
        itemView.setOnClickListener(this);
        textViewHeaderDescription = (TextView) itemView
                .findViewById(
                        R.id.recyclerview_lecturer_address_cardview_textview_headerdescription);
        imageViewAddress = (ImageView) itemView
                .findViewById(R.id.recyclerview_lecturer_address_cardview_imageview_address);
        textViewAddress = (TextView) itemView
                .findViewById(R.id.recyclerview_lecturer_address_cardview_textview_address);
        imageViewRoomnumber = (ImageView) itemView
                .findViewById(R.id.recyclerview_lecturer_address_cardview_imageview_roomnumber);
        textViewRoomnumber = (TextView) itemView
                .findViewById(R.id.recyclerview_lecturer_address_cardview_textview_roomnumber);
        imageViewWelearnurl = (ImageView) itemView
                .findViewById(R.id.recyclerview_lecturer_address_cardview_imageview_welearnurl);
        textViewWelearnurl = (TextView) itemView
                .findViewById(R.id.recyclerview_lecturer_address_cardview_textview_welearnurl);
    }

    public void assignData(Lecturer model) {
        this.lecturer = model;
        textViewHeaderDescription.setText(R.string.adresse);
        imageViewAddress.setImageDrawable(context.getDrawable(R.drawable.navigation));
        textViewAddress.setText(model.getAddress());
        imageViewRoomnumber.setImageDrawable(context.getDrawable(R.drawable.room));
        textViewRoomnumber.setText(R.string.Raumnummer);
        textViewRoomnumber.append(model.getRoomNumber());
        imageViewWelearnurl.setImageDrawable(context.getDrawable(R.drawable.website));
        textViewWelearnurl.setText(R.string.besuchen_sie_die_webseite);
    }

    @Override
    public void onClick(View v) {
        if (context != null) {
            if (lecturer != null) {
                Intent intent = null;
                String chooserTitle = "";
                switch (v.getId()) {
                    case R.id.recyclerview_lecturer_address_cardview_textview_headerdescription:
                        return;
                    case R.id.recyclerview_lecturer_address_cardview_imageview_address:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+lecturer.getAddress()));
                        chooserTitle = "Navigieren mit";
                        break;
                    case R.id.recyclerview_lecturer_address_cardview_textview_address:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+lecturer.getAddress()));
                        chooserTitle = "Navigieren mit";
                        break;
                    case R.id.recyclerview_lecturer_address_cardview_imageview_roomnumber:
                        return;
                    case R.id.recyclerview_lecturer_address_cardview_textview_roomnumber:
                        return;
                    case R.id.recyclerview_lecturer_address_cardview_imageview_welearnurl:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lecturer.getUrlWelearn()));
                        chooserTitle = "Webseite öffnen mit";
                        break;
                    case R.id.recyclerview_lecturer_address_cardview_textview_welearnurl:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lecturer.getUrlWelearn()));
                        chooserTitle = "Webseite öffnen mit";
                        break;
                    default:
                        Log.w(TAG, "default path in onItemClick with view id: "+v.getId());
                }
                context.startActivity(Intent.createChooser(intent, chooserTitle));
            } else {
                Log.w(TAG, "lecturer is null");
            }
        } else {
            Log.w(TAG, "context is null");
        }
    }
}
