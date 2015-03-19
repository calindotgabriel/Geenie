package ro.geenie.models;

/**
 * Created by motan on 18.03.2015.
 */
public class Item implements ItemInterface {

    @Override
    public String getTitle() {
        throw new UnsupportedOperationException("Called super's method");
    }

    @Override
    public String getSubtitle() {
        throw new UnsupportedOperationException("Called super's method");
    }
}
