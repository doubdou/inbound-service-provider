package ai.cbz.inbound.mybatis;

//import com.alibaba.fastjson.annotation.JSONCreator;
//import com.alibaba.fastjson.annotation.JSONField;
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
    NODE_TYPE_REFUSE (3001),
//    预应答，说明：在预应答与应答期间，可播放音频或合成音，在通讯协议上视为早期媒体/彩铃音
    NODE_TYPE_PRE_ANSWER(3002),
//    应答
    NODE_TYPE_ANSWER(3003),
//    播放音频文件
    NODE_TYPE_PLAY_FILE(3004),
//    播放合成音
    NODE_TYPE_PLAY_TTS(3005),
//    停止播放
    NODE_TYPE_STOP_PLAY(3006),
//    语音识别
    NODE_TYPE_RECOGNIZE(3007),
//    挂机
    NODE_TYPE_HANGUP(3008),
//    无任何动作
    NODE_TYPE_NONE(3009);

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
