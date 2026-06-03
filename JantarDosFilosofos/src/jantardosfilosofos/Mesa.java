package jantardosfilosofos;

import java.util.concurrent.Semaphore;

public class Mesa{
    Semaphore[] garfos;

    public Mesa() {
        garfos = new Semaphore[5];
        for (int i = 0; i < 5; i++)
            garfos[i] = new Semaphore(1); // 1 = garfo disponível, 0 = garfo indisponível
    }
}
