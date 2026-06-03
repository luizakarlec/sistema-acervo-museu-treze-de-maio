package jantardosfilosofos;

public class Principal {

    public static void main(String[] args) {
        Mesa mesa = new Mesa();
        
        for (int i = 0; i < 5; i++) {
            Thread filosofo = new Thread(new Filosofo(i, mesa));
            filosofo.start();
        }
    }
}
