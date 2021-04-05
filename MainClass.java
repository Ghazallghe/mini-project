import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Console;
import java.io.File;
import java.util.Collections;
import java.io.FileWriter;

public class MainClass {
    public static void main(String[] args) throws InterruptedException, IOException {
        clearScreen();
        welcomeDesign();
        TimeUnit.SECONDS.sleep(5);
        clearScreen();
        Registration();
    }
    public static void welcomeDesign(){
        changeCursor(getRows() / 4,getColumns() / 2 - 10);
        System.out.println(Colors.CYAN_BOLD + "Welcome to hangman" + Colors.RESET);
        changeCursor(getRows() / 4 + 5,getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "----" + "                                " + Colors.RED_BOLD_BRIGHT + "----" + Colors.RESET);
        changeCursor(getRows() / 4 + 6,getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "|   " + "                                " + Colors.RED_BOLD_BRIGHT + "|  |" + Colors.RESET);
        changeCursor(getRows() / 4 + 7,getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "|   " + "                                " + Colors.RED_BOLD_BRIGHT + "|  O" + Colors.RESET);
        changeCursor(getRows() / 4 + 8,getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "|   " + "                                " + Colors.RED_BOLD_BRIGHT + "| /|\\" + Colors.RESET);
        changeCursor(getRows() / 4 + 9,getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "|   " + "                                " + Colors.RED_BOLD_BRIGHT + "| / \\" + Colors.RESET);
        changeCursor(getRows() / 4 + 10,getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "|   " + "                                " + Colors.RED_BOLD_BRIGHT + "|" + Colors.RESET);
    }

    public static int getRows(){
        return Integer.parseInt(System.getenv("LINES"));
    }
    public static int getColumns(){
        return Integer.parseInt(System.getenv("COLUMNS"));
    }
    public static void changeCursor(int row, int column){
        char escCode = 0x1B;
        System.out.printf("%c[%d;%df",escCode,row,column);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void Registration() throws InterruptedException, IOException {
        changeCursor(5,getColumns() / 2 - 10);
        System.out.println(Colors.YELLOW_BOLD + "1.Signup");
        changeCursor(6,getColumns() / 2 - 10);
        System.out.println("2.Login");
        changeCursor(7,getColumns() / 2 - 10);
        System.out.println("3.How to play");
        changeCursor(8,getColumns() / 2 - 10);
        System.out.println("4.Exit" + Colors.RESET);
        int response = getResponse();
        if (response == 1) {
            SignUp s = new SignUp();
            clearScreen();
            s.username();

        } else if (response == 2) {
            clearScreen();
            Login l = new Login();
            l.username();

        }else if(response == 3){
            howToPlay();
        }else if(response == 4){
            clearScreen();
            LeaderBoard.leaderboardFile(LeaderBoard.order());
            System.exit(0);
        } else {
            clearScreen();
            changeCursor(5,getColumns() / 2 - 10);
            System.out.println(Colors.RED_BOLD_BRIGHT + "Invalid input please try again" + Colors.RESET);
            TimeUnit.SECONDS.sleep(2);
            clearScreen();
            Registration();
        }
    }
    public static int getResponse() {
        footer();
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }
    public static void startGameOrLeaderBoard() throws InterruptedException, IOException {
        changeCursor(5,getColumns() / 2 - 10);
        System.out.println(Colors.YELLOW_BOLD + "1.Start game");
        changeCursor(6,getColumns() / 2 - 10);
        System.out.println("2.Show leaderboard");
        changeCursor(7,getColumns() / 2 - 10);
        System.out.println("3.Exit" + Colors.RESET + Colors.RESET);
        int response = getResponse();
        if(response == 1){
            clearScreen();
            Game g = new Game();
            g.fillTheBlanks();
        }
        else if(response == 2){
            clearScreen();
            LeaderBoard.printOrder();
        }
        else if(response == 3){
            clearScreen();
            LeaderBoard.leaderboardFile(LeaderBoard.order());
            System.exit(0);
        }
    }
    public static void howToPlay() throws IOException, InterruptedException {
        clearScreen();
        File file = new File("howtoplay.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            System.out.println(Colors.CYAN_BOLD + st);
        }
        System.out.println(Colors.RESET);
        changeCursor(25,getColumns() / 2 - 15);
        System.out.println(Colors.YELLOW_BOLD + "1.Back to registration");
        changeCursor(26,getColumns() / 2 - 15);
        int response = getResponse();
        System.out.println("2.Exit" + Colors.RESET);
        if(response == 1){
            clearScreen();
            Registration();
        }else if(response == 2){
            clearScreen();
            LeaderBoard.leaderboardFile(LeaderBoard.order());
            System.exit(0);
        }else {
            clearScreen();
            changeCursor(10,getColumns() / 2 - 15);
            System.out.println(Colors.RED_BOLD_BRIGHT + "Invalid input please try again!!" + Colors.RESET);
            TimeUnit.SECONDS.sleep(2);
            howToPlay();
        }
    }
    public static void footer(){
        for (int i = 0; i < getColumns() + 2; i++) {
            changeCursor(getRows() - 5,i);
            System.out.print(Colors.YELLOW_BOLD + '-' + Colors.RESET);
        }
    }
}
class SignUp {
    ArrayList<Integer> scores = SignUp.scores();
    static int whichPlayer = 0;
    public SignUp() throws FileNotFoundException {
    }

