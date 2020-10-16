package mk.gov.moepp.emi.invertoryinfo.controller;

import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.dto.GasDto;
import mk.gov.moepp.emi.invertoryinfo.service.GasService;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/gas", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class GasController {

    private final GasService gasService;

    public GasController(GasService gasService) {
        this.gasService = gasService;
    }

    @GetMapping
    public List<Gas> getAllGasses(){
        return gasService.getAllGasses();
    }

    @GetMapping(path = "/{id}")
    public Gas getGas(@PathVariable int id){
        return gasService.getGas(id);
    }

    @PostMapping
    public Gas saveGas(@RequestBody Gas gas){

        return gasService.saveGas(gas);
    }

    @PutMapping(name = "/{id}")
    public Gas editGas(@PathVariable int id, @RequestBody GasDto gas){
        return gasService.editGas(id, gas);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteGas(@PathVariable int id){
        gasService.deleteGas(id);
    }

}
