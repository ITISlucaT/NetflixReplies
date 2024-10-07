package com.example.NetflixReplies.Controller;

import com.example.NetflixReplies.Model.Film;
import com.example.NetflixReplies.Model.MovieGenre;
import com.example.NetflixReplies.Service.NetflixRepliesService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

@RestController
@RequestMapping("/api/films")


public class NetflixRepliesController {
    NetflixRepliesService service;
    public NetflixRepliesController(){
        this.service = new NetflixRepliesService();
    }

    @GetMapping
    public Vector<Film> getFilms(){return service.getFilms();}

    @GetMapping("/genres/{genre}")
    public Vector<Film> getFilmsByGenre(@PathVariable String genre) {
        MovieGenre movieGenre = MovieGenre.valueOf(genre);
        return  service.getFilmsByGenre(movieGenre);
    }

    @GetMapping("/name/normal/{name}")
    public Vector<Film> getFilmsByName(@PathVariable String name){return service.getFilmsByName(name);}

    @GetMapping("/name-of-author/{nameOfAuthor}")
    public Vector<Film> getFilmsByAuthor(@PathVariable String nameOfAuthor){
        return service.getFilmsByAuthor(nameOfAuthor);
    }

    @GetMapping("/name/sorted")
    public Vector<Film> getFilmsSortedByName(){return service.getFilmsSortedByName();}

    @GetMapping("/id/{id}")
    public Film findFilmById(@PathVariable int id){return service.findFilmById(id);}

    @GetMapping("/suggest")
    public Film suggestAFilm(){return service.suggestAFilm();}

    //POST
    @PostMapping("/add-new-film")
    public boolean addNewFilm(@RequestBody Film filmToAdd){
        System.out.println(filmToAdd);
        return service.addNewFilm(filmToAdd);
    }
    /*
    TOR implementazione dell'endpoint cambiata
    utilizzo dell'interfaccia MultipartFile
    usata documentazione a questo link https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/multipart.html
    utilizzo del transferTo()
    * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html#transferTo(java.io.File)
    * */
    @PostMapping("/fuse-CSVs")
    public boolean fuseCSVs(@RequestParam("file") MultipartFile fileToAdd) {
        File convFile;
        try {
            convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileToAdd.getOriginalFilename());
            fileToAdd.transferTo(convFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return service.fuseCSVs(convFile);
    }

    //PUT
    @PutMapping("/update")
    public boolean updateFilm(@RequestBody Film film){
        return service.updateFilm(film);}

    //DELETE
    @DeleteMapping("/delete/{idFilm}")
    public boolean removeFilm(@PathVariable int idFilm){
        return service.removeFilm(idFilm);}
}
