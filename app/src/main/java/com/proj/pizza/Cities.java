package com.proj.pizza;

public enum Cities {
	MANILA("Manila","A"),
	QUEZON("Quezon","B"),
	CALOOCAN("Caloocan","C"),
	LAS_PINAS("Las Pinas","D"),
	MAKATI("Makati","E"),
	MALABON("Malabon","F"),
	MANDALUYONG("Mandaluyong","G"),
	MARIKINA("Marikina","H"),
	MUNTINLUPA("Muntinlupa","I"),
	NAVOTAS("Navotas","J"),
	PARANAQUE("Paranaque","K"),
	PASAY("Pasay","L"),
	PASIG("Pasig","M"),
	PATEROS("Pateros","N"),
	SAN_JUAN("San Juan","O"),
	TAGUIG("Taguig","P"),
	VALENZUELA("Valenzuela","Q"),
	NONE("none","Z");
	
	private String strCity;
	private String number;
	private Cities(String s, String number){
		strCity = s;
		this.number = number;
	}

	public String getStringLabel(){
		return strCity;
	}
	public String getStringNum(){
		return number;
	}
	
	public Cities getCity(String s){
		s = s.toLowerCase();
		switch(s){
			case "manila": 
				return Cities.MANILA;
			case "quezon":
			case "quezon city":
				return Cities.QUEZON;
			case "caloocan":
			case "caloocan city":
				return Cities.CALOOCAN;
			case "las pinas": 
			case "las piñas":
			case "las piñas city":
				return Cities.LAS_PINAS;
			case "makati":
			case "makati city":
				return Cities.MAKATI;
			case "malabon":
			case "malabon city":
				return Cities.MALABON;
			case "mandaluyong":
			case "mandaluyong city":
				return Cities.MANDALUYONG;
			case "marikina":
			case "marikina city":
				return Cities.MARIKINA;
			case "muntinlupa":
			case "muntinlupa city":
				return Cities.MUNTINLUPA;
			case "navotas":
			case "navotas city":
				return Cities.NAVOTAS;
			case "paranaque":
			case "paranaque city":
			case "parañaque":
			case "parañaque city":
				return Cities.PARANAQUE;
			case "pasay":
			case "pasay city":
				return Cities.PASAY;
			case "san juan ":
			case "san juan city":
				return Cities.SAN_JUAN;
			case "pasig":
			case "pasig city":
				return Cities.PASIG;
			case "pateros": 
			case "pateros city": return Cities.PATEROS;
			case "taguig": 
			case "taguig city": return Cities.TAGUIG;
			case "valenzuela": 
			case "valenzuela city": return Cities.VALENZUELA;
			default: return Cities.NONE;
		}
		
	}


	
	
}
