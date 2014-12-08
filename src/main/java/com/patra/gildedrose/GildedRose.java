package com.patra.gildedrose;

import java.util.ArrayList;
import java.util.List;

import com.patra.exceptions.InvalidItemException;
import com.patra.gildedrose.quality.impl.BackstageQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.DoubleDegradingQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.DoubleImprovingQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.ImprovingQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.LegendaryQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.SuperFastDegradingQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.ZeroQualityUpdateStrategy;
import com.patra.gildedrose.sellin.impl.LegendaryItemSellinUpdateStrategy;

public class GildedRose {

	private static List<MaintainableItem> items = null;

	public static void main(String[] args) {

		System.out.println("OMGHAI!");

		items = new ArrayList<MaintainableItem>();
		MaintainableItem dexterity;
		try {
			dexterity = new MaintainableItem("+5 Dexterity Vest", 10, 20);
			items.add(dexterity);
		} catch (InvalidItemException e) {
			e.printStackTrace();
		}
		MaintainableItem elixir;
		try {
			elixir = new MaintainableItem("Elixir of the Mongoose", 5, 7);
			items.add(elixir);
		} catch (InvalidItemException e) {
			e.printStackTrace();
		}

		MaintainableItem agedBrie;
		try {
			agedBrie = new MaintainableItem("Aged Brie", 2, 0);
			agedBrie.setQualityStrategyBeforeSellin(new ImprovingQualityUpdateStrategy());
			agedBrie.setQualityStrategyAfterSellin(new DoubleImprovingQualityUpdateStrategy());
			items.add(agedBrie);
		} catch (InvalidItemException e) {
			e.printStackTrace();
		}

		MaintainableItem sulfuras;
		try {
			sulfuras = new MaintainableItem("Sulfuras, Hand of Ragnaros", 0, 80);
			sulfuras.setSellInStrategy(new LegendaryItemSellinUpdateStrategy());
			sulfuras.setQualityStrategyBeforeSellin(new LegendaryQualityUpdateStrategy());
			sulfuras.setQualityStrategyAfterSellin(new LegendaryQualityUpdateStrategy());
			items.add(sulfuras);
		} catch (InvalidItemException e) {
			e.printStackTrace();
		}

		MaintainableItem backstage;
		try {
			backstage = new MaintainableItem(
					"Backstage passes to a TAFKAL80ETC concert", 15, 20);
			backstage
					.setQualityStrategyBeforeSellin(new BackstageQualityUpdateStrategy());
			backstage
					.setQualityStrategyAfterSellin(new ZeroQualityUpdateStrategy());
			items.add(backstage);
		} catch (InvalidItemException e) {
			e.printStackTrace();
		}

		MaintainableItem conjured;
		try {
			conjured = new MaintainableItem("Conjured Mana Cake", 3, 18);
			conjured.setQualityStrategyBeforeSellin(new DoubleDegradingQualityUpdateStrategy());
			conjured.setQualityStrategyAfterSellin(new SuperFastDegradingQualityUpdateStrategy());
			items.add(conjured);
		} catch (InvalidItemException e) {
			e.printStackTrace();
		}

		for (int i = 1; i <= 50; i++) {
			System.out.println(i
					+ " th Iteration-------------------------------");
			for (MaintainableItem item : items) {
				item.updateItem();
				System.out.println(item.getName() + "\t" + item.getSellIn()
						+ "\t" + item.getQuality() + "\n");
			}
		}

	}
	
	// Get the number of items in the list
	public static int getNumberOfItems() {
		if (items == null)
			return 0;
		return items.size();
	}
}
