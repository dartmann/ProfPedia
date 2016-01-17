package de.davidartmann.profpedia.adapter.mensa.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.model.MensaMeal;

/**
 * Viewholder for the mensa food list.
 * Created by david on 17.01.16.
 */
public class MensaListViewHolder extends RecyclerView.ViewHolder {

    //TODO: interface for click listening + implement clicklistener

    private ImageView imageViewFoodImage;
    private TextView textViewFoodName, textViewPriceStudent;
    private MensaMeal mensaMeal;
    private Context context;

    public MensaListViewHolder(View itemView, Context context) {
        super(itemView);
        imageViewFoodImage = (ImageView) itemView.findViewById(R.id.cardview_mensa_meal_imageview);
        textViewFoodName =
                (TextView) itemView.findViewById(R.id.cardview_mensa_meal_textview_foodname);
        textViewPriceStudent =
                (TextView) itemView.findViewById(R.id.cardview_mensa_meal_textview_pricestudent);
        this.context = context;
    }

    public void assignData(MensaMeal mensaMeal) {
        this.mensaMeal = mensaMeal;
        Drawable drawable;
        switch (mensaMeal.getFoodtype()) {
            case "S":
                drawable = context.getDrawable(R.drawable.pig);
                break;
            case "G":
                drawable = context.getDrawable(R.drawable.chicken);
                break;
            case "R":
                drawable = context.getDrawable(R.drawable.cow);
                break;
            case "K":
                drawable = context.getDrawable(R.drawable.cow);
                break;
            case "F":
                drawable = context.getDrawable(R.drawable.fish);
                break;
            case "FL":
                drawable = context.getDrawable(R.drawable.vegetarian);
                break;
            case "V":
                drawable = context.getDrawable(R.drawable.vegan);
                break;
            case "A":
                drawable = context.getDrawable(R.drawable.alkohol);
                break;
            case "VS":
                drawable = context.getDrawable(R.drawable.pig);
                break;
            case "L":
                drawable = context.getDrawable(R.drawable.lamb);
                break;
            case "W":
                drawable = context.getDrawable(R.drawable.deer);
                break;
            default:
                // there are several meals without a foodtype in the backend...
                drawable = context.getDrawable(R.drawable.food);
        }
        //Picasso.with(context).load(mensaMeal.getImageUrl()).into(imageViewFoodImage);
        imageViewFoodImage.setImageDrawable(drawable);
        //imageViewFoodImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        textViewFoodName.setText(mensaMeal.getName());
        textViewPriceStudent.setText(mensaMeal.getPrice());
        textViewPriceStudent.append(" €");
    }

}
