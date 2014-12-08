package com.patra.gildedrose.quality.impl;
import com.patra.gildedrose.MaintainableItem;
import com.patra.gildedrose.quality.*;

public class BackstageQualityUpdateStrategy implements QualityUpdateStrategy{
	
	@Override
	public void updateQuality(MaintainableItem item){
		item.increaseQuality();
		if(item.getSellIn() < 10){
			item.increaseQuality();
		}
		if(item.getSellIn() < 5){
			item.increaseQuality();
		}
	}

}
