package generator;

public interface IRelation {

	/**
	 * Returns the name of the class that it points from.
	 *
	 * @return String of the Class name this relationship is pointing from.
	 */
	String getFrom();

	/**
	 * Returns the name of the class it is pointing to.
	 *
	 * @return String of the Class name this relationship is pointing to.
	 */
	String getTo();

	/**
	 * @return the edge style of this relation
	 */
	String getEdgeStyle();
}
