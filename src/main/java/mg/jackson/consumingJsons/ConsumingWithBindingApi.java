package mg.jackson.consumingJsons;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import mg.jackson.model.Job;
import mg.jackson.model.LoanApplication;

public class ConsumingWithBindingApi {

    private static final String FILE_PATH = "src/main/resources/bank_loan.json";
    private static final Path BANK_LOAN_FILE = Path.of(FILE_PATH);

    public static void main(String[] args) throws StreamReadException, DatabindException, IOException {
        LocalDateDeserializer dateDeserializer = new LocalDateDeserializer(DateTimeFormatter.ISO_DATE);
        var module = new SimpleModule().addDeserializer(LocalDate.class, dateDeserializer);
        final var mapper = new ObjectMapper().registerModule(module);
        LoanApplication loanApplication = mapper.readValue(BANK_LOAN_FILE.toFile(), LoanApplication.class);
        System.out.println(loanApplication);
        double totalIncome = loanApplication
                .getJobs()
                .values()
                .stream()
                .mapToDouble(Job::getAnnualIncome)
                .sum();
        System.out.println(totalIncome);
    }

}
