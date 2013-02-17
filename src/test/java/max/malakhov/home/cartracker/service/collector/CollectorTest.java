package max.malakhov.home.cartracker.service.collector;

import org.testng.annotations.Test;

/**
 * @author Max Malakhov <malakhovbox@gmail.com>
 * @version 0.1
 * @since 2013-02-17
 */
public class CollectorTest {

    private Collector collector;

    public CollectorTest() {
        collector = Collector.getInstance();
    }

    @Test
    public void testPerform() throws Exception {
        collector.schedule();
    }
}
