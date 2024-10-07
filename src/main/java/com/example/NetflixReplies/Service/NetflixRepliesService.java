package com.example.NetflixReplies.Service;

import com.example.NetflixReplies.Model.Film;
import com.example.NetflixReplies.Model.MovieGenre;
import com.example.NetflixReplies.Repository.NetflixRepliesRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Vector;

@Service
public class NetflixRepliesService {
    NetflixRepliesRepository repository;
    public NetflixRepliesService(){
        this.repository = new NetflixRepliesRepository();
    }
//GET
    public Vector<Film> getFilms(){return repository.getFilms();}
    public Vector<Film> getFilmsByGenre(MovieGenre genre) {return  repository.getFilmsByGenre(genre);}
    public Vector<Film> getFilmsByName(String name){return repository.getFilmsByName(name);}
    public Vector<Film> getFilmsByAuthor(String nameOfAuthor){return repository.getFilmsByAuthor(nameOfAuthor);}
    public Vector<Film> getFilmsSortedByName(){return repository.getFilmsSortedByName();}
    public Film findFilmById(int id){return repository.findFilmById(id);}
    public Film suggestAFilm(){return repository.suggestAFilm();}

   //POST
    public boolean addNewFilm(Film filmToAdd){return repository.addNewFilm(filmToAdd);}
    public boolean fuseCSVs(File fileToAdd){return repository.fuseCSVs(fileToAdd);}

    //PUT
    public boolean updateFilm(Film film){return repository.updateFilm(film);}

    //DELETE
    public boolean removeFilm(int idFilm){return repository.removeFilm(idFilm);}



}
