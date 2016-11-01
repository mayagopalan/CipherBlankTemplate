import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Cipher {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '\"![]/%-_;?-=:" + '\n' + '\r';
	private static final String SIMPLE_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private static final String DEFAULT_ALPHABET = ALPHABET;
	
	private static final Dictionary dictionary = Dictionary.buildDictionary("/Users/mayagopalan/Documents/workspace/CipherBlankTemplate/words.txt");
	
	public static String rotationCipherEncrypt(String plain, int shift, String alphabet) {
		String encryptedtext = "";
		for(int index=0; index<plain.length(); index++){
			String character = plain.substring(index, index+1);
			int newindex = alphabet.indexOf(character);
			newindex+=shift;
			String encryptedLetters = alphabet.substring(newindex, newindex+1);
			encryptedtext+=encryptedLetters;
		}
		return encryptedtext;
		}
	/**
	 * Returns plaintext encrypted by the rotation cipher with a shift of movement.
	 * @param alphabet 
	 * @param plainText the plain text to be encrypted.
	 * @param shiftAmount the number of characters in ALPHABET to shift by.
	 * @return returns the encrypted plainText.
	 */

	public static String rotationCipherDecrypt(String cipher, int shift, String alphabet) {
		String plainText = "";
		for (int index =0; index <  cipher.length(); index++) {
			String character = cipher.substring(index, index +1);
			int newIndex = alphabet.indexOf(character);
			newIndex -= shift;
			if (newIndex <  0) {
				newIndex += alphabet.length();
			}
			if (newIndex > alphabet.length()) {
				newIndex = newIndex - alphabet.length();
			}
			String newCharacter =  alphabet.substring(newIndex, newIndex +1);
			plainText += newCharacter;
			
			
		}
		return plainText;
}
	/**
	 * Returns a the result of decrypting cipherText by shiftAmount using the rotation cipher.
	 * @param alphabet the alphabet to be used for the encryption
	 * @param cipherText the encrypted text.
	 * @param shiftAmount the key to decrypt the cipher.
	 * @return returns the decrypted cipherText.
	 */
	public static String VigenereCipherEncrypt(String plain, String password, String alphabet) {
       int[] plainIdx=new int[plain.length()];
       int[] passwordIdx = new int[password.length()];
       int[] encryptIdx = new int[plain.length()];
       String encrypt="";
		for(int i=0;i<plain.length();i++){
			String ltr=plain.substring(i,i+1);
			plainIdx[i]=alphabet.indexOf(ltr);
		}

		for(int j =0; j<password.length(); j++){
			String ltr=password.substring(j, j+1);
			passwordIdx[j] = alphabet.indexOf(ltr);
		}
		int nloop=(plain.length()/password.length()) +1;
		int iloop=-1;
		
		for(int j=0;j<nloop;j++){
			for(int k=0;k<password.length();k++){
				iloop++;
				if (iloop<plain.length()){
					encryptIdx[iloop]=passwordIdx[k];
				}	
			}		
		}

		for(int i=0;i<plain.length();i++){
			encryptIdx[i]+=plainIdx[i];
			encryptIdx[i]=encryptIdx[i]%alphabet.length();
			encrypt+=alphabet.substring(encryptIdx[i],encryptIdx[i]+1);
		}

		for(int i=0;i<plain.length();i++){
			String ltr=plain.substring(i, i+1);
			String outstr=i+" "+ltr+" "+plainIdx[i]+" "+encryptIdx[i];
			System.out.println(outstr);
		}
		
		return encrypt;
	}
	/**
	 * Returns plaintext encrypted by the vigenere cipher encoded with the String code
	 * @param alphabet the alphabet to be used for the encryption
	 * @param plainText the plain text to be encrypted.
	 * @param code the code to use as the encryption key.
	 * @return returns the encrypted plainText.
	 */
	
	public static String vigenereCipherDecrypt(String cipher, String password, String alphabet) {
		int[] CipherIdx=new int[cipher.length()];
	       int[] passwordIdx = new int[password.length()];
	       int[] plainIdx = new int[cipher.length()];
	       String plain="";
			for(int i=0;i<cipher.length();i++){
				CipherIdx[i]=alphabet.indexOf(cipher.substring(i, i+1));
			}

			for(int j =0; j<password.length(); j++){
				String ltr=password.substring(j, j+1);
				passwordIdx[j] = alphabet.indexOf(ltr);
			}
			int nloop=(cipher.length()/password.length()) +1;
			int iloop=-1;
			
			for(int j=0;j<nloop;j++){
				for(int k=0;k<password.length();k++){
					iloop++;
					if (iloop<cipher.length()){
						plainIdx[iloop]=passwordIdx[k];
					}
				}
			}		

			for(int i=0;i<cipher.length();i++){
				for(int j =0; j<password.length(); j++){
					if(passwordIdx[j]>CipherIdx[i]){
					 plainIdx[i]=CipherIdx[i]+alphabet.length()-passwordIdx[j];
					}else{
						plainIdx[i]=CipherIdx[i]-passwordIdx[i];
					}
				}	
			}
			for(int i=0; i<cipher.length(); i++){
				plain+=alphabet.substring(plainIdx[i],plainIdx[i]+1);
			}
			
			
			return plain;
	}

	public String rotationCipherCrack(String cipher, String alphabet) {
		String plaintext = "";
 
		for (int shiftAmount = 0; shiftAmount < Integer.MAX_VALUE; shiftAmount++) {
			 plaintext = rotationCipherDecrypt(cipher, shiftAmount, alphabet);
			 plaintext = removePunctuation(plaintext);
			 if (isEnglish(plaintext)) {
			 	return plaintext;
			 } 
		}
		return "";
	}
	
	
	/***
	 * Returns a new string without any punctuation
	 * @param plaintext - the string that is copied to the new string.
	 * @return returns a new string without any punctuation
	 */
	public static String removePunctuation(String plaintext) {
		String newStr = "";
		 plaintext = plaintext.toLowerCase(); 
		 String basicAlphabet = "abcdefghijklmnopqrstuvwxyz ";
		 
		 for (int i = 0; i <  plaintext.length(); i++) {
			 if (basicAlphabet.contains(plaintext.substring(i,i+1))) {
				 newStr += plaintext.substring(i,i+1);
			 }
		 }
		 
		 return newStr;
	}
	
	
	/***
	 * Returns true if plaintext is valid English and false if otherwise.
	 * @param plaintext - the text you wish to test for whether it's valid English.
	 * @return returns true if plaintext is valid English and false if otherwise
	 */
	private static boolean isEnglish(String plaintext) {
		String[] words = getWords(plaintext);
		
		int numberWordsEnglish = 0;
		for (int i = 0; i <  words.length; i++) {
			if (dictionary.isWord(words[i])) {
				numberWordsEnglish++;
			}
		}
		if (((double) numberWordsEnglish) / (words.length) >= 0.8) {
			return true;
		}
		
		return false;
	}
 
	/***
	 * returns an array of the words inside the input string.
	 * @param str - the String that is used to find the words inside of it.
	 * @return returns an array of the words inside the input string.
	 */
	public static String[] getWords(String str) {
		String[] strings = new String[countStrings(str)];
		String trimmedString = trimString(str);
		
		int lastIndexOfString = trimmedString.lastIndexOf(" ");
		
		for (int stringsIndex = 0; stringsIndex <  strings.length-1; stringsIndex++) {
			for (int i = 0; i <  lastIndexOfString; i++, stringsIndex++) {
				String indivString = "";
				while (!trimmedString.substring(i, i+1).equals(" ")) {
					indivString += trimmedString.substring(i, i+1);
					i++;
				}
				strings[stringsIndex] = indivString;
			}
		}
		
		String lastString = "";
		for (int i = lastIndexOfString+1; i <  trimmedString.length(); i++) {
			lastString += trimmedString.substring(i,i+1);
		}
		strings[strings.length-1] = lastString;
		
		return strings;
	}
	
	/***
	 * Returns the number of words in the input string.
	 * @param str - the String that is used to check for the number of words.
	 * @return returns the number of words in the input string.
	 */
	public static int countStrings(String str) {
		String trimmedString = trimString(str);
		
		int numOfStrings = 0;
		boolean isString = true;
		
		for (int i = 0; i <  trimmedString.length()-1; i++) {
			if (isString) {
				if (trimmedString.substring(i,i+1).equals(" ")) {
					isString = false;
					numOfStrings++;
				}
			}
			else {
				if (!trimmedString.substring(i,i+1).equals(" ")) {
					isString = true;
				}
			}
		}
		
		if (!trimmedString.substring(trimmedString.length()-1, trimmedString.length()).equals(" ")) {
			numOfStrings++;
		}
		
		return numOfStrings;
	}
	
	/***
	 * Returns a new string without spaces at the front or at the end.
	 * @param str - the string that is copied to a new string to be trimmed.
	 * @return returns a new string without spaces at the front or at the end.
	 */
	public static String trimString(String str) {
		String newStr = "";
		
		int lastIndexOfFirstSpace = 0;
		int firstIndexOfLastSpace = str.length();
		
		while(str.substring(lastIndexOfFirstSpace,lastIndexOfFirstSpace+1).equals(" ")) {
			lastIndexOfFirstSpace++;
		}
		
		while (str.substring(firstIndexOfLastSpace-1,firstIndexOfLastSpace).equals(" ")) {
			firstIndexOfLastSpace--;
		}
		
		for (int i = lastIndexOfFirstSpace; i <  firstIndexOfLastSpace; i++) {
			newStr += str.substring(i, i+1);
		}
		
		return newStr;
	}
	public static String vigenereCipherCrackThreeLetter(String cipher, String alphabet) {
		String password = "";
		for(int shiftvalue =0; shiftvalue < alphabet.length(); shiftvalue++){
			for(int pwletteralt = 0; pwletteralt < cipher.length(); pwletteralt+3){
				if(shiftvalue>cipher.length()){
					shiftvalue=shiftvalue%alphabet.length();
	
				String shiftedoutcome = cipher.substring(pwletteralt-shiftvalue, pwletteralt-shiftvalue+1);
				int maxspaces =0;
				int idealshift=0;
				Bag bag = new Bag();
				bag.add(shiftedoutcome);
				int mostfrequent = bag.getNumOccurances(" ");
				if(mostfrequent > maxspaces){
					maxspaces = mostfrequent;
					idealshift = shiftvalue;
				}
				for(int k =0; k< cipher.length(); k+3){
					String encrypt+=(alphabet.substring(k-shiftvalue, k+1-shiftvalue);
					return encrypt;
				}
			}
		}
				
	}
		for(int shiftvalue =0; shiftvalue < alphabet.length(); shiftvalue++){
			for(int pwletteralt = 0; pwletteralt < cipher.length(); pwletteralt+3){
				if(shiftvalue>cipher.length()){
					shiftvalue=shiftvalue%alphabet.length();
	
				String shiftedoutcome = cipher.substring(pwletteralt-shiftvalue, pwletteralt-shiftvalue+1);
				int maxspaces =0;
				int idealshift=0;
				Bag bag = new Bag();
				bag.add(shiftedoutcome);
				int mostfrequent = bag.getNumOccurances(" ");
				if(mostfrequent > maxspaces){
					maxspaces = mostfrequent;
					idealshift = shiftvalue;
				}
				for(int k =0; k< cipher.length(); k+3){
					String encrypt2+=(alphabet.substring(k-shiftvalue, k+1-shiftvalue);
					return encrypt2;
				}
			}
		}
				
	}
		for(int shiftvalue =0; shiftvalue < alphabet.length(); shiftvalue++){
			for(int pwletteralt = 0; pwletteralt < cipher.length(); pwletteralt+3){
				if(shiftvalue>cipher.length()){
					shiftvalue=shiftvalue%alphabet.length();
	
				String shiftedoutcome = cipher.substring(pwletteralt-shiftvalue, pwletteralt-shiftvalue+1);
				int maxspaces =0;
				int idealshift=0;
				Bag bag = new Bag();
				bag.add(shiftedoutcome);
				int mostfrequent = bag.getNumOccurances(" ");
				if(mostfrequent > maxspaces){
					maxspaces = mostfrequent;
					idealshift = shiftvalue;
				}
				for(int k =0; k< cipher.length(); k+3){
					String encrypt3+=(alphabet.substring(k-shiftvalue, k+1-shiftvalue);
					return encrypt3;
				}
			}
		}
				
	}	
}
	
}
	/***
	 * Returns the encrypted string by using the number of letters in the password and the shift amount
	 * @param string is tested with each set of letters and each shift value
	 * @param string is then solved for the one with the most spaces
	 * @return returns a new string decrypted text
	 ***/
