package sony.deriggi.ffs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	    return ResponseEntity.ok(ffsService.fetchData());
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @RequestMapping(value = "/ffs", method = RequestMethod.POST)
	public ResponseEntity<?> addOrUpdate(@RequestBody FeatureStatus fs) throws Exception {
        ApiMessage message  = ffsService.updateFeatures(fs);
        BodyBuilder builder = ResponseEntity.status(message.getStatusCode());
        builder.body(message.getDataItems());
        return builder.build();
    }
    
}
