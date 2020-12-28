package ai.cbz.inbound.service;

import ai.cbz.inbound.common.api.IDialogService;
import ai.cbz.inbound.common.enums.DialogActionTypeEnum;
import ai.cbz.inbound.common.request.DialogManageRequest;
import ai.cbz.inbound.common.response.DialogAction;
import ai.cbz.inbound.common.response.DialogData;
import ai.cbz.inbound.common.response.DialogManageResponse;
import ai.cbz.inbound.common.response.DialogPlayFileParams;
import ai.cbz.inbound.constant.TestCaseConst;
import ai.cbz.inbound.dao.RedisDao;
import ai.cbz.inbound.mybatis.UserInfoDTO;
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
    public DialogManageResponse dialogManage(DialogManageRequest request){
        System.out.println("DEBUG DialogManage received callID:{} " + request.getCallId());
        DialogManageResponse response = new DialogManageResponse();
//        DialogData dialogData = response.getData();
        DialogData dialogData = new DialogData();

        if(counter == 0) {
            DialogAction createAction = new DialogAction();
            createAction.setAction(DialogActionTypeEnum.DM_CC_CHAT_CREATE_COMPLETE);
            dialogData.addAction(createAction);
            response.setMsg("demo first msg create session...");
            counter++;
        }else {
            DialogAction playAction = new DialogAction();
            playAction.setAction(DialogActionTypeEnum.DM_CC_CHAT_PLAY_FILE);
            DialogPlayFileParams dialogPlayFileParams = new DialogPlayFileParams();
            dialogPlayFileParams.setPath("/opt/fs-hlr/sounds/wanda.wav");
            playAction.setParams(dialogPlayFileParams);
            dialogData.addAction(playAction);
            response.setMsg("2rd msg play file...");
        }
        response.setDialogData(dialogData);

        response.setStatus(1000);
        return response;
    }
}
