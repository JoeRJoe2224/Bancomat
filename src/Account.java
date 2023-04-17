import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 6969765457069380371L;
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private LocalDate dataCreazione;
    private String mail;
    private String password;
    private String telefono;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public LocalDate getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDate dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Account(String nome, String cognome, LocalDate dataDiNascita, String mail, String password, String telefono) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.dataCreazione = LocalDate.now();
        this.mail = mail;
        this.password = password;
        this.telefono = telefono;
    }
}
