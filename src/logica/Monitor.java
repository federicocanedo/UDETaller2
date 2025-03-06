package src.logica;

public class Monitor {
    int lectores;
    boolean escribiendo;
    static final int maxLect = 10; // Definir maxLect

    public Monitor() {
            this.lectores = 0;
        this.escribiendo = false;
    }

    public synchronized void comienzoLectura() {
        while (this.escribiendo || this.lectores >= Monitor.maxLect) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Hilo interrumpido durante comienzoLectura");
            }
        }
        this.lectores++;
    }

    public synchronized void terminoLectura() {
        this.lectores--;
        if (this.lectores == 0) {
            notifyAll();
        }
    }

    public synchronized void comienzoEscritura() {
        while (this.escribiendo || this.lectores > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Hilo interrumpido durante comienzoEscritura");
            }
        }
        this.escribiendo = true;
    }

    public synchronized void terminoEscritura() {
        this.escribiendo = false;
        notifyAll();
    }
}