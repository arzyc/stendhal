package games.stendhal.server.maps.ados.wall;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Creates a man NPC to help populate Ados
 *
 */
public class HolidayingManNPC implements ZoneConfigurator {
	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Martin Farmer") {

			@Override
			protected void createPath() {
				final List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(70, 52));
				nodes.add(new Node(76, 52));
				nodes.add(new Node(76, 17));
				nodes.add(new Node(70, 17));
				nodes.add(new Node(70, 14));
				nodes.add(new Node(79, 14));
				nodes.add(new Node(79, 52));
				setPath(new FixedPath(nodes, true));
			}

			@Override
			protected void createDialog() {
				addGreeting("Hi hi.");
				addHelp("Ehm... I don't need help at the moment, but thanks.");
				addOffer("What?");
				addQuest("Nothing to do."); 
				addJob("No no, I'm on holiday here with my wife Alice.");
				addGoodbye("See you, and take care because of the lions beyond the wall.");

				}
		};

		npc.setEntityClass("man_008_npc");
		npc.setPosition(70, 52);
		npc.initHP(100);
		zone.add(npc);
	}
}
