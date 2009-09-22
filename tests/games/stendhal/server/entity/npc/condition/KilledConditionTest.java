package games.stendhal.server.entity.npc.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import games.stendhal.server.entity.player.Player;

import java.util.Arrays;

import org.junit.Test;

import utilities.PlayerTestHelper;

public class KilledConditionTest {

	@Test
	public final void testHashCode() {

		assertEquals(new KilledCondition("rat").hashCode(),
				new KilledCondition("rat").hashCode());
		assertEquals("i would expect this equal", new KilledCondition("rat",
				"mouse").hashCode(),
				new KilledCondition("mouse", "rat").hashCode());

	}

	@Test
	public final void testFire() {
		KilledCondition kc = new KilledCondition();
		assertTrue(kc.fire(null, null, null));
		Player bob = PlayerTestHelper.createPlayer("player");

		assertTrue("bob has killed all of none", kc.fire(bob, null, null));
		kc = new KilledCondition("rat");
		assertFalse(kc.fire(bob, null, null));
		bob.setSoloKill("rat");
		assertTrue("bob killed a rat ", kc.fire(bob, null, null));

		bob = PlayerTestHelper.createPlayer("player");
		new KilledCondition(Arrays.asList("rat"));
		assertFalse(kc.fire(bob, null, null));
		bob.setSoloKill("rat");
		assertTrue("bob killed a rat ", kc.fire(bob, null, null));
	}

	@Test
	public final void testToString() {
		final KilledCondition kc = new KilledCondition("rat");
		assertEquals("KilledCondition <[rat]>", kc.toString());
	}

	@Test
	public final void testEqualsObject() {
		assertEquals(new KilledCondition("rat"), new KilledCondition("rat"));
		assertEquals("i would expect this equal", new KilledCondition("rat",
				"mouse"), new KilledCondition("mouse", "rat"));
	}

}
