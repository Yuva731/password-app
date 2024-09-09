import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PasswordGeneratorApp {

    
    interface Validator {
        boolean validate(String input);
    }

    
    static class PasswordValidator implements Validator {
        private final String type;

        public PasswordValidator(String type) {
            this.type = type;
        }

        @Override
        public boolean validate(String input) {
            switch (type) {
                case "letters":
                    return input.length() >= 5 && input.matches("[a-zA-Z]+");
                case "special":
                    return input.length() >= 5 && input.matches("[!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>/?]+");
                case "numbers":
                    return input.length() >= 5 && input.matches("[0-9]+");
                default:
                    return false;
            }
        }
    }

    
    static class PasswordGenerator {
        public static String generatePassword(String lettersInput, String specialCharsInput, String numbersInput) {
            
            List<Character> letters = new ArrayList<>();
            List<Character> specials = new ArrayList<>();
            List<Character> numbers = new ArrayList<>();

            for (char c : lettersInput.toCharArray()) {
                letters.add(c);
            }
            for (char c : specialCharsInput.toCharArray()) {
                specials.add(c);
            }
            for (char c : numbersInput.toCharArray()) {
                numbers.add(c);
            }

            Collections.shuffle(letters);
            Collections.shuffle(specials);
            Collections.shuffle(numbers);

            
            StringBuilder password = new StringBuilder();
            int length = 15;
            int letterIndex = 0, specialIndex = 0, numberIndex = 0;

            for (int i = 0; i < length; i++) {
                if (i % 3 == 0 && letterIndex < letters.size()) {
                    password.append(letters.get(letterIndex++));
                } else if (i % 3 == 1 && specialIndex < specials.size()) {
                    password.append(specials.get(specialIndex++));
                } else if (i % 3 == 2 && numberIndex < numbers.size()) {
                    password.append(numbers.get(numberIndex++));
                }
            }

            return password.toString();
        }
    }

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        Validator letterValidator = new PasswordValidator("letters");
        Validator specialValidator = new PasswordValidator("special");
        Validator numberValidator = new PasswordValidator("numbers");

        
        System.out.print("Enter a string with at least 5 letters: ");
        String lettersInput = scanner.nextLine();
        if (!letterValidator.validate(lettersInput)) {
            System.out.println("Invalid letters input. Please enter at least 5 letters.");
            return;
        }

        
        System.out.print("Enter at least 5 special characters: ");
        String specialCharsInput = scanner.nextLine();
        if (!specialValidator.validate(specialCharsInput)) {
            System.out.println("Invalid special characters input. Please enter at least 5 special characters.");
            return;
        }

        
        System.out.print("Enter at least 5 numbers: ");
        String numbersInput = scanner.nextLine();
        if (!numberValidator.validate(numbersInput)) {
            System.out.println("Invalid numbers input. Please enter at least 5 numbers.");
            return;
        }

        
        String password = PasswordGenerator.generatePassword(lettersInput, specialCharsInput, numbersInput);
        System.out.println("Generated Password: " + password);
    }
}
