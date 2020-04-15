package server.logic;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import server.model.JwtDecode;

@Service
public class JwtService {
    Gson gson = new Gson();

    private JwtDecode jwtDecode;

    public void decodeJwt(String jwtToken){
        String token = jwtToken.replace("Bearer ", "");

        System.out.println("------------ Decode JWT ------------");
        String[] split_string = token.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        Base64 base64Url = new Base64(true);
        String header = new String(base64Url.decode(base64EncodedHeader));
        System.out.println("JWT Header : " + header);

        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
        String body = new String(base64Url.decode(base64EncodedBody));
        System.out.println("JWT Body : "+body);
        System.out.println("JWT Body : "+body);

        // Hierin zitten de belangrijke properties van de token
        jwtDecode = gson.fromJson(body,JwtDecode.class );
    }

    public int getIdFromToken(){
        return jwtDecode.getId();
    }
}
