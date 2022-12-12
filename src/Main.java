import com.fasterxml.jackson.databind.ObjectMapper;
import input.Input;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputPath = args[0];
        String outputPath = args[1];

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(inputPath), Input.class);
        System.out.println(inputData.getUsers().get(0).getCredentials().getName());
    }
}
