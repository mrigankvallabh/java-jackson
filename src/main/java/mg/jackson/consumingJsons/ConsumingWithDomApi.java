package mg.jackson.consumingJsons;

import java.io.IOException;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsumingWithDomApi {
    private static final String FILE_PATH = "src/main/resources/bank_loan.json";
    private static final Path BANK_LOAN_FILE = Path.of(FILE_PATH);

    public static void main(String[] args) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonDom = mapper.readTree(BANK_LOAN_FILE.toFile());
        /*
        ObjectWriter prettyWriter = mapper.writerWithDefaultPrettyPrinter();
        System.out.println(prettyWriter.writeValueAsString(jsonTree));
        */

        if (validateDates(jsonDom)) {
            System.out.println("All Dates are formatted yyyy-mm-dd");
        } else {
            System.err.println("Some Dates not in yyyy-mm-dd format");
        }

        double totalIncome = getTotalIncome(jsonDom);
        System.out.println(totalIncome);
    }

    private static double getTotalIncome(JsonNode jsonNode) {
        JsonNode jobNodes = jsonNode.get("jobs");
        double totalIncome = 0.0;
        if (jobNodes != null) {
            for (JsonNode jobNode : jobNodes) {
                totalIncome += jobNode.get("annualIncome").asDouble(0.0);
            }
        }
        return totalIncome;
    }

    private static boolean validateDates(JsonNode jsonNode) {
        boolean valid = true;
        var fields = jsonNode.fields();
        while (fields.hasNext()) {
            var field = fields.next();
            String jsonKey = field.getKey();
            JsonNode jsonValue = field.getValue();
            if (jsonKey.toLowerCase().endsWith("date") && jsonValue.isTextual()) {
                System.out.println(jsonKey + " [String] -> " + jsonValue);
                try {
                    LocalDate parsedDate = LocalDate.parse(jsonValue.asText(), DateTimeFormatter.ISO_LOCAL_DATE);
                    System.out.println(jsonKey + " [Date] -> " + parsedDate);
                    valid = true;
                } catch (DateTimeException e) {
                    System.err.println("Invalid Date Format");
                    valid = false;
                }
            }
            if (jsonValue.isArray()) {
                for (JsonNode node : jsonValue) {
                    valid = validateDates(node);
                }
            }
            if (jsonValue.isObject()) {
                valid = validateDates(jsonValue);
            }
        }
        return valid;
    }
}
