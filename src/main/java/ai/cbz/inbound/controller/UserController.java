package ai.cbz.inbound.controller;

import ai.cbz.inbound.mybatis.UserInfoDTO;
import ai.cbz.inbound.response.TestToolResponse;
import ai.cbz.inbound.service.TestCaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("dialogue/user")
@Controller
//@Service
public class UserController {

    @Autowired
    private TestCaseService testCaseService;

    /**
     * 根据号码id查询号码数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserInfoDTO> queryUserById(@PathVariable("id") Long id) {
        try {
            UserInfoDTO user = testCaseService.queryUserById(id);
            if (null == user) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
            }
            // 200
            // return ResponseEntity.status(HttpStatus.OK).body(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 新增号码
     *
     * @param users
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<Void> saveUser(User user) {
    public TestToolResponse saveUser(@RequestBody List<UserInfoDTO> users) {
        TestToolResponse responseMessage = new TestToolResponse();
        try {
            boolean ret = testCaseService.saveUserList(users);
//            return ResponseEntity.status(HttpStatus.CREATED).build();
            if(ret) {
                responseMessage.setCode(100);
                responseMessage.setDesc("ok");
            }
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 500
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        return null;
    }

    /**
     * 更新号码资源
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public TestToolResponse updateUser(@RequestBody UserInfoDTO user) {
        TestToolResponse responseMessage = new TestToolResponse();
        try {
            boolean ret = testCaseService.updateUser(user);
            if(ret) {
                responseMessage.setCode(100);
                responseMessage.setDesc("ok");
            }
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 500
        return responseMessage;
    }

    /**
     * 删除号码资源
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public TestToolResponse deleteUser(@RequestParam(value = "id", defaultValue = "0") Long id) {
        TestToolResponse responseMessage = new TestToolResponse();
        responseMessage.setCode(500);
        try {
            if (id.intValue() == 0) {
                // 请求参数有误
                responseMessage.setCode(400);
                responseMessage.setDesc("params error");
                return responseMessage;
            }
            boolean ret = testCaseService.deleteUserById(id);
            if(ret) {
                responseMessage.setCode(100);
                responseMessage.setDesc("ok");
            }
            return responseMessage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 500
        responseMessage.setDesc("server internal error");
        return responseMessage;
    }
}