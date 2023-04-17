import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Database data = Database.leggiConti();
        if(data == null) data = new Database();

        Menu myMenu = new Menu(data);
        myMenu.startMenu();

        data.salvaConti();
    }
}