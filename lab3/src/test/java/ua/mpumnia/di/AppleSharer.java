package ua.mpumnia.di;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppleSharer {

    private final Apple apple;

    @Inject
    public AppleSharer(Apple apple) {
        this.apple = apple;
    }

    public Apple shareApple() {
        return apple;
    }

}
