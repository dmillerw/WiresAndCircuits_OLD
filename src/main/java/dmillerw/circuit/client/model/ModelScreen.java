package dmillerw.circuit.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelScreen extends ModelBase {

    public ModelRenderer right;
    public ModelRenderer screen;
    public ModelRenderer top;
    public ModelRenderer bottom;
    public ModelRenderer left;

    public ModelScreen() {
        textureWidth = 64;
        textureHeight = 32;

        right = new ModelRenderer(this, 0, 0);
        right.addBox(0F, 0F, 0F, 12, 2, 2);
        right.setRotationPoint(-6F, 14F, 6F);
        right.setTextureSize(64, 32);
        right.mirror = true;
        setRotation(right, 0F, 0F, 0F);
        screen = new ModelRenderer(this, 0, 0);
        screen.addBox(0F, 0F, 0F, 12, 1, 12);
        screen.setRotationPoint(-6F, 15F, -6F);
        screen.setTextureSize(64, 32);
        screen.mirror = true;
        setRotation(screen, 0F, 0F, 0F);
        top = new ModelRenderer(this, 0, 0);
        top.addBox(0F, 0F, 0F, 2, 2, 16);
        top.setRotationPoint(-8F, 14F, -8F);
        top.setTextureSize(64, 32);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
        bottom = new ModelRenderer(this, 0, 0);
        bottom.addBox(0F, 0F, 0F, 2, 2, 16);
        bottom.setRotationPoint(6F, 14F, -8F);
        bottom.setTextureSize(64, 32);
        bottom.mirror = true;
        setRotation(bottom, 0F, 0F, 0F);
        left = new ModelRenderer(this, 0, 0);
        left.addBox(0F, 0F, 0F, 12, 2, 2);
        left.setRotationPoint(-6F, 14F, -8F);
        left.setTextureSize(64, 32);
        left.mirror = true;
        setRotation(left, 0F, 0F, 0F);
    }

    public void render() {
        right.render(0.0625F);
        screen.render(0.0625F);
        top.render(0.0625F);
        bottom.render(0.0625F);
        left.render(0.0625F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
