import com.google.gson.Gson;
import me.decentos.myJsonWriter.MyJson;
import me.decentos.myJsonWriter.MyJsonImpl;

public class MyJsonDemo {
    public static void main(String[] args) {
        Gson gson = new Gson();
        MyJson myJson = new MyJsonImpl();
        Object toJsonObj = new Object();
        System.out.println(toJsonObj);

        String json = myJson.toJson(toJsonObj);
        System.out.println(json);

        Object fromJsonObj = gson.fromJson(json, Object.class);
        System.out.println(fromJsonObj);
        System.out.println("Equals objects: " + toJsonObj.equals(fromJsonObj));
    }
}
