package de.davidartmann.profpedia.activity.contract;

import de.davidartmann.profpedia.model.MensaMeal;

/**
 * Interface for the event, when a viewholder of a mensa food is clicked.
 * Created by david on 18.01.16.
 */
public interface OnFoodClickListener {

    void onClick(MensaMeal mensaMeal);
}
