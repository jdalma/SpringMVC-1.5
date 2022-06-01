package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * {"username":"hello","age":20}
     * content-type: application/json
     */
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request , HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}" , messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("helloData = {}" , helloData);

        response.getWriter().write("ok");
    }

    /**
     * @RequestBody
     * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * @ResponseBody
     * - 모든 메서드에 @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @PostMapping("/request-body-json-v2")
    @ResponseBody
    public String requestBodyJsonV2(@RequestBody String messageBody) throws JsonProcessingException {

        log.info("messageBody = {}" , messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("helloData = {}" , helloData);

        return "ok";
    }

    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type: application/json)
     *
     */
    @PostMapping("/request-body-json-v3")
    @ResponseBody
    public String requestBodyJsonV3(@RequestBody HelloData helloData){

        log.info("helloData = {}" , helloData);

        return "ok";
    }

    @PostMapping("/request-body-json-v4")
    @ResponseBody
    public String requestBodyJsonV4(HttpEntity<HelloData> data){
        HelloData helloData = data.getBody();

        log.info("helloData = {}" , helloData);

        return "ok";
    }


    /**
    * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
    * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type: application/json)
    *
    * @ResponseBody 적용
    * - 메시지 바디 정보 직접 반환(view 조회X)
    * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용 (Accept: application/json)
    */
    @PostMapping("/request-body-json-v5")
    @ResponseBody
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData){

        log.info("helloData = {}" , helloData);

        return helloData;
    }
}
