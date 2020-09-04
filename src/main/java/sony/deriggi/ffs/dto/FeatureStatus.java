package sony.deriggi.ffs.dto;

public class FeatureStatus {

    private String name;
    private Integer value;
    private Integer[] flags;

    private final static String SPACE = " ";

    public FeatureStatus() {
    }

    

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
    }

    public Integer[] getFlags() {
        return flags; 
     }
 
     public void setFlags(Integer[] flags) {
         this.flags = flags;
     }

    
}
