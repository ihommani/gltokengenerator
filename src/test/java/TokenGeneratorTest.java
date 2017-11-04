import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenGeneratorTest {


    @Test
    void should_complain_on_the_vararg_size() throws IOException, URISyntaxException {
        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TokenGenerator.main();
            }
        }, "Please provide at least a key file path and a scope");

        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TokenGenerator.main("toto", "fsfs");
            }
        }, "Keyfile path does not exist. Please provide a valid path.");
    }
}