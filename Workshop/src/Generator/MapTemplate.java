package Generator;

import Bing.StaticMap;

public class MapTemplate extends AbstractTemplate{


	private Slot mapSlot;

	private StaticMap map = null; // map in center of collage
	/**
	 * Template can only be created by static methods
	 * @param slotsInTemplate
	 * @param width map pixel width 
	 * @param height map pixel height
	 */
	private MapTemplate(int slotsInTemplate) {
		super(slotsInTemplate);
	}

	public double getMapPixelWidth() {
		return mapSlot.getSlotWidth();
	}

	public double getMapPixelHeight() {
		return mapSlot.getSlotHeight();
	}

	public boolean setMap(StaticMap newMap) {
		if (newMap.getPixelWidth() == this.getMapPixelWidth() && // only add map if it meets the planned map dimension
				newMap.getPixelHeight() == this.getMapPixelHeight()) {
			map = newMap;
			return true;
		}
		else {
			return false;
		}
	}
	
	public Slot getMapSlot() {
		return this.mapSlot;
	}
	
	public static MapTemplate getTemplate(int num) {
		MapTemplate template = null;
		switch (num) {
		case 1:
			template =  getTemplate1();
		default:
			break;
		}
		
		return template;
	}

	/**
	 * Constructing hard-coded template1
	 * @return
	 */
	private static MapTemplate getTemplate1() {
		MapTemplate template = new MapTemplate(8);

		// building slots - Counter Clockwise, starting with topLeft
		template.addSlot(new Slot(
				new PixelPoint(0, 367), 
				new PixelPoint(642, 1224)),
				0);
		template.addSlot(new Slot(
				new PixelPoint(0, 1224), 
				new PixelPoint(642, 2080)), 
				1);
		template.addSlot(new Slot(
				new PixelPoint(775, 1805), 
				new PixelPoint(1632, 2448)), 
				2);
		template.addSlot(new Slot(
				new PixelPoint(1632, 1805),
				new PixelPoint(2488, 2448)), 
				3);
		template.addSlot(new Slot(
				new PixelPoint(2621, 1224),
				new PixelPoint(3264, 2080)),
				4);
		template.addSlot(new Slot(
				new PixelPoint(2621, 367), 
				new PixelPoint(3264, 1224)), 
				5);
		template.addSlot(new Slot(new PixelPoint(1632, 0),
				new PixelPoint(2488, 642)), 
				6);
		template.addSlot(new Slot(
				new PixelPoint(775, 0),
				new PixelPoint(1632, 642)),
				7);

		template.mapSlot = new Slot(new PixelPoint(642, 642), new PixelPoint(2621, 1805));

		return template;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("[");
		for (int i = 0; i<slots.length; i++) {
			buffer.append(i + "=" + slots[i].getSlotWidth() + "," + slots[i].getSlotHeight() + ";" );
		}
		
		buffer.append("map=" + mapSlot.getSlotWidth() + "," + mapSlot.getSlotHeight());
		buffer.append("]");
		
		return buffer.toString();
	}

}
