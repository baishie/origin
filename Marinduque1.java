import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.regex.*;  

/**
 *
 * @author KELLY M
 */

public class Marinduque1 {
  	String file;
	File f;
	ArrayList <String> data = new ArrayList <String> ();
	ArrayList <String> results = new ArrayList <String> ();

	String result1 = "Valid Variable Declaration";
	String result2 = "Invalid Variable Declaration";
	String result3 = "Valid Function Declaration";
	String result4 = "Invalid Function Declaration";
	String result5 = "Valid Function Definition";
	String result6 = "Invalid Function Definition";

	String datatypes [] = {"int", "float", "char", "double"};
	String invalidWords [] = {"return", "void", "if", "do", "while", "break", "int", "float", "char", "double"};	
	String currentStr;
	Queue<String> q = new LinkedList<String>();
	boolean semiColon = false; 
	boolean hasEnded = false;
	boolean end = false;

	boolean validDatatype = false; 
	boolean validName = false;
	boolean validContent = false;
	boolean validFirst = false;

	boolean isValidFunc = false;
	boolean isValidDef = false;
	boolean isValidVar = false;

	boolean ifFunction = false;
	boolean ifFuncDec = false;
	boolean ifVarDec = false;

	boolean statusQueue = false;
	boolean hasMapped = false;
	String head2 = null;
	String leDatatype = "";
	// ArrayList <Boolean> tempResults;
	Queue<String> paramsQ = new LinkedList<String>();
	Queue<String> insideF = new LinkedList<String>();
	String parameters = null;	
	String head;
	int ctr = 0;
	String out;
	public void run (String a, String b) {
	  	String file = a;
		File f = new File (a);
		String filename = a;
		out = b;
	    String line, token, delimiter = "\n\"";
	    StringTokenizer tokenizer;
	    BufferedReader input = null;
	  
	    try {
		    // String filename = "input.txt";
		    input = new BufferedReader(new FileReader(filename));
		    line = input.readLine(); // when printed gives first line in file  
		    // outer while (process lines)
		    Pattern pattern = Pattern.compile("(?<=\\{).*");
		    Matcher matcher;
		    boolean funcFlag = false;
		    String line2;
			String strLine= " ";
          	String fData="";
          	String fData2="";
          	String strLine2 = "";
          	String read = null;
		    while (line != null) { 
		    	if(line.isEmpty()) {
		      		line = input.readLine(); 		    		
		    	}
		    	line2 = line;
		    	read = line;
		    	if((!(line.contains(";"))) && (!(line.contains("{")) && (!(line.contains("["))))) {
		    		while(line2 != null) {
			            fData = fData + line2 +" ";
				      	line2 = input.readLine(); 
		    			if(line2.contains(";")){
		    				// //("out");
			            	fData = fData + line2 +" ";
			            	data.add(fData);
				      		line2 = input.readLine(); 			            			    				
		    				line = line2;
		    				break;
		    			}
		    		}		    		
		    	}		    	
		    	else if((line.contains("{")) && (!(line.contains("[")))) {
		    		// //("in");
		    		while(line2 != null) {
			            fData = fData + line2 +" ";
				      	line2 = input.readLine(); 
		    			if(line2.contains("}")){
		    				//("out");
			            	fData = fData + line2 +" ";
			            	//("fData "+fData);
			            	data.add(fData);
				      		line2 = input.readLine(); 			            			    				
		    				line = line2;
		    				break;
		    			}
		    		}
		    	}
		    	else {
			        String[] splited = read.split("\\;");
			        for (String part : splited) {
		            	data.add(part +";");
			            // //(part);
			        }	    		
		    	}    	
		    	// data.add(line);
		      	line = input.readLine(); 

		    }  
		    //("data "+data.toString());
	    }
	    catch (Exception e) {
			System.err.println(e);  
			return;  
	    }
	    goOver();
	}	
  	public void goOver () {
		String s;
		for(int i = 0; i < data.size(); i++) {
			s = data.get(i);
			currentStr = s;
			semiColon = false; 
			hasEnded = false;
			end = false;

			validDatatype = false; 
			validName = false;
			validContent = false;
			validFirst = false;

			isValidFunc = false;
			isValidDef = false;
			isValidVar = false;

			ifFunction = false;
			ifFuncDec = false;
			ifVarDec = false;

			statusQueue = false;
			hasMapped = false;

			ctr = 0;
			//("ang s "+s);
			q = new LinkedList<String>();						
			checker(s);
		}
		writeFile();
	}
	public void writeFile() {
		String str = null;
	    try{     
	      	BufferedWriter writer = new BufferedWriter(new FileWriter(out, true));
	      	for(int i = 0; i < results.size(); i++) {
		        str = results.get(i);
		        writer.write(str);
		        writer.newLine();
	        }
      		writer.close();  
  		}      	
	    catch(Exception e){
	       e.printStackTrace();
	    }		
	}
	public void checker(String a) {
		int first;
		int sec;
 		if(currentStr != null) {
			if(currentStr.contains("{") && currentStr.contains("(")) {
				ifFunction = true;
			}
			else if ((currentStr.contains("("))) {
				ifFuncDec = true;
			}
			else {
				ifVarDec = true;
			}
			for(String word : a.split(" |\\\n | \\s")) {
				// q.add(word);
				if((word.trim().length() > 0)) {
					q.add(word);			
				}
			}		
 		}
		if(ifFunction == true) {
			//("HAHAAA");
			//("ANG currentStr "+currentStr);
			String pattern1 = "{";
			String pattern2 = "}";
			String s = null;
			Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
			Matcher m = p.matcher(currentStr);
			while (m.find()) {
				s = m.group(1);
			}
			ifFuncDec = true;
			// //("ANG QUE "+q.toString());
			if(s == null) {
				results.add(result6);
				hasMapped = true;
				mapResults();
			}				
			while(!(q.isEmpty())) {
	 			head = q.remove();
	 			validFirst = checkFirst (head);	
	 			if(head.equals("return")) {	 				
	 				results.add(result6);
	 				hasMapped = true;
	 				mapResults();	
	 				break; 				
	 			}	 
	 			if(validFirst == false) {
	 				results.add(result6);
	 				hasMapped = true;
	 				mapResults();	
	 				break; 	 				
	 			}
	 			else {
		 			checkDatatype(head);
		 			isValidName(head);	 				
	 				break;
	 			}				 						
			}
		}
		else if( ifFuncDec == true) {
			//("hoho");
			while(!(q.isEmpty())) {
	 			head = q.remove();
	 			//("ang head bes "+head);
				String pattern1 = "(";
				String pattern2 = ")";
				String s2 = "";
				Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
				Matcher m = p.matcher(currentStr);	 
				while (m.find()) {
					s2 = m.group(1);
				}
				// //("ang s "+s);								
	 			validFirst = checkFirst (head);
	 			if(head.equals("return")) {
	 				results.add(result6);
	 				hasMapped = true;
	 				mapResults();
	 				break;	 				
	 			}
	 			if(s2.contains("void")){
	 				// //("sud ba dire?");
	 				results.add(result6);
	 				hasMapped = true;
	 				mapResults();
	 				break;	 				
	 			}	 			
	 			if(validFirst == false) {
	 				isValidDef = false;
	 				mapResults();
	 				break;
	 			}
	 			else {
	 				//("or dire");
		 			checkDatatype(head);
		 			// //("ang head "+head );
		 			isValidName (head);	
		 			//("ANG Q "+q.toString()); 				
	 			}	 			
			}
		}
		else if (ifVarDec == true) {
			while(!(q.isEmpty()))  {
	 			head = q.remove();
	 			if(head.equals("void")) {
	 				results.add(result4);
	 				hasMapped = true;
	 				mapResults();
	 				break;
	 			} 
	 			else if(head.equals("return")) {
	 				results.add(result6);
	 				hasMapped = true;
	 				mapResults();
	 				break;				
	 			}
	 			validFirst = checkFirst (head);
	 			if(validFirst == false) {
	 				if(currentStr.contains("(")) {
	 					isValidDef = false;
	 				}
	 				else {
	 					isValidVar = false;
	 				}
	 				mapResults();
	 			}
	 			else {
		 			checkDatatype(head);
		 			isValidName (head);	 				
	 			}

			}
		} 	
	}
	public void mapResults () {
		// //("kapila diay bes ");
		// //("valid var "+isValidVar);
		// //("valid def "+isValidDef);
		// //("semicolon "+semiColon);
		// //("validDatatype "+validDatatype);	
		// //("qqqq "+q.toString());
		// //("ang head "+head);
		if(ifFunction == true) {
			ifFuncDec = false;
		}	
		//("ANG HAZ MAPP "+hasMapped);
		//("HAS ENDED "+hasEnded);
		if(hasMapped == false && hasEnded == true) {
			// System.out.print("SULD JAPON HZ MAP "+hasMapped);
			hasMapped = true;
			if(ifVarDec == true) {
				//("ari ba ni sud?");
				if(validDatatype == true && validName == true && semiColon == true) { //is valid variable de
					//("map res HUHIHI");
					results.add(result1);
					return;
				}
				else if(isValidVar == true && semiColon == true) { //is valid variable dec
					//(" map res HOY");
					results.add(result1);
				}
				else if (isValidVar == true && semiColon == false){
					//("asa");
					results.add(result2);
				}
				else{
					//("hoyy");
					results.add(result2);
				}						
			}
			else if(ifFuncDec == true) {
					//("OR DIRRR");
				if(isValidDef == true && semiColon == true && validName == true) {
					//("man");
					results.add(result3);
				}				
				else if(isValidDef == false && semiColon == false) {
					//("ka");	
					results.add(result4);
				}
				else if(isValidDef == true && semiColon == false) {
					//("nisud");
					results.add(result4);
				}
				else if (isValidDef == true && semiColon == true) {
					//("arriii siyaaa");
					results.add(result3);					
				}
				else {
					//("TAKIRU");
					results.add(result4);
				}
			}	
			else if(ifFunction == true) {
				//("joke asa man");
				if(isValidFunc == true) {
					//("dumitooo ka besss");
					results.add(result5);					
				}
				else if(isValidFunc == false) {
					//("tengene dito");
					results.add(result6);
				}
				else if(validDatatype == true && validName == true && semiColon == true) { //is valid variable de
					//("map res HUHIHI");
					results.add(result5);
					return;
				}
				else if(isValidVar == true && semiColon == true) { //is valid variable dec
					//(" map res HOY");
					results.add(result5);
				}
				else if (isValidVar == true && semiColon == false){
					//("asa");
					results.add(result6);
				}
				else if(isValidDef == true && semiColon == true && validName == true) {
					//("man");
					results.add(result5);
				}				
				else if(isValidDef == false && semiColon == false) {
					//("ka");	
					results.add(result6);
				}
				else if(isValidDef == true && semiColon == false) {
					//("nisud");
					results.add(result6);
				}
				else if (isValidDef == true && semiColon == true) {
					//("arriii siyaaa");
					results.add(result5);					
				}

				else{
					//("hoyy");
					results.add(result6);
				}	
			}
		}
		else {
			return;
		}
		return ;	
	}		
	public void semiColChecker (String s) {
		String next = "";
		if(q.peek() != null) {
			// //("peek "+q.peek());
			next = q.peek();
		}
		// //("YUNG ULO BES "+s);	
		if (next.equals(";")) {
			remover();
			hasEnded = true;
			semiColon = true;			
			// return true;
		}		
		else if (s.charAt(s.length()-1) == (';')) {
			hasEnded = true; 
			semiColon = true;
			// //("kasag dito ba?");
			// return true;
		}
		if(ifFunction == true) {
			if(hasEnded == true) {
				if(!(next.equals("}"))) {
					isValidFunc = false;
					mapResults();
					return;
				}
				else if(next.equals("}")) {
					isValidFunc = true;
					mapResults();
					return;					
				}				
			}

		}
		else if(hasEnded == true && hasMapped == false) {
			// breaker();
			//("direee");
			mapResults();
			return;
		}
		return;
	}	
	public void remover() {
		if(q.peek() != null) {
			head = q.remove();
		}	
	}
	public void checkContents () {
		String next = null;
		String s = null;		
		if(q.peek() != null) {
			next = q.peek();
		}
		if((head.equals("+")) || (head.equals("-")) || (head.equals("*")) || (head.equals("/"))) {
			//("siloddd");
		}
		else if (head.contains("{")) {
			// //("DIRE BA NI SUD?");
			String pattern1 = "{";
			String pattern2 = "}";
		
			Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
			Matcher m = p.matcher(currentStr);
			while (m.find()) {
				s = m.group(1);
			}	
		}
		else if(head.contains(",")) {
			//("tangina dito bess");
			remover();
			isValidName (head);
		}
		else if(next != null ){
			if((next.equals("+")) || (next.equals("-")) || (next.equals("*")) || (next.equals("/"))) {
				//("suddd");
				remover();
				remover();
				//("head "+head);
			}
			else if(head.contains(",")) {
				remover();
				//("ANG HEDURRRUU "+head);
				isValidName(head);
			}
			else if(head.contains("{")) {
				//("checkuruu");
			}			
		}
		else {
			semiColChecker(head);
			//("DOMITO KA ORUT");
			return;
		}
		if(s!=null) {
			isValidVar = true;
			do {
				remover();
			}
			while(!(head.contains("}")));
		}
		if(q.peek()!= null && ifVarDec == true) {
			//("OR DITOOORRR");			
			semiColChecker(head);
		}
		else {
			//("ADNRITP");
			semiColChecker(head);
		}
 		return ;
	}		
	public void breaker (){
		head = null;
		if(head == null) {
			return;
		}	
	}
	public void paramsQueue (String s) {
		for(String word : s.split(" |\\\n | \\s")) {
			// q.add(word);
			if((word.trim().length() > 0)) {
				paramsQ.add(word);			
			}
		}
		boolean check = false;
		boolean check2 = false;
		String str = null;
		//("ANG PARAMS Q "+paramsQ.toString());
		do {
			check = checkFirst (paramsQ.peek());
			if(paramsQ.peek().equals("void")) {
				results.add(result4);
				hasMapped = true;
				mapResults();
				return;
			}	
			if(check == true) {
				//("params "+paramsQ.peek());
				if(paramsQ.peek()!= null) {
					str = paramsQ.remove();
					if(str.equals("void")) {
						results.add(result4);
						hasMapped = true;
						mapResults();
						return;
					}						
					check2 = checkFirst (str);
					//("ang check2 " +check2);
					if(check2 == false) {
						argsChecker(str);
					}						
				}
			}
			if(paramsQ.peek()!= null) {
				paramsQ.remove();
			}		
		}
		while(!(paramsQ.isEmpty()));
		if(check == true && check2 == true) {
			isValidDef = true;
		}
		//("params Q"+paramsQ.toString());
		return ; 
	}
	public void argsChecker (String s) {
		boolean flag2 = false;
		for (int i = 0; i <invalidWords.length; i++) {
			if((s.equals(invalidWords[i]))) {
				flag2 = true;
				isValidDef = false;
			}
		}					
		if(flag2 == false) {
			if ((Character.isLetter(s.charAt(0))) || s.charAt(0)==('_')) {
				isValidDef = true;	
			}
			else {
				isValidDef = false;
			}		
		}	
	}
	public void tempRemover() {
		if(insideF.peek()!=null) {
			head2 = insideF.remove();
		}
	}
	public void leCycle() {
		//("sud direeee???");
		String pattern1 = "{";
		String pattern2 = "}";
		String s = null;
		Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
		Matcher m = p.matcher(currentStr);
		while (m.find()) {
			s = m.group(1);
		}			
		for(String word : s.split(" |\\\n | \\s")) {
			// q.add(word);
			if((word.trim().length() > 0)) {
				insideF.add(word);			
			}
		}
		head2 = head;
		while(!(q.isEmpty()))  {
			// //("bogoo");
 			head = q.remove();
 			if(head.equals("void")) {
 				results.add(result6);
 				hasMapped = true;
 				mapResults();
 				break;
 			} 
 			else if(head.equals("return")) {
 				results.add(result5);
 				hasMapped = true;
 				mapResults();
 				break;				
 			}
 			validFirst = checkFirst (head);
 			//("VALID FIRST hoooyyy "+validFirst);
 			if(validFirst == false) {
 				if(currentStr.contains("(")) {
 					isValidDef = false;
 				}
 				else {
 					isValidVar = false;
 				}
 				mapResults();
 			}
 			else {
 				//("hoyyy");
	 			checkDatatype(head);
	 			//("CHECK DATA TYPE "+leDatatype);
	 			isValidName (head);	 				
 			}

		}		
	}
	public void symbolChecker (String s) {
		//("q "+q.toString());
		// breaker();
		String next = null;
		if(q.peek() != null) {
			next = q.peek();
		}

		//("YUNG ULO "+head);
		//("yung s "+s);
		//("ANG NEXT "+next);
	
		if((s.contains("("))) {
			if(ifFuncDec == true) {
				//("sud 1");
				if(s.contains("()")) {
					if(s.contains(");")) {
						isValidDef = true;
						// //("q");
						mapResults();
						return;
						// return true;						
					}
					else {
						isValidDef = false;
						mapResults();
						return;
						// return true;						
					}
				}
				else {
					String pattern1 = "(";
					String pattern2 = ")";
				
					Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
					Matcher m = p.matcher(currentStr);
					while (m.find()) {
						parameters = m.group(1);
					  	// //("PARAMETERS "+parameters);
					}
					paramsQueue(parameters);
					remover();
					if(ifFunction == true){
						//("PASOK BA!!!!");
						leCycle();
					}
					semiColChecker(head);
				}
			}
			else if(ifFunction == true) {			
			}
		}
		else if(s.contains("=")) {
			if(semiColon == true && q.peek() == null) {
				//("suuud");
			}
			else if(semiColon == false &&  q.peek() == null) {
				//("chick");
			}
			else if((s.length()==1)) {
				//("tangnaaa");
				remover();
				// //("ang headdd" +head);
				checkContents();			
			}
			else {
				//("ARI BA NI" +head);
				if(s.contains(",")) {
					isValidName(head);
					//("ANG ULO LOL "+head);
				}
				else {
					//("or dire" +head);
					semiColChecker(head);
				}
			}
			// return true;
		}
		else if((s.contains(","))) {
			//("huha");		
			remover();
			// //("ANG HEAD ");
			isValidName(head);
			// return true;
		}
		else if((s.contains("{"))) {
			//("DUMITO KA BES "+s);		
			remover();
			isValidName(head);
			// return true;
		}		
		else if ((next != null) && (next.length() ==1)) {
			remover();
			if((next.contains("("))) {
				//("SUD 2");	
				// return true;
			}
			else if(next.contains(",")) {	
				// remover();
				// return true;
			}		
			else if (next.contains("=")) {
				remover();
				// //("ANG HEADUUU "+head);
				checkContents ();
				// remover();
				// return true;
			}
			else if (next.contains("{")) {
				// remover();
				// return true;
			}						
		}
		else if ((next != null) && (!(next.length() ==1))) {
			remover();
			if((next.contains("("))) {
				if(ifFuncDec == true || ifFunction == true) {
					if(s.contains("()")) {
						if(s.contains(");")) {
							isValidDef = true;
							// //("q");
							mapResults();
							// return true;						
						}
						else {
							isValidDef = false;
							mapResults();
							// return true;						
						}
					}
					else {
						String pattern1 = "(";
						String pattern2 = ")";
					
						Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
						Matcher m = p.matcher(currentStr);
						while (m.find()) {
							parameters = m.group(1);
						  	// //("PARAMETERS "+parameters);
						}
						paramsQueue(parameters);
						remover();
						if(ifFunction == true) {
							symbolChecker (head);
						}
						else {
							semiColChecker(head);
						}
						// //("ANG YLOOO " +head);
					}
				}
			}
			else if(next.contains(",")) {	
				// remover();
				// return true;
			}		
			else if (next.contains("=")) {
				remover();
				// //("ANG HEADUUU "+head);
				checkContents ();
				// remover();
				// return true;
			}
			else if (next.contains("{")) {
				// remover();
				// return true;
			}				
		}
		// //("abot ba dire"); 		
		return;
	}	
	public void isValidName (String s) {
		ctr++;
		// //("q kapila "+q.toString());
		boolean flag = false;	
		boolean flag2 = false;
		boolean symChcker = false;
		// //("ANG HEAD "+head);
		String next = null;
		String str;
		// //("UNSAY NEXT "+q.peek());
		if(ifFuncDec == true) {
			s = head;
		}		
		if(ctr < 2) {
			if(q.peek()!= null) {
				next = q.element();
				if(next.length() <= 1) {
					next = q.peek();
				}
				else {
	      			next = next.substring(0, next.length()-1);
	      		}
			} 
			else {
				return;
			}			
		}
		// //("ang next "+next);
		if(next != null && ifFunction == false) {
			for (int i = 0; i <invalidWords.length; i++) {
				str = invalidWords[i];
				if((next.equals(str))) {
					flag2 = true;
					validName = false;
				}
			}			
		}
		
		if(flag2 == false) {
			if ((Character.isLetter(s.charAt(0))) || s.charAt(0)==('_')) {
				flag = true;
			}
			else {
				flag = false;
			}		
			validName = flag;	
		}
		boolean isArray = isArrays(s);
		if(isArray == true) {
			validArray(head);		
		}		
		else {
			if((!(q.isEmpty())) || head!=null) {
				// //("yawa kapila ka sulod");
				remover();					
				semiColChecker (head);	
				// //("ULO BES "+head);	
				symbolChecker (head);	
			}	
		}	
		return;
		// //("ang semi "+semiColon);	
		// //("ang symbol "+symChcker);						
	}
	public void validArray (String s) {
		boolean flag = false;
		boolean flag2 = false;
		boolean flag3 = false;
		// //("S FIRST "+s);
		char c;
		if(q.peek() != null) {
			if((q.peek().contains("["))) {
				s = q.remove();
			}
		}
		// //("S "+s);float huy[5];
		// //("S SEC "+s);
		flag = Character.isDigit(s.charAt(s.indexOf("[")+1));
		// //("flag "+flag);
		flag2 = ((s.charAt(s.indexOf("]"))) == (']'));
		// //("flag2 "+flag2);

		if(flag == true && flag2 == true) {
			if((s.charAt(s.length()-1) == (';'))) {
				end = true;
				isValidVar = true;				
				semiColChecker(head);
				// mapResults();
				// return true;
			}
			// return true;
		} 
		else if (flag == false && flag2 == true) {
			if(currentStr.contains("{")) {
				// //("flag3 "+flag3);
				flag3 = true;
				end = false;
				remover();
				symbolChecker(head);
				// //("ANG HEAD "+head);
				// return true;
			}
			else {
				end = true;
				isValidVar = false;	
				validName = false;			
				// ///("flag3 "+flag3);
				mapResults();
				// return false;
			}
		}
		else if(flag == true && currentStr.contains("{")) {
			// //("ANG HEADUUU "+head);
		}

		// return false;
	}

