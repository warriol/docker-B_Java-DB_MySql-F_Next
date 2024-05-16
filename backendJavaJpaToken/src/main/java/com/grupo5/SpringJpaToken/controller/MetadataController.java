package com.grupo5.SpringJpaToken.controller;

import com.grupo5.SpringJpaToken.Response.DataGrafo;
import com.grupo5.SpringJpaToken.service.IMetadataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Objects;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    private final IMetadataService iMetadataService;

    public MetadataController(IMetadataService iMetadataService) {
        this.iMetadataService = iMetadataService;
    }

    @GetMapping("/getDataGrafo")
    public ResponseEntity<DataGrafo> getDataGrafo(@RequestParam Long idCarrera){
        DataGrafo data= iMetadataService.getDataGrafo(idCarrera);
        if(Objects.equals(data.getEvento(), "error"))
            return ResponseEntity.badRequest().body(data);

        return ResponseEntity.ok(data);
    }
}
