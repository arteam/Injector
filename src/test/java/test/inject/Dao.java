package test.inject;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Date: 4/27/13
 * Time: 12:00 PM
 *
 * @author Artem Prigoda
 */
@Singleton
public class Dao {

    private final DataSource dataSource;

    @Inject
    public Dao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
