package com.patra.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

import com.patra.exceptions.InvalidItemException;
import com.patra.gildedrose.quality.impl.BackstageQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.DoubleDegradingQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.DoubleImprovingQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.ImprovingQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.LegendaryQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.SuperFastDegradingQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.ZeroQualityUpdateStrategy;
import com.patra.gildedrose.sellin.impl.LegendaryItemSellinUpdateStrategy;


public class GildedRoseTest {
	
	private static void passNoOfDays(MaintainableItem item, int noofDays){
		for(int day=1;day<=noofDays;day++){
			item.updateItem();
//			System.out.println(item.getName()+'\t'+item.getSellIn()+'\t'+item.getQuality());
		}
	}
	

	//Tests if sellin days are properly getting set
	@Test
	public void testSellInDecrements() throws InvalidItemException{
		MaintainableItem dexterity = new MaintainableItem("+5 Dexterity Vest", 10, 20);
		passNoOfDays(dexterity,20);
		assertEquals(-10,dexterity.getSellIn());
	}
	
	
//	//Tests if a new item is getting added successfully
//	@Test
//	public void testIfNewItemIsAdded() throws InvalidItemException{
//		int numberofItems = GildedRose.getNumberOfItems();
//		Item item = new Item("+5 Dexterity Vest", 10, 20);
//		GildedRose.addNewItem(item);
//		assertEquals(numberofItems+1,GildedRose.getNumberOfItems());
//	}
	
	//Tests if a new item with negative quality is not added
	@Test(expected = InvalidItemException.class)
	public void testIfNewItemWithNegativeQualityIsNotAdded() throws InvalidItemException{
		MaintainableItem invalid = new MaintainableItem("Invalid Item", 10, -5);
	}
	
//	//Tests if a new item with more than the max allowed quality is not added
//	@Test(expected = InvalidItemException.class)
//	public void testIfNewItemWithMoreThanMaximumQualityIsNotAdded() throws InvalidItemException{
//		int numberofItems = GildedRose.getNumberOfItems();
//		Item item = new Item("Invalid Item", 10, 60);
//		GildedRose.addNewItem(item);
//		assertEquals(numberofItems+1,GildedRose.getNumberOfItems());
//	}
	
//	//Tests if a new item with with more than the max allowed quality is added only if its a legend item
//	@Test
//	public void testIfLegendItemIsAdded() throws InvalidItemException{
//		int numberofItems = GildedRose.getNumberOfItems();
//		Item item = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
//		GildedRose.addNewItem(item);
//		assertEquals(numberofItems+1,GildedRose.getNumberOfItems());
//	}
//
	//Tests if quality of a general item is never negative
	@Test
	public void testGeneralItemQualityNeverNegative() throws InvalidItemException {
		MaintainableItem dexterity = new MaintainableItem("+5 Dexterity Vest", 10, 20);
		passNoOfDays(dexterity,50);
		assertEquals(0,dexterity.getQuality());
	}
	
	//Tests if quality of a general item is never more than 50
	@Test
	public void testQualityNeverMorethanFifty() throws InvalidItemException {
		MaintainableItem agedBrie = new MaintainableItem("Aged Brie", 2, 0);
		agedBrie.setQualityStrategyBeforeSellin(new ImprovingQualityUpdateStrategy());
		agedBrie.setQualityStrategyAfterSellin(new DoubleImprovingQualityUpdateStrategy());
		passNoOfDays(agedBrie,32);
		assertEquals(50,agedBrie.getQuality());
	}
	
	
	@Test
	public void testQualityNeverDegradesForLegendItems() throws InvalidItemException {
		MaintainableItem sulfuras = new MaintainableItem("Sulfuras, Hand of Ragnaros", 0, 80);
		sulfuras.setSellInStrategy(new LegendaryItemSellinUpdateStrategy());
		sulfuras.setQualityStrategyBeforeSellin(new LegendaryQualityUpdateStrategy());
		sulfuras.setQualityStrategyAfterSellin(new LegendaryQualityUpdateStrategy());
		passNoOfDays(sulfuras,200);
		assertEquals(80,sulfuras.getQuality());
	}
	
	//Tests if quality of Conjured item (special item) is never negative
	@Test
	public void testConjuredItemQualityNeverNegative() throws InvalidItemException {
		MaintainableItem conjured = new MaintainableItem("Conjured Mana Cake", 3, 18);
		conjured.setQualityStrategyBeforeSellin(new DoubleDegradingQualityUpdateStrategy());
		conjured.setQualityStrategyAfterSellin(new SuperFastDegradingQualityUpdateStrategy());
		passNoOfDays(conjured,50);
		assertEquals(0,conjured.getQuality());
	}
		
	//Tests if quality of a general item is decreasing by 1 before Sell By date has passed
	@Test
	public void testGeneralItemDecreasesQualityByOneBeforeSellByDate() throws InvalidItemException {
		MaintainableItem dexterity = new MaintainableItem("+5 Dexterity Vest", 10, 20);
		passNoOfDays(dexterity,7);
		assertEquals(13,dexterity.getQuality());
	}
	
