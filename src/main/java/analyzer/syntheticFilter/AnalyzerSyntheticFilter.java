package analyzer.syntheticFilter;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

public class AnalyzerSyntheticFilter implements IAnalyzer {

    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        return new SyntheticFilterSystemModel(systemModel);
    }

}
