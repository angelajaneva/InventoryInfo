package mk.gov.moepp.emi.invertoryinfo.web;


import mk.gov.moepp.emi.invertoryinfo.mappers.AnalysisMapper;
import mk.gov.moepp.emi.invertoryinfo.model.Year;
import mk.gov.moepp.emi.invertoryinfo.model.Analysis;
import mk.gov.moepp.emi.invertoryinfo.model.dto.YearDto;
import mk.gov.moepp.emi.invertoryinfo.model.dto.AnalysisYearlyDto;
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
    @PostMapping(path = "/upload/{year}") // //new annotation since 4.3
    public void singleFileUploadYearly(@RequestParam("file") MultipartFile file, @PathVariable(name = "year") String year) throws FileNotFoundException {

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
        return yearService.getAllAnalysis();
    }

    @GetMapping(path = "/{year}")
    public Optional<Year> getAnalysisById(@PathVariable String year){
        return Optional.ofNullable(yearService.getByYear(year));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(path = "/{year}/edit")
    public void editYear(@PathVariable String year, @RequestBody String newYear){
        if (!year.equals(newYear)) {
            Year model = yearService.getByYear(year);
            model.setYear(newYear);
            yearService.saveAnalysis(model);
        }
    }

    @PostMapping
    public Year saveAnalysis(@RequestBody Year year){
        return yearService.saveAnalysis(year);
    }

    // ako treba mozze DTO
    @PutMapping(path = "/{id}")
    public Year editAnalysis(@PathVariable int id, @RequestBody YearRequest year){
        return yearService.editAnalysis(id, year);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteYear(@PathVariable int id){
        yearService.deleteAnalysis(id);
    }
}
