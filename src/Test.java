import org.apache.log4j.BasicConfigurator;

import opencraft.lib.tick.ITickable;
import opencraft.lib.tick.TickManager;

public class Test {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		TickManager tick = new TickManager(10, 1000);
		tick.start();
		tick.addTick(new ITickable() {
			@Override
			public void tick() {
				System.out.println("1 ticking!");
			}
		});
		tick.addTick(new ITickable() {
			@Override
			public void tick() {
				System.out.println("2 ticking!");
			}
		});
	}
}
