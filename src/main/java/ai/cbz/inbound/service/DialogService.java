package ai.cbz.inbound.service;

import ai.cbz.inbound.common.api.IDialogService;
import ai.cbz.inbound.common.request.DialogManageRequest;
import ai.cbz.inbound.common.response.DialogData;
import ai.cbz.inbound.common.response.DialogManageResponse;
import org.apache.dubbo.config.annotation.DubboService;
//import org.springframework.stereotype.Service;

/**
 * @Author: Jinzw
 * @Date: 2020/11/5 19:46
 */
@DubboService
public class DialogService  implements IDialogService {
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
        System.out.println("DEBUG DialogManage received invoke callID ->{} " + request.getCallId());
        DialogManageResponse response = new DialogManageResponse();
        DialogData dialogData = response.getData();

        return response;
    }
}
