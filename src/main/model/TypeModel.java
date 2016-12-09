package main.model;

import org.objectweb.asm.Type;

/**
 * 
 * @author zhang
 *
 */
public class TypeModel {
	private final ClassModel classModel;
	private final int dimension;
	private final PrimitiveType primiType;

	public TypeModel(ClassModel classModel, int dimension, PrimitiveType primiType) {
		this.classModel = classModel;
		this.dimension = dimension;
		this.primiType = primiType;
	}

	public ClassModel getClassModel() {
		return classModel;
	}

	public int getDimension() {
		return dimension;
	}

	public String getName() {
		if (classModel != null)
			return classModel.getName();
		return primiType.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TypeModel) {
			TypeModel o = (TypeModel) obj;
			return classModel == o.classModel && primiType == o.primiType && dimension == o.dimension;
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (classModel == null)
			return primiType.hashCode() + dimension;
		return classModel.hashCode() + primiType.hashCode() + dimension;
	}

	public static TypeModel parse(ASMServiceProvider serviceProvider, Type type) {
		int dimension = type.getDimensions();
		if (type.getSort() == Type.ARRAY)
			type = type.getElementType();
		PrimitiveType primiType = PrimitiveType.parse(type);
		ClassModel classModel;
		if (primiType == PrimitiveType.OBJECT)
			classModel = serviceProvider.getClassByName(type.getClassName());
		else
			classModel = null;
		return new TypeModel(classModel, dimension, primiType);
	}

	public enum PrimitiveType {
		INT, DOUBLE, FLOAT, BOOLEAN, BYTE, CHAR, SHORT, LONG, OBJECT, VOID;

		String getName() {
			switch (this) {
			case VOID:
				return "void";
			case INT:
				return "int";
			case DOUBLE:
				return "double";
			case FLOAT:
				return "float";
			case BOOLEAN:
				return "boolean";
			case BYTE:
				return "byte";
			case CHAR:
				return "char";
			case SHORT:
				return "short";
			case LONG:
				return "long";
			case OBJECT:
			default:
				throw new RuntimeException(" getName(): We missed " + this);
			}
		}

		public static PrimitiveType parse(Type type) {
			switch (type.getSort()) {
			case Type.OBJECT:
				return OBJECT;
			case Type.VOID:
				return VOID;
			case Type.INT:
				return INT;
			case Type.DOUBLE:
				return DOUBLE;
			case Type.CHAR:
				return CHAR;
			case Type.BOOLEAN:
				return BOOLEAN;
			case Type.LONG:
				return LONG;
			case Type.BYTE:
				return BYTE;
			case Type.SHORT:
				return SHORT;
			case Type.ARRAY:
			default:
				throw new RuntimeException("does not suport type sort " + type.getClassName());
			}
		}
	}
}
