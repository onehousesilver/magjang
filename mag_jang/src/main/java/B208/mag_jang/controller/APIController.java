package B208.mag_jang.controller;

import B208.mag_jang.domain.User;
import B208.mag_jang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
@RequestMapping("/api")
public class APIController {
    private final UserService userService;
    private final String CLI_SECRET = "dxLkip23Qz";

//    @Autowired
    public APIController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test(){
        System.out.println("TEST");
        User user = new User();
        user.setNickName("김주호");
//        userService.join(user);

        return user.toString();  // String 반환하면 해당하는 html 불러낸다.. 객체 만들어서 쓸것!
    }
}
