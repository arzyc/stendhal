package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.PlayerHelper;

public class ToysCollectorTest {
	ToysCollector quest;
	@BeforeClass
	static public  void setupFixture(){
		PlayerHelper.generateNPCRPClasses();

	}

	@Before
	public void setUp() throws Exception {
		NPCList.get().add(new SpeakerNPC("Anna"));
		quest = new ToysCollector();
		quest.addToWorld();
	}

	@After
	public void tearDown() throws Exception {
		NPCList.get().remove("Anna");
	}

	@Test
	public final void testAddToWorld() {


		ToysCollector tc = new ToysCollector();
		tc.addToWorld();
	}

	@Test
	public final void testGetNeededItems() {

		assertEquals(Arrays.asList(new String[]{"teddy","dice","dress"}), quest.getNeededItems());
	}

	@Test
	public final void testGetSlotName() {
		assertEquals("toys_collector", quest.getSlotName());

	}

	@Test
	public final void testGetTriggerPhraseToEnumerateMissingItems() {
		assertEquals(ConversationPhrases.YES_MESSAGES, quest.getTriggerPhraseToEnumerateMissingItems());
	}

	@Test
	public final void testGetAdditionalTriggerPhraseForQuest() {
		assertEquals(Arrays.asList(new String[]{"toys"}), quest.getAdditionalTriggerPhraseForQuest());

	}

	@Test
	public final void testShouldWelcomeAfterQuestIsCompleted() {
		assertTrue(quest.shouldWelcomeAfterQuestIsCompleted());
	}



	}

