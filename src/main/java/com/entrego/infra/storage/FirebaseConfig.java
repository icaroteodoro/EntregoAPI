package com.entrego.infra.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            InputStream serviceAccount;

            // 1️⃣ Tenta carregar via variável de ambiente
            String firebaseConfig = System.getenv("FIREBASE_CONFIG_JSON");

            if (firebaseConfig != null && !firebaseConfig.isBlank()) {
                System.out.println("🔧 Carregando credenciais Firebase da variável de ambiente...");
                serviceAccount = new ByteArrayInputStream(
                        firebaseConfig.getBytes(StandardCharsets.UTF_8)
                );
            } else {
                // 2️⃣ Se não houver variável de ambiente, usa arquivo local
                System.out.println("🔧 Variável de ambiente não encontrada. Tentando carregar serviceAccountKey.json...");
                serviceAccount = getClass()
                        .getClassLoader()
                        .getResourceAsStream("serviceAccountKey.json");

                if (serviceAccount == null) {
                    throw new IllegalStateException(
                            "Arquivo serviceAccountKey.json não encontrado em src/main/resources"
                    );
                }
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("entrego-07.firebasestorage.app")
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("🔥 Firebase inicializado com sucesso!");
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao inicializar Firebase: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
