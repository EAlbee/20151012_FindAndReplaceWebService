package attempt;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService()
public class Main {

    public static void main(String[] args) {
        //FindAndReplace findAndReplace = new FindAndReplace("input.txt", "output.txt", "findandreplace.txt");

        Object implementor = new FindAndReplace("input.txt", "output.txt", "findandreplace.txt");
        String address = "http://localhost:9000/FindAndReplace";
        Endpoint.publish(address, implementor);
    }
}
