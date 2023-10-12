import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class AES {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string to encrypt: ");
        String input = scanner.nextLine();
        try {
            byte[] key = generateKey(256);
            String encrypted = AESKrypto.encrypt(input, key);
            System.out.println("Encrypted string: " + encrypted);
            String decrypted = AESKrypto.decrypt(encrypted, key);
            System.out.println("Decrypted string: " + decrypted);
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static class AESKrypto {
        private static final String ALGORITHM = "AES";

        public static String encrypt(String value, byte[] key) throws Exception {
            SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedValue = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedValue);
        }

        public static String decrypt(String value, byte[] key) throws Exception {
            SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decryptedValue = cipher.doFinal(Base64.getDecoder().decode(value));
            return new String(decryptedValue);
        }
    }

    public static byte[] generateKey(int keyLength) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[keyLength / 8];
        random.nextBytes(key);
        return key;
    }
}
