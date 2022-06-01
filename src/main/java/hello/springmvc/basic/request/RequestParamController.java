package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {} , age = {}" , username , age);

        response.getWriter().write("ok");
    }

    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody 추가
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     * */
    @RequestMapping("/request-param-v2")
    @ResponseBody
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam int age){

        log.info("username = {} , age = {}" , memberName , age);

        return "ok";
    }

    /**
     * 쿼리 파라미터의 키값과 변수 이름이 똑같고 단순 타입이라면 자동으로 변수에 할당된다
     */
    @RequestMapping("/request-param-v4")
    @ResponseBody
    public String requestParamV4(String username, int age){

        log.info("username = {} , age = {}" , username , age);

        return "ok";
    }

    /**
     * @RequestParam
     * required = true 기본 값
     * 필수 파라미터가 없다면 400 Bad Request 에러가 난다
     * 하지만 쿼리 파라미터에 키가 존재하고 값이 없는 상태로 온다면 "공백"으로 받는다
     */
    @RequestMapping("/request-param-required")
    @ResponseBody
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age){

        /**
         * age 파라미터가 없으면 정상적으로 작동해야 하지만 프리미티브 타입은 null을 넣을 수 없기에 Integer로 받아준다
         */

        log.info("username = {} , age = {}" , username , age);

        return "ok";
    }

    /**
     * defaultValue는 쿼리 파라미터의 키 조차 없거나 , 키는 있지만 값이 없어도 설정한 기본 값이 적용된다
     */
    @RequestMapping("/request-param-default")
    @ResponseBody
    public String requestParamDefault(
            @RequestParam(required = true , defaultValue = "guest") String username,
            @RequestParam(required = false , defaultValue = "-1") int age){

        log.info("username = {} , age = {}" , username , age);

        return "ok";
    }

    @RequestMapping("/request-param-map")
    @ResponseBody
    public String requestParamMap(@RequestParam Map<String , Object> paramMap){

        log.info("username = {} , age = {}" , paramMap.get("username") , paramMap.get("age"));

        return "ok";
    }

    /**
     * 단일 키 다중 값이 가능하다
     */
    @RequestMapping("/request-param-multivaluemap")
    @ResponseBody
    public String requestParamMultiValueMap(@RequestParam MultiValueMap<String , Object> paramMap){

        log.info("username = {} , age = {}" , paramMap.get("username") , paramMap.get("age"));

        return "ok";
    }

    @RequestMapping("/model-attribute-v1")
    @ResponseBody
    public String modelAttributeV1(@ModelAttribute HelloData helloData){

        log.info("hello data = " + helloData);

        return "ok";
    }

    @RequestMapping("/model-attribute-v2")
    @ResponseBody
    public String modelAttributeV2(HelloData helloData){

        log.info("hello data = " + helloData);

        return "ok";
    }
}