    public static String input() {
        Scanner sc = new Scanner(System.in);
        String userOrPass = sc.next();
        return userOrPass;
    }

    static boolean checkUsername(String username) throws IOException {
        File usernameFile = new File("username.txt");
        Scanner scanUsername = new Scanner(usernameFile);
        while (scanUsername.hasNextLine()) {
            String usernameInFile = scanUsername.nextLine();
            if (username.equals(usernameInFile)) {
                scanUsername.close();
                return false;
            }
        }
        scanUsername.close();
        return true;
    }

    static void username() throws IOException, InterruptedException {
        File usernameFile = new File("username.txt");
        File passwordFile = new File("password.txt");
        File scoreFile = new File("score.txt");
        MainClass.clearScreen();
        MainClass.changeCursor(5,MainClass.getColumns() / 2 - 10);
        System.out.print(Colors.YELLOW_BOLD + "Username: " + Colors.RESET);
        String usernameInput = input();
        whichPlayer = whichPlayer(registeredUsernames(),usernameInput);
        if (checkUsername(usernameInput)) {
            myFileWriter(usernameFile,usernameInput);
            myFileWriter(scoreFile,"0");
            password();
        } else {
            MainClass.changeCursor(5,MainClass.getColumns() / 2 - 10);
            System.out.println(Colors.RED_BOLD_BRIGHT + "This username is already taken please try again!" + Colors.RESET);
            TimeUnit.SECONDS.sleep(2);
            MainClass.clearScreen();
            username();
        }
    }

    public static void myFileWriter(File file,String input) throws IOException {
        FileWriter usernameWriter = new FileWriter(file, true);
        usernameWriter.write(input);
        usernameWriter.write('\n');
        usernameWriter.close();
    }
    static boolean validPasswordCheck(String password) {
        String passwordRegex = "((?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w])).{6,}";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        return passwordMatcher.matches();
    }

    static void password() throws IOException, InterruptedException {
        Console console = System.console();
        File passwordFile = new File("password.txt");
        MainClass.clearScreen();
        MainClass.changeCursor(4,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.BLUE_BOLD + "Your password should consist of small and capital letters , numbers and one special character(!,@,#,..) !!" + Colors.RESET);
        MainClass.changeCursor(6,MainClass.getColumns() / 2 - 10);
        char[] pass = console.readPassword(Colors.YELLOW_BOLD + "Password: " + Colors.RESET);
        String passwordInput = "";
        for (int i = 0; i < pass.length; i++) {
            passwordInput += pass[i];
        }
        if (validPasswordCheck(passwordInput)) {
            myFileWriter(passwordFile,passwordInput);
            MainClass.clearScreen();
            MainClass.changeCursor(5,MainClass.getColumns() / 2 - 10);
            System.out.println(Colors.GREEN_BOLD_BRIGHT + "Your registration completed successfully :)" + Colors.RESET);
            TimeUnit.SECONDS.sleep(2);
            MainClass.clearScreen();
            MainClass.startGameOrLeaderBoard();
        } else {
            MainClass.changeCursor(5,MainClass.getColumns() / 2 - 10);
            MainClass.clearScreen();
            MainClass.changeCursor(19,MainClass.getColumns() / 2 - 20);
            System.out.println(Colors.RED_BOLD_BRIGHT + "Invalid password please try again! :(" + Colors.RESET);
            TimeUnit.SECONDS.sleep(2);
            MainClass.clearScreen();
            password();
        }
    }

