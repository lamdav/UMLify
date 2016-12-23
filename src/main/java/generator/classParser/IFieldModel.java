package generator.classParser;

import utility.Modifier;

/**
 * An interface for Fields.
 * <p>
 * Created by lamd on 12/9/2016.
 */
public interface IFieldModel {
    /**
     * Returns the access Modifier of the class.
     *
     * @return Field's Acess Modifier.
     */
    Modifier getModifier();

    /**
     * Returns the name of the Field.
     *
     * @return Name of the Field.
     */
    String getName();

    /**
     * Returns the Type MOdel of the Field.
     *
     * @return Type of the Field.
     */
    ITypeModel getType();
}