package com.multilanguage.util;

import java.util.HashMap;
import java.util.Map;

public class LanguageFolder {
	private static Map<String, String> LanMap = new HashMap<String, String>();
	
	public static Map<String, String> getMap() {
		return LanMap;
	}

	static {
		LanMap.put("English", "values");//"English (United States)"); 
		
		LanMap.put("CHT", "values-zh-rHK");//"Chinese (HongKong)");                      
		LanMap.put("CHS", "values-zh-rCN");//"Chinese (People's Republic of China)");    
		LanMap.put("CHT", "values-zh-rTW");//"Chinese (Taiwan)"); 
		
		LanMap.put("DEU", "values-de");//"German (Germany)");//                          
		LanMap.put("PTB", "values-pt-rPT");//"Portuguese (Brazil)");//                   
		LanMap.put("PLK", "values-pl");//"Polish (Poland)");//                           
		LanMap.put("CSY", "values-cs");//"Czech (Czech Republic)");//                    
		LanMap.put("FRA", "values-fr");//"French (France)");//                           
		LanMap.put("ITA", "values-it");//"Italian (Italy)");//                           
		LanMap.put("ARA", "values-ar");//"Arabic (Saudi Arabia)");//                     
		LanMap.put("THA", "values-th");//"Thai (Thailand)");//                           
		LanMap.put("ESM", "values-es-rUS");//"Spanish (Chile)");//                       
		LanMap.put("ESN", "values-es");//"Spanish (Spain)");//                           
		LanMap.put("ELL", "values-el");//"Greek (Greece)");//                            
		LanMap.put("FIN", "values-fi");//"Finnish (Finland)"); //                        
		LanMap.put("HUN", "values-hu");//"Hungarian (Hungary)");//                       
		LanMap.put("JPN", "values-ja");//"Japanese (Japan)");//                          
		LanMap.put("NLD", "values-nl");//"Dutch (Netherlands)");//                       
		LanMap.put("PTG", "values-pt");//"Portuguese (Portugal)");//                     
		LanMap.put("RUS", "values-ru");//"Russian (Russia)");//                          
		LanMap.put("SKY", "values-sk");//"Slovak (Slovakia)");//                         
		LanMap.put("SLV", "values-sl");//"Slovenian (Slovenia)");//                      
		LanMap.put("SVE", "values-sv");//"Swedish (Sweden)");//                          
		LanMap.put("TRK", "values-tr");//"Turkish (Turkey)");//                          
		LanMap.put("VIT", "values-vi");//"Vietnamese (Vietnam)");//                      
		LanMap.put("KOR", "values-ko");//"Korean (Korea)");//                            
		LanMap.put("BGR", "values-bg");//"Bulgarian (Bulgaria)");//                      
		LanMap.put("DAN", "values-da");//"Danish (Denmark)");//                          
		LanMap.put("HRV", "values-hr");//"Croatian (Croatia)");//                        
		LanMap.put("NOR", "values-nb");//"Norwegian, Bokmål (Norway)");//                
		LanMap.put("ROM", "values-ro");//"Romanian (Romania)");//                        
		LanMap.put("SRL", "values-sr");//"Serbian (Latin, Serbia)");//                   
		LanMap.put("IND", "values-id");//"Indonesian");                                  
		LanMap.put("CAT", "values-ca");//"Catalan");                                     
		LanMap.put("ETI", "values-et");//"Estonian");                                    
		LanMap.put("LVI", "values-lv");//"Latvian");                                     
		LanMap.put("LTH", "values-lt");//"Lithuanian");                                  
		LanMap.put("HYE", "values-hy");//"Armenian");                                    
		LanMap.put("KKZ", "values-kk");//"Kazakh");                                      
		LanMap.put("FAR", "values-fa");//"Farsi");  
		
		LanMap.put("HEB", "values-he");//"Hebrew");  
		LanMap.put("HEB", "values-iw");//"Hebrew");
		
		LanMap.put("UKR", "values-uk");//"Ukrainian");                                   
		LanMap.put("MYA", "values-my");//"Burmese");                                     

	}

}
