package test.inject;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Random;

/**
 * Date: 4/28/13
 * Time: 11:05 AM
 *
 * @author Artem Prigoda
 */
@Singleton
public class DataSource {

    private String source = "DataSource";

    @Inject
    private Random random;

    public String getSource() {
        return source;
    }

}
