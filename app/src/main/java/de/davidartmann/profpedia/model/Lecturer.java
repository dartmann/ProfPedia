package de.davidartmann.profpedia.model;

import java.io.Serializable;

/**
 * Lecturer model class.
 * Created by david on 25.12.15.
 */
public class Lecturer implements Serializable, Comparable<Lecturer> {

    private int id;
    private String firstName;
    private String lastName;
    private String title;
    private String email;
    private String phone;
    private String urlWelearn;
    private String urlProfileImage;
    private String address;
    private String roomNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrlWelearn() {
        return urlWelearn;
    }

    public void setUrlWelearn(String urlWelearn) {
        this.urlWelearn = urlWelearn;
    }

    public String getUrlProfileImage() {
        return urlProfileImage;
    }

    public void setUrlProfileImage(String urlProfileImage) {
        this.urlProfileImage = urlProfileImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public int compareTo(Lecturer another) {
        return this.getLastName().compareToIgnoreCase(another.getLastName());
    }
}
