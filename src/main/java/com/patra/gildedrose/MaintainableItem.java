package com.patra.gildedrose;

import com.patra.exceptions.InvalidItemException;
import com.patra.gildedrose.quality.QualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.DegradingQualityUpdateStrategy;
import com.patra.gildedrose.quality.impl.DoubleDegradingQualityUpdateStrategy;
import com.patra.gildedrose.sellin.SellInUpdateStrategy;
import com.patra.gildedrose.sellin.impl.RegularItemSellinUpdateStrategy;

public class MaintainableItem extends Item{

	private static final int MAX_POSSIBLE_QUALITY= 50;
	private static final int MIN_POSSIBLE_QUALITY= 0;
	
	private SellInUpdateStrategy sellInStrategy;
	private QualityUpdateStrategy qualityStrategyBeforeSellin;
	private QualityUpdateStrategy qualityStrategyAfterSellin;
	
	
	
	public SellInUpdateStrategy getSellInStrategy() {
		return sellInStrategy;
	}


	public void setSellInStrategy(SellInUpdateStrategy sellInStrategy) {
		this.sellInStrategy = sellInStrategy;
	}


	public QualityUpdateStrategy getQualityStrategyBeforeSellin() {
		return qualityStrategyBeforeSellin;
	}


	public void setQualityStrategyBeforeSellin(
			QualityUpdateStrategy qualityStrategyBeforeSellin) {
		this.qualityStrategyBeforeSellin = qualityStrategyBeforeSellin;
	}


	public QualityUpdateStrategy getQualityStrategyAfterSellin() {
		return qualityStrategyAfterSellin;
	}


	public void setQualityStrategyAfterSellin(
			QualityUpdateStrategy qualityStrategyAfterSellin) {
		this.qualityStrategyAfterSellin = qualityStrategyAfterSellin;
	}


	public MaintainableItem(String name, int sellIn, int quality) throws InvalidItemException {
		super(name, sellIn, quality);
		if(quality < 0){
			throw new InvalidItemException();
		}
		setDefaultUpdateStrategies();
	}


	private void setDefaultUpdateStrategies() {
		this.sellInStrategy = new RegularItemSellinUpdateStrategy();
		this.qualityStrategyBeforeSellin = new DegradingQualityUpdateStrategy();
		this.qualityStrategyAfterSellin = new DoubleDegradingQualityUpdateStrategy();
	}
	
	public void increaseQuality(){
		if(getQuality() < MAX_POSSIBLE_QUALITY){
			setQuality(getQuality()+1);
		}
	}
	
	public void decreaseQuality(){
		if(getQuality() > MIN_POSSIBLE_QUALITY){
			setQuality(getQuality()-1);
		}
	}

	public void decreaseSellIn(){
			setSellIn(getSellIn()-1);
	}
	
	private boolean isSellByDatePassed() {
		return getSellIn()<0;
	}
	
	public void updateItem(){
		sellInStrategy.UpdateSellin(this);
		if(!isSellByDatePassed()){
			qualityStrategyBeforeSellin.updateQuality(this);
		}
		else{
			qualityStrategyAfterSellin.updateQuality(this);
		}
		
	}
}
