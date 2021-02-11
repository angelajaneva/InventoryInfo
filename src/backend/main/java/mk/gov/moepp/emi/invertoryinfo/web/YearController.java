package mk.gov.moepp.emi.invertoryinfo.web;

import mk.gov.moepp.emi.invertoryinfo.model.Year;
import mk.gov.moepp.emi.invertoryinfo.model.exception.ResourceNotFound;
import mk.gov.moepp.emi.invertoryinfo.service.YearService;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/year", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class YearController {

    private final YearService yearService;

    public YearController(YearService yearService) {
        this.yearService = yearService;
    }

    @GetMapping
    public List<Year> getAllYears(){
        return yearService.getAllYears();
    }

    @GetMapping("/{year}")
    public Year getYear (@PathVariable String year) {
        Year model = yearService.getByYear(year);
        if (model == null){
            throw new ResourceNotFound("Year not found.");
        }
        return model;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteYear (@PathVariable int id){
        yearService.deleteYear(id);
    }

}
