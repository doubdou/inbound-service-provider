package ai.cbz.inbound.mybatis;

//import com.alibaba.fastjson.annotation.JSONCreator;
//import com.alibaba.fastjson.annotation.JSONField;
import ai.cbz.inbound.common.enums.DialogActionTypeEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
//
//import java.util.Arrays;
//import java.util.Optional;

/**
 * @Description:
 * @Author: Jinzw
 * @Date: 2020/12/7 17:58
 */
public enum UserNodeTypeEnum {
//    拒绝接听
    NODE_TYPE_REFUSE (30001),
//    预应答，说明：在预应答与应答期间，可播放音频或合成音，在通讯协议上视为早期媒体/彩铃音
    NODE_TYPE_PRE_ANSWER(30002),
//    应答
    NODE_TYPE_ANSWER(30003),
//    播放音频文件
    NODE_TYPE_PLAY_FILE(30004),
//    播放合成音
    NODE_TYPE_PLAY_TTS(30005),
//    停止播放
    NODE_TYPE_STOP_PLAY(30006),
//    语音识别
    NODE_TYPE_RECOGNIZE(30007),
//    挂机
    NODE_TYPE_HANGUP(30008),
//    无任何动作
    NODE_TYPE_WAIT(30009);

    private final int value;

    UserNodeTypeEnum(int value){
        this.value  =value;
    }

    @JsonCreator
    public static UserNodeTypeEnum getNodeTypeEnum(int code){
//        System.out.println("---------code :"+ code + "--------\n");
        for(UserNodeTypeEnum nodeTypeEnum:values()){
            if(nodeTypeEnum.value==code){
                return nodeTypeEnum;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
