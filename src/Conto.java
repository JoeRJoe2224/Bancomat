import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Conto  implements Serializable {
    @Serial
    private static final long serialVersionUID = 4242786442979368901L;
    private Account intestatario;
    private String iban;
    private int idConto;
    private Carta carta;
    private double saldo = 0;
    private List<Movimento> listaMovimenti = new ArrayList<>();

    public Account getIntestatario() {
        return intestatario;
    }

    public void setIntestatario(Account intestatario) {
        this.intestatario = intestatario;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getIdConto() {
        return idConto;
    }

    public void setIdConto(int idConto) {
        this.idConto = idConto;
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Movimento> getListaMovimenti() {
        return listaMovimenti;
    }

    public void setListaMovimenti(List<Movimento> listaMovimenti) {
        this.listaMovimenti = listaMovimenti;
    }

    public void aggiunguMovimento(Movimento movimento) {
        listaMovimenti.add(movimento);
    }

    public void cambiaStatoCarta() {
        carta.setAttiva(!carta.isAttiva());
    }

    public Conto(Account account, String iban) {
        setIntestatario(account);
        setIban(iban);
        setCarta(new Carta());
    }
}
