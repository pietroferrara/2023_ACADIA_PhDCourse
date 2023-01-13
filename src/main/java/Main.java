import it.unive.lisa.AnalysisException;
import it.unive.lisa.LiSA;
import it.unive.lisa.LiSAConfiguration;
import it.unive.lisa.analysis.SimpleAbstractState;
import it.unive.lisa.analysis.heap.MonolithicHeap;
import it.unive.lisa.analysis.nonrelational.value.TypeEnvironment;
import it.unive.lisa.analysis.nonrelational.value.ValueEnvironment;
import it.unive.lisa.analysis.types.InferredTypes;
import it.unive.lisa.analysis.value.TypeDomain;
import it.unive.lisa.imp.IMPFrontend;
import it.unive.lisa.imp.ParsingException;
import it.unive.lisa.program.Program;

public class Main {
    public static void main(String[] args) throws ParsingException, AnalysisException {
        Program program = IMPFrontend.processFile("program.imp");
        LiSAConfiguration conf = new LiSAConfiguration();
        conf.jsonOutput = true;
        conf.analysisGraphs = LiSAConfiguration.GraphType.DOT;
        conf.workdir = "output";
        conf.abstractState =
                new SimpleAbstractState<>(
                        new MonolithicHeap(),
                        new ValueEnvironment<>(ACADIASignDomain.top),
                        new TypeEnvironment<>(new InferredTypes())
                );
        LiSA lisa = new LiSA(conf);
        lisa.run(program);
        System.out.println("Hello world");
    }

}
