package Generator;

public class BlockTemplate extends AbstractTemplate{

	private BlockTemplate(int blocks) {
		super(blocks);
	}

	public static BlockTemplate getTemplate(int num) {
		BlockTemplate template = null;
		switch (num) {
		case 1:
			template =  getTemplate1();
		default:
			break;
		}

		return template;
	}

	private static BlockTemplate getTemplate1() {
		BlockTemplate template = new BlockTemplate(9);

		template.addSlot(new Slot(new PixelPoint(0, 0), new PixelPoint(1761, 1357)), 0);
		template.addSlot(new Slot(new PixelPoint(0, 1358), new PixelPoint(1761, 2448)), 1);
		template.addSlot(new Slot(new PixelPoint(1762, 0), new PixelPoint(2715, 1013)), 2);
		template.addSlot(new Slot(new PixelPoint(2716, 0), new PixelPoint(3264, 1013)), 3);
		template.addSlot(new Slot(new PixelPoint(1762, 1014), new PixelPoint(2431, 1725)), 4);
		template.addSlot(new Slot(new PixelPoint(2432, 1014), new PixelPoint(2850, 1775)), 5);
		template.addSlot(new Slot(new PixelPoint(2851, 1014), new PixelPoint(3264, 1775)), 6);
		template.addSlot(new Slot(new PixelPoint(1762, 1725), new PixelPoint(2431, 2448)), 7);
		template.addSlot(new Slot(new PixelPoint(2432, 1775), new PixelPoint(3264, 2448)), 8);

		return template;
	}

}