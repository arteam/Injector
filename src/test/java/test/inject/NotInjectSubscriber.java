package test.inject;

import javax.inject.Inject;

/**
 * Date: 4/27/13
 * Time: 12:00 PM
 *
 * @author Artem Prigoda
 */
public class NotInjectSubscriber {

    @Inject
    private Dao dao;

    public NotInjectSubscriber(Dao dao) {
        this.dao = dao;
    }

    public Dao getDao() {
        return dao;
    }
}