    public static ArrayList<String> registeredUsernames() throws FileNotFoundException {
        File usernameFile = new File("username.txt");
        Scanner scanUsername = new Scanner(usernameFile);
        ArrayList<String> usernames = new ArrayList<String>();
        while (scanUsername.hasNextLine()) {
            usernames.add(scanUsername.nextLine());
        }
        return usernames;
    }
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(whichPlayer);
    }

    public static ArrayList<String> registeredPasswords() throws FileNotFoundException {
        File passwordFile = new File("password.txt");
        Scanner scanPassword = new Scanner(passwordFile);
        ArrayList<String> passwords = new ArrayList<String>();
        while (scanPassword.hasNextLine()) {
            passwords.add(scanPassword.nextLine());
        }
        return passwords;
    }
    public static ArrayList<Integer> scores() throws FileNotFoundException {
        File scoreFile = new File("score.txt");
        Scanner scanScore = new Scanner(scoreFile);
        ArrayList<Integer> scores = new ArrayList<>();
        while (scanScore.hasNextLine()){
            String temp = scanScore.nextLine();
            scores.add(Integer.parseInt(temp));
        }
        return scores;
    }
    public static int whichPlayer(ArrayList<String> registeredUsername,String input){
        for (int i = 0; i < registeredUsername.size(); i++) {
            if (registeredUsername.get(i).equals(input))
                return i;
        }
        return registeredUsername.size();
    }
}
class Login extends SignUp{
    public Login() throws FileNotFoundException {
    }

    static void username() throws IOException, InterruptedException {
        boolean flag = true;
        ArrayList<String> rUsernames = registeredUsernames();
        MainClass.changeCursor(5,MainClass.getColumns() / 2 - 10);
        System.out.print(Colors.YELLOW_BOLD + "Username: " + Colors.RESET);
        String loginUsername = input();
        for(int i = 0; i < rUsernames.size();i++){
            if(rUsernames.get(i).equals(loginUsername)){
                flag = false;
                whichPlayer = whichPlayer(rUsernames,loginUsername);
                if(checkPassword(i)){
                    MainClass.clearScreen();
                    MainClass.startGameOrLeaderBoard();
                    break;
                }
                else {
                    MainClass.clearScreen();
                    MainClass.changeCursor(5,MainClass.getColumns() / 2 - 10);
                    System.out.println(Colors.RED_BOLD_BRIGHT + "Incorrect password please try again" + Colors.RESET);
                    TimeUnit.SECONDS.sleep(2);
                    MainClass.clearScreen();
                    username();
                }
            }
        }
        if(flag) {
            MainClass.clearScreen();
            MainClass.changeCursor(5,MainClass.getColumns() / 2 - 10);
            System.out.println(Colors.RED_BOLD_BRIGHT + "This username isn't signed up yet please try again" + Colors.RESET);
            TimeUnit.SECONDS.sleep(2);
            MainClass.clearScreen();
            MainClass.Registration();
        }
    }
    static boolean checkPassword(int i) throws FileNotFoundException {
        Console console = System.console();
        ArrayList<String> rPasswords = registeredPasswords();
        MainClass.changeCursor(6,MainClass.getColumns() / 2 - 10);
        char[] pass = console.readPassword(Colors.YELLOW_BOLD + "Password: " + Colors.RESET);
        String loginPassword = "";
        for (int j = 0; j < pass.length; j++) {
            loginPassword += pass[j];
        }
        if(rPasswords.get(i).equals(loginPassword))
            return true;
        return false;
    }
}
class Game {
    File scoreFile = new File("score.txt");
    ArrayList<Integer> scores = SignUp.scores();

    public Game() throws FileNotFoundException {
    }

