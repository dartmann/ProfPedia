package de.davidartmann.profpedia.adapter.mensa.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.activity.contract.OnFoodClickListener;
import de.davidartmann.profpedia.model.MensaMeal;

/**
 * Viewholder for the mensa food list.
 * Created by david on 17.01.16.
 */
public class MensaListViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = MensaListViewHolder.class.getSimpleName();

    private ImageView imageViewFoodImage;
    private TextView textViewFoodName, textViewPriceStudent;
    private Context context;
    private MensaMeal mensaMeal;

    public MensaListViewHolder(View itemView, Context context,
                               final OnFoodClickListener onFoodClickListener) {
        super(itemView);
        imageViewFoodImage = (ImageView) itemView.findViewById(R.id.cardview_mensa_meal_imageview);
        textViewFoodName =
                (TextView) itemView.findViewById(R.id.cardview_mensa_meal_textview_foodname);
        textViewPriceStudent =
                (TextView) itemView.findViewById(R.id.cardview_mensa_meal_textview_pricestudent);
        this.context = context;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mensaMeal != null) {
                    onFoodClickListener.onClick(mensaMeal);
                } else {
                    Log.d(TAG, "mensaMeal was null");
                }
            }
        });
    }

    public void assignData(MensaMeal mensaMeal) {
        this.mensaMeal = mensaMeal;
        Drawable drawable;
        switch (mensaMeal.getFoodtype()) {
            // normal food
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
                drawable = context.getDrawable(R.drawable.pig_ham);
                break;
            case "L":
                drawable = context.getDrawable(R.drawable.lamb);
                break;
            case "W":
                drawable = context.getDrawable(R.drawable.deer);
                break;
            // alkohol
            case "S/A":
                drawable = context.getDrawable(R.drawable.pig_alk);
                break;
            case "G/A":
                drawable = context.getDrawable(R.drawable.chicken_alk);
                break;
            case "R/A":
                drawable = context.getDrawable(R.drawable.cow_alk);
                break;
            case "K/A":
                drawable = context.getDrawable(R.drawable.cow_alk);
                break;
            case "F/A":
                drawable = context.getDrawable(R.drawable.fish_alk);
                break;
            case "FL/A":
                drawable = context.getDrawable(R.drawable.vegetarian_alk);
                break;
            case "V/A":
                drawable = context.getDrawable(R.drawable.vegan_alk);
                break;
            case "VS/A":
                drawable = context.getDrawable(R.drawable.pig_ham_alk);
                break;
            case "L/A":
                drawable = context.getDrawable(R.drawable.lamb_alk);
                break;
            case "W/A":
                drawable = context.getDrawable(R.drawable.deer_alk);
                break;
            // mixed food
            case "F/G":
                drawable = context.getDrawable(R.drawable.fish_chicken);
                break;
            case "R/S":
                drawable = context.getDrawable(R.drawable.cow_pig);
                break;
            case "S/R":
                drawable = context.getDrawable(R.drawable.cow_pig);
                break;
            case "S/VS":
                drawable = context.getDrawable(R.drawable.pig_pig_ham);
                break;
            case "VS/S":
                drawable = context.getDrawable(R.drawable.pig_pig_ham);
                break;
            default:
                // there could be meals without a foodtype or unkown
                drawable = context.getDrawable(R.drawable.food);
        }
        //Picasso.with(context).load(mensaMeal.getImageUrl()).into(imageViewFoodImage);
        imageViewFoodImage.setImageDrawable(drawable);
        //imageViewFoodImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        textViewFoodName.setText(mensaMeal.getName());
        //TODO: student price bold?!
        textViewPriceStudent.setText(String.format("%s%s%s%s%s%s%s",
                "Stud.: ",
                mensaMeal.getPrice(),
                " € | Bed.: ",
                mensaMeal.getPricebed(),
                " € | Gast: ",
                mensaMeal.getPriceguest(),
                " €"));
    }
}
