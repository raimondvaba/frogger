package frogger.graphics;

import frogger.World;
import jig.engine.ImageResource;
import jig.engine.PaintableCanvas;
import jig.engine.PaintableCanvas.JIGSHAPE;
import jig.engine.ResourceFactory;
import jig.engine.hli.ImageBackgroundLayer;

public class Graphics {
    
    public static final String RSC_PATH = "resources/";
    public static final String SPRITE_SHEET = RSC_PATH + "frogger_sprites.png";
    
    private ImageBackgroundLayer backgroundLayer;

    public Graphics() {
        initialize();
    }

    private void initialize() {
        ResourceFactory.getFactory().loadResources(RSC_PATH, "resources.xml");

        ImageResource bkg = ResourceFactory.getFactory().getFrames(SPRITE_SHEET + "#background").get(0);
        setBackgroundLayer(new ImageBackgroundLayer(bkg, World.WORLD_WIDTH, World.WORLD_HEIGHT, ImageBackgroundLayer.TILE_IMAGE));

        PaintableCanvas.loadDefaultFrames("col", 30, 30, 2, JIGSHAPE.RECTANGLE, null);
        PaintableCanvas.loadDefaultFrames("colSmall", 4, 4, 2, JIGSHAPE.RECTANGLE, null);
    }

    public ImageBackgroundLayer getBackgroundLayer() {
        return backgroundLayer;
    }

    private void setBackgroundLayer(ImageBackgroundLayer backgroundLayer) {
        this.backgroundLayer = backgroundLayer;
    }
}
