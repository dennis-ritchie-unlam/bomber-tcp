package test.dominioTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jdk.nashorn.internal.objects.annotations.Setter;
import main.dominio.Obstaculo;

public class ObstaculoTest {
	
	@Test
	public void testObstaculoDestructible() {
		Obstaculo obstaculito = new Obstaculo(4, 4, true);
		assertTrue(obstaculito.isDestructible());
	}
	
	@Test
	public void testObstaculoIndestructible() {
		Obstaculo obstaculito = new Obstaculo(4, 4, false);
		assertFalse(obstaculito.isDestructible());
	}

}
