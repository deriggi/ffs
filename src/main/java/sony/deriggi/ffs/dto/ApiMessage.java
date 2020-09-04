package sony.deriggi.ffs.dto;

import java.util.List;

public class ApiMessage {
    private List<? extends ApiDatum> dataItems;
    private Integer statusCode;

    public void setDataItems(List<? extends ApiDatum> items){
        dataItems = items;
    }

    public List<? extends ApiDatum> getDataItems(){
        return dataItems;
    }

	public Integer getStatusCode() {
		return statusCode;
    }
    
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
    
    
}
