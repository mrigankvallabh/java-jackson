package mg.jackson.producingJsons;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import mg.jackson.model.ExampleLoan;
import mg.jackson.model.Job;
import mg.jackson.model.LoanApplication;
import mg.jackson.model.LoanDetails;

public class ProducingWithGenerator {
    public static void main(String[] args) throws IOException {
        var loanApplication = ExampleLoan.LOAN_APPLICATION;
        System.out.println(loanApplication);
        System.out.println();
        toJsonString(loanApplication);
    }

    private static void toJsonString(final LoanApplication loanApplication) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator generator = jsonFactory.createGenerator(System.out, JsonEncoding.UTF8);
        generator.useDefaultPrettyPrinter(); // pretty print
        generator.writeStartObject();
        generator.writeStringField("name", loanApplication.getApplicantName());
        generator.writeStringField("purposeOfLoan", loanApplication.getPurposeOfLoan());
        toJsonString(generator, loanApplication.getLoanDetails());
        toJsonString(generator, loanApplication.getJobs().values().stream().toList());
        generator.writeEndObject();
        generator.flush();
    }

    private static void toJsonString(JsonGenerator generator, List<Job> jobs) throws IOException {
        generator.writeFieldName("jobs");
        generator.writeStartArray();
        for (Job job : jobs) {
            generator.writeStartObject();
            generator.writeStringField("title", job.getTitle());
            generator.writeNumberField("annualIncome", job.getAnnualIncome());
            generator.writeNumberField("yearsActive", job.getYearsActive());
            generator.writeEndObject();
        }
        generator.writeEndArray();
    }

    private static void toJsonString(JsonGenerator generator, LoanDetails loanDetails) throws IOException {
        generator.writeFieldName("loanDetails");
        generator.writeStartObject();
        generator.writeNumberField("amount", loanDetails.getAmount());
        generator.writeStringField("startDate", loanDetails.getStartDate().toString());
        generator.writeStringField("endDate", loanDetails.getEndDate().toString());
        generator.writeEndObject();
    }
}
