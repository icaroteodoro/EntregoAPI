package com.entrego.infra.storage;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageException;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.UUID;

@Service
public class FirebaseService {

    public String uploadStoreCoverImage(MultipartFile file, String storeName) throws IOException {
        String path = "stores/cover/" + generateFileName(storeName, file);
        return uploadToFirebase(file, path);
    }

    public String uploadStoreProfileImage(MultipartFile file, String storeName) throws IOException {
        String path = "stores/profile/" + generateFileName(storeName, file);
        return uploadToFirebase(file, path);
    }

    public String uploadProductImage(MultipartFile file, String productName) throws IOException {
        String path = "products/" + generateFileName(productName, file);
        return uploadToFirebase(file, path);
    }

    private String uploadToFirebase(MultipartFile file, String fullPath) throws IOException {
        try {
            Bucket bucket = StorageClient.getInstance().bucket();
            com.google.cloud.storage.Blob blob = bucket.create(fullPath, file.getBytes(), file.getContentType());
            return String.format(
                    "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                    bucket.getName(),
                    fullPath.replace("/", "%2F")
            );
        } catch (StorageException e) {
            throw new IOException("Erro ao enviar arquivo para o Firebase", e);
        }
    }

    private String generateFileName(String base, MultipartFile file) {
        String sanitized = base.toLowerCase().replaceAll("[^a-z0-9]", "-");
        return sanitized + "-" + UUID.randomUUID() + getFileExtension(file.getOriginalFilename());
    }

    private String getFileExtension(String filename) {
        return filename != null && filename.contains(".")
                ? filename.substring(filename.lastIndexOf("."))
                : "";
    }

    public boolean deleteImage(String imageUrl) {
        try {
            Bucket bucket = StorageClient.getInstance().bucket();

            String bucketName = bucket.getName();
            String prefix = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/", bucketName);

            if (!imageUrl.startsWith(prefix)) return false;

            // Extrai a parte codificada do path (ex: "products%2Farquivo.png")
            String encodedPath = imageUrl.substring(prefix.length());

            // Remove o parâmetro ?alt=media, se estiver presente
            int queryIndex = encodedPath.indexOf("?");
            if (queryIndex != -1) {
                encodedPath = encodedPath.substring(0, queryIndex);
            }

            // Decodifica para obter o caminho correto (ex: "products/arquivo.png")
            String decodedPath = URLDecoder.decode(encodedPath, StandardCharsets.UTF_8);

            com.google.cloud.storage.Blob blob = bucket.get(decodedPath);
            return blob != null && blob.delete();

        } catch (Exception e) {
            System.out.println("Erro ao deletar imagem: " + e.getMessage());
            return false;
        }
    }
}
