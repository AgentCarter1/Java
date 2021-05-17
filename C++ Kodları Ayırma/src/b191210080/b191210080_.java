package b191210080;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
public class b191210080_ {
	public static void main(String[]args) throws IOException {					
		int satirSayisi = 0;
        String[] diziTutan = null;
        String[] diziTutan2 = null;
        String satir = "";
        BufferedReader reader = null;
        File file = new File("src/program.cpp");
        reader = new BufferedReader(new FileReader(file));
        while((satir = reader.readLine())!=null) {
        	satirSayisi++;
        }
        reader.close();      
        BufferedReader reader1 = null;
        reader1 = new BufferedReader(new FileReader(file));
        satir="";       
        String[] satirlar = new String[satirSayisi];
        satirSayisi=0;
        while((satir=reader1.readLine())!=null) {
        	satirlar[satirSayisi]=satir;
        	satirSayisi++;
        }       
        reader1.close();               
        Pattern classPattern = Pattern.compile("class\\s*\\w+");   
        Pattern superPattern = Pattern.compile("public\\s*\\w+|protected\\s*\\w+|private\\s*\\w+");
    	Pattern fonksiyonBulma = Pattern.compile("void\\s*\\w+\\(|int\\s*\\w+\\(|string\\s*\\w+\\(|double\\s*\\w+\\(|\\float\\s*\\w+\\s*\\(");
    	Pattern publicFonksiyonBulma = Pattern.compile("");
        Pattern ParametreAl = Pattern.compile("\\(.*\\)");
        Pattern publicPattern = Pattern.compile("public:");
        Pattern yıkıcıFonksiyonPattern = Pattern.compile("~\\w+");
        Pattern operatorPattern = Pattern.compile("operator\\s*\\<<");
        boolean baslangicKontrol = true;
        boolean fonksiyonKontrol = true;
        boolean publicKontrol = true;
        int i=0;
        ArrayList<String> sınıfSuper = new ArrayList<String>();
        ArrayList<Integer> sınıfSuperSayac = new ArrayList<Integer>();
        for(int z=0;z<satirlar.length;z++) {
        	Matcher superMatcher1 = superPattern.matcher(satirlar[z]);
        	if(superMatcher1.find()) {
        		String yardım = superMatcher1.group();
        		yardım=yardım.replaceAll("\\s+", " ");
        		diziTutan=yardım.split(" ");
        		yardım=diziTutan[diziTutan.length-1];
        		if(sınıfSuper==null) {
        			sınıfSuper.add(yardım);
        			sınıfSuperSayac.add(1);
        		}else if(sınıfSuper.indexOf(yardım) == -1) {      			       			
        			sınıfSuper.add(yardım);
        			sınıfSuperSayac.add(1);
        		}else {
        			int index = sınıfSuper.indexOf(yardım);
        			int deger = sınıfSuperSayac.get(index);
        			deger++;
        			sınıfSuperSayac.set(index, deger);
        		}       		
        	}
        }
        while(baslangicKontrol) {
        	for(;i<satirlar.length;i++) {
        		fonksiyonKontrol = true;
        		publicKontrol = true;
        		Matcher classMatcher = classPattern.matcher(satirlar[i]);           	
            	if(classMatcher.find()) {
            		String className = classMatcher.group();
            		className=className.replaceAll("\\s+", " ");
            		diziTutan = className.split(" ");
            		className = diziTutan[1];
            		String className1 = className.replaceAll("^\\s+", "");
            		className1 = className1.trim();           		
            		Pattern kurucuFonksiyonPattern = Pattern.compile(className+"\\s*\\(");           		
            		System.out.println("Sınıf : " + className1);           		
            		while(publicKontrol) {
	            		for(;i<satirlar.length;i++) {
	            			Matcher publicMatcher = publicPattern.matcher(satirlar[i]);
	            			if(publicMatcher.find()) {
	            				while(fonksiyonKontrol) {
	            					for(;i<satirlar.length;i++) {
	                					Matcher fonksiyonMatcher = fonksiyonBulma.matcher(satirlar[i]);
	                					Matcher yıkıcıFonksiyonMatcher = yıkıcıFonksiyonPattern.matcher(satirlar[i]);
	                					Matcher kurucuFonksiyonMatcher = kurucuFonksiyonPattern.matcher(satirlar[i]);
	                					Matcher operatorFonksiyonMatcher = operatorPattern.matcher(satirlar[i]);
	                					Matcher publicFonksiyonMatcher = publicFonksiyonBulma.matcher(satirlar[i]);
	                					if(fonksiyonMatcher.find()) {
	                						String functionName = fonksiyonMatcher.group();
	                						functionName = functionName.replaceAll("\\s+", " ");
	                						diziTutan = functionName.split(" ");
	                						String fonksiyonDonusParametresi = diziTutan[0];
	                						functionName = diziTutan[1];
	                						functionName = functionName.replaceAll("\\(", "");
	                						functionName.trim();
	                						System.out.println(functionName);	                						
	                						Matcher parametreMatcher = ParametreAl.matcher(satirlar[i]);
	                						if(parametreMatcher.find()) {  
	                							int parametreSayisi = 0;
	                							diziTutan2=null;
	                							String parametre = parametreMatcher.group();
	                							diziTutan = parametre.split(":");
	                							diziTutan2 = parametre.split(",");
	                							parametreSayisi=diziTutan2.length;
	                							String ayir = diziTutan[0].replaceAll("\\(", "");
	                							ayir = ayir.replaceAll("\\)", "");
	                							ayir = ayir.trim();
	                							
	                							if(ayir != "") {
	                								for(int p=0;p<diziTutan.length;p++) {
	                									System.out.println("     Parametre Sayısı: "+parametreSayisi+"  "+diziTutan[p]);
	                									System.out.println("     Dönüş Nesnesi: "+fonksiyonDonusParametresi);
	                								}
	                							}else {
	                								System.out.println("     Parametre Sayısı: 0");
	                								System.out.println("     Dönüş Nesnesi: "+fonksiyonDonusParametresi);
	                							}
	                						}              						
	                					}else if(kurucuFonksiyonMatcher.find()) {
	                						String functionName = kurucuFonksiyonMatcher.group();
	                						functionName = functionName.replaceAll("\\s+", " ");
	                						functionName = functionName.replaceAll("\\(", "");
	                						functionName.trim();
	                						System.out.println(functionName);
	                						Matcher parametreMatcher = ParametreAl.matcher(satirlar[i]);
	                						if(parametreMatcher.find()) {
	                							int parametreSayisi=0;
	                							diziTutan2=null;
	                							String parametre = parametreMatcher.group();
	                							diziTutan = parametre.split(":");
	                							diziTutan2 = parametre.split(",");
	                							parametreSayisi = diziTutan2.length;
	                							String ayir = diziTutan[0].replaceAll("\\(", "");
	                							ayir = ayir.replaceAll("\\)", "");
	                							ayir = ayir.trim();
	                							if(ayir != "") {
	                								for(int j=0;j<diziTutan.length;j++) {
	                									System.out.println("     Parametre Sayısı: "+parametreSayisi+"  "+diziTutan[j]);
	                									System.out.println("     Dönüş Nesnesi: "+"Nesne Adresi");
	                								}
	                							}else {
	                								System.out.println("     Parametre Sayısı: 0");
	                								System.out.println("     Dönüş Nesnesi: "+"Nesne Adresi");
	                							}
	                						}	                						
	                					}else if(operatorFonksiyonMatcher.find()) {
	                						String functionName =operatorFonksiyonMatcher.group();
	                						functionName = functionName.replaceAll("\\s+", " ");
	                						System.out.println(functionName);
	                						
	                						Matcher parametreEslesme = ParametreAl.matcher(satirlar[i]);
	                						int parametreSayisi =0;
	                						if(parametreEslesme.find()) {
	                							String parametre = parametreEslesme.group();
		                						diziTutan = parametre.split(":");
		                						diziTutan2= parametre.split(",");
		                						parametreSayisi=diziTutan2.length;
		                						String Ayir = diziTutan[0].replaceAll("\\(", "");
		                						Ayir = Ayir.replaceAll("\\)", "");
		                						Ayir=Ayir.trim();
		                						if(Ayir !="") {
		                							for(int j=0;j<diziTutan.length;j++) {
		                								System.out.println("     Parametre Sayısı: "+parametreSayisi+"  "+diziTutan[j]);
		                								System.out.println("     Dönüş Nesnesi: ostream&");
		                							}	                							
		                						}else {
		                							System.out.println("     Parametre Sayısı: "+"0");
	                								System.out.println("     Dönüş Nesnesi: ostream&");
		                						}
	                						}
	                						
	                						
	                					}
	                					if(yıkıcıFonksiyonMatcher.find()) {
	                						String tutanımız = yıkıcıFonksiyonMatcher.group();
	                						System.out.println(tutanımız);
	                						System.out.println("     Parametre Sayısı: 0");
	                						System.out.println("     Dönüş Nesnesi: Void");
	                						fonksiyonKontrol=false;
	                						publicKontrol=false;
	                						break;
	                					}
	                					if(new String(satirlar[i]).equals("};")||satirlar[i]=="};") 
	                					{
	                						fonksiyonKontrol=false;
	                						publicKontrol=false;
	                						break;
	                					}	                						                					
	                				}
	            				}
	            				break;
	            			}	         			
	            		}
            		}
            		
            	}else {
            		baslangicKontrol = false;
            	}
        	}
        }
        System.out.println("Super Sınıflar: ");
        for(int p =0;p<sınıfSuper.size();p++) {
        	System.out.println(sınıfSuper.get(p)+": "+sınıfSuperSayac.get(p));
        }
        
	}
}
