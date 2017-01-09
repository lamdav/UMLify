package model;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;

import analyzer.IFieldModel;
import utility.Modifier;

/**
 * Representing field in java programs
 *
 * @author zhang
 */
class FieldModel implements IFieldModel {
	private final FieldNode asmFieldNode;
	private final ClassModel belongsTo;

	private final Modifier modifier;
	private final boolean isFinal;
	private final TypeModel fieldType;
	private final boolean isStatic;

	/**
	 * creates an FieldModel given the class it belongs to, and the asmFieldNode
	 *
	 * @param classModel
	 * @param fieldNode
	 */
	FieldModel(ClassModel classModel, FieldNode fieldNode) {
		belongsTo = classModel;
		asmFieldNode = fieldNode;
		modifier = Modifier.parse(asmFieldNode.access);
		isFinal = Modifier.parseIsFinal(asmFieldNode.access);
		isStatic = Modifier.parseIsStatic(asmFieldNode.access);
		if (asmFieldNode.signature != null) {
			TypeModel rawfieldType = TypeParser.parseFieldTypeSignature(asmFieldNode.signature);
			fieldType = rawfieldType.replaceTypeVar(belongsTo.getParamsMap());
		} else {
			fieldType = TypeParser.parse(Type.getType(asmFieldNode.desc));
		}
	}

	public String getName() {
		return asmFieldNode.name;
	}

	public ClassModel getBelongTo() {
		return belongsTo;
	}

	public Modifier getModifier() {
		return modifier;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public TypeModel getFieldType() {
		return fieldType;
	}

	@Override
	public String toString() {
		return getFieldType().toString() + getName();
	}

}
