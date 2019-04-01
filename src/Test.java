import com.sun.org.apache.xerces.internal.impl.dv.xs.TypeValidator;

import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) {
        User user = new User();
        //使用反射进行参数注解


        TAG classAnnotation = User.class.getAnnotation(TAG.class);
        System.out.println(classAnnotation.Key());

        //测试注解
        Method[] methods = User.class.getMethods();
        for(Method method:methods){
//            System.out.println(method.getName());
            TAG methodAnnotation = method.getAnnotation(TAG.class);
            try {
                if (methodAnnotation != null){
                    method.invoke(user,methodAnnotation.Key());
                }
            } catch(Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println(user.toString());
    }

//    public class xmlParser<T>{
//        public T create(){
//            return T;
//        }
//    }

    public interface xmlParserObserver{
        void onNext(String msg);
        void onError(String msg);
        void onCompleted(String msg);
    }


}


