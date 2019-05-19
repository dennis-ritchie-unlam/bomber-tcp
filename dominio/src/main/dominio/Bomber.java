package main.dominio;

public class Bomber {
    private boolean estaVivo;
    private int bombasDisponibles;
    private double posicionX;
    private double posicionY;

    public Bomber(double posX, double posY) {
        this.posicionX = posX;
        this.posicionY = posY;
        this.estaVivo = true;
        this.bombasDisponibles = 1;
    }

    public Bomba ponerBomba() {
        if (bombasDisponibles != 0) {
            this.bombasDisponibles--;
            return new Bomba(this.getPosicionX(), this.getPosicionY());
        }
        return null;
    }

    public void morir() {
        this.estaVivo = false;
    }

    public boolean EstaVivo() {
        return estaVivo;
    }

    public void revivir() {
        this.estaVivo = true;
    }

    public int getBombasDisponibles() {
        return bombasDisponibles;
    }

    public void setBombasDisponibles(int bombasDisponibles) {
        this.bombasDisponibles = bombasDisponibles;
    }

    public double getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    public double getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(double posicionY) {
        this.posicionY = posicionY;
    }
    
    public void moverse(double despX, double despY) {
        setPosicionX(posicionX + despX);
        setPosicionY(posicionY + despY);
    }
}
