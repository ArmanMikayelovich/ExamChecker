import java.io.*;
import java.util.*;
public class Main {
	public static void main(String[] args)throws FileNotFoundException,IOException {
		Checker importer = new Checker();
		importer.start();

	}
}

class Checker {


	public void start() throws FileNotFoundException, IOException {
		///Write firstName & lastName and get result in String array
		HashMap<String,String[]> personList = new HashMap<String,String[]>();

		//itog@ faylum grelu hamar HashMap
		HashMap<String,Integer> personPoints = new HashMap<String,Integer>();


		String[] trueAnswers = readInfo(new File("answers.txt"));

		File folder = new File("Students/");
		File[] listOfFiles = folder.listFiles();
		for(File student : listOfFiles) {
			personList.put(student.getName(),readInfo(student) );
			}
		for (Map.Entry<String, String[]> entry : personList.entrySet()) {
			 int individualPoint = 0;
			 String[] personAnswers = entry.getValue();

			 //chisht patasxanneri hashvark
			 for(int x = 0;x<trueAnswers.length;x++) {
			 	if( trueAnswers[x].equals(personAnswers[x]) ) individualPoint++;
			 }

			 personPoints.put(entry.getKey(),individualPoint);
		}

		System.out.println(personPoints);
		writer(personPoints);


	}//end of void start()
	

	//this method reads  answers with one student
	 String[] readInfo(File student) throws FileNotFoundException, IOException {

		BufferedReader reader = new BufferedReader(new FileReader(student) );
			String answers[] = new String[14];
			int index = 0;
			String line;
			while ( ( line = reader.readLine() ) != null ) {
				String[] text = line.split(":");
				answers[index++]  = answerSorter(text[1]); //adding sorted text
			}
		
		return answers;
			
	}//end void readInfo(File student) throws FileNotFoundException, IOException {



	//for answer sorting... |||Example (e,b,d => bde)
	String answerSorter(String text) {

		StringBuilder builder = new StringBuilder();
		char[] t = text.toCharArray();
		 Arrays.sort(t);

        for(char ch : t)  {
        	builder.append(ch);
        }
            
        String s = builder.toString().replace(",","").replace(" ","");
		return s;
	}

	void writer(HashMap<String, Integer> persons) {
		StringBuilder toJson = new StringBuilder();
		toJson.append("{\n");
		for(Map.Entry<String, Integer> entry : persons.entrySet()) {
			toJson.append("\t\"" + entry.getKey() + "\" : \"" + entry.getValue() + "\"" + ",\n");
		}
		 int lastIndex = toJson.lastIndexOf(",");
		 toJson.setCharAt(lastIndex,' ');

		 toJson.append("}");
		 System.out.println(toJson.toString());
		 try {
		 	FileWriter f = new FileWriter("Out.txt");
		 	f.write(toJson.toString());
		 	f.flush();
		 } catch(Exception e) {
		 	e.printStackTrace();
		 }
		 
	}
}
