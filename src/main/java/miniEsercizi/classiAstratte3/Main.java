package miniEsercizi.classiAstratte3;

public class Main {
    public static void main(String[] args) {
        Server server = new Server("Ubuntu", "Intel", 8, 500, "SSD", "192.168.100.2", true, 34.50);
        System.out.println(server.toString());
    }
}
