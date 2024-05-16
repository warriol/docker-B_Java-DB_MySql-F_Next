package com.grupo5.SpringJpaToken.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class ConexionFirebase {

    @Value("${firebase.credentials.json}")
    private String credentialsJson;

    @Bean
    public Firestore startConnection() throws IOException {
        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(credentialsJson.getBytes());

        //FileInputStream serviceAccount = new FileInputStream(credentialsPath);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        return FirestoreClient.getFirestore();
    }
}
