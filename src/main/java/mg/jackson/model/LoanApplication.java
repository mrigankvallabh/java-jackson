package mg.jackson.model;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(with = { Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES })
public class LoanApplication {
    @JsonAlias(value = { "name", "applicantName" })
    @JsonProperty(value = "name", defaultValue = "NOT PROVIDED", required = true)
    private String applicantName;
    private String purposeOfLoan;
    private LoanDetails loanDetails;
    private Map<String, Job> jobs;

    public LoanApplication() {
    }

    @JsonCreator // Can be used when dealing with Immutable LoanApplication class
    public LoanApplication(
            @JsonProperty("name") final String applicantName,
            @JsonProperty("purposeOfLoan") String purposeOfLoan,
            @JsonProperty("loanDetails") LoanDetails loanDetails,
            @JsonProperty("jobs") List<Job> jobs) {
        this.applicantName = applicantName;
        this.purposeOfLoan = purposeOfLoan;
        this.loanDetails = loanDetails;
        this.jobs = setJobsJson(jobs);
        ;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getPurposeOfLoan() {
        return purposeOfLoan;
    }

    public void setPurposeOfLoan(final String purposeOfLoan) {
        this.purposeOfLoan = purposeOfLoan;
    }

    public LoanDetails getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(final LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
    }

    @JsonProperty("jobs")
    public List<Job> getJobsJson() {
        return jobs.values().stream().toList();
    }

    @JsonProperty("jobs")
    public Map<String, Job> setJobsJson(final List<Job> jobs) {
        return this.jobs = jobs.stream().collect(toMap(Job::getTitle, job -> job));
    }

    @JsonIgnore
    public Map<String, Job> getJobs() {
        return jobs;
    }

    @JsonIgnore
    public void setJobs(Map<String, Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return "LoanApplication{" +
                "name='" + applicantName + '\'' +
                ", purposeOfLoan='" + purposeOfLoan + '\'' +
                ", loanDetails=\n\t" + loanDetails +
                ", jobs=\n\t" + jobs.values().stream().map(Job::toString).collect(joining("\n\t\t", "[\n\t\t", "\n\t]"))
                +
                '}';
    }

}
