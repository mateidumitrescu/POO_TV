import actions.Action;
import application.Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Input;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputPath = args[0];
        String outputDirPath = args[1];

        //String inputPath = "checker/resources/in/basic_1.json";
        //String outputDirPath = "results.out";

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(inputPath), Input.class);
        Application application = Application.getInstance(inputData);

        ArrayNode output = objectMapper.createArrayNode();
        application.startActions(output);

        Application.setApplication(null);

        String[] firstSplitArray = inputPath.split("_");
        String firstSplit = firstSplitArray[firstSplitArray.length - 1];
        String outputPath = "basic_" + firstSplit;

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(outputDirPath), output);
        //objectWriter.writeValue(new File(inputPath.replace("in", "out")), output);
    }
}
