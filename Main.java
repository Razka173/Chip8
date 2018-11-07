import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main{

    private Path path;
    private Decoder decoder;

    public Main(Path path){
        this.path = path;
        decoder = new Decoder();
    }

    public void Run(){
        byte[] fileContents = "".getBytes(); //dummy to by pass try catch
        try{
            //read binary file
            fileContents = Files.readAllBytes(path);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
		System.out.println("reading " + this.path);
        System.out.println("op   addr notes");
        System.out.println("---- ---- -----------------------------------------------------");
        for (int i = 0; i < fileContents.length; i+=2) {
            byte b1 = fileContents[i];
            byte b2 = fileContents[i+1];
            String byteString = String.format("%02X", b1);
            String byteString2 = String.format("%02X", b2);
            String address = String.format("%04X", i);
            String note = decoder.DecodeInstruction(b1, b2);
            System.out.println(byteString + byteString2 + " " + address + " " + note);
        }
    }

    public static void main(String[] args) {
        //read length of argument
        int argc = args.length;
        Path path = Paths.get("");
        if(argc == 0){
            //need to support ROM path
            path = Paths.get("rom/WriteYourCodeHere.ch8");
        } else{
            path = Paths.get(args[0]);
        }
        Main m = new Main(path);
        m.Run();
    }
}