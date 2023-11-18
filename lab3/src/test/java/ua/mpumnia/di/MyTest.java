package ua.mpumnia.di;

import org.fpm.di.Container;
import org.fpm.di.Environment;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class MyTest {

    private final Environment environment = new BasicEnvironment();

    @Test(expected = BindException.class)
    public void shouldThrowBindExceptionWhenTryingToRebindWithClass() {
        environment.configure((binder) -> {
            binder.bind(Fruit.class, Apple.class);
            binder.bind(Fruit.class, Orange.class);
        });
    }

    @Test(expected = BindException.class)
    public void shouldThrowBindExceptionWhenTryingToRebindWithInstance() {
        environment.configure((binder) -> {
            binder.bind(Fruit.class, Apple.class);
            binder.bind(Fruit.class, new Orange());
        });
    }

    @Test(expected = BindException.class)
    public void shouldThrowBindExceptionWhenTryingToRegisterAbstractClass() {
        environment.configure((binder) -> {
            binder.bind(Fruit.class);
        });
    }

    @Test(expected = BindException.class)
    public void shouldThrowBindExceptionWhenTryingToRegisterInterface() {
        environment.configure((binder) -> {
            binder.bind(Eatable.class);
        });
    }

    @Test(expected = BindException.class)
    public void shouldThrowBindExceptionWhenTryingToRegisterNull() {
        environment.configure((binder) -> {
            binder.bind(null);
        });
    }

    @Test(expected = BindException.class)
    public void shouldThrowBindExceptionWhenTryingToRegisterClassWithMoreThanOneInjectionConstructor() {
        environment.configure((binder) -> {
            binder.bind(ClassWithTwoConstructorInjection.class);
        });
    }

    @Test(expected = UnregisteredComponentException.class)
    public void shouldThrowUnregisteredComponentExceptionWhenTryingToGetUnregisteredComponent() {
        Container container = environment.configure((binder) -> {});
        container.getComponent(Apple.class);
    }

    @Test(expected = UnregisteredComponentException.class)
    public void shouldThrowUnregisteredComponentExceptionWhenTryingToGetComponentWhichDependenciesNotRegistered() {
        Container container = environment.configure((binder) -> {
            binder.bind(AppleSharer.class);
        });
        container.getComponent(AppleSharer.class);
    }

    @Test(expected = CircularInjectException.class)
    public void shouldThrowCircularInjectExceptionWhenTryingToRegisterComponentWithCircularInjectDependency() {
        environment.configure((binder) -> {
            binder.bind(A.class);
            binder.bind(B.class);
            binder.bind(C.class);
        });
    }

    @Test
    public void shouldResolveSingletonWithInjection() {
        Container container = environment.configure((binder) -> {
            binder.bind(Apple.class);
            binder.bind(AppleSharer.class);
        });
        /* @Singleton AppleSharer */
        AppleSharer sharer1 = container.getComponent(AppleSharer.class);
        AppleSharer sharer2 = container.getComponent(AppleSharer.class);
        assertSame(sharer1, sharer2);
        assertSame(sharer1.shareApple(), sharer2.shareApple());
    }

    @Test
    public void shouldResolveNestedInjectDependency() {
        Container container = environment.configure((binder) -> {
            binder.bind(ClassWithNestedInjection.class);
            binder.bind(Apple.class);
            binder.bind(AppleSharer.class);
        });
        ClassWithNestedInjection c = container.getComponent(ClassWithNestedInjection.class);
        assertSame(c.getSharer(), container.getComponent(AppleSharer.class));
    }

}
