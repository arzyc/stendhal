package games.stendhal.server.entity.spell.effect;

import games.stendhal.common.constants.Nature;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;

import java.util.Date;
/**
 * An Effect that temporarily modifies a player's base_hp
 * 
 * @author madmetzger
 */
public class ModifyBaseHpEffect extends AbstractEffect {

	public ModifyBaseHpEffect(Nature nature, int amount, int atk, int def,
			double lifesteal, int rate, int regen, double modifier) {
		super(nature, amount, atk, def, lifesteal, rate, regen, modifier);
	}

	public void act(Player caster, Entity target) {
		actInternal(caster, (RPEntity) target);
	}

	private void actInternal(Player caster, RPEntity target) {
		Date expire = new Date(System.currentTimeMillis() + getAmount()*1000);
		target.addBaseHpModifier(expire, getModifier());
	}

}