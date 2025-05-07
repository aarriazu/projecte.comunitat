package com.comunitat.projecte.comunitat.service;

import com.comunitat.projecte.comunitat.model.Comunitat;
import com.comunitat.projecte.comunitat.util.FitxerParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ComunitatService {
    private Comunitat comunitat;

    public void processarFitxers(MultipartFile comunitatTxt, MultipartFile despesesTxt) throws IOException {
        this.comunitat = FitxerParser.parseComunitat(comunitatTxt);
        FitxerParser.parseDespeses(despesesTxt, this.comunitat);
    }

    public Comunitat getComunitat() {
        return comunitat;
    }
}