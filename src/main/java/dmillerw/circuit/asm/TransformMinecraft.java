package dmillerw.circuit.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;

/**
 * @author dmillerw
 */
public class TransformMinecraft implements IClassTransformer {

    public static final String TARGET_METHOD_NAME = CorePlugin.obfuscated ? "" : "runTick";

    @Override
    public byte[] transform(String obfName, String deobfName, byte[] data) {
        if (obfName.equals("net.minecraft.client.Minecraft")) {
            ClassReader classReader = new ClassReader(data);
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, 0);

            // Modify findPlayerToAttack
            for (MethodNode methodNode : classNode.methods) {
                if (methodNode.name.equals(TARGET_METHOD_NAME)) {
                    Iterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                    while (iterator.hasNext()) {
                        AbstractInsnNode node = iterator.next();

                        if (node.getOpcode() == Opcodes.INVOKESTATIC) {
                            MethodInsnNode mNode = (MethodInsnNode) node;

                            // Divert scroll-wheel check to our method
                            if (mNode.owner.equals("org/lwjgl/input/Mouse") && mNode.name.equals("getEventDWheel")) {
                                mNode.owner = "dmillerw/circuit/client/handler/ClientEventHandler";
                                break;
                            }
                        }
                    }
                }
            }

            //COMPUTE_FRAMES is not added due to an issue with getCommonSuperClass. Refer to this for more info: http://www.minecraftforge.net/forum/index.php?topic=20911.0
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        } else {
            return data;
        }
    }
}
