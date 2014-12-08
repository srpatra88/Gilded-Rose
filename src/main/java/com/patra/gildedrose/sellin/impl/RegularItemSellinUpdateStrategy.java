package com.patra.gildedrose.sellin.impl;

import com.patra.gildedrose.MaintainableItem;
import com.patra.gildedrose.sellin.SellInUpdateStrategy;

public class RegularItemSellinUpdateStrategy implements SellInUpdateStrategy{
	
	@Override
	public void UpdateSellin(MaintainableItem item){
		item.decreaseSellIn();
	}

}
