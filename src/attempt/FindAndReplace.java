package attempt;

import org.apache.log4j.Logger;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

//import java.util.logging.Logger;

/**
 * Sample Find and Replace program to practice debugging, unit testing, and refactoring.
 *
 * The program will read in a file of words and their replacement values. The format of the file is:
 * Placeholder:ReplacementValue,Placeholder:ReplacementValue
 *
 * The program will read in a file that contains some placeholder "words" to be replaced,
 * will replace the words with the correct values and then write out a new file.
 *
 *@author paulawaite
 */
@WebService()
public class FindAndReplace {


    String inputFile;
    String outputFile;
    String findReplaceFile;
    private static Logger logger =Logger.getLogger(FindAndReplace.class);
    Map<String, String> findReplaceValues = new HashMap<>();


    public static void main(String[] args) {
        //FindAndReplace findAndReplace = new FindAndReplace("input.txt", "output.txt", "findandreplace.txt");

        Object implementor = new FindAndReplace("input.txt", "output.txt", "findandreplace.txt");
        String address = "http://localhost:9000/FindAndReplace";
        Endpoint.publish(address, implementor);
    }

    public FindAndReplace(String inputFile, String outputFile, String findReplaceFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.findReplaceFile = findReplaceFile;
        createMapOfFindReplaceValues();
        readInputFile();
        logger.info("MSGINFO");
    }

    private void createMapOfFindReplaceValues() {
        String line = null;
        try (BufferedReader in = new BufferedReader(new FileReader(findReplaceFile))) {
            while (in.ready()) {
                line = in.readLine();
                String str[] = line.split(",");
                for (int i = 0; i < str.length; i++) {
                    String arr[] = str[i].split(":");
                    findReplaceValues.put(arr[0], arr[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @WebMethod
    public String readInputFile() {
        String line = null;
        PrintWriter out = createOutputFile();

        try (BufferedReader in = new BufferedReader(new FileReader(inputFile))) {
            while (in.ready()) {
                line = in.readLine();
                for (Map.Entry<String, String> entry : findReplaceValues.entrySet()) {
                    line = line.replaceAll(entry.getKey(), entry.getValue());
                }
                out.println(line);
            }

            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
            logger.trace("MSG", e);
        }
        return out.toString();
    }

    public PrintWriter createOutputFile() {
        try {
           PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
                return out;
        } catch (IOException e) {
            e.printStackTrace();
        return null;
    }

}}
