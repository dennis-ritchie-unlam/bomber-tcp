package test.dominioTest;
import main.dominio.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapaTest {
	
	public Mapa mapita;

	@Before
	public void setup() {
		mapita = new Mapa();
	}

	@Test
	public void generaObstaculosIndestructibles() {
		Entidad[][] entidades = mapita.getEntidades();
		Assert.assertTrue(entidades[2][2] instanceof Obstaculo);
		Obstaculo obstaculito = (Obstaculo) entidades[2][2];
		Assert.assertEquals(false,obstaculito.isDestructible());	
	}
	
	@Test
	public void generaObstaculosIndestructiblesEnElBorde() {
		Entidad[][] entidades = mapita.getEntidades();
		Assert.assertTrue(entidades[0][0] instanceof Obstaculo);
		Obstaculo obstaculito = (Obstaculo) entidades[0][0];
		Assert.assertEquals(false,obstaculito.isDestructible());
		Assert.assertTrue(entidades[12][5] instanceof Obstaculo);
		Obstaculo obstaculito1 = (Obstaculo) entidades[12][5];
		Assert.assertEquals(false,obstaculito1.isDestructible());
	}
	
	@Test
	public void generaObstaculosDestructibles() {
		Entidad[][] entidades = mapita.getEntidades();
		Assert.assertTrue(entidades[4][5] instanceof Obstaculo);
		Obstaculo obstaculito = (Obstaculo) entidades[4][5];
		Assert.assertEquals(true,obstaculito.isDestructible());
	}
	
	@Test
	public void seCreaBienBombers() {
		mapita.añadirBomber(new Bomber(1, 1));
		mapita.añadirBomber(new Bomber(3, 3));
		ArrayList<Bomber> bombers;
		bombers = mapita.getBombers();
		Assert.assertEquals(2, bombers.size());
	}
	
	@Test
	public void bomberSePuedeMover() {
		mapita.añadirBomber(new Bomber(1, 1));
		ArrayList<Bomber> bombers;
		bombers = mapita.getBombers();
		Assert.assertEquals(1, bombers.size());
		Assert.assertEquals(bombers.get(0).getPosicionX(), 1, 0.000001);
		mapita.moverBomber(bombers.get(0), 1, 0);
		Assert.assertEquals(bombers.get(0).getPosicionX(), 2, 0.000001);
	}
	
	@Test
	public void bomberNoSePuedeMoverAUnObstaculo() {
		mapita.añadirBomber(new Bomber(1, 2));
		ArrayList<Bomber> bombers;
		bombers = mapita.getBombers();
		Assert.assertEquals(1, bombers.size());
		Assert.assertEquals(bombers.get(0).getPosicionX(), 1, 0.000001);
		mapita.moverBomber(bombers.get(0), 1, 0);
		Assert.assertEquals(bombers.get(0).getPosicionX(), 1, 0.000001);
	}
	
	@Test
	public void bomberEstaEnUnaEsquina() {
		mapita.añadirBomber(new Bomber(1, 1));
		ArrayList<Bomber> bombers;
		bombers = mapita.getBombers();
		Assert.assertEquals(1, bombers.size());
		Assert.assertEquals(bombers.get(0).getPosicionX(), 1, 0.000001);
		mapita.moverBomber(bombers.get(0), -1, 0);
		Assert.assertEquals(bombers.get(0).getPosicionX(), 1, 0.000001);
	}
	
	@Test
	public void añadirBomba() {
		mapita.añadirBomba(new Bomba(3,3));
		Entidad[][] entidades = mapita.getEntidades();
		Assert.assertTrue(entidades[3][3] instanceof Bomba);
	}
	
	@Test
	public void explotarBomba() throws InterruptedException {
		mapita.añadirBomba(new Bomba(3,3));
		Entidad[][] entidades = mapita.getEntidades();
		Assert.assertTrue(entidades[3][3] instanceof Bomba);
		Bomba bombita = (Bomba) entidades[3][3];
		Thread.sleep(bombita.getTiempoExplosion()*1000);
		bombita.explotar();
		Assert.assertTrue(bombita.isExploto());
	}
	
	@Test
	public void explotarMatarBomberConBomba() {
		mapita.añadirBomba(new Bomba(3,3));
		mapita.añadirBomber(new Bomber(4,4));		
		ArrayList<Bomber> bombers;
		bombers = mapita.getBombers();
		Entidad[][] entidades = mapita.getEntidades();
		Assert.assertTrue(entidades[3][3] instanceof Bomba);
		Bomba bombita = (Bomba) entidades[3][3];
		bombita.explotar();
		mapita.explotarBomba(bombita);
		Assert.assertFalse(bombers.get(0).EstaVivo());
	}
	
	@Test
	public void explotarRomperObstaculoConBomba() {
		mapita.añadirBomba(new Bomba(3,4));
		Entidad[][] entidades = mapita.getEntidades();
		Assert.assertTrue(entidades[3][4] instanceof Bomba);
		Bomba bombita = (Bomba) entidades[3][4];
		bombita.explotar();
		mapita.explotarBomba(bombita);
		assertEquals(null, entidades[3][5]);
	}
	
	@Test
	public void eliminarObstaculo() {
		Entidad[][] entidades = mapita.getEntidades();
		mapita.eliminarObstaculo(3, 5);
		assertEquals(null, entidades[3][5]);
	}
	
	@Test
	public void eliminarBomba() {
		mapita.añadirBomba(new Bomba(3,3));
		Entidad[][] entidades = mapita.getEntidades();
		Assert.assertTrue(entidades[3][3] instanceof Bomba);
		mapita.eliminarBomba((Bomba)entidades[3][3]);
		assertEquals(null, entidades[3][3]);
	}
}
