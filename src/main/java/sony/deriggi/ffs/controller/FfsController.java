package sony.deriggi.ffs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sony.deriggi.ffs.dao.FfsDao;

@RestController
public class FfsController {

    @CrossOrigin(origins = "http://localhost:1234")
    @RequestMapping(value = "/ffs", method = RequestMethod.GET)
	public ResponseEntity<?> getArtists() throws Exception {
	    return ResponseEntity.ok(new FfsDao().fetchStatus());
    }
    
}
