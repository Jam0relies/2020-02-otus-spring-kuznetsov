package ru.otus.homework12.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SecurityController {
    ////    private final RememberMeServices rememberMeServices;
//    private final AuthenticationManager authenticationManager;
//
//    public SecurityController(/*RememberMeServices rememberMeServices,*/ AuthenticationManager authenticationManager) {
////        this.rememberMeServices = rememberMeServices;
//        this.authenticationManager = authenticationManager;
//    }
//
//    @PostMapping("/api/auth/login")
//    public AuthenticationResponseDto login(@RequestBody AuthenticationRequestDto requestDto){
//        log.info("{}", requestDto);
//        String login= requestDto.getUsername();
//        String password = requestDto.getPassword();
//        Authentication authRequest = new UsernamePasswordAuthenticationToken(login,password);
//        Authentication auth = authenticationManager.authenticate(authRequest);
//        UserDetails userDetails = (UserDetails) auth.getPrincipal();
//
//        return new AuthenticationResponseDto(userDetails.getUsername());
//    }
    @PostMapping("/api/auth/refresh")
    @ResponseStatus(HttpStatus.OK)
    void refresh() {

    }
}
