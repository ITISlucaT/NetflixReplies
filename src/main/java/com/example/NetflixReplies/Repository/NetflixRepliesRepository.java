package com.example.NetflixReplies.Repository;

import com.example.NetflixReplies.Model.Film;
import com.example.NetflixReplies.Model.MovieCharacteristic;
import com.example.NetflixReplies.Model.MovieGenre;
import com.example.NetflixReplies.Model.PegiRating;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Comparator;
import java.util.Random;
import java.util.Vector;

@Repository
public class NetflixRepliesRepository {
    private Vector<Film> filmsCollection;
    private static File file_archive;
    private static final String HEADER = "id,name,description,author,producer,genres,characteristics,PEGI,duration,yearOfPublish\n";

    public NetflixRepliesRepository() {
        String FILE_PATH = "src/main/java/com/example/NetflixReplies/Model/films.csv";
        file_archive = new File(FILE_PATH);
        this.filmsCollection = new Vector<Film>();
        loadCSV();
    }

    public boolean saveInCSV() {
        boolean result;
        try {
            FileWriter fw = new FileWriter(file_archive);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(HEADER);
            for (Film m : this.filmsCollection) {
                bw.write(m.getId() + ","
                        + m.getName() + ","
                        + m.getDescription() + ","
                        + m.getAuthor() + ","
                        + m.getProducer() + ","
                        + m.getGenres().toString() + ","
                        + m.getCharacteristics().toString() + ","
                        + m.getPEGI().toString() + ","
                        + m.getDuration() + ","
                        + m.getYearOfPublish() + "\n");
            }
            bw.flush();
            bw.close();
            fw.close();
            result = true;
        } catch (IOException e) {
            System.out.println("cannot write correctly");
            System.out.println(e.getMessage());
            result = false;
        }
        return result;
    }


    public boolean loadCSV() {

        Vector<Film> result = new Vector<Film>();

        try {
            FileReader fr = new FileReader(file_archive);
            BufferedReader br = new BufferedReader(fr);
            String line;
            br.readLine();  // Salta l'intestazione
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                result.add(new Film(Integer.parseInt(values[0]), values[1], values[2], values[3], values[4], MovieGenre.valueOf(values[5].toUpperCase()), MovieCharacteristic.valueOf(values[6].toUpperCase()), PegiRating.valueOf(values[7].toUpperCase()), Integer.parseInt(values[8]), Integer.parseInt(values[9])));
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while reading the file");
            System.out.println(e.getMessage());
        }
        this.filmsCollection.clear();
        this.filmsCollection.addAll(result);
        return true;
    }

    /*
     * Dovrei inserirli senza id? li genererebbe in automatico il sistema
     * diversa costruzione del csv, non rispetterebbe la consegna
     * */
    public boolean fuseCSVs(File fileToAdd) {

        Vector<Film> result = new Vector<Film>();
        System.out.println(fileToAdd);
        try {
            FileReader fr = new FileReader(fileToAdd);
            BufferedReader br = new BufferedReader(fr);
            String line;
            br.readLine();  // Salta l'intestazione
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                result.add(new Film(Integer.parseInt(values[0]), values[1], values[2], values[3], values[4], MovieGenre.valueOf(values[5].toUpperCase()), MovieCharacteristic.valueOf(values[6].toUpperCase()), PegiRating.valueOf(values[7].toUpperCase()), Integer.parseInt(values[8]), Integer.parseInt(values[9])));
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while reading the file");
            System.out.println(e.getMessage());
            return false;
        }
        for (Film film : result) {//TOR più dispendioso a livello di operazioni sul file (salvo ogni volta) ma aggiunta più sicura
            addNewFilm(film);
        }
        if (!saveInCSV()) return false;
        return loadCSV();
    }

    public boolean sortFilmsByIdInCSV() {
        Vector<Film> sortedFilms = new Vector<Film>(this.filmsCollection);
        sortedFilms.sort(Comparator.comparingInt(Film::getId));
        System.out.println(sortedFilms);
        this.filmsCollection.clear();
        this.filmsCollection.addAll(sortedFilms);

        return saveInCSV();
    }

    public Vector<Film> getFilms() {
        return this.filmsCollection;
    }

    public Vector<Film> getFilmsByGenre(MovieGenre genre) {
        Vector<Film> result = new Vector<Film>();
        for (Film f : this.filmsCollection) {
            if (f.getGenres().equals(genre)) {
                result.add(f);
            }
        }
        if (!result.isEmpty()) return result;
        else return null;
    }

    public Vector<Film> getFilmsByName(String name) {
        Vector<Film> result = new Vector<Film>();
        for (Film f : this.filmsCollection) {
            if (f.getName().equals(name)) {
                result.add(f);
            }
        }
        if (!result.isEmpty()) return result;
        else return null;
    }

    public Vector<Film> getFilmsByAuthor(String nameOfAuthor) {
        Vector<Film> result = new Vector<Film>();
        for (Film f : this.filmsCollection) {
            if (f.getAuthor().equals(nameOfAuthor)) {
                result.add(f);
            }
        }
        if (!result.isEmpty()) return result;
        else return null;
    }

