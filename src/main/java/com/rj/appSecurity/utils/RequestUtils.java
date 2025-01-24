package com.rj.appSecurity.utils;


import com.rj.appSecurity.domain.userDto.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import java.time.LocalDateTime;
import java.util.Map;

public class RequestUtils {

    public static Response getResponse(HttpServletRequest request, Map<?,?> data,String message , HttpStatus status) {
        return new Response(LocalDateTime.now().toString(),status.value(),request.getRequestURI(),HttpStatus.valueOf(status.value()) ,message,EMPTY,data);
    }
}
