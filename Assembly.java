import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;

public class Assembly{

    private Path path;

    public Assembly(Path path){
        this.path = path;
    }
	
	public static void main(String[] args) {
        //read length of argument
        int argc = args.length;
        Path path = Paths.get("");
        if(argc == 0){
            //need to support ROM path
            path = Paths.get("rom/WriteYourCodeHere.txt");
        } else{
            //path = Paths.get("rom/"+args[0]);
			path = Paths.get(args[0]);
        }
        Assembly m = new Assembly(path);
        m.Run();
    }

    public void Run(){
		ArrayList<String> Memory = new ArrayList<String>();
		ArrayList<Byte> MemoryBytes = new ArrayList<Byte>();
        try{
			BufferedReader br = new BufferedReader(new FileReader(path.toString()));
			BufferedWriter bw = new BufferedWriter(new FileWriter(path.toString().replace(".txt", "New.txt"), false));	//BW must close or flush.
			System.out.println("\nReading " + path.toString() + " files...\n");
			String line = br.readLine();
			int z = 512;
			while(line != null) {
				while(line.length()<4){
					bw.write(line+System.lineSeparator());
					line = br.readLine();
				}
				while(line.substring(0,1).equals("-")){
					bw.write(line+System.lineSeparator());
					line = br.readLine();
				}
				while(line.substring(0,1).equals(";")){
					bw.write(line+System.lineSeparator());
					line = br.readLine();
				}
				while(line.substring(0,1).equals("#")){
					System.out.println("masuk ke #");
					bw.write(line+System.lineSeparator());
					line = br.readLine();
				}
				String notes = "";
				if(line.length()>4){
					int indeks = line.indexOf(";");
					if(indeks!=-1){
						notes = line.substring(line.indexOf(";"), line.length());
					}
					else{
						notes = "";
					}
				}
				String b1 = line.substring(0,2);
				String b2 = line.substring(2,4);
				Memory.add(b1);
				Memory.add(b2);
				String address = String.format("%04X", z);
				bw.write(b1+b2 + " " + address + " " + notes + System.lineSeparator());
				line = br.readLine();
				z += 2;
			}
			bw.close();
			br.close();
			for(int i=0; i<Memory.size(); i++){
				String hex = Memory.get(i);
				int value = Integer.parseInt(hex, 16);
				//System.out.println(value);
				byte bytes = (byte) value;
				MemoryBytes.add(bytes);
			}
			//System.out.println(Memory.toString());
			//System.out.println(MemoryBytes.toString());
			byte[] ByteArray = new byte[MemoryBytes.size()];
			for(int i=0; i<ByteArray.length; i++){
				ByteArray[i] = MemoryBytes.get(i);
			}
			try {
				String namaFile = this.path.toString().replace(".txt", ".ch8");
				Path path = Paths.get(namaFile);
				Files.write(path, ByteArray);
				System.out.println("Write your code into chip8 file... \n");
				System.out.println("Operation success... \n");
				System.out.println("See the result in rom folder \n");
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
        }
        catch(IOException ex){
            //ex.printStackTrace();
			System.err.println(ex.getMessage()+"\n");
        }	
		System.out.println("Program Closed... \n");
		System.out.println("Thanks you \n");
    }
}