	//Tests if quality of a general item is decreasing by 2 after Sell By date has passed
	@Test
	public void testGeneralItemDecreasesQualityBytwoAfterSellByDate() throws InvalidItemException {
		MaintainableItem dexterity = new MaintainableItem("+5 Dexterity Vest", 10, 20);
		passNoOfDays(dexterity,12);
		assertEquals(6,dexterity.getQuality());
	}
	
	//Tests if quality of an Aged Brie item is increasing by 1 before Sell By date has passed and by 2 after the Sell By date has passed
	@Test
	public void testAgedBrieQualityIncreasesByOneBeforeSellByDate() throws InvalidItemException {
		MaintainableItem agedBrie = new MaintainableItem("Aged Brie", 2, 0);
		agedBrie.setQualityStrategyBeforeSellin(new ImprovingQualityUpdateStrategy());
		agedBrie.setQualityStrategyAfterSellin(new DoubleImprovingQualityUpdateStrategy());
		passNoOfDays(agedBrie,6);
		assertEquals(10,agedBrie.getQuality());
	}
	
	//Tests if quality of a Backstage Passes item is increasing by 1 till 10 days before Sell By date has passed
	@Test
	public void testBackstagePassesQualityIncreasesByOneBeforeTenDaysOfSellByDate() throws InvalidItemException {
		MaintainableItem backstage = new MaintainableItem("Backstage passes to a TAFKAL80ETC concert", 15, 20);
		backstage
				.setQualityStrategyBeforeSellin(new BackstageQualityUpdateStrategy());
		backstage
				.setQualityStrategyAfterSellin(new ZeroQualityUpdateStrategy());
		passNoOfDays(backstage,4);
		assertEquals(24,backstage.getQuality());
	}
	

	//Tests if quality of Backstage Passes item is increasing by 2 between 10 days and 5 days before Sell By date has passed
	@Test
	public void testBackstagePassesQualityIncreasesByTwoBetweenTenDaysAndFiveDaysOfSellByDate() throws InvalidItemException {
		MaintainableItem backstage = new MaintainableItem("Backstage passes to a TAFKAL80ETC concert", 15, 20);
		backstage
				.setQualityStrategyBeforeSellin(new BackstageQualityUpdateStrategy());
		backstage
				.setQualityStrategyAfterSellin(new ZeroQualityUpdateStrategy());
		passNoOfDays(backstage,9);
		assertEquals(33,backstage.getQuality());
	}
	
	//Tests if quality of Backstage Passes item is increasing by 3 between 5 days and 0 days before Sell By date has passed
	@Test
	public void testBackstagePassesQualityIncreasesByThreeBetweenFiveDaysAndZeroDaysOfSellByDate() throws InvalidItemException {
		MaintainableItem backstage = new MaintainableItem("Backstage passes to a TAFKAL80ETC concert", 15, 20);
		backstage
				.setQualityStrategyBeforeSellin(new BackstageQualityUpdateStrategy());
		backstage
				.setQualityStrategyAfterSellin(new ZeroQualityUpdateStrategy());
		passNoOfDays(backstage,14);
		assertEquals(47,backstage.getQuality());
	}
	
	//Tests if quality of Backstage Passes item is reset to 0 days after Sell By date has passed
	@Test
	public void testBackstagePassesQualityResetToZeroAfterSellByDate() throws InvalidItemException {
		MaintainableItem backstage = new MaintainableItem("Backstage passes to a TAFKAL80ETC concert", 15, 20);
		backstage
				.setQualityStrategyBeforeSellin(new BackstageQualityUpdateStrategy());
		backstage
				.setQualityStrategyAfterSellin(new ZeroQualityUpdateStrategy());
		passNoOfDays(backstage,16);
		assertEquals(0,backstage.getQuality());
	}
	
	//Tests if quality of a conjured item is decreasing by 2 before Sell By date has passed
	@Test
	public void testConjuredItemDecreasesQualityBytwoBeforeSellByDate() throws InvalidItemException {
		MaintainableItem conjured = new MaintainableItem("Conjured Mana Cake", 3, 6);
		conjured.setQualityStrategyBeforeSellin(new DoubleDegradingQualityUpdateStrategy());
		conjured.setQualityStrategyAfterSellin(new SuperFastDegradingQualityUpdateStrategy());
		passNoOfDays(conjured,3);
		assertEquals(0,conjured.getQuality());
	}
	
	//Tests if quality of a conjured item is decreasing by 4 after Sell By date has passed
	@Test
	public void testConjuredItemDecreasesQualityByfourAfterSellByDate() throws InvalidItemException {
		MaintainableItem conjured = new MaintainableItem("Conjured Mana Cake", 3, 14);
		conjured.setQualityStrategyBeforeSellin(new DoubleDegradingQualityUpdateStrategy());
		conjured.setQualityStrategyAfterSellin(new SuperFastDegradingQualityUpdateStrategy());
		passNoOfDays(conjured,5);
		assertEquals(0,conjured.getQuality());
		
	}
	
	
	
}
