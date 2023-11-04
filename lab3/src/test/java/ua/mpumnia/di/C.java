package ua.mpumnia.di;

import javax.inject.Inject;

public class C {
    private A a;
    @Inject
    public C(A a) {
        this.a = a;
    }
}