    public static String chooseRandomWord() {
        String[] wordsArray = {"tehran", "pizza", "banana", "new york", "advanced programming", "michael jordan",
                "lionel messi", "apple", "macaroni", "university", "intel", "kitten", "python", "java",
                "data structures", "algorithm", "assembly", "basketball", "hockey", "leader", "javascript",
                "toronto", "united states of america", "psychology", "chemistry", "breaking bad", "physics",
                "abstract classes", "linux kernel", "january", "march", "time travel", "twitter", "instagram",
                "dog breeds", "strawberry", "snow", "game of thrones", "batman", "ronaldo", "soccer",
                "hamburger", "italy", "greece", "albert einstein", "hangman", "clubhouse", "call of duty",
                "science", "theory of languages and automata"};
        int randomNumber = (int)(Math.random()*wordsArray.length);
        return wordsArray[randomNumber];
    }
    public static StringBuilder assignBlankSpace(String chosenWord){
        StringBuilder blankSpace = new StringBuilder("");
        for (int i = 0; i < chosenWord.length(); i++) {
            if(chosenWord.charAt(i) != ' ')
                blankSpace.append('-');
            else
                blankSpace.append(' ');
        }
        return blankSpace;
    }
    public static char getCharResponse(){
        MainClass.footer();
        Scanner input = new Scanner(System.in);
        return input.next().charAt(0);
    }
    public void fillTheBlanks() throws InterruptedException, IOException {
        String chosenWord = chooseRandomWord();
        StringBuilder blankSpace = assignBlankSpace(chosenWord);
        if(chosenWord.length() < 9){
            guessing(chosenWord,blankSpace,7);
        }
        else {
            guessing(chosenWord,blankSpace,14);
        }
    }
    void redOrGreen(int n,int errors){
        char[] table = new char[2 * n + 1];
        for (int i = 0; i <= 2 * n; i++) {
            if (i % 2 == 0) {
                table[i] = '|';
            } else if(i % 2 != 0) {
                table[i] = 'V';
            }
        }
        for (int i = 1; i < errors * 2; i += 2) {
            table[i] = 'X';
        }
        for (int i = 0; i < table.length; i++) {
            if(table[i] == 'V'){
                System.out.print(Colors.GREEN + table[i] + Colors.RESET);
            }
            else if(table[i] == 'X'){
                System.out.print(Colors.RED + table[i] + Colors.RESET);
            }
            else {
                System.out.print(table[i]);
            }
        }
    }
    public void guessing(String chosenWord, StringBuilder blankSpace, int n) throws InterruptedException, IOException {
        int errors = 0 , i = 0 , onlyOneHint = 0;
        boolean flag = true;
        String answers = "";
        callThemAll(n,errors,blankSpace,answers,onlyOneHint);
        while(errors < n) {
            String tempBlankSpace = blankSpace.toString();
            if (!tempBlankSpace.contains("-")) {
                win();
                flag = false;
                break;
            } else {
                char response = getCharResponse();
                if (response == '*' && onlyOneHint == 0) {
                        blankSpace = hint(blankSpace, chosenWord);
                        answers += alphabet(answers, blankSpace);
                    onlyOneHint++;
                    callThemAll(n, errors, blankSpace, answers, onlyOneHint);
                }else if(response == '*' && onlyOneHint > 0){
                    MainClass.changeCursor(19,MainClass.getColumns() / 2 - 20);
                    System.out.println(Colors.RED_BOLD_BRIGHT + "You already used your hint" + Colors.RESET);
                    TimeUnit.SECONDS.sleep(2);
                    callThemAll(n,errors,blankSpace,answers,onlyOneHint);
                } else {
                    if (answers.indexOf(response) == -1) {
                        answers += response;
                    } else {
                        MainClass.changeCursor(19,MainClass.getColumns() / 2 - 20);
                        System.out.println(Colors.RED_BOLD_BRIGHT + "You have already chosen this alphabet please try again!" +Colors.RESET);
                        TimeUnit.SECONDS.sleep(2);
                        callThemAll(n, errors, blankSpace, answers, onlyOneHint);
                        continue;
                    }
                    if (chosenWord.indexOf(response) != -1) {
                        blankSpace = correctAlphabets(response, blankSpace, chosenWord);
                    } else {
                        errors++;
                    }
                    callThemAll(n, errors, blankSpace, answers, onlyOneHint);
                }
            }
        }
        if(flag){
            gameOver(chosenWord);
        }
    }
    public static StringBuilder correctAlphabets(char response,StringBuilder blankSpace,String chosenWord){
        for (int i = 0; i < chosenWord.length(); i++) {
            if(chosenWord.charAt(i) == response){
                blankSpace.setCharAt(i, response);
            }
        }
        return blankSpace;
    }
    public static void printUsedAlphabets(String answers){
        for (int i = 0; i < answers.length(); i++) {
            MainClass.changeCursor(14,MainClass.getColumns() / 2 - (20 - 2 * i));
            System.out.print(Colors.CYAN_BOLD + answers.charAt(i) + Colors.RESET);
        }
    }
    public static void draw(int error){
        switch (error){
            case 0 : Hangman.first();
                break;
            case 1 : Hangman.second();
                break;
            case 2 : Hangman.third();
                break;
            case 3 : Hangman.forth();
                break;
            case 4 : Hangman.fifth();
                break;
            case 5 : Hangman.sixth();
                break;
            case 6 : Hangman.seventh();
                break;
            case 7 : Hangman.eight();
                break;
        }
    }
    public static void nineOrMore(int n,int error){
        if(n == 7){
            draw(error);
        }
        else{
            if(error % 2 == 0)
                draw(error / 2);
            else {
                draw((error - 1) / 2);
            }
        }
    }
    void callThemAll(int n, int errors, StringBuilder blankSpace, String answers,int onlyOneHint){
        MainClass.clearScreen();
        nineOrMore(n,errors);
        MainClass.changeCursor(12,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.BLUE_BRIGHT + blankSpace + Colors.RESET);
        printUsedAlphabets(answers);
        System.out.println();
        MainClass.changeCursor(10,MainClass.getColumns() / 2 + 10);
        System.out.println(Colors.YELLOW_BRIGHT+ "score:" + scores.get(SignUp.whichPlayer) + Colors.RESET);
        MainClass.changeCursor(15,MainClass.getColumns() / 2 - 20);
        redOrGreen(n,errors);
        System.out.println();
        MainClass.changeCursor(17,MainClass.getColumns() / 2 - 20);
        if(onlyOneHint == 0)
            System.out.println(Colors.PURPLE_BRIGHT + "*Hint (for using hint please enter *  you're allowed to use it once in the game and it will take 10 scores)" + Colors.RESET);
    }

