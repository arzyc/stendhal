package games.stendhal.server.maps.quests.captureflag;

import games.stendhal.server.actions.admin.AdministrationAction;
import games.stendhal.server.core.engine.GameEvent;
import games.stendhal.server.core.events.UseListener;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.player.Player;


/**
 * handles tagging of players in Capture the Flag games.
 * This is attached to a player to listen for other players tagging
 * the player.
 */
public class CaptureFlagUseListener implements UseListener {

	/*
	 * note that target is "full time", and many different senders
	 * may call onUsed
	 */
	private String senderName;
	private Player sender;

	private String targetName;
	private Player target;

	/**
	 * creates a new CaptureFlagUseListener
	 *
	 * @param target target player
	 */
	public CaptureFlagUseListener(Player target) {
		this.target     = target;
		this.targetName = target.getName();
	}

	/**
	 * called when tagger tags the target
	 * 
	 * @param user
	 */
	private void init(Player tagger) {
		sender      = tagger;
		senderName  = tagger.getName();
	}

	/**
	 * confirm that target is online at this time
	 */
	private boolean checkOnline() {
		if ((target == null) || (target.isGhost() && (sender.getAdminLevel() < AdministrationAction.getLevelForCommand("ghostmode")))) {
			sender.sendPrivateText("No player named \"" + targetName + "\" is currently active.");
			return false;
		}
		return true;
	}


	/**
	 * 
	 * if properly equipped and in range, returns the first word of
	 * the ammunition or weapon name - "fumble arrow" -> fumble.
	 * (that is a feeble approach to Effects)
	 * 
	 * @param text
	 * @return
	 */
	// protected Effect checkEquippedAndInRange
	// protected String checkEquippedAndInRange() {
	protected String checkEquippedAndInRange() {
		
		// confirm player is equipped with proper weapons (bow and tag arrow) (fumble, slowdown, paralyze, ...) 

		Item          bow    = sender.getRangeWeapon();
		StackableItem arrows = sender.getAmmunition();

		if (bow == null) {
			// System.out.println("    sender not equipped with bow");
			return "not-equipped";
		}
		
		if (arrows == null) {
			// System.out.println("    sender not equipped with arrows");
			return "not-equipped";
		}
				
		// confirm target in range
		final int maxRange = sender.getMaxRangeForArcher();
		if (!sender.canDoRangeAttack(target, maxRange)) {
			// The attacker is attacking either using a range weapon with
			// ammunition such as a bow and arrows, or a missile such as a
			// spear.
			// note: could also be that player does not have enough ammunition
			return "not-in-range";
		}

		// take away one arrow
		arrows.sub(1);

		// get the type of the equipped arrows, return first word
		String[] parts = arrows.get("name").split(" ");
		String type = parts[0];

		// TODO: if type not one of the special types, return null
		
		return type;
	}

	/**
	 * this is called when another player left-clicks on this.target
	 */
	public boolean onUsed(RPEntity user) {

		String result = null;
		
		// i think if sender == target, should either prohibit,
		//    or allow funny self-inflicted wounds

		init((Player) user);

		/* If the targetis not logged in or if it is a ghost 
		 * and you don't have the level to see ghosts... */
		if (!checkOnline()) {
			return false;
		}

		// XXX all of this is essentially just going through the combat steps, but 
		//     avoiding the combat engine, to avoid PvP
		// XXX should get back an Effect subclass, or None
		// this call also removes one arrow from stack
		// probably more efficient if effect were some sort of enum
		String effect = this.checkEquippedAndInRange();

		// System.out.println("  effect: " + effect);
		
		if (effect == null) {
			return false;
		}
		
		if (effect.equals("not-equipped")) {
			user.sendPrivateText("You cannot tag unless equipped with bow and special ammunition");
			return false;
		}
		
		if (effect.equals("not-in-range")) {
			user.sendPrivateText("You must be in range to tag a player");
			return false;
		}
				
		
		if (effect.equals("fumble") || effect.equals("drop") || effect.equals("")) {

			result = target.maybeDropDroppables(sender);
			
		} else if (effect.equals("slow") || effect.equals("slowdown")) {

			// TODO: add back in when RPEntity supports changeSpeed
			// result = target.maybeSlowDown(sender);
			
			result = "maybe would have slowed down target";
			
		} else if (effect.equals("speedup")) {

			// probably just developer scaffolding
			//  but maybe could be part of team strategy - 

			// TODO: add back in when RPEntity supports changeSpeed 
			// target.changeSpeed(0.1);
			// result = "sped up - 0.1";
			
			result = "should have sped up - 0.1";
			
		} else {
			
			// did not recognize tag
			//   don't just silently fail
			return false;
		}
		
		if (result != null) {

			String message = user.getName() + " " + result;
			
			// transmit the message
			target.sendPrivateText(message);
		
			if (!senderName.equals(targetName)) {
				user.sendPrivateText(message);
			}
		}
		
		// maybe change this to mark the last ctf attacker
		// target.setLastPrivateChatter(senderName);
		
		new GameEvent(user.getName(), "tag", targetName, effect, result).raise();
		return true;
	}

}
