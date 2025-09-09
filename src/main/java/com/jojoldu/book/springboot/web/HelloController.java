package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// ★★★ 이 어노테이션이 가장 중요합니다! ★★★
// 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 줍니다.
@RestController
public class HelloController {

    // HTTP Method인 Get의 요청을 받을 수 있는 API를 만들어 줍니다.
    // /hello로 요청이 오면 문자열 hello를 반환하는 기능을 가지게 되었습니다.
    @GetMapping("/hello") // <--- hello가_리턴된다() 테스트를 위한 API
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto") // <--- helloDto가_리턴된다() 테스트를 위한 API
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}