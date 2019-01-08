package com.worksap.morphling.raptor.dump.thread.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worksap.morphling.raptor.dump.thread.entity.ThreadDumpSimpleDo;
import com.worksap.morphling.raptor.dump.thread.service.UserDumpService;

@RestController
@RequestMapping("dump/user")
public class UserDumpInfoController {
    @Autowired
    UserDumpService userDumpService;

    @GetMapping("/")
    public List<ThreadDumpSimpleDo> getAllHistory() throws Exception {
        return userDumpService.getAllHistory();
    }

    @GetMapping("/{userName:.+}")
    public List<ThreadDumpSimpleDo> getUserHistory(@PathVariable String userName) throws Exception {
        return userDumpService.getUserHistory(userName);
    }

}
