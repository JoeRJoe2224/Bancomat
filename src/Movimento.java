import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Movimento implements Serializable {
    @Serial
    private static final long serialVersionUID = 8204734799017448860L;
    private String ibanMittente;
    private String ibanDestinatario;
    private double quantita;
    private LocalDate data;
    private String causale;

    public String getIbanMittente() {
        return ibanMittente;
    }

    public void setIbanMittente(String ibanMittente) {
        this.ibanMittente = ibanMittente;
    }

    public String getIbanDestinatario() {
        return ibanDestinatario;
    }

    public void setIbanDestinatario(String ibanDestinatario) {
        this.ibanDestinatario = ibanDestinatario;
    }

    public double getQuantita() {
        return quantita;
    }

    public void setQuantita(double quantita) {
        this.quantita = quantita;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCausale() {
        return causale;
    }

    public void setCausale(String causale) {
        this.causale = causale;
    }

    public Movimento(String ibanMittente, String ibanDestinatario, double quantita, String causale) {
        this.ibanMittente =ibanMittente;
        this.ibanDestinatario = ibanDestinatario;
        this.quantita = quantita;
        this.causale = causale;
        this.data = LocalDate.now();
    }

}
