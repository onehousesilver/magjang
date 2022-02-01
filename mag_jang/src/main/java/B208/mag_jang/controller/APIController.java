package B208.mag_jang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class APIController {
    private final String CLI_SECRET = "dxLkip23Qz";

    @GetMapping("/test")
    public String test(){
        System.out.println("TEST");
        return "TEST COMPLETE";  // String 반환하면 해당하는 html 불러낸다.. 객체 만들어서 쓸것!
    }
}
