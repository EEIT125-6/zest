package xun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TESTAOP {

	
    @GetMapping("/hello")
    public String hello() {
        String s = "hello moneynote";
        System.out.println(s);

        return s;
    }
}
