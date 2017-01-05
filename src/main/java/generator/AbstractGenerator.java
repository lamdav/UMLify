package generator;

import generator.classParser.IClassModel;
import generator.classParser.IParser;
import generator.relParser.IParseGuide;
import generator.relParser.Relation;

import java.util.Map;

/**
 * An abstract class for Generators.
 * <p>
 * Created by lamd on 12/17/2016.
 */
public abstract class AbstractGenerator implements IGenerator {
    private final IParser<IClassModel> classModelParser;
    // private final Collection<IParseGuide> relParsers;
    private final String basicConfiguration;
    private final Map<Class<? extends Relation>, IParseGuide> relationshipFormat;

    AbstractGenerator(IGeneratorConfiguration config) {
        this.classModelParser = createClassParser(config);
        // this.relParsers = createRelationshipParsers(config);
        this.basicConfiguration = createBasicConfiguration(config);
        this.relationshipFormat = defineEdgeFormat(config);
    }

    @Override
    public String generate(ISystemModel sm) {
        // DOT parent.
        Iterable<? extends IClassModel> classes = sm.getClasses();
        StringBuilder dotString = new StringBuilder();

        // Basic Configurations.
        dotString.append(basicConfiguration);

        // Parse the class
        dotString.append(classModelParser.parse(classes)).append('\n');

        // Parse each relationship.
        Iterable<Relation> relations = sm.getRelations();
        relations.forEach(relation -> {
            IParseGuide relParser = this.relationshipFormat.get(relation.getClass());
            dotString.append(String.format("\t\"%s\" -> \"%s\" [%s];\n\n", relation.getFrom(), relation.getTo(), relParser.getEdgeStyle(relation)));
        });

        return String.format("digraph GraphVizGeneratedDOT {\n%s}", dotString.toString());
    }

    /**
     * Returns the String of the Basic Configuration.
     *
     * @param config
     * @return String of the Basic Configuration
     */
    public abstract String createBasicConfiguration(IGeneratorConfiguration config);

    /**
     * Returns the class parser.
     *
     * @return ParseGuide of the Class.
     */
    public abstract IParser<IClassModel> createClassParser(IGeneratorConfiguration config);

    /**
     * Define the format of each type of relationship
     *
     * @param config
     * @return
     */
    public abstract Map<Class<? extends Relation>, IParseGuide> defineEdgeFormat(IGeneratorConfiguration config);
}
