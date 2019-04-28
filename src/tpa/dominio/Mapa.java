package tpa.dominio;

public class Mapa {
	private Entidad[][] entidades;
	
	public Mapa(){
		entidades = new Entidad[15][13];
	}
	
	public void generarObstaculos() {
		int i,j;	
		//GENERA INDESTRUCTIBLES TODOS		
		for(i=0; i<13; i++) {
	        for(j=0; j<15; j++) {
	            if(i==0 || i==12 || j==0 || j==14 ||(i%2==0 && j%2==0)) {
	                entidades[i][j] = new Obstaculo(i, j, false);
	            }
	        }
	    }
		// GENERA OBSTACULOS DESTRUCTIBLES EJEMPLO
		for(i=1; i<14; i++) {
			entidades[i][4]= new Obstaculo( i, 4, true);
		}
		for(i=1; i<14; i++) {
			entidades[i][10]= new Obstaculo( i, 10, true);
		}
		
//		for(i=3; i<11; i++) 
//			for(j=3; j<12; j+=2)
//				entidad[i][4]= new Obstaculo( i, j, true);
	}
	
	public void eliminarObstaculo( int x, int y) {
		if(entidades[x][y] instanceof Obstaculo && ((Obstaculo) entidades[x][y]).isDestructible() ) {
			entidades[x][y]=null;
		}
	}
	
	public Entidad[][] getEntidades(){	
		return entidades;
	}
	
	public void añadirBomba( Bomba bombita ) {
		
	}
	
	public void eliminarBomba( int x, int y) {
		
	}
	
	public void añadirBomber( Bomber personaje) {
		
	}
}
