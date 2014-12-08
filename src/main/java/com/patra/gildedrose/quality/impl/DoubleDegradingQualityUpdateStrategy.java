package com.patra.gildedrose.quality.impl;
import com.patra.gildedrose.MaintainableItem;
import com.patra.gildedrose.quality.*;

public class DoubleDegradingQualityUpdateStrategy implements QualityUpdateStrategy{
	
	@Override
	public void updateQuality(MaintainableItem item){
		item.decreaseQuality();
		item.decreaseQuality();
	}

}