    public Vector<Film> getFilmsSortedByName() {
        Vector<Film> sortedFilms = this.filmsCollection;
        sortedFilms.sort((f1, f2) -> f1.getName().compareToIgnoreCase(f2.getName()));
        return sortedFilms;
    }


    public boolean addNewFilm(Film filmToAdd) {
        if (!filmsCollection.contains(filmToAdd)) {
            this.filmsCollection.add(filmToAdd);
            saveInCSV();
            return true;
        }
        System.out.println("Film already added");
        return false;
    }

    public Film findFilmById(int id) {
        for (Film film : this.filmsCollection) {
            if (film.getId() == id) {
                return film;
            }
        }
        return null;
    }

    public boolean updateFilm(Film film) {
        int i = 0;
        for (Film f : this.filmsCollection) {
            if (f.equals(film)) {
                loadCSV();
                filmsCollection.set(i, film);
                saveInCSV();
                return true;
            }
            i++;
        }
        System.out.println("Film not found");
        return false;
    }

    public boolean removeFilm(int idFilm) {
        Film clientToDelete = findFilmById(idFilm);
        this.filmsCollection.remove(clientToDelete);
        return saveInCSV();
    }


    public Film suggestAFilm() {
        return this.filmsCollection.elementAt(new Random().nextInt(this.filmsCollection.size()));
    }


    public static void main(String[] args) {
        Film film1 = new Film("Inception", "A mind-bending thriller", "Christopher Nolan", "Emma Thomas", MovieGenre.SCIENCE_FICTION, MovieCharacteristic.THREE_D, PegiRating.PEGI_12, 148, 2010);
        Film film2 = new Film("The Dark Knight", "A dark take on Batman", "Christopher Nolan", "Charles Roven", MovieGenre.ACTION, MovieCharacteristic.IMAX, PegiRating.PEGI_12, 152, 2008);
        Film film3 = new Film("Interstellar", "A journey through space and time", "Jonathan Nolan", "Emma Thomas", MovieGenre.SCIENCE_FICTION, MovieCharacteristic.FEATURE_LENGTH, PegiRating.PEGI_12, 169, 2014);
        Film film4 = new Film("Parasite", "A tale of social inequality", "Bong Joon-ho", "Kwak Sin-ae", MovieGenre.DRAMA, MovieCharacteristic.INDEPENDENT, PegiRating.PEGI_16, 132, 2019);
        Film film5 = new Film("Avengers: Endgame", "Superheroes unite to defeat Thanos", "Christopher Markus", "Kevin Feige", MovieGenre.ACTION, MovieCharacteristic.BLOCKBUSTER, PegiRating.PEGI_12, 181, 2019);
        Film film6 = new Film("The Matrix", "A hacker discovers a simulated reality", "The Wachowskis", "Joel Silver", MovieGenre.SCIENCE_FICTION, MovieCharacteristic.TALKIE, PegiRating.PEGI_16, 136, 1999);
        Film film7 = new Film("Titanic", "A romance aboard the ill-fated ship", "James Cameron", "James Cameron", MovieGenre.ROMANCE, MovieCharacteristic.COLOR, PegiRating.PEGI_12, 195, 1997);
        Film film8 = new Film("Gladiator", "A Roman general seeks revenge", "David Franzoni", "Douglas Wick", MovieGenre.HISTORICAL, MovieCharacteristic.AWARD_WINNING, PegiRating.PEGI_16, 155, 2000);
        Film film9 = new Film("The Godfather", "The saga of an Italian-American crime family", "Mario Puzo", "Albert S. Ruddy", MovieGenre.CRIME, MovieCharacteristic.CULT_CLASSIC, PegiRating.PEGI_18, 175, 1972);
        Film film10 = new Film("Joker", "The origin story of Gotham's most notorious villain", "Todd Phillips", "Bradley Cooper", MovieGenre.DRAMA, MovieCharacteristic.SILENT, PegiRating.PEGI_18, 122, 2019);
        Film film11 = new Film("Joker", "The origin story of Gotham's most notorious villain", "Todd Phillips", "Bradley Cooper", MovieGenre.DRAMA, MovieCharacteristic.SILENT, PegiRating.PEGI_18, 122, 2019);

        NetflixRepliesRepository n = new NetflixRepliesRepository();
        //n.addNewFilm(film11);
//        n.addNewFilm(film2);
//        n.addNewFilm(film3);
//        n.addNewFilm(film4);
//        n.addNewFilm(film5);
//        n.addNewFilm(film6);
//        n.addNewFilm(film7);
//        n.addNewFilm(film8);
//        n.addNewFilm(film9);
//        n.addNewFilm(film10);
        //n.removeFilm(1);
        //System.out.println(n.suggestAFilm());
//        System.out.println("films: "+n.getFilms());
//        System.out.println("film by name"+n.getFilmsByName("Interstellar"));
//        System.out.println("film by genre"+n.getFilmsByGenre(MovieGenre.ACTION));
        System.out.println(n.getFilmsSortedByName());
        //n.fuseCSVs();
        n.sortFilmsByIdInCSV();

    }

}
