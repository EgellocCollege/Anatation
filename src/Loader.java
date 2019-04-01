import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;

public class Loader{
        public static void main(String[] args) {

            String XMLFile = "<config>  \n" +
                    "    <student name=\"0\" sex=\"male\"/>  \n" +
                    "    <student name=\"1\" sex=\"female\"/>  \n" +
                    "    <student name=\"2\" sex=\"male\"/>  \n" +
                    "    <student name=\"3\" sex=\"female\"/>  \n" +
                    "    <student name=\"4\" sex=\"male\"/> \n" +
                    "    <student name=\"fuck\" sex=\"male\"/> \n" +
                    "</config>  ";

            new main.MagicParser().create(new StringReader(XMLFile),new main.onParserLisener(){

                @Override
                public void onParserError(String msg) {
                    System.out.println(msg);
                }

                @Override
                public void onParserOK(User user) {
                    System.out.println(user.toString() + "fuck");
                }
            });
        }

        public static class MagicParser{
            public void create(StringReader stringReader, main.onParserLisener lisener){

                TAG classAnnotation = User.class.getAnnotation(TAG.class);
                String classTag = classAnnotation.Key();
                System.out.println(classTag);


                XmlPullParserFactory factory;
                try {
                    factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(stringReader);

                    int eventType = xpp.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG && xpp.getName().equals(classTag)) {

                            User user = new User();
                            //测试注解
                            Method[] methods = User.class.getMethods();
                            for(Method method:methods){
                                TAG methodAnnotation = method.getAnnotation(TAG.class);
                                try {
                                    if (methodAnnotation != null){
                                        method.invoke(user,xpp.getAttributeValue(null, methodAnnotation.Key()));
                                    }
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            lisener.onParserOK(user);
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
