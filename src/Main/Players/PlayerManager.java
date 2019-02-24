package Players;

import Exceptions.AdminException;
import Exceptions.NameLengthExcption;
import Exceptions.PLayerExistException;
import Exceptions.PasswordException;


import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerManager {
    public final String FILENAME = "players";
    public final String SCOREFILENAME = "score";
    private Map<String,Integer> scoreBoard = new HashMap<>();
    private Set<Player> players = new HashSet<>();
    private Player currentLoginPlayer = null;
    public PlayerManager(){
        load();
    }

    public boolean checkPassWord(String usrnm, String psw) throws IOException{
//        Player p= getPlayerWithName(usrnm);
//        if(p!=null){
//            return p.checkPassword(psw);
//        }
//        return false;
        BufferedReader br = null;

        try {
            String theURL = "http://[::1]:8000/Home.php/?user="+usrnm+"&pass="+psw; //this can point to any URL
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                //sb.append(System.lineSeparator());
            }
            return sb.toString().equals("1");

        }catch (MalformedURLException e){
            //throw e;
        }catch(IOException e){
            throw e;
        }finally {

            if (br != null) {
                try {
                    br.close();
                }catch (Exception e){
                    ;
                }
            }
        }
        return false;
    }

    public Map<String, Integer> getScoreBoard() {
        return new HashMap<>(scoreBoard);
    }

    public void setScoreBoard(Map<String,Integer> scoreBoard) throws AdminException{
        if (currentLoginPlayer instanceof Admin){
            this.scoreBoard = scoreBoard;
        }else{
            throw new AdminException();
        }
    }

    public void save(){
        try {
            PrintWriter writer = new PrintWriter(FILENAME, "UTF-8");
            for(Player p: players) {
                    writer.println(p.toString());
                }
                writer.close();

        }catch (Exception e){
            System.out.println("Can't Save file");
        }


        try {
            PrintWriter writer = new PrintWriter(SCOREFILENAME, "UTF-8");
                for(Map.Entry<String,Integer> e : scoreBoard.entrySet()){
                    writer.println("{\n" + e.getKey()+ "\n" +e.getValue().toString() +"\n}\n");
                }
            writer.close();

        }catch (Exception e){
            System.out.println("Can't Save file");
        }


    }

    public void load(){
        //loadPlayer();
    }

    private void loadScore() {
//        try {
//            List<String> lines = new CopyOnWriteArrayList<>(Files.readAllLines(Paths.get(SCOREFILENAME)));
//            List<String> templ = new ArrayList<>();
//            generateScore(lines, templ);
//        } catch (Exception e) {
//            System.out.println("Error reading Files");
//
//        }
        BufferedReader br = null;
        try {
            String theURL = "http://[::1]:8000/Score.php/?user="+currentLoginPlayer.getName()+"&pass="+currentLoginPlayer.getPsw(); //this can point to any URL
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            ArrayList<String> sb = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                sb.add(line);
            }
            generateScore(sb,new ArrayList<>());

        }catch (Exception e){
            System.out.println("Error");
        }finally {

            if (br != null) {
                try {
                    br.close();
                }catch (Exception e){
                    ;
                }
            }
        }

    }

    private void generateScore(List<String> lines, List<String> templ) {
        int depth=0;
        for(String line:lines){
            if(line.length()==0)
                continue;
            templ.add(line);
            if(line.contains("{"))
                depth++;
            if(line.contains("}"))
                depth--;
            if(depth==0){
                if(templ.size()==4)
                    scoreBoard.put(templ.get(1),Integer.parseInt(templ.get(2)));
                templ.clear();
            }
        }
    }

    private void loadPlayer() {
        try {
            List<String> lines = new CopyOnWriteArrayList<>(Files.readAllLines(Paths.get(FILENAME)));
            List<String> templ = new ArrayList<>();
            generatePlayer(lines, templ);
        } catch (Exception e) {
            System.out.println("Error reading Files");
        }
    }

    private void generatePlayer(List<String> lines, List<String> templ) {
        int depth=0;
        for(String line:lines){
            if(line.length()==0)
                continue;
            templ.add(line);
            if(line.contains("{//"))
                depth++;
            if(line.contains("}//"))
                depth--;
            if(depth==0){
                if(templ.get(0).startsWith("Admin")){
                    players.add(new Admin(templ));
                }else{
                    players.add(new Player(templ));
                }
                templ.clear();
            }
        }
    }

    public void createPlayer(String name, String psw) throws NameLengthExcption, PasswordException, PLayerExistException, IOException{
        try {
            byte[] bytesOfMessage = psw.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            BigInteger number = new BigInteger(1, thedigest);
            psw = number.toString(16);
        }catch (NoSuchAlgorithmException e){

        }
        if(name.length()<6 || name.contains("{") || name.contains("}")){
            throw new NameLengthExcption();
        }
        if(psw.length()<6){
            throw new PasswordException();
        }
//        if(getPlayerWithName(name)==null)
//            players.add(new Player(name,psw));
//        else throw new PLayerExistException();
//        save();
        BufferedReader br = null;
        try {
            String theURL = "http://[::1]:8000/NewUser.php/?user="+name+"&pass="+psw; //this can point to any URL
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            if(!sb.toString().equals("0")){
                throw new PLayerExistException();
            }
        }catch (MalformedURLException e){

        }catch (IOException e){
            throw e;
        }finally {
            if (br != null) {
                try {
                    br.close();
                }catch (Exception e){
                    ;
                }
            }
        }
    }


    public void login(String name, String psw) throws PasswordException, IOException{
        try {
            byte[] bytesOfMessage = psw.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            BigInteger number = new BigInteger(1, thedigest);
            psw = number.toString(16);
        }catch (NoSuchAlgorithmException e){

        }
        if(checkPassWord(name,psw)){
            currentLoginPlayer = new Player(name,psw);
            resetScoreBoard();
            loadScore();
        }else{
            throw new PasswordException();
        }

    }

    public void logout(){
        currentLoginPlayer =null;
    }


