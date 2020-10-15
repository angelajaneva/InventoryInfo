package mk.gov.moepp.emi.invertoryinfo.controller;


import lombok.Getter;
import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.requests.CreateAnalysisRequest;
import mk.gov.moepp.emi.invertoryinfo.service.impl.AnalysisServiceImpl_v2;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "api/analysis", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class AnalysisController {

    private final AnalysisServiceImpl_v2 analysisService;

    public AnalysisController(AnalysisServiceImpl_v2 analysisService){
        this.analysisService = analysisService;
    }

    @PostMapping("/upload/gasses/{gasId}") // //new annotation since 4.3
    public void singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long gasId) throws FileNotFoundException {

        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            throw new FileNotFoundException("File not found");
        }

        analysisService.saveFromFile(file);
    }

    @GetMapping
    public List<Analysis> getAllAnalysis(){
        return analysisService.getAllAnalysis();
    }

    @GetMapping(path = "{id}")
    public Analysis getAnalysisById(@PathVariable int id){
        return analysisService.getAnalysisById(id);
    }

    @PostMapping()
    public Analysis saveAnalysis(Analysis analysis){
        return analysisService.saveAnalysis(analysis);
    }

    // ako treba mozze DTO
    @PatchMapping(path = "/edit")
    public Analysis editAnalysis(Analysis analysis){
        return analysisService.editAnalysis(analysis);
    }

    @DeleteMapping(path = "{id}")
    public void deleteAnalysis(@PathVariable int id){
        analysisService.deleteAnalysis(id);
    }

    @GetMapping("{analysisId}/categories/{categoryId}/gases/{gasId}")
    public void Bla() {}

}
