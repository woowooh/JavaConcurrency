import java.util.concurrent.Executor;

public class serialToParallel {
    void processSequentially(List<Element> elements) {
        for (Element e: elements) {
            process(e);
        }
    }

    void processInParallel(Executor exec, List<Element> elements) {
        for (final Element e: elements) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    process(e);
                }
            });
        }
    }
}
