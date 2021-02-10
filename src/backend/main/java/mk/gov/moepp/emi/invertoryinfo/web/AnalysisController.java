package mk.gov.moepp.emi.invertoryinfo.web;


import mk.gov.moepp.emi.invertoryinfo.mappers.AnalysisMapper;
import mk.gov.moepp.emi.invertoryinfo.model.Year;
import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.dto.YearDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisYearlyDto;
import mk.gov.moepp.emi.invertoryinfo.model.exception.ResourceNotFound;
import mk.gov.moepp.emi.invertoryinfo.model.requests.YearRequest;
import mk.gov.moepp.emi.invertoryinfo.service.AnalysisService;
import mk.gov.moepp.emi.invertoryinfo.service.impl.YearServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/analysis", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class AnalysisController {

    //Od tuka ce gi zimame site Dto a site mappinzi ce gi pravime u package "mappers"
    //(ce bide slicno na service, no tamu samo cisti save edit delete se pravat ne se mapira)
    private final YearServiceImpl yearService;
    private final AnalysisMapper analysisMapper;
    private final AnalysisService analysisService;

    public AnalysisController(YearServiceImpl yearService, AnalysisMapper analysisMapper, AnalysisService analysisService){
        this.yearService = yearService;
        this.analysisMapper = analysisMapper;
        this.analysisService = analysisService;
    }

    //upload pravime so gasName i file (treba prethodno da imame kreirano gas)
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping(path = "/upload/gasses/{gas}") // //new annotation since 4.3
//    public void singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable(name = "gas") String gas) throws FileNotFoundException {
//
//        if (file.isEmpty()) {
////            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
//            throw new FileNotFoundException("File not found");
//        }
//
//        yearService.saveFromFile(file, gas);
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/upload") // //new annotation since 4.3
    public void singleFileUploadYearly(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "year") String year) throws FileNotFoundException {
        Year yearModel = yearService.getByYear(year);
        if (yearModel != null){
            throw new RuntimeException("Year already exist try again");
        }

        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            throw new FileNotFoundException("File not found");
        }

        yearService.saveFromFileYearly(year,file);
    }

    @GetMapping(path = "/gas/{id}")
    public List<YearDto> getAllByGasId(@PathVariable int id){
        return analysisMapper.getByGasId(id);
    }

    @GetMapping(path = "/yearly/{year}")
    public AnalysisYearlyDto getAllByYear(@PathVariable(name = "year") String year, @RequestParam(name = "gassesId") Integer[] gassesId, @RequestParam(name = "categoryId") Integer[] categoryId){
        return analysisMapper.getByYear(year,Arrays.asList(gassesId),Arrays.asList(categoryId));
    }

    @GetMapping(path = "/all/data")
    public Set<Analysis> getAllByIds(@RequestParam(name = "gasIds")List<Integer> gasIds, @RequestParam(name = "categoryIds")List<Integer> categoryIds, @RequestParam(name = "analysisIds")List<Integer> analysisIds){
        return analysisService.findAllByIds(gasIds, categoryIds,  analysisIds);
    }

    @GetMapping(path = "/all")
    public List<Analysis> getAll(){
        return analysisService.getAllAnalysis();
    }

    @GetMapping
    public List<Year> getAllAnalysis(){
        return yearService.getAllYears();
    }

    @GetMapping(path = "/{year}")
    public Year getAnalysisById(@PathVariable String year){
        return yearService.getByYear(year);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(path = "/{year}")
    public void editAnalysis(@RequestParam(name = "file", required = false) MultipartFile file, @RequestParam(name = "newYear") String newYear, @PathVariable(name = "year") String year) throws FileNotFoundException {
        if (newYear != null && !year.equals(newYear)) {
            Year model = yearService.getByYear(year);
            if (model == null) {
                throw new ResourceNotFound("Year not found");
            }
            model.setYear(newYear);
            yearService.saveYear(model);
        }
        if (file != null && !file.isEmpty()) {
            yearService.saveFromFileYearly(newYear,file);
        }

    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteYear(@PathVariable int id){
        yearService.deleteYear(id);
    }
}
