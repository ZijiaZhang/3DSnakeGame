package Players;


import java.util.List;

public class Admin extends Player {
    public Admin(List<String> info){
        super(info);
    }

    @Override
    public String toString() {
        return "Admin{//\n" +
                "name= \n" + name + '\n' +
                "psw= \n" + psw + '\n' +
                "}//\n";
    }
}
