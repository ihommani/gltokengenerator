import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class TokenGeneratorOldWay {

    private static HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            throw new RuntimeException("Error while initializing the HTTP transport.", t);
        }
    }

    /**
     * Google provides two kinds of credentials.
     * {@link com.google.api.client.auth.oauth2.Credential} and {@link com.google.auth.Credentials}.<br/>
     * The former is the newer and has a more fluent API.<br/>
     * However we need for some Google API clients (e.g: Sheet) we need to
     * provide and {@link com.google.api.client.http.HttpRequestInitializer} which is the case of {@link com.google.api.client.auth.oauth2.Credential}.<br/>
     * @param args
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static void main(String... args) throws GeneralSecurityException, IOException {

        Preconditions.checkState(args.length > 1, "Please provide at least a key file path and a scope");
        ArrayList<String> arguments = Lists.newArrayList(args);

        Path keyFilePath = Paths.get(arguments.remove(0));

        Preconditions.checkState(keyFilePath.toFile().exists(), "Keyfile path does not exist. Please provide a valid path.");
        Preconditions.checkState(keyFilePath.toFile().isFile(), "Keyfile path is not a path. Please provide a valid path.");
        Preconditions.checkState(keyFilePath.getFileName().toString().endsWith(".json"), "Keyfile seems not to be a json key file.");

        String serviceAccountId = arguments.remove(0);

        File p12File = Paths.get("/home/ismael/keyfile.p12").toFile();
        GoogleCredential googleCredential = new GoogleCredential.Builder()
                .setJsonFactory(JacksonFactory.getDefaultInstance())
                .setTransport(HTTP_TRANSPORT)
                .setServiceAccountId(serviceAccountId)
                .setServiceAccountPrivateKeyFromP12File(p12File)
                .setServiceAccountScopes(arguments)
                //.setServiceAccountUser("userEmail@impersonate.com")
                .build();


        googleCredential.refreshToken();
        System.out.println(googleCredential.getAccessToken());
    }
}
