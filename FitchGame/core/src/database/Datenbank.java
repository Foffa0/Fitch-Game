package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Datenbank {
    
    /**
     * Prüft, ob die Datenbank leer ist und befüllt sie gegebenenfalls mit Startwerten
     * 
     * 
     */    
    public void checkDb() {
        String datenbank = pfadZumOberverzeichnis() + "database/highscores.sqlite";
        
        System.out.println("Ich suche nach der Datenbank in: \n"+datenbank);
        
        String treiber = "org.sqlite.JDBC";
        String praefix = "jdbc:sqlite:";
        String anfragestring = "SELECT LevelId FROM Highscore;";
        
        Connection c = null;
        
        try {
            Class.forName(treiber);
            
            c = DriverManager.getConnection(praefix + datenbank);
            System.out.println("Verbindung zu sqlite steht.");
        } catch (Exception e) {
            System.out.println("Fehler beim Erstellen der Verbindung: "+e);
        }
        
        try {
            Statement stmt = c.createStatement();            
            ResultSet rset = stmt.executeQuery(anfragestring);
            int count = 0;
            while (rset.next()){
                System.out.println(rset.getString("LevelId"));     //mit getString- bzw. getInt-Methoden des Ergebnismengen-Objekts jeweils die Daten herausholen
                count++;
            }
            
            if (count < 10) {
            	anfragestring = "DELETE FROM Highscore";
            	stmt = c.createStatement();            
                stmt.execute(anfragestring);
            	anfragestring = "INSERT INTO Highscore VALUES (1,0),(2,0),(3,0),(4,0),(5,0),(6,0),(7,0),(8,0),(9,0),(10,0);";
            	stmt = c.createStatement();            
                stmt.execute(anfragestring);
            }
            rset.close();
            stmt.close();
            c.close();
            System.out.println("Abgemeldet.\n");
        } catch (Exception e) {
            System.out.println(e.toString());
            
        }
    }
    
    public int getHighscore(int level) {
        String datenbank = pfadZumOberverzeichnis() + "database/highscores.sqlite";
        
        System.out.println("Ich suche nach der Datenbank in: \n"+datenbank);
        
        String treiber = "org.sqlite.JDBC";
        String praefix = "jdbc:sqlite:";
        String anfragestring = "SELECT Score FROM [Highscore] WHERE LevelId=" + String.valueOf(level);
        
        Connection c = null;
        
        int result = 0;
        
        try {
            Class.forName(treiber);
            
            c = DriverManager.getConnection(praefix + datenbank);
            System.out.println("Verbindung zu sqlite steht.");
        } catch (Exception e) {
            System.out.println("Fehler beim Erstellen der Verbindung: "+e);
        }
        
        try {
            Statement stmt = c.createStatement(); 
            ResultSet rset = stmt.executeQuery(anfragestring);
            while (rset.next()){
                result = rset.getInt("Score");
            }
            rset.close();
            stmt.close();
            c.close();
            System.out.println("Abgemeldet.\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }
    
    /**
     * Diese Methode gibt den absoluten Pfad zum Oberverzeichnis des aktuellen Ordners zurueck.
     * Zunaechst wird der Pfad zum aktuellen Verzeichnis ermittelt, aber *ohne* das Verzeichnis selbst.
     * Wenn sich die .class-Datei also im Order "C:\Projekte BlueJ\Projekt42\" befindet, gibt die Methode
     * "C:\Projekte BlueJ" zurueck, plus einem angehaengten "/" ("\\" ginge auch)
     * (Dabei wird in einem Zwischenschritt der Pfad "C:\Projekte%20BlueJ" ermittelt und dann in UTF 8 umgeformt.)
     * 
     * @return der Pfad zum Verzeichnis, in dem diese .class-Datei liegt, ohne dieses Verzeichnis selbst!
     */
    //http://stackoverflow.com/questions/16076911/how-to-find-absolute-path-from-a-relative-path-in-file-system
    private String pfadZumOberverzeichnis(){
        java.security.ProtectionDomain pd = getClass().getProtectionDomain(); //        java.security.ProtectionDomain pd =DBVerbindung.class.getProtectionDomain();
        if ( pd == null ) return null;
        java.security.CodeSource cs = pd.getCodeSource();
        if ( cs == null ) return null;
        java.net.URL url = cs.getLocation();
        if ( url == null ) return null;
        java.io.File f = new java.io.File( url.getFile() );
        if (f == null) return null;
    
        String pfad = "";
        try {
            pfad = f.getParentFile().getAbsolutePath();         // Ermitteln des Pfades zum Oberverzeichnis
            pfad = java.net.URLDecoder.decode(pfad, "utf-8");   // Rueckverwandeln von als "%20" dargestellten evtl. Leerzeichen und anderen Sonderzeichen
        } catch (Exception e) { }
        System.out.println("\nAkt. Verzeichnis: "+System.getProperty("user.dir"));
        System.out.println("Absoluter Pfad:  " +pfad);
        pfad = pfad.replace("bin","src");
        System.out.println("pfad: " + pfad);
        return pfad + "/";
        //wenn stattdessen "" zurueckgegeben wird, handelt es sich um einen rein relativen Pfad
        //wenn stattdessen zurueckgegeben wird "J:/TdI 2013/DEMO_Tester" + "/", handelt es sich um einen absoluten Pfad
    }
}