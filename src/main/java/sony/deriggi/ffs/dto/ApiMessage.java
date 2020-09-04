package sony.deriggi.ffs.dto;

public class ApiMessage {
    private Integer code;
    private String message;

    public ApiMessage(){

    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        return sb.toString();
    }


}
