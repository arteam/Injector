package test.inject;

import javax.inject.Inject;

/**
 * Date: 4/27/13
 * Time: 12:00 PM
 *
 * @author Artem Prigoda
 */
public class Admin {

    @Inject
    private Subscriber subscriber;

    @Inject
    private Dao dao;

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public Dao getDao() {
        return dao;
    }
}
