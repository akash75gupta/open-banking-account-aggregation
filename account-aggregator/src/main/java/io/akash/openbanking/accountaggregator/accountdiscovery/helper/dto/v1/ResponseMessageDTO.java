package io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1;


/**
 * Purpose: This is generic class for responding in case of success or failure of request .
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */
public class ResponseMessageDTO {

    /**
     * Represent the message it contain
     */
    private String message;

    /**
     *
     */
    private String username;


    /**
     * Represent the response Error/Success code
     */
    private String code;

    public ResponseMessageDTO(String message,String username,String code)
    {
        this.message=message;
        this.username=username;
        this.code=code;
    }

    public ResponseMessageDTO(String message,String code)
    {
        this.message=message;
        this.code=code;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb=new StringBuilder("ResponseMessageDTO(");
        sb.append("message='").append(message).append('\'');
        sb.append(",username='").append(username).append('\'');
        sb.append(",code='").append(code).append('\'');
        sb.append(')');
        return sb.toString();

    }

    public String getUsername() {
        return username;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
