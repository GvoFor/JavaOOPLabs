package ua.mpumnia.di;

import javax.inject.Inject;

public class ClassWithNestedInjection {

    private final AppleSharer sharer;

    @Inject
    public ClassWithNestedInjection(AppleSharer sharer) {
        this.sharer = sharer;
    }

    public AppleSharer getSharer() {
        return sharer;
    }

}
