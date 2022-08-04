package mg.jackson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.jackson.model.LoanApplication;


@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(2)
@State(Scope.Thread)
public class JsonBenchmark {
    String bankLoanData;
    ObjectMapper objectMapper;
    JsonFactory jsonFactory;

    @Setup
    public void prepare() throws IOException
    {

        bankLoanData = new String(Files.readAllBytes((Path.of("src/main/resources/bank_loan.json"))));
        objectMapper = new ObjectMapper().findAndRegisterModules();
        jsonFactory = objectMapper.getFactory();
    }

    @Benchmark
    public void streaming(Blackhole blackhole) throws IOException
    {
        try (final JsonParser parser = jsonFactory.createParser(bankLoanData))
        {
            JsonToken token;
            while ((token = parser.nextToken()) != null)
            {
                if (token.isScalarValue())
                {
                    final String currentName = parser.getCurrentName();
                    if (currentName != null)
                    {
                        final String text = parser.getText();

                        blackhole.consume(text);
                    }
                }
            }
        }
    }

    @Benchmark
    public LoanApplication binding() throws IOException
    {
        return objectMapper.readValue(bankLoanData, LoanApplication.class);
    }

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }
}
