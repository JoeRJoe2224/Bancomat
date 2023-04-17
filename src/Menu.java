import com.sun.tools.jconsole.JConsoleContext;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final Scanner scn = new Scanner(System.in);
    private Database data;
    private Account utente;
    List<Integer> idConti;
    private Conto contoSelezionato;
    public Menu(Database data) {
        this.data = data;
    }

    public void startMenu(){
        System.out.println("1. Login \n2. Registrati \n3. Esci");
        int risposta = scn.nextInt();
        switch (risposta) {
            case 1 -> loginMenu();
            case 2 -> registrazioneMenu();
            case 3 -> esci();
            default -> startMenu();
        }
    }

    private void esci() {
        System.out.println("Arrivederci!");
        scn.close();
    }

    public void accountMenu(){
        idConti = data.accountConto(utente);
        if(idConti.isEmpty()) {
            System.out.println("\nNon hai un conto a te intestato!\n1. Crea conto\n2. Torna al login");
            int risposta = scn.nextInt();
            switch (risposta) {
                case 1 -> creazioneContoMenu();
                case 2 -> {
                    idConti = null;
                    startMenu();
                }
                default -> accountMenu();
            }
        }
        else
        {
            System.out.println("\n1. Seleziona conto \n2. Crea nuovo conto\n3. Torna al login");
            int risposta = scn.nextInt();
            switch (risposta) {
                case 1 -> selezionaContoMenu();
                case 2 -> creazioneContoMenu();
                case 3 -> {
                    idConti = null;
                    startMenu();
                }
                default -> accountMenu();
            }
        }
    }

    public void selezionaContoMenu() {
        System.out.println("\nLista conti:");
        for(Integer i : idConti)
            System.out.println("Conto N° "+i+": "+data.getContoById(i).getSaldo());

        int id;
        do {
            System.out.println("Digitare id conto da selezionare");
            id = scn.nextInt();
            if(!idConti.contains(id)) System.out.println("Conto non presente!");
        }while(!idConti.contains(id));
        setContoSelezionato(data.getContoById(id));
        contoMenu();
    }

    public void contoMenu() {
        System.out.println("\nConto N° "+contoSelezionato.getIdConto());
        System.out.println("IBAN: "+contoSelezionato.getIban());
        System.out.println("Saldo: €"+contoSelezionato.getSaldo());
        if(!contoSelezionato.getCarta().isAttiva()) System.out.println("Carta: NON ATTIVATA");
        else
        {
            System.out.println("Carta:");
            System.out.println("  Codice: "+contoSelezionato.getCarta().getCodice());
            System.out.println("  PIN: "+contoSelezionato.getCarta().getPin());
            System.out.println("  PUK: "+contoSelezionato.getCarta().getPuk());
            System.out.println("  Scadenza: "+contoSelezionato.getCarta().getScadenza());
        }

        System.out.println("\n1. Visualizza movimenti \n2. Aggiungi saldo \n3. Impostazioni carta \n4. Esegui bonifico \n5. Torna al menù principale");
        int risposta = scn.nextInt();
        switch (risposta) {
            case 1 -> visualizzaMovimenti();
            case 2 -> aggiungiSaldo();
            case 3 -> settingCarta();
            case 4 -> bonifico();
            case 5 -> {
                contoSelezionato=null;
                accountMenu();
            }
            default -> contoMenu();
        }
    }

    private void bonifico() {
        String iban;
        double quantita;
        String causale;
        do {
            System.out.println("\nInserire IBAN destinatario: ");
            iban = scn.next();
            if (!data.contoExists(iban)) System.out.println("Conto non esistente!");
        }while (!data.contoExists(iban));
        do {
            System.out.println("Inserire importo: ");
            quantita = scn.nextDouble();
            if (quantita<=0) System.out.println("Inserire quantità positiva!");
            if (contoSelezionato.getSaldo()<quantita) System.out.println("Saldo troppo basso");
        }while (quantita<=0 || contoSelezionato.getSaldo()<quantita);
        System.out.println("Inserire causale: ");
        scn.nextLine();
        causale = scn.nextLine();
        Movimento bonifico = new Movimento(contoSelezionato.getIban(), iban, quantita, causale);
        contoSelezionato.aggiunguMovimento(bonifico);
        contoSelezionato.setSaldo(contoSelezionato.getSaldo()-quantita);
        data.getContoByIban(iban).aggiunguMovimento(bonifico);
        data.getContoByIban(iban).setSaldo(data.getContoByIban(iban).getSaldo()+quantita);
        System.out.println("Bonifico eseguito!");
        contoMenu();
    }

    private void settingCarta() {
        if(!contoSelezionato.getCarta().isAttiva()) System.out.println("La Carta non è attivata. \n1. Attiva carta \n2. Torna indietro");
        else System.out.println("La Carta è attivata. \n1. Disattiva carta \n2. Torna indietro");
        int risposta = scn.nextInt();
        switch (risposta) {
            case 1 -> {
                System.out.println("Operazione completata!");
                contoSelezionato.cambiaStatoCarta();
            }
            case 2 -> contoMenu();
            default -> settingCarta();
        }
        contoMenu();
    }

    private void aggiungiSaldo() {
        double saldo;
        do {
            System.out.println("\nInserire saldo da aggiungere: ");
            saldo = scn.nextDouble();
            if (saldo<=0) System.out.println("Inserire quantità positiva!");
        }while (saldo<=0);
        contoSelezionato.setSaldo(contoSelezionato.getSaldo()+saldo);
        System.out.println("Saldo aggiunto!");
        contoMenu();
    }

    private void visualizzaMovimenti() {
        List<Movimento> movimenti = contoSelezionato.getListaMovimenti();
        for(Movimento m : movimenti){
            System.out.print("\n"+m.getData()+" -> ");
            if(m.getIbanDestinatario().equals(contoSelezionato.getIban())) System.out.print("ENTRATA ");
            else System.out.print("USCITA ");
            System.out.print(m.getQuantita());
            if(m.getIbanDestinatario().equals(contoSelezionato.getIban())) System.out.print(" DA "+m.getIbanMittente());
            else System.out.print(" VERSO "+m.getIbanDestinatario());
            System.out.println(" - "+m.getCausale());
        }
        contoMenu();
    }

    private void creazioneContoMenu() {
        String iban;
        do {
            System.out.println("\nInserire IBAN univoco: ");
            iban = scn.next();
            if (data.contoExists(iban)) System.out.println("IBAN già presente!");
        }while(data.contoExists(iban));

        data.creaConto(new Conto(utente, iban));
        System.out.println("Conto creato!");
        accountMenu();
    }

    public void setUtente(Account utente) {
        this.utente = utente;
    }

    public void setContoSelezionato(Conto contoSelezionato) {
        this.contoSelezionato = contoSelezionato;
    }

    private void registrazioneMenu() {
        String mail;
        do {
            System.out.println("\nInserire nuova mail: ");
            mail = scn.next();
            if (data.accountExists(mail)) System.out.println("Mail già in uso!");
        } while (data.accountExists(mail));

        System.out.println("Inserire password: ");
        String password = scn.next();

        System.out.println("Inserire nome: ");
        String nome = scn.next();

        System.out.println("Inserire cognome: ");
        String cognome = scn.next();

        System.out.println("Inserire data di nascita (yyyy-mm-dd): ");
        LocalDate nascita = LocalDate.parse(scn.next());

        System.out.println("Inserire telefono: ");
        String telefono = scn.next();

        setUtente(new Account(nome, cognome, nascita, mail, password, telefono));
        data.creaAccount(utente);
        System.out.println("Account creato!");

        accountMenu();
    }

    private void loginMenu() {
        String mail;
        String password;
        do {
            System.out.println("\nInserire mail: ");
            mail = scn.next();
            if (!data.accountExists(mail)) System.out.println("Mail non esistente!");
        } while (!data.accountExists(mail));

        do {
            System.out.println("Inserire password: ");
            password = scn.next();
            if (!data.existingAccountPassword(mail).equals(password)) System.out.println("Password errata!");
        } while (!data.existingAccountPassword(mail).equals(password));

        setUtente(data.getAccountByMail(mail));
        System.out.println("Login effettuato!");

        accountMenu();
    }
}
