package ai.cbz.inbound.mybatis;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: Jinzw
 * @Date: 2020/12/9 13:19
 */
public class UserInfoDTO implements Serializable {
    private  long phoneNumber;
    private List<UserNode>  nodes;

    public long getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(long phoneNumber) { this.phoneNumber = phoneNumber; }

    public List<UserNode> getNodes() { return nodes; }

    public void setNodes(List<UserNode> nodes) { this.nodes = nodes; }

    @JSONCreator
    public UserInfoDTO(@JsonProperty("phoneNumber") long phoneNumber,
                       @JsonProperty("nodes") List<UserNode>  nodes) {
//        System.out.println("UserInfoDTO constructor ---> JSONCreator ------>JsonProperty");
        this.phoneNumber = phoneNumber;
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "phoneNumber=" + phoneNumber +
                ", nodes=" + nodes +
                '}';
    }

    public UserNode popNode(){
         UserNode node = nodes.get(0);
         nodes.remove(0);
         return node;
    }
}
