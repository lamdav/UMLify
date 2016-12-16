package utility;

import org.objectweb.asm.Opcodes;

/**
 * use to represent each modifier level in java
 *
 * @author zhang
 */
public enum Modifier {
	PUBLIC("+"), DEFAULT(" "), PROTECTED("#"), PRIVATE("-");

	private final String modifierValue;

	Modifier(String modifierValue) {
		this.modifierValue = modifierValue;
	}

	public static Modifier parse(int access) {
		if ((access & Opcodes.ACC_PUBLIC) != 0)
			return PUBLIC;
		if ((access & Opcodes.ACC_PRIVATE) != 0)
			return PRIVATE;
		if ((access & Opcodes.ACC_PROTECTED) != 0)
			return PROTECTED;
		return DEFAULT;
	}

	public static boolean parseIsFinal(int access) {
		return (access & Opcodes.ACC_FINAL) != 0;
	}

	public String getModifierSymbol() {
		return modifierValue;
	}
	
}