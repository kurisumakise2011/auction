package com.auction.game.controller;

import com.auction.game.entity.Category;
import com.auction.game.entity.Genre;
import com.auction.game.entity.Material;
import com.auction.game.model.ListWrapper;
import com.auction.game.repository.CategoryRepository;
import com.auction.game.repository.GenreRepository;
import com.auction.game.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/dictionary")
@RestController
public class DictionaryController {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/category")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/genre")
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @GetMapping("/material")
    public List<Material> getMaterials() {
        return materialRepository.findAll();
    }

    @PutMapping("/category")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateCategories(@RequestBody ListWrapper<Category> categories) {
        categoryRepository.saveAll(categories.getList());
    }

    @PutMapping("/genre")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateGenres(@RequestBody ListWrapper<Genre> genres) {
        genreRepository.saveAll(genres.getList());
    }

    @PutMapping("/material")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateMaterials(@RequestBody ListWrapper<Material> materials) {
        materialRepository.saveAll(materials.getList());
    }


}
