package test.dominioTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.dominio.Bomba;
import main.dominio.Bomber;

public class BomberTest {
	
	Bomber bomber;
	
	@Before
	public void setUp() {
		bomber = new Bomber(1, 1);
	}
	
	@Test
	public void ponerBomba() {
		assertTrue(bomber.ponerBomba() instanceof Bomba);
	}
	
	@Test
	public void bombasCeroDespuesDePonerUna() {
		bomber.ponerBomba();
		assertEquals(0, bomber.getBombasDisponibles());
	}
	
	@Test
	public void bombasCeroNoPoneBomba() {
		bomber.ponerBomba();
		assertEquals(null, bomber.ponerBomba());
	}
	
	@Test
	public void posicionBombaRedondeoParaAbajo() {
		Bomba bombita = bomber.ponerBomba();
		assertEquals(1, bombita.getPosicionX());
		assertEquals(1, bombita.getPosicionY());
	}
	
	@Test
	public void posicionBombaRedondeoParaArriba() {
		bomber.setPosicionX(2);
		bomber.setPosicionY(2);
		Bomba bombita = bomber.ponerBomba();
		assertEquals(2, bombita.getPosicionX());
		assertEquals(2, bombita.getPosicionY());
	}
	
	@Test
	public void matarBomber() {
		bomber.explotar();
		assertFalse(bomber.EstaVivo());
	}
	
	@Test
	public void revivirBomber() {
		bomber.explotar();
		assertFalse(bomber.EstaVivo());
		bomber.revivir();
		assertTrue(bomber.EstaVivo());
	}
}
