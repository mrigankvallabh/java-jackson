package mg.jackson.producingJsons;

import static java.util.stream.Collectors.joining;

import java.util.List;

import mg.jackson.model.ExampleLoan;
import mg.jackson.model.Job;
import mg.jackson.model.LoanApplication;
import mg.jackson.model.LoanDetails;

public class ProducingWithStringFormats
{
    public static void main(String[] args)
    {
        LoanApplication loanApplication = ExampleLoan.LOAN_APPLICATION;
        System.out.println(loanApplication);
        System.out.println();
        System.out.println(toJsonString(loanApplication));
    }

    private static CharSequence toJsonString(final LoanApplication loanApplication) {
        return String.format(
                "{\n" +
                "  \"name\": \"%s\",\n" +
                "  \"purposeOfLoan\": \"%s\",\n" +
                "  \"loanDetails\": %s,\n" +
                "  \"jobs\": %s\n" +
                "}\n",
                loanApplication.getApplicantName(),
                loanApplication.getPurposeOfLoan(),
                toJsonString(loanApplication.getLoanDetails()),
                toJsonString(loanApplication.getJobsJson()));
    }

    private static CharSequence toJsonString(final List<Job> jobs)
    {
        return jobs
            .stream()
            .map(job -> String.format(
                "    {\n" +
                "      \"title\": \"%s\",\n" +
                "      \"annualIncome\": \"%g\",\n" +
                "      \"yearsActive\": \"%d\"\n" +
                "    }",
                job.getTitle(),
                job.getAnnualIncome(),
                job.getYearsActive()))
            .collect(joining(",\n", "[\n", "\n  ]"));
    }

    private static CharSequence toJsonString(final LoanDetails loanDetails)
    {
        return String.format(
                "{\n" +
                "    \"amount\": %g,\n" +
                "    \"startDate\": \"%s\",\n" +
                "    \"endDate\": \"%s\"\n" +
                "  }",
                loanDetails.getAmount(),
                loanDetails.getStartDate(),
                loanDetails.getEndDate());
    }
}
