
public class App {

	public static void main(String[] args) {
		String u="",v ="";
		if(args.length==2) {
			u=args[0];
			v=args[1];
		}else{
			System.exit(0);
		}
		
		//java App "12432134341245674745675476" "7054920058988836008343024"

		String k = tiraZeros(karatsuba(u, v));
	    System.out.println(k);
	}	
	public static String karatsuba(String u,String v){
		String[] uv = zeros(u,v);	  
		u=uv[0];
		v=uv[1];
	    //System.out.println(uv[0]);
	    //System.out.println(uv[1]);
		int n=u.length();
		if(u.length()==1 && v.length()==1) {
			return Integer.toString(Integer.parseInt(u)*Integer.parseInt(v));
		}
		else {
			int m = n/2;
			
			String p = u.substring(0,u.length()/2);
			String q = u.substring(u.length()/2,u.length());

			String r = v.substring(0,v.length()/2);
			String s = v.substring(v.length()/2,v.length());
			
			String pr = karatsuba(p, r); 
			String qs = karatsuba(q, s); 
			
			String y = sub(sub(karatsuba(soma(p,q),soma(r,s)),pr),qs);
			String x = soma(soma(shift(pr,2*m),shift(y,m)),qs); //pr*(10^(2*m))+(y-pr-qs)*(10^m)+qs;

			return x;	
		}
	 }
	public static String soma(String s1,String s2) {
		String[] uv = zeros(s1,s2);	  
		s1=uv[0];
		s2=uv[1];		
		String resultado = "";  
        int s = 0;         
        int i = s1.length() - 1, j = s2.length() - 1; 
        while (i >= 0 || j >= 0 || s == 1) 
        {
            s = s + ((i >= 0)? s1.charAt(i) - '0': 0); 
            s = s + ((j >= 0)? s2.charAt(j) - '0': 0); 
            resultado = (char)(s % 10 + '0') + resultado; 
            s /= 10; //carry
            i--;
            j--; 
        }
        return resultado;
	}
	public static String sub(String s1,String s2) {
		String[] uv = zeros(s1,s2);	  
		s1=uv[0];
		s2=uv[1];
		StringBuilder resultado = new StringBuilder();
		int i = s1.length()-1;
		while(i>=0) {
			int subtr=0;
			int y = Integer.parseInt(s1.substring(i, i+1));
			int z = Integer.parseInt(s2.substring(i, i+1));
			if(y>=z){
				subtr=y-z;
				resultado.insert(0, subtr);
			}
			else {
				int j = i-1;
				while(s1.substring(j,j+1).equals("0")){j--;};					
				s1=s1.substring(0,j)+(Integer.parseInt(s1.substring(j,j+1))-1)+s1.substring(j+1);
				j++;
				while(j<i){
					s1=s1.substring(0,j)+"9"+s1.substring(j+1);
					j++;
				}
				subtr=(y+10)-z;
				resultado.insert(0,subtr);
			}
			i--;
		}
		return resultado.toString();
		//return String.valueOf(Integer.parseInt(s1)-Integer.parseInt(s2));
	}
	public static String shift(String s1, int n) {
		String zeros="";
		for(int i=0;i<n;i++) {
			zeros=zeros+'0';
		}
		return s1+zeros;
	}
	public static String[] zeros(String x, String y) {
		String [] xy= {"",""};
		String aux="";
		if(x.length()<y.length()) {
			for(int i=0;i<y.length()-x.length();i++) {
				aux='0'+aux;
			}			
			x=aux+x;
		}
		else if(y.length()<x.length()) {
			for(int i=0;i<x.length()-y.length();i++) {
				aux='0'+aux;
			}		
			y=aux+y;
		}
		if(x.length() % 2 != 0 && x.length()>1) {
			x='0'+x;
			y='0'+y;
		}
		xy[0]=x;
		xy[1]=y;
		return xy;	
	}
	public static String tiraZeros(String s) {
		char[] v = s.toCharArray();
		for(int i=0;i<s.length();i++) {
			if(v[i]=='0') {
				v[i]=00;
			}
			else {
				break;
			}
		}
		String r = new String(v);
		return r.trim();
	}
}
