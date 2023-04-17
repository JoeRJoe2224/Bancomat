import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Carta implements Serializable {
    @Serial
    private static final long serialVersionUID = -6331633706781358104L;
    private int[] codice = new int[4];
    private int pin;
    private int puk;
    private LocalDate scadenza;
    private boolean attiva;

    public String getCodice() {
        return codice[0]+" "+codice[1]+" "+codice[2]+" "+codice[3];
    }

    public void setCodice(int[] codice) {
        this.codice = codice;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getPuk() {
        return puk;
    }

    public void setPuk(int puk) {
        this.puk = puk;
    }

    public LocalDate getScadenza() {
        return scadenza;
    }

    public void setScadenza(LocalDate scadenza) {
        this.scadenza = scadenza;
    }

    public boolean isAttiva() {
        return attiva;
    }

    public void setAttiva(boolean attiva) {
        this.attiva = attiva;
    }

    public Carta() {
        this.scadenza = LocalDate.now().plusYears(5);
        this.attiva = false;

        do {
            this.pin = (int) (((Math.random()*1000000)%99999)+1);
        }while (this.pin < 10000);

        do {
            this.puk = (int) (((Math.random()*10000)%999)+1);
        }while (this.puk < 100);

        for(int i = 0; i<4; i++)
            do {
                this.codice[i] = (int) ((Math.random() * 100000)%9999)+1;
            }while (this.codice[i] < 1000);
    }
}