	public boolean isArrays (String s) {
		boolean flag = false;
		if(s.contains("[")) {
			flag = true;
		}
		else if(q.peek() != null) {
			if((q.peek().contains("["))) {
				flag = true;
			}
		}
		else {
			flag = false;
		}
		return flag;
	}
	public boolean checkFirst (String s) {
		boolean flag = false;
		if(ifFuncDec == true) {
			s = head;
		}
		// //("ng ");
		for (int i = 0; i <datatypes.length; i++) {
			if((s.equals(datatypes[i]))) {
				flag = true;
			}
		}
		if(s.equals("void")) {
			flag = true;
		}
		return flag;				
	}
	public void checkDatatype (String s) {
		boolean flag = false;
		for (int i = 0; i <datatypes.length; i++) {
			if((s.equals(datatypes[i]))) {
				flag = true;
				leDatatype = datatypes[i];
			}
		}
		if(ifFuncDec == true && flag == false) {
			if((s.equals("void"))) {
				flag = true;
				leDatatype = "void";
			}			
		} 
		validDatatype = flag;		
	}
	public static void main(String[] args) {
		Marinduque1 a = new Marinduque1();
		Scanner sc = new Scanner(System.in);		
		System.out.print("Enter File Input: ");		
		String input = sc.nextLine();
		System.out.print("Enter File Output: ");		
		String output = sc.nextLine();

		// //(input+" "+output);
		a.run(input, output);
		// //(a.results.toString());		
	}	
}