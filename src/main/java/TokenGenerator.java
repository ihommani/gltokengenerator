import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

public class TokenGenerator {

    public static void main(String... args) throws IOException, URISyntaxException {

        Preconditions.checkState(args.length > 1, "Please provide at least a key file path and a scope");
        ArrayList<String> arguments = Lists.newArrayList(args);

        Path keyFilePath = Paths.get(arguments.remove(0));

        Preconditions.checkState(keyFilePath.toFile().exists(), "Keyfile path does not exist. Please provide a valid path.");
        Preconditions.checkState(keyFilePath.toFile().isFile(), "Keyfile path is not a path. Please provide a valid path.");
        Preconditions.checkState(keyFilePath.getFileName().toString().endsWith(".json"), "Keyfile seems not to be a json key file.");

        ServiceAccountCredentials serviceAccountCredentials = ServiceAccountCredentials
                .fromStream(Files.newInputStream(keyFilePath))
                .toBuilder()
                .setScopes(arguments)
                .build();

        AccessToken accessToken = serviceAccountCredentials.refreshAccessToken();
        System.out.println(accessToken.getTokenValue());
    }
}
