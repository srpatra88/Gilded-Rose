package com.patra.gildedrose.quality.impl;
import com.patra.gildedrose.MaintainableItem;
import com.patra.gildedrose.quality.*;

public class ImprovingQualityUpdateStrategy implements QualityUpdateStrategy{
	
	@Override
	public void updateQuality(MaintainableItem item){
		item.increaseQuality();
	}

}
