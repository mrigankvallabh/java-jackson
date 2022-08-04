package mg.jackson.producingJsons;

import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import mg.jackson.model.ExampleLoan;
import mg.jackson.model.LoanApplication;

public class ProducingWithBinding {
    public static void main(String[] args) throws IOException {
        var loanApplication = ExampleLoan.LOAN_APPLICATION;
        System.out.println(loanApplication);
        System.out.println();
        toJsonString(loanApplication);
    }

    private static void toJsonString(LoanApplication loanApplication)
            throws StreamWriteException, DatabindException, IOException {
        ObjectMapper mapper = new ObjectMapper()
                // .registerModule(new ParameterNamesModule())
                // .registerModule(new Jdk8Module())
                // .registerModule(new JavaTimeModule())
                .findAndRegisterModules(); // Does same as above 3 by automatically registering

        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(System.out, loanApplication);
    }
}