    void win() throws IOException, InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        MainClass.clearScreen();
        MainClass.changeCursor(5,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "Congratulations you win,you earned 5 scores!" + Colors.RESET);
        scores.set(SignUp.whichPlayer,scores.get(SignUp.whichPlayer) + 5);
        rewriteScoresFile();
        TimeUnit.SECONDS.sleep(4);
        MainClass.clearScreen();
        MainClass.startGameOrLeaderBoard();
    }
    public static void deleteFile(File file) throws IOException {
        FileWriter usernameWriter = new FileWriter(file);
        usernameWriter.write("");
    }
    void gameOver(String chosenWord) throws InterruptedException, IOException {
        MainClass.clearScreen();
        MainClass.changeCursor(5,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.CYAN_BOLD_BRIGHT + chosenWord + Colors.RESET);
        MainClass.changeCursor(7,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.RED_BOLD_BRIGHT + "Game Over :(" + Colors.RESET);
        rewriteScoresFile();
        TimeUnit.SECONDS.sleep(5);
        MainClass.clearScreen();
        MainClass.startGameOrLeaderBoard();
    }
    StringBuilder hint(StringBuilder blankSpace,String chosenWord) throws InterruptedException {
        if(scores.get(SignUp.whichPlayer) >= 10) {
            scores.set(SignUp.whichPlayer, scores.get(SignUp.whichPlayer) - 10);
            int randomAlphabet = (int) (Math.random() * chosenWord.length());
            do {
                randomAlphabet = (int) (Math.random() * chosenWord.length());
                if (blankSpace.charAt(randomAlphabet) == '-') {
                    for (int i = 0; i < chosenWord.length(); i++) {
                        if(chosenWord.charAt(i) == chosenWord.charAt(randomAlphabet)){
                            blankSpace.setCharAt(i, chosenWord.charAt(randomAlphabet));
                        }
                    }
                    return blankSpace;
                }
            } while (blankSpace.charAt(randomAlphabet) != '-');
        }else {
            MainClass.changeCursor(19,MainClass.getColumns() / 2 - 20);
            System.out.println(Colors.RED_BOLD_BRIGHT + "You don't have enough scores :(" + Colors.RESET);
            TimeUnit.SECONDS.sleep(2);
        }
        return blankSpace;
    }
    char alphabet(String response,StringBuilder blankSpace){
        for (int i = 0; i < blankSpace.length(); i++) {
            if(blankSpace.charAt(i) != '-'){
                if(response.indexOf(i) == -1)
                    return blankSpace.charAt(i);
            }
        }
        return 0;
    }
    void rewriteScoresFile() throws IOException {
        deleteFile(scoreFile);
        for (int i = 0; i < scores.size(); i++) {
            SignUp.myFileWriter(scoreFile,scores.get(i).toString());
        }
    }
}
class Hangman{
    public static void first(){
        MainClass.changeCursor(5,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT +"----");
        MainClass.changeCursor(6,MainClass.getColumns() / 2 - 20);
        System.out.println("|");
        MainClass.changeCursor(7,MainClass.getColumns() / 2 - 20);
        System.out.println("|");
        MainClass.changeCursor(8,MainClass.getColumns() / 2 - 20);
        System.out.println("|");
        MainClass.changeCursor(9,MainClass.getColumns() / 2 - 20);
        System.out.println("|");
        MainClass.changeCursor(10,MainClass.getColumns() / 2 - 20);
        System.out.println("|" + Colors.RESET);
    }
    public static void second(){
        MainClass.changeCursor(5,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.RED_BOLD_BRIGHT + "----");
        MainClass.changeCursor(6,MainClass.getColumns() / 2 - 20);
        System.out.println("|  |");
        MainClass.changeCursor(7,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "|");
        MainClass.changeCursor(8,MainClass.getColumns() / 2 - 20);
        System.out.println("|");
        MainClass.changeCursor(9,MainClass.getColumns() / 2 - 20);
        System.out.println("|");
        MainClass.changeCursor(10,MainClass.getColumns() / 2 - 20);
        System.out.println("|" + Colors.RESET);
    }
    public static void third(){
        MainClass.changeCursor(5,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.RED_BOLD_BRIGHT + "----");
        MainClass.changeCursor(6,MainClass.getColumns() / 2 - 20);
        System.out.println("|  |");
        MainClass.changeCursor(7,MainClass.getColumns() / 2 - 20);
        System.out.println("|  O");
        MainClass.changeCursor(8,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "|");
        MainClass.changeCursor(9,MainClass.getColumns() / 2 - 20);
        System.out.println("|");
        MainClass.changeCursor(10,MainClass.getColumns() / 2 - 20);
        System.out.println("|" + Colors.RESET);
    }
    public static void forth(){
        MainClass.changeCursor(5,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.RED_BOLD_BRIGHT + "----");
        MainClass.changeCursor(6,MainClass.getColumns() / 2 - 20);
        System.out.println("|  |");
        MainClass.changeCursor(7,MainClass.getColumns() / 2 - 20);
        System.out.println("|  O");
        MainClass.changeCursor(8,MainClass.getColumns() / 2 - 20);
        System.out.println("| /");
        MainClass.changeCursor(9,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "|");
        MainClass.changeCursor(10,MainClass.getColumns() / 2 - 20);
        System.out.println("|" + Colors.RESET);
    }
    public static void fifth(){
        MainClass.changeCursor(5,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.RED_BOLD_BRIGHT + "----");
        MainClass.changeCursor(6,MainClass.getColumns() / 2 - 20);
        System.out.println("|  |");
        MainClass.changeCursor(7,MainClass.getColumns() / 2 - 20);
        System.out.println("|  O");
        MainClass.changeCursor(8,MainClass.getColumns() / 2 - 20);
        System.out.println("| /|");
        MainClass.changeCursor(9,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "|");
        MainClass.changeCursor(10,MainClass.getColumns() / 2 - 20);
        System.out.println("|" + Colors.RESET);
    }
    public static void sixth(){
        MainClass.changeCursor(5,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.RED_BOLD_BRIGHT + "----");
        MainClass.changeCursor(6,MainClass.getColumns() / 2 - 20);
        System.out.println("|  |");
        MainClass.changeCursor(7,MainClass.getColumns() / 2 - 20);
        System.out.println("|  O");
        MainClass.changeCursor(8,MainClass.getColumns() / 2 - 20);
        System.out.println("| /|\\");
        MainClass.changeCursor(9,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "|");
        MainClass.changeCursor(10,MainClass.getColumns() / 2 - 20);
        System.out.println("|" + Colors.RESET);
    }
    public static void seventh(){
        MainClass.changeCursor(5,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.RED_BOLD_BRIGHT + "----");
        MainClass.changeCursor(6,MainClass.getColumns() / 2 - 20);
        System.out.println("|  |");
        MainClass.changeCursor(7,MainClass.getColumns() / 2 - 20);
        System.out.println("|  O");
        MainClass.changeCursor(8,MainClass.getColumns() / 2 - 20);
        System.out.println("| /|\\");
        MainClass.changeCursor(9,MainClass.getColumns() / 2 - 20);
        System.out.println("| /");
        MainClass.changeCursor(10,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "|" + Colors.RESET);
    }
    public static void eight(){
        MainClass.changeCursor(5,MainClass.getColumns() / 2 - 20);
        System.out.println(Colors.RED_BOLD_BRIGHT + "----");
        MainClass.changeCursor(6,MainClass.getColumns() / 2 - 20);
        System.out.println("|  |");
        MainClass.changeCursor(7,MainClass.getColumns() / 2 - 20);
        System.out.println("|  O");
        MainClass.changeCursor(8,MainClass.getColumns() / 2 - 20);
        System.out.println("| /|\\");
        MainClass.changeCursor(9,MainClass.getColumns() / 2 - 20);
        System.out.println("| / \\");
        MainClass.changeCursor(10,MainClass.getColumns() / 2 - 20);
        System.out.println("|" + Colors.RESET);
    }
}
class LeaderBoard {
    public static String order() throws IOException, InterruptedException {
        ArrayList<String> registeredUsernames = SignUp.registeredUsernames();
        ArrayList<Integer> scores = SignUp.scores();
        for (int i = 0; i < scores.size() - 1; i++) {
            for (int j = 0; j < scores.size() - i - 1; j++) {
                if (scores.get(j) < scores.get(j + 1)) {
                    Collections.swap(registeredUsernames, j, j + 1);
                    Collections.swap(scores, j, j + 1);
                }
            }
        }
        String forFile = "";
        for (int i = 0; i < scores.size(); i++) {
            forFile += forFile(registeredUsernames.get(i),scores.get(i));
        }
        leaderboardFile(forFile);
        return forFile;
    }
    public static void options() throws IOException, InterruptedException {
        System.out.println();
        MainClass.changeCursor(25,MainClass.getColumns() / 2 - 10);
        System.out.println(Colors.YELLOW_BOLD + "1.Start Game");
        MainClass.changeCursor(26,MainClass.getColumns() / 2 - 10);
        System.out.println("2.exit" + Colors.RESET);
        if(MainClass.getResponse() == 1){
            MainClass.clearScreen();
            Game g = new Game();
            g.fillTheBlanks();
        }
        else {
            MainClass.clearScreen();
            System.exit(0);
        }
    }
    public static void printOrder() throws IOException, InterruptedException {
        MainClass.clearScreen();
        MainClass.changeCursor(5,0);
        System.out.println(Colors.CYAN_BOLD + order() + Colors.RESET);
        options();
    }
    public static String forFile(String username,int score){
        String forFile = "";
        forFile += username;
        for (int i = username.length(); i < 20; i++) {
            forFile += "-";
        }
        forFile += score;
        forFile += "\n";
        return forFile;
    }
    public static void leaderboardFile(String forFile) throws IOException {
        File leaderboard = new File("leaderboard.txt");
        Game.deleteFile(leaderboard);
        SignUp.myFileWriter(leaderboard,forFile);
    }
}
class Colors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";  // BLACK
    public static final String RED = "\033[0;31m";    // RED
    public static final String GREEN = "\033[0;32m";  // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";  // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";  // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";  // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";  // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";  // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";  // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
}


