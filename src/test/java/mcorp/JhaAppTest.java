package mcorp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class JhaAppTest {

    AppConfig appConfig = new AppConfig();

    @Test
    @SneakyThrows
    void testJackson() {
        ObjectMapper mapper = appConfig.getObjectMapper();
        String fileName = "mqttResponse.json";

        RadiatorDto radiatorDto = mapper.readValue(readTestFile(fileName), RadiatorDto.class);
        Assertions.assertEquals(21, radiatorDto.getLocalTemperature());
    }

    @SneakyThrows
    String readTestFile(String fileName){
        File file = Paths.get("./src/test/resources/responses/" + fileName).toFile();
        BufferedReader br = new BufferedReader(new FileReader(file));

        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        sb.append(line);
        int length = 0;
        while (line != null) {
            length = length + line.length();
            line = br.readLine();
            sb.append(line);
        }
        return sb.toString();
    }

}