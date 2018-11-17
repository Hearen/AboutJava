package site.lhearen.ajava.base.io;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class EncryptFilePath {
    public static void main(String... args) {
        String s = "text/binary/multimedia";
        System.out.println(getEncryptedPath(s));
    }

    private static String getEncryptedPath(String filePath) {
        String[] tokens = filePath.split("/");
        List<String> tList = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            tList.add(Hashing.md5().newHasher() // com.google.common.hash.Hashing;
                    .putString(tokens[i] + filePath, StandardCharsets.UTF_8).hash().toString()
                    .substring(2 * i, 2 * i + 5)); // to make it impossible to encrypt, add your custom secret here;
        }
        return String.join("/", tList);
    }
}
