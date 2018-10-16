package com.miao.springbootswagger.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController
{
    private static Map<String, User> users=new ConcurrentHashMap<>();

    @ApiOperation(value = "添加用户", notes = "添加一条用户信息")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @PostMapping("/add")
    public User add(User user){
        String id=UUID();
        while(users.containsKey(id)){
            id=UUID();
        }
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @ApiOperation(value = "获取一个用户", notes = "根据用户id获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @GetMapping("/{id}")
    public User getOne(@PathVariable String id){
        if(!users.containsKey(id)){
            return null;
        }
        return users.get(id);
    }

    @ApiOperation(value = "获取所有用户信息列表")
    @GetMapping("/")
    public List<User> getUsers(){
        return new ArrayList<>(users.values());
    }

    @ApiOperation(value = "更新用户信息")
    @ApiImplicitParam(name = "user", value = "用户信息实体",required = true, dataType = "User")
    @PutMapping("/")
    public User updateOne(User u){
        User tmp = users.get(u.getId());
        if(tmp == null){
            return null;
        }
        if(u.getName()!=null){
            tmp.setName(u.getName());
        }
        if(u.getAge() != null){
            tmp.setAge(u.getAge());
        }
        return tmp;
    }

    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        if(users.containsKey(id)){
            users.remove(id);
        }
    }

    private String UUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
