package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the model's HasRelations.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizHasRelParser implements IParser<IClassModel> {
    private Collection<IModifier> filters;

    GraphVizHasRelParser(Collection<IModifier> filters) {
        this.filters = filters;
    }

    @Override
    public String parse(IClassModel thisClass) {
        Iterable<? extends IClassModel> otherClassList = thisClass.getHasRelation();

        StringBuilder sb = new StringBuilder();
        GraphVizDependencyFormatter.setupDependencyVizDescription(sb, thisClass.getName());
        int hasALengthBefore = sb.length();

        otherClassList.forEach((has) -> {
            if (!filters.contains(has.getModifier())) {
                sb.append(String.format("\"%s\", ", has.getName()));
            }
        });

        GraphVizDependencyFormatter.closeDependencyVizDescription(sb, hasALengthBefore);
        sb.append("\n\t");

        return sb.toString();
    }

}
