package tda.spring.back.util;
import java.security.SecureRandom;
import java.util.Random;
public class util {


        private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        private static final String NUMBER = "0123456789";
        private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

        private static final String PASSWORD_ALLOW_BASE;
        private static final String PASSWORD_ALLOW_BASE_SHUFFLED;
        private static final Random RANDOM = new SecureRandom();

        static {
            PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
            PASSWORD_ALLOW_BASE_SHUFFLED = shuffleString(PASSWORD_ALLOW_BASE);
        }

        private static String shuffleString(String string) {
            String[] parts = string.split("");
            for (int i = parts.length - 1; i > 0; i--) {
                int j = RANDOM.nextInt(i + 1);
                String temp = parts[i];
                parts[i] = parts[j];
                parts[j] = temp;
            }
            return String.join("", parts);
        }

        public static String generatePassword(int length) {
            if (length < 1) {
                throw new IllegalArgumentException("length must be at least 1");
            }
            StringBuilder sb = new StringBuilder(length);
            sb.append(PASSWORD_ALLOW_BASE_SHUFFLED.charAt(RANDOM.nextInt(CHAR_LOWER.length())));
            sb.append(PASSWORD_ALLOW_BASE_SHUFFLED.charAt(RANDOM.nextInt(CHAR_UPPER.length())));
            sb.append(PASSWORD_ALLOW_BASE_SHUFFLED.charAt(RANDOM.nextInt(NUMBER.length())));
            for (int i = 0; i < length - 3; i++) {
                sb.append(PASSWORD_ALLOW_BASE_SHUFFLED.charAt(RANDOM.nextInt(PASSWORD_ALLOW_BASE_SHUFFLED.length())));
            }
            return sb.toString();
        }
    }


