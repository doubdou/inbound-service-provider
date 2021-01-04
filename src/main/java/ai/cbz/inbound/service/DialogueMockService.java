package ai.cbz.inbound.service;

import ai.cbz.inbound.common.api.IDialogService;
import ai.cbz.inbound.common.enums.DialogActionTypeEnum;
import ai.cbz.inbound.common.request.DialogManageRequest;
import ai.cbz.inbound.common.response.*;
import ai.cbz.inbound.constant.TestCaseConst;
import ai.cbz.inbound.dao.RedisDao;
import ai.cbz.inbound.mybatis.UserInfoDTO;
import ai.cbz.inbound.mybatis.UserNode;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;

/**
 * @Author: Jinzw
 * @Date: 2020/11/5 19:46
 */
@DubboService
public class DialogueMockService implements IDialogService {
    private int counter;
    @Autowired
    private RedisDao redisDao;

    @Override
    public String greeting(String name){
        System.out.println("Provider received invoke of greeting: " + name);
//        sleepWhile();
        return "Jinzw Annotation, greeting " + name;
    }

    public String replyGreeting(String name){
        System.out.println("Provider received invoke of replyGreeting: " + name);
//        sleepWhile();
        return "Jinzw Annotation, fine " + name;
    }
    private void sleepWhile(){
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private DialogData assembleDialogData(UserNode node){
        DialogData dialogData = new DialogData();
        DialogAction action = new DialogAction();
        switch (node.getTypeEnum()){
            case NODE_TYPE_REFUSE:
                action.setAction(DialogActionTypeEnum.DM_CC_CHAT_REFUSE);
                break;

            case NODE_TYPE_PRE_ANSWER:
                action.setAction(DialogActionTypeEnum.DM_CC_CHAT_PRE_ANSWER);
                break;
            case NODE_TYPE_ANSWER:
                action.setAction(DialogActionTypeEnum.DM_CC_CHAT_ANSWER);
                System.out.println("应答节点:");
                break;
            case NODE_TYPE_PLAY_FILE:
                action.setAction(DialogActionTypeEnum.DM_CC_CHAT_PLAY_FILE);
                DialogPlayFileParams dialogPlayFileParams = new DialogPlayFileParams();
                //添加节点参数
                dialogPlayFileParams.setPath(node.getPath());
                System.out.println("播放文件节点:"+ node.getPath());
                action.setParams(dialogPlayFileParams);
                break;
            case NODE_TYPE_STOP_PLAY:
                action.setAction(DialogActionTypeEnum.DM_CC_CHAT_PAUSE_PLAY);
                break;
            case NODE_TYPE_PLAY_TTS:
                action.setAction(DialogActionTypeEnum.DM_CC_CHAT_PLAY_TTS);
                DialogPlayTTSParams dialogPlayTTSParams = new DialogPlayTTSParams();
                //添加参数
                dialogPlayTTSParams.setText(node.getText());
                action.setParams(dialogPlayTTSParams);
                break;
            case NODE_TYPE_RECOGNIZE:
                action.setAction(DialogActionTypeEnum.DM_CC_CHAT_DETECT_SPEECH);
                break;
            case NODE_TYPE_WAIT:
                action.setAction(DialogActionTypeEnum.DM_CC_CHAT_WAIT);
                DialogWaitParams dialogWaitParams = new DialogWaitParams();
                //添加节点参数
                dialogWaitParams.setTimeout(node.getTimeout());
                action.setParams(dialogWaitParams);
                System.out.println("等待节点: "+ node.getTimeout());
                break;
            case NODE_TYPE_HANGUP:
            default:
                action.setAction(DialogActionTypeEnum.DM_CC_CHAT_HANGUP);
        }

        dialogData.addAction(action);
        return dialogData;
    }

    public DialogManageResponse dialogManage(DialogManageRequest request){
        DialogManageResponse response = new DialogManageResponse();

        UserInfoDTO userInfoDTO = redisDao.hGetObject(TestCaseConst.REDIS_KEY, request.getCallee(), UserInfoDTO.class);
        if (userInfoDTO == null){
            response.setStatus(480);
            response.setMsg("can not found the testcase");
        }else{
            UserNode node = userInfoDTO.popNode();
            if (node == null){
                response.setStatus(480);
                response.setMsg("can not found node in the testcase");
            }else {
                DialogData dialogData = assembleDialogData(node);
                redisDao.hSetObject(TestCaseConst.REDIS_KEY, request.getCallee(), userInfoDTO);
                response.setDialogData(dialogData);
                response.setStatus(200);
                response.setMsg("ok");
            }
        }
        return response;
    }

    public DialogManageResponse dialogManage1(DialogManageRequest request){
        System.out.println("DEBUG DialogManage received callID:{} " + request.getCallId());
        DialogManageResponse response = new DialogManageResponse();
//        DialogData dialogData = response.getData();
        DialogData dialogData = new DialogData();

//        if(counter == 0) {
//            DialogAction createAction = new DialogAction();
//            createAction.setAction(DialogActionTypeEnum.DM_CC_CHAT_CREATE_COMPLETE);
//            dialogData.addAction(createAction);
//            response.setMsg("demo first msg create session...");
//            counter++;
//        }else {
//            DialogAction playAction = new DialogAction();
//            playAction.setAction(DialogActionTypeEnum.DM_CC_CHAT_PLAY_FILE);
//            DialogPlayFileParams dialogPlayFileParams = new DialogPlayFileParams();
//            dialogPlayFileParams.setPath("/opt/fs-hlr/sounds/wanda.wav");
//            playAction.setParams(dialogPlayFileParams);
//            dialogData.addAction(playAction);
//            response.setMsg("2rd msg play file...");
//        }
        //查询手机号对应的测试用例
        UserInfoDTO userInfoDTO = redisDao.hGetObject(TestCaseConst.REDIS_KEY, request.getCallId(), UserInfoDTO.class);
        if (userInfoDTO == null){
            response.setStatus(1001);
            response.setMsg("can not found this case");
        }
        response.setDialogData(dialogData);

        response.setStatus(1000);
        return response;
    }
}
