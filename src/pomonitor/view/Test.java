package pomonitor.view;

public class Test {
	@org.junit.Test
	public void testRegex(){
		String str="1,2!3:6      7";
		String[] strArray=str.split("[\\s]*[\\pP]*[\\s]*");
		for(String s:strArray){
			if(!s.equals(""))
			System.out.println(s+"~~~~~~");
		}
	}

}
