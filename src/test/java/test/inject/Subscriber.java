package test.inject;

import javax.inject.Inject;

/**
 * Date: 4/27/13
 * Time: 12:00 PM
 *
 * @author Artem Prigoda
 */
public class Subscriber {

    @Inject
    private Dao dao;

    public Subscriber() {
    }

    public Subscriber(Dao dao) {
        this.dao = dao;
    }

    public Dao getDao() {
        return dao;
    }
}
