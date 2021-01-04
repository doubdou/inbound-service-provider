package ai.cbz.inbound.service;

import ai.cbz.inbound.constant.TestCaseConst;
import ai.cbz.inbound.dao.RedisDao;
import ai.cbz.inbound.mybatis.UserInfoDTO;
import ai.cbz.inbound.mybatis.UserNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Jinzw
 * @Date: 2020/11/5 14:16
 */
@Service
public class TestCaseService {
    @Autowired
    private RedisDao redisDao;

    private Map<Long, UserInfoDTO> userMap;


    public TestCaseService(){
        this.userMap = new HashMap<Long, UserInfoDTO>();
    }

    public UserInfoDTO queryUserById(Long id){
        boolean contains = userMap.containsKey(id);
        if (contains){
            System.out.println("this id exists.");
            return userMap.get(id);
        }else {
            System.out.println("this id not exists.");
            return redisDao.hGetObject(TestCaseConst.REDIS_KEY,id.toString(),UserInfoDTO.class);
        }
    }

    public boolean saveUserList(List<UserInfoDTO> users){

        //1.检查用户是否已存在

        //2.更新到内存

        //3.设置到redis中
        for(UserInfoDTO user:users){
//            this.updateUser(user);
            userMap.put(user.getPhoneNumber(), user);
            System.out.println("---" + user.getPhoneNumber() + "---");
            Long phoneNumber = user.getPhoneNumber();

            redisDao.hSetObject(TestCaseConst.REDIS_KEY, phoneNumber.toString(), user);
            for(UserNode node:user.getNodes()){
                System.out.println("type:" + node.getTypeEnum() + " text:"+ node.getText()+ " timeout:" + node.getTimeout()+"\n");
            }
        }
        return true;
    }

    public  boolean updateUser(UserInfoDTO user){
        //需要检查内存中key是否存在

        //更新内存
        userMap.put(user.getPhoneNumber(), user);
        Long phoneNumber = user.getPhoneNumber();
        //更新redis
        redisDao.hSetObject(TestCaseConst.REDIS_KEY, phoneNumber.toString(),user);
        return true;
    }

    public boolean deleteUserById(Long phoneNumber){
        //检查是否存在

        //在内存中删除
        userMap.remove(phoneNumber);
        UserInfoDTO user = new UserInfoDTO(phoneNumber,null);
        //在redis中删除
        redisDao.hSetObject(TestCaseConst.REDIS_KEY,phoneNumber.toString(),user);
        return true;
    }
}
