package mk.gov.moepp.emi.invertoryinfo.web;

import mk.gov.moepp.emi.invertoryinfo.mappers.GasMapper;
import mk.gov.moepp.emi.invertoryinfo.mappers.impl.GasMapperImpl;
import mk.gov.moepp.emi.invertoryinfo.model.Gas;
import mk.gov.moepp.emi.invertoryinfo.model.dto.GasDto;
import mk.gov.moepp.emi.invertoryinfo.model.requests.GasRequest;
import mk.gov.moepp.emi.invertoryinfo.service.GasService;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/gas", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class GasController {

    private final GasService gasService;
    private final GasMapper gasMapper;

    public GasController(GasService gasService, GasMapper gasMapper) {
        this.gasService = gasService;
        this.gasMapper = gasMapper;
    }

    @GetMapping
    public List<GasDto> getAllGasses(){
        return gasMapper.getAllGasses();
    }

    @GetMapping(path = "/year/{id}")
    public List<GasDto> getAllGassesByYear(@PathVariable int id){
        return gasMapper.getAllGassesFromYear(id);
    }

    @GetMapping(path = "/{id}")
    public Gas getGas(@PathVariable int id){
        return gasService.getGas(id);
    }

    @PostMapping
    public Gas saveGas(@RequestBody Gas gas){

        return gasService.saveGas(gas);
    }

    @PutMapping(path = "/{id}")
    public Gas editGas(@PathVariable int id, @RequestBody GasRequest gas){
        return gasService.editGas(id, gas);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGas(@PathVariable int id){
        gasService.deleteGas(id);
    }

}
