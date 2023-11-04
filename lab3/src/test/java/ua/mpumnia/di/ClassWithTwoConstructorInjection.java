package ua.mpumnia.di;

import javax.inject.Inject;

public class ClassWithTwoConstructorInjection {

    private Apple apple;
    private AppleSharer appleSharer;

    @Inject
    public ClassWithTwoConstructorInjection(Apple apple) {
        this.apple = apple;
    }

    @Inject
    public ClassWithTwoConstructorInjection(AppleSharer appleSharer) {
        this.appleSharer = appleSharer;
    }

}
