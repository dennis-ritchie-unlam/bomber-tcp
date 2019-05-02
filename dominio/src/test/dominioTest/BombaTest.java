package test.dominioTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.dominio.Bomba;

public class BombaTest {
	Bomba bombita;
	
	@Before
	public void setUp() {
		bombita = new Bomba(6, 6);
	}
	
	@Test
	public void explotoBomba() {
		bombita.explotar();
		assertTrue(bombita.isExploto());
	}

}
