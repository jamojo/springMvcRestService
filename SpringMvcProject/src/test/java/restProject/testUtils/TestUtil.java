package restProject.testUtils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;


public class TestUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public static String convertObjectToJson(List objects) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(objects);
    }

}