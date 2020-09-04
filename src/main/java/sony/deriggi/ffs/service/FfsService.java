package sony.deriggi.ffs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sony.deriggi.ffs.RegionConfig;
import sony.deriggi.ffs.dao.FfsDao;
import sony.deriggi.ffs.dto.ApiMessage;
import sony.deriggi.ffs.dto.FeatureStatus;

@Service
public class FfsService {

    @Autowired
    FfsDao ffsDao;
    

    public String[] fetchHeader(){
        return RegionConfig.HEADER;
    };

    public FeatureStatus[] fetchData(){
        FeatureStatus[] features = ffsDao.fetchStatus();
        return features;
    }

    public ApiMessage updateFeatures(FeatureStatus fs){
        return ffsDao.createOrUpdate( fs );

    }
    
   
}
