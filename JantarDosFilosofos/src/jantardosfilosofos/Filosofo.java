package jantardosfilosofos;

public class Filosofo implements Runnable {
    int id;
    Mesa mesa = new Mesa();

    public Filosofo(int id, Mesa mesa) {
        this.id = id;
        this.mesa = mesa;
    }
    
    public void pensar(){
      try {
        System.out.println("Filosofo " + id + " pensando..");
        long intervalo = (long) (Math.random() * 2000) + 500; // entre 500ms e 2500ms
        Thread.sleep(intervalo);
      } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
      }
    }
    
    public void comer(){
        try {
            // pegar posicao dos garfos no array de garfos
            int esq = id;
            int dir = (id + 1) % 5;

            // lock() pega os dois mutexes ao mesmo tempo 
            // porem em java é preciso quebrar a simetria manualmente
            // lock(table.forks[left], table.forks[right]);        
            int primeiro, segundo;
            if (id % 2 == 0) {
                primeiro = esq;
                segundo = dir;
            } else {
                primeiro = dir;
                segundo = esq;
            }
            
            
            if(!mesa.garfos[primeiro].tryAcquire()) 
            { 
                return ; 
            }
            if(!mesa.garfos[segundo].tryAcquire()) 
            { 
                mesa.garfos[primeiro].release();
                return ; 
            }
            
            //mesa.garfos[primeiro].acquire();
            //mesa.garfos[segundo].acquire();
            
            System.out.println("Filosofo " + id + " comendo");
            Thread.sleep((long)(Math.random() * 1000));
            
            mesa.garfos[primeiro].release();
            mesa.garfos[segundo].release();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
      }
    }
    
    @Override
    public void run(){
        while (true){
            pensar();
            comer();   
        }
    }
}
