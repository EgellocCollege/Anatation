import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.lang.reflect.Method;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) {

        String XMLFile = "<config>  \n" +
                "    <student name=\"0\" sex=\"male\"/>  \n" +
                "    <student name=\"1\" sex=\"female\"/>  \n" +
                "    <student name=\"2\" sex=\"male\"/>  \n" +
                "    <student name=\"3\" sex=\"female\"/>  \n" +
                "    <student name=\"4\" sex=\"male\"/> \n" +
                "</config>  ";

        new MagicParser().create(new StringReader(XMLFile),new onParserLisener(){

            @Override
            public void onParserError(String msg) {
                System.out.println(msg);
            }

            @Override
            public void onParserOK(User user) {
                System.out.println(user.toString());
            }
        });
    }

    public static class MagicParser{
        public void create(StringReader stringReader,onParserLisener lisener){

            XmlPullParserFactory factory;
            try {
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(stringReader);

                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("student")) {
                        String name = xpp.getAttributeValue(null, "name");
                        String sex = xpp.getAttributeValue(null, "sex");
                        lisener.onParserOK(new User(name,sex));
                    }
                    eventType = xpp.next();
                }
            } catch (XmlPullParserException e) {
                lisener.onParserError("Exception to Parse MagicWin Config XML file");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                lisener.onParserError("Exception to Parse MagicWin Config XML file");
            }
        }
    }

    public interface onParserLisener{
        void onParserError(String msg);
        void onParserOK(User user);
    }

}
