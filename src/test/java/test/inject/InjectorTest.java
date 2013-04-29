package test.inject;

import org.injector.Injector;
import org.junit.Assert;
import org.junit.Test;

/**
 * Date: 4/27/13
 * Time: 2:19 PM
 *
 * @author Artem Prigoda
 */
public class InjectorTest {

    private static final Injector injector = Injector.create();

    @Test
    public void testInjectOnFields() {
        Admin admin = injector.get(Admin.class);
        System.out.println(admin);
        System.out.println(admin.getDao());
        System.out.println(admin.getSubscriber());
        Assert.assertNotNull(admin);
        Assert.assertNotNull(admin.getDao());
        Assert.assertNotNull(admin.getSubscriber());
    }

    @Test
    public void testInjectOnConstructor() {
        Dao dao = injector.get(Dao.class);
        System.out.println(dao);
        System.out.println(dao.getDataSource());
        Assert.assertNotNull(dao);
        Assert.assertNotNull(dao.getDataSource());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoMatchedConstructor() {
        injector.get(NotInjectSubscriber.class);
    }

    @Test
    public void testSingletonScope() {
        DataSource dataSource1 = injector.get(DataSource.class);
        DataSource dataSource2 = injector.get(DataSource.class);
        System.out.println(dataSource1);
        System.out.println(dataSource2);
        Assert.assertSame(dataSource1, dataSource2);
    }

    @Test
    public void testPrototypeScope() {
        Subscriber subscriber1 = injector.get(Subscriber.class);
        Subscriber subscriber2 = injector.get(Subscriber.class);
        System.out.println(subscriber1);
        System.out.println(subscriber2);
        Assert.assertNotSame(subscriber1, subscriber2);
    }

    @Test
    public void testInjectSingletonToSingleton() {
        Dao dao = injector.get(Dao.class);
        System.out.println(dao);
        System.out.println(dao.getDataSource());
        Assert.assertNotNull(dao);
        Assert.assertNotNull(dao.getDataSource());
    }

    @Test
    public void testInjectSingletonToPrototype() {
        Subscriber subscriber = injector.get(Subscriber.class);
        System.out.println(subscriber);
        System.out.println(subscriber.getDao());
        Assert.assertNotNull(subscriber);
        Assert.assertNotNull(subscriber.getDao());
    }

    @Test
    public void testInjectPrototypeToPrototype() {
        Admin admin = injector.get(Admin.class);
        System.out.println(admin);
        System.out.println(admin.getSubscriber());
        Assert.assertNotNull(admin);
        Assert.assertNotNull(admin.getSubscriber());
    }


}
