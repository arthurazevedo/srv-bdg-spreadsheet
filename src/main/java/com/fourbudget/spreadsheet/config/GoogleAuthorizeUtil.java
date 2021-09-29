package com.fourbudget.spreadsheet.config;

import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleAuthorizeUtil {

    private static final String APPLICATION_NAME = "Budget Generator";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String KEY_FILE_LOCATION = "budget-generator-3a45048e60ae.p12";
    private static final String SERVICE_ACCOUNT_EMAIL = "budget-generator-591@budget-generator.iam.gserviceaccount.com";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    private static Credential authorize() throws IOException, GeneralSecurityException, URISyntaxException {
        URL fileURL = GoogleAuthorizeUtil.class.getClassLoader().getResource(KEY_FILE_LOCATION);

        if (fileURL == null) {
            fileURL = (new File("tokens/" + KEY_FILE_LOCATION)).toURI().toURL();
        }

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
                .setServiceAccountPrivateKeyFromP12File(new File(fileURL.toURI()))
                .setServiceAccountScopes(SCOPES)
                .build();
    }

    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        try {
            Credential credential = authorize();

            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (Exception exception) {
            System.out.println(exception);
            throw new SpreadsheetApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
        }
    }
}
