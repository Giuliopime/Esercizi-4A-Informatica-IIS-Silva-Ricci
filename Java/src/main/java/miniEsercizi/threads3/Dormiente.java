package miniEsercizi.threads3;

public class Dormiente extends Thread {
    private final String nome;

    public Dormiente(String nome) {
        this.nome = nome;
    }

    @Override
    public void run() {
        try {
            System.out.println(nome + " dormo");
            Thread.sleep(1000);
            System.out.println(nome);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
