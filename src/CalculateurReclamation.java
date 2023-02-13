/**
 * Cette classe permet de calculer les montants a rembourser selon le type de contrat et le prix des soins.
 *
 * @version 12 février 2023
 */

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class CalculateurReclamation {

    static ArrayList<Double> montantsRembourses;
    private static String fichierEntree;
    private static String fichierSortie;

    public CalculateurReclamation() throws IOException, ParseException {
        this.montantsRembourses = ecrireSoinsRembourses();
    }

    /**
     * Genere les informations d'une reclamation a partir du fichier JSON
     *
     * @return Reclamation sous form d'ArrayList
     * @throws IOException
     * @throws ParseException
     */
    private static ArrayList<Soin> genererReclamation() throws IOException, ParseException {
        JSONHash JSON = new JSONHash(fichierEntree, fichierSortie);
        JSON.load();
        ArrayList<Soin> reclamation = new ArrayList();

        for (int i = 0; i < JSON.getNbSoin(); ++i) {
            reclamation.add(new Soin(JSON.getNumClient(), JSON.getContrat(), JSON.getMois(), JSON.getSoin(i),
                    JSON.getDate(i), (double) JSON.getMontant(i)));
        }
        return reclamation;
    }

    /**
     * Appelle la methode qui calcule les remboursements et retourne les donnes dans un Arraylist
     *
     * @return Un ArrayList de double
     * @throws IOException
     * @throws ParseException
     */
    public static ArrayList<Double> ecrireSoinsRembourses() throws IOException, ParseException {
        ArrayList<Double> soinsRembourses = new ArrayList<>();
        for (int i = 0; i < genererReclamation().size(); ++i) {
            soinsRembourses.add(genererReclamation().get(i).calculerRemboursement());
        }
        return soinsRembourses;
    }

    /**
     * Permet d'obtenir les informations de tous les soins auxquels on a applique un remboursement sous forme
     * d'ArrayList
     *
     * @return Un ArrayList des soins rembourses
     * @throws IOException
     * @throws ParseException
     */
    public static ArrayList<Soin> getSoinsRembourses() throws IOException, ParseException {
        ArrayList<Soin> soinsRembourses = new ArrayList();

        for (int i = 0; i < genererReclamation().size(); ++i) {
            soinsRembourses.add(new Soin(((Soin) genererReclamation().get(i)).getClient(),
                    ((Soin) genererReclamation().get(i)).getTypeContrat(),
                    ((Soin) genererReclamation().get(i)).getDateReclamation(),
                    ((Soin) genererReclamation().get(i)).getNumeroSoin(),
                    ((Soin) genererReclamation().get(i)).getDateSoin(), montantsRembourses.get(i)));
        }

        return soinsRembourses;
    }

    /**
     * Cette methode permet d'instancier le fichier d'entree
     * @param fichierEntree le fichier d'entree
     */
    public static void setFichierEntree(String fichierEntree) {
        CalculateurReclamation.fichierEntree = fichierEntree;
    }

    /**
     * Cette methode permet d'instancier le ficheir de sortie
     * @param fichierSortie le ficheir de sortie
     */
    public static void setFichierSortie(String fichierSortie) {
        CalculateurReclamation.fichierSortie = fichierSortie;
    }
}