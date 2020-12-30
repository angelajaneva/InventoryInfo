package mk.gov.moepp.emi.invertoryinfo.web;


import mk.gov.moepp.emi.invertoryinfo.mappers.AnalysisMapper;
import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.AnalysisCategoryGas;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisGasDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisYearlyDto;
import mk.gov.moepp.emi.invertoryinfo.model.requests.AnalysisRequest;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisCategoryGasService;
import mk.gov.moepp.emi.invertoryinfo.service.impl.AnalysisServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/analysis", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class AnalysisController {

    //Od tuka ce gi zimame site Dto a site mappinzi ce gi pravime u package "mappers"
    //(ce bide slicno na service, no tamu samo cisti save edit delete se pravat ne se mapira)
    private final AnalysisServiceImpl analysisService;
    private final AnalysisMapper analysisMapper;
    private final AnalysisCategoryGasService analysisCategoryGasService;

    public AnalysisController(AnalysisServiceImpl analysisService, AnalysisMapper analysisMapper, AnalysisCategoryGasService analysisCategoryGasService){
        this.analysisService = analysisService;
        this.analysisMapper = analysisMapper;
        this.analysisCategoryGasService = analysisCategoryGasService;
    }

    //upload pravime so gasName i file (treba prethodno da imame kreirano gas)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/upload/gasses/{gas}") // //new annotation since 4.3
    public void singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable(name = "gas") String gas) throws FileNotFoundException {

        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            throw new FileNotFoundException("File not found");
        }

        analysisService.saveFromFile(file, gas);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/upload/year/{year}") // //new annotation since 4.3
    public void singleFileUploadYearly(@RequestParam("file") MultipartFile file, @PathVariable(name = "year") String year) throws FileNotFoundException {

        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            throw new FileNotFoundException("File not found");
        }

        analysisService.saveFromFileYearly(year,file);
    }

    @GetMapping(path = "/years")
    public List<AnalysisDto> getAllYears (){
        return analysisMapper.getAllYears();
    }

    @GetMapping(path = "/gasses/{gas}")
    public AnalysisGasDto getAllByGas(@PathVariable(name = "gas") String gas, @RequestParam(name = "analysisId") Integer[] analysisId, @RequestParam(name = "categoryId") Integer[] categoryId){
        return analysisMapper.getByGas(gas, Arrays.asList(analysisId),Arrays.asList(categoryId));
    }

    @GetMapping(path = "gas/{id}")
    public List<AnalysisDto> getAllByGasId(@PathVariable int id){
        return analysisMapper.getByGasId(id);
    }

    @GetMapping(path = "/yearly/{year}")
    public AnalysisYearlyDto getAllBaryYear(@PathVariable(name = "year") String year, @RequestParam(name = "gassesId") Integer[] gassesId, @RequestParam(name = "categoryId") Integer[] categoryId){
        return analysisMapper.getByYear(year,Arrays.asList(gassesId),Arrays.asList(categoryId));
    }

    @GetMapping(path = "/all")
    public List<AnalysisCategoryGas> getAll(){
        System.out.println("angela");
        return analysisCategoryGasService.getAllAnalysisCategoryGas();
    }

    @GetMapping
    public List<Analysis> getAllAnalysis(){
        return analysisService.getAllAnalysis();
    }

    @GetMapping(path = "/{id}")
    public Analysis getAnalysisById(@PathVariable int id){
        return analysisService.getAnalysisById(id);
    }

    @PostMapping
    public Analysis saveAnalysis(@RequestBody Analysis analysis){
        return analysisService.saveAnalysis(analysis);
    }

    // ako treba mozze DTO
    @PutMapping(path = "/{id}")
    public Analysis editAnalysis(@PathVariable int id, @RequestBody AnalysisRequest analysis){
        return analysisService.editAnalysis(id, analysis);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnalysis(@PathVariable int id){
        analysisService.deleteAnalysis(id);
    }
}
