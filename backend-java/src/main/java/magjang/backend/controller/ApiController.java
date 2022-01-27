package magjang.backend.controller;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class ApiController {

    private final String CLIENT_ID = "XcnVRW5TXoLW3FDEkiUJ"; //애플리케이션 클라이언트 아이디값";
    private final String CLI_SECRET = "dxLkip23Qz"; //애플리케이션 클라이언트 시크릿값";
    private final String CALL_BACK = "http://localhost:8080/api/navercallback";
    private final String FRONT = "http://localhost:8083";

    @GetMapping("/naverloginrequest")
    public String naverLoginRequest() throws UnsupportedEncodingException, UnknownHostException {
//        String redirectURI = URLEncoder.encode("http://localhost:8080/api/navercallback", "UTF-8");
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();
        String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
        apiURL += String.format("&client_id=%s&redirect_uri=%s&state=%s",
                CLIENT_ID, CALL_BACK, state);
        return "redirect:" + apiURL;
    }

    /**
     * 콜백 페이지 컨트롤러
     * @param request
     * @return
     * @throws IOException
     * @throws ParseException
     */

    @GetMapping("/navercallback")
    public String naverCallBack(HttpServletRequest request) throws IOException, ParseException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String apiURL;
        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
        apiURL += "client_id=" + CLIENT_ID;
        apiURL += "&client_secret=" + CLI_SECRET;
        apiURL += "&redirect_uri=" + CALL_BACK;
        apiURL += "&code=" + code;
        apiURL += "&state=" + state;
        System.out.println("apiURL=" + apiURL);
        String res = requestToServer(apiURL);

        String token="";
        if(res != null && !res.equals("")) {
            Map<String, Object> parsedJson = new JSONParser(res).parseObject();
            System.out.println(parsedJson);

            // todo: 얻어온 토큰 이용해서 유저 정보 얻고 DB 확인

            String VueUrl = FRONT + "/redirect";
            VueUrl += "?accesstoken=" + parsedJson.get("access_token");
            VueUrl += "&refreshtoken=" + parsedJson.get("refresh_token");

            return "redirect:" + VueUrl;
        }
        System.out.println("fail");
        return "redirect:" + FRONT;
    }

    /**
     * 토큰 삭제 컨트롤러(로그아웃)
     * @param accessToken
     * @return
     * @throws IOException
     */
    @PostMapping("/naver/deleteToken")
    public String deleteToken(String accessToken) throws IOException {
        String apiURL;
        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=delete&";
        apiURL += "client_id=" + CLIENT_ID;
        apiURL += "&client_secret=" + CLI_SECRET;
        apiURL += "&access_token=" + accessToken;
        apiURL += "&service_provider=NAVER";
        System.out.println("apiURL=" + apiURL);
        return "redirect:" + FRONT;
    }

    /**
     * 서버 통신 메소드
     * @param apiURL
     * @return
     * @throws IOException
     */

    private String requestToServer(String apiURL) throws IOException {
        return requestToServer(apiURL, "");
    }

    /**
     * 서버 통신 메소드
     * @param apiURL
     * @param headerStr
     * @return
     * @throws IOException
     */
    private String requestToServer(String apiURL, String headerStr) throws IOException {
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        System.out.println("header Str: " + headerStr);
        if(headerStr != null && !headerStr.equals("") ) {
            con.setRequestProperty("Authorization", headerStr);
        }
        int responseCode = con.getResponseCode();
        BufferedReader br;
        System.out.println("responseCode="+responseCode);
        if(responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer res = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            res.append(inputLine);
        }
        br.close();
        if(responseCode==200) {
            return res.toString();
        } else {
            return null;
        }
    }





}
