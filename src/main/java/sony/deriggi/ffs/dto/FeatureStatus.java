package sony.deriggi.ffs.dto;

import java.util.ArrayList;

import sony.deriggi.ffs.RegionConfig;

public class FeatureStatus {

    private String name;
    private Integer value;
    private Integer[] flags;

    private final static String SPACE = " ";

    public FeatureStatus() {}

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(SPACE);
        sb.append(value);
        return sb.toString();
    }

    public FeatureStatus(String name, Integer value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
        pad(RegionConfig.HEADER.length);
    }

    /**
     * create flags and pad flag field to equal number of regions in data store
     */
    private void pad(final Integer headerLength){
        if(value == null){
            return ;
        }

        String[] parts = Integer.toBinaryString(getValue()).split("");
        ArrayList<Integer> flags = new ArrayList<>(headerLength);
        for(String p: parts){
            flags.add(Integer.parseInt(p));
        }

        while(flags.size() < headerLength){
            flags.add(0,0);
        }

        this.setFlags(flags.toArray(new Integer[flags.size()]));
    
    }

    public Integer[] getFlags() {
        return flags; 
    }
 
     private void setFlags(Integer[] flags) {
         this.flags = flags;
     }

    
}
