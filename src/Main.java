//Homework 2
//Adam Stahly
//Brief Description of Program
//  Reads a user input of String and displays the morse or letter equivalent.
//  Informs the user if input is not compatible with the program.
import java.util.*;
import java.io.*;
class MorseTranslator {
    private ArrayList<String> _morse;
    private ArrayList<Character> _letter;
    private static MorseTranslator _uniqueInstance;

    //Method to find a letter
    private int findLetter(Character l) {
        boolean found = false;
        int currentPosition = 0;
        while (currentPosition < _letter.size() && !found) {
            if (_letter.get(currentPosition).equals(l))
                found = true;
            else
                currentPosition++;
        }
        if (found)
            return currentPosition;
        else
            return -1;
    }

    //Method to find a morse
    private int findMorse(String m) {
        boolean found = false;
        int currentPosition = 0;
        while (currentPosition < _morse.size() && !found) {
            if (_morse.get(currentPosition).equals(m))
                found = true;
            else
                currentPosition++;
        }
        if (found)
            return currentPosition;
        else
            return -1;
    }

    //Default Constructor
    private MorseTranslator() {
        _morse = new ArrayList<String>();
        _letter = new ArrayList<Character>();
    }

    //Narg Constructor
    private MorseTranslator(int size) {
        _morse = new ArrayList<String>(size);
        _letter = new ArrayList<Character>(size);
    }

    //Default getInstance
    public static MorseTranslator getInstance() {
        if (_uniqueInstance == null)
            _uniqueInstance = new MorseTranslator();
        return _uniqueInstance;
    }

    //Narg getInstance
    public static MorseTranslator getInstance(int size) {
        if (_uniqueInstance == null)
            _uniqueInstance = new MorseTranslator(size);
        return _uniqueInstance;
    }

    //Add method
    public void addMorseLetter(String m, Character l) {
        _morse.add(m);
        _letter.add(l);
    }

    //Remove method
    public boolean removeMorseLetter(String m, Character l) {
        int currentPosition = findMorse(m);
        if (currentPosition != findLetter(l))
            return false;
        else if (currentPosition == -1)
            return false;
        else {
            _morse.remove(currentPosition);
            _letter.remove(currentPosition);
            return true;
        }
    }

    //Parallel modify method
    public boolean modifyMorseLetter(String oldM, Character oldL,
                                     String newM, Character newL) {
        int currentPositionOfMorse = findMorse(oldM);
        int currentPositionOfLetter = findLetter(oldL);
        if (currentPositionOfMorse != currentPositionOfLetter)
            return false;
        else if (currentPositionOfMorse == -1)
            return false;
        else {
            _morse.set(currentPositionOfMorse, newM);
            _letter.set(currentPositionOfLetter, newL);
            return true;
        }
    }

    //Method to get a letter equivalent of a morse
    public Character getLetterEquivalent(String morse) {
        int currentPosition = findMorse(morse);
        if (currentPosition == -1)
            return '\0';
        else
            return _letter.get(currentPosition);
    }

    //Method to get a morse equivalent of a letter
    public String getMorseEquivalent(Character letter) {
        int currentPosition = findLetter(letter);
        if (currentPosition == -1)
            return "";
        else
            return _morse.get(currentPosition);
    }

    //Method to translate multiple morse to a word
    public String translateMorseToWord(String morseCode) {
        if (!morseCode.contains(" ")) {
            return Character.toString(getLetterEquivalent(morseCode));
        }
        String[] morseWords = morseCode.split("\\s+");
        String translatedWord = "";
        for (String morse : morseWords)
            translatedWord += getLetterEquivalent(morse);
        return translatedWord;
    }

    //Method to translate a word into morse
    public String translateWordToMorse(String letters) {
        char currentCharacter;
        String currentString;
        String morseCode = "";
        if (letters.length() == 1) {
            morseCode = getMorseEquivalent(letters.charAt(0));
        }
        else {
            for (int i = 0; i < letters.length(); i++) {
                currentCharacter = letters.charAt(i);
                morseCode += getMorseEquivalent(currentCharacter) + " ";
            }
        }
        return morseCode;
    }

    //Method to return size of letter and morse ArrayLists
    public int size() {
        if (_morse.size() == _letter.size())
            return _morse.size();
        else
            return -1;
    }
}
public class Main {
    //Function to populate a morseTranslator object with a file
    public static void populate(MorseTranslator m) {
        try {
            Scanner infile = new Scanner(new File("morse.txt"));
            char letter;
            String morse;
            while (infile.hasNext()) {
                letter = infile.next().charAt(0);
                morse = infile.next();
                m.addMorseLetter(morse, letter);
            }
            infile.close();
        } catch (Exception e) {
            System.out.println("Problem with File");
            System.exit(-1);
        }
    }
    //Function to check if the user's input is compatible with the file
    public static String checkInput(String userInput) {
        boolean isValidInput = true;
        int i = 0;
        if (!userInput.equals(userInput.toUpperCase()))
            userInput = userInput.toUpperCase();
        while (i < userInput.length() && isValidInput) {
            if (!Character.isLetter(userInput.charAt(i)))
                isValidInput = false;
            else
                i++;
        }
        if (isValidInput)
            return userInput;
        else
            return "";
    }
    public static void main(String[] args) {
        MorseTranslator m = MorseTranslator.getInstance(30);
        Scanner scan = new Scanner(System.in);
        String userInput;
        String correctedInput;
        populate(m);
        System.out.println("Enter an Alphabetic Word or a Word in Morse"
                 + " Code Separated by Spaces or -1 to Stop: ");
        userInput = scan.nextLine();
        while (!userInput.equals("-1")) {
            if (userInput.matches("[.\\- ]+"))
                System.out.println(m.translateMorseToWord(userInput));
            else {
                correctedInput = checkInput(userInput);
                if (!correctedInput.isEmpty()) {
                    userInput = m.translateWordToMorse(correctedInput);
                    System.out.println(userInput);
                }
                else
                    System.out.println("Invalid Input");
            }
            userInput = scan.nextLine();
        }
        System.out.println("Thank you for using MorseTranslator! " +
                "Program will now end.");
    }
}
