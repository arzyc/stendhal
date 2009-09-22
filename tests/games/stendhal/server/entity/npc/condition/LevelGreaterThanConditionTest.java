package games.stendhal.server.entity.npc.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import games.stendhal.server.entity.npc.parser.ConversationParser;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.PlayerTestHelper;

public class LevelGreaterThanConditionTest {
	private Player level100Player;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
		
	}

	@Before
	public void setUp() throws Exception {
		level100Player = PlayerTestHelper.createPlayer("player");
		level100Player.setLevel(100);
	}


	@Test
	public final void testHashCode() {
		assertEquals(new LevelGreaterThanCondition(101).hashCode(),
				new LevelGreaterThanCondition(101).hashCode());

	}

	@Test
	public final void testFire() {
		assertTrue(new LevelGreaterThanCondition(99).fire(level100Player,
				ConversationParser.parse("greaterthan"), null));
		assertFalse(new LevelGreaterThanCondition(100).fire(level100Player,
				ConversationParser.parse("greaterthan"), null));
		assertFalse(new LevelGreaterThanCondition(101).fire(level100Player,
				ConversationParser.parse("greaterthan"), null));
	}

	@Test
	public final void testLevelGreaterThanCondition() {
		new LevelGreaterThanCondition(0);

	}

	@Test
	public final void testToString() {
		assertEquals("level > 0 ", new LevelGreaterThanCondition(0).toString());
	}

	@Test
	public final void testEqualsObject() {
		assertEquals(new LevelGreaterThanCondition(101),
				new LevelGreaterThanCondition(101));
		assertFalse((new LevelGreaterThanCondition(101)).equals(new LevelGreaterThanCondition(
				102)));
		assertFalse((new LevelGreaterThanCondition(102)).equals(new LevelGreaterThanCondition(
				101)));
	}

}
