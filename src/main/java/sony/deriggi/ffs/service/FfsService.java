package sony.deriggi.ffs.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sony.deriggi.ffs.dao.FfsDao;
import sony.deriggi.ffs.dto.FeatureStatus;

@Service
public class FfsService {

    @Autowired
    FfsDao ffsDao;
    

    private static final String[] HEADER = {"Asia", "Korea", "Europe","Japan", "America"};

    public String[] fetchHeader(){
        return HEADER;
    };

    public FeatureStatus[] fetchData(){
        FeatureStatus[] features = ffsDao.fetchStatus();
        pad(features);
        return features;
    }

    public FeatureStatus[] updateFeatures(FeatureStatus fs){
        return pad(ffsDao.createOrUpdate( fs ));
    }
    
    private FeatureStatus[] pad(FeatureStatus[] features){
        if(features == null){
            return null;
        }

        for(FeatureStatus feature: features){
            String[] parts = Integer.toBinaryString(feature.getValue()).split("");
            ArrayList<Integer> flags = new ArrayList<>(parts.length);
            for(String p: parts){
                flags.add(Integer.parseInt(p));
            }

            while(flags.size() < HEADER.length){
                flags.add(0,0);
            }


            feature.setFlags(flags.toArray(new Integer[flags.size()]));

        }
        return features;
    }
}
