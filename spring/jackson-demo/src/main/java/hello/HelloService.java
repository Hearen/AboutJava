package hello;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class HelloService {
    public void testJackson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Car car0 = new Car("yellow", "renault");
        System.out.println(objectMapper.writeValueAsString(car0));

        String json1 = "{\"color\":\"Black\",\"type\":\"BMW\"}";
        // https://stackoverflow.com/questions/50253841/jackson-parsing-class-doesnt-work/50277038#50277038
        Car car1 = objectMapper.readValue(json1, Car.class);

        String jsonCarArray =
                "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>() {
        });
        listCar.forEach(System.out::println);

        String json2 = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Map<String, Object> map = objectMapper.readValue(json2, new TypeReference<Map<String, Object>>() {
        });
        map.values().forEach(System.out::println);
    }
}
