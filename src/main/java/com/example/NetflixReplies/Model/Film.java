package com.example.NetflixReplies.Model;

/*fields
* id
* name
* description
* authors
* contributors
* producers
* cast
* genres
* characteristics
* PEGI
* duration
* yearOfPublish
* */

public class Film {
    private static int idCounter = 0;
    private int id;
    private String name;
    private String description;
    private String author;
//    private String contributors; TOR: non saprei come gestire array di persone, in csv vanno inseriti dati primitivi o stringhe
//    private String cast;
    private String producer;

    private MovieGenre genres;
    private MovieCharacteristic characteristics;
    private PegiRating PEGI;
    private int duration;
    private int yearOfPublish;

    public Film(String name, String description, String author, String producer, MovieGenre genres, MovieCharacteristic characteristics, PegiRating PEGI, int duration, int yearOfPublish) {
        this.id = generateNewId();
        this.name = name;
        this.description = description;
        this.author = author;
        this.producer = producer;
        this.genres = genres;
        this.characteristics = characteristics;
        this.PEGI = PEGI;
        this.duration = duration;
        this.yearOfPublish = yearOfPublish;
    }

    public Film(int id, String name, String description, String author, String producer, MovieGenre genres, MovieCharacteristic characteristics, PegiRating PEGI, int duration, int yearOfPublish) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.producer = producer;
        this.genres = genres;
        this.characteristics = characteristics;
        this.PEGI = PEGI;
        this.duration = duration;
        this.yearOfPublish = yearOfPublish;
    }
    public Film(){

    }

    public int generateNewId() {
        return ++idCounter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public MovieGenre getGenres() {
        return genres;
    }

    public void setGenres(MovieGenre genres) {
        this.genres = genres;
    }

    public MovieCharacteristic getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(MovieCharacteristic characteristics) {
        this.characteristics = characteristics;
    }

    public PegiRating getPEGI() {
        return PEGI;
    }

    public void setPEGI(PegiRating PEGI) {
        this.PEGI = PEGI;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", producer='" + producer + '\'' +
                ", genres=" + genres +
                ", characteristics=" + characteristics +
                ", PEGI=" + PEGI +
                ", duration=" + duration +
                ", yearOfPublish=" + yearOfPublish +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film film)) return false;

        return id == film.id;
    }

}
