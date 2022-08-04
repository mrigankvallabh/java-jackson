# JSON processing with Jackson
To run the code, please follow the below steps
1. Download the code from Git
2. Run `mvn clean package`
3. Run the `main` functions in the source files
4. There are JMH benchmarks to compare between Streaming and Binding versions in `src/test`
    1. `Extract the src/test/resources/citylots.json.gz` to  `src/test/resources/citylots.json`
    2. Then run the main functions in the `src/test/java`
