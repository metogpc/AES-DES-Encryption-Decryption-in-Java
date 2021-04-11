

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;


public class main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		  List<String> text=new ArrayList<String>(); 
		  String path="textFile.txt";
		  List<String> textData=bufferReaderToList(path, text);
		  cbcModeResult("AES", "CBC", textData);
		  ecbModeResult("AES", "ECB", textData);			
		  cbcModeResult("DES", "CBC", textData);
		  ecbModeResult("DES", "ECB", textData);
		  
	}
	
	//File Read
    private static List<String> bufferReaderToList(String path, List<String> list) {
    	try {
            final BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                list.add(line);
                }
            in.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //CBC Mode Result
    private static void cbcModeResult(String anahtar,String mod,List<String> textData) {
		  List<byte[]> encrypted=new ArrayList<byte[]>();
		  int size;
		  long timeMili;
		  long timeMili2;
		  try {
			KeyGenerator keyGen = KeyGenerator.getInstance(anahtar);
			Key key = keyGen.generateKey();
			Cipher cipher = Cipher.getInstance(anahtar+"/"+mod+"/PKCS5Padding");
			
			if(anahtar.equals("AES"))
				size=16;
			else
				size=8;
			
			cipher.init(Cipher.ENCRYPT_MODE,key,new IvParameterSpec(new byte[size]));
			
			timeMili=System.currentTimeMillis();
			
			for(String s: textData) {
				byte[] encryptedText = cipher.doFinal(s.getBytes());
				encrypted.add(encryptedText);
			}
			
			timeMili2=System.currentTimeMillis();
			
			String output="Process:Encryption	Key:"
					+anahtar
					+"		Mode:"
					+anahtar
					+"/"
					+mod
					+"/PKCS5Padding		Time(ms)"
					+(timeMili2-timeMili);
			
			System.out.println(output);
			timeMili=System.currentTimeMillis();
			cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(new byte[size]));
			
			for(int i=0;i<encrypted.size();i++) {
				byte[] decodedText = cipher.doFinal(encrypted.get(i));
			}
			
			timeMili2=System.currentTimeMillis();
			output="Process:Decryption	Key:"
					+anahtar
					+"		Mode:"
					+anahtar
					+"/"
					+mod
					+"/PKCS5Padding		Time(ms)"
					+(timeMili2-timeMili);
			System.out.println(output);
			
		 }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		  
    }
    
    //ECB Mode Result
    private static void ecbModeResult(String anahtar,String mode,List<String> textData) {
		  List<byte[]> encrypted=new ArrayList<byte[]>();
		  
		  long timeMili;
		  long timeMili2;
		  try {
			KeyGenerator keyGen = KeyGenerator.getInstance(anahtar);
			Key key = keyGen.generateKey();
			Cipher cipher = Cipher.getInstance(anahtar+"/"+mode+"/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE,key);
			
			timeMili=System.currentTimeMillis();
			
			for(String s: textData) {
				byte[] encryptedText = cipher.doFinal(s.getBytes());
				encrypted.add(encryptedText);
			}
			
			timeMili2=System.currentTimeMillis();
			
			String output="Process:Encryption	Key:"
					+anahtar
					+"		Mode:"
					+anahtar
					+"/"
					+mode
					+"/PKCS5Padding		Time(ms)"
					+(timeMili2-timeMili);
			
			System.out.println(output);
			timeMili=System.currentTimeMillis();
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			for(int i=0;i<encrypted.size();i++) {
				byte[] decodedText = cipher.doFinal(encrypted.get(i));
			}
			
			timeMili2=System.currentTimeMillis();
			output="Process:Decryption	Key:"
					+anahtar
					+"		Mode:"
					+anahtar
					+"/"
					+mode
					+"/PKCS5Padding		Time(ms)"
					+(timeMili2-timeMili);
			System.out.println(output);
			
		 }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		  
  }

}
