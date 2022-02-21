package B208.mag_jang.controller;

import B208.mag_jang.domain.User;
import B208.mag_jang.service.UserService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

//@Controller
@RestController
@RequestMapping("/account")
public class UserController {
    private final UserService userService;
//    private final String NAVER_CLIENT_ID = "XcnVRW5TXoLW3FDEkiUJ";
//    private final String NAVER_CLI_SECRET = "dxLkip23Qz";

//    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/naverloginrequest")
//    public ResponseEntity<User> naverLoginRequest(String access_token) throws IOException {
//        String apiURL = "https://openapi.naver.com/v1/nid/me";
//        String headerStr = "Bearer " + access_token; // Bearer 다음에 공백 추가
//        String res = requestToServer(apiURL, headerStr);
//        if (res != null) {
//            JSONObject jObj = new JSONObject(res);
//            JSONObject response = jObj.getJSONObject("response");
//            String naverId = response.getString("id");
//            User user = userService.findUserUsingNaverId(naverId);  // null이 아니라면 return, null이라면 하나 생성 후 return
////        deleteToken(access_token);  // 이거 왜 연동 끊어지는 코드지..?
//            return ResponseEntity.ok()
//                    .body(user);  // String 반환하면 해당하는 html 불러낸다.. 객체 만들어서 쓸것!
//        } else {
//            return ResponseEntity.badRequest()
//                    .body(new User());
//        }
//    }

    @GetMapping("/kakaologinrequest")
    public ResponseEntity<User> kakaoLoginRequest(String code) throws IOException {
        String access_token = getReturnAccessToken(code);

        if (access_token == null)
            return ResponseEntity.badRequest()
                    .body(new User());

        String apiURL = "https://kapi.kakao.com/v2/user/me";
        String headerStr = "Bearer " + access_token;
        String res = requestToServer(apiURL, headerStr);
//        System.out.println(res);
        if (res != null) {
            JSONObject jObj = new JSONObject(res);
//            JSONObject response = jObj.getJSONObject("id");
            Integer kakaoId = jObj.getInt("id");
            // 토큰 받아오기
//            System.out.println(id);

            User user = userService.findUserUsingKakaoId(kakaoId);  // null이 아니라면 return, null이라면 하나 생성 후 return
//        deleteToken(access_token);  // 이거 왜 연동 끊어지는 코드지..?
            return ResponseEntity.ok()
                    .body(user);  // String 반환하면 해당하는 html 불러낸다.. 객체 만들어서 쓸것!
        } else {
            return ResponseEntity.badRequest()
                    .body(new User());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> requestData) throws IOException {
        String nickName = requestData.get("nickName");
        if (nickName == null || nickName.trim().isEmpty()) {
            return ResponseEntity.ok()
                    .body("닉네임이 비었습니다!");
        }
        Long userId = Long.parseLong(requestData.get("userId"));
        return ResponseEntity.ok()
                .body(userService.setNickName(userId, nickName));
//        JSONObject jObj = new JSONObject(requestData.get("params"));
//        String nickName = jObj.getString("nickName");
//        Long userId = jObj.getLong("userId");
//        System.out.println(nickName);
//        System.out.println(jObj);
    }

//    private void deleteToken(String access_token) throws IOException {  // 탈퇴 코드인 줄 몰랐네..
//        String apiURL;
//        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=delete&";
//        apiURL += "client_id=" + NAVER_CLIENT_ID;
//        apiURL += "&client_secret=" + NAVER_CLI_SECRET;
//        apiURL += "&access_token=" + access_token;
//        apiURL += "&service_provider=NAVER";
//        String res = requestToServer(apiURL);
//    }

//    @GetMapping("/rank")
//    public ResponseEntity<List<User>> getRank() throws IOException {
//        List<User> user = userService.getRank();
//        System.out.println(user);
//        return ResponseEntity.ok().body(user);
//    }
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
        if(headerStr != null && !headerStr.equals("") ) {
            con.setRequestProperty("Authorization", headerStr);
        }
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if(responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuilder res = new StringBuilder();
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

    public String getReturnAccessToken(String code) {
        String access_token = "";
        String refresh_token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //HttpURLConnection 설정 값 셋팅
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);


            // buffer 스트림 객체 값 셋팅 후 요청
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=2e27d4b0376f4dd2b90c1074925f3f60");  //앱 KEY VALUE
            sb.append("&redirect_uri=https://i6b208.p.ssafy.io/kakaocallback"); // 앱 CALLBACK 경로
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //  RETURN 값 result 변수에 저장
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String br_line = "";
            String result = "";

            while ((br_line = br.readLine()) != null) {
                result += br_line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);


            // 토큰 값 저장 및 리턴
            access_token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_token;
    }


}
