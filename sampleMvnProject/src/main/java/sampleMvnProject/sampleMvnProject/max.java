package sampleMvnProject.sampleMvnProject;

public class max {
	
	public static int max() {
		String s =  "123456";
		String result = s.substring(0, 1);
		for(int i=1; i< s.length(); i++) {
			String small = s.substring(i, i+1);
			if(result.compareTo(small) <0) {
				result = small;
			}
		}
		return Integer.valueOf(result);
		
	}
	public static void main() {
		// TODO Auto-generated method stub
		System.out.println(max());
	}

}
