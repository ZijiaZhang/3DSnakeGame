package Players;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.security.*;
public class Player {
    protected String name;
    protected String psw;

    public Player(List<String> info){
        name = info.get(2);
        psw = info.get(4);
    }

    public Player(String name, String psw){
        this.name = name;

        this.psw = psw;
    }

    public String getName() {
        return name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPassword(String password){
        byte[] bytesOfMessage = password.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            psw = new String(thedigest, StandardCharsets.UTF_8);
        }catch (NoSuchAlgorithmException e){

        }
    }

    public boolean checkPassword(String psw){
        byte[] bytesOfMessage = psw.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            psw = new String(thedigest, StandardCharsets.UTF_8);
        }catch (NoSuchAlgorithmException e){

        }
        return psw.equals(this.psw);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Player{//\n" +
                "name= \n" + name + '\n' +
                "psw= \n" + psw + '\n' +
                "}//\n";
    }
}
