package com.patra.gildedrose.quality.impl;
import com.patra.gildedrose.MaintainableItem;
import com.patra.gildedrose.quality.*;

public class SuperFastDegradingQualityUpdateStrategy implements QualityUpdateStrategy{
	
	@Override
	public void updateQuality(MaintainableItem item){
		for(int i=1;i<=4;i++){
			item.decreaseQuality();
		}
	}

}
