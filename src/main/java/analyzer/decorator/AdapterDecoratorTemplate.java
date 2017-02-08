package analyzer.decorator;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

import java.util.Collection;
import java.util.LinkedList;

/**
 * An Abstract Template for Adapter and Decorator pattern detection.
 * <p>
 * Created by lamd on 2/2/2017.
 */
public abstract class AdapterDecoratorTemplate implements IAnalyzer {
    protected IAdapterDecoratorConfiguration config;

    @Override
    public final void analyze(ISystemModel systemModel, IConfiguration config) {
        this.config = setupConfig(config);
        updateModel(systemModel);
    }

    protected abstract IAdapterDecoratorConfiguration setupConfig(IConfiguration config);

    private void updateModel(ISystemModel systemModel) {
        systemModel.getClasses().forEach((clazz) -> evaluateClass(systemModel, clazz));
    }

    /**
     * Returns a Collection of ClassModel that are parents of the given ClassModel that fulfills the evaluation criteria
     * defined by the subclass.
     * <p>
     *
     * @param child IClassModel to be evaluated.
     */
    private void evaluateClass(ISystemModel systemModel, IClassModel child) {
        getPotentialParents(child).stream()
                .filter((parent) -> detectPattern(child, parent))
                .forEach((parent) -> {
                    styleParent(systemModel, parent);
                    styleChild(systemModel, child);
                    styleChildParentRelationship(systemModel, child, parent);
                    updateRelatedClasses(systemModel, child);
                });
    }

    private Collection<IClassModel> getPotentialParents(IClassModel child) {
        Collection<IClassModel> potentialParents = new LinkedList<>();

        potentialParents.add(child.getSuperClass());
        child.getInterfaces().forEach(potentialParents::add);
        potentialParents.add(child);

        return potentialParents;
    }

    /**
     * Define how to style the parent of the Child-to-Parent Relationship.
     *
     * @param systemModel ISystemModel that stores the styling information per class.
     * @param parent      IClassModel of the parent class in the Child-to-Parent Relationship.
     */
    protected abstract void styleParent(ISystemModel systemModel, IClassModel parent);

    /**
     * Define how to style the child of the Child-to-Parent Relationship.
     *
     * @param systemModel ISystemModel that stores the styling information per class.
     * @param child       IClassModel of the child class in the Child-to-Parent Relationship.
     */
    protected abstract void styleChild(ISystemModel systemModel, IClassModel child);

    /**
     * Define how to style the Child-to-Parent Relationship.
     *
     * @param systemModel ISystemModel that stores the styling information per class.
     * @param child       IClassModel of a child of a Superclass relation.
     * @param parent      IClassModel of a parent of a Superclass relation.
     */
    protected abstract void styleChildParentRelationship(ISystemModel systemModel, IClassModel child, IClassModel parent);

    /**
     * Updates Classes related the the class clazz if necessary. This is a hook method.
     *
     * @param systemModel ISystemModel that stores the styling information per class.
     * @param clazz       IClassModel of the child class with possible subclasses to be updated.
     */
    protected void updateRelatedClasses(ISystemModel systemModel, IClassModel clazz) {
        // Hook
    }

    /**
     * Evaluates a given parent class and the child class and determine whether they meet the desired pattern criteria.
     * <p>
     * For example: decorator detection may check if child has a field of the parent,
     * a constructor that takes the field as an argument, and if the child overrides each of the parent's
     * methods where the child method's body uses the field of the parent type.
     *
     * @param child  IClassModel of the dependent Relation.
     * @param parent IClassModel of the depended Relation.
     * @return true if the parent and child should be updated for this analyzer.
     */
    protected abstract boolean detectPattern(IClassModel child, IClassModel parent);
}
