package ai.cbz.inbound.mybatis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserNode implements Serializable {
    private UserNodeTypeEnum type;
    private String path;
    private String text;
    private int  timeout;

    @JsonCreator
    public UserNode(@JsonProperty("type") UserNodeTypeEnum type,
                    @JsonProperty("path") String path,
                    @JsonProperty("text") String text,
                    @JsonProperty("timeout") int timeout
                    ){
//        System.out.println("UserNode constructor ---> JSONCreator ------>JsonProperty");
        this.type = type;
        this.path = path;
        this.text = text;
        this.timeout = timeout;
    }

    public UserNodeTypeEnum getTypeEnum() { return this.type; }

    public void setTypeEnum(UserNodeTypeEnum type) {
        this.type = type;
    }

    public void setPath(String path) { this.path = path; }

    public String getPath() { return path; }

    public void setText(String text) { this.text = text; }

    public String getText() { return text; }

    public void setTimeout(int timeout) { this.timeout = timeout; }

    public int getTimeout() { return timeout; }

    @Override
    public String toString() {
        return "UserNode{" +
                "type=" + type +
                ", path='" + path + '\'' +
                ", text='" + text + '\'' +
                ", timeout=" + timeout +
                '}';
    }
}
