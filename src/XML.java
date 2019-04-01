import java.io.StringReader;
import java.lang.reflect.Method;

 import java.io.FileInputStream;
import java.io.IOException;
 import java.util.ArrayList;
import java.util.List;
 import org.xmlpull.v1.XmlPullParser;
 import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XML {

    public static void main(String[] args) {

        Personne personne = null;
        ArrayList<Personne> listePersonne = new ArrayList<Personne>();

        String XMLFile = "<personnes><personne genre=\"feminin\"><nom>Diallo</nom>"
                + "<prenom>Binta</prenom><age>15</age></personne><personne genre=\"masculin\">"
                + "<nom>Ndiaye</nom><prenom>Alioune</prenom><age>25</age></personne></personnes>";

        XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(XMLFile));
            String valeur = null;
            int eventType = xpp.getEventType();
            // Tant qu'on n'est pas à la fin du document
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // Si on est au debut d'un tag
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("personne")) {
                        personne = new Personne();
                        personne.setGenre(xpp.getAttributeValue(null, "genre"));
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equals("personne")) {
                        listePersonne.add(personne);
                    }
                    if (xpp.getName().equals("nom")) {
                        personne.setNom(valeur);
                        valeur = null;
                    }
                    if (xpp.getName().equals("prenom")) {
                        personne.setPrenom(valeur);
                        valeur = null;
                    }
                    if (xpp.getName().equals("age")) {
                        personne.setAge(Integer.valueOf(valeur));
                        valeur = null;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    // recuperation de la valeur pour le tag actuel
                    valeur = xpp.getText();
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Personne personn : listePersonne) {
            System.out.println( personn.toString());
        }

//
//        try{
//            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
//            XmlPullParser parser=factory.newPullParser();
//            //获取输入流
////            FileInputStream fis = new FileInputStream("config.xml");
////            //设置流和字符集
////            StringReader
//
//            parser.setInput(new StringReader("config.xml"));
//            //开始解析
//            int event = parser.getEventType();
//
//            while (event != XmlPullParser.END_DOCUMENT) {
//
//
//                    String value = parser.getAttributeValue(null, "name");
//                    System.out.println(value);
//
//                event = parser.next();
//            }
//
//
//            System.out.println("Hello");
//        }catch (Exception e){
//
//        }

    }

}
