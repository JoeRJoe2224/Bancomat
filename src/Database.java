import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database implements Serializable {
    @Serial
    private static final long serialVersionUID = -7623624101684553740L;
    private Map<Integer,Conto> listaConti = new HashMap<Integer, Conto>();
    private List<Account> listaAccount = new ArrayList<>();
    int idProgressivo;

    public Database(){
        idProgressivo = 0;
    }

    public List<Account> getListaAccount() {
        return listaAccount;
    }

    public void setListaAccount(List<Account> listaAccount) {
        this.listaAccount = listaAccount;
    }

    public Map<Integer,Conto> getListaConti() {
        return listaConti;
    }

    public void setListaConti(Map<Integer,Conto> listaConti) {
        this.listaConti = listaConti;
    }

    public void creaAccount(Account account) {
        if(!accountExists(account.getMail())) {
            listaAccount.add(account);
        }
    }

    public void creaConto(Conto conto) {
        if(!contoExists(conto.getIban())) {
            idProgressivo++;
            conto.setIdConto(idProgressivo);
            listaConti.put(idProgressivo, conto);
        }
    }

    public void listaAccount() {
        for(Account c : listaAccount) {
            System.out.println(c.getMail());
        }
    }

    public void listaConti() {
        for(Conto c : listaConti.values()) {
            System.out.println(c.getIban());
        }
    }

    public void rimuoviConto(int id) {
        listaConti.remove(id);
    }

    public List<Integer> accountConto(Account account) {
        List<Integer> l = new ArrayList<>();
        for (Map.Entry<Integer, Conto> c : listaConti.entrySet()){
            if (c.getValue().getIntestatario().getMail().equals(account.getMail()))
                l.add(c.getKey());
        }
        return l;
    }

    public boolean contoExists(String iban) {
        for(Conto c : listaConti.values()) {
            if(c.getIban().equals(iban)) return true;
        }
        return false;
    }

    public boolean accountExists(String mail) {
        for(Account c : listaAccount) {
            if(c.getMail().equals(mail)) return true;
        }
        return false;
    }

    public String existingAccountPassword(String mail) {
        for(Account c : listaAccount) {
            if(c.getMail().equals(mail)) return c.getPassword();
        }
        return "";
    }

    public Account getAccountByMail(String mail) {
        for(Account c : listaAccount) {
            if(c.getMail().equals(mail)) return c;
        }
        return null;
    }

    public Conto getContoById(int id) {
        return listaConti.get(id);
    }

    public Conto getContoByIban(String iban) {
        for (Conto c : listaConti.values())
            if (c.getIban().equals(iban)) return c;
        return null;
    }

    public void salvaConti() throws IOException {

        FileOutputStream out = new FileOutputStream("D:\\Progetti Java\\Bancomat\\data\\conti.dat");
        ObjectOutputStream s = new ObjectOutputStream(out);
        s.writeObject(this);
        s.flush();
        s.close();
        out.close();
    }

    public static Database leggiConti() {
        try {
            FileInputStream in = new FileInputStream("D:\\Progetti Java\\Bancomat\\data\\conti.dat");
            ObjectInputStream s = new ObjectInputStream(in);
            Database conti = (Database) s.readObject();
            s.close();
            in.close();
            return conti;
        } catch (IOException ex) {
            System.out.println("Il file non esiste");
            return null;
        } catch (ClassNotFoundException ex) {
            System.out.println("Classe non trovata");
            return null;
        } catch (NullPointerException ex) {
            System.out.println("Il file è vuoto");
            return null;
        }
    }
}
