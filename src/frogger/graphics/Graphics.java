package frogger.graphics;

import java.util.List;

import frogger.World;
import jig.engine.ImageResource;
import jig.engine.PaintableCanvas;
import jig.engine.PaintableCanvas.JIGSHAPE;
import jig.engine.ResourceFactory;
import jig.engine.hli.ImageBackgroundLayer;

public class Graphics {
    
    public static final String RSC_PATH = "resources/";
    public static final String SPRITE_SHEET = "frogger_sprites.png";
    public static final String SPRITE_SHEET_PATH = RSC_PATH + SPRITE_SHEET;
    
    private ImageBackgroundLayer backgroundLayer;

    public Graphics() {
        initialize();
    }

    private void initialize() {
        ResourceFactory.getFactory().loadResources(RSC_PATH, "resources.xml");

        ImageResource bkg = ResourceFactory.getFactory().getFrames(getSpritePath("background")).get(0);
        setBackgroundLayer(new ImageBackgroundLayer(bkg, World.WORLD_WIDTH, World.WORLD_HEIGHT, ImageBackgroundLayer.TILE_IMAGE));

        PaintableCanvas.loadDefaultFrames("col", 30, 30, 2, JIGSHAPE.RECTANGLE, null);
        PaintableCanvas.loadDefaultFrames("colSmall", 4, 4, 2, JIGSHAPE.RECTANGLE, null);
    }
    
    public static String getSpritePath(String resource) {
        return SPRITE_SHEET_PATH + "#" + resource;
    }
    
    public static List<ImageResource> getImageResources(String resourceName) {
        String spritePath = getSpritePath(resourceName);
        ResourceFactory factory = ResourceFactory.getFactory(); 
        return factory.getFrames(spritePath);
    }

    public ImageBackgroundLayer getBackgroundLayer() {
        return backgroundLayer;
    }

    private void setBackgroundLayer(ImageBackgroundLayer backgroundLayer) {
        this.backgroundLayer = backgroundLayer;
    }
}