//    private Player getPlayerWithName(String name){
//        for(Player p: players){
//            if(p.getName().equals(name)){
//                return p;
//            }
//        }
//        return null;
//    }

    public Player getCurrentLoginPlayer() {
        return currentLoginPlayer;
    }

    public void updateScore(Player p, int score) throws IOException{
//        if(p==currentLoginPlayer || p instanceof Admin){
//            scoreBoard.put(p.name,score);
//        }
        BufferedReader br = null;
        try {

            String theURL = "http://[::1]:8000/ChangeScore.php/?user="+currentLoginPlayer.getName()+"&pass="+currentLoginPlayer.getPsw()+"&score="+Integer.toString(score); //this can point to any URL
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            loadScore();
        }catch (MalformedURLException e){

        }catch (IOException e){
            throw e;
        }finally {
            if (br != null) {
                try {
                    br.close();
                }catch (Exception e){
                    ;
                }
            }
        }

    }
    public void deleteAccount() throws IOException{
        String name = currentLoginPlayer.getName();
        String psw = currentLoginPlayer.getPsw();
        BufferedReader br = null;
        try {

            String theURL = "http://[::1]:8000/DeleteAccount.php/?user="+name+"&pass="+psw; //this can point to any URL
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        }catch (MalformedURLException e){

        }catch (IOException e){
            throw e;
        }finally {
            if (br != null) {
                try {
                    br.close();
                }catch (Exception e){
                    ;
                }
            }
        }

        players.remove(getCurrentLoginPlayer());
        scoreBoard.remove(getCurrentLoginPlayer());
        logout();
        save();
    }
    public void resetScoreBoard(){
        scoreBoard = new HashMap<>();
    }
    public void resetPlayer(){
        currentLoginPlayer = null;
    }
}
