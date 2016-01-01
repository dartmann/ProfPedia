package de.davidartmann.profpedia.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Root ViewHolder for the {@link }
 * Created by david on 30.12.15.
 */
public class LecturerViewholder extends RecyclerView.ViewHolder/* implements View.OnClickListener*/ {

    /*
    private static final String TAG = LecturerViewholder.class.getSimpleName();

    private ImageView imageView;
    private TextView textView;
    private Context context;
    private Lecturer lecturer;
    */

    public LecturerViewholder(View itemView, Context context) {
        super(itemView);
        //this.context = context;
        //itemView.setOnClickListener(this);
        //imageView = (ImageView) itemView.findViewById(R.id.recyclerview_lecturer_detail_imageview);
        //textView = (TextView) itemView.findViewById(R.id.recyclerview_lecturer_detail_textview);
    }

    /*
    public void assignData(Lecturer model, int position) {
        this.lecturer = model;
        switch (position) {
            case 0:
                imageView.setImageDrawable(context.getDrawable(R.drawable.email));
                textView.setText(model.getEmail());
                break;
            case 1:
                imageView.setImageDrawable(context.getDrawable(R.drawable.call));
                textView.setText(model.getPhone());
                break;
            case 2:
                imageView.setImageDrawable(context.getDrawable(R.drawable.navigation));
                textView.setText(model.getAddress());
                break;
            case 3:
                imageView.setImageDrawable(context.getDrawable(R.drawable.room));
                textView.setText(String.format("%s: %s",
                        context.getString(R.string.Raumnummer),
                        model.getRoomNumber()));
                break;
            case 4:
                imageView.setImageDrawable(context.getDrawable(R.drawable.website));
                textView.setText(R.string.besuchen_sie_die_webseite);
                break;
            default:
                Log.w(TAG, "default path in assignData");
        }
    }
    */

    /*
    @Override
    public void onClick(View v) {
        if (lecturer != null) {
            Intent intent = null;
            String chooserTitle = "";
            switch (getAdapterPosition()) {
                case 0:
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, lecturer.getEmail());
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    chooserTitle = "Email versenden mit";
                    break;
                case 1:
                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + lecturer.getPhone().trim()));
                    if (ActivityCompat.checkSelfPermission(
                            context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    chooserTitle = "Anrufen mit";
                    break;
                case 2:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+lecturer.getAddress()));
                    chooserTitle = "Navigieren mit";
                    break;
                case 3:
                    return;
                case 4:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lecturer.getUrlWelearn()));
                    chooserTitle = "Webseite öffnen mit";
                    break;
                default:
                    Log.w(TAG, "default path in onItemClick with position: "+getAdapterPosition());
            }
            context.startActivity(Intent.createChooser(intent, chooserTitle));
        }
    }
    */
}
