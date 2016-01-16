package de.davidartmann.profpedia.model;

/**
 * Model class for a meal.
 * Similar to the JSON response from the mensa api, but only with relevant fields for this app.
 * Created by david on 16.01.16.

 */
public class MensaMeal {

    private String name;
    private String date;
    private String price;
    private String pricebed;
    private String priceguest;
    private String foodtype;
    private String additivenumbers;

    /**
     * @return name of food.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of food.
     * @param name name of food.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the date to start of day.
     * Like 01.01.1970 00:00:00.
     * @return date of food.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date of food.
     * @param date date of food.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets price of food for a student.
     * @return price of food.
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets price of food for a student.
     * @param price price of food.
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Gets price of food for a guest.
     * @return price of food.
     */
    public String getPriceguest() {
        return priceguest;
    }

    /**
     * Sets price of food for a guest.
     * @param priceguest price of food.
     */
    public void setPriceguest(String priceguest) {
        this.priceguest = priceguest;
    }

    /**
     * Gets appended string of all foodtypes the food has. Known types are:
     *     <ul>
     *         <li>S = Schwein</li>
     *         <li>G = Geflügel</li>
     *         <li>R = Rind</li>
     *         <li>K = Kalb</li>
     *         <li>F = Fisch</li>
     *         <li>FL = Fleischlos</li>
     *         <li>V = Vegan</li>
     *         <li>A = Alkohol</li>
     *         <li>VS = Vorderschinken</li>
     *         <li>(?)L = Lamm (not seen in menu, so letter is doubtful!)</li>
     *         <li>(?)W = Wild (not seen in menu, so letter is doubtful!)</li>
     *     </ul>
     * @return appended string of all foodtypes the food has.
     */
    public String getFoodtype() {
        return foodtype;
    }

    /**
     * Sets appended string of all foodtypes the food has. Known types are:
     *     <ul>
     *         <li>S = Schwein</li>
     *         <li>G = Geflügel</li>
     *         <li>R = Rind</li>
     *         <li>K = Kalb</li>
     *         <li>F = Fisch</li>
     *         <li>FL = Fleischlos</li>
     *         <li>V = Vegan</li>
     *         <li>A = Alkohol</li>
     *         <li>VS = Vorderschinken</li>
     *         <li>(?)L = Lamm (not seen in menu, so letter is doubtful!)</li>
     *         <li>(?)W = Wild (not seen in menu, so letter is doubtful!)</li>
     *     </ul>
     * @param foodtype appended string of all foodtypes the food has.
     */
    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    /**
     * Gets appended string of all additive numbers (allergies etc.) the food has. Known types are:
     * <ul>
     *     <li>A = Alkohol</li>
     *     <li>A1 = glutenhaltiges Getreide</li>
     *     <li>A2 = Krebstiere</li>
     *     <li>A3 = Eier</li>
     *     <li>A4 = Fische</li>
     *     <li>A5 = Erdnüsse</li>
     *     <li>A6 = Soja</li>
     *     <li>A7 = Milch (einschließlich Lactose)</li>
     *     <li>A8 = Schalenfrüchte</li>
     *     <li>A9 = Sellerie</li>
     *     <li>A10 = Senf</li>
     *     <li>A11 = Sesamsamen</li>
     *     <li>A12 = Schwefeldioxid und Sulfite</li>
     *     <li>A13 = ? (not seen in menu, so unknown!)</li>
     *     <li>A14 = Weichtiere</li>
     *     <li>A15 = Weizen</li>
     *     <li>A16 = Roggen</li>
     *     <li>A17 = Gerste</li>
     *     <li>A18 = ? (not seen in menu, so unknown!)</li>
     *     <li>A19 = Mandeln</li>
     *     <li>F = Fisch</li>
     *     <li>K = Knoblauch</li>
     *     <li>G = Geflügel</li>
     *     <li>R = Rind/Kalb</li>
     *     <li>S = Schwein</li>
     *     <li>Z1 = Farbstoff</li>
     *     <li>Z2 = konserviert</li>
     *     <li>Z3 = Antioxidationsmittel</li>
     *     <li>Z4 = Geschmacksverstärker</li>
     *     <li>Z5 = geschwefelt</li>
     *     <li>Z9 = Süßungsmittel</li>
     *     <li>Z10 = kann Aktivität und Aufmerksamkeit beeinträchtigen</li>
     * </ul>
     * @return appended string of all additive numbers the food has.
     */
    public String getAdditivenumbers() {
        return additivenumbers;
    }

    /**
     * Sets appended string of all additive numbers the food has. Known types are:
     * <ul>
     *     <li>A = Alkohol</li>
     *     <li>A1 = glutenhaltiges Getreide</li>
     *     <li>A2 = Krebstiere</li>
     *     <li>A3 = Eier</li>
     *     <li>A4 = Fische</li>
     *     <li>A5 = Erdnüsse</li>
     *     <li>A6 = Soja</li>
     *     <li>A7 = Milch (einschließlich Lactose)</li>
     *     <li>A8 = Schalenfrüchte</li>
     *     <li>A9 = Sellerie</li>
     *     <li>A10 = Senf</li>
     *     <li>A11 = Sesamsamen</li>
     *     <li>A12 = Schwefeldioxid und Sulfite</li>
     *     <li>A13 = ? (not seen in menu, so unknown!)</li>
     *     <li>A14 = Weichtiere</li>
     *     <li>A15 = Weizen</li>
     *     <li>A16 = Roggen</li>
     *     <li>A17 = Gerste</li>
     *     <li>A18 = ? (not seen in menu, so unknown!)</li>
     *     <li>A19 = Mandeln</li>
     *     <li>F = Fisch</li>
     *     <li>K = Knoblauch</li>
     *     <li>G = Geflügel</li>
     *     <li>R = Rind/Kalb</li>
     *     <li>S = Schwein</li>
     *     <li>Z1 = Farbstoff</li>
     *     <li>Z2 = konserviert</li>
     *     <li>Z3 = Antioxidationsmittel</li>
     *     <li>Z4 = Geschmacksverstärker</li>
     *     <li>Z5 = geschwefelt</li>
     *     <li>Z9 = Süßungsmittel</li>
     *     <li>Z10 = kann Aktivität und Aufmerksamkeit beeinträchtigen</li>
     * </ul>
     * @param additivenumbers appended string of all additive numbers the food has.
     */
    public void setAdditivenumbers(String additivenumbers) {
        this.additivenumbers = additivenumbers;
    }

    /**
     * Gets price of food for emplyoee.
     * @return price of food.
     */
    public String getPricebed() {
        return pricebed;
    }

    /**
     * Sets price of food for emplyoee.
     * @param pricebed price of food.
     */
    public void setPricebed(String pricebed) {
        this.pricebed = pricebed;
    }
}
