package mg.jackson.model;

import static java.time.Month.JANUARY;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExampleLoan
{
    public static final LoanApplication LOAN_APPLICATION;

    static
    {
        final LoanDetails loanDetails = new LoanDetails();
        loanDetails.setAmount(10000.00);
        loanDetails.setStartDate(LocalDate.of(2018, JANUARY, 15));
        loanDetails.setEndDate(LocalDate.of(2023, JANUARY, 15));

        final List<Job> jobs = new ArrayList<>();

        Job job = new Job();
        job.setTitle("Freelance Developer");
        job.setAnnualIncome(18000);
        job.setYearsActive(3);
        jobs.add(job);

        job = new Job();
        job.setTitle("Part Time Developer");
        job.setAnnualIncome(40000);
        job.setYearsActive(8);
        jobs.add(job);

        job = new Job();
        job.setTitle("Pluralsight Author");
        job.setAnnualIncome(13000);
        job.setYearsActive(1);
        jobs.add(job);

        LOAN_APPLICATION = new LoanApplication();
        LOAN_APPLICATION.setApplicantName("Mrs Joan Smith");
        LOAN_APPLICATION.setPurposeOfLoan("To build an extension to my house");
        LOAN_APPLICATION.setLoanDetails(loanDetails);
        LOAN_APPLICATION.setJobsJson(jobs);
    }
}
