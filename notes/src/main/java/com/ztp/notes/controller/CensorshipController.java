package com.ztp.notes.controller;

import com.ztp.notes.model.Censorship;
import com.ztp.notes.service.CensorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CensorshipController {

    @Autowired
    private CensorshipService censorshipService;

    @GetMapping("/censorship/{username}")
    public List<Censorship> getUserCensorship(@PathVariable String username) {
        return censorshipService.getUserCensorship(username);
    }

    @GetMapping("/censorship/{username}/{phrase}")
    public Censorship getCensorship(@PathVariable String username, @PathVariable String phrase) {
        return censorshipService.getCensorship(username, phrase);
    }

    @PostMapping("/censorship/{username}")
    public Censorship saveCensorship(@PathVariable String username, @RequestBody Censorship censorship) {
        censorshipService.saveCensorship(username, censorship);
        return censorship;
    }

    @DeleteMapping("/censorship/{username}/{phrase}")
    public void deleteCensorship(@PathVariable String username, @PathVariable String phrase) {
        censorshipService.deleteCensorship(username, phrase);
    }
}
