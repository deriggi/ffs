package sony.deriggi.ffs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sony.deriggi.ffs.dto.ApiDatum;
import sony.deriggi.ffs.dto.ApiMessage;
import sony.deriggi.ffs.dto.FeatureStatus;
import sony.deriggi.ffs.service.FfsService;


@RestController
public class FfsController {

    
    @Autowired
    FfsService ffsService;

    @CrossOrigin(origins = "http://localhost:1234")
    @RequestMapping(value = "/regions", method = RequestMethod.GET)
	public ResponseEntity<?> getHeader() throws Exception {
	    return ResponseEntity.ok(ffsService.fetchHeader());
    }

    @CrossOrigin(origins = "http://localhost:1234" )
    @RequestMapping(value = "/ffs", method = RequestMethod.GET)
	public ResponseEntity<?> getSites() throws Exception {
        ApiMessage message  = ffsService.fetchData();
        return createResponse(message.getDataItems(), message.getStatusCode());
        
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @RequestMapping(value = "/ffs", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdate(@RequestBody FeatureStatus fs) throws Exception {
        ApiMessage message  = ffsService.updateFeatures(fs);
        return createResponse(message.getDataItems(), message.getStatusCode());
    }

    private ResponseEntity<?>  createResponse(List<? extends ApiDatum> items, Integer statusCode){
        return ResponseEntity.status(statusCode)
        .contentType(MediaType.APPLICATION_JSON)
        .body(items);
        
    }
    
}
