package mg.jackson.consumingJsons;

import java.io.IOException;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class ConsumeWithStreamingApi {
    private static final String FILE_PATH = "src/main/resources/bank_loan.json";
    private static final Path BANK_LOAN_FILE = Path.of(FILE_PATH);

    public static void main(String[] args) throws JsonParseException, IOException {
        JsonFactory factory = new JsonFactory();
        double amount = 0.0;
        double totalIncome = 0.0;
        try (final JsonParser parser = factory.createParser(BANK_LOAN_FILE.toFile())) {
            JsonToken token;

            while ((token = parser.nextToken()) != null ) {
                if (token.isScalarValue()) {
                    String currentName = parser.getCurrentName();
                    if (currentName != null) {
                        String text = parser.getText();
                        System.out.println(currentName + ": " + text);
                        if (currentName.equals("amount")) {
                            amount = Double.parseDouble(text);
                        } else if (currentName.equals("annualIncome")) {
                            totalIncome += Double.parseDouble(text);
                        }
                    }
                }
            }
        }

        System.out.println("Amount Borrowd: " + amount);
        System.out.println("Total Income: " + totalIncome);
    }
}
